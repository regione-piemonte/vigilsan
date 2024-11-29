/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.integration.aura.soggettoaura.client;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import it.csi.aura.auraws.services.central.screeningepatitec.ScreeningEpatiteC;
import it.csi.aura.auraws.services.central.screeningepatitec.ScreeningEpatiteCResponse;

public abstract class AbstractSoggettoAuraClient extends WebServiceGatewaySupport {
	
	protected abstract String getEndpoint();

	public ScreeningEpatiteCResponse getAuraSoggetto(ScreeningEpatiteC richiesta) {
		ScreeningEpatiteCResponse response = (ScreeningEpatiteCResponse) getWebServiceTemplate().
				marshalSendAndReceive(getEndpoint(), richiesta);
		return response;
	}

}
