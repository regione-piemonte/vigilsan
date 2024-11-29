/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.vigilsan.vigilsancommon.api.generated.EnteApi;
import it.csi.vigilsan.vigilsancommon.api.generated.dto.ModelEnteSoggetto;
import it.csi.vigilsan.vigilsancommon.api.service.EnteService;
import it.csi.vigilsan.vigilsancommon.api.service.impl.EnteSoggettoServiceImpl;
import it.csi.vigilsan.vigilsancommon.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.SecurityContext;

@Component
public class EnteApiImpl extends RESTBaseService implements EnteApi {

	@Autowired
	private EnteService enteService;

	@Autowired
	private EnteSoggettoServiceImpl enteSoggettoService;

	@Override
	public Response funzionalitaEnteGet(String shibIdentitaCodiceFiscale, String xRequestId, String xCodiceServizio,
			@NotNull Integer enteId, @NotNull Integer sId, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaEnteGet";
		try {
			RESTErrorUtil.checkNotNull(enteId, ErrorCodeEnum.ENTE_ID_OBBLIGATORIO);
			return Response.ok().entity(enteService.getEnteById(enteId)).build();

		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaEnteTipoGet(Integer sId, Integer enteTipoId, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaEnteTipoGet";
		try {
			RESTErrorUtil.checkNotNull(enteTipoId, ErrorCodeEnum.ENTE_TIPO_ID_OBBLIGATORIO);
			return Response.ok().entity(enteService.getEnteTipoById(enteTipoId)).build();

		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaEnteSoggettoListaGet(Integer sId, Integer enteId, Integer enteRuoloId,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaEnteSoggettoListaGet";
		try {
			RESTErrorUtil.checkNotNull(enteId, ErrorCodeEnum.ENTE_ID_OBBLIGATORIO);
			return Response.ok().entity(enteSoggettoService.getEnteSoggettoLista(enteId, enteRuoloId)).build();

		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaEnteSoggettoPost(ModelEnteSoggetto body, String shibIdentitaCodiceFiscale, Integer sId,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaEnteSoggettoPost";
		try {
			ModelEnteSoggetto res = enteSoggettoService.inserisciEnteSoggetto(body, shibIdentitaCodiceFiscale, sId);

			return Response.status(Status.CREATED).entity(res).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaEnteSoggettoRuoloDecodificaListaGet(Integer sId, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaEnteSoggettoRuoloDecodificaListaGet";
		try {
			return Response.ok().entity(enteSoggettoService.getRuoloEnteSoggettoDecodificaLista()).build();

		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaEnteSoggettoInvalidaPost(ModelEnteSoggetto body, String shibIdentitaCodiceFiscale,
			Integer sId, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaEnteSoggettoInvalidaPost";
		try {
			RESTErrorUtil.checkNotNull(body.getEnteSoggId(), ErrorCodeEnum.ENTE_SOGG_ID_OBBLIGATORIO);
			ModelEnteSoggetto res = enteSoggettoService.invalidaEnteSoggetto(body, shibIdentitaCodiceFiscale, sId);

			return Response.status(Status.CREATED).entity(res).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaEnteSoggettoDeletePost(ModelEnteSoggetto body, String shibIdentitaCodiceFiscale,
			Integer sId, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaEnteSoggettoDeletePost";
		try {
			RESTErrorUtil.checkNotNull(body.getEnteSoggId(), ErrorCodeEnum.ENTE_SOGG_ID_OBBLIGATORIO);
			ModelEnteSoggetto res = enteSoggettoService.cancellaEnteSoggetto(body, shibIdentitaCodiceFiscale, sId);

			return Response.status(Status.CREATED).entity(res).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

}