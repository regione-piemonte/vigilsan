/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.integration.gatefire;

import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.Attachment;
import it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.CallInfo;
import it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.GateFireSrvc;
import it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.GateFireSrvc_Service;
import it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.SignVerifyResult;
import it.csi.vigilsan.vigilsanmoduli.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableApiServiceImpl;
import jakarta.ws.rs.WebApplicationException;

@Service
public class GateFireService extends AuditableApiServiceImpl {

	@Value("${gatefire.wsdl.url}")
	private String baseUrl;

	/**
	 * 
	 * @param request
	 * @return
	 */
	public SignVerifyResult verificaFirma(Integer sId, Attachment attachment, CallInfo info)
			throws WebApplicationException {
		final var methodName = "verificaFirma";

		try {

			GateFireSrvc soap = getSoap(sId);

			logDebug(sId, methodName, "## RICHIESTA :");
			return soap.verificaFirma(attachment, info);
		} catch (Exception e) {
			logError(sId, methodName, " Errore durante la chiamata al servizio ", e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.ERRORE_CHIAMATA_GATEFIRE);
		}

	}

	private GateFireSrvc getSoap(Integer sId) throws MalformedURLException {
		final var methodName = "getSoap ";
		final var wsdlURL = new URL(baseUrl + "?wsdl");
		// costruisco il service
		logDebug(sId, methodName, "creazione servizio");
		GateFireSrvc_Service service = new GateFireSrvc_Service(wsdlURL);
		logDebug(sId, methodName, "fine creazione servizio");
		return service.getGateFireSrvcPort();
	}
}
