/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl.EnteStrutturaDao;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.EnteStruttura;
import it.csi.vigilsan.vigilsancommon.api.service.EnteStrutturaService;
import it.csi.vigilsan.vigilsancommon.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsancommon.util.LogMessageEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTException;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableApiServiceImpl;

@Service
public class EnteStrutturaServiceImpl extends AuditableApiServiceImpl implements EnteStrutturaService  {


	@Autowired
	private EnteStrutturaDao enteStrutturaDao;

	
	@Override
	public EnteStruttura getEnteStrutturaById(EnteStruttura inEnteStruttura) {
		try {
			return enteStrutturaDao.getEnteStrutturaByStrutturaId(inEnteStruttura);
		} catch (EmptyResultDataAccessException e) {
			logError(null,"getEnteStrutturaById", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.ENTE_STRUTTURA_NON_TROVATA);
		}
	}
	
	// GESTIONE PER BATCH RPVIAR
	public void gestioneEnteStruttura(Integer sId, EnteStruttura inEnteStruttura, Date inDateBatch) throws Exception {
		var methodName = "gestioneEnteStruttura";
		logDebug(null,methodName, LogMessageEnum.BEGIN.getMessage());
	
		Integer enteStrId = null;
		// se l'ente struttura con ente id è diverso da quello presente creo una nuova entry e invalido quella presente
		try {

			// check su ente struttura (entei id posizione 0)
			logDebug(null, methodName, "ID ENTE : "+inEnteStruttura.getEnteId());
			logDebug(null, methodName, "ID STRUTTURA : "+inEnteStruttura.getStrutturaId());
			logDebug(null, methodName, "ID RUOLO ENTE STRUTTURA : "+inEnteStruttura.getRuoloEnteStrutturaId());
			EnteStruttura enteStrutturaFound = getEnteStrutturaById(inEnteStruttura);

			enteStrId = enteStrutturaFound.getEnteId();
			logDebug(null, methodName, "ID ENTE FOUND : "+enteStrutturaFound);
			
			// Check sull'id ente
			if(enteStrutturaFound.getEnteId() == inEnteStruttura.getEnteId()) {
				// Modifico la data di modifica
				enteStrutturaDao.refreshEnteStruttura(inDateBatch, inEnteStruttura);
			} else {
				EnteStruttura enteStrutturaInsert = new EnteStruttura();
				enteStrutturaInsert.setEnteId(Integer.valueOf(inEnteStruttura.getEnteId()));
				enteStrutturaInsert.setStrutturaId(inEnteStruttura.getStrutturaId());
				enteStrutturaInsert.setRuoloEnteStrutturaId(inEnteStruttura.getRuoloEnteStrutturaId());	
				enteStrutturaInsert.setUtenteCreazione(inEnteStruttura.getUtenteCreazione());	
							
				// Inserisco il record nuovo
				enteStrutturaDao.insert(enteStrutturaInsert);
				
				// Cancello il record vecchio
				enteStrutturaDao.deleteEnteStruttura(inDateBatch, inEnteStruttura);
			}
		} catch (RESTException restException) {
			logDebug(null, methodName, "Code error : "+restException.getCode());		
			
			// se l'ente struttura non è presente devo inserirla 
			if (ErrorCodeEnum.ENTE_STRUTTURA_NON_TROVATA.getCode().equals(restException.getCode())) {
				
				EnteStruttura enteStrutturaInsert = new EnteStruttura();
				enteStrutturaInsert.setEnteId(Integer.valueOf(inEnteStruttura.getEnteId()));
				enteStrutturaInsert.setStrutturaId(inEnteStruttura.getStrutturaId());
				enteStrutturaInsert.setRuoloEnteStrutturaId(inEnteStruttura.getRuoloEnteStrutturaId());	
				enteStrutturaInsert.setUtenteCreazione(inEnteStruttura.getUtenteCreazione());
				// Inserisco il record nuovo
				enteStrutturaDao.insert(enteStrutturaInsert);
			}
		} catch (Exception exception) {
			logDebug(null, methodName, "Eccezione generica per arpe attivita classe : "+exception.getMessage());		
			throw exception;
		} finally {
			logDebug(null,methodName, LogMessageEnum.END.getMessage());
		}
	}

	
}
