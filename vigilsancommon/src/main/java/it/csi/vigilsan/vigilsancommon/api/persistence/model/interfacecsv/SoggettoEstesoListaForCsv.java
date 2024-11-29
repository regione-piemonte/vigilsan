/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.model.interfacecsv;

import java.util.Date;

import it.csi.vigilsan.vigilsancommon.api.persistence.model.SoggettoEstesoLista;

public class SoggettoEstesoListaForCsv extends SoggettoEstesoLista implements SoggettoCsvInterface {

	@Override
	public Boolean getPresenteConfiguratore() {
		return isPresenteConfiguratore();
	}

	@Override
    public String getruoloStrutturaRuoloStrutturaDesc() {
        return getRuoloStruttura().getRuoloStrutturaSoggettoDesc();
    }

	@Override
	public String getUltimoStatoospiteStatoDesc() {
		return getUltimoStato().getOspiteStatoDesc();
	}

	@Override
	public Date getUltimoStatoDataMovimento() {
		return getUltimoStato().getDataMovimento();
	}


}
