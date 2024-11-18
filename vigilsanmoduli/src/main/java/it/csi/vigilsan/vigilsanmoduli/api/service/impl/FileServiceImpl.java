/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.api.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.vigilsan.vigilsanmoduli.api.generated.dto.ModelFileGruppo;
import it.csi.vigilsan.vigilsanmoduli.api.generated.dto.ModelFilePost;
import it.csi.vigilsan.vigilsanmoduli.api.generated.dto.ModelFileTipo;
import it.csi.vigilsan.vigilsanmoduli.api.persistence.dao.impl.FileContentTypeDao;
import it.csi.vigilsan.vigilsanmoduli.api.persistence.dao.impl.FileDao;
import it.csi.vigilsan.vigilsanmoduli.api.persistence.dao.impl.FileTipoDao;
import it.csi.vigilsan.vigilsanmoduli.api.persistence.model.File;
import it.csi.vigilsan.vigilsanmoduli.api.persistence.model.FileContentType;
import it.csi.vigilsan.vigilsanmoduli.api.persistence.model.FileTipo;
import it.csi.vigilsan.vigilsanmoduli.api.persistence.repository.impl.FileRepository;
import it.csi.vigilsan.vigilsanmoduli.integration.gatefire.GateFireService;
import it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.Attachment;
import it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.CallInfo;
import it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.SignVerifyResult;
import it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.Signer;
import it.csi.vigilsan.vigilsanmoduli.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanmoduli.util.FileTypeEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTException;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableWPersistenceApiServiceImpl;
import it.csi.vigilsan.vigilsanutil.generic.util.AuditOperazioneEnum;
import it.csi.vigilsan.vigilsanutil.generic.util.pdf.dto.FileCustom;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.core.Response;

@Service
public class FileServiceImpl extends AuditableWPersistenceApiServiceImpl {

	private static final String GATEFIRE_APPLICATION_CODE = "GESTIONESTRUTTURE";

	private static final String GATEFIRE_CA_CODE = "INFOCERT";

	public FileServiceImpl(FilesEncrypt filesEncrypt, FileRepository fileRepository, FileDao fileDao,
			FileContentTypeDao fileContentTypeDao, FileTipoDao fileTipoDao, GateFireService firmeService) {
		super();
		this.filesEncrypt = filesEncrypt;
		this.fileRepository = fileRepository;
		this.fileDao = fileDao;
		this.fileContentTypeDao = fileContentTypeDao;
		this.fileTipoDao = fileTipoDao;
		this.firmeService = firmeService;
	}

	@Autowired
	private FilesEncrypt filesEncrypt;

	@Autowired
	private FileRepository fileRepository;

	@Autowired
	private FileDao fileDao;

	@Autowired
	private FileContentTypeDao fileContentTypeDao;

	@Autowired
	private FileTipoDao fileTipoDao;

	@Autowired
	private GateFireService firmeService;

	@Value("${filesystem.basedir.file}")
	private String baseFilePath;

	public ModelFileGruppo getFileGruppoFromFileGruppoId(Integer moduloVoceId, Integer fileGruppoId,
			Integer moduloCompilatoId) {
		try {
			var res = fileRepository.getFileGruppoFromFileGruppoId(fileGruppoId);

			List<ModelFileTipo> fileTipi = fileRepository.getFileTipoFromModuloVoceId(moduloVoceId, moduloCompilatoId);
			res.setFileTipi(fileTipi);
			for (ModelFileTipo fileTipo : fileTipi) {
				fileTipo.setFileContentTypes(fileContentTypeDao.getListIdByFileTipoId(fileTipo.getFileTipoId()));
			}
			return res;
		} catch (Exception e) {
			logError(null, "getFileGruppoFromFileGruppoId", e.getMessage(), e);
			throw e;
		}
	}

	@Transactional
	public File postFile(Integer sId, ModelFilePost body, String shibIdentitaCodiceFiscale) {
		final var methodName = "postFile";
		try {
			// fai verifiche del caso

			var fileTipo = getFileTipoFromFileTipoId(sId, body.getFileTipoId());
			var fileContentType = getFileContentType(body);

			var fileId = fileDao.getSequence();

			var decodedBytes = Base64.getDecoder().decode(body.getBase64File());

			FileTypeEnum fileType = FileTypeEnum.valueOf(fileContentType.getFileContentTypeCod());
			// se specificato verifichiamo la versione del file
			if (Boolean.FALSE.equals(fileType.isType(decodedBytes))) {
				throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.FILE_FORMATO_SBAGLIATO,
						fileType.getCode());
			}
//				getFileContentType(filePath) //verifica estensione file che sia corretta e che sia verificabile 
			// e che sia valida per il filetipo
			var fileName = fileTipo.getFileTipoCod() + "." + fileId.toString() + "." + fileType.getExtension();
			var filePath = generatePath(sId, fileName,
					body.getSoggettoId() != null
							? new String[] { body.getEnteCod(), body.getStrutturaCod(), fileTipo.getFileTipoCod(),
									body.getSoggettoId().toString() }
							: new String[] { body.getEnteCod(), body.getStrutturaCod(), fileTipo.getFileTipoCod() });

			// controlliamo il content type specifico
			if (!getFileContentType(filePath).equals(fileContentType.getFileContentTypeMime())) {
				throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.FILE_CONTENT_TYPE_NON_VERIFICABILE);
			}

			String cfFirmaVerificata;
			if (Boolean.TRUE.equals(fileTipo.getFileTipoFirmaRichiesta())) {
				cfFirmaVerificata = verificaFirmaFileEdEstraiCfFirmatario(sId, fileName, decodedBytes,
						body.getCfVerificaFirma(), shibIdentitaCodiceFiscale);
				logDebug(sId, methodName, "cf firmatario: " + cfFirmaVerificata);
				// gestiamo solo firmaPresente e in futuro aggiungeremo id_soggetto/CF_SOGGETTO
				// come colonna della firma trovata.
			} else {
				cfFirmaVerificata = null;
			}

			saveFile(sId, decodedBytes, filePath);
			var file = insertFile(sId, body.getFileTipoId(), filePath.getParent().toString(), fileName,
					body.getFileName(), body.getFileContentTypeId(), decodedBytes.length, cfFirmaVerificata, fileId,
					shibIdentitaCodiceFiscale);
			logAuditDb(sId, AuditOperazioneEnum.INSERT, "vigil_t_file", file.getFileId().toString());

			return file;
		} catch (RESTException e) {
			logWarn(sId, methodName, "RESTException :" + e.getMessage(), null);
			throw e;
		} catch (Exception e) {
			logError(sId, methodName, e.getMessage(), e);
			throw e;
		}

	}

	private FileContentType getFileContentType(ModelFilePost body) {
		Optional<FileContentType> fileContentTypeOptional = fileContentTypeDao.getListByFileTipoId(body.getFileTipoId())
				.stream().filter(p -> p.getFileContentTypeId().equals(body.getFileContentTypeId())).findFirst();
		if (!fileContentTypeOptional.isPresent()) {
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.FILE_CONTENT_TYPE_ID_NON_COERENTE);
		}
		return fileContentTypeOptional.get();
	}

	private static String getFileContentType(Path path) {
		String contentType;
		try {
			contentType = Files.probeContentType(path);
		} catch (IOException e) {
			contentType = null;
		}
		if (contentType == null) {
			return ""; // Nessuna estensione trovata
		}
		return contentType;
	}

	protected String verificaFirmaFileEdEstraiCfFirmatario(Integer sId, String fileName, byte[] decodedBytes,
			List<String> cfList, String shibIdentitaCodiceFiscale) {
		RESTErrorUtil.checkNotNull(cfList, ErrorCodeEnum.CF_LIST_OBBLIGATORIA);
		RESTErrorUtil.checkCondition(!cfList.isEmpty(), ErrorCodeEnum.CF_LIST_OBBLIGATORIA);
		var varificaFirmaResponse = callVerificaFirmaGatefire(sId, shibIdentitaCodiceFiscale, decodedBytes, fileName);
		return verificaSeFirmaValida(sId, varificaFirmaResponse, cfList, fileName);
	}

	private String verificaSeFirmaValida(Integer sId, SignVerifyResult res, List<String> cfList, String fileName) {
		final var methodName = "verificaSeFirmaValida";

		if (Objects.nonNull(res.getSigner()) && !res.getSigner().isEmpty()) {
			Signer signer = null;
			for (Signer singleSigner : res.getSigner()) {
				if (Boolean.TRUE.equals(singleSigner.isValid()) && Objects.nonNull(singleSigner.getCertificate())
						&& Objects.nonNull(singleSigner.getCertificate().getSubject())
						&& Objects.nonNull(singleSigner.getCertificate().getSubject().getCodiceFiscale())
						&& cfList.contains(singleSigner.getCertificate().getSubject().getCodiceFiscale())) {
					return singleSigner.getCertificate().getSubject().getCodiceFiscale();
				} else if (Boolean.TRUE.equals(singleSigner.isValid())) {
					// ne prendo una valida
					signer = singleSigner;
				}
			}
			if (signer == null) {
				// proseguo firma valida ma non sappiamo se e' del rappresentante legale
				logError(sId, methodName, String.format(ErrorCodeEnum.FIRMA_NON_VALIDA.getMessage(), fileName), null);
				throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.FIRMA_NON_VALIDA, fileName);
			} else {
				logError(sId, methodName,
						String.format(ErrorCodeEnum.FIRMA_NON_VALIDA_PER_RAPPRESENTATE.getMessage(), fileName), null);
				throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.FIRMA_NON_VALIDA_PER_RAPPRESENTATE,
						fileName);
			}
		} else {
			logError(sId, methodName, String.format(ErrorCodeEnum.FIRMA_NON_TROVATA.getMessage(), fileName), null);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.FIRMA_NON_TROVATA, fileName);
		}
	}

	private SignVerifyResult callVerificaFirmaGatefire(Integer sId, String shibIdentitaCodiceFiscale,
			byte[] decodedBytes, String fileName) {
		var attachment = new Attachment();
		attachment.setFile(decodedBytes);
		attachment.setFileName(fileName);
		var info = new CallInfo();
		info.setApplicationCode(GATEFIRE_APPLICATION_CODE);
		info.setCaCode(GATEFIRE_CA_CODE);
		info.setCodiceFiscale(shibIdentitaCodiceFiscale);
		return firmeService.verificaFirma(sId, attachment, info);
	}

	public File insertFile(Integer sId, Integer fileTipoId, String filePath, String fileName, String fileNameOrig,
			Integer fileContentTypeId, Integer fileSize, String cfFirmaVerificata, Integer fileId,
			String utenteCreazione) {
		try {

			var mc = new File();
			mc.setFileId(fileId);
			mc.setFileTipoId(fileTipoId);
			mc.setFileName(fileName);
			mc.setFileNameOrig(fileNameOrig);
			mc.setFileContentTypeId(fileContentTypeId);
			mc.setFileSize(fileSize);
			mc.setCfFirmaVerificata(cfFirmaVerificata);
			mc.setUtenteCreazione(utenteCreazione);
			mc.setFilePath(filePath);
			fileDao.insert(mc);
			mc.setFileName(fileNameOrig);
			return mc;
		} catch (Exception e) {
			logError(sId, "insertFile", e.getMessage(), e);
			throw e;
		}

	}

	private void saveFile(Integer sId, byte[] data, Path filePath) {
		var methodName = "saveFile";

		// Scrivere i dati nel file
		try {
			filesEncrypt.encrypt(sId, data, filePath);
		} catch (RESTException e) {
			throw e;
		} catch (Exception e) {
			logError(sId, methodName, e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.SALVATAGGIO_FILE_ERRORE);
		}
	}

	private Path generatePath(Integer sId, String fileName, String[] directoryPath) {
		// Creare la directory se non esiste
		var directory = Paths.get(baseFilePath, directoryPath);
		if (!Files.exists(directory)) {
			try {
				Files.createDirectories(directory);
			} catch (IOException e) {
				logError(sId, "generatePath", e.getMessage(), e);
				throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.CREAZIONE_DIR_ERRORE);
			}
		}

		// Costruire il percorso completo del file
		return directory.resolve(fileName);
	}

	protected FileTipo getFileTipoFromFileTipoId(Integer sId, Integer fileTipoId) {
		try {
			return fileTipoDao.get(fileTipoId);
		} catch (EmptyResultDataAccessException e) {
			logError(sId, "getFileTipoFromFileTipoId", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.FILE_TIPO_NON_TROVATO);
		}
	}

	protected File getFileFromFileId(Integer sId, Integer fileId) {
		try {
			return fileDao.get(fileId);
		} catch (EmptyResultDataAccessException e) {
			logError(sId, "getFileFromFileId", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.FILE_NON_TROVATO);
		}
	}

	protected FileContentType getFileContentTypeFromFileContentTypeId(Integer sId, Integer fileContentTypeId) {
		try {
			return fileContentTypeDao.get(fileContentTypeId);
		} catch (EmptyResultDataAccessException e) {
			logError(sId, "getFileContentTypeFromFileContentTypeId", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.FILE_CONTENT_TYPE_NON_TROVATO);
		}
	}

	public FileCustom getFile(Integer sId, @NotNull Integer fileId) {
		var tFile = getFileFromFileId(sId, fileId);
		var fileContentType = getFileContentTypeFromFileContentTypeId(sId, tFile.getFileContentTypeId());
		var path = Paths.get(tFile.getFilePath(), tFile.getFileName());
		try {
			var fileContent = filesEncrypt.decrypt(sId, path);
			return new FileCustom(fileContent, tFile.getFileNameOrig(), fileContentType.getFileContentTypeMime(),
					tFile.getFileSize(), fileContentType.getFileContentTypeExt());
		} catch (RESTException e) {
			throw e;
		} catch (Exception e) {
			logError(sId, "getFile", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.FILE_NON_CARICATO_DA_SYSTEM);
		}
	}

	public FileCustom getFileWCodName(Integer sId, @NotNull Integer fileId) {
		var tFile = getFileFromFileId(sId, fileId);
		var fileContentType = getFileContentTypeFromFileContentTypeId(sId, tFile.getFileContentTypeId());
		var path = Paths.get(tFile.getFilePath(), tFile.getFileName());
		try {
			var fileContent = filesEncrypt.decrypt(sId, path);
			return new FileCustom(fileContent, tFile.getFileName(), fileContentType.getFileContentTypeMime(),
					tFile.getFileSize(), fileContentType.getFileContentTypeExt());
		} catch (RESTException e) {
			throw e;
		} catch (Exception e) {
			logError(sId, "getFile", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.FILE_NON_CARICATO_DA_SYSTEM);
		}
	}

	public List<FileContentType> getFileContentTypeLista() {
		return fileContentTypeDao.getAll();
	}

	public FileContentType getFileContentTypeFromCod(String code) {
		return fileContentTypeDao.getByCode(code);
	}

	public FileTipo getfileTipo(String fileTipoCod, Integer sId) {
		return fileTipoDao.getByCod(fileTipoCod);
	}

}
