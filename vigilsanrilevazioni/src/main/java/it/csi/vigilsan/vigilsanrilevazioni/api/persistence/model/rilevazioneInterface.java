/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanrilevazioni.api.persistence.model;

import java.util.Date;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.model.GenericColumntInterface;

public interface rilevazioneInterface extends GenericColumntInterface {

	/**
	   **/

	Integer getRilevazioneId();

	void setRilevazioneId(Integer rilevazioneId);

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

	Date getDataoraRilevazione();

	void setDataoraRilevazione(Date dataoraRilevazione);

}