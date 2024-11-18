/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.vigilsan.vigilsanpratiche.api.generated.AppuntamentoApi;
import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelAppuntamentoSoggetto;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.AppuntamentoSoggettoEsteso;
import it.csi.vigilsan.vigilsanpratiche.api.service.impl.AppuntamentoServiceImpl;
import it.csi.vigilsan.vigilsanpratiche.api.service.impl.AppuntamentoSoggettoServiceImpl;
import it.csi.vigilsan.vigilsanpratiche.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.SecurityContext;

@Component
public class AppuntamentoApiImpl extends RESTBaseService implements AppuntamentoApi {

	@Autowired
	AppuntamentoServiceImpl appuntamentoService;

	@Autowired
	AppuntamentoSoggettoServiceImpl appuntamentoSoggettoService;

	@Override
	public Response funzionalitaAppuntamentoStatoGet(Integer sId, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaAppuntamentoStatoGet";
		try {
			return Response.ok().entity(appuntamentoService.getAppuntamentoStatoDecodifica(sId)).build();

		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaAppuntamentoTipoListaGet(Integer sId, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaAppuntamentoTipoListaGet";
		try {
			return Response.ok().entity(appuntamentoService.getAppuntamentoTipoDecodifica(sId)).build();

		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaAppuntamentoSoggettoDeletePost(ModelAppuntamentoSoggetto body,
			String shibIdentitaCodiceFiscale, Integer sId, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaAppuntamentoSoggettoDeletePost";
		try {
			RESTErrorUtil.checkNotNull(body, ErrorCodeEnum.PAYLOAD_OBBLIGATORIO);
			RESTErrorUtil.checkNotNull(body.getAppuntamentoSoggettoId(), ErrorCodeEnum.APPUNTAMENTO_SOGGETTO_ID_OBBLIGATORIO);
			return Response.status(Status.CREATED)
					.entity(appuntamentoSoggettoService.deleteAppuntamentoSoggetto(sId, body, shibIdentitaCodiceFiscale))
					.build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaAppuntamentoSoggettoListaGet(Integer sId, Integer appuntamentoId,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaAppuntamentoSoggettoListaGet";
		try {
			List<AppuntamentoSoggettoEsteso> appuntamentoSoggettoListaByAppuntamento = appuntamentoSoggettoService.getAppuntamentoSoggettoListaByAppuntamento(appuntamentoId);
			return Response.ok().entity(appuntamentoSoggettoListaByAppuntamento).build();

		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaAppuntamentoSoggettoPost(ModelAppuntamentoSoggetto body,
			String shibIdentitaCodiceFiscale, Integer sId, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaOspiteMovimentoPost";
		try {
			RESTErrorUtil.checkNotNull(body, ErrorCodeEnum.PAYLOAD_OBBLIGATORIO);
			RESTErrorUtil.checkNotNull(body.getAppuntamentoId(), ErrorCodeEnum.APPUNTAMENTO_ID_OBBLIGATORIO);
			RESTErrorUtil.checkNotNull(body.getSoggettoId(), ErrorCodeEnum.SOGGETTO_ID_OBBLIGATORIO);
			return Response.status(Status.CREATED)
					.entity(appuntamentoSoggettoService.postAppuntamentoSoggetto(sId, body, shibIdentitaCodiceFiscale))
					.build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaAppuntamentoSoggettoRuoloDecodificaListaGet(Integer sId,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaAppuntamentoSoggettoRuoloDecodificaListaGet";
		try {
			return Response.ok().entity(appuntamentoSoggettoService.getAppuntamentoSoggettoRuoloLista()).build();

		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

}