/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.model;

public class AppuntamentoForPost extends Appuntamento implements AppuntamentoInterface {
	private String updateStatus;
	private Integer statoId;
	private Integer praDetIdApertura;
	private Integer praDetIdChiusura;

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

	public Integer getPraDetIdApertura() {
		return praDetIdApertura;
	}

	public void setPraDetIdApertura(Integer praDetIdApertura) {
		this.praDetIdApertura = praDetIdApertura;
	}

	public Integer getPraDetIdChiusura() {
		return praDetIdChiusura;
	}

	public void setPraDetIdChiusura(Integer praDetIdChiusura) {
		this.praDetIdChiusura = praDetIdChiusura;
	}
}
