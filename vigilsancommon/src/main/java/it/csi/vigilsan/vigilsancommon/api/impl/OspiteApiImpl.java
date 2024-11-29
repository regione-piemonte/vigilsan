/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.vigilsan.vigilsancommon.api.generated.OspiteApi;
import it.csi.vigilsan.vigilsancommon.api.generated.dto.ModelOspiteMovimento;
import it.csi.vigilsan.vigilsancommon.api.generated.dto.ModelOspiteMovimentoPost;
import it.csi.vigilsan.vigilsancommon.api.service.impl.OspiteServiceImpl;
import it.csi.vigilsan.vigilsancommon.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.SecurityContext;

@Component
public class OspiteApiImpl extends RESTBaseService implements OspiteApi {

	@Autowired
	private OspiteServiceImpl ospiteService;

	@Override
	public Response funzionalitaOspiteStatoListaGet(Integer sId, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaOspiteStatoListaGet";
		try {
			return Response.ok().entity(ospiteService.getStatoAllValid(sId)).build();

		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaOspiteMovimentoTipoListaGet(Integer sId, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaOspiteMovimentoTipoListaGet";
		try {
			return Response.ok().entity(ospiteService.getOspiteMovimentoTipoAllValid(sId)).build();

		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaOspiteMovimentoListaGet(Integer sId, Integer strutturaId, Integer soggettoId,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaOspiteMovimentoTipoListaGet";
		try {
			RESTErrorUtil.checkNotNull(strutturaId, ErrorCodeEnum.STRUTTURA_ID_OBBLIGATORIO);
			RESTErrorUtil.checkNotNull(soggettoId, ErrorCodeEnum.SOGGETTOAPI_SOGG_ID_OBB);
			
			return Response.ok().entity(ospiteService.getOspiteMovimentoTipoAllValid(sId, strutturaId, soggettoId)).build();

		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}


	@Override
	public Response funzionalitaOspiteMovimentoPost(ModelOspiteMovimentoPost body, String shibIdentitaCodiceFiscale,
			Integer sId, Integer strutturaId, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaOspiteMovimentoPost";
		try {
			ModelOspiteMovimento res;
			RESTErrorUtil.checkNotNull(body, ErrorCodeEnum.PAYLOAD_OBBLIGATORIO);
			RESTErrorUtil.checkNotNull(strutturaId, ErrorCodeEnum.STRUTTURA_ID_OBBLIGATORIO);
			RESTErrorUtil.checkNotNull(body.getOspiteMovimentoTipoId(), ErrorCodeEnum.OSPITE_MOVIMENTO_TIPO_ID_OBBLIGATORIO);
			RESTErrorUtil.checkNotNull(body.getSoggettoId(), ErrorCodeEnum.SOGGETTOAPI_SOGG_ID_OBB);
			res = ospiteService.postOspiteMovimento(sId, strutturaId, body, shibIdentitaCodiceFiscale);
			return Response.status(Status.CREATED).entity(res).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaOspiteMovimentoDeletePost(ModelOspiteMovimento body, String shibIdentitaCodiceFiscale,
			Integer sId, Integer strutturaId, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaOspiteMovimentoDeletePost";
		try {
			ModelOspiteMovimento res = null;
			RESTErrorUtil.checkNotNull(body, ErrorCodeEnum.PAYLOAD_OBBLIGATORIO);
			RESTErrorUtil.checkNotNull(body.getOspiteMovimentoId(), ErrorCodeEnum.OSPITE_MOVIMENTO_TIPO_ID_OBBLIGATORIO);
			res = ospiteService.deleteOspiteMovimento(sId, body, strutturaId, shibIdentitaCodiceFiscale);
			return Response.status(Status.CREATED).entity(res).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaOspiteCondizioniListaGet(Integer sId, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaOspiteCondizioniListaGet";
		try {
			return Response.ok().entity(ospiteService.getOspiteCondizioniLista(sId)).build();

		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}
}