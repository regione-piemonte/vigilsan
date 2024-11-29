/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl.ArpeAttivitaDao;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.ArpeAttivita;
import it.csi.vigilsan.vigilsancommon.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsancommon.util.LogMessageEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTException;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableApiServiceImpl;

@Service
public class ArpeAttivitaServiceImpl extends AuditableApiServiceImpl  {

	@Autowired
	private ArpeAttivitaDao dao;
	
	public ArpeAttivita getById(Integer sId,Integer id) {
		try {
			return dao.get(id);
		} catch (EmptyResultDataAccessException e) {
			logError(sId,"getById", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.ATTIVITA_NON_TROVATA);
		}
	}
	
	public ArpeAttivita getArpeAttivitaByCod(String inCod) {
		try {
			return dao.getArpeAttivitaByCod(inCod);
		} catch (EmptyResultDataAccessException e) {
			logError(null, "getArpeStrutturaTipoByCod", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.ATTIVITA_NON_TROVATA);
		}
	}
	
	
	// GESTIONE PER BATCH RPVIAR
	public Integer gestioneArpeAttivita(String inAttivitaCod, String inAttivitaDesc, String inUtenteCreazione) throws Exception {
		var methodName = "gestioneArpeAttivita";
		logDebug(null,methodName, LogMessageEnum.BEGIN.getMessage());
	
		Integer arpeAttivitaId = null;
		// se l'attivita non e' presente sulla base dati la creo 
		try {

			// check su codice attivita (COD_ATTIVITA) posizione 22
			logDebug(null, methodName, "COD_ATTIVITA : "+inAttivitaCod);
			logDebug(null, methodName, "DESC_ATTIVITA : "+inAttivitaDesc);
			ArpeAttivita arpeAttivitaFound = getArpeAttivitaByCod(inAttivitaCod);
			logDebug(null, methodName, "Arpe Attivita Id found : "+arpeAttivitaFound.getArpeAttivitaId());
			arpeAttivitaId = arpeAttivitaFound.getArpeAttivitaId();
		} catch (RESTException restException) {
			logDebug(null, methodName, "Code error : "+restException.getCode());		
			
			// se il codice attivita non è presente devo inserirla con
			// arpe_attivita_cod = COD_ATTIVITA (posizione 22)
			// arpe_attivita_desc = DESC_ATTIVITA (posizione 23)
			if (ErrorCodeEnum.ATTIVITA_NON_TROVATA.getCode().equals(restException.getCode())) {

				ArpeAttivita arpeAttivita = new ArpeAttivita();
				logDebug(null, methodName, "COD_ATTIVITA : "+inAttivitaCod);
				arpeAttivita.setArpeAttivitaCod(inAttivitaCod);
				logDebug(null, methodName, "DESC_ATTIVITA : "+inAttivitaDesc);
				arpeAttivita.setArpeAttivitaDesc(inAttivitaDesc);
				logDebug(null, methodName, "Utente creazione : "+inUtenteCreazione);
				arpeAttivita.setUtenteCreazione(inUtenteCreazione);
				arpeAttivitaId = dao.getSequence();
				logDebug(null, methodName, "Arpe Attiva Id inserted : "+arpeAttivitaId);
				arpeAttivita.setArpeAttivitaId(arpeAttivitaId);	
				// insert
				dao.insert(arpeAttivita);
			}

		} catch (Exception exception) {
			logDebug(null, methodName, "Eccezione generica per arpe attivita : "+exception.getMessage());		
			throw exception;
		} finally {
			logDebug(null,methodName, LogMessageEnum.END.getMessage());
		}
		return arpeAttivitaId;
	}

}

