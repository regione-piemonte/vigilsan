/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.vigilsan.vigilsanpratiche.api.generated.NuovapraticaApi;
import it.csi.vigilsan.vigilsanpratiche.api.service.impl.AppuntamentoServiceImpl;
import it.csi.vigilsan.vigilsanpratiche.api.service.impl.PraticaServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Component
public class NuovapraticaApiImpl extends RESTBaseService implements NuovapraticaApi {

	@Autowired
	PraticaServiceImpl praticaService;

	@Override
	public Response funzionalitaNuovapraticaPraticaTipoListaGet(Integer sId, Integer enteTipoId,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaNuovapraticaPraticaTipoListaGet";
		try {
			return Response.ok().entity(praticaService.getPraticaTipoNuovapratica(sId,enteTipoId)).build();

		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaNuovapraticaStrutturaTipoListaGet(Integer sId, Integer enteTipoId,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaNuovapraticaStrutturaTipoListaGet";
		try {
			return Response.ok().entity(praticaService.getStrutturaRidottaByEnteTipoId(sId, enteTipoId)).build();

		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}


}