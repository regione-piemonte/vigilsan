/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.service.impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import it.csi.vigilsan.vigilsancommon.api.generated.dto.ModelComuneEsteso;
import it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl.ComuneDao;
import it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl.ProvinciaDao;
import it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl.RegioneDao;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.Comune;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.ComuneEsteso;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.ProvinciaEsteso;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.Regione;
import it.csi.vigilsan.vigilsancommon.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableWPersistenceApiServiceImpl;
import jakarta.validation.constraints.NotNull;

@Service
public class ComuneServiceImpl extends AuditableWPersistenceApiServiceImpl {

	@Autowired
	private ComuneDao comuneDao;

	@Autowired
	private RegioneDao regioneDao;

	@Autowired
	private ProvinciaDao provinciaDao;

	public ModelComuneEsteso getComuneEstesoByComuneId(@NotNull Integer comuneId) {
		var res = getComuneEsteso(comuneId);
		if (Objects.nonNull(res.getProvinciaId())) {
			res.setProvincia(getProvinciaEstesaByProvinciaId(res.getProvinciaId()));
			Integer regioneId = res.getProvincia().getRegioneId();
			if (Objects.nonNull(regioneId)) {
				res.getProvincia().setRegione(getRegioneByRegioneId(regioneId));
			}
		}
		return res;
	}

	public Comune getComuneByComuneCod(String inComuneCod) {
		try {
			return comuneDao.getComuneByCod(inComuneCod);
		} catch (EmptyResultDataAccessException e) {
			logError(null,"getComuneByComuneCod", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.COMUNE_NON_TROVATO);
		}
	
	}

	private Regione getRegioneByRegioneId(Integer regioneId) {
		try {
			return regioneDao.get(regioneId);
		} catch (EmptyResultDataAccessException e) {
			logError(null,"getRegioneByRegioneId", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.REGIONE_NON_TROVATO);
		}
	
	}


	private ProvinciaEsteso getProvinciaEstesaByProvinciaId(Integer provinciaId) {
		try {
			return provinciaDao.get(provinciaId, ProvinciaEsteso.class);
		} catch (EmptyResultDataAccessException e) {
			logError(null,"getProvinciaEstesaByProvinciaId", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.PROVINCIA_NON_TROVATO);
		}
	}


	private ComuneEsteso getComuneEsteso(Integer comuneId) {
		try {
			return comuneDao.get(comuneId, ComuneEsteso.class);
		} catch (EmptyResultDataAccessException e) {
			logError(null,"getComuneEsteso", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.COMUNE_NON_TROVATO);
		}
	
	}
	
	
	
}
