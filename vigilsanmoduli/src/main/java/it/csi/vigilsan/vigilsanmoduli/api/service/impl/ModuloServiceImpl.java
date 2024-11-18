/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.api.service.impl;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.vigilsan.vigilsanmoduli.api.generated.dto.ModelFileTipo;
import it.csi.vigilsan.vigilsanmoduli.api.generated.dto.ModelModulo;
import it.csi.vigilsan.vigilsanmoduli.api.generated.dto.ModelModuloEsteso;
import it.csi.vigilsan.vigilsanmoduli.api.generated.dto.ModelModuloPost;
import it.csi.vigilsan.vigilsanmoduli.api.generated.dto.ModelVoce;
import it.csi.vigilsan.vigilsanmoduli.api.generated.dto.ModelVoceListaValore;
import it.csi.vigilsan.vigilsanmoduli.api.persistence.model.ModuloCompilato;
import it.csi.vigilsan.vigilsanmoduli.api.persistence.model.ModuloCompilatoDettaglio;
import it.csi.vigilsan.vigilsanmoduli.api.persistence.repository.impl.ModuloRepository;
import it.csi.vigilsan.vigilsanmoduli.api.service.ModuloService;
import it.csi.vigilsan.vigilsanmoduli.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableWPersistenceApiServiceImpl;
import it.csi.vigilsan.vigilsanutil.generic.util.AuditOperazioneEnum;
import it.csi.vigilsan.vigilsanutil.generic.util.pdf.GeneratoreFileBuilderPdf;
import it.csi.vigilsan.vigilsanutil.generic.util.pdf.dto.BloccoDiTesto;
import it.csi.vigilsan.vigilsanutil.generic.util.pdf.dto.CheckboxCustom;
import it.csi.vigilsan.vigilsanutil.generic.util.pdf.dto.FileCustom;
import it.csi.vigilsan.vigilsanutil.generic.util.pdf.dto.Testo;
import it.csi.vigilsan.vigilsanutil.generic.util.pdf.mapper.PdfCheckBoxCustomMapper;
import it.csi.vigilsan.vigilsanutil.generic.util.pdf.mapper.PdfMapperBloccoDiTesto;
import it.csi.vigilsan.vigilsanutil.generic.util.pdf.mapper.PdfMapperBloccoDiTestoLista;

@Service
public class ModuloServiceImpl extends AuditableWPersistenceApiServiceImpl implements ModuloService {
	public static final Boolean MODULO_ESTESO = Boolean.FALSE;

	@Autowired
	private ModuloRepository moduloRepository;

	@Autowired
	private FileServiceImpl fileServiceImpl;

	@Autowired
	private ModuloCompilatoServiceImpl moduloCompilatoService;

	@Override
	public Integer getModuloIdFromModuloCompilatoId(Integer sId, Integer moduloCompilatoId) {
		try {
			var modulo = moduloCompilatoService.getModuloFromModuloCompilatoId(sId, moduloCompilatoId);
			return modulo.getModuloId();
		} catch (Exception e) {
			logError(sId, "getModuloIdFromModuloCompilatoId", e.getMessage(), e);
			throw e;
		}

	}

	@Override
	@Transactional
	public ModelModuloPost updateOrInsertModuloCompilato(ModelModuloPost body, String shibIdentitaCodiceFiscale,
			Integer sId) {
		AuditOperazioneEnum method = AuditOperazioneEnum.INSERT;
		var keyLog = "";
		if (body.getModuloCompilatoId() != null) {
			method = AuditOperazioneEnum.UPDATE;
			keyLog = body.getModuloCompilatoId() + KEY_SEPARATOR;
			var modToBeDeleted = moduloCompilatoService.getModuloFromModuloCompilatoId(sId,
					body.getModuloCompilatoId());
			if (!modToBeDeleted.getModuloId().equals(body.getModulo().getModuloId())) {
				// si sta cercando di modificare un modulo diverso da quello che inseriremo
				throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.MODULO_DA_MODIFICARE_CAMBIATO);
			}
			moduloCompilatoService.deleteModuloCompilato(sId, body.getModuloCompilatoId(), shibIdentitaCodiceFiscale);
		}

		body.setModuloCompilatoId(saveModulo(sId, body, shibIdentitaCodiceFiscale));
		logAuditDb(sId, method, "vigil_t_modulo_compilato", keyLog + body.getModuloCompilatoId());
		return body;
	}

	public Integer saveModulo(Integer sId, ModelModuloPost modulo, String shibIdentitaCodiceFiscale) {
		try {

			Integer moduloCompilatoId = moduloCompilatoService.insertModuloCompilato(sId,
					modulo.getModulo().getModuloId(), modulo.getNote(), shibIdentitaCodiceFiscale);

			var mcDet = new ArrayList<ModuloCompilatoDettaglio>();
			saveModuloRecursion(modulo.getModulo(), moduloCompilatoId, mcDet, shibIdentitaCodiceFiscale);

			moduloCompilatoService.saveModuloCompilatoDettaglio(sId, mcDet);

			return moduloCompilatoId;
		} catch (Exception e) {
			logError(sId, "saveModulo", e.getMessage(), e);
			throw e;
		}

	}

	private void saveModuloRecursion(ModelModulo modulo, Integer moduloCompilatoId,
			ArrayList<ModuloCompilatoDettaglio> mcDet, String shibIdentitaCodiceFiscale) {
		for (ModelVoce voce : modulo.getVoci()) {
			saveVoceRecursion(voce, moduloCompilatoId, mcDet, shibIdentitaCodiceFiscale);
		}
		for (ModelModulo moduloFiglio : modulo.getSezioni()) {
			this.saveModuloRecursion(moduloFiglio, moduloCompilatoId, mcDet, shibIdentitaCodiceFiscale);
		}
	}

	private void saveVoceRecursion(ModelVoce voce, Integer moduloCompilatoId, ArrayList<ModuloCompilatoDettaglio> mcDet,
			String shibIdentitaCodiceFiscale) {
		if (voce.getValore() != null) {

			var voceDet = new ModuloCompilatoDettaglio();
			voceDet.setModuloCompilatoId(moduloCompilatoId);
			voceDet.setValore(voce.getValore());
			voceDet.setNote(voce.getNote());
			voceDet.setModuloVoceId(voce.getModuloVoceId());
			if (Objects.nonNull(voce.isFlgCheck())) {
				voceDet.setFlgCheck(voce.isFlgCheck());
			} else {
				voceDet.setFlgCheck(Boolean.FALSE);
			}
			voceDet.setUtenteCreazione(shibIdentitaCodiceFiscale);
			mcDet.add(voceDet);

		} else if (voce.getLista() != null) {
			for (ModelVoceListaValore valore : voce.getLista().getValori()) {

				if (Boolean.TRUE.equals(valore.isValore())) {
					var voceDet = new ModuloCompilatoDettaglio();
					voceDet.setModuloCompilatoId(moduloCompilatoId);
					voceDet.setModuloVoceId(voce.getModuloVoceId());
					voceDet.setModuloListaValoreId(valore.getModuloListaValoreId());
					voceDet.setUtenteCreazione(shibIdentitaCodiceFiscale);
					voceDet.setNote(voce.getNote());
					if (Objects.nonNull(voce.isFlgCheck())) {
						voceDet.setFlgCheck(voce.isFlgCheck());
					} else {
						voceDet.setFlgCheck(Boolean.FALSE);
					}
					mcDet.add(voceDet);
				}

			}

		} else if (voce.getFileGruppo() != null) {
			for (ModelFileTipo file : voce.getFileGruppo().getFileTipi()) {

				if (file.getFileId() != null) {
					var voceDet = new ModuloCompilatoDettaglio();
					voceDet.setModuloCompilatoId(moduloCompilatoId);
					voceDet.setModuloVoceId(voce.getModuloVoceId());
					voceDet.setFileId(file.getFileId());
					voceDet.setUtenteCreazione(shibIdentitaCodiceFiscale);
					voceDet.setNote(file.getNote());
					if (Objects.nonNull(voce.isFlgCheck())) {
						voceDet.setFlgCheck(voce.isFlgCheck());
					} else {
						voceDet.setFlgCheck(Boolean.FALSE);
					}
					mcDet.add(voceDet);
				}

			}

		}

		for (ModelVoce voceFiglia : voce.getVoci()) {
			saveVoceRecursion(voceFiglia, moduloCompilatoId, mcDet, shibIdentitaCodiceFiscale);
		}
	}

	@Override
	public ModelModuloEsteso getModulo(Integer sId, Integer moduloId, Integer moduloCompilatoId, Boolean ridotto) {
		if (Objects.isNull(ridotto)) {
			ridotto = MODULO_ESTESO;
		}
		ModelModuloEsteso res;
		if (moduloCompilatoId != null) {
			RESTErrorUtil.checkNotNull(moduloCompilatoId, ErrorCodeEnum.MODULO_COMPILATO_ID_OBB);
			ModuloCompilato moduloCompilato = moduloCompilatoService.getModuloFromModuloCompilatoId(sId,
					moduloCompilatoId);
			res = getModulo(sId, moduloCompilato.getModuloId(), moduloCompilatoId, ridotto, ModelModuloEsteso.class);
			res.setNote(moduloCompilato.getNote());
			
			res.setListaPlaceOlder(moduloRepository.selectPlaceHolderModulo(moduloCompilato.getModuloId()));
		} else {
			RESTErrorUtil.checkNotNull(moduloId, ErrorCodeEnum.MODULO_ID_OBB);
			res = getModulo(sId, moduloId, moduloCompilatoId, ridotto, ModelModuloEsteso.class);
			res.setListaPlaceOlder(moduloRepository.selectPlaceHolderModulo(moduloId));
		}

		return res;
	}

	private <T extends ModelModulo> T getModulo(Integer sId, Integer idModulo, Integer moduloCompilatoId,
			Boolean isRidotto, Class<T> clazz) {
		try {
			T modulo = getModuloBase(sId, idModulo, clazz);
			setVociPerSezione(sId, modulo, moduloCompilatoId, isRidotto);
			setRegolePerSezione(sId, modulo, isRidotto);
			Map<Integer, List<ModelModulo>> map = new HashMap<>();

			List<ModelModulo> sezioni = moduloRepository.getSezioniModulo(idModulo);
			map.put(idModulo, modulo.getSezioni());

			for (ModelModulo sezione : sezioni) {

				// costruzione lista delle voci
				setVociPerSezione(sId, sezione, moduloCompilatoId, isRidotto);
				setRegolePerSezione(sId, sezione, isRidotto);

				if (Objects.nonNull(map.get(sezione.getModuloIdPadre()))) {
					map.get(sezione.getModuloIdPadre()).add(sezione);
				} else {
					var nuovaLista = new ArrayList<ModelModulo>();
					nuovaLista.add(sezione);
					map.put(sezione.getModuloIdPadre(), nuovaLista);
				}
			}
			// associazione tra sezioni e moduli
			for (Map.Entry<Integer, List<ModelModulo>> entry : map.entrySet()) {
				for (ModelModulo sezione : entry.getValue()) {
					if (map.get(sezione.getModuloId()) != null && !map.get(sezione.getModuloId()).isEmpty()) {
						sezione.setSezioni(map.get(sezione.getModuloId()));
					}
				}
			}
			return modulo;
		} catch (Exception e) {
			logError(sId, "getFileGruppoFromFileGruppoId", e.getMessage(), e);
			throw e;
		}

	}

	private <T extends ModelModulo> void setRegolePerSezione(Integer sId, T modulo, Boolean isRidotto) {
		modulo.setRegole(moduloRepository.getModuloRegolaByModuloId(modulo.getModuloId()));
	}

	private void setVociPerSezione(Integer sId, ModelModulo sezione, Integer moduloCompilatoId, Boolean isRidotto) {
		try {
			List<ModelVoce> voci = moduloRepository.getModuloVoceByModuloId(sezione.getModuloId(), moduloCompilatoId,
					isRidotto);
			Map<Integer, ModelVoce> vociMap = new HashMap<>();
			for (ModelVoce voce : voci) {

				if (voce.getModuloListaId() != null) {
					voce.setLista(moduloRepository.getModuloListaFromModuloListaId(voce.getModuloListaId()));
					voce.getLista().setValori(moduloRepository
							.getModuloListaValoreFromModuloListaId(voce.getModuloVoceId(), moduloCompilatoId));
					if (moduloCompilatoId != null) {
						List<String> moduloCompilatoDett = moduloRepository
								.getModuloCompilatoVoceNota(moduloCompilatoId, voce.getModuloVoceId());
						voce.setNote(moduloCompilatoDett.isEmpty() ? null : moduloCompilatoDett.get(0));
					}
				}
				voce.setRegole(moduloRepository.getModuloVoceRegolaByModuloVoceRegolaId(voce.getModuloVoceId()));
				if (voce.getFileGruppoId() != null) {
					voce.setFileGruppo(fileServiceImpl.getFileGruppoFromFileGruppoId(voce.getModuloVoceId(),
							voce.getFileGruppoId(), moduloCompilatoId));
				}

				Integer moduloVoceIdPadre = voce.getModuloVoceIdPadre();
				if (moduloVoceIdPadre == null) {
					sezione.getVoci().add(voce);
					vociMap.put(voce.getModuloVoceId(), voce);

				} else {
					vociMap.put(voce.getModuloVoceId(), voce);
					vociMap.get(moduloVoceIdPadre).getVoci().add(voce);
				}
			}
		} catch (Exception e) {
			logError(sId, "setVociPerSezione", e.getMessage(), e);
			throw e;
		}
	}

	private <T extends ModelModulo> T getModuloBase(Integer sId, Integer idModulo, Class<T> clazz) {
		try {
			return moduloRepository.getModulo(idModulo, clazz);
		} catch (EmptyResultDataAccessException e) {
			logError(sId, "getModuloBase", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.MODULO_NON_TROVATO);
		}
	}

	@Override
	public FileCustom getPdf(Integer sId, ModelModulo modulo) {

		try {
			var fileBuilder = GeneratoreFileBuilderPdf.builder(modulo.getModuloCod()).setDistanzaDivisorio(2L);
			Integer profondita = 0;
			aggiungiParagrafoModulo(modulo, fileBuilder, profondita, new PdfMapperBloccoDiTesto());

			return fileBuilder.aggiungiPiePagina().generaFilePdf();
		} catch (IOException e1) {
			// TODO correggi errore
			e1.printStackTrace();
			throw new RuntimeException();
		}
	}

	private static final float TITOLO = 18F;
	private static final float TITOLO_DIFFERENZA_SOTTOMODULI = 2F;
	private static final float TITOLO_SEPARATORE = 14F;
	private static final float TITOLO_SEPARATORE_SOTTO = 8F;

	private static final float VOCE = 10F;

	private void aggiungiParagrafoModulo(ModelModulo modulo, GeneratoreFileBuilderPdf fileBuilder, Integer profondita,
			PdfMapperBloccoDiTesto mapper) throws IOException {
		if (profondita != 0)
			generaTestoPerTitolo(fileBuilder, mapper, "",
					TITOLO_SEPARATORE - TITOLO_DIFFERENZA_SOTTOMODULI * profondita);
		generaTestoPerTitolo(fileBuilder, mapper, modulo.getModuloDesc(),
				TITOLO - TITOLO_DIFFERENZA_SOTTOMODULI * profondita);
		generaTestoPerTitolo(fileBuilder, mapper, "",
				TITOLO_SEPARATORE_SOTTO - TITOLO_DIFFERENZA_SOTTOMODULI * profondita);

		for (ModelVoce voce : modulo.getVoci()) {
			aggiungiParagrafoVoceModulo(voce, fileBuilder, profondita, mapper);

		}

		for (ModelModulo sezione : modulo.getSezioni()) {
			aggiungiParagrafoModulo(sezione, fileBuilder, profondita + 1, mapper);
		}

	}

	private void generaTestoPerTitolo(GeneratoreFileBuilderPdf fileBuilder, PdfMapperBloccoDiTesto mapper, String test,
			float fontSizeSeparatoreSotto) throws IOException {
		if (fontSizeSeparatoreSotto >= 0) {
			var spazio = new ArrayList<Testo>();
			spazio.add(new Testo(test, Standard14Fonts.FontName.HELVETICA_BOLD, fontSizeSeparatoreSotto, Color.BLUE));
			fileBuilder.aggiungiOggetto(new BloccoDiTesto(spazio), mapper);
		}
	}

	private void aggiungiParagrafoVoceModulo(ModelVoce voce, GeneratoreFileBuilderPdf fileBuilder, Integer profondita,
			PdfMapperBloccoDiTesto mapper) throws IOException {
		if (Objects.nonNull(voce.getFileGruppo())) {
			Boolean firstValueInserted = Boolean.FALSE;
			BloccoDiTesto testoSx = null;
			var voceBlocco = new ArrayList<Testo>();
			for (ModelFileTipo file : voce.getFileGruppo().getFileTipi()) {

				if (Boolean.FALSE.equals(firstValueInserted)) {
					testoSx = getBloccoTestoSingolo(file.getFileTipoDesc(), Standard14Fonts.FontName.HELVETICA_BOLD,
							VOCE, Color.black);
				}

				if (Objects.nonNull(file.getFileName())) {
					voceBlocco.add(new Testo(file.getFileName(), Standard14Fonts.FontName.HELVETICA_BOLD, VOCE,
							new Color(0, 153, 51)));
				} else {
					voceBlocco.add(new Testo("________________________________________",
							Standard14Fonts.FontName.HELVETICA, VOCE, Color.black));

				}

				firstValueInserted = Boolean.TRUE;
			}
			BloccoDiTesto testoDx = new BloccoDiTesto(voceBlocco);
			fileBuilder.aggiungiBlocchiDivisiDaDivisorioSpecifico(testoSx, mapper, testoDx,
					new PdfMapperBloccoDiTestoLista());

		} else if (Objects.nonNull(voce.getLista()) && !voce.getLista().getValori().isEmpty()) {
			Boolean firstValueInserted = Boolean.FALSE;
			BloccoDiTesto testoSx = null;
			var voceBlocco = new ArrayList<Testo>();
			for (ModelVoceListaValore valore : voce.getLista().getValori()) {
//				if (Boolean.TRUE.equals(valore.isValore())) {
//					if (Boolean.FALSE.equals(firstValueInserted)) {
//						fileBuilder.aggiungiBlocchiDivisiDaDivisorioSpecifico(
//								getBloccoTestoSingolo(voce.getModuloVoceDesc(), Standard14Fonts.FontName.HELVETICA_BOLD,
//										VOCE, Color.black),
//								mapper, getBloccoTestoSingolo(valore.getModuloListaValoreDesc(),
//										Standard14Fonts.FontName.HELVETICA, VOCE, Color.black),
//								mapper);
//						firstValueInserted = Boolean.TRUE;
//					} else {
//						fileBuilder.aggiungiBlocchiDivisiDaDivisorioSpecifico(null, mapper,
//								getBloccoTestoSingolo(valore.getModuloListaValoreDesc(),
//										Standard14Fonts.FontName.HELVETICA, VOCE, Color.black),
//								mapper);
//
//					}
//
//				}

				if (Boolean.FALSE.equals(firstValueInserted)) {
					testoSx = getBloccoTestoSingolo(voce.getModuloVoceDesc(), Standard14Fonts.FontName.HELVETICA_BOLD,
							VOCE, Color.black);
				}

				if (Boolean.TRUE.equals(valore.isValore())) {
					voceBlocco.add(new Testo(valore.getModuloListaValoreDesc(), Standard14Fonts.FontName.HELVETICA_BOLD,
							VOCE, new Color(0, 153, 51)));
				} else {

					voceBlocco.add(new Testo(valore.getModuloListaValoreDesc(), Standard14Fonts.FontName.HELVETICA,
							VOCE, Color.black));
				}
				firstValueInserted = Boolean.TRUE;
			}
			BloccoDiTesto testoDx = new BloccoDiTesto(voceBlocco);
			fileBuilder.aggiungiBlocchiDivisiDaDivisorioSpecifico(testoSx, mapper, testoDx,
					new PdfMapperBloccoDiTestoLista());

		} else if (Objects.nonNull(voce.getModuloVoceTipoDati()) && voce.getModuloVoceTipoDati().equals("bool")) {
			fileBuilder.aggiungiBlocchiDivisiDaDivisorioSpecifico(
					getBloccoTestoSingolo(voce.getModuloVoceDesc(), Standard14Fonts.FontName.HELVETICA_BOLD, VOCE,
							Color.black),
					mapper, new CheckboxCustom(voce.getModuloVoceCod(), "true".equalsIgnoreCase(voce.getValore()),
							Standard14Fonts.FontName.HELVETICA, VOCE, Color.black),
					new PdfCheckBoxCustomMapper());
		} else if (Objects.nonNull(voce.getModuloVoceTipoDati()) && voce.getModuloVoceTipoDati().equals("date")) {
			fileBuilder.aggiungiBlocchiDivisiDaDivisorioSpecifico(
					getBloccoTestoSingolo(voce.getModuloVoceDesc(), Standard14Fonts.FontName.HELVETICA_BOLD, VOCE,
							Color.black),
					mapper, getBloccoTestoSingolo(voce.getValore() != null ? voce.getValore() : "__-__-____",
							Standard14Fonts.FontName.HELVETICA, VOCE, Color.black),
					mapper);
		} else if (Objects.nonNull(voce.getModuloVoceTipoDati()) && voce.getModuloVoceTipoDati().equals("datetime")) {
			fileBuilder.aggiungiBlocchiDivisiDaDivisorioSpecifico(
					getBloccoTestoSingolo(voce.getModuloVoceDesc(), Standard14Fonts.FontName.HELVETICA_BOLD, VOCE,
							Color.black),
					mapper, getBloccoTestoSingolo(voce.getValore() != null ? voce.getValore() : "__:__ __-__-____",
							Standard14Fonts.FontName.HELVETICA, VOCE, Color.black),
					mapper);
		} else if (Objects.nonNull(voce.getModuloVoceTipoDati()) && voce.getModuloVoceTipoDati().equals("time")) {
			fileBuilder.aggiungiBlocchiDivisiDaDivisorioSpecifico(
					getBloccoTestoSingolo(voce.getModuloVoceDesc(), Standard14Fonts.FontName.HELVETICA_BOLD, VOCE,
							Color.black),
					mapper, getBloccoTestoSingolo(voce.getValore() != null ? voce.getValore() : "__:__",
							Standard14Fonts.FontName.HELVETICA, VOCE, Color.black),
					mapper);
		} else if (Objects.nonNull(voce.getModuloVoceTipoDati()) && voce.getModuloVoceTipoDati().equals("email")) {
			fileBuilder.aggiungiBlocchiDivisiDaDivisorioSpecifico(
					getBloccoTestoSingolo(
							voce.getModuloVoceDesc(), Standard14Fonts.FontName.HELVETICA_BOLD, VOCE, Color.black),
					mapper,
					getBloccoTestoSingolo(
							voce.getValore() != null ? voce.getValore() : "__________________________@____________",
							Standard14Fonts.FontName.HELVETICA, VOCE, Color.black),
					mapper);
		} else if (Objects.nonNull(voce.getModuloVoceTipoDati())) {
			fileBuilder.aggiungiBlocchiDivisiDaDivisorioSpecifico(
					getBloccoTestoSingolo(
							voce.getModuloVoceDesc(), Standard14Fonts.FontName.HELVETICA_BOLD, VOCE, Color.black),
					mapper,
					getBloccoTestoSingolo(
							voce.getValore() != null ? voce.getValore() : "________________________________________",
							Standard14Fonts.FontName.HELVETICA, VOCE, Color.black),
					mapper);
		}

		for (ModelVoce sottoVoce : voce.getVoci()) {
			aggiungiParagrafoVoceModulo(sottoVoce, fileBuilder, profondita, mapper);

		}

	}

	private BloccoDiTesto getBloccoTestoSingolo(String testo, FontName font, float fontSize, Color color)
			throws IOException {
		var voceBlocco = new ArrayList<Testo>();
		voceBlocco.add(new Testo(testo, font, fontSize, color));
		return new BloccoDiTesto(voceBlocco);
	}

}
