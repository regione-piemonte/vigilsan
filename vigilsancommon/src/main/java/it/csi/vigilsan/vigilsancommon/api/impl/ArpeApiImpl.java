/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.vigilsan.vigilsancommon.api.generated.ArpeApi;
import it.csi.vigilsan.vigilsancommon.api.service.impl.ArpeStrutturaDettaglioServiceImpl;
import it.csi.vigilsan.vigilsancommon.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Component
public class ArpeApiImpl extends RESTBaseService implements ArpeApi {

	@Autowired
	private ArpeStrutturaDettaglioServiceImpl arpeService;

	@Override
	public Response funzionalitaArpeStrutturaDettaglioGet(String shibIdentitaCodiceFiscale,
			@NotNull Integer strutturaId, Integer sId, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaArpeStrutturaDettaglioGet";
		try {
			RESTErrorUtil.checkNotNull(strutturaId, ErrorCodeEnum.STRUTTURA_ID_OBBLIGATORIO);
			return Response.ok().entity(arpeService.getArpeStrutturaDettaglioByStrutturaId(sId, strutturaId)).build();
			
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

}