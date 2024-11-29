/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.model;

import it.csi.vigilsan.vigilsanutil.generic.util.paginazione.ListInterface;

public class StrutturaPaginata extends Struttura implements ListInterface {

	private Long totalCount;

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
}
