/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl.ArpeStrutturaTipoDao;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.ArpeStrutturaTipo;
import it.csi.vigilsan.vigilsancommon.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsancommon.util.LogMessageEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTException;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableApiServiceImpl;

@Service
public class ArpeStrutturaTipoServiceImpl extends AuditableApiServiceImpl  {

	@Autowired
	private ArpeStrutturaTipoDao dao;
	
	public ArpeStrutturaTipo getById(Integer sId,Integer id) {
		try {
			return dao.get(id);
		} catch (EmptyResultDataAccessException e) {
			logError(sId,"getById", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.STRUTTURA_TIPO_NON_TROVATA);
		}
	}
	
	public ArpeStrutturaTipo getArpeStrutturaTipoByCod(String inCod) {
		try {
			return dao.getArpeStrutturaTipoByCod(inCod);
		} catch (EmptyResultDataAccessException e) {
			logError(null, "getArpeStrutturaTipoByCod", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.STRUTTURA_TIPO_NON_TROVATA);
		}
	}
	
	
	// GESTIONE PER BATCH RPVIAR
	public Integer gestioneArpeStrutturaTipo(String inStrutturaTipoCod, String inStrutturaTipoDesc , String inUtenteCreazione) throws Exception {
		var methodName = "gestioneArpeStrutturaTipo";
		logDebug(null,methodName, LogMessageEnum.BEGIN.getMessage());
	
		Integer arpeStrutturaTipoId = null;
		// la struttura tipo non è presente sulla base dati la creo 
		try {

			// check su codice tipo struttura (COD_TIPO_STRUTTURA) posizione 10
			logDebug(null, methodName, "COD_TIPO_STRUTTURA : "+inStrutturaTipoCod);
			logDebug(null, methodName, "DESC_TIPO_STRUTTURA : "+inStrutturaTipoDesc);
			ArpeStrutturaTipo arpeStrutturaTipoFound = getArpeStrutturaTipoByCod(inStrutturaTipoCod);
			logDebug(null, methodName, "ArpeStruttura Tipo Id found : "+arpeStrutturaTipoFound.getArpeStrutturaTipoId());
			arpeStrutturaTipoId = arpeStrutturaTipoFound.getArpeStrutturaTipoId();
		} catch (RESTException restException) {
			logDebug(null, methodName, "Code error : "+restException.getCode());		
			
			// se il codice natura non è presente devo inserire la struttura tipo con
			// arpe_struttura_tipo_cod = COD_TIPO_STRUTTURA (posizione 10)
			// arpe_struttura_tipo_desc = DESC_TIPO_STRUTTURA (posizione 11)
			if (ErrorCodeEnum.STRUTTURA_TIPO_NON_TROVATA.getCode().equals(restException.getCode())) {
					
				ArpeStrutturaTipo arpeStrutturaTipo = new ArpeStrutturaTipo();
				logDebug(null, methodName, "COD_TIPO_STRUTTURA : "+inStrutturaTipoCod);
				arpeStrutturaTipo.setArpeStrutturaTipoCod(inStrutturaTipoCod);
				logDebug(null, methodName, "ACCREDITAMENTO : "+inStrutturaTipoDesc);
				arpeStrutturaTipo.setArpeStrutturaTipoDesc(inStrutturaTipoDesc);
				logDebug(null, methodName, "Utente creazione : "+inUtenteCreazione);
				arpeStrutturaTipo.setUtenteCreazione(inUtenteCreazione);
				arpeStrutturaTipoId = dao.getSequence();
				logDebug(null, methodName, "Struttura Tipo Id inserted : "+arpeStrutturaTipoId);
				arpeStrutturaTipo.setArpeStrutturaTipoId(arpeStrutturaTipoId);		
				// insert
				dao.insert(arpeStrutturaTipo);
			}

		} catch (Exception exception) {
			logDebug(null, methodName, " : "+exception.getMessage());		
			throw exception;
		} finally {
			logDebug(null,methodName, LogMessageEnum.END.getMessage());
		}
		return arpeStrutturaTipoId;
	}


}
