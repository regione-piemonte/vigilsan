/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsanmoduli;

import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelFile;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelModuloPost;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.impl.PostApiEnumInterface;

public enum VigilsanModuliPostApiEnum implements PostApiEnumInterface {

	POST_MODULO("/api/modulo",ModelModuloPost.class),
	POST_FILE("/api/file",ModelFile.class)	
	;

	private final String url;
	private final Class<?> clazz;

	private VigilsanModuliPostApiEnum(String url) {
		this.url = url;
		this.clazz = null;
	}

	VigilsanModuliPostApiEnum(String url, Class<?> clazz) {
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
