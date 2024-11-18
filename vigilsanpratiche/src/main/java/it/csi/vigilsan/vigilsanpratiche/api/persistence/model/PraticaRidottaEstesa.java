/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelPraticaRidottaEstesa;
import it.csi.vigilsan.vigilsanutil.generic.util.paginazione.ListInterface;

public class PraticaRidottaEstesa extends ModelPraticaRidottaEstesa implements PraticaInterface, ListInterface {

	private Integer azioneIdIniziale;

	private Long totalCount;

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	@JsonIgnore
	public Integer getAzioneIdIniziale() {
		return azioneIdIniziale;
	}

	public void setAzioneIdIniziale(Integer azioneIdIniziale) {
		this.azioneIdIniziale = azioneIdIniziale;
	}
}
