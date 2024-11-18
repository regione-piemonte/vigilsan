/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.EnteApi;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelEnteSoggetto;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.impl.EnteServiceImpl;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.dto.ModelProfiloUtente;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Component
public class EnteApiImpl extends RESTBaseService implements EnteApi {

	@Autowired
	private EnteServiceImpl enteService;

	@Override
	public Response funzionalitaEnteSoggettoListaGet(String shibIdentitaCodiceFiscale, String xAccessToken,
			Integer enteRuoloId, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaEnteSoggettoListaGet";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			return Response.ok()
					.entity(enteService.getEnteSoggettoLista(enteRuoloId, profiloUtente, httpHeaders))
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

	@Override
	public Response funzionalitaEnteSoggettoPost(ModelEnteSoggetto body, String shibIdentitaCodiceFiscale,
			String xAccessToken, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaEnteSoggettoPost";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);

			return Response.ok().entity(enteService.postEnteSoggetto(body, profiloUtente, httpHeaders))
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

	@Override
	public Response funzionalitaEnteSoggettoRuoloDecodificaListaGet(String shibIdentitaCodiceFiscale,
			String xAccessToken, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaEnteSoggettoRuoloDecodificaListaGet";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			return Response.ok()
					.entity(enteService.getRuoloEnteSoggettoDecodifica(profiloUtente, httpHeaders))
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

	@Override
	public Response funzionalitaEnteSoggettoInvalidaPost(ModelEnteSoggetto body, String shibIdentitaCodiceFiscale,
			String xAccessToken, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaEnteSoggettoInvalidaPost";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);

			return Response.ok().entity(enteService.postInvalidaEnteSoggetto(body, profiloUtente, httpHeaders))
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

	@Override
	public Response funzionalitaEnteSoggettoDeletePost(ModelEnteSoggetto body, String shibIdentitaCodiceFiscale,
			String xAccessToken, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaEnteSoggettoDeletePost";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);

			return Response.ok().entity(enteService.postCancellaEnteSoggetto(body, profiloUtente, httpHeaders))
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}
}