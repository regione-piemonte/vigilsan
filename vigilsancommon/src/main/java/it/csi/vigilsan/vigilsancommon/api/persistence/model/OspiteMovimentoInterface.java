/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.model;

import java.util.Date;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.model.GenericColumntInterface;

public interface OspiteMovimentoInterface extends GenericColumntInterface {

	/**
	   **/

	Integer getOspiteMovimentoId();

	void setOspiteMovimentoId(Integer ospiteMovimentoId);

	/**
	   **/

	Date getDataMovimento();

	void setDataMovimento(Date dataMovimento);

	/**
	   **/

	Integer getStrSoggId();

	void setStrSoggId(Integer strSoggId);

	/**
	   **/

	Integer getOspiteMovimentoTipoId();

	void setOspiteMovimentoTipoId(Integer ospiteMovimentoTipoId);

	/**
	   **/

	Integer getOspiteCondizioniId();

	void setOspiteCondizioniId(Integer ospiteCondizioniId);

	/**
	   **/

	Integer getStrutturaIdOd();

	void setStrutturaIdOd(Integer strutturaIdOd);

	/**
	   **/

	String getNote();

	void setNote(String note);

	public Integer getStrutturaCategoriaId();

	public void setStrutturaCategoriaId(Integer strutturaCategoriaId);

}