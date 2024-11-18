/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.ModuloApi;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.impl.ModuliServiceImpl;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.dto.ModelProfiloUtente;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.dto.FileCustom;
import it.csi.vigilsan.vigilsanutil.generic.util.ApiHeaderParamEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Component
public class ModuloApiImpl extends RESTBaseService implements ModuloApi {

	@Autowired
	private ModuliServiceImpl moduliService;

	@Override
	public Response funzionalitaModuloGet(String shibIdentitaCodiceFiscale, String xRequestId, String xCodiceServizio,
			Integer moduloId, Integer moduloCompilatoId, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaModuloGet";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			return Response.ok().entity(moduliService.getModulo(moduloId, moduloCompilatoId, null,
					profiloUtente, httpHeaders)).type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

	@Override
	public Response funzionalitaModuloPdfGet(String shibIdentitaCodiceFiscale, String xRequestId,
			String xCodiceServizio, Integer moduloId, Integer moduloCompilatoId, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaModuloPdfGet";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);

			FileCustom fileCustom = moduliService.getModuloPdf(profiloUtente, moduloId, moduloCompilatoId, null,
					httpHeaders);
			return Response.ok().entity(fileCustom.getFile()).type(fileCustom.getContentType())
					.header(ApiHeaderParamEnum.CONTENT_LENGTH.getCode(), fileCustom.getContentLength())
					.header(ApiHeaderParamEnum.CONTENT_DISPOSITION.getCode(), fileCustom.getContentDisposition())
					.build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}
	}

}