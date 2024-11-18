/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon;

import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelEnteSoggetto;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelNotifica;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelOspiteMovimento;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelSoggetto;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.dto.ModelProfiloUtente;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.impl.PostApiEnumInterface;

public enum VigilsanCommonPostApiEnum implements PostApiEnumInterface {

	POST_UTENTE_LOGOUT("/api/utente/logout",String.class),
	POST_UTENTE_MODIFICA_SESSIONE("/api/utente/modifica/sessione", ModelProfiloUtente.class),
	POST_SOGGETTO("/api/soggetto",ModelSoggetto.class),
	POST_OSPITE_MOVIMENTO("/api/ospite/movimento", ModelOspiteMovimento.class),
	POST_OSPITE_MOVIMENTO_DELETE("/api/ospite/movimento/delete", ModelOspiteMovimento.class), 
	POST_ENTE_SOGGETTO("/api/ente/soggetto", ModelEnteSoggetto.class), 
	POST_ENTE_SOGGETTO_INVALIDA("/api/ente/soggetto/invalida", ModelEnteSoggetto.class),
	POST_ENTE_SOGGETTO_DELETE("/api/ente/soggetto/delete", ModelEnteSoggetto.class),
	POST_NOTIFICA("/api/notifica",ModelNotifica.class),
	;

	private final String url;
	private final Class<?> clazz;

	private VigilsanCommonPostApiEnum(String url) {
		this.url = url;
		this.clazz = null;
	}

	VigilsanCommonPostApiEnum(String url, Class<?> clazz) {
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
