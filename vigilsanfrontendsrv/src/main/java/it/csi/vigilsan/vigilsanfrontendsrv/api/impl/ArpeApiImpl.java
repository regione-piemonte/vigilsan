/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.ArpeApi;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.impl.UtenteServiceImpl;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.dto.ModelProfiloUtente;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Component
public class ArpeApiImpl extends RESTBaseService implements ArpeApi {

	@Autowired
	private UtenteServiceImpl utenteService;

	@Override
	public Response funzionalitaArpeStrutturaDettaglioGet(String shibIdentitaCodiceFiscale, String xAccessToken,
			Integer strutturaId, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaArpeStrutturaDettaglioGet";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			return Response.ok()
					.entity(utenteService.getArpeStrutturaDettaglio(profiloUtente, httpHeaders))
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}
}