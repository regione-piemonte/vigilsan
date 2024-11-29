/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanbatchsrv.rpvini.service.impl;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.vigilsan.vigilsanbatchsrv.api.service.vigilsan.vigilsancommon.VigilsanCommonPostApiEnum;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.impl.ApiUtils;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.vigilsancommon.impl.VigilsanCommonService;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableApiServiceImpl;
import it.csi.vigilsan.vigilsanutil.generic.util.ApiHeaderParamEnum;
import jakarta.ws.rs.core.HttpHeaders;

@Service
public class RpviniServiceImpl extends AuditableApiServiceImpl {

	@Autowired
	private VigilsanCommonService vigilsanCommonService;

	public void postBatchInvioNotifysan(Integer sId, String nomeBatch, HttpHeaders httpHeaders)
			throws IOException {
		var queryParam = new HashMap<String, Object>();
		var aggiungiUtenteBatch = new HashMap<String, String>();
		aggiungiUtenteBatch.put(ApiHeaderParamEnum.SHIB_IDENTITA_CODICEFISCALE.getCode(), nomeBatch);

		var headers = ApiUtils.convertJakartaToOkHttpHeaders(httpHeaders, aggiungiUtenteBatch);
		vigilsanCommonService.getGenericPost(VigilsanCommonPostApiEnum.POST_INVIONOTIFYSAN, null, headers, queryParam, sId);
	}
}
