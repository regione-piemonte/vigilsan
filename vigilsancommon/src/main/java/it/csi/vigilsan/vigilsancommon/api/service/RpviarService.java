/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.service;

import it.csi.vigilsan.vigilsancommon.api.generated.dto.ModelInputFileInformation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.SecurityContext;

public interface RpviarService {

	
	void funzionalitaBatchrpviar(Integer sId, ModelInputFileInformation body, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws Exception;

}