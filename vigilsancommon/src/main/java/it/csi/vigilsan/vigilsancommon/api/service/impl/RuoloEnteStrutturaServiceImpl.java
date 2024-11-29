/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl.RuoloEnteStrutturaDao;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.RuoloEnteStruttura;
import it.csi.vigilsan.vigilsancommon.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;

@Service
public class RuoloEnteStrutturaServiceImpl {

	@Autowired
	private RuoloEnteStrutturaDao ruoloEnteStrutturaDao;

	public RuoloEnteStruttura getRuoloEnteStrutturaCodByRuoloCod(Integer sId, String ruoloEnteStrutturaCod, String inUtenteCreazione) {
		try {
			return ruoloEnteStrutturaDao.getCod(ruoloEnteStrutturaCod);
		} catch (EmptyResultDataAccessException e) {
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.RUOLO_ENTE_STRUTTURA_NON_TROVATO);
		}
	}
}
