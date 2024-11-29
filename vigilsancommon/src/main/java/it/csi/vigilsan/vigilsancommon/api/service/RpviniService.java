/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.service;

import jakarta.ws.rs.core.HttpHeaders;

public interface RpviniService {

	void rpviniInviaNotifiche(Integer sId, String cfUtente, HttpHeaders httpHeaders) throws Exception;
}