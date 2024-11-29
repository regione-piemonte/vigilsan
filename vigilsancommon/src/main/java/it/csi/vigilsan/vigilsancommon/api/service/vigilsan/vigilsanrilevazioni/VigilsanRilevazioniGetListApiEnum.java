/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.service.vigilsan.vigilsanrilevazioni;

import it.csi.vigilsan.vigilsancommon.api.generated.dto.ModelParametro;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.impl.ApiUtils;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.impl.GetListApiEnumInterface;

public enum VigilsanRilevazioniGetListApiEnum implements GetListApiEnumInterface {

	GET_DOCUMENTAZIONE_PARAMETRO_LISTA("/api/documentazione/parametro/lista", ModelParametro.class), 
	;

	private final String url;
	private final Class<?> clazz;

	private VigilsanRilevazioniGetListApiEnum(String url) {
		this.url = url;
		this.clazz = null;
	}

	private VigilsanRilevazioniGetListApiEnum(String url, Class<?> clazz) {
		this.url = url;
		this.clazz = ApiUtils.getistFromObject(clazz);
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
