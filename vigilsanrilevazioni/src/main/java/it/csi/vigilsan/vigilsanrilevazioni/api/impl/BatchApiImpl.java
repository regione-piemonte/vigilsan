/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanrilevazioni.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.vigilsan.vigilsanrilevazioni.api.generated.BatchApi;
import it.csi.vigilsan.vigilsanrilevazioni.api.service.impl.RilevazioneDocumentazionePivotServiceImpl;
import it.csi.vigilsan.vigilsanutil.generic.util.AuditOperazioneEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.SecurityContext;

@Component
public class BatchApiImpl extends RESTBaseService implements BatchApi {

	@Autowired
	RilevazioneDocumentazionePivotServiceImpl rilevazionePivotServiceImpl;

	@Override
	public Response funzionalitaBatchEstrazioneRilevazioni(Integer sId, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaBatchEstrazioneRilevazioni";
		try {
			logAuditDb(sId, AuditOperazioneEnum.BATCH, methodName, "inizio");
			rilevazionePivotServiceImpl.funzionalitaRilevazionePivotModuliGeneraView(sId);

			logAuditDb(sId, AuditOperazioneEnum.BATCH, methodName, "fine");
			return Response.status(Status.CREATED).entity("OK").build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

}