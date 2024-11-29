/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanbatchsrv.api.service.vigilsan.vigilsancommon;

import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.impl.PostApiEnumInterface;

public enum VigilsanCommonPostApiEnum implements PostApiEnumInterface {
	
	POST_UTENTE_LOGOUT("/api/utente/logout",String.class),
	POST_STRUTTURA("/api/batch/rpviar",String.class),	
	POST_INVIONOTIFYSAN("/api/batch/rpvini",String.class),	
	POST_CONTROLLONOTIFYSAN("/api/batch/rpvinv",String.class)	
	;

	private final String url;
	private final Class<?> clazz;

	private VigilsanCommonPostApiEnum(String url) {
		this.url = url;
		this.clazz = null;
	}

	VigilsanCommonPostApiEnum(String url, Class<?> clazz) {
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
