/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.service.integration.configuratore.dto;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;



public class TokenInformazione implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6159438653261259760L;
	@SerializedName("richiedente")
	private Richiedente richiedente;
	@SerializedName("parametri_login")
	private List<ParametroLogin>parametriLogin;
	@SerializedName("funzionalita")
	private List<Funzionalita>funzionalita;
	public Richiedente getRichiedente() {
		return richiedente;
	}
	public void setRichiedente(Richiedente richiedente) {
		this.richiedente = richiedente;
	}
	public List<ParametroLogin> getParametriLogin() {
		return parametriLogin;
	}
	public void setParametriLogin(List<ParametroLogin> parametriLogin) {
		this.parametriLogin = parametriLogin;
	}
	public List<Funzionalita> getFunzionalita() {
		return funzionalita;
	}
	public void setFunzionalita(List<Funzionalita> funzionalita) {
		this.funzionalita = funzionalita;
	}
	@Override
	public String toString() {
		return "TokenInformazione [richiedente=" + richiedente + ", parametriLogin=" + parametriLogin
				+ ", funzionalita=" + funzionalita + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
	
}
