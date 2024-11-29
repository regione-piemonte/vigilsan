/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.service.impl;

import org.springframework.stereotype.Service;

import it.csi.vigilsan.vigilsancommon.api.service.RpviniService;
import it.csi.vigilsan.vigilsancommon.api.service.RpvinvService;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableWPersistenceApiServiceImpl;

@Service
public class RpvinvServiceImpl extends AuditableWPersistenceApiServiceImpl implements RpvinvService {

	@Override
	public void rpvinvVerificaNotifiche(Integer sId, String cfUtente) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
