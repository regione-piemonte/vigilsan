/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.service;

import java.util.List;

import it.csi.vigilsan.vigilsancommon.api.persistence.model.Ente;

public interface EnteService {

	Ente getEnteByEnteCodConfiguratore(String enteCodConfiguratore);

	Ente getEnteById(Integer enteId);

	List<Ente> getAslByStrutturaId(Integer sId, Integer strutturaId);

	Ente getEnteAslByEnteCod(String enteCod);

	Object getEnteTipoById(Integer enteId);

}