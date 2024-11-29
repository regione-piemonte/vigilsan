/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl.ArpeDisciplinaDao;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.ArpeDisciplina;
import it.csi.vigilsan.vigilsancommon.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsancommon.util.LogMessageEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTException;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableApiServiceImpl;

@Service
public class ArpeDisciplinaServiceImpl extends AuditableApiServiceImpl  {

	@Autowired
	private ArpeDisciplinaDao dao;

	public ArpeDisciplina getById(Integer sId,Integer id) {
		try {
			return dao.get(id);
		} catch (EmptyResultDataAccessException e) {
			logError(sId,"getById", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.DISCIPLINA_NON_TROVATA);
		}
	}
	
	public ArpeDisciplina getArpeDisciplinaByCod(String inCod) {
		try {
			return dao.getArpeDisciplinaByCod(inCod);
		} catch (EmptyResultDataAccessException e) {
			logError(null, "getArpeDisciplinaByCod", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.DISCIPLINA_NON_TROVATA);
		}
	}
	
	
	// GESTIONE PER BATCH RPVIAR
	public Integer gestioneArpeDisciplina(String inDisciplinaCod, String inDisciplinaDesc, String inUtenteCreazione) throws Exception {
		var methodName = "gestioneArpeDisciplina";
		logDebug(null,methodName, LogMessageEnum.BEGIN.getMessage());
	
		Integer arpeDisciplinaId = null;
		// se la disciplina non e' presente sulla base dati la creo 
		try {

			// check su codice disciplina (COD_DISCIPLINA) posizione 28
			logDebug(null, methodName, "COD_DISCIPLINA : "+inDisciplinaCod);
			logDebug(null, methodName, "DESC_DISCIPLINA : "+inDisciplinaDesc);
			ArpeDisciplina arpeDisciplinaFound = getArpeDisciplinaByCod(inDisciplinaCod);
			logDebug(null, methodName, "Arpe Disciplina Id found : "+arpeDisciplinaFound.getArpeDisciplinaId());
			arpeDisciplinaId = arpeDisciplinaFound.getArpeDisciplinaId();
		} catch (RESTException restException) {
			logDebug(null, methodName, "Code error : "+restException.getCode());		
			
			// se la disciplina non è presente devo inserirla con
			// arpe_disciplina_cod = COD_DISCIPLINA (posizione 28)
			// arpe_disciplina_desc = DESC_DISCIPLINA(posizione 29)
			if (ErrorCodeEnum.DISCIPLINA_NON_TROVATA.getCode().equals(restException.getCode())) {			
				ArpeDisciplina arpeDisciplina = new ArpeDisciplina();
				logDebug(null, methodName, "COD_DISCIPLINA : "+inDisciplinaCod);
				arpeDisciplina.setArpeDisciplinaCod(inDisciplinaCod);
				logDebug(null, methodName, "DESC_DISCIPLINA : "+inDisciplinaDesc);
				arpeDisciplina.setArpeDisciplinaDesc(inDisciplinaDesc);
				logDebug(null, methodName, "Utente creazione : "+inUtenteCreazione);
				arpeDisciplina.setUtenteCreazione(inUtenteCreazione);
				arpeDisciplinaId = dao.getSequence();
				logDebug(null, methodName, "Arpe Disciplina Id inserted : "+arpeDisciplinaId);
				arpeDisciplina.setArpeDisciplinaId(arpeDisciplinaId);	
				// insert
				dao.insert(arpeDisciplina);
			}
		} catch (Exception exception) {
			logDebug(null, methodName, "Eccezione generica per arpe attivita classe : "+exception.getMessage());		
			throw exception;
		} finally {
			logDebug(null,methodName, LogMessageEnum.END.getMessage());
		}
		return arpeDisciplinaId;
	}

}

