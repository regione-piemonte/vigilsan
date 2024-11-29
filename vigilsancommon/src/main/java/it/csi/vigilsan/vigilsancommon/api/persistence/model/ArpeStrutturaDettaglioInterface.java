/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.model.GenericColumntInterface;

public interface ArpeStrutturaDettaglioInterface extends GenericColumntInterface {

	/**
	   **/

	Integer getArpeStrDettId();

	void setArpeStrDettId(Integer arpeStrDettId);

	/**
	   **/

	Integer getStrutturaId();

	void setStrutturaId(Integer strutturaId);

	/**
	   **/

	Integer getArpeStrutturaTipoId();

	void setArpeStrutturaTipoId(Integer arpeStrutturaTipoId);

	/**
	   **/

	Integer getArpeAssistenzaTipoId();

	void setArpeAssistenzaTipoId(Integer arpeAssistenzaTipoId);

	/**
	   **/

	Integer getArpeAttivitaId();

	void setArpeAttivitaId(Integer arpeAttivitaId);

	/**
	   **/

	Integer getArpeAttivitaClasseId();

	void setArpeAttivitaClasseId(Integer arpeAttivitaClasseId);

	/**
	   **/

	Integer getArpeDisciplinaId();

	void setArpeDisciplinaId(Integer arpeDisciplinaId);
	
	/**
	   **/

    Date getArpeDataAttivazioneStruttura();

    void setArpeDataAttivazioneStruttura(Date arpeDataAttivazioneStruttura);

	/**
	   **/
  
	Date getArpeDataAttivazioneAssistenza();
	
    void setArpeDataAttivazioneAssistenza(Date arpeDataAttivazioneAssistenza);

	/**
	   **/
	Date getArpeDataAttivazioneAttivita();
	
	void setArpeDataAttivazioneAttivita(Date arpeDataAttivazioneAttivita);

}