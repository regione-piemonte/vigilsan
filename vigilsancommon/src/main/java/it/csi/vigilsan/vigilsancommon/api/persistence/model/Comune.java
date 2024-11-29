/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.model;

import java.util.Date;

public class Comune implements ComuneInterface {
	  private Integer comuneId = null;
	  private String comuneCod = null;
	  private String comuneDesc = null;
	  private Integer provinciaId = null;
	  private Date validitaInizio = null;
	  private Date validitaFine = null;
	  private Date dataCreazione = null;
	  private Date dataModifica = null;
	  private Date dataCancellazione = null;
	  private String utenteCreazione = null;
	  private String utenteModifica = null;
	  private String utenteCancellazione = null;
	  
	public Integer getComuneId() {
		return comuneId;
	}
	public void setComuneId(Integer comuneId) {
		this.comuneId = comuneId;
	}
	public String getComuneCod() {
		return comuneCod;
	}
	public void setComuneCod(String comuneCod) {
		this.comuneCod = comuneCod;
	}
	public String getComuneDesc() {
		return comuneDesc;
	}
	public void setComuneDesc(String comuneDesc) {
		this.comuneDesc = comuneDesc;
	}
	public Integer getProvinciaId() {
		return provinciaId;
	}
	public void setProvinciaId(Integer provinciaId) {
		this.provinciaId = provinciaId;
	}
	public Date getValiditaInizio() {
		return validitaInizio;
	}
	public void setValiditaInizio(Date validitaInizio) {
		this.validitaInizio = validitaInizio;
	}
	public Date getValiditaFine() {
		return validitaFine;
	}
	public void setValiditaFine(Date validitaFine) {
		this.validitaFine = validitaFine;
	}
	public Date getDataCreazione() {
		return dataCreazione;
	}
	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}
	public Date getDataModifica() {
		return dataModifica;
	}
	public void setDataModifica(Date dataModifica) {
		this.dataModifica = dataModifica;
	}
	public Date getDataCancellazione() {
		return dataCancellazione;
	}
	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}
	public String getUtenteCreazione() {
		return utenteCreazione;
	}
	public void setUtenteCreazione(String utenteCreazione) {
		this.utenteCreazione = utenteCreazione;
	}
	public String getUtenteModifica() {
		return utenteModifica;
	}
	public void setUtenteModifica(String utenteModifica) {
		this.utenteModifica = utenteModifica;
	}
	public String getUtenteCancellazione() {
		return utenteCancellazione;
	}
	public void setUtenteCancellazione(String utenteCancellazione) {
		this.utenteCancellazione = utenteCancellazione;
	}
	  
}
