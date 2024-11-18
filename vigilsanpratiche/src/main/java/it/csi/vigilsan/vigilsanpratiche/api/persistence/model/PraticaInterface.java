/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.model.GenericColumntInterface;

public interface PraticaInterface extends GenericColumntInterface {

	/**
	   **/

	Integer getPraticaId();

	void setPraticaId(Integer praticaId);

	/**
	   **/

	Integer getEnteId();

	void setEnteId(Integer enteId);

	/**
	   **/

	Integer getStrutturaId();

	void setStrutturaId(Integer strutturaId);

	/**
	   **/

	Integer getPraticaTipoId();

	void setPraticaTipoId(Integer praticaTipoId);

	/**
	   **/

	Date getDataoraApertura();

	void setDataoraApertura(Date dataoraApertura);

	/**
	   **/

	Date getDataoraChiusura();

	void setDataoraChiusura(Date dataoraChiusura);

	String getPraticaNumero();

	void setPraticaNumero(String praticaNumero);

}