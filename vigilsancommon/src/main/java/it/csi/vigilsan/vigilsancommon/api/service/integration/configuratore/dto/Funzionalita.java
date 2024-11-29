/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.service.integration.configuratore.dto;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Funzionalita implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1655652009459601374L;
	@SerializedName("codice")
	private String codice;
	@SerializedName("descrizione")
	private String descrizione;
	@SerializedName("codice_funzionalita_padre")
	private String codiceFunzionalitaPadre;
	@SerializedName("descrizione_funzionalita_padre")
	private String descrizioneFunzionalitaPadre;
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
	public String getCodiceFunzionalitaPadre() {
		return codiceFunzionalitaPadre;
	}
	public void setCodiceFunzionalitaPadre(String codiceFunzionalitaPadre) {
		this.codiceFunzionalitaPadre = codiceFunzionalitaPadre;
	}
	public String getDescrizioneFunzionalitaPadre() {
		return descrizioneFunzionalitaPadre;
	}
	public void setDescrizioneFunzionalitaPadre(String descrizioneFunzionalitaPadre) {
		this.descrizioneFunzionalitaPadre = descrizioneFunzionalitaPadre;
	}
	@Override
	public String toString() {
		return "Funzionalita [codice=" + codice + ", descrizione=" + descrizione + ", codiceFunzionalitaPadre="
				+ codiceFunzionalitaPadre + ", descrizioneFunzionalitaPadre=" + descrizioneFunzionalitaPadre + "]";
	}
	
}
