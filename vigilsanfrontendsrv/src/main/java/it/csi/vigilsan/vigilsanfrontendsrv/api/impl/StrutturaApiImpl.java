/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.StrutturaApi;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelSoggettoEstesoLista;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.impl.StrutturaServiceImpl;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.impl.UtenteServiceImpl;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.dto.ModelProfiloUtente;
import it.csi.vigilsan.vigilsanfrontendsrv.util.ListaGenericaPaginataWrapper;
import it.csi.vigilsan.vigilsanutil.generic.util.ApiHeaderParamEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Component
public class StrutturaApiImpl extends RESTBaseService implements StrutturaApi {

	@Autowired
	private StrutturaServiceImpl strutturaService;

	@Autowired
	private UtenteServiceImpl utenteServiceImpl;

	@Override
	public Response funzionalitaStrutturaParametroGet(String shibIdentitaCodiceFiscale, String xAccessToken,
			Integer strutturaId, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaStrutturaParametroGet";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			return Response.ok().entity(strutturaService.getParametriStruttura(profiloUtente,strutturaId, httpHeaders))
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

	@Override
	public Response funzionalitaStrutturaListaGet(String shibIdentitaCodiceFiscale, String xAccessToken, String filter,
			String ruoloEnteStrutturaCod, Long pageNumber, Long rowPerPage, Boolean descending, String orderBy,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaStrutturaListaGet";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			ListaGenericaPaginataWrapper<ModelSoggettoEstesoLista> res = strutturaService.getStrutturaListaByEnteId(
					profiloUtente, filter, null, pageNumber, rowPerPage, descending, orderBy, httpHeaders);
			return Response.ok().entity(res.getGenericObject()).type(MediaType.APPLICATION_JSON)
					.header(ApiHeaderParamEnum.ROWS_NUMBER.getCode(), res.getRowNumbers()).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

	@Override
	public Response funzionalitaStrutturaEstesaGet(String shibIdentitaCodiceFiscale, String xAccessToken,
			Integer strutturaId, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaStrutturaEstesaGet";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			return Response.ok().entity(utenteServiceImpl.getstrutturaEstesaIdEsterno(profiloUtente,strutturaId, httpHeaders))
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}
}