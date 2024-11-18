/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanrilevazioni.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.vigilsan.vigilsanrilevazioni.api.generated.dto.ModelVerificaDocumentazione;
import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.dao.impl.VerificaDocumentazioneDao;
import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.model.VerificaDocumentazione;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableWPersistenceApiServiceImpl;
import it.csi.vigilsan.vigilsanutil.generic.util.AuditOperazioneEnum;

@Service
public class VerificaDocumentazioneServiceImpl extends AuditableWPersistenceApiServiceImpl {

	@Autowired
	private VerificaDocumentazioneDao verificaDocumentazioneDao;

	private VerificaDocumentazione getByDcumentazioneId(Integer sId, Integer documentazioneId) {
		try {
			return verificaDocumentazioneDao.getByDcoumentazioneId(documentazioneId);
		} catch (Exception e) {
			logError(sId, "getByDcumentazioneId", e.getMessage(), e);
			throw e;
		}
	}

	@Transactional
	public ModelVerificaDocumentazione updateOrInsert(Integer sId, ModelVerificaDocumentazione doc,
			String shibIdentitaCodiceFiscale) {

		var method = AuditOperazioneEnum.INSERT;
		var keyLog = "";

		if (doc.getDocumentazioneId() != null) {
			verificaDocumentazioneDao.deleteOldDocumenti(doc.getDocumentazioneId(), shibIdentitaCodiceFiscale);
			method = AuditOperazioneEnum.UPDATE;
			keyLog = doc.getDocumentazioneId() + KEY_SEPARATOR;
		}

		var res = insert(sId, doc, shibIdentitaCodiceFiscale);
		
		logAuditDb(sId, method, "vigil_t_verifica_documentazione", keyLog + res.getVerificaDocumentazioneId());
		return verificaDocumentazioneDao.get(res.getVerificaDocumentazioneId());
	}

	private ModelVerificaDocumentazione insert(Integer sId, ModelVerificaDocumentazione doc,
			String shibIdentitaCodiceFiscale) {
		VerificaDocumentazione verifica = new VerificaDocumentazione();
		verifica.setVerificaDocumentazioneId(verificaDocumentazioneDao.getSequence());
		verifica.setDocumentazioneId(doc.getDocumentazioneId());
		verifica.setNote(doc.getNote());
		verifica.setEsitoVerifica(doc.isEsitoVerifica());
		verifica.setUtenteCreazione(shibIdentitaCodiceFiscale);
		verifica.setNotificaId(doc.getNotificaId());
		verificaDocumentazioneDao.insert(verifica);

		return verifica;
	}
}
