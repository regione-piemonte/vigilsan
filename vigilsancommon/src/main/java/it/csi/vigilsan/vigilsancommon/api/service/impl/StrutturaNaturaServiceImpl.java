/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl.StrutturaNaturaDao;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.StrutturaNatura;
import it.csi.vigilsan.vigilsancommon.api.service.StrutturaNaturaService;
import it.csi.vigilsan.vigilsancommon.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsancommon.util.LogMessageEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTException;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableApiServiceImpl;

@Service
public class StrutturaNaturaServiceImpl extends AuditableApiServiceImpl implements StrutturaNaturaService  {

	@Autowired
	private StrutturaNaturaDao dao;
		
	@Override
	public StrutturaNatura getById(Integer sId,Integer strutturaNaturaId) {
		try {
			return dao.get(strutturaNaturaId);
		} catch (EmptyResultDataAccessException e) {
			logError(sId,"getById", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.STRUTTURA_NATURA_NON_TROVATA);
		}
	}
	
	@Override
	public StrutturaNatura getStrutturaNaturaByCodArpe(String inCodArpe) throws Exception {
		try {
			return dao.getStrutturaNaturaByCodArpe(inCodArpe);
		} catch (EmptyResultDataAccessException e) {
			logError(null, "getStrutturaNaturaByCodArpe", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.STRUTTURA_NATURA_NON_TROVATA);
		}
	}

	
	@Override
	public void insertStrutturaNatura(StrutturaNatura inStrutturaNatura) {
		var methodName = "insertStrutturaNatura";
		logDebug(null,methodName, LogMessageEnum.BEGIN.getMessage());

		try {			
			dao.insert(inStrutturaNatura);
			logDebug(null,methodName, "Insert Struttura Natura");

		} catch (Exception e) {
			logError(null, "getStrutturaNaturaByCodArpe", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.ERRORE_INSERIMENTO_STRUTTURA_NATURA);
		} finally {
			logDebug(null,methodName, LogMessageEnum.END.getMessage());			
		}
	}
	
	// GESTIONE PER BATCH RPVIAR
	@Override
	public Integer gestioneStrutturaNatura(String inStrutturaNaturaCod, String inStrutturaNaturaDesc, String inUtenteCreazione) throws Exception {
		var methodName = "gestioneStrutturaNatura";
		logDebug(null,methodName, LogMessageEnum.BEGIN.getMessage());
	
		Integer strutturaNaturaId = null;
		// la struttura natura non è presente sulla base dati la creo 
		try {

			// check su codice natura (COD_NATURA) posizione 6
			logDebug(null, methodName, "COD_NATURA : "+inStrutturaNaturaCod);
			logDebug(null, methodName, "NATURA : "+inStrutturaNaturaDesc);
			StrutturaNatura strutturaNaturaFound = getStrutturaNaturaByCodArpe(inStrutturaNaturaCod);
			logDebug(null, methodName, "Struttura Natura Id found : "+strutturaNaturaFound.getStrutturaNaturaId());
			strutturaNaturaId = strutturaNaturaFound.getStrutturaNaturaId();
		} catch (RESTException restException) {			
			logDebug(null, methodName, "Code error : "+restException.getCode());		
			
			// se il codice natura non è presente devo inserire la struttura natura con
			// struttura_natura_cod = cod_natura (posizione 6)
			// struttura_natura_cod_arpe = cod_natura (posizione 6)
		    //struttura_natura_desc = natura (posizione 6)
			logDebug(null, methodName, "Code error : "+restException.getCode());		
		    if(ErrorCodeEnum.STRUTTURA_NATURA_NON_TROVATA.getCode().equals(restException.getCode())) {
		    	
//			if(restException.getMessage().equals(ErrorCodeEnum.STRUTTURA_NATURA_NON_TROVATA)) {
				StrutturaNatura strutturaNatura = new StrutturaNatura();
				logDebug(null, methodName, "COD_NATURA : "+inStrutturaNaturaCod);
				strutturaNatura.setStrutturaNaturaCod(inStrutturaNaturaCod);
				logDebug(null, methodName, "COD_NATURA ARPE : "+inStrutturaNaturaCod);
				strutturaNatura.setStrutturaNaturaCodArpe(inStrutturaNaturaCod);
				logDebug(null, methodName, "COD_NATURA DESC : "+inStrutturaNaturaDesc);
				strutturaNatura.setStrutturaNaturaDesc(inStrutturaNaturaDesc);
				logDebug(null, methodName, "Utente creazione : "+inUtenteCreazione);
				strutturaNatura.setUtenteCreazione(inUtenteCreazione);
				strutturaNaturaId = dao.getSequence();
				strutturaNatura.setStrutturaNaturaId(strutturaNaturaId);
				logDebug(null, methodName, "Struttura Natura Id inserted : "+strutturaNaturaId);

				dao.insert(strutturaNatura);
			}
		} catch (Exception exception) {
			logDebug(null, methodName, "Eccezione generica per la struttura natura : "+exception.getMessage());		
			throw exception;
		} finally {
			logDebug(null,methodName, LogMessageEnum.END.getMessage());
		}
		return strutturaNaturaId;
	}
	
}
