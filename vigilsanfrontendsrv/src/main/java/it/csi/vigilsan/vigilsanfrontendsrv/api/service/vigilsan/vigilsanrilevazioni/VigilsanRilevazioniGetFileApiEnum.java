/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsanrilevazioni;

import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.impl.GetFileApiEnumInterface;

public enum VigilsanRilevazioniGetFileApiEnum implements GetFileApiEnumInterface {

	GET_RILEVAZIONE_COMPILATA_LISTA_CSV("/api/rilevazione/compilata/lista/csv"),
	;

	private final String url;

	private VigilsanRilevazioniGetFileApiEnum(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
}
