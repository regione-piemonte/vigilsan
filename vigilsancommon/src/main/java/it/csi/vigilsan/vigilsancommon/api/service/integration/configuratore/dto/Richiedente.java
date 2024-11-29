/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.service.integration.configuratore.dto;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Richiedente  implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = -6709993511415924659L;
	@SerializedName("nome")
	private String nome;
	@SerializedName("cognome")
	private String cognome;
	@SerializedName("codice_fiscale")
	private String codiceFiscale;
	@SerializedName("ruolo")
	private String ruolo;
	@SerializedName("collocazione")
	private Collocazione collocazione;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public Collocazione getCollocazione() {
		return collocazione;
	}

	public void setCollocazione(Collocazione collocazione) {
		this.collocazione = collocazione;
	}

//	@Override
//	public String toString() {
//		return "Richiedente [nome=" + nome + ", cognome=" + cognome + ", codiceFiscale=" + codiceFiscale + ", ruolo="
//				+ ruolo + ", collocazione=" + collocazione + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
//				+ ", toString()=" + super.toString() + "]";
//	}
	  @Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class Richiedente {\n");
	    
	    sb.append("    nome: ").append(toIndentedStringGDPR(nome)).append("\n");
	    sb.append("    cognome: ").append(toIndentedStringGDPR(cognome)).append("\n");
	    sb.append("    codiceFiscale: ").append(toIndentedString(codiceFiscale)).append("\n");
	    sb.append("    ruolo: ").append(toIndentedString(ruolo)).append("\n");
	    sb.append("    collocazione: ").append(toIndentedString(collocazione)).append("\n");
	    sb.append("}");
	    return sb.toString();
	  }
	  private String toIndentedString(Object o) {
		    if (o == null) {
		      return "null";
		    }
		    return o.toString().replace("\n", "\n    ");
		  }
	  protected String toIndentedStringGDPR(Object o) {
		    if (o == null) {
		      return "null";
		    }
		    return "XXXXXX";
		  }
}
