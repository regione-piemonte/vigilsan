/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsanpratiche;

import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelAppuntamentoSoggettoEsteso;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelAppuntamentoStato;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelAppuntamentoTipo;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelChecklistReqEsteso;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelClreqEsito;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelPraticaDettaglioClreqEsteso;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelPraticaEstesa;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelPraticaRidottaEstesa;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelPraticaScadenza;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelPraticaStato;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelPraticaTipo;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelPrescrizioneStato;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelPrescrizioneTipo;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelRuoloAppuntamentoSoggetto;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelStrutturaTipo;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.impl.ApiUtils;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.impl.GetListApiEnumInterface;

public enum VigilsanPraticheGetListApiEnum implements GetListApiEnumInterface {

	GET_APPUNTAMENTO_STATO_LISTA("/api/appuntamento/stato/lista", ModelAppuntamentoStato.class),
	GET_APPUNTAMENTO_TIPO_LISTA("/api/appuntamento/tipo/lista", ModelAppuntamentoTipo.class),
	GET_PRATICA_STATO_LISTA("/api/pratica/stato/lista", ModelPraticaStato.class),
	GET_PRATICA_TIPO_LISTA("/api/pratica/tipo/lista", ModelPraticaTipo.class),
	GET_PRATICA_INSERIBILI_LISTA("/api/pratica/inseribili/lista", ModelPraticaRidottaEstesa.class),
	GET_PRESCRIZIONE_STATO_LISTA("/api/prescrizione/stato/lista", ModelPrescrizioneStato.class),
	GET_PRESCRIZIONE_TIPO_LISTA("/api/prescrizione/tipo/lista", ModelPrescrizioneTipo.class),
	GET_NUOVAPRATICA_PRAICA_TIPO_LISTA("/api/nuovapratica/pratica/tipo/lista", ModelPraticaTipo.class),
	GET_PRATICA_LISTA("/api/pratica/lista", ModelPraticaEstesa.class),
	GET_NUOVAPRATICA_STRUTTURA_TIPO_LISTA("/api/nuovapratica/struttura/tipo/lista", ModelStrutturaTipo.class),
	GET_PRATICA_DETTAGLIO_SCADENZE("/api/pratica/dettaglio/scadenze", ModelPraticaScadenza.class),
	GET_APPUNTAMENTO_SOGGETTO_LISTA("/api/appuntamento/soggetto/lista",ModelAppuntamentoSoggettoEsteso.class),
	GET_APPUNTAMENTO_SOGGETTO_RUOLO_DECODIFICA_LISTA("/api/appuntamento/soggetto/ruolo/decodifica/lista",ModelRuoloAppuntamentoSoggetto.class),
	GET_PRATICA_REQUISITI_ESITO_DECODIFICA_LISTA("/api/pratica/requisiti/esito/lista",ModelClreqEsito.class),
	GET_PRATICA_REQUISITI_LISTA("/api/pratica/requisiti/lista",ModelChecklistReqEsteso.class),
	GET_PRATICA_DETTAGLIO_REQUISITI_LISTA("/api/pratica/dettaglio/requisiti/lista",ModelPraticaDettaglioClreqEsteso.class),
;

	private final String url;
	private final Class<?> clazz;

	private VigilsanPraticheGetListApiEnum(String url) {
		this.url = url;
		this.clazz = null;
	}

	private VigilsanPraticheGetListApiEnum(String url, Class<?> clazz) {
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
