/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsanpratiche;

import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelAppuntamentoSoggetto;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelPraticaClreqPost;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelPraticaDettaglio;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelPraticaDettaglioClreq;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelPraticaPost;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.impl.PostApiEnumInterface;

public enum VigilsanPratichePostApiEnum implements PostApiEnumInterface {

	POST_PRATICA("/api/pratica", ModelPraticaPost.class),
	POST_PRATICA_MODULO("/api/pratica/modulo", ModelPraticaPost.class),
	POST_PRATICA_DETTALGIO("/api/pratica/dettaglio", ModelPraticaDettaglio.class),
	POST_APPUNTAMENTO_SOGGETTO("/api/appuntamento/soggetto", ModelAppuntamentoSoggetto.class),
	POST_APPUNTAMENTO_SOGGETTO_DELETE("/api/appuntamento/soggetto/delete", ModelAppuntamentoSoggetto.class),
	POST_PRATICA_REQUISITI("/api/pratica/requisiti", ModelPraticaClreqPost.class),
	POST_PRATICA_DETTAGLIO_REQUISITI("/api/pratica/dettaglio/requisiti", ModelPraticaDettaglioClreq.class),
	POST_PRATICA_DETTAGLIO_REQUISITI_DELETE("/api/pratica/dettaglio/requisiti/delete", ModelPraticaDettaglioClreq.class),
	;

	private final String url;
	private final Class<?> clazz;

	private VigilsanPratichePostApiEnum(String url) {
		this.url = url;
		this.clazz = null;
	}

	VigilsanPratichePostApiEnum(String url, Class<?> clazz) {
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
