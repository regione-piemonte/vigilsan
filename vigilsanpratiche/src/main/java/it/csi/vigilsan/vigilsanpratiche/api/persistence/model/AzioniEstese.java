/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.model;

import java.util.ArrayList;
import java.util.List;

public class AzioniEstese {
	private List<AzioneEstesa> pratica = new ArrayList<>();
	private List<AzioneEstesa> prescrizione = new ArrayList<>();
	private List<AzioneEstesa> appuntamento = new ArrayList<>();

	public List<AzioneEstesa> getPratica() {
		return pratica;
	}

	public void setPratica(List<AzioneEstesa> pratica) {
		this.pratica = pratica;
	}

	public List<AzioneEstesa> getPrescrizione() {
		return prescrizione;
	}

	public void setPrescrizione(List<AzioneEstesa> prescrizione) {
		this.prescrizione = prescrizione;
	}

	public List<AzioneEstesa> getAppuntamento() {
		return appuntamento;
	}

	public void setAppuntamento(List<AzioneEstesa> appuntamento) {
		this.appuntamento = appuntamento;
	}

}
