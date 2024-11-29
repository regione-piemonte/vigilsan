/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.model;

import java.util.Date;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.model.GenericColumntInterface;

public class Ruolo implements GenericColumntInterface {

	private Date validitaInizio;
	private Date validitaFine;
	private Date dataCreazione;
	private Date dataModifica;
	private Date dataCancellazione;
	private String utenteCreazione;
	private String utenteModifica;
	private String utenteCancellazione;
	private Integer ruoloId;
	private String ruoloCod;
	private String ruoloDesc;

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

	public Integer getRuoloId() {
		return ruoloId;
	}

	public void setRuoloId(Integer ruoloId) {
		this.ruoloId = ruoloId;
	}

	public String getRuoloCod() {
		return ruoloCod;
	}

	public void setRuoloCod(String ruoloCod) {
		this.ruoloCod = ruoloCod;
	}

	public String getRuoloDesc() {
		return ruoloDesc;
	}

	public void setRuoloDesc(String ruoloDesc) {
		this.ruoloDesc = ruoloDesc;
	}
}
