/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.api.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelProfilo;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.VigilsanCommonGetListApiEnum;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.dto.ModelProfiloUtente;
import it.csi.vigilsan.vigilsanfrontendsrv.util.OkhttpCallUtils;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.vigilsancommon.impl.VigilsanCommonService;
import jakarta.ws.rs.core.HttpHeaders;

@Service
public class ProfiloServiceImpl {

	@Autowired
	private VigilsanCommonService vigilsanCommonService;


	public List<ModelProfilo> getNuovapraticaStrutturaTipo(ModelProfiloUtente profiloUtente, HttpHeaders httpHeaders)
			throws IOException {
		var qp = new HashMap<String, Object>();
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		return vigilsanCommonService.getGenericGet(
				VigilsanCommonGetListApiEnum.GET_PROFILO_LISTA, headers, qp,
				profiloUtente.getSessioneId());
	}
}
