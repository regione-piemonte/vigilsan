/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.model;

import java.util.Date;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.model.GenericColumntInterface;

public interface ProvinciaInterface extends GenericColumntInterface {

	/**
	   **/

	Integer getProvinciaId();

	void setProvinciaId(Integer provinciaId);

	/**
	   **/

	String getProvinciaCod();

	void setProvinciaCod(String provinciaCod);

	/**
	   **/

	String getProvinciaSigla();

	void setProvinciaSigla(String provinciaSigla);

	/**
	   **/

	String getProvinciaDesc();

	void setProvinciaDesc(String provinciaDesc);

	/**
	   **/

	Integer getRegioneId();

	void setRegioneId(Integer regioneId);

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

	int hashCode();

}