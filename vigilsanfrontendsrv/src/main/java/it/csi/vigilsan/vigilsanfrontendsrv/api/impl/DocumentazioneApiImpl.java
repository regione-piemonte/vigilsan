/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.DocumentazioneApi;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelModuloDocumentazione;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelVerificaDocumentazione;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.impl.RilevazioniServiceImpl;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.dto.ModelProfiloUtente;
import it.csi.vigilsan.vigilsanfrontendsrv.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.dto.FileCustom;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.util.ApiHeaderParamEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Component
public class DocumentazioneApiImpl extends RESTBaseService implements DocumentazioneApi {

	@Autowired
	private RilevazioniServiceImpl rilevazioniService;

	@Override
	public Response funzionalitaDocumentazioneCompilabileListaGet(String shibIdentitaCodiceFiscale, String xAccessToken,
			@NotNull String moduloConfigCod, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaDocumentazioneCompilabileListaGet";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			return Response.ok()
					.entity(rilevazioniService.getDocumentazioneCompilabileLista(profiloUtente.getStrutturaId(),
							profiloUtente.getSessioneId(), moduloConfigCod, httpHeaders))
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

	@Override
	public Response funzionalitaDocumentazioneCompilataListaGet(String shibIdentitaCodiceFiscale, String xAccessToken,
			@NotNull String moduloConfigCod, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaDocumentazioneCompilataListaGet";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);

			var res = rilevazioniService.getDocumentazioneCompilataLista(profiloUtente.getStrutturaId(),
					profiloUtente.getSessioneId(), moduloConfigCod, httpHeaders);
			
			return Response.ok().entity(res.getGenericObject()).type(MediaType.APPLICATION_JSON)
					.header(ApiHeaderParamEnum.ROWS_NUMBER.getCode(), res.getRowNumbers()).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}


	@Override
	public Response funzionalitaDocumentazioneModuloListaGet(String shibIdentitaCodiceFiscale, String xAccessToken,
			@NotNull String moduloConfigGruppoCod, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaDocumentazioneModuloListaGet";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			return Response.ok()
					.entity(rilevazioniService.getDocumentazioneModuloListaGet(profiloUtente.getStrutturaId(),
							profiloUtente.getSessioneId(), moduloConfigGruppoCod, httpHeaders))
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

	@Override
	public Response funzionalitaDocumentazionePost(ModelModuloDocumentazione body, String shibIdentitaCodiceFiscale,
			String xAccessToken, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaDocumentazionePost";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);

			RESTErrorUtil.checkCondition(
					body.getDocumentazione() != null
							&& profiloUtente.getStrutturaId().equals(body.getDocumentazione().getStrutturaId()),
					ErrorCodeEnum.STRUTTURA_ID_NON_VALIDA);

			return Response.ok().entity(rilevazioniService.postDocumentazione(body, profiloUtente, httpHeaders))
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

	@Override
	public Response funzionalitaDocumentazioneVerificaPost(ModelVerificaDocumentazione body,
			String shibIdentitaCodiceFiscale, String xAccessToken, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaDocumentazioneVerificaPost";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);

			return Response.ok().entity(rilevazioniService.postDocumentazioneVerifica(body, profiloUtente, httpHeaders))
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

}