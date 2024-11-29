/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.service;

import java.util.Date;

import it.csi.vigilsan.vigilsancommon.api.persistence.model.EnteStruttura;

public interface EnteStrutturaService {

	EnteStruttura getEnteStrutturaById(EnteStruttura inEnteStruttura);

	void gestioneEnteStruttura(Integer sId, EnteStruttura inEnteStruttura, Date inDateBatch) throws Exception;

}