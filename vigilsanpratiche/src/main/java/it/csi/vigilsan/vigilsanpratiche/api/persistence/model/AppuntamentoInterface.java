/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.model;

import java.util.Date;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.model.GenericColumntInterface;

public interface AppuntamentoInterface extends GenericColumntInterface {

	/**
	   **/

	Integer getAppuntamentoId();

	void setAppuntamentoId(Integer appuntamentoId);

	/**
	   **/

	Integer getAppuntamentoTipoId();

	void setAppuntamentoTipoId(Integer appuntamentoTipoId);

	/**
	   **/

	Integer getPraticaId();

	void setPraticaId(Integer praticaId);

	/**
	   **/

	Date getDataoraInizio();

	void setDataoraInizio(Date dataoraInizio);

	/**
	   **/

	Date getDataoraFine();

	void setDataoraFine(Date dataoraFine);

	Date getDataoraApertura();

	void setDataoraApertura(Date dataoraApertura);

	Date getDataoraChiusura();

	void setDataoraChiusura(Date dataoraChiusura);

	String getAppuntamentoNumero();

	void setAppuntamentoNumero(String appuntamentoNumero);

}