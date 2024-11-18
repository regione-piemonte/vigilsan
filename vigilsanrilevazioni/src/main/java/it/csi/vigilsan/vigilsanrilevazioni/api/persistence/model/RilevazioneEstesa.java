/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanrilevazioni.api.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.csi.vigilsan.vigilsanrilevazioni.api.generated.dto.ModelRilevazioneEsteso;
import it.csi.vigilsan.vigilsanutil.generic.util.paginazione.ListInterface;

public class RilevazioneEstesa extends ModelRilevazioneEsteso implements rilevazioneInterface, ListInterface {

	private Long totalCount;

	private String moduloConfigCod;

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	@JsonIgnore
	public String getModuloConfigCod() {
		return moduloConfigCod;
	}

	public void setModuloConfigCod(String moduloConfigCod) {
		this.moduloConfigCod = moduloConfigCod;
	}

}
