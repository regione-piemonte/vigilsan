/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl.EnteDao;
import it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl.EnteTipoDao;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.Ente;
import it.csi.vigilsan.vigilsancommon.api.persistence.repository.EnteRepository;
import it.csi.vigilsan.vigilsancommon.api.service.EnteService;
import it.csi.vigilsan.vigilsancommon.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableApiServiceImpl;

@Service
public class EnteServiceImpl extends AuditableApiServiceImpl implements EnteService {

	@Autowired
	private EnteRepository enteRepository;

	@Autowired
	private EnteDao enteDao;
	
	@Autowired
	private EnteTipoDao enteTipoDao;

	@Override
	public Ente getEnteByEnteCodConfiguratore(String enteCodConfiguratore) {
		try {
			return enteRepository.getByEnteCodConfiguratore(enteCodConfiguratore);
		} catch (EmptyResultDataAccessException e) {
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.ENTE_NON_TROVATO);
		}
	}

	@Override
	public Ente getEnteById(Integer enteId) {
		try {
			return enteDao.get(enteId);
		} catch (EmptyResultDataAccessException e) {
			logError(null, "getEnteById", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.ENTE_NON_TROVATO);
		}
	}

	@Override
	public List<Ente> getAslByStrutturaId(Integer sId, Integer strutturaId) {
		try {
			return enteDao.getEnteAslByStrutturaId(strutturaId);
		} catch (EmptyResultDataAccessException e) {
			logError(sId, "getAslByStrutturaId", e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public Ente getEnteAslByEnteCod(String enteCod) {
		try {
			return enteDao.getEnteAslByEnteCod(enteCod);
		} catch (EmptyResultDataAccessException e) {
			logError(null, "getEnteAslByEnteCod", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.ENTE_NON_TROVATO);
		}
	}

	@Override
	public Object getEnteTipoById(Integer enteTipoId) {
		try {
			return enteTipoDao.get(enteTipoId);
		} catch (EmptyResultDataAccessException e) {
			logError(null, "getEnteTipoById", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.ENTE_NON_TROVATO);
		}
	}

}
