/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl.StrutturaTitolaritaDao;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.StrutturaTitolarita;
import it.csi.vigilsan.vigilsancommon.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsancommon.util.LogMessageEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTException;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableApiServiceImpl;

@Service
public class StrutturaTitolaritaServiceImpl extends AuditableApiServiceImpl  {

	@Autowired
	private StrutturaTitolaritaDao dao;

	public StrutturaTitolarita getById(Integer sId,Integer id) {
		try {
			return dao.get(id);
		} catch (EmptyResultDataAccessException e) {
			logError(sId,"getById", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.STRUTTURA_TITOLARITA_NON_TROVATA);
		}
	}

	
	public StrutturaTitolarita getStrutturaTitolaritaByCodArpe(String inCodArpe) {
		try {
			return dao.getStrutturaTitolaritaByCodArpe(inCodArpe);
		} catch (EmptyResultDataAccessException e) {
			logError(null, "getStrutturaTitolaritaByCodArpe", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.STRUTTURA_TITOLARITA_NON_TROVATA);
		}
	}

	// GESTIONE PER BATCH RPVIAR
	public Integer gestioneStrutturaTitolarita(String inStrutturaTitolaritaCod, String inStrutturaTitolaritaDesc, String inUtenteCreazione) throws Exception {
		var methodName = "gestioneStrutturaTitolarita";
		logDebug(null,methodName, LogMessageEnum.BEGIN.getMessage());
	
		Integer strutturaTitolaritaId = null;
		// la struttura titolarita non è presente sulla base dati la creo 
		try {

			// check su codice titolarita (COD_TITOLARITA) posizione 26
			logDebug(null, methodName, "COD_TITOLARITA : "+inStrutturaTitolaritaCod);
			logDebug(null, methodName, "TITOLARITA : "+inStrutturaTitolaritaDesc);
			StrutturaTitolarita strutturaTitolaritaFound = getStrutturaTitolaritaByCodArpe(inStrutturaTitolaritaCod);
			logDebug(null, methodName, "Struttura Titolarita Id found : "+strutturaTitolaritaFound.getStrutturaTitolaritaId());
			strutturaTitolaritaId = strutturaTitolaritaFound.getStrutturaTitolaritaId();
		} catch (RESTException restException) {
			logDebug(null, methodName, "Code error : "+restException.getCode());		
			
			// se il codice titolarita non è presente devo inserire la struttura titolarita con
			// struttura_titolarita_cod = cod_natura (posizione 26)
			// struttura_titolarita_cod_arpe = cod_natura (posizione 26)
		    // struttura_titolarita_desc = natura (posizione 27)
		    if (ErrorCodeEnum.STRUTTURA_TITOLARITA_NON_TROVATA.getCode().equals(restException.getCode())) {
				StrutturaTitolarita strutturaTitolarita = new StrutturaTitolarita();
				logDebug(null, methodName, "COD_TITOLARITA : "+inStrutturaTitolaritaCod);
				strutturaTitolarita.setStrutturaTitolaritaCod(inStrutturaTitolaritaCod);
				logDebug(null, methodName, "COD_TITOLARITA ARPE : "+inStrutturaTitolaritaCod);
				strutturaTitolarita.setStrutturaTitolaritaCodArpe(inStrutturaTitolaritaCod);
				logDebug(null, methodName, "TITOLARITA"+inStrutturaTitolaritaDesc);
				strutturaTitolarita.setStrutturaTitolaritaDesc(inStrutturaTitolaritaDesc);
				logDebug(null, methodName, "Utente creazione : "+inUtenteCreazione);
				strutturaTitolarita.setUtenteCreazione(inUtenteCreazione);
				strutturaTitolaritaId = dao.getSequence();
				logDebug(null, methodName, "Struttura Titolarita Id inserted : "+strutturaTitolaritaId);
				strutturaTitolarita.setStrutturaTitolaritaId(strutturaTitolaritaId);	
				// insert
				dao.insert(strutturaTitolarita);
			}

		} catch (Exception exception) {
			logDebug(null, methodName, "Eccezione generica per la struttura titolarita : "+exception.getMessage());		
			throw exception;
		} finally {
			logDebug(null,methodName, LogMessageEnum.END.getMessage());
		}
		return strutturaTitolaritaId;
	}
	
	
}
