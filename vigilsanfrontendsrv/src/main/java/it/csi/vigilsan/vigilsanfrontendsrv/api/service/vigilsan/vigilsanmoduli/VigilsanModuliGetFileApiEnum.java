/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsanmoduli;

import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.impl.GetFileApiEnumInterface;

public enum VigilsanModuliGetFileApiEnum implements GetFileApiEnumInterface {

	GET_FILE("/api/file"), 
	GET_MODULO_PDF("/api/modulo/pdf"),
	;

	private final String url;

	private VigilsanModuliGetFileApiEnum(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
}
