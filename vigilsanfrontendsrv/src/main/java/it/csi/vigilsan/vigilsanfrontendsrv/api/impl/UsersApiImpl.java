/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.api.impl;

import org.springframework.stereotype.Component;

import it.csi.iride2.policy.entity.Identita;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.UsersApi;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.Error;
import it.csi.vigilsan.vigilsanfrontendsrv.filter.IrideIdAdapterFilter;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Component
public class UsersApiImpl implements UsersApi {

	@Override
	@PermitAll
	public Response getCurrentUser(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		
		Identita i = (Identita)httpRequest.getAttribute(IrideIdAdapterFilter.IRIDE_ID_REQ_ATTR);
		if (i==null){
            Error error = new Error();
            error.setErrorMessage("utente non disponibile");
            error.setCode("404");
            return Response.status(404).entity(error).build();
        }
		
		return Response.ok().entity("{codiceFiscale=\""+i.getCodFiscale()+"\"}").type(MediaType.APPLICATION_JSON).build();
	}

}