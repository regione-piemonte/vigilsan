/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.model;

import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelAzioneEstesa;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.model.GenericColumntInterface;

public class AzioneEstesa extends ModelAzioneEstesa implements GenericColumntInterface {
	
	private String statoIdLIsta;

	public String getStatoIdLIsta() {
		return statoIdLIsta;
	}

	public void setStatoIdLIsta(String statoIdLIsta) {
		this.statoIdLIsta = statoIdLIsta;
	}
}
