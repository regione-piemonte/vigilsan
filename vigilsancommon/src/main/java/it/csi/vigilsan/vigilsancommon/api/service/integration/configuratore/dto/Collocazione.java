/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.service.integration.configuratore.dto;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Collocazione implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4002197226586202490L;
	@SerializedName("codice_collocazione")
	private String codiceCollocazione;
	@SerializedName("descrizione_collocazione")
	private String descrizioneCollocazione;
	@SerializedName("codice_azienda")
	private String codiceAzienda;
	@SerializedName("descrizione_azienda")
	private String descrizioneAzienda;
	public String getCodiceCollocazione() {
		return codiceCollocazione;
	}
	public void setCodiceCollocazione(String codiceCollocazione) {
		this.codiceCollocazione = codiceCollocazione;
	}
	public String getDescrizioneCollocazione() {
		return descrizioneCollocazione;
	}
	public void setDescrizioneCollocazione(String descrizioneCollocazione) {
		this.descrizioneCollocazione = descrizioneCollocazione;
	}
	public String getCodiceAzienda() {
		return codiceAzienda;
	}
	public void setCodiceAzienda(String codiceAzienda) {
		this.codiceAzienda = codiceAzienda;
	}
	public String getDescrizioneAzienda() {
		return descrizioneAzienda;
	}
	public void setDescrizioneAzienda(String descrizioneAzienda) {
		this.descrizioneAzienda = descrizioneAzienda;
	}
	@Override
	public String toString() {
		return "Collocazione [codiceCollocazione=" + codiceCollocazione + ", descrizioneCollocazione="
				+ descrizioneCollocazione + ", codiceAzienda=" + codiceAzienda + ", descrizioneAzienda="
				+ descrizioneAzienda + "]";
	}
	
}
