/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelAppuntamentoSoggetto;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.dao.impl.AppuntamentoSoggettoDao;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.AppuntamentoSoggetto;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.AppuntamentoSoggettoEsteso;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.AppuntamentoSoggettoEstesoWsoggetto;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.RuoloAppuntamentoSoggetto;
import it.csi.vigilsan.vigilsanpratiche.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableWPersistenceApiServiceImpl;
import it.csi.vigilsan.vigilsanutil.generic.util.AuditOperazioneEnum;

@Service
public class AppuntamentoSoggettoServiceImpl extends AuditableWPersistenceApiServiceImpl {

	@Autowired
	private AppuntamentoSoggettoDao appuntamentoSoggettoDao;

	// @Autowired
	// private EnteSoggettoDao enteSoggetto;

//	post e delete (logica) che dato appuntamento id e soggetto id inserisca su r 
	// appuntamneto soggetto la associazione se non già presente valida.

	@Transactional
	public AppuntamentoSoggetto postAppuntamentoSoggetto(Integer sId, ModelAppuntamentoSoggetto body,
			String shibIdentitaCodiceFiscale) {
		List<AppuntamentoSoggetto> old = appuntamentoSoggettoDao.selectBySoggettoIdAppuntamentoId(body.getSoggettoId(),
				body.getAppuntamentoId(), body.getRuoloAppuntamentoSoggettoId());
		if (old != null && !old.isEmpty()) {
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.APPUNTAMENTO_SOGGETTO_ASSOCIAZIONE_ESISTENTE);
		}
		Boolean verificato = appuntamentoSoggettoDao.verificaAssociazioneEnteSoggetto(body.getSoggettoId(),
				body.getAppuntamentoId());
		if (Boolean.FALSE.equals(verificato)) {
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.APPUNTAMENTO_SOGGETTO_ASSOCIAZIONE_NON_VALIDA);
		}

		AppuntamentoSoggetto res = new AppuntamentoSoggetto();
		res.setAppuntamentoSoggettoId(appuntamentoSoggettoDao.getSequence());
		res.setAppuntamentoId(body.getAppuntamentoId());
		res.setSoggettoId(body.getSoggettoId());
		res.setRuoloAppuntamentoSoggettoId(body.getRuoloAppuntamentoSoggettoId());
		res.setUtenteCreazione(shibIdentitaCodiceFiscale);

		appuntamentoSoggettoDao.insert(res);
		logAuditDb(sId, AuditOperazioneEnum.INSERT, "vigil_r_appuntamento_soggetto",
				res.getAppuntamentoSoggettoId().toString());
		return null;
	}

	@Transactional
	public AppuntamentoSoggetto deleteAppuntamentoSoggetto(Integer sId, ModelAppuntamentoSoggetto body,
			String shibIdentitaCodiceFiscale) {

		AppuntamentoSoggetto old = appuntamentoSoggettoDao.get(body.getAppuntamentoSoggettoId());

		if (old.getDataCancellazione() != null) {
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.APPUNTAMENTO_SOGGETTO_NON_VALIDO);
		}
		old.setUtenteCancellazione(shibIdentitaCodiceFiscale);

		appuntamentoSoggettoDao.delete(old);
		logAuditDb(sId, AuditOperazioneEnum.LOGIC_DELETE, "vigil_r_appuntamento_soggetto",
				old.getAppuntamentoSoggettoId().toString());
		return old;
	}

	public List<AppuntamentoSoggettoEsteso> getAppuntamentoSoggettoListaByAppuntamento(Integer appuntamentoId) {
		return appuntamentoSoggettoDao.getAppuntamentoSoggettoByAppuntamento(appuntamentoId);
	}
	protected List<AppuntamentoSoggettoEstesoWsoggetto> getAppuntamentoSoggettoListaByAppuntamentoWsoggeto(Integer appuntamentoId) {
		return appuntamentoSoggettoDao.getAppuntamentoSoggettoByAppuntamentoWsoggetto(appuntamentoId);
	}

	public List<RuoloAppuntamentoSoggetto> getAppuntamentoSoggettoRuoloLista() {
		return appuntamentoSoggettoDao.selectRuoloAppuntamentoSoggettoDecodifica();
	}
}
