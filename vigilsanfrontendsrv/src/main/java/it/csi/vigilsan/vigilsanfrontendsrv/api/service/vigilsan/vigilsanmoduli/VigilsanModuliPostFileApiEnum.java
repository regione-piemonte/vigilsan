/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsanmoduli;

import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelZipLista;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.impl.PostFileApiEnumInterface;

public enum VigilsanModuliPostFileApiEnum implements PostFileApiEnumInterface {

	POST_ZIP("/api/zip", ModelZipLista.class),
	;

	private final String url;
	private final Class<?> clazz;

	private VigilsanModuliPostFileApiEnum(String url) {
		this.url = url;
		this.clazz = null;
	}

	VigilsanModuliPostFileApiEnum(String url, Class<?> clazz) {
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
