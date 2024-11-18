/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.vigilsan.vigilsanpratiche.api.generated.PrescrizioneApi;
import it.csi.vigilsan.vigilsanpratiche.api.service.impl.PrescrizoneServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Component
public class PrescrizioneApiImpl extends RESTBaseService implements PrescrizioneApi {

	@Autowired
	PrescrizoneServiceImpl prescrizioneService;
	
	@Override
	public Response funzionalitaPrescrizioneStatoGet(Integer sId, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaPrescrizioneStatoGet";
		try {
			return Response.ok().entity(prescrizioneService.getPrescrizioneStatoDecodifica(sId)).build();

		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaPrescrizioneTipoListaGet(Integer sId, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaPrescrizioneTipoListaGet";
		try {
			return Response.ok().entity(prescrizioneService.getPrescrizioneTipoDecodifica(sId)).build();

		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}


}