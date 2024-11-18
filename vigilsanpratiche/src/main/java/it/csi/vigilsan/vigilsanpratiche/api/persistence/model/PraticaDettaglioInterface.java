/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.model.GenericColumntInterface;

public interface PraticaDettaglioInterface extends GenericColumntInterface {

	/**
	   **/

	Integer getPraticaDetId();

	void setPraticaDetId(Integer praticaDetId);

	/**
	   **/

	Integer getPraticaId();

	void setPraticaId(Integer praticaId);

	/**
	   **/

	Integer getPraticaStatoId();

	void setPraticaStatoId(Integer praticaStatoId);

	/**
	   **/

	Integer getPrescrizioneId();

	void setPrescrizioneId(Integer prescrizioneId);

	/**
	   **/

	Integer getPrescrizioneStatoId();

	void setPrescrizioneStatoId(Integer prescrizioneStatoId);

	/**
	   **/

	Integer getAppuntamentoId();

	void setAppuntamentoId(Integer appuntamentoId);

	/**
	   **/

	Integer getAppuntamentoStatoId();

	void setAppuntamentoStatoId(Integer appuntamentoStatoId);

	/**
	   **/

	Integer getAzioneId();

	void setAzioneId(Integer azioneId);

	/**
	   **/

	Date getDataoraAzione();

	void setDataoraAzione(Date dataoraAzione);

	/**
	   **/

	Integer getModuloCompilatoId();

	void setModuloCompilatoId(Integer moduloCompilatoId);

	/**
	   **/

	String getNote();

	void setNote(String note);

	/**
	   **/

	Integer getProfiloId();

	void setProfiloId(Integer profiloId);

	String getFlgScadenza();

	void setFlgScadenza(String flgScadenza);

	/**
	 **/

	Integer getProfiloIdScadenza();

	void setProfiloIdScadenza(Integer profiloIdScadenza);
}