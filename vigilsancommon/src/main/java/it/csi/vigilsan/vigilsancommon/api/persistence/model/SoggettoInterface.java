/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.model;

import java.util.Date;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.model.GenericColumntInterface;

public interface SoggettoInterface extends GenericColumntInterface {

	Integer getSoggettoId();

	void setSoggettoId(Integer soggettoId);

	String getCodiceFiscale();

	void setCodiceFiscale(String codiceFiscale);

	String getNome();

	void setNome(String nome);

	String getCognome();

	void setCognome(String cognome);

	Date getDataNascita();

	void setDataNascita(Date dataNascita);

	Boolean isPresenteConfiguratore();

	void setPresenteConfiguratore(Boolean presenteConfiguratore);

}