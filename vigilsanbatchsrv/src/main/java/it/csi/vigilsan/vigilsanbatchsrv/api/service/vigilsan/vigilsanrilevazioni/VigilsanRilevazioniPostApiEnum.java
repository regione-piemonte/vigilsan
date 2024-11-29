/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanbatchsrv.api.service.vigilsan.vigilsanrilevazioni;

import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.impl.PostApiEnumInterface;

public enum VigilsanRilevazioniPostApiEnum implements PostApiEnumInterface {

	POST_BATCH_ESTRAZIONE_RILEVAZIONI("/api/batch/estrazione/rilevazioni",String.class)
	;

	private final String url;
	private final Class<?> clazz;

	private VigilsanRilevazioniPostApiEnum(String url) {
		this.url = url;
		this.clazz = null;
	}

	VigilsanRilevazioniPostApiEnum(String url, Class<?> clazz) {
		this.url = url;
		this.clazz = clazz;
	}

	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public Class<?> getClazz() {
		return clazz;
	}

}
