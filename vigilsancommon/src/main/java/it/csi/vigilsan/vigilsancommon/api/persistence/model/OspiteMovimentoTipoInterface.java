/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.model;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.model.GenericColumntInterface;

public interface OspiteMovimentoTipoInterface extends GenericColumntInterface  {

	/**
	   **/

	Integer getOspiteMovimentoTipoId();

	void setOspiteMovimentoTipoId(Integer ospiteMovimentoTipoId);

	/**
	   **/

	String getOspiteMovimentoTipoCod();

	void setOspiteMovimentoTipoCod(String ospiteMovimentoTipoCod);

	/**
	   **/

	String getOspiteMovimentoTipoDesc();

	void setOspiteMovimentoTipoDesc(String ospiteMovimentoTipoDesc);

	/**
	   **/

	String getOspiteMovimentoTipoOrd();

	void setOspiteMovimentoTipoOrd(String ospiteMovimentoTipoOrd);

	/**
	   **/

	String getOspiteMovimentoTipoHint();

	void setOspiteMovimentoTipoHint(String ospiteMovimentoTipoHint);

	/**
	   **/

	Integer getOspiteStatoId();

	void setOspiteStatoId(Integer ospiteStatoId);

	String toString();

}