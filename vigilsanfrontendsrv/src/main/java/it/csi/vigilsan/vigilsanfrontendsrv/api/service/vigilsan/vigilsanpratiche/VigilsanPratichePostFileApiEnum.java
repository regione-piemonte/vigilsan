/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsanpratiche;

import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelChiaveValoreList;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.impl.PostFileApiEnumInterface;

public enum VigilsanPratichePostFileApiEnum implements PostFileApiEnumInterface {

	GET_PRATICA_GENERA_DIARIO("/api/pratica/genera/diario", ModelChiaveValoreList.class), 
	;

	private final String url;
	private final Class<?> clazz;

	private VigilsanPratichePostFileApiEnum(String url) {
		this.url = url;
		this.clazz = null;
	}

	VigilsanPratichePostFileApiEnum(String url, Class<?> clazz) {
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
