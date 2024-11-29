/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.service;

import java.util.Date;
import java.util.List;

import it.csi.vigilsan.vigilsancommon.api.persistence.model.Struttura;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.StrutturaPaginata;
import it.csi.vigilsan.vigilsanutil.generic.util.paginazione.Paginazione;

public interface StrutturaService {

	Struttura getStrutturaByStrutturaCodArpe(Integer sId, String strutturaCodArpe);

	Struttura getStrutturaByStrutturaId(Integer sId, Integer strutturaId);

	Struttura getStrutturaByCodArpe(Integer sId, String inCodArpe);

	Struttura gestioneStruttura(Integer sId, Struttura inStrutturaToManage, String inStrutturaCod, String inUtente);

	void cancellaStrutture(Integer sId, Date inDataInizioElaborazione);

	Date getActualDate(Integer sId);

	void updateStrutturaByStrutturaId(Integer sId, Struttura strutturaId);

	void ricalcolaStruttura(Integer sId);

	Struttura getStrutturaByCodArpeValida(Integer sId, String inCodArpe);

	Object getStrutturaParametro(Integer sId, Integer strutturaId, String parametroCod);

	List<StrutturaPaginata> getStrutturaByEnteId(Integer sId, Integer enteId, String ruoloEnteStrutturaCod,
			String filter, Integer strutturaId, Paginazione paginazione);

}