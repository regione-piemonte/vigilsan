/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.model;

import java.util.Date;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.model.GenericColumntInterface;

public interface StruttraInterface extends GenericColumntInterface {

	/**
	   **/

	Integer getStrutturaId();

	void setStrutturaId(Integer strutturaId);

	/**
	   **/

	String getStrutturaCod();

	void setStrutturaCod(String strutturaCod);

	/**
	   **/

	String getStrutturaCodConfiguratore();

	void setStrutturaCodConfiguratore(String strutturaCodConfiguratore);

	/**
	   **/

	String getStrutturaCodArpe();

	void setStrutturaCodArpe(String strutturaCodArpe);

	/**
	   **/

	String getStrutturaDesc();

	void setStrutturaDesc(String strutturaDesc);

	/**
	   **/

	Integer getStrutturaTipoId();

	void setStrutturaTipoId(Integer strutturaTipoId);

	/**
	   **/

	Integer getComuneId();

	void setComuneId(Integer comuneId);

	/**
	   **/

	String getIndirizzo();

	void setIndirizzo(String indirizzo);

	/**
	   **/

	String getCap();

	void setCap(String cap);

	/**
	   **/

	String getCoordSrid();

	void setCoordSrid(String coordSrid);

	/**
	   **/

	String getCoordX();

	void setCoordX(String coordX);

	/**
	   **/

	String getCoordY();

	void setCoordY(String coordY);

	/**
	   **/

	Integer getStrutturaNaturaId();

	void setStrutturaNaturaId(Integer strutturaNaturaId);

	/**
	   **/

	Integer getStrutturaAccreditamentoId();

	void setStrutturaAccreditamentoId(Integer strutturaAccreditamentoId);

	/**
	   **/

	Integer getStrutturaTitolaritaId();

	void setStrutturaTitolaritaId(Integer strutturaTitolaritaId);

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