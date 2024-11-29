/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.vigilsan.vigilsancommon.api.generated.dto.ModelEnteSoggetto;
import it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl.EnteSoggettoDao;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.EnteSoggetto;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.EnteSoggettoEsteso;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.RuoloEnteSoggetto;
import it.csi.vigilsan.vigilsancommon.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableWPersistenceApiServiceImpl;
import it.csi.vigilsan.vigilsanutil.generic.util.AuditOperazioneEnum;

@Service
public class EnteSoggettoServiceImpl extends AuditableWPersistenceApiServiceImpl {

	@Autowired
	private EnteSoggettoDao enteSoggettoDao;

	public List<EnteSoggettoEsteso> getEnteSoggettoLista(Integer enteId, Integer enteRuoloId) {
		return enteSoggettoDao.getListByEnteId(enteId, enteRuoloId);
	}

	@Transactional
	public ModelEnteSoggetto inserisciEnteSoggetto(ModelEnteSoggetto body, String shibIdentitaCodiceFiscale,
			Integer sId) {
		EnteSoggetto daInserire = new EnteSoggetto();

		if (body.getEnteSoggId() != null) {
			var old = enteSoggettoDao.get(body.getEnteSoggId());
			if (old == null || old.getDataCancellazione() != null
					|| (old.getValiditaFine() != null && old.getValiditaFine().before(new Date()))) {
				throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.ENTE_SOGGETTO_NON_VALIDO);
			}

			old.setUtenteCancellazione(shibIdentitaCodiceFiscale);
			enteSoggettoDao.delete(old);
			logAuditDb(sId, AuditOperazioneEnum.LOGIC_DELETE, "vigil_r_ente_soggetto", body.getEnteSoggId().toString());
		}

		daInserire.setSoggettoId(body.getSoggettoId());
		daInserire.setRuoloEnteSoggettoId(body.getRuoloEnteSoggettoId());
		daInserire.setEnteId(body.getEnteId());
		daInserire.setEnteSoggId(enteSoggettoDao.getSequence());
		daInserire.setUtenteCreazione(shibIdentitaCodiceFiscale);
		daInserire.setValiditaInizio(body.getValiditaInizio());
		daInserire.setValiditaFine(body.getValiditaFine());
		enteSoggettoDao.insert(daInserire);
		logAuditDb(sId, AuditOperazioneEnum.INSERT, "vigil_r_ente_soggetto", daInserire.getEnteSoggId().toString());
		return enteSoggettoDao.get(daInserire.getEnteSoggId());
	}

	public List<RuoloEnteSoggetto> getRuoloEnteSoggettoDecodificaLista() {
		return enteSoggettoDao.getRuoloEnteSoggettoDecodificaLista();
	}

	@Transactional
	public ModelEnteSoggetto invalidaEnteSoggetto(ModelEnteSoggetto body, String shibIdentitaCodiceFiscale,
			Integer sId) {

		var old = enteSoggettoDao.get(body.getEnteSoggId());
		if (old == null || old.getDataCancellazione() != null
				|| (old.getValiditaFine() != null && old.getValiditaFine().before(new Date()))) {
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.ENTE_SOGGETTO_NON_VALIDO);
		}

		old.setUtenteCancellazione(shibIdentitaCodiceFiscale);
		enteSoggettoDao.delete(old);
		logAuditDb(sId, AuditOperazioneEnum.LOGIC_DELETE, "vigil_r_ente_soggetto", body.getEnteSoggId().toString());

		EnteSoggetto daInserire = new EnteSoggetto();
		daInserire.setSoggettoId(old.getSoggettoId());
		daInserire.setRuoloEnteSoggettoId(old.getRuoloEnteSoggettoId());
		daInserire.setEnteId(old.getEnteId());
		daInserire.setEnteSoggId(enteSoggettoDao.getSequence());
		daInserire.setUtenteCreazione(shibIdentitaCodiceFiscale);
		daInserire.setValiditaInizio(old.getValiditaInizio());
		daInserire.setValiditaFine(old.getValiditaFine());

		enteSoggettoDao.invalida(daInserire);
		logAuditDb(sId, AuditOperazioneEnum.INSERT, "vigil_r_ente_soggetto", daInserire.getEnteSoggId().toString());

		return enteSoggettoDao.get(daInserire.getEnteSoggId());
	}

	@Transactional
	public ModelEnteSoggetto cancellaEnteSoggetto(ModelEnteSoggetto body, String shibIdentitaCodiceFiscale,
			Integer sId) {

		var old = enteSoggettoDao.get(body.getEnteSoggId());
		if (old == null || old.getDataCancellazione() != null) {
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.ENTE_SOGGETTO_NON_VALIDO);
		}

		
		old.setUtenteCancellazione(shibIdentitaCodiceFiscale);
		enteSoggettoDao.delete(old);
		logAuditDb(sId, AuditOperazioneEnum.LOGIC_DELETE, "vigil_r_ente_soggetto", body.getEnteSoggId().toString());

		return body;
	}
}
