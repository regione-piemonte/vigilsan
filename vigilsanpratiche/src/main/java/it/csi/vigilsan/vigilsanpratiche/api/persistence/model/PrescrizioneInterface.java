/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.model;

import java.util.Date;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.model.GenericColumntInterface;

public interface PrescrizioneInterface extends GenericColumntInterface {

	/**
	   **/

	Integer getPrescrizioneId();

	void setPrescrizioneId(Integer prescrizioneId);

	/**
	   **/

	Integer getPrescrizioneTipoId();

	void setPrescrizioneTipoId(Integer prescrizioneTipoId);

	/**
	   **/

	Integer getPraticaId();

	void setPraticaId(Integer praticaId);

	/**
	   **/

	Date getDataoraApertura();

	void setDataoraApertura(Date dataoraApertura);

	/**
	   **/

	Date getDataoraChiusura();

	void setDataoraChiusura(Date dataoraChiusura);

	String getPrescrizioneNumero();

	void setPrescrizioneNumero(String prescrizioneNumero);

}