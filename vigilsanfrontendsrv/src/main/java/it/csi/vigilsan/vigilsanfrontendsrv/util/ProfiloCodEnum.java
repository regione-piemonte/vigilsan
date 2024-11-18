/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.util;

import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;

public enum ProfiloCodEnum {

	VIGIL_RES("VIGIL_RES", null) {
		@Override
		public RuoloEnteStrutturaCodEnum getRuoloEnteStruttura() {
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.CHIAMATA_NON_PERMESSA_PER_PROFILO);
		}
	},
	VIGIL_ASL("VIGIL_ASL", RuoloEnteStrutturaCodEnum.ASL_TERR),
	VIGIL_ENTE("VIGIL_ENTE", RuoloEnteStrutturaCodEnum.ENTE_VIGIL), 
	VIGIL_REGIONE("VIGIL_REGIONE", null, null),
	VIGIL_CSI("VIGIL_CSI", null, null), 
	VIGIL_DIRMEI("VIGIL_DIRMEI", null, null);

	private final String code;
	private final RuoloEnteStrutturaCodEnum ruoloEnteStruttura;
	private final String conoVisiblita;

	private ProfiloCodEnum(String code, RuoloEnteStrutturaCodEnum ruoloEnteStruttura) {
		this.code = code;
		this.ruoloEnteStruttura = ruoloEnteStruttura;
		this.conoVisiblita = code;
	}

	private ProfiloCodEnum(String code, RuoloEnteStrutturaCodEnum ruoloEnteStruttura, String conoVisibilita) {
		this.code = code;
		this.ruoloEnteStruttura = ruoloEnteStruttura;
		this.conoVisiblita = conoVisibilita;
	}

	public String getCode() {
		return code;
	}

	public RuoloEnteStrutturaCodEnum getRuoloEnteStruttura() {
		return ruoloEnteStruttura;
	}

	public String getConoVisiblita() {
		return conoVisiblita;
	}

}