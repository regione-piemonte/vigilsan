/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanbatchsrv.api.service.vigilsan.vigilsancommon;

import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.impl.GetApiEnumInterface;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.vigilsancommon.dto.ModelProfiloUtente;

public enum VigilsanCommonGetApiEnum implements GetApiEnumInterface{

	GET_SESSIONE_BATCH("/api/utente/sessione-batch",ModelProfiloUtente.class)
	;

	private final String url;
	private final Class<?> clazz;

	private VigilsanCommonGetApiEnum(String url) {
		this.url = url;
		this.clazz = null;
	}

	VigilsanCommonGetApiEnum(String url, Class<?> clazz) {
		this.url = url;
		this.clazz = clazz;
	}

	public String getUrl() {
		return url;
	}

	public Class<?> getClazz() {
		return clazz;
	}

}
