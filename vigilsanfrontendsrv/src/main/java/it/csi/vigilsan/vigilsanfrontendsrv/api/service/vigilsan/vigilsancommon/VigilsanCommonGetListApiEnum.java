/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon;

import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelApplicativoOperazioneRidottoCustom;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelArpeStrutturaDettaglioEsteso;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelEnte;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelEnteSoggettoEsteso;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelOspiteCondizioni;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelOspiteMovimentoEsteso;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelOspiteMovimentoTipoEsteso;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelOspiteStato;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelProfilo;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelRuoloEnteSoggetto;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelSoggettoEstesoLista;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelStruttura;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelStrutturaCategoria;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.dto.ModelProfiloUtente;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.impl.ApiUtils;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.impl.GetListApiEnumInterface;

public enum VigilsanCommonGetListApiEnum implements GetListApiEnumInterface {

	GET_USER_AUTH("/api/utente/auth", ModelProfiloUtente.class),
	GET_UTENTE_PERMESSI("/api/utente/permessi", ModelApplicativoOperazioneRidottoCustom.class),
	GET_STRUTTURA_ASL("/api/struttura/asl", ModelEnte.class),
	GET_STRUTTURA_CATEGORIA("/api/struttura/categoria/lista", ModelStrutturaCategoria.class), 
	GET_OSPITE_STATO_LISTA("/api/ospite/stato/lista", ModelOspiteStato.class), 
	GET_OSPITE_MOVIMENTO_TIPO_LISTA("/api/ospite/movimento/tipo/lista", ModelOspiteMovimentoTipoEsteso.class), 
	GET_OSPITE_MOVIMENTO_LISTA("/api/ospite/movimento/lista", ModelOspiteMovimentoEsteso.class), 
	GET_OSPITE_CONDIZIONI_LISTA("/api/ospite/condizioni/lista", ModelOspiteCondizioni.class), 
	GET_SOGGETTO_LISTA("/api/soggetto/lista", ModelSoggettoEstesoLista.class),
	GET_STRUTTURA_LISTA("/api/struttura/lista", ModelStruttura.class),
	GET_ARPE_STRUTTURA_DETTAGLIO_LISTA("/api/arpe/struttura/dettaglio", ModelArpeStrutturaDettaglioEsteso.class),
	GET_PROFILO_LISTA("/api/profilo/lista",ModelProfilo.class),
	GET_ENTE_STRUTTURA_LISTA("/api/ente/soggetto/lista",ModelEnteSoggettoEsteso.class),
	GET_ENTE_STRUTTURA_RUOLO_DECODIFICA_LISTA("/api/ente/soggetto/ruolo/decodifica/lista",ModelRuoloEnteSoggetto.class),
	
	;

	private final String url;
	private final Class<? extends Object> clazz;

	private VigilsanCommonGetListApiEnum(String url) {
		this.url = url;
		this.clazz = null;
	}

	private VigilsanCommonGetListApiEnum(String url, Class<?> clazz) {
		this.url = url;
		this.clazz = ApiUtils.getistFromObject(clazz);
	}

	public String getUrl() {
		return url;
	}

	public Class<? extends Object> getClazz() {
		return clazz;
	}

}
