/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.vigilsan.vigilsancommon.api.generated.ComuneApi;
import it.csi.vigilsan.vigilsancommon.api.service.impl.ComuneServiceImpl;
import it.csi.vigilsan.vigilsancommon.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Component
public class ComuneApiImpl extends RESTBaseService implements ComuneApi {

	@Autowired
	private ComuneServiceImpl comuneService;

	@Override
	public Response funzionalitaComuneEstesoGet(String shibIdentitaCodiceFiscale, String xRequestId,
			String xCodiceServizio, @NotNull Integer comuneId, Integer sId, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaEnteGet";
		try {
			RESTErrorUtil.checkNotNull(comuneId, ErrorCodeEnum.COMUNE_ID_OBBLIGATORIO);
			return Response.ok().entity(comuneService.getComuneEstesoByComuneId(comuneId)).build();
			
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

}