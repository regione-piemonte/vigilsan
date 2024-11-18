/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsanmoduli;

import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelFileContentType;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.dto.ModelProfiloUtente;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.impl.ApiUtils;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.impl.GetListApiEnumInterface;

public enum VigilsanModuliGetListApiEnum implements GetListApiEnumInterface {

	GET_USER_AUTH("/api/utente/auth", ModelProfiloUtente.class), 
	GET_FILE_CONTENT_TYPE_LISTA("/api/file/content/type/lista", ModelFileContentType.class), 
	;

	private final String url;
	private final Class<? extends Object> clazz;

	private VigilsanModuliGetListApiEnum(String url) {
		this.url = url;
		this.clazz = null;
	}

	private VigilsanModuliGetListApiEnum(String url, Class<?> clazz) {
		this.url = url;
		this.clazz = ApiUtils.getistFromObject(clazz);
	}

	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public Class<? extends Object> getClazz() {
		return clazz;
	}

}
