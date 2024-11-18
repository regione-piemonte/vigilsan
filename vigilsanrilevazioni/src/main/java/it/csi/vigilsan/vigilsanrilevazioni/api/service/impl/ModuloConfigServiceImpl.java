/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanrilevazioni.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.repository.impl.ModuloConfigRepository;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableWPersistenceApiServiceImpl;

@Service
public class ModuloConfigServiceImpl extends AuditableWPersistenceApiServiceImpl {

	@Autowired
	private ModuloConfigRepository moduloConfigRepository;
	


	public Boolean existModuloConfig(Integer sId, Integer moduloConfigId) {
		final var methodName = "selectModuloConfigurazione";
		try {
			
			return moduloConfigRepository.existModuloConfigByModuloConfigId(moduloConfigId);
		} catch (Exception e) {
			logError(sId, methodName, e.getMessage(), e);
			throw e;
		}

	}
}
