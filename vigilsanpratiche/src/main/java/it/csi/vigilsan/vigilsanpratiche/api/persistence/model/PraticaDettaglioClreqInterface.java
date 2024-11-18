/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.model;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.model.GenericColumntInterface;

public interface PraticaDettaglioClreqInterface extends GenericColumntInterface {
	
	public Integer getPraticaDetClreqId();

	public void setPraticaDetClreqId(Integer praticaDetClreqId);

	/**
	 **/

	public Integer getPraticaDetId();

	public void setPraticaDetId(Integer praticaDetId);

	/**
	 **/

	public Integer getClreqId();

	public void setClreqId(Integer clreqId);

	/**
	 **/

	public Integer getClreqEsitoId();

	public void setClreqEsitoId(Integer clreqEsitoId);

	/**
	 **/

	public Integer getModuloCompilatoId();

	public void setModuloCompilatoId(Integer moduloCompilatoId);

	/**
	 **/

	public String getNote();

	public void setNote(String note);
}