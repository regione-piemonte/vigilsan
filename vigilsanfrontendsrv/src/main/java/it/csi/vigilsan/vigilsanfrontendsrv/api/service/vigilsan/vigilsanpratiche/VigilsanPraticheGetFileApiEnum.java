/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsanpratiche;

import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.impl.GetFileApiEnumInterface;

public enum VigilsanPraticheGetFileApiEnum implements GetFileApiEnumInterface {

	;

	private final String url;

	private VigilsanPraticheGetFileApiEnum(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
}
