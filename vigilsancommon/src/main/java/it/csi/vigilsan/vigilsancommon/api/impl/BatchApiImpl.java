/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.vigilsan.vigilsancommon.api.generated.BatchApi;
import it.csi.vigilsan.vigilsancommon.api.generated.dto.ModelInputFileInformation;
import it.csi.vigilsan.vigilsancommon.api.service.RpviarService;
import it.csi.vigilsan.vigilsancommon.api.service.RpviniService;
import it.csi.vigilsan.vigilsancommon.api.service.RpvinvService;
import it.csi.vigilsan.vigilsancommon.util.LogMessageEnum;
import it.csi.vigilsan.vigilsanutil.generic.util.AuditOperazioneEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.SecurityContext;

@Component
public class BatchApiImpl extends RESTBaseService implements BatchApi {

	@Autowired
	private RpviarService rpviarServiceImpl;

	@Autowired
	private RpvinvService rpvinvService;
	
	@Autowired
	private RpviniService rpviniService;
	
	@Override
	public Response funzionalitaBatchrpviar(ModelInputFileInformation body, Integer sId,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaBatchrpviar";
		try {
			rpviarServiceImpl.funzionalitaBatchrpviar(sId, body, securityContext, httpHeaders, httpRequest);

			return Response.status(Status.CREATED).entity("OK").build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		} finally {
			logDebug(sId, methodName, LogMessageEnum.END.getMessage());
		}
	}

	@Override
	public Response funzionalitaBatchrpvinv(String shibIdentitaCodiceFiscale, Integer sId,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaBatchrpvinv";
		try {
			logAuditDb(sId, AuditOperazioneEnum.BATCH, methodName, "inizio");
			rpvinvService.rpvinvVerificaNotifiche(sId, shibIdentitaCodiceFiscale);

			logAuditDb(sId, AuditOperazioneEnum.BATCH, methodName, "fine");
			return Response.status(Status.CREATED).entity("OK").build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}
	
	@Override
	public Response funzionalitaBatchrpvini(String shibIdentitaCodiceFiscale, Integer sId,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaBatchrpvini";
		try {
			logAuditDb(sId, AuditOperazioneEnum.BATCH, methodName, "inizio");
			rpviniService.rpviniInviaNotifiche(sId, shibIdentitaCodiceFiscale, httpHeaders);

			logAuditDb(sId, AuditOperazioneEnum.BATCH, methodName, "fine");
			return Response.status(Status.CREATED).entity("OK").build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

}