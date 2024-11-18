/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.vigilsan.vigilsanmoduli.api.generated.ModuloApi;
import it.csi.vigilsan.vigilsanmoduli.api.generated.dto.ModelModuloPost;
import it.csi.vigilsan.vigilsanmoduli.api.service.ModuloService;
import it.csi.vigilsan.vigilsanmoduli.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.util.ApiHeaderParamEnum;
import it.csi.vigilsan.vigilsanutil.generic.util.pdf.dto.FileCustom;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.SecurityContext;

@Component
public class ModuloApiImpl extends RESTBaseService implements ModuloApi {

	@Autowired
	ModuloService moduloService;

	@Override
	public Response funzionalitaModuloGet(String shibIdentitaCodiceFiscale, String xRequestId, String xCodiceServizio,
			Integer sId, Integer moduloId, Integer moduloCompilatoId, Boolean ridotto, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaModuloGet";
		try {
			var modulo = moduloService.getModulo(sId, moduloId, moduloCompilatoId, ridotto);
			return Response.ok().entity(modulo).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaModuloPost(ModelModuloPost body, String shibIdentitaCodiceFiscale, Integer sId,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaModuloPost";
		try {
			RESTErrorUtil.checkNotNull(body, ErrorCodeEnum.PAYLOAD_OBBLIGATORIO);

			return Response.status(Status.CREATED)
					.entity(moduloService.updateOrInsertModuloCompilato(body, shibIdentitaCodiceFiscale, sId)).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaModuloPdfGet(String shibIdentitaCodiceFiscale, String xRequestId,
			String xCodiceServizio, Integer sId, Integer moduloId, Integer moduloCompilatoId, Boolean ridotto,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaModuloPdfGet";
		try {
			var modulo = moduloService.getModulo(sId, moduloId, moduloCompilatoId, ridotto);

			FileCustom fileCustom = moduloService.getPdf(sId, modulo);
			return Response.ok().entity(fileCustom.getFile())
					.type(fileCustom.getContentType())
					.header(ApiHeaderParamEnum.CONTENT_LENGTH.getCode(), String.valueOf(fileCustom.getContentLength()))
					.header(ApiHeaderParamEnum.CONTENT_DISPOSITION.getCode(), "attachment; filename=\"" + fileCustom.getFileName() + "\"")
					.build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

}