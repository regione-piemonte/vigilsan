/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.util;

public enum RuoloEnteStrutturaCodEnum {

	ASL_TERR("ASL_TERR", ProfiloCodEnum.VIGIL_ASL),
	ENTE_VIGIL("ENTE_VIGIL", ProfiloCodEnum.VIGIL_ENTE);

	private final String code;
	private ProfiloCodEnum profilo;

	private RuoloEnteStrutturaCodEnum(String code, ProfiloCodEnum profilo) {
		this.code = code;
		this.setProfilo(profilo);
	}

	public String getCode() {
		return code;
	}

	public ProfiloCodEnum getProfilo() {
		return profilo;
	}

	public void setProfilo(ProfiloCodEnum profilo) {
		this.profilo = profilo;
	}

}