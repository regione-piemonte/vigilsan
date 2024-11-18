/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.model;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.model.GenericColumntInterface;

public interface ChecklistReqInterface extends GenericColumntInterface {
	public Integer getClreqId();

	public void setClreqId(Integer clreqId);

	/**
	 **/
	public String getClreqCod();

	public void setClreqCod(String clreqCod);

	/**
	 **/

	public String getClreqDesc();

	public void setClreqDesc(String clreqDesc);

	/**
	 **/

	public String getClreqOrd();

	public void setClreqOrd(String clreqOrd);

	/**
	 **/

	public String getClreqHint();

	public void setClreqHint(String clreqHint);

	/**
	 **/
	public Integer getClreqIdPadre();

	public void setClreqIdPadre(Integer clreqIdPadre);

	/**
	 **/

	public Integer getModuloId();

	public void setModuloId(Integer moduloId);
}