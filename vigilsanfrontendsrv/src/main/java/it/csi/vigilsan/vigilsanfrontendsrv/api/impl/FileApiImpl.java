/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.FileApi;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelFilePostBff;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.impl.ModuliServiceImpl;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.dto.ModelProfiloUtente;
import it.csi.vigilsan.vigilsanutil.generic.util.ApiHeaderParamEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Component
public class FileApiImpl extends RESTBaseService implements FileApi {
	
	@Autowired
	private ModuliServiceImpl moduliService;
	
	
	@Override
	public Response funzionalitaFilePost(ModelFilePostBff body, String shibIdentitaCodiceFiscale, String xRequestId,
			String xCodiceServizio, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaFilePost";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			return Response.ok()
					.entity(moduliService.postFile(profiloUtente,body,httpHeaders))
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}


	@Override
	public Response funzionalitaFileGet(String shibIdentitaCodiceFiscale, String xRequestId, String xCodiceServizio,
			@NotNull Integer fileId, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaFileGet";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);

			var fileCustom = moduliService.getFile(profiloUtente.getSessioneId(), fileId,httpHeaders);
			
			return Response.ok().entity(fileCustom.getFile())
					.type(fileCustom.getContentType())
					.header(ApiHeaderParamEnum.CONTENT_LENGTH.getCode(), fileCustom.getContentLength())
					.header(ApiHeaderParamEnum.CONTENT_DISPOSITION.getCode(), fileCustom.getContentDisposition())
					.build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}
	}


	@Override
	public Response funzionalitaFileContentTypeListaGet(String shibIdentitaCodiceFiscale, String xAccessToken,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaFileContentTypeListaGet";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			return Response.ok()
					.entity(moduliService.getFileContentTypeLista(profiloUtente.getSessioneId(), httpHeaders))
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}
	

}