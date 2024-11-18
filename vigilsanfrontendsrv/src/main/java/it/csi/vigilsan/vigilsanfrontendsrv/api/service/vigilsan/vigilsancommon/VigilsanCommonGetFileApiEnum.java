/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon;

import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.impl.GetFileApiEnumInterface;

public enum VigilsanCommonGetFileApiEnum implements GetFileApiEnumInterface {

	GET_SOGGETTO_LISTA_CSV("/api/soggetto/lista/csv"),
	;

	private final String url;

	private VigilsanCommonGetFileApiEnum(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
}
