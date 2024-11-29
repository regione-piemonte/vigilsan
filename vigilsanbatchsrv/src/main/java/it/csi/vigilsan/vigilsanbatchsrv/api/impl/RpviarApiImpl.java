/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanbatchsrv.api.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.vigilsan.vigilsanbatchsrv.api.generated.RpviarApi;
import it.csi.vigilsan.vigilsanbatchsrv.api.generated.dto.ModelInputFileInformation;
import it.csi.vigilsan.vigilsanbatchsrv.api.service.impl.SessioneServiceImpl;
import it.csi.vigilsan.vigilsanbatchsrv.rpviar.service.impl.RpviarServiceImpl;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.vigilsancommon.dto.ModelProfiloUtente;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Service
public class RpviarApiImpl extends RESTBaseService implements RpviarApi {
	private static final String RPVIAR = "rpviar";
	
	@Autowired
	private RpviarServiceImpl rpviarService;

	@Override
	public Response funzionalitaBatchRpviarPost(ModelInputFileInformation body, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaBatchRpviarPost";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = sessioneService.getSessione(RPVIAR, httpHeaders);
			
			rpviarService.postFile(profiloUtente.getSessioneId(), RPVIAR, body.getPathFile(), httpHeaders);
			
			sessioneService.getLogout(httpHeaders, profiloUtente.getSessioneId());
			return Response.ok().build();
		} catch (IOException e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}
	}

}
