/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.integration.aura.soggettoaura.client;

import org.springframework.beans.factory.annotation.Value;

public class SoggettoAuraServiceClient  extends AbstractSoggettoAuraClient {

	@Value("${aura.soggetto.service}")
	public String auraSoggettoServiceUrl;

	@Override
	protected String getEndpoint() {
		logger.info("AURA Endpoint url {} " + auraSoggettoServiceUrl);

		return auraSoggettoServiceUrl;
	}
	
}
