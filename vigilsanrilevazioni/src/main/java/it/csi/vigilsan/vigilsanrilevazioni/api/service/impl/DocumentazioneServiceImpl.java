/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanrilevazioni.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.vigilsan.vigilsanrilevazioni.api.generated.dto.ModelChiaveValore;
import it.csi.vigilsan.vigilsanrilevazioni.api.generated.dto.ModelChiaveValoreList;
import it.csi.vigilsan.vigilsanrilevazioni.api.generated.dto.ModelDocumentazione;
import it.csi.vigilsan.vigilsanrilevazioni.api.generated.dto.ModelParametro;
import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.dao.impl.DocumentazioneDao;
import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.model.Documentazione;
import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.model.DocumentoEsteso;
import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.model.DocumentoEstesoList;
import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.model.ModuloConfigRidotto;
import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.repository.impl.DocumentazioneRepository;
import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.repository.impl.ModuloConfigRepository;
import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.repository.impl.RilevazioneDocumentazioneCsvRepository;
import it.csi.vigilsan.vigilsanrilevazioni.util.DocumentazioneParametroEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableWPersistenceApiServiceImpl;
import it.csi.vigilsan.vigilsanutil.generic.util.AuditOperazioneEnum;
import it.csi.vigilsan.vigilsanutil.generic.util.paginazione.Paginazione;
import jakarta.validation.constraints.NotNull;

@Service
public class DocumentazioneServiceImpl extends AuditableWPersistenceApiServiceImpl {

	@Autowired
	private DocumentazioneRepository documentazioneRepository;

	@Autowired
	private DocumentazioneDao documentazioneDao;

	@Autowired
	private ModuloConfigRepository moduloConfigRepository;

	@Autowired
	private RilevazioneDocumentazioneCsvRepository rilevazioneDocumentazioneCsvRepository;

	private Documentazione insertDocumentazione(Integer sId, ModelDocumentazione doc,
			String shibIdentitaCodiceFiscale) {
		try {
			var pk = documentazioneDao.getSequence();

			var mc = new Documentazione();
			mc.setDocumentazioneId(pk);
			mc.setStrutturaId(doc.getStrutturaId());
			mc.setStrCatId(doc.getStrCatId());
			mc.setModuloCompilatoId(doc.getModuloCompilatoId());
			mc.setModuloConfigId(doc.getModuloConfigId());
			mc.setDataoraDocumentazione(doc.getDataoraDocumentazione());
			mc.setDataoraScadenza(doc.getDataoraScadenza());
			mc.setUtenteCreazione(shibIdentitaCodiceFiscale);
			mc.setOccorrenza(doc.getOccorrenza());
			documentazioneDao.insert(mc);

			return mc;
		} catch (Exception e) {
			logError(sId, "insertDocumentazione", e.getMessage(), e);
			throw e;
		}

	}

	@Transactional
	public ModelDocumentazione updateOrInsertDocumentazione(Integer sId, ModelDocumentazione doc,
			String shibIdentitaCodiceFiscale) {

		var method = AuditOperazioneEnum.INSERT;
		var keyLog = "";

		if (doc.getDocumentazioneId() != null) {
			documentazioneDao.deleteOldDocumenti(doc, shibIdentitaCodiceFiscale);
			method = AuditOperazioneEnum.UPDATE;
			keyLog = doc.getDocumentazioneId() + KEY_SEPARATOR;
		}

		var res = insertDocumentazione(sId, doc, shibIdentitaCodiceFiscale);
		logAuditDb(sId, method, "vigil_t_documentazione", keyLog + res.getDocumentazioneId());
		return res;
	}

	public List<DocumentoEsteso> getDocumentazioneDaCompilare(Integer sId, @NotNull Integer strutturaId,
			String moduloConfigCod) {
		try {
			return documentazioneRepository.getDocumentazioneDaCompilareByStrutturaId(strutturaId, moduloConfigCod);
		} catch (Exception e) {
			logError(sId, "getDocumentazioneDaCompilare", e.getMessage(), e);
			throw e;
		}
	}

	public List<DocumentoEstesoList> getDocumentazioneCompilataList(Integer sId, @NotNull Integer strutturaId,
			@NotNull String moduloConfigCod, Paginazione paginazione) {
		try {
			var res = documentazioneRepository.getDocumentazioneCompilataByStrutturaIdAndModuloCod(strutturaId,
					moduloConfigCod, paginazione);
			return res;
		} catch (Exception e) {
			logError(sId, "getDocumentazioneCompilataList", e.getMessage(), e);
			throw e;
		}
	}

	public List<ModuloConfigRidotto> getDocumentazioneModuli(Integer sId, Integer strutturaId,
			String moduloConfigGruppoCod) {
		try {
			return moduloConfigRepository.getDocumentazioneModuliDisponibili(strutturaId, moduloConfigGruppoCod);
		} catch (Exception e) {
			logError(sId, "getDocumentazioneModuli", e.getMessage(), e);
			throw e;
		}
	}

	public Object getDocumentazioneParametro(Integer sId, Integer strutturaId, String parametroCod) {
		try {
			return DocumentazioneParametroEnum.valueOf(parametroCod).getResponse(documentazioneDao, strutturaId);
		} catch (Exception e) {
			logError(sId, "getDocumentazioneParametro", "parametroCod: " + parametroCod + " " + e.getMessage(), e);
			throw e;
		}
	}

	public Object getDocumentazioneParametroLista(Integer sId, Integer strutturaId, @NotNull String parametroCod) {
		try {
			if ("RES_INFO".equals(parametroCod)) {
				List<ModelChiaveValore> res = new ArrayList<>();

				setCampiParametro(res, strutturaId, "resdoc_contratto", "resdoc_contr_emissione",
						"resdoc_contr_emittente");
				setCampiParametro(res, strutturaId, "resinfo", "documentazione_id");
				setCampiParametro(res, strutturaId, "resdoc_contratto_lavoro", "occorrenza");

				return res;
			} else if ("EMAIL".equals(parametroCod)) {
				List<ModelChiaveValore> res = new ArrayList<>();

				setCampiParametro(res, strutturaId, "resinfo", "resinfo_dati_email1");
				setCampiParametro(res, strutturaId, "semiresinfo", "semiresinfo_dati_email1");
				return res;
			} else {
				return null;
			}
		} catch (Exception e) {
			logError(sId, "getDocumentazioneParametro", "parametroCod: " + parametroCod + " " + e.getMessage(), e);
			throw e;
		}
	}

	public ModelChiaveValoreList getDocumentazioneParametroLista(Integer sId, Integer strutturaId,
			ModelChiaveValoreList body) {
		List<ModelChiaveValore> res = new ArrayList<>();
		Map<String, List<ModelChiaveValore>> groupedByPrefix = body.getChiaveValore().stream()
				.collect(Collectors.groupingBy(obj -> {
					String[] parts = obj.getChiave().split("\\.");
					return parts[1];
				}));

		groupedByPrefix.forEach((key, value) -> {
			String[] result = value.stream().map(ModelChiaveValore::getChiave)
					.map(s -> s.split("\\.")[2]).toArray(String[]::new);

			setCampiParametro("doc.", res, strutturaId, key, result);
		});

		body.setChiaveValore(res);
		return body;
	}

	private void setCampiParametro(List<ModelChiaveValore> res, Integer strutturaId, String document, String... campi) {
		this.setCampiParametro("", res, strutturaId, document, campi);
	}

	private void setCampiParametro(String prefix, List<ModelChiaveValore> res, Integer strutturaId, String document,
			String... campi) {
		Map<String, List<String>> resultMap = new HashMap<>();
		for (String campo : campi) {
			resultMap.put(campo, new ArrayList<>());
		}
		rilevazioneDocumentazioneCsvRepository.selectModuliDocumentazione(document, strutturaId, null, null, null, null,
				resultMap);

		for (Map.Entry<String, List<String>> entry : resultMap.entrySet()) {
			ModelChiaveValore parametro = new ModelChiaveValore();
			parametro.setChiave(prefix + document + "." + entry.getKey());
			parametro.setValore(String.join(",", entry.getValue()));
			res.add(parametro);
		}
	}
}
