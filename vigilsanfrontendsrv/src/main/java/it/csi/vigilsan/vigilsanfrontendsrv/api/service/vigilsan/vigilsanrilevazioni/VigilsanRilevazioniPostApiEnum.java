/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsanrilevazioni;

import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelChiaveValoreList;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelDocumentazione;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelRilevazione;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelVerificaDocumentazione;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.impl.PostApiEnumInterface;

public enum VigilsanRilevazioniPostApiEnum implements PostApiEnumInterface {

	POST_DOCUMENTAZIONE("/api/documentazione",ModelDocumentazione.class),
	POST_RILEVAZIONE("/api/rilevazione",ModelRilevazione.class),
	POST_DOCUMENTAZIONE_VERIFICA("/api/documentazione/verifica",ModelVerificaDocumentazione.class),
	POST_DOCUMENTAZIONE_PARAMETRO_LISTA("/api/documentazione/parametro", ModelChiaveValoreList.class),
	;

	private final String url;
	private final Class<?> clazz;

	private VigilsanRilevazioniPostApiEnum(String url) {
		this.url = url;
		this.clazz = null;
	}

	VigilsanRilevazioniPostApiEnum(String url, Class<?> clazz) {
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
