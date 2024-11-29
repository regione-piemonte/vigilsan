/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.service;

import java.io.IOException;
import java.util.List;

import it.csi.vigilsan.vigilsancommon.api.generated.dto.ModelProfiloUtente;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.ApplicativoOperazioneRidottoCustom;

public interface UtenteInterface {

	ModelProfiloUtente getCurrentUserFromConfiguratore(String shibIdentitaCodiceFiscale, String xRequestId,
			String xForwardedFor, String xCodiceServizio, String token) throws IOException;

	void logout(String shibIdentitaCodiceFiscale, Integer sId);

	ModelProfiloUtente getCurrentUserFromShibIdentitaForBatch(String shibIdentitaCodiceFiscale, String xForwardedFor);

	List<ApplicativoOperazioneRidottoCustom> getPermessiByProfiloIdAndApplicativoId(Integer profiloId, Integer applicativoId, Integer sId, Integer sId2);

	ModelProfiloUtente modificaSessione(ModelProfiloUtente body, String xForwardedFor, Integer sId);


}