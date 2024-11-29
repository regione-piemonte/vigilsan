/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl.ArpeStrutturaDettaglioDao;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.ArpeStrutturaDettaglio;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.ArpeStrutturaDettaglioEsteso;
import it.csi.vigilsan.vigilsancommon.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsancommon.util.LogMessageEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTException;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableApiServiceImpl;
import jakarta.validation.constraints.NotNull;

@Service
public class ArpeStrutturaDettaglioServiceImpl extends AuditableApiServiceImpl  {

	@Autowired
	private ArpeStrutturaDettaglioDao dao;

	@Autowired
	private ArpeAssistenzaTipoServiceImpl assistenzaTipoService;
	
	@Autowired
	private ArpeAttivitaClasseServiceImpl attivitaClasseService;
	
	@Autowired
	private ArpeAttivitaServiceImpl arpeAttivitaService;

	@Autowired
	private ArpeDisciplinaServiceImpl arpeDisciplinaService;
	
	@Autowired
	private ArpeStrutturaTipoServiceImpl arpeStrutturaTipoService;
	
	public ArpeStrutturaDettaglio getById(Integer sId,Integer id) {
		try {
			return dao.get(id);
		} catch (EmptyResultDataAccessException e) {
			logError(sId,"getById", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.STRUTTURA_DETTAGLIO_NON_TROVATO);
		}
	}
	
	public ArpeStrutturaDettaglio getArpeStrutturaDettaglioByCod(ArpeStrutturaDettaglio inStrutturaDettaglio) {
		try {
			return dao.getArpeStrutturaDettaglioByCod(inStrutturaDettaglio);
		} catch (EmptyResultDataAccessException e) {
			//logError(null, "getArpeStrutturaTipoByCod", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.STRUTTURA_DETTAGLIO_NON_TROVATO);
		}
	}
	
	
	// GESTIONE PER BATCH RPVIAR
	public Integer gestioneArpeStrutturaDettaglio(Integer sId, ArpeStrutturaDettaglio inStrutturaDettaglio, Date inDateBatch, String inUtenteCreazione) throws Exception {
		var methodName = "gestioneArpeStrutturaDettaglio";
		logDebug(null,methodName, LogMessageEnum.BEGIN.getMessage());
	
		Integer arpeStrutturaDettaglioId = null;
		// la struttura dettaglio non è presente sulla base dati la creo 
		try {

			// check su codice arpe struttura dettaglio
			logDebug(null, methodName, "ASSISTENZA TIPO ID : "+inStrutturaDettaglio.getArpeAssistenzaTipoId());
			logDebug(null, methodName, "ATTIVITA CLASSE ID : "+inStrutturaDettaglio.getArpeAttivitaClasseId());
			logDebug(null, methodName, "ATTIVITA ID : "+inStrutturaDettaglio.getArpeAttivitaId());
			logDebug(null, methodName, "STRUTTURA TIPO ID : "+inStrutturaDettaglio.getArpeStrutturaTipoId());
			logDebug(null, methodName, "DISCIPLINA ID : "+inStrutturaDettaglio.getArpeDisciplinaId());
			
			ArpeStrutturaDettaglio arpeStrutturaDettaglioFound = getArpeStrutturaDettaglioByCod(inStrutturaDettaglio);
			logDebug(null, methodName, "ArpeStrutturaDettaglio Id found : "+arpeStrutturaDettaglioFound.getArpeStrDettId());
			logDebug(null, methodName, "ArpeDataAttivazioneAssistenza value : "+arpeStrutturaDettaglioFound.getArpeDataAttivazioneAssistenza());
			logDebug(null, methodName, "ArpeDataAttivazioneAttivita value : "+arpeStrutturaDettaglioFound.getArpeDataAttivazioneAttivita());
			logDebug(null, methodName, "ArpeDataAttivazioneStruttura value : "+arpeStrutturaDettaglioFound.getArpeDataAttivazioneStruttura());
			arpeStrutturaDettaglioId = arpeStrutturaDettaglioFound.getArpeStrDettId();
			
			logDebug(null, methodName, "DATA MODIFICA : "+inDateBatch);
			arpeStrutturaDettaglioFound.setDataModifica(inDateBatch);
			logDebug(null, methodName, "UTENTE MODIFICA : "+inUtenteCreazione);
			arpeStrutturaDettaglioFound.setUtenteModifica(inUtenteCreazione);
			arpeStrutturaDettaglioFound.setArpeDataAttivazioneAssistenza(inStrutturaDettaglio.getArpeDataAttivazioneAssistenza());
			
			// Se le date passate dallo scarico sono precedenti a quelle estratte dal db, le aggiorno
			// la data di inizio delle tre attività relative alle 3 date partono dalla date più vecchia
			logDebug(null, methodName, "DATA ATTIVAZIONE STRUTTURA estratta : "+arpeStrutturaDettaglioFound.getArpeDataAttivazioneStruttura());
			logDebug(null, methodName, "DATA ATTIVAZIONE STRUTTURA letta dallo scarico arpe : "+inStrutturaDettaglio.getArpeDataAttivazioneStruttura());
			if((!isInsertable(sId, arpeStrutturaDettaglioFound.getArpeDataAttivazioneStruttura()) &&
				isInsertable(sId, inStrutturaDettaglio.getArpeDataAttivazioneStruttura())) ||					
			    (isInsertable(sId, arpeStrutturaDettaglioFound.getArpeDataAttivazioneStruttura()) &&
			     isInsertable(sId, inStrutturaDettaglio.getArpeDataAttivazioneStruttura()) &&
			     arpeStrutturaDettaglioFound.getArpeDataAttivazioneStruttura().after(inStrutturaDettaglio.getArpeDataAttivazioneStruttura()))) {
				arpeStrutturaDettaglioFound.setArpeDataAttivazioneStruttura(inStrutturaDettaglio.getArpeDataAttivazioneStruttura());
			}

			logDebug(null, methodName, "DATA ATTIVAZIONE ATTIVITA estratta : "+arpeStrutturaDettaglioFound.getArpeDataAttivazioneAttivita());
			logDebug(null, methodName, "DATA ATTIVAZIONE ATTIVITA letta dallo scarico arpe : "+inStrutturaDettaglio.getArpeDataAttivazioneAttivita());
			if((!isInsertable(sId, arpeStrutturaDettaglioFound.getArpeDataAttivazioneAttivita()) &&
				isInsertable(sId, inStrutturaDettaglio.getArpeDataAttivazioneAttivita())) ||					
				(isInsertable(sId, arpeStrutturaDettaglioFound.getArpeDataAttivazioneAttivita()) &&
				 isInsertable(sId, inStrutturaDettaglio.getArpeDataAttivazioneAttivita()) &&
				 arpeStrutturaDettaglioFound.getArpeDataAttivazioneAttivita().after(inStrutturaDettaglio.getArpeDataAttivazioneAttivita()))) {
				arpeStrutturaDettaglioFound.setArpeDataAttivazioneAttivita(inStrutturaDettaglio.getArpeDataAttivazioneAttivita());
			}

			logDebug(null, methodName, "DATA ATTIVAZIONE ASSISTENZA estratta : "+arpeStrutturaDettaglioFound.getArpeDataAttivazioneAssistenza());	
			logDebug(null, methodName, "DATA ATTIVAZIONE ASSISTENZA letta dallo scarico arpe : "+inStrutturaDettaglio.getArpeDataAttivazioneAssistenza());
			if((!isInsertable(sId, arpeStrutturaDettaglioFound.getArpeDataAttivazioneAssistenza()) &&
				isInsertable(sId, inStrutturaDettaglio.getArpeDataAttivazioneAssistenza())) ||					
				(isInsertable(sId, arpeStrutturaDettaglioFound.getArpeDataAttivazioneAssistenza()) &&
			     isInsertable(sId, inStrutturaDettaglio.getArpeDataAttivazioneAssistenza()) &&
				arpeStrutturaDettaglioFound.getArpeDataAttivazioneAssistenza().after(inStrutturaDettaglio.getArpeDataAttivazioneAssistenza()))) {			
				arpeStrutturaDettaglioFound.setArpeDataAttivazioneAssistenza(inStrutturaDettaglio.getArpeDataAttivazioneAssistenza());
			}
			
			//logDebug(null, methodName, "STRUTTURA DETTAGLIO ID : "+arpeStrutturaDettaglioFound.getArpeStrDettId());
			//arpeStrutturaDettaglioFound.setArpeStrDettId(arpeStrutturaDettaglioFound.getArpeStrDettId());
			// Refresh dei record della tavola struttura dettaglio 
			dao.updateRefreshModifica(arpeStrutturaDettaglioFound);
			
		} catch (RESTException restException) {
			logDebug(null, methodName, "Code error : "+restException.getCode());		
			
			// se i codici delle tavole associate  non sono presenti devo inserire la struttura dettaglio c
			if (ErrorCodeEnum.STRUTTURA_DETTAGLIO_NON_TROVATO.getCode().equals(restException.getCode())) {

				arpeStrutturaDettaglioId = dao.getSequence();
				logDebug(null, methodName, "Struttura Dettaglio Id inserted : "+arpeStrutturaDettaglioId);
				inStrutturaDettaglio.setArpeStrDettId(arpeStrutturaDettaglioId);		
				// insert
				dao.insert(inStrutturaDettaglio);
			}

		} catch (Exception exception) {
			logDebug(null, methodName, " : "+exception.getMessage());		
			throw exception;
		} finally {
			logDebug(null,methodName, LogMessageEnum.END.getMessage());
		}
		return arpeStrutturaDettaglioId;
	}

	public void cancellaStruttureDettaglio(Integer sId, Date inDataInizioElaborazione) {
		var methodName = "cancellaStruttureDettaglio";
        logDebug(sId, methodName, LogMessageEnum.BEGIN.getMessage());
        
		try {
			dao.deleteStruttureDettaglio(inDataInizioElaborazione);
		} catch (Exception e) {
			logError(sId, methodName, e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.ERRORE_CANCELLAZIONE_STRUTTURA_DETTAGLIO);
		} finally {
			logDebug(sId, methodName, LogMessageEnum.END.getMessage());
		}
	}
	
	public List<ArpeStrutturaDettaglioEsteso> getArpeStrutturaDettaglioByStrutturaId(@NotNull Integer sId, @NotNull Integer strutturaId) {
		var res = dao.getArpeStrutturaDettaglioByStrutturaId(strutturaId);
		for(ArpeStrutturaDettaglioEsteso r:res) {
			var id = r.getArpeAssistenzaTipoId();
			if(Objects.nonNull(id)) {
				r.setArpeAssistenzaTipo(assistenzaTipoService.getById(sId, id));
			}
			
			id = r.getArpeAttivitaClasseId();
			if(Objects.nonNull(id)) {
				r.setArpeAttivitaClasse(attivitaClasseService.getById(sId, id));
			}
			
			id = r.getArpeAttivitaId();
			if(Objects.nonNull(id)) {
				r.setArpeAttivita(arpeAttivitaService.getById(sId, id));
			}
			
			id = r.getArpeDisciplinaId();
			if(Objects.nonNull(id)) {
				r.setArpeDisciplina(arpeDisciplinaService.getById(sId, id));
			}
			
			id = r.getArpeStrutturaTipoId();
			if(Objects.nonNull(id)) {
				r.setArpeStrutturaTipo(arpeStrutturaTipoService.getById(sId, id));
			}
		}
		return res;
	}

	
    private boolean isInsertable(Integer sId, Date inField) throws Exception {   	
		var methodName = "isInsertable";
		logDebug(sId,methodName, LogMessageEnum.BEGIN.getMessage());

		boolean insertable = false;
		try {
			if(inField != null) {
				insertable = true;
			}
		} finally {
			logDebug(sId,methodName, LogMessageEnum.END.getMessage());			
		}
        return insertable;
    }
	
	

}
