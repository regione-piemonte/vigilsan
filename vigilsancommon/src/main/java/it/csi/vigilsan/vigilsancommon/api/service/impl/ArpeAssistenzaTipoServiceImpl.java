/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl.ArpeAssistenzaTipoDao;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.ArpeAssistenzaTipo;
import it.csi.vigilsan.vigilsancommon.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsancommon.util.LogMessageEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTException;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableApiServiceImpl;

@Service
public class ArpeAssistenzaTipoServiceImpl extends AuditableApiServiceImpl  {

	@Autowired
	private ArpeAssistenzaTipoDao dao;

	public ArpeAssistenzaTipo getById(Integer sId,Integer id) {
		try {
			return dao.get(id);
		} catch (EmptyResultDataAccessException e) {
			logError(sId,"getById", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.ASSISTENZA_TIPO_NON_TROVATA);
		}
	}
	
	public ArpeAssistenzaTipo getArpeAssistenzaTipoByCod(String inCod) {
		try {
			return dao.getArpeAssistenzaTipoByCod(inCod);
		} catch (EmptyResultDataAccessException e) {
			logError(null, "getArpeStrutturaTipoByCod", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.ASSISTENZA_TIPO_NON_TROVATA);
		}
	}
	
	
	// GESTIONE PER BATCH RPVIAR
	public Integer gestioneArpeAssistenzaTipo(String inAssistenzaTipoCod, String inAssistenzaTipoDesc, String inUtenteCreazione) throws Exception {
		var methodName = "gestioneArpeAssistenzaTipo";
		logDebug(null,methodName, LogMessageEnum.BEGIN.getMessage());
	
		Integer arpeAssistenzaTipoId = null;
		// se l'assistenza tipo non è presente sulla base dati la creo 
		try {

			// check su codice assistenza tipo (COD_TIPO_STRUTTURA) posizione 12
			logDebug(null, methodName, "COD_TIPO_ASSISTENZA : "+inAssistenzaTipoCod);
			logDebug(null, methodName, "DESC_TIPO_ASSISTENZA : "+inAssistenzaTipoDesc);
			ArpeAssistenzaTipo arpeAssistenzaTipoFound = getArpeAssistenzaTipoByCod(inAssistenzaTipoCod);
			logDebug(null, methodName, "Arpe Assistenza Tipo Id found : "+arpeAssistenzaTipoFound.getArpeAssistenzaTipoId());
			arpeAssistenzaTipoId = arpeAssistenzaTipoFound.getArpeAssistenzaTipoId();
		} catch (RESTException restException) {
			logDebug(null, methodName, "Code error : "+restException.getCode());		
		
			// se il codice assistenza tipo non è presente devo inserirla con
			// arpe_assistenza_tipo_cod = COD_TIPO_ASSISTENZA (posizione 12)
			// arpe_assistenza_tipo_desc = DESC_TIPO_ASSISTENZA(posizione 13)
			if(restException.getCode().equals(ErrorCodeEnum.ASSISTENZA_TIPO_NON_TROVATA.getCode())) {
//			if(restException.getMessage().equals(ErrorCodeEnum.ASSISTENZA_TIPO_ID_NON_TROVATA)) {
				ArpeAssistenzaTipo arpeAssistenzaTipo = new ArpeAssistenzaTipo();
				logDebug(null, methodName, "COD_TIPO_ASSISTENZA : "+inAssistenzaTipoCod);
				arpeAssistenzaTipo.setArpeAssistenzaTipoCod(inAssistenzaTipoCod);
				logDebug(null, methodName, "DESC_TIPO_ASSISTENZA : "+inAssistenzaTipoDesc);
				arpeAssistenzaTipo.setArpeAssistenzaTipoDesc(inAssistenzaTipoDesc);
				logDebug(null, methodName, "Utente creazione : "+inUtenteCreazione);
				arpeAssistenzaTipo.setUtenteCreazione(inUtenteCreazione);
				arpeAssistenzaTipoId = dao.getSequence();
				logDebug(null, methodName, "Assistenza Tipo Id inserted : "+arpeAssistenzaTipoId);
				arpeAssistenzaTipo.setArpeAssistenzaTipoId(arpeAssistenzaTipoId);	
				// insert
				dao.insert(arpeAssistenzaTipo);
			}
		} catch (Exception exception) {
			logDebug(null, methodName, "Eccezione generica per arpe assistenza tipo : "+exception.getMessage());		
			throw exception;
		} finally {
			logDebug(null,methodName, LogMessageEnum.END.getMessage());
		}
		return arpeAssistenzaTipoId;
	}


}
