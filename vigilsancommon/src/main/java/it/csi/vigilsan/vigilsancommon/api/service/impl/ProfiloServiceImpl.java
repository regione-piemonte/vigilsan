/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl.ProfiloDao;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.Profilo;
import it.csi.vigilsan.vigilsancommon.api.persistence.repository.ProfiloRepository;
import it.csi.vigilsan.vigilsancommon.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;

@Service
public class ProfiloServiceImpl {

	@Autowired
	private ProfiloRepository profiloRepository;

	@Autowired
	private ProfiloDao profiloDao;
	
	public Profilo getProfiloByProfiloCod(String profiloCod) {
		try {
			return profiloRepository.getProfiloByProfiloCod(profiloCod);
		} catch (EmptyResultDataAccessException e) {
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.PROFILO_NON_TROVATO);
		}
	}
	
	public Profilo getProfiloByProfiloId(Integer profiloId) {
		try {
			return profiloDao.get(profiloId);
		} catch (EmptyResultDataAccessException e) {
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.PROFILO_NON_TROVATO);
		}
	}

	public List<Profilo> getProfilo() {
		try {
			return profiloDao.getAll();
		} catch (EmptyResultDataAccessException e) {
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.PROFILO_NON_TROVATO);
		}
	}
}