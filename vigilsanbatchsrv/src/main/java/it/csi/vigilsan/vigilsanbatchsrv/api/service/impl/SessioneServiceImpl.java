/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanbatchsrv.api.service.impl;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.vigilsan.vigilsanbatchsrv.api.service.vigilsan.vigilsancommon.VigilsanCommonGetApiEnum;
import it.csi.vigilsan.vigilsanbatchsrv.api.service.vigilsan.vigilsancommon.VigilsanCommonPostApiEnum;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.impl.ApiUtils;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.vigilsancommon.dto.ModelProfiloUtente;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.vigilsancommon.impl.VigilsanCommonService;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableApiServiceImpl;
import it.csi.vigilsan.vigilsanutil.generic.util.ApiHeaderParamEnum;
import jakarta.ws.rs.core.HttpHeaders;

@Service
public class SessioneServiceImpl extends AuditableApiServiceImpl {

	@Autowired
	private VigilsanCommonService vigilsanCommonService;

	public ModelProfiloUtente getSessione(String nomeBatch, HttpHeaders httpHeaders) throws IOException {
		var queryParam = new HashMap<String, Object>();
		var aggiungiUtenteBatch = new HashMap<String, String>();
		aggiungiUtenteBatch.put(ApiHeaderParamEnum.SHIB_IDENTITA_CODICEFISCALE.getCode(), nomeBatch);
		var headers = ApiUtils.convertJakartaToOkHttpHeaders(httpHeaders, aggiungiUtenteBatch);
		return vigilsanCommonService.getGenericGet(VigilsanCommonGetApiEnum.GET_SESSIONE_BATCH, headers, queryParam,
				null);
	}


	public void getLogout(HttpHeaders httpHeaders, Integer sessioneId) {
		final var methodName = "getLogout";
		try {
			postLogout(sessioneId, httpHeaders);
		} catch (IOException e) {
			logError(sessioneId, methodName, "IOException: ", e);
		} catch (Exception e) {
			logError(sessioneId, methodName, "Error: ", e);
		}
	}

	protected String postLogout(Integer sId, HttpHeaders httpHeaders) throws IOException {
		var headers = ApiUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		var qp = new HashMap<String, Object>();
		return vigilsanCommonService.getGenericPost(VigilsanCommonPostApiEnum.POST_UTENTE_LOGOUT, null, headers, qp,
				sId);
	}

}
