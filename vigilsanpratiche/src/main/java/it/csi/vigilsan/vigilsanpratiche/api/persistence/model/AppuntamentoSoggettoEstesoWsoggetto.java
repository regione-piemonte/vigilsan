/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.model;

import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelAppuntamentoSoggettoEsteso;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.model.GenericColumntInterface;

public class AppuntamentoSoggettoEstesoWsoggetto extends ModelAppuntamentoSoggettoEsteso implements GenericColumntInterface {
	private String nome;
	private String cognome;
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
}
