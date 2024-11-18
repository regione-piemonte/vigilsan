/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.vigilsan.vigilsanmoduli.api.generated.FileApi;
import it.csi.vigilsan.vigilsanmoduli.api.generated.dto.ModelFilePost;
import it.csi.vigilsan.vigilsanmoduli.api.service.impl.FileServiceImpl;
import it.csi.vigilsan.vigilsanmoduli.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.util.ApiHeaderParamEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.SecurityContext;

@Component
public class FileApiImpl extends RESTBaseService implements FileApi {

	@Autowired
	FileServiceImpl fileService;

	@Override
	public Response funzionalitaFilePost(ModelFilePost body, String shibIdentitaCodiceFiscale, Integer sId,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaFilePost";
		try {
			RESTErrorUtil.checkNotNull(body, ErrorCodeEnum.PAYLOAD_OBBLIGATORIO);
			RESTErrorUtil.checkNotNull(body.getFileName(), ErrorCodeEnum.FILE_NAME_OBBLIGATORIO);
			RESTErrorUtil.checkNotNull(body.getEnteCod(), ErrorCodeEnum.ENTE_COD_OBBLIGATORIO);
			RESTErrorUtil.checkNotNull(body.getStrutturaCod(), ErrorCodeEnum.STRUTTURA_COD_OBBLIGATORIO);
			RESTErrorUtil.checkNotNull(body.getFileTipoId(), ErrorCodeEnum.FILE_TIPO_ID_OBBLIGATORIO);
			RESTErrorUtil.checkNotNull(body.getFileContentTypeId(), ErrorCodeEnum.FILE_CONTENT_TYPE_ID_OBBLIGATORIO);

			return Response.status(Status.CREATED).entity(fileService.postFile(sId, body, shibIdentitaCodiceFiscale))
					.build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaFileGet(String shibIdentitaCodiceFiscale, @NotNull Integer fileId, Integer sId,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaFileGet";
		try {
			RESTErrorUtil.checkNotNull(fileId, ErrorCodeEnum.FILE_ID_OBBLIGATORIO);

			var fileCustom = fileService.getFile(sId, fileId);
			return Response.ok().entity(fileCustom.getFile()).type(fileCustom.getContentType())
					.header(ApiHeaderParamEnum.CONTENT_LENGTH.getCode(), String.valueOf(fileCustom.getContentLength()))
					.header(ApiHeaderParamEnum.CONTENT_DISPOSITION.getCode(),
							"attachment; filename=\"" + fileCustom.getFileName() + "\"")
					.build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaFileContentTypeListaGet(Integer sId, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaFileContentTypeListaGet";
		try {
			return Response.ok().entity(fileService.getFileContentTypeLista()).build();

		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaFileTipoGet(Integer sId, String fileTipoCod, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaFileTipoGet";
		try {
			return Response.ok().entity(fileService.getfileTipo(fileTipoCod, sId)).build();

		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}

	}

}