/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.model;

public class PraticaDettaglioForPost extends PraticaDettaglio implements PraticaDettaglioInterface {
	private String updateStatus;
	private Integer statoId;
	private String praticaNumero;
	private String appuntamentoNumero;
	private String prescrzioneNumero;

	public String getUpdateStatus() {
		return updateStatus;
	}

	public void setUpdateStatus(String updateStatus) {
		this.updateStatus = updateStatus;
	}

	public Integer getStatoId() {
		return statoId;
	}

	public void setStatoId(Integer statoId) {
		this.statoId = statoId;
	}

	public String getPraticaNumero() {
		return praticaNumero;
	}

	public void setPraticaNumero(String praticaNumero) {
		this.praticaNumero = praticaNumero;
	}

	public String getAppuntamentoNumero() {
		return appuntamentoNumero;
	}

	public void setAppuntamentoNumero(String appuntamentoNumero) {
		this.appuntamentoNumero = appuntamentoNumero;
	}

	public String getPrescrzioneNumero() {
		return prescrzioneNumero;
	}

	public void setPrescrzioneNumero(String prescrzioneNumero) {
		this.prescrzioneNumero = prescrzioneNumero;
	}

}
