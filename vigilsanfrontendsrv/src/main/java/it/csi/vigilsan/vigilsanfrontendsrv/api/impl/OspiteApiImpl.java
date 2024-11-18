/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.OspiteApi;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelOspiteMovimentoPost;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelSoggetto;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelSoggettoEstesoLista;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.impl.OspiteServiceImpl;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.dto.ModelProfiloUtente;
import it.csi.vigilsan.vigilsanfrontendsrv.util.ListaGenericaPaginataWrapper;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.dto.FileCustom;
import it.csi.vigilsan.vigilsanutil.generic.util.ApiHeaderParamEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Component
public class OspiteApiImpl extends RESTBaseService implements OspiteApi {

	@Autowired
	private OspiteServiceImpl ospiteService;

	@Override
	public Response funzionalitaOspitePost(ModelSoggetto body, String shibIdentitaCodiceFiscale, String xAccessToken,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaOspitePost";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);

			return Response.ok().entity(ospiteService.postSoggetto(body, profiloUtente, httpHeaders))
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

	@Override
	public Response funzionalitaOspiteMovimentoPost(ModelOspiteMovimentoPost body, String shibIdentitaCodiceFiscale,
			String xAccessToken, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaOspitePost";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);

			return Response.ok().entity(ospiteService.postMovimento(body, profiloUtente, httpHeaders))
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

	@Override
	public Response funzionalitaOspiteMovimentoTipoListaGet(String shibIdentitaCodiceFiscale, String xAccessToken,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaOspiteMovimentoTipoListaGet";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			return Response.ok()
					.entity(ospiteService.getOspiteMovimentoTipoLista(profiloUtente.getSessioneId(), httpHeaders))
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

	@Override
	public Response funzionalitaOspiteStatoListaGet(String shibIdentitaCodiceFiscale, String xAccessToken,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaOspiteStatoListaGet";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			return Response.ok().entity(ospiteService.getOspiteStatoLista(profiloUtente.getSessioneId(), httpHeaders))
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

	@Override
	public Response funzionalitaOspiteListaGet(String shibIdentitaCodiceFiscale, String xAccessToken, String filter,
			Integer ospiteStatoId, String dataIngressoDa, String dataIngressoA, String dataUscitaDa, String dataUscitaA,
			Long pageNumber, Long rowPerPage, Boolean descending, String orderBy, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaOspiteListaGet";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			ListaGenericaPaginataWrapper<ModelSoggettoEstesoLista> res = ospiteService.getOspiteLista(profiloUtente,
					filter, ospiteStatoId, dataIngressoDa, dataIngressoA, dataUscitaDa, dataUscitaA, pageNumber,
					rowPerPage, descending, orderBy, httpHeaders);
			return Response.ok().entity(res.getGenericObject()).type(MediaType.APPLICATION_JSON)
					.header(ApiHeaderParamEnum.ROWS_NUMBER.getCode(), res.getRowNumbers()).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

	@Override
	public Response funzionalitaOspiteMovimentoDelete(String shibIdentitaCodiceFiscale, String xAccessToken, Integer id,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaOspiteMovimentoDelete";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			ospiteService.deleteMovimento(id, profiloUtente, httpHeaders);
			return Response.noContent().build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

	@Override
	public Response funzionalitaOspiteListaCsvGet(String shibIdentitaCodiceFiscale, String xAccessToken, String filter,
			Integer ospiteStatoId, String dataIngressoDa, String dataIngressoA, String dataUscitaDa, String dataUscitaA,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaFileGet";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);

			FileCustom fileCustom = ospiteService.getOspiteListaCsv(profiloUtente, filter, ospiteStatoId,
					dataIngressoDa, dataIngressoA, dataUscitaDa, dataUscitaA, httpHeaders);
			return Response.ok().entity(fileCustom.getFile()).type(fileCustom.getContentType())
					.header(ApiHeaderParamEnum.CONTENT_LENGTH.getCode(), fileCustom.getContentLength())
					.header(ApiHeaderParamEnum.CONTENT_DISPOSITION.getCode(), fileCustom.getContentDisposition())
					.build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}
	}

	@Override
	public Response funzionalitaOspiteCondizioniListaGet(String shibIdentitaCodiceFiscale, String xAccessToken,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaOspiteCondizioniListaGet";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			return Response.ok()
					.entity(ospiteService.getOspiteCondizioniLista(profiloUtente.getSessioneId(), httpHeaders))
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

	@Override
	public Response funzionalitaOspiteGet(String shibIdentitaCodiceFiscale, String xAccessToken,
			@NotNull Integer soggettoId, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaOspiteGet";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			return Response.ok().entity(ospiteService.getOspite(profiloUtente, soggettoId, httpHeaders))
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}
}