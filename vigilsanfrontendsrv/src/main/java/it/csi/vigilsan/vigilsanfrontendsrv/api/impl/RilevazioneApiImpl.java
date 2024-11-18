/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.api.impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.RilevazioneApi;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelModuloRilevazione;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelRilevazioneEsteso;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.impl.RilevazioniServiceImpl;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.dto.ModelProfiloUtente;
import it.csi.vigilsan.vigilsanfrontendsrv.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanfrontendsrv.util.ListaGenericaPaginataWrapper;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.dto.FileCustom;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.util.ApiHeaderParamEnum;
import it.csi.vigilsan.vigilsanutil.generic.util.DateFormatEnum;
import it.csi.vigilsan.vigilsanutil.generic.util.DateUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Component
public class RilevazioneApiImpl extends RESTBaseService implements RilevazioneApi {

	@Autowired
	private RilevazioniServiceImpl rilevazioniService;

	@Override
	public Response funzionalitaRilevazioneCompilabileListaGet(String shibIdentitaCodiceFiscale, String xAccessToken,
			@NotNull String moduloConfigCod, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaRilevazioneCompilabileListaGet";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			return Response.ok()
					.entity(rilevazioniService.getRilevazioneCompilabileLista(profiloUtente.getStrutturaId(),
							profiloUtente.getSessioneId(), moduloConfigCod, httpHeaders))
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

	@Override
	public Response funzionalitaRilevazionePost(ModelModuloRilevazione body, String shibIdentitaCodiceFiscale,
			String xAccessToken, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaRilevazionePost";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);

			RESTErrorUtil.checkCondition(
					body.getRilevazione() != null
							&& profiloUtente.getStrutturaId().equals(body.getRilevazione().getStrutturaId()),
					ErrorCodeEnum.STRUTTURA_ID_NON_VALIDA);

			return Response.ok().entity(rilevazioniService.postRilevazione(body, profiloUtente, httpHeaders))
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

	@Override
	public Response funzionalitaRilevazioneCompilataListaGet(String shibIdentitaCodiceFiscale, String xAccessToken,
			@NotNull String moduloConfigCod, Long pageNumber, Long rowPerPage, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaRilevazioneCompilataListaGet";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			ListaGenericaPaginataWrapper<ModelRilevazioneEsteso> res = rilevazioniService.getRilevazioneCompilataLista(
					profiloUtente.getStrutturaId(), profiloUtente.getSessioneId(), moduloConfigCod, pageNumber,
					rowPerPage, httpHeaders);
			return Response.ok().entity(res.getGenericObject()).type(MediaType.APPLICATION_JSON)
					.header(ApiHeaderParamEnum.ROWS_NUMBER.getCode(), res.getRowNumbers()).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}


	@Override
	public Response funzionalitaRilevazioneModuloListaGet(String shibIdentitaCodiceFiscale, String xAccessToken,
			@NotNull String moduloConfigGruppoCod, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaRilevazioneModuloListaGet";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			return Response.ok()
					.entity(rilevazioniService.getRilevazioneModuloListaGet(profiloUtente.getStrutturaId(),
							profiloUtente.getSessioneId(), moduloConfigGruppoCod, httpHeaders))
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

	@Override
	public Response funzionalitaRilevazioneCompilataListaCsvGet(String shibIdentitaCodiceFiscale, String xAccessToken,
			@NotNull String moduloConfigCod, String dataRilevazioneDa, String dataRilevazioneA,
			String strutturaCategoriaCod, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaRilevazioneCompilataListaCsvGet";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);

			FileCustom fileCustom = rilevazioniService.getRilevazioneCompilataListaCsv(profiloUtente, moduloConfigCod,
					dataRilevazioneDa, dataRilevazioneA, strutturaCategoriaCod, httpHeaders);
			return Response.ok().entity(fileCustom.getFile()).type(fileCustom.getContentType())
					.header(ApiHeaderParamEnum.CONTENT_LENGTH.getCode(), fileCustom.getContentLength())
					.header(ApiHeaderParamEnum.CONTENT_DISPOSITION.getCode(), fileCustom.getContentDisposition())
					.build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}
	}

	@Override
	public Response funzionalitaDocumentazioneStoricoCsv(String shibIdentitaCodiceFiscale, String xRequestId,
			String xForwardedFor, String tipo, String dataRilevazioneDa, String dataRilevazioneA,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaDocumentazioneStoricoCsv";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);

			verifyDataCorrectFormat(dataRilevazioneDa);
			verifyDataCorrectFormat(dataRilevazioneA);
			
			FileCustom fileCustom = rilevazioniService.getRilevazioneStoricoCsv(profiloUtente, tipo, dataRilevazioneDa,
					dataRilevazioneA, shibIdentitaCodiceFiscale, xRequestId, xForwardedFor, httpHeaders);
			return Response.ok().entity(fileCustom.getFile()).type(fileCustom.getContentType())
					.header(ApiHeaderParamEnum.CONTENT_LENGTH.getCode(), fileCustom.getContentLength())
					.header(ApiHeaderParamEnum.CONTENT_DISPOSITION.getCode(), fileCustom.getContentDisposition())
					.build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}
	}

	private void verifyDataCorrectFormat(String data) {
		if (Objects.nonNull(data) && Boolean.FALSE
				.equals(DateUtils.isDateCorrectFormat(data, DateFormatEnum.API_DATE_FORMAT))) {
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.DATA_FORMATO_SBAGLIATO,data);
		}
	}


}