/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.integration.aura.soggettoaura.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ws.soap.SoapFaultException;

import it.csi.aura.auraws.services.central.screeningepatitec.ScreeningEpatiteC;
import it.csi.aura.auraws.services.central.screeningepatitec.ScreeningEpatiteCResponse;
import it.csi.vigilsan.vigilsancommon.integration.aura.soggettoaura.client.SoggettoAuraServiceClient;
import it.csi.vigilsan.vigilsancommon.integration.aura.soggettoaura.dto.SoggettoAuraCustom;
import it.csi.vigilsan.vigilsancommon.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsancommon.util.ModelMapper;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableWPersistenceApiServiceImpl;

@Service
public class SoggettoAuraService extends AuditableWPersistenceApiServiceImpl {

	@Autowired
	@Qualifier("soggettoAuraClient")
	private SoggettoAuraServiceClient soggettoAuraClient;

	@Autowired
	private ModelMapper modelMapper;

	public SoggettoAuraCustom getAuraSoggettoCustom(Integer sId, ScreeningEpatiteC richiesta) {
		try {
			ScreeningEpatiteCResponse response = soggettoAuraClient.getAuraSoggetto(richiesta);
			return modelMapper.fromScreeningEpatiteCResponseToSoggettoAuraCustom(response.getScreeningEpatiteCResult());
		} catch (SoapFaultException e) {
			var err = RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.AURA_ERRORE_SERVIZIO_ESTERNO_GENERICO);
			logErrorWhitAudit(sId, err, "getAuraSoggettoCustom",
					"cf ricercato: " + richiesta.getAssistito().getCodiceFiscale());
			throw err;

		} catch (Exception e) {
			var err = RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.AURA_ERRORE_SERVIZIO_ESTERNO_GENERICO);
			logErrorWhitAudit(sId, err, "getAuraSoggettoCustom",
					"cf ricercato: " + richiesta.getAssistito().getCodiceFiscale());
			throw err;
		}
	}

}
