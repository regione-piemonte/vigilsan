/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.model;

import java.util.List;

import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelAppuntamentoEsteso;

public class AppuntamentoEstesoPerDiario extends ModelAppuntamentoEsteso implements AppuntamentoInterface {
	private List<AppuntamentoSoggettoEstesoWsoggetto> appuntamentoSoggettoLista;

	public List<AppuntamentoSoggettoEstesoWsoggetto> getAppuntamentoSoggettoLista() {
		return appuntamentoSoggettoLista;
	}

	public void setAppuntamentoSoggettoLista(List<AppuntamentoSoggettoEstesoWsoggetto> appuntamentoSoggettoLista) {
		this.appuntamentoSoggettoLista = appuntamentoSoggettoLista;
	}
}
