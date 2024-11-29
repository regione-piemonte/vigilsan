/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import it.csi.vigilsan.vigilsancommon.api.generated.dto.ModelStrutturaCategoria;
import it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl.StrutturaCategoriaDao;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.StrutturaCategoria;
import it.csi.vigilsan.vigilsancommon.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableApiServiceImpl;

@Service
public class StrutturaCategoriaServiceImpl extends AuditableApiServiceImpl  {

	@Autowired
	private StrutturaCategoriaDao dao;
	
	public List<StrutturaCategoria> getByStrutturaId(Integer sId,Integer id) {
		try {
			return dao.getByStrutturaId(id);
		} catch (EmptyResultDataAccessException e) {
			logError(sId,"getByStrutturaId", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.STRUTTURA_CATEGORIA_NON_TROVATO);
		}
	}
	
	public StrutturaCategoria getByStrCatId(Integer sId,Integer strCatId) {
		try {
			return dao.getByStrCatId(strCatId);
		} catch (EmptyResultDataAccessException e) {
			logError(sId,"getByStrCatId", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.STRUTTURA_CATEGORIA_NON_TROVATO);
		}
	}

	public ModelStrutturaCategoria getById(Integer sId, Integer strutturaCategoriaId) {
		try {
			return dao.get(strutturaCategoriaId);
		} catch (EmptyResultDataAccessException e) {
			logError(sId,"getById", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.STRUTTURA_CATEGORIA_NON_TROVATO);
		}
	}

}
