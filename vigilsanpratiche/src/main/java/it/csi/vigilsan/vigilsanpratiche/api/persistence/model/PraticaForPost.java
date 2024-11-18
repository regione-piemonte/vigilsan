/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.model;

public class PraticaForPost extends Pratica implements PraticaInterface {
	private String updateStatus;
	private Integer statoId;
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
}
