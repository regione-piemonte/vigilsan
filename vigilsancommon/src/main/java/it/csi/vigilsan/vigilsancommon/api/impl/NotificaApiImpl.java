/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.vigilsan.vigilsancommon.api.generated.NotificaApi;
import it.csi.vigilsan.vigilsancommon.api.generated.dto.ModelNotifica;
import it.csi.vigilsan.vigilsancommon.api.generated.dto.ModelOspiteMovimento;
import it.csi.vigilsan.vigilsancommon.api.service.impl.NotificaServiceImpl;
import it.csi.vigilsan.vigilsancommon.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.SecurityContext;

@Component
public class NotificaApiImpl extends RESTBaseService implements NotificaApi {

	@Autowired
	private NotificaServiceImpl notificaService;




	@Override
	public Response funzionalitaNotificaPost(ModelNotifica body, String shibIdentitaCodiceFiscale, Integer sId,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaNotificaPost";
		try {
			RESTErrorUtil.checkNotNull(body, ErrorCodeEnum.PAYLOAD_OBBLIGATORIO);
			var res = notificaService.postNotifica(sId, body, shibIdentitaCodiceFiscale);
			return Response.status(Status.CREATED).entity(res).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}}