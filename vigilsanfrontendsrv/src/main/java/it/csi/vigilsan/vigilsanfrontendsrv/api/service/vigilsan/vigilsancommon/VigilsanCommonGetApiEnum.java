/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon;

import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelApplicativo;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelComuneEsteso;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelEnte;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelEnteEsteso;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelEnteTipo;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelNatura;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelOspiteEsteso;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelParametro;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelProfilo;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelRuolo;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelSoggetto;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelStruttura;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelStrutturaAccreditamento;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelStrutturaCategoria;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelStrutturaEstesa;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelStrutturaSoggetto;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelStrutturaTipo;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelStrutturaTitolarita;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.dto.ModelProfiloUtente;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.impl.GetApiEnumInterface;

public enum VigilsanCommonGetApiEnum implements GetApiEnumInterface{

	GET_USER_AUTH("/api/utente/auth",ModelProfiloUtente.class),
	GET_ENTE("/api/ente",ModelEnte.class),
	GET_ENTE_TIPO("/api/ente/tipo",ModelEnteTipo.class),
	GET_ENTE_ESTESO("/api/ente",ModelEnteEsteso.class),
	GET_SOGGETTO("/api/soggetto",ModelSoggetto.class),
	GET_STRUTTURA("/api/struttura",ModelStruttura.class),
	GET_STRUTTURA_ESTESA("/api/struttura",ModelStrutturaEstesa.class),
	GET_STRUTTURA_NATURA("/api/struttura/natura",ModelNatura.class),
	GET_RUOLO("/api/ruolo",ModelRuolo.class),
	GET_APPLICATIVO("/api/applicativo",ModelApplicativo.class),
	GET_PROFILO("/api/profilo",ModelProfilo.class),
	GET_COMUNE_ESTESO("/api/comune/esteso",ModelComuneEsteso.class),
	GET_STRUTTURA_PARAMETRO("/api/struttura/parametro", ModelParametro.class), 
	GET_STRUTTURA_ACCREDITAMENTO("/api/struttura/accreditamento",ModelStrutturaAccreditamento.class),
	GET_STRUTTURA_TIPO("/api/struttura/tipo",ModelStrutturaTipo.class),
	GET_STRUTTURA_TITOLARITA("/api/struttura/titolarita",ModelStrutturaTitolarita.class),
	GET_STRUTTURA_CATEGORIA("/api/struttura/categoria", ModelStrutturaCategoria.class),
	GET_OSPITE("/api/soggetto", ModelOspiteEsteso.class), 
	GET_STRUTTURA_SOGGETTO("/api/struttura/soggetto", ModelStrutturaSoggetto.class), 
	;

	private final String url;
	private final Class<?> clazz;

	private VigilsanCommonGetApiEnum(String url) {
		this.url = url;
		this.clazz = null;
	}

	VigilsanCommonGetApiEnum(String url, Class<?> clazz) {
		this.url = url;
		this.clazz = clazz;
	}

	public String getUrl() {
		return url;
	}

	public Class<?> getClazz() {
		return clazz;
	}

}
