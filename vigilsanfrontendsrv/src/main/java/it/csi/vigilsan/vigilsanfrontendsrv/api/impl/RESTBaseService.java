/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.api.impl;

import org.apache.commons.lang3.StringUtils;

import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.dto.ModelProfiloUtente;
import it.csi.vigilsan.vigilsanfrontendsrv.filter.GenericJwtFilter;
import it.csi.vigilsan.vigilsanfrontendsrv.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTException;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableApiServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.Response;

public abstract class RESTBaseService extends AuditableApiServiceImpl {

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
			return ((RESTException) e).getResponse();
		}
		logError(profiloUtente != null ? profiloUtente.getSessioneId() : null, methodName, "Errore in chiamata", e);
		return RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.INTERNAL_SERVER_ERROR).getResponse();
	}

	protected ModelProfiloUtente getModelProfiloUtente(String methodName, HttpServletRequest httpRequest) {
		try {
			return (ModelProfiloUtente) httpRequest.getAttribute(GenericJwtFilter.JWT_PROFILE_ATTR);
		} catch (Exception e) {
			logError(null, methodName, "Errore in chiamata lettura token", e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.INTERNAL_SERVER_ERROR_TOKEN);
		}
	}

}