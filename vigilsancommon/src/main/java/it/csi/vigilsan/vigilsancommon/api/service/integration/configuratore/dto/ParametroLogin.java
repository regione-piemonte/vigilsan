/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.service.integration.configuratore.dto;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class ParametroLogin implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1799280703576307803L;

	@SerializedName("codice")
	private String codice;
	@SerializedName("valore")
	private String valore;
	
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public String getValore() {
		return valore;
	}
	public void setValore(String valore) {
		this.valore = valore;
	}
	@Override
	public String toString() {
		return "ParametroLogin [codice=" + codice + ", valore=" + valore + "]";
	}
	
}
