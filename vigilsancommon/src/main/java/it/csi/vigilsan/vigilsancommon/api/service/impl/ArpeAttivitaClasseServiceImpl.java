/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl.ArpeAttivitaClasseDao;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.ArpeAttivitaClasse;
import it.csi.vigilsan.vigilsancommon.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsancommon.util.LogMessageEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTException;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableApiServiceImpl;

@Service
public class ArpeAttivitaClasseServiceImpl extends AuditableApiServiceImpl  {

	@Autowired
	private ArpeAttivitaClasseDao dao;

	public ArpeAttivitaClasse getById(Integer sId,Integer id) {
		try {
			return dao.get(id);
		} catch (EmptyResultDataAccessException e) {
			logError(sId,"getById", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.ATTIVITA_CLASSE_NON_TROVATA);
		}
	}
	
	public ArpeAttivitaClasse getArpeAttivitaClasseByCod(String inCod) {
		try {
			return dao.getArpeAttivitaClasseByCod(inCod);
		} catch (EmptyResultDataAccessException e) {
			logError(null, "getArpeStrutturaTipoByCod", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.ATTIVITA_CLASSE_NON_TROVATA);
		}
	}
	
	
	// GESTIONE PER BATCH RPVIAR
	public Integer gestioneArpeAttivitaClasse(String inAttivitaClasseCod, String inAttivitaClasseDesc, String inUtenteCreazione) throws Exception {
		var methodName = "gestioneArpeAttivita";
		logDebug(null,methodName, LogMessageEnum.BEGIN.getMessage());
	
		Integer arpeAttivitaClasseId = null;
		// se l'attivita classe non e' presente sulla base dati la creo 
		try {

			// check su codice attivita (COD_CLASSE_ATTIVITA) posizione 24
			logDebug(null, methodName, "COD_CLASSE_ATTIVITA : "+inAttivitaClasseCod);
			logDebug(null, methodName, "DESC_CLASSE_ATTIVITA : "+inAttivitaClasseDesc);
			ArpeAttivitaClasse arpeAttivitaClasseFound = getArpeAttivitaClasseByCod(inAttivitaClasseCod);
			logDebug(null, methodName, "Arpe Attivita Id found : "+arpeAttivitaClasseFound.getArpeAttivitaClasseId());
			arpeAttivitaClasseId = arpeAttivitaClasseFound.getArpeAttivitaClasseId();
		} catch (RESTException restException) {
			logDebug(null, methodName, "Code error : "+restException.getCode());		
			
			// se il codice attivita classe non è presente devo inserirla con
			// arpe_attivita_classe_cod = COD_CLASSE_ATTIVITA (posizione 24)
			// arpe_attivita_classe_desc = DESC_CLASSE_ATTIVITA(posizione 25)
			if (ErrorCodeEnum.ATTIVITA_CLASSE_NON_TROVATA.getCode().equals(restException.getCode())) {
				
				ArpeAttivitaClasse arpeAttivitaClasse = new ArpeAttivitaClasse();
				logDebug(null, methodName, "COD_CLASSE_ATTIVITA : "+inAttivitaClasseCod);
				arpeAttivitaClasse.setArpeAttivitaClasseCod(inAttivitaClasseCod);
				logDebug(null, methodName, "DESC_ATTIVITA : "+inAttivitaClasseDesc);
				arpeAttivitaClasse.setArpeAttivitaClasseDesc(inAttivitaClasseDesc);
				logDebug(null, methodName, "Utente creazione : "+inUtenteCreazione);
				arpeAttivitaClasse.setUtenteCreazione(inUtenteCreazione);
				arpeAttivitaClasseId = dao.getSequence();
				logDebug(null, methodName, "Arpe Attivita Id inserted : "+arpeAttivitaClasseId);
				arpeAttivitaClasse.setArpeAttivitaClasseId(arpeAttivitaClasseId);	
				// insert
				dao.insert(arpeAttivitaClasse);
			}
		} catch (Exception exception) {
			logDebug(null, methodName, "Eccezione generica per arpe attivita classe : "+exception.getMessage());		
			throw exception;
		} finally {
			logDebug(null,methodName, LogMessageEnum.END.getMessage());
		}
		return arpeAttivitaClasseId;
	}

}

