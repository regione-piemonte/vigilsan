/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.model;

import java.util.Date;

public class Scadenza {

	private Integer praticaDetId;
	private Integer praticaId;
	private Integer prescrizioneId;
	private Integer appuntamentoId;
	private Integer azioneId;
	private Date dataoraOrigine;
	private Date dataoraAzione;
	private Integer moduloCompilatoId;
	private String note;
	private Integer profiloId;
	private Integer profiloIdScadenza;
	private String flgScadenza;
	private Date validitaInizio;
	private Date validitaFine;
	private Date dataCreazione;
	private Date dataModifica;
	private Date dataCancellazione;
	private String utenteCreazione;
	private String utenteModifica;
	private String utenteCancellazione;
	private String updateStatus;
	public Integer getPraticaDetId() {
		return praticaDetId;
	}
	public void setPraticaDetId(Integer praticaDetId) {
		this.praticaDetId = praticaDetId;
	}
	public Integer getPraticaId() {
		return praticaId;
	}
	public void setPraticaId(Integer praticaId) {
		this.praticaId = praticaId;
	}
	public Integer getPrescrizioneId() {
		return prescrizioneId;
	}
	public void setPrescrizioneId(Integer prescrizioneId) {
		this.prescrizioneId = prescrizioneId;
	}
	public Integer getAzioneId() {
		return azioneId;
	}
	public void setAzioneId(Integer azioneId) {
		this.azioneId = azioneId;
	}
	public Date getDataoraOrigine() {
		return dataoraOrigine;
	}
	public void setDataoraOrigine(Date dataoraOrigine) {
		this.dataoraOrigine = dataoraOrigine;
	}
	public Date getDataoraAzione() {
		return dataoraAzione;
	}
	public void setDataoraAzione(Date dataoraAzione) {
		this.dataoraAzione = dataoraAzione;
	}
	public Integer getModuloCompilatoId() {
		return moduloCompilatoId;
	}
	public void setModuloCompilatoId(Integer moduloCompilatoId) {
		this.moduloCompilatoId = moduloCompilatoId;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Integer getProfiloId() {
		return profiloId;
	}
	public void setProfiloId(Integer profiloId) {
		this.profiloId = profiloId;
	}
	public Integer getProfiloIdScadenza() {
		return profiloIdScadenza;
	}
	public void setProfiloIdScadenza(Integer profiloIdScadenza) {
		this.profiloIdScadenza = profiloIdScadenza;
	}
	public String getFlgScadenza() {
		return flgScadenza;
	}
	public void setFlgScadenza(String flgScadenza) {
		this.flgScadenza = flgScadenza;
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
	public String getUpdateStatus() {
		return updateStatus;
	}
	public void setUpdateStatus(String updateStatus) {
		this.updateStatus = updateStatus;
	}
	public Integer getAppuntamentoId() {
		return appuntamentoId;
	}
	public void setAppuntamentoId(Integer appuntamentoId) {
		this.appuntamentoId = appuntamentoId;
	}
}
