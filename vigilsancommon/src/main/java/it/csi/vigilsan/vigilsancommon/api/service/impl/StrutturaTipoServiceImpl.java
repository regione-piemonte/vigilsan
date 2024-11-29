/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl.StrutturaTipoDao;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.StrutturaTipo;
import it.csi.vigilsan.vigilsancommon.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableApiServiceImpl;

@Service
public class StrutturaTipoServiceImpl extends AuditableApiServiceImpl  {

	@Autowired
	private StrutturaTipoDao dao;
	
	public StrutturaTipo getById(Integer sId,Integer id) {
		try {
			return dao.get(id);
		} catch (EmptyResultDataAccessException e) {
			logError(sId,"getById", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.STRUTTURA_TIPO_ID_NON_TROVATO);
		}
	}
}
