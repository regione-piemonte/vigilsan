/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.model;

import java.util.Date;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.model.GenericColumntInterface;

public class Profilo implements GenericColumntInterface {

	private Date validitaInizio;
	private Date validitaFine;
	private Date dataCreazione;
	private Date dataModifica;
	private Date dataCancellazione;
	private String utenteCreazione;
	private String utenteModifica;
	private String utenteCancellazione;
	private Integer profiloId;
	private String profiloCod;
	private String profiloDesc;

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

	public String getProfiloCod() {
		return profiloCod;
	}

	public void setProfiloCod(String profiloCod) {
		this.profiloCod = profiloCod;
	}

	public Integer getProfiloId() {
		return profiloId;
	}

	public void setProfiloId(Integer profiloId) {
		this.profiloId = profiloId;
	}

	public String getProfiloDesc() {
		return profiloDesc;
	}

	public void setProfiloDesc(String profiloDesc) {
		this.profiloDesc = profiloDesc;
	}

}
