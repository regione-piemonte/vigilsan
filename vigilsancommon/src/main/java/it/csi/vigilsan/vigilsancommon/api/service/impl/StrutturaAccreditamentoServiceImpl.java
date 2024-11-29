/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl.StrutturaAccreditamentoDao;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.StrutturaAccreditamento;
import it.csi.vigilsan.vigilsancommon.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsancommon.util.LogMessageEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTException;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableApiServiceImpl;

@Service
public class StrutturaAccreditamentoServiceImpl extends AuditableApiServiceImpl  {

	@Autowired
	private StrutturaAccreditamentoDao dao;

	public StrutturaAccreditamento getById(Integer sId,Integer id) {
		try {
			return dao.get(id);
		} catch (EmptyResultDataAccessException e) {
			logError(sId,"getById", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.STRUTTURA_ACCREDITAMENTO_NON_TROVATA);
		}
	}

	
	public StrutturaAccreditamento getStrutturaAccreditamentoByCodArpe(String inCodArpe) {
		try {
			return dao.getStrutturaAccreditamentoByCodArpe(inCodArpe);
		} catch (EmptyResultDataAccessException e) {
			logError(null, "getStrutturaAccreditamentoByCodArpe", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.STRUTTURA_ACCREDITAMENTO_NON_TROVATA);
		}
	}

	// GESTIONE PER BATCH RPVIAR
	public Integer gestioneStrutturaAccreditamento(String inStrutturaAccreditamentoCod, String inStrutturaAccreditamentoDesc, String inUtenteCreazione) throws Exception {
		var methodName = "gestioneStrutturaAccreditamento";
		logDebug(null,methodName, LogMessageEnum.BEGIN.getMessage());
	
		Integer strutturaAccreditamentoId = null;
		// la struttura accreditamento non è presente sulla base dati la creo 
		try {

			// check su codice accreditamento (COD_ACCREDITAMENTO) posizione 8
			logDebug(null, methodName, "COD_ACCREDITAMENTO : "+inStrutturaAccreditamentoCod);
			logDebug(null, methodName, "ACCREDITAMENTO : "+inStrutturaAccreditamentoDesc);
			StrutturaAccreditamento strutturaAccreditamentoFound = getStrutturaAccreditamentoByCodArpe(inStrutturaAccreditamentoCod);
			logDebug(null, methodName, "Struttura Accreditamento Id found : "+strutturaAccreditamentoFound.getStrutturaAccreditamentoId());
			strutturaAccreditamentoId = strutturaAccreditamentoFound.getStrutturaAccreditamentoId();
		} catch (RESTException restException) {
			logDebug(null, methodName, "Code error : "+restException.getCode());		
			
			// se il codice natura non è presente devo inserire la struttura accreditamento con
			// struttura_accreditamento_cod = cod_natura (posizione 8)
			// struttura_accreditamento_cod_arpe = cod_natura (posizione 8)
		    //struttura_accreditamento_desc = natura (posizione 9)
		    if (ErrorCodeEnum.STRUTTURA_ACCREDITAMENTO_NON_TROVATA.getCode().equals(restException.getCode())) {
		    	
//			if(restException.getMessage().equals(ErrorCodeEnum.STRUTTURA_ACCREDITAMENTO_NON_TROVATA)) {
				StrutturaAccreditamento strutturaAccreditamento = new StrutturaAccreditamento();
				logDebug(null, methodName, "COD_ACCREDITAMENTO : "+inStrutturaAccreditamentoCod);
				strutturaAccreditamento.setStrutturaAccreditamentoCod(inStrutturaAccreditamentoCod);
				logDebug(null, methodName, "COD_ACCREDITAMENTO ARPE : "+inStrutturaAccreditamentoCod);
				strutturaAccreditamento.setStrutturaAccreditamentoCodArpe(inStrutturaAccreditamentoCod);
				logDebug(null, methodName, "ACCREDITAMENTO : "+inStrutturaAccreditamentoDesc);
				strutturaAccreditamento.setStrutturaAccreditamentoDesc(inStrutturaAccreditamentoDesc);
				logDebug(null, methodName, "Utente creazione : "+inUtenteCreazione);
				strutturaAccreditamento.setUtenteCreazione(inUtenteCreazione);
				strutturaAccreditamentoId = dao.getSequence();
				logDebug(null, methodName, "Struttura Accreditamento Id inserted : "+strutturaAccreditamentoId);
				strutturaAccreditamento.setStrutturaAccreditamentoId(strutturaAccreditamentoId);	
				// insert
				dao.insert(strutturaAccreditamento);
			}

		} catch (Exception exception) {
			logDebug(null, methodName, "Eccezione generica per la struttura accreditamento : "+exception.getMessage());		
			throw exception;
		} finally {
			logDebug(null,methodName, LogMessageEnum.END.getMessage());
		}
		return strutturaAccreditamentoId;
	}
	
	
}
