/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.service;

import it.csi.vigilsan.vigilsancommon.api.persistence.model.StrutturaNatura;

public interface StrutturaNaturaService {

	StrutturaNatura getById(Integer sId, Integer strutturaNaturaId);
    
	StrutturaNatura getStrutturaNaturaByCodArpe(String inCodArpe) throws Exception ;
	
	void insertStrutturaNatura(StrutturaNatura inStrutturaNatura);

	// Servizi per il batch RPVIAR
	Integer gestioneStrutturaNatura(String inStrutturaNaturaCod, String inStrutturaNaturaDesc, String inUtenteCreazione) throws Exception;

}