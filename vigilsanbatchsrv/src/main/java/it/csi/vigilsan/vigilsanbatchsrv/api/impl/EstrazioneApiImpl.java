/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanbatchsrv.api.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.vigilsan.vigilsanbatchsrv.api.generated.EstrazioneApi;
import it.csi.vigilsan.vigilsanbatchsrv.api.service.impl.EstrazioneServiceImpl;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.vigilsancommon.dto.ModelProfiloUtente;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Service
public class EstrazioneApiImpl extends RESTBaseService implements EstrazioneApi {
	private static final String batchName = "estrazioneRilevazioneBatch";

	@Autowired
	private EstrazioneServiceImpl estrazioneService;

	@Override
	public Response funzionalitaBatchEstrazioniRilevazionePost(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaBatchRpviarPost";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = sessioneService.getSessione(batchName, httpHeaders);

			estrazioneService.postBatchEstrazioneRilevazioni(profiloUtente.getSessioneId(), batchName, httpHeaders);

			sessioneService.getLogout(httpHeaders, profiloUtente.getSessioneId());
			return Response.noContent().build();
		} catch (IOException e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}
	}

}
