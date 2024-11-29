/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.service;

public interface RpvinvService {

	void rpvinvVerificaNotifiche(Integer sId, String cfUtente) throws Exception;
}