/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.model;

import java.util.Date;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.model.GenericColumntInterface;

public interface InvioNotificaInterface extends GenericColumntInterface {

	public Integer getInvioNotificaId();

	public void setInvioNotificaId(Integer invioNotificaId);

	/**
	 **/

	public Integer getNotificaId();

	public void setNotificaId(Integer notificaId);

	/**
	 **/

	public String getCfDestinatario();

	public void setCfDestinatario(String cfDestinatario);

	/**
	 **/

	public Date getDataoraInvio();

	public void setDataoraInvio(Date dataoraInvio);

	/**
	 **/

	public Boolean isEsitoInvio();

	public void setEsitoInvio(Boolean esitoInvio);

	/**
	 **/

	public String getErroreInvio();

	public void setErroreInvio(String erroreInvio);

	/**
	 **/

	public String getRequestContent();

	public void setRequestContent(String requestContent);

	/**
	 **/

	public String getResponseContent();

	public void setResponseContent(String responseContent);

	/**
	 **/

	public String getResponseResult();

	public void setResponseResult(String responseResult);
}