/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.vigilsan.vigilsancommon.api.generated.ProfiloApi;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.Profilo;
import it.csi.vigilsan.vigilsancommon.api.service.impl.ProfiloServiceImpl;
import it.csi.vigilsan.vigilsancommon.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Component
public class ApplicativoApiImpl extends RESTBaseService implements ProfiloApi {

	@Autowired
	private ProfiloServiceImpl profiloService;

	@Override
	public Response funzionalitaProfiloGet(String shibIdentitaCodiceFiscale, String xRequestId, String xCodiceServizio,
			@NotNull Integer profiloId, Integer sId, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaProfiloGet";
		try {
			RESTErrorUtil.checkNotNull(profiloId, ErrorCodeEnum.PROFILO_ID_OBBLIGATORIO);
			var res = profiloService.getProfiloByProfiloId(profiloId);
			return Response.ok().entity(res).build();
			
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaProfiloListaGet(String shibIdentitaCodiceFiscale, String xRequestId,
			String xCodiceServizio, Integer sId, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaProfiloListaGet";
		try {
			var res = profiloService.getProfilo();
			return Response.ok().entity(res).build();
			
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

}