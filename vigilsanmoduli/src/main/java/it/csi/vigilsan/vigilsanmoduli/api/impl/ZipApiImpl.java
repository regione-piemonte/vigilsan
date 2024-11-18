/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.api.impl;

import java.io.ByteArrayInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.vigilsan.vigilsanmoduli.api.generated.ZipApi;
import it.csi.vigilsan.vigilsanmoduli.api.generated.dto.ModelZipLista;
import it.csi.vigilsan.vigilsanmoduli.api.service.impl.FileServiceImpl;
import it.csi.vigilsan.vigilsanmoduli.api.service.impl.ZipServiceImpl;
import it.csi.vigilsan.vigilsanmoduli.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.util.ApiHeaderParamEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.SecurityContext;

@Component
public class ZipApiImpl extends RESTBaseService implements ZipApi {

	@Autowired
	ZipServiceImpl zipService;

	@Autowired
	FileServiceImpl fileService;

	@Override
	public Response funzionalitaZipGet(ModelZipLista body, String shibIdentitaCodiceFiscale, Integer sId,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaZipGet";
		try {
			RESTErrorUtil.checkNotNull(body, ErrorCodeEnum.PAYLOAD_OBBLIGATORIO);

			String type = fileService.getFileContentTypeFromCod("zip").getFileContentTypeMime();
			var res = zipService.getZip(sId, body, shibIdentitaCodiceFiscale);
			// Prepara lo stream per la risposta
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(res.toByteArray());
			return Response.status(Status.CREATED).entity(byteArrayInputStream).type(type)
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + body.getFolderName() + ".zip")
					.header(ApiHeaderParamEnum.CONTENT_LENGTH.getCode(), res.size()).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

}