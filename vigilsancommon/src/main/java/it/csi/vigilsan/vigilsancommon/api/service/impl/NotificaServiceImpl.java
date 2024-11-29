/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.vigilsan.vigilsancommon.api.generated.dto.ModelNotifica;
import it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl.NotificaDao;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.Notifica;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableWPersistenceApiServiceImpl;
import it.csi.vigilsan.vigilsanutil.generic.util.AuditOperazioneEnum;

@Service
public class NotificaServiceImpl extends AuditableWPersistenceApiServiceImpl  {

	@Autowired
	private NotificaDao notificaDao;

	@Transactional
	public Notifica postNotifica(Integer sId, ModelNotifica body, String shibIdentitaCodiceFiscale) {
		Notifica res = new Notifica();
		res.setNotificaId(notificaDao.getSequence());
		res.setProfiloId(body.getProfiloId());
		res.setSoggettoId(body.getSoggettoId());
		res.setEnteId(body.getEnteId());
		res.setStrutturaId(body.getStrutturaId());

		res.setTitle(body.getTitle());
		res.setBodyHtml(body.getBodyHtml());
		res.setBodyTextShort(body.getBodyTextShort());
		res.setBodyTextLong(body.getBodyTextLong());
		res.setUtenteCreazione(shibIdentitaCodiceFiscale);
		notificaDao.insert(res);
		logAuditDb(sId, AuditOperazioneEnum.INSERT, "vigil_t_notifica", res.getNotificaId().toString());
		return res;
	}

}
