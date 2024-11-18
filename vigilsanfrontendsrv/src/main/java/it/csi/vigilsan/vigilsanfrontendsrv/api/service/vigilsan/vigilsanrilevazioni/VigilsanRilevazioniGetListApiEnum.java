/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsanrilevazioni;

import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelDocumentazioneEsteso;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelModuloConfigRidotto;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelParametro;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelRilevazioneEsteso;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.impl.ApiUtils;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.impl.GetListApiEnumInterface;

public enum VigilsanRilevazioniGetListApiEnum implements GetListApiEnumInterface {

	GET_RILEVAZIONE_COMPILATA_LISTA("/api/rilevazione/compilata/lista", ModelRilevazioneEsteso.class),
	GET_RILEVAZIONE_COMPILABILE_LISTA("/api/rilevazione/compilabile/lista", ModelRilevazioneEsteso.class),
	GET_RILEVAZIONE_MODULO_LISTA("/api/rilevazione/modulo/lista", ModelModuloConfigRidotto.class),
	GET_DOCUMENTAZIONE_MODULO_LISTA("/api/documentazione/modulo/lista", ModelModuloConfigRidotto.class),
	GET_DOCUMENTAZIONE_COMPILATA_LISTA("/api/documentazione/compilata/lista", ModelDocumentazioneEsteso.class),
	GET_DOCUMENTAZIONE_COMPILABILE_LISTA("/api/documentazione/compilabile/lista", ModelDocumentazioneEsteso.class),
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
