/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.vigilsan.vigilsancommon.api.generated.RuoloApi;
import it.csi.vigilsan.vigilsancommon.api.service.impl.RuoloServiceImpl;
import it.csi.vigilsan.vigilsancommon.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Component
public class RuoloApiImpl extends RESTBaseService implements RuoloApi {

	@Autowired
	private RuoloServiceImpl ruoloService;

	@Override
	public Response funzionalitaRuoloGet(String shibIdentitaCodiceFiscale, String xRequestId, String xCodiceServizio,
			@NotNull Integer ruoloId, Integer sId, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaRuoloGet";
		try {
			RESTErrorUtil.checkNotNull(ruoloId, ErrorCodeEnum.RUOLO_ID_OBBLIGATORIO);
			var res = ruoloService.getRuoloByRuoloId(ruoloId);
			return Response.ok().entity(res).build();
			
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

}