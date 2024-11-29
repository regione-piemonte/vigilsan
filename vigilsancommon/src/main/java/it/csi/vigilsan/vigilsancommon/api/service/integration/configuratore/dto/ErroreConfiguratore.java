/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.service.integration.configuratore.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ErroreConfiguratore implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3994048398643610049L;
	@SerializedName("status")
	private Integer status;
	@SerializedName("codice")
	private String codice;
	@SerializedName("descrizione")
	private String descrizione;

	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Override
	public String toString() {
		return "Errore [status=" + status + ", codice=" + codice + ", descrizione=" + descrizione + "]";
	}

}
