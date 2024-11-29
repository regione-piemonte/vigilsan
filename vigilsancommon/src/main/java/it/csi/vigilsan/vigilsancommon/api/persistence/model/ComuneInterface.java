/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.model;

import java.util.Date;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.model.GenericColumntInterface;

public interface ComuneInterface extends GenericColumntInterface {

	/**
	   **/

	Integer getComuneId();

	void setComuneId(Integer comuneId);

	/**
	   **/

	String getComuneCod();

	void setComuneCod(String comuneCod);

	/**
	   **/

	String getComuneDesc();

	void setComuneDesc(String comuneDesc);

	/**
	   **/

	Integer getProvinciaId();

	void setProvinciaId(Integer provinciaId);

	/**
	   **/

	Date getValiditaInizio();

	void setValiditaInizio(Date validitaInizio);

	/**
	   **/

	Date getValiditaFine();

	void setValiditaFine(Date validitaFine);

	/**
	   **/

	Date getDataCreazione();

	void setDataCreazione(Date dataCreazione);

	/**
	   **/

	Date getDataModifica();

	void setDataModifica(Date dataModifica);

	/**
	   **/

	Date getDataCancellazione();

	void setDataCancellazione(Date dataCancellazione);

	/**
	   **/

	String getUtenteCreazione();

	void setUtenteCreazione(String utenteCreazione);

	/**
	   **/

	String getUtenteModifica();

	void setUtenteModifica(String utenteModifica);

	/**
	   **/

	String getUtenteCancellazione();

	void setUtenteCancellazione(String utenteCancellazione);


}