/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanrilevazioni.api.persistence.model;

import java.util.Date;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.model.GenericColumntInterface;

public interface DocumentazioneInterface extends GenericColumntInterface {

	/**
	   **/

	Integer getDocumentazioneId();

	void setDocumentazioneId(Integer documentazioneId);

	/**
	   **/

	Integer getStrutturaId();

	void setStrutturaId(Integer strutturaId);

	/**
	   **/

	Integer getStrCatId();

	void setStrCatId(Integer strCatId);

	/**
	   **/

	Integer getModuloCompilatoId();

	void setModuloCompilatoId(Integer moduloCompilatoId);

	/**
	   **/

	Integer getModuloConfigId();

	void setModuloConfigId(Integer moduloConfigId);

	/**
	   **/

	Date getDataoraDocumentazione();

	void setDataoraDocumentazione(Date dataoraDocumentazione);

	/**
	   **/

	Date getDataoraScadenza();

	void setDataoraScadenza(Date dataoraScadenza);

	/**
	   **/

	Integer getOccorrenza();

	void setOccorrenza(Integer occorrenza);

}