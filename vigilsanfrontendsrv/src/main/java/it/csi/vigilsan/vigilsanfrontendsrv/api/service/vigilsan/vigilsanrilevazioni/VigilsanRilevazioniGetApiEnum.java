/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsanrilevazioni;

import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelModulo;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelParametro;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.impl.GetApiEnumInterface;

public enum VigilsanRilevazioniGetApiEnum implements GetApiEnumInterface {

	GET_MODULO("/api/modulo",ModelModulo.class),
	GET_DOCUMENTAZIONE_PARAMETRO("/api/documentazione/parametro", ModelParametro.class), 
	;

	private final String url;
	private final Class<?> clazz;

	private VigilsanRilevazioniGetApiEnum(String url) {
		this.url = url;
		this.clazz = null;
	}

	VigilsanRilevazioniGetApiEnum(String url, Class<?> clazz) {
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
