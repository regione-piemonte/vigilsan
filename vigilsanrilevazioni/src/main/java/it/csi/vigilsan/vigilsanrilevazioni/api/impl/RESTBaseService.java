/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanrilevazioni.api.impl;

import it.csi.vigilsan.vigilsanrilevazioni.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTException;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableWPersistenceApiServiceImpl;
import jakarta.ws.rs.core.Response;

public abstract class RESTBaseService extends AuditableWPersistenceApiServiceImpl {

	protected RESTBaseService() {
		super();
	}

	/**
	 * @param methodName
	 * @param e
	 * @return
	 */
	protected Response generalExceptionReturn(String methodName,Integer sessionId, Exception e) {
		if (e instanceof RESTException) {
			return ((RESTException) e).getResponse();
		}
		logError(sessionId, methodName, "Errore in chiamata", e);
		return RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.INTERNAL_SERVER_ERROR).getResponse();
	}

}