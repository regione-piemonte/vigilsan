/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.vigilsan.vigilsancommon.api.generated.UtenteApi;
import it.csi.vigilsan.vigilsancommon.api.generated.dto.ModelProfiloUtente;
import it.csi.vigilsan.vigilsancommon.api.service.UtenteInterface;
import it.csi.vigilsan.vigilsancommon.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.SecurityContext;

@Component
public class UtenteApiImpl extends RESTBaseService implements UtenteApi {

	@Autowired
	private UtenteInterface utenteInterface;

	@Override
	public Response funzionalitaUtenteAuthGet(String shibIdentitaCodiceFiscale, String xRequestId,
			String xCodiceServizio, String xForwardedFor, @NotNull String token, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaUtenteAuthGet";
		try {
			RESTErrorUtil.checkNotNull(token, ErrorCodeEnum.TOKEN_OBBLIGATORIO);

			return Response.ok(utenteInterface.getCurrentUserFromConfiguratore(shibIdentitaCodiceFiscale, xRequestId,
					xForwardedFor, xCodiceServizio, token)).build();

		} catch (Exception e) {
			return generalExceptionReturn(methodName, null, e);
		}
	}

	@Override
	public Response funzionalitaUtenteLogout(String body, String shibIdentitaCodiceFiscale, Integer sId,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaUtenteLogout";
		try {
			RESTErrorUtil.checkNotNull(sId, ErrorCodeEnum.TOKEN_OBBLIGATORIO);
			utenteInterface.logout(shibIdentitaCodiceFiscale, sId);
			return Response.status(Status.CREATED).entity("OK").build();

		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaSessioneBatchGet(String xForwardedFor, String shibIdentitaCodiceFiscale,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaUtenteAuthGet";
		try {
			RESTErrorUtil.checkNotNull(shibIdentitaCodiceFiscale, ErrorCodeEnum.SHIB_IDENTITA_OBBLIGATORIO);

			return Response.ok(
					utenteInterface.getCurrentUserFromShibIdentitaForBatch(shibIdentitaCodiceFiscale, xForwardedFor))
					.build();

		} catch (Exception e) {
			return generalExceptionReturn(methodName, null, e);
		}
	}

	@Override
	public Response funzionalitaUtentePermessiGet(Integer profiloId, Integer applicativoId, Integer strutturaId,
			Integer sId, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaUtentePermessiGet";
		try {
			RESTErrorUtil.checkNotNull(profiloId, ErrorCodeEnum.PROFILO_ID_OBBLIGATORIO);
			RESTErrorUtil.checkNotNull(applicativoId, ErrorCodeEnum.APPLICATIVO_ID_OBBLIGATORIO);

			return Response.ok(
					utenteInterface.getPermessiByProfiloIdAndApplicativoId(profiloId, applicativoId, strutturaId, sId))
					.build();

		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaUtenteModificaSessionePost(ModelProfiloUtente body, String xForwardedFor, Integer sId,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaUtenteModificaSessionePost";
		try {
			RESTErrorUtil.checkNotNull(body, ErrorCodeEnum.PAYLOAD_OBBLIGATORIO);
			ModelProfiloUtente res = utenteInterface.modificaSessione(body, xForwardedFor, sId);
			return Response.status(Status.CREATED).entity(res).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

}