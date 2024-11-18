/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.ProfiloApi;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.StrutturaApi;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelSoggettoEstesoLista;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.impl.ProfiloServiceImpl;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.impl.StrutturaServiceImpl;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.impl.UtenteServiceImpl;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.dto.ModelProfiloUtente;
import it.csi.vigilsan.vigilsanfrontendsrv.util.ListaGenericaPaginataWrapper;
import it.csi.vigilsan.vigilsanutil.generic.util.ApiHeaderParamEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Component
public class ProfiloApiImpl extends RESTBaseService implements ProfiloApi {

	@Autowired
	private ProfiloServiceImpl profiloService;

	@Override
	public Response funzionalitaProfiloDecodificaLista(String shibIdentitaCodiceFiscale, String xAccessToken,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaProfiloDecodificaLista";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			Object res = profiloService.getNuovapraticaStrutturaTipo(profiloUtente, httpHeaders);
			return Response.ok().entity(res).type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}
}