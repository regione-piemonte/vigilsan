/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.model;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.model.GenericColumntInterface;

public interface NotificaInterface extends GenericColumntInterface {

	public Integer getNotificaId();

	public void setNotificaId(Integer notificaId);

	/**
	 **/

	public Integer getProfiloId();

	public void setProfiloId(Integer profiloId);

	/**
	 **/

	public Integer getEnteId();

	public void setEnteId(Integer enteId);

	/**
	 **/

	public Integer getStrutturaId();

	public void setStrutturaId(Integer strutturaId);

	/**
	 **/

	public Integer getSoggettoId();

	public void setSoggettoId(Integer soggettoId);

	/**
	 **/

	public String getTitle();

	public void setTitle(String title);

	/**
	 **/

	public String getBodyTextShort();

	public void setBodyTextShort(String bodyTextShort);

	/**
	 **/

	public String getBodyTextLong();

	public void setBodyTextLong(String bodyTextLong);

	/**
	 **/

	public String getBodyHtml();

	public void setBodyHtml(String bodyHtml);

}