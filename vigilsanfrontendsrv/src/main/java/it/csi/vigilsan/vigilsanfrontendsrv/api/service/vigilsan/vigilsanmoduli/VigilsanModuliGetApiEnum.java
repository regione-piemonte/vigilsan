/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsanmoduli;

import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelFileTipo;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelModuloEsteso;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.impl.GetApiEnumInterface;

public enum VigilsanModuliGetApiEnum implements GetApiEnumInterface {

	GET_MODULO("/api/modulo",ModelModuloEsteso.class),
	GET_FILE_TIPO("/api/file/tipo",ModelFileTipo.class)

	;

	private final String url;
	private final Class<?> clazz;

	private VigilsanModuliGetApiEnum(String url) {
		this.url = url;
		this.clazz = null;
	}

	VigilsanModuliGetApiEnum(String url, Class<?> clazz) {
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
