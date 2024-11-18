/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanrilevazioni.api.impl;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import it.csi.vigilsan.vigilsanrilevazioni.api.generated.StatusApi;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Component
public class StatusApiImpl implements StatusApi {
	private static final Logger LOG = LoggerFactory.getLogger(StatusApiImpl.class);


	@Override
	public Response statusGet(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		LOG.atInfo().log("statusGet invoked at {}", LocalDateTime.now());
		return Response.ok().entity("Status OK").build();
	}

}