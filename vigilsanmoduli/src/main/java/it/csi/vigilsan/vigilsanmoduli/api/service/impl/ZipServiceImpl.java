/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.api.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.vigilsan.vigilsanmoduli.api.generated.dto.ModelFileTipo;
import it.csi.vigilsan.vigilsanmoduli.api.generated.dto.ModelModulo;
import it.csi.vigilsan.vigilsanmoduli.api.generated.dto.ModelVoce;
import it.csi.vigilsan.vigilsanmoduli.api.generated.dto.ModelZip;
import it.csi.vigilsan.vigilsanmoduli.api.generated.dto.ModelZipLista;
import it.csi.vigilsan.vigilsanmoduli.api.service.ModuloService;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableWPersistenceApiServiceImpl;
import it.csi.vigilsan.vigilsanutil.generic.util.pdf.dto.FileCustom;

@Service
public class ZipServiceImpl extends AuditableWPersistenceApiServiceImpl {

	@Autowired
	FileServiceImpl fileService;

	@Autowired
	ModuloService moduloService;

	public ByteArrayOutputStream getZip(Integer sId, ModelZipLista body, String shibIdentitaCodiceFiscale)
			throws FileNotFoundException, IOException {

		// Crea uno stream temporaneo per lo ZIP
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try (ZipOutputStream zipOut = new ZipOutputStream(byteArrayOutputStream)) {
			for (ModelZip fileOrigin : body.getFiles()) {
				FileCustom fileCustom;
				if (fileOrigin.getFileId() != null) {
					// prendi file ed aggiungilo a fileToZip
					fileCustom = fileService.getFile(sId, fileOrigin.getFileId());
					fileCustom.setFileName(generaFileName(fileOrigin, fileCustom));
					aggiungiFileAZip(zipOut, fileCustom);
				} else if (fileOrigin.getModuloCompilatoId() != null) {
					var modulo = moduloService.getModulo(sId, null, fileOrigin.getModuloCompilatoId(), Boolean.FALSE);

					aggiungiFilesInZipFromModulo(modulo, zipOut, fileOrigin, sId);
					fileCustom = moduloService.getPdf(sId, modulo);

					fileCustom.setFileName(generaFileName(fileOrigin, fileCustom));
					aggiungiFileAZip(zipOut, fileCustom);
				} else {
					throw RESTErrorUtil.generateGenericRestException(null);
				}

			}
		}

		return byteArrayOutputStream;
	}

	private String generaFileName(ModelZip fileOrigin, FileCustom fileCustom) {
		return (fileOrigin.getFileName() !=null?(fileOrigin.getFileName() + "_"):"") + fileCustom.getFileName();
	}

	private void aggiungiFilesInZipFromModulo(ModelModulo modulo, ZipOutputStream zipOut, ModelZip fileOrigin, Integer sId)
			throws IOException {
		for (ModelModulo sezione : modulo.getSezioni()) {
			aggiungiFilesInZipFromModulo(sezione, zipOut, fileOrigin, sId);
		}
		for (ModelVoce sezione : modulo.getVoci()) {
			aggiungiFilesInZipFromVoce(sezione,zipOut, fileOrigin, sId);
		}

	}

	private void aggiungiFilesInZipFromVoce(ModelVoce voceCorrente, ZipOutputStream zipOut, ModelZip fileOrigin,
			Integer sId) throws IOException {
		for (ModelVoce voce : voceCorrente.getVoci()) {
			aggiungiFilesInZipFromVoce(voce,zipOut, fileOrigin, sId);
		}
		
		if (voceCorrente.getFileGruppoId() != null && voceCorrente.getFileGruppo() != null) {
			for (ModelFileTipo file : voceCorrente.getFileGruppo().getFileTipi()) {
				if (file != null && file.getFileId() != null) {

					FileCustom fileCustom = fileService.getFileWCodName(sId, file.getFileId());
					fileCustom.setFileName(generaFileName(fileOrigin, fileCustom));
					file.setFileName(fileCustom.getFileName());
					aggiungiFileAZip(zipOut, fileCustom);
				}
			}
		}
		}

	private void aggiungiFileAZip(ZipOutputStream zipOut, FileCustom fileCustom) throws IOException {
		ZipEntry zipEntry = new ZipEntry(fileCustom.getFileName());
		zipOut.putNextEntry(zipEntry);

		// Scrivi i byte del file nello ZIP
		zipOut.write(fileCustom.getFile(), 0, fileCustom.getContentLength());
		zipOut.closeEntry();
	}

}
