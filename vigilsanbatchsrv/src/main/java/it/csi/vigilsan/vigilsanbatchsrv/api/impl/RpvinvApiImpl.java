/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanbatchsrv.api.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.vigilsan.vigilsanbatchsrv.api.generated.RpviniApi;
import it.csi.vigilsan.vigilsanbatchsrv.rpvinv.service.impl.RpvinvServiceImpl;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.vigilsancommon.dto.ModelProfiloUtente;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Service
public class RpvinvApiImpl extends RESTBaseService implements RpviniApi {
	private static final String batchName = "rpvini";
	
	@Autowired
	private RpvinvServiceImpl rpvinvService;

	@Override
	public Response funzionalitaBatchInvioNotifysanPost(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaBatchRpviniPost";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = sessioneService.getSessione(batchName, httpHeaders);
			
			rpvinvService.postBatchControlloNotifysan(profiloUtente.getSessioneId(), batchName, httpHeaders);

			sessioneService.getLogout(httpHeaders, profiloUtente.getSessioneId());
			return Response.ok().build();
		} catch (IOException e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}
	}
	

}
