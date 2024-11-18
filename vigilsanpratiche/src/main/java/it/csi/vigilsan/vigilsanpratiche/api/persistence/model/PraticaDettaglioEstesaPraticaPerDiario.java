/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.model;

import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelPraticaDettaglioEstesaPratica;

public class PraticaDettaglioEstesaPraticaPerDiario extends ModelPraticaDettaglioEstesaPratica
		implements PraticaDettaglioInterface {
	private String appuntamentoNumero;
	private String prescrizioneNumero;

	public String getAppuntamentoNumero() {
		return appuntamentoNumero;
	}

	public void setAppuntamentoNumero(String appuntamentoNumero) {
		this.appuntamentoNumero = appuntamentoNumero;
	}

	public String getPrescrizioneNumero() {
		return prescrizioneNumero;
	}

	public void setPrescrizioneNumero(String prescrizioneNumero) {
		this.prescrizioneNumero = prescrizioneNumero;
	}
}
