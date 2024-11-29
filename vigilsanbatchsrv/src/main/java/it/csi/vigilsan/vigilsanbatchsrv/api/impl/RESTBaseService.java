/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanbatchsrv.api.impl;

import org.springframework.beans.factory.annotation.Autowired;

import it.csi.vigilsan.vigilsanbatchsrv.api.service.impl.SessioneServiceImpl;
import it.csi.vigilsan.vigilsanbatchsrv.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.vigilsancommon.dto.ModelProfiloUtente;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTException;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableApiServiceImpl;
import jakarta.ws.rs.core.Response;

public abstract class RESTBaseService extends AuditableApiServiceImpl {

	@Autowired
	protected SessioneServiceImpl sessioneService;
	
	protected RESTBaseService() {
		super();
	}

	/**
	 * @param methodName
	 * @param e
	 * @return
	 */
	protected Response generalExceptionReturn(String methodName, ModelProfiloUtente profiloUtente, Exception e) {
		if (e instanceof RESTException) {
			logError(profiloUtente != null ? profiloUtente.getSessioneId() : null, methodName, "Errore in chiamata",
					(RESTException) e);
			return ((RESTException) e).getResponse();
		}
		logError(profiloUtente != null ? profiloUtente.getSessioneId() : null, methodName, "Errore in chiamata", e);
		return RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.INTERNAL_SERVER_ERROR).getResponse();
	}

}