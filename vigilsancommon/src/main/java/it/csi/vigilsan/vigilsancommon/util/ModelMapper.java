/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.util;

import org.mapstruct.Mapper;

import it.csi.aura.auraws.services.central.screeningepatitec.Response;
import it.csi.vigilsan.vigilsancommon.integration.aura.soggettoaura.dto.SoggettoAuraCustom;

@Mapper(componentModel = "spring")
public interface ModelMapper {

	
	public abstract SoggettoAuraCustom fromScreeningEpatiteCResponseToSoggettoAuraCustom (Response response); 
}
