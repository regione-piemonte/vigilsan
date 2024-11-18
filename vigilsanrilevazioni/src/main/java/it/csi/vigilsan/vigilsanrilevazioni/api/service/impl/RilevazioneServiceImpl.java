/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanrilevazioni.api.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.vigilsan.vigilsanrilevazioni.api.generated.dto.ModelRilevazione;
import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.dao.impl.RilevazioneDao;
import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.model.ModuloConfigRidotto;
import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.model.Rilevazione;
import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.model.RilevazioneEstesa;
import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.repository.impl.ModuloConfigRepository;
import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.repository.impl.RilevazioneRepository;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableWPersistenceApiServiceImpl;
import it.csi.vigilsan.vigilsanutil.generic.util.AuditOperazioneEnum;
import it.csi.vigilsan.vigilsanutil.generic.util.paginazione.Paginazione;
import jakarta.validation.constraints.NotNull;

@Service
public class RilevazioneServiceImpl extends AuditableWPersistenceApiServiceImpl {

	@Autowired
	private RilevazioneRepository rilevazioneRepository;

	@Autowired
	private RilevazioneDao rilevazioneDao;
	
	@Autowired
	private ModuloConfigRepository moduloConfigRepository;

	public List<RilevazioneEstesa> getRilevazioneDaCompilareList(Integer sId, @NotNull Integer strutturaId, @NotNull String moduloConfigCod) {
		try {
			return rilevazioneRepository.getRilevazioneDaCompilareByStrutturaId(strutturaId, moduloConfigCod);
		} catch (Exception e) {
			logError(sId, "getRilevazioneDaCompilareList", e.getMessage(), e);
			throw e;
		}
	}


	public List<RilevazioneEstesa> getRilevazioneCompilataList(Integer sId, @NotNull Integer strutturaId, @NotNull String moduloConfigCod,
			Paginazione paginazione) {
		try {
			return rilevazioneRepository.getRilevazioneCompilataByStrutturaIdAndModuloCod(strutturaId,moduloConfigCod,paginazione);
		} catch (Exception e) {
			logError(sId, "getRilevazioneCompilataList", e.getMessage(), e);
			throw e;
		}
	}

	@Transactional
	public ModelRilevazione updateOrInsertRilevazione(Integer sId, ModelRilevazione body,
			String shibIdentitaCodiceFiscale) {

		var method = AuditOperazioneEnum.INSERT;
		var keyLog = "";
		if (body.getRilevazioneId() != null) {
			var toBeDeleted = new Rilevazione();
			rilevazioneDao.deleteOldRilevazioni(body, shibIdentitaCodiceFiscale);
			method = AuditOperazioneEnum.UPDATE;
			keyLog = body.getRilevazioneId() + KEY_SEPARATOR;
		}

		var res = insertRilevazione(sId, body, shibIdentitaCodiceFiscale);
		logAuditDb(sId, method, "vigil_t_rilevazione", keyLog + res.getRilevazioneId());
		return res;
	}

	private Rilevazione insertRilevazione(Integer sId, ModelRilevazione body, String utenteCreazione) {
		try {
			var pk = rilevazioneDao.getSequence();

			var mc = new Rilevazione();
			mc.setRilevazioneId(pk);
			mc.setStrutturaId(body.getStrutturaId());
			mc.setStrCatId(body.getStrCatId());
			mc.setModuloCompilatoId(body.getModuloCompilatoId());
			mc.setModuloConfigId(body.getModuloConfigId());
			mc.setDataoraRilevazione(body.getDataoraRilevazione());
			mc.setValiditaInizio(body.getValiditaInizio());
			mc.setValiditaFine(body.getValiditaFine());
			mc.setUtenteCreazione(utenteCreazione);
			rilevazioneDao.insert(mc);

			return mc;
		} catch (Exception e) {
			logError(sId, "insertRilevazione", e.getMessage(), e);
			throw e;
		}

	}


	public List<ModuloConfigRidotto> getRilevazioneModuli(Integer sId, @NotNull Integer strutturaId, String moduloConfigGruppoCod) {
		try {
			return moduloConfigRepository.getRilevazioniModuliDisponibili(strutturaId, moduloConfigGruppoCod);
		} catch (Exception e) {
			logError(sId, "getRilevazioneModuli", e.getMessage(), e);
			throw e;
		}
	}

}
