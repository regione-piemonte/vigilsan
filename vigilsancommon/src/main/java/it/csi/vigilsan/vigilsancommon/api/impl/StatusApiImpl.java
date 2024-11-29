/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import it.csi.vigilsan.vigilsancommon.api.generated.StatusApi;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableApiServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Component
public class StatusApiImpl extends AuditableApiServiceImpl implements StatusApi {
	
	@Override
	public Response statusGet(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		String methodName = "statusGet";
		logInfo(null,methodName, "invoked at {}",LocalDateTime.now());
		return Response.ok().entity("Status OK").build();
	}

}