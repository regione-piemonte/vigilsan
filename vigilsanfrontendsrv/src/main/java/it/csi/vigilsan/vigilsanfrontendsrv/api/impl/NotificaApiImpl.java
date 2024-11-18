/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.NotificaApi;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelNotifica;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.impl.NotificaServiceImpl;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.impl.UtenteServiceImpl;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.dto.ModelProfiloUtente;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.SecurityContext;

@Component
public class NotificaApiImpl extends RESTBaseService implements NotificaApi {

	@Autowired
	private NotificaServiceImpl notificaService;

	@Override
	public Response funzionalitaNotificaGestioneDocumentalePost(ModelNotifica body, String shibIdentitaCodiceFiscale,
			String xAccessToken, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaNotificaGestioneDocumentalePost";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			return Response.status(Status.CREATED).entity(notificaService.postNotificaGestioneDocumentale(body, profiloUtente, httpHeaders))
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}
}