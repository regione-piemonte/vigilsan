/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.model.interfacecsv;

import java.util.Date;

import it.csi.vigilsan.vigilsancommon.api.generated.dto.ModelOspiteStatoUltimo;
import it.csi.vigilsan.vigilsancommon.api.generated.dto.ModelRuoloStrutturaSoggetto;
import it.csi.vigilsan.vigilsancommon.api.generated.dto.ModelStrutturaSoggetto;
import it.csi.vigilsan.vigilsanutil.generic.util.csv.GenericColumnCSVIgnoreAllInterface;
import it.csi.vigilsan.vigilsanutil.generic.util.csv.annotation.CSVColumn;
import it.csi.vigilsan.vigilsanutil.generic.util.csv.annotation.CSVExclude;

public interface SoggettoCsvInterface extends GenericColumnCSVIgnoreAllInterface {

	@CSVExclude
	Integer getSoggettoId();

	@CSVColumn(value = "codiceFiscale", order = 200)
	String getCodiceFiscale();

	@CSVColumn(value = "nome", order = 300)
	String getNome();

	@CSVColumn(value = "cognome", order = 400)
	String getCognome();

	@CSVColumn(value = "dataNascita", order = 500)
	Date getDataNascita();

	@CSVColumn(value = "ruoloStrutturaDesc", nestedField = "ruoloStrutturaDesc", order = 501)
	public String getruoloStrutturaRuoloStrutturaDesc();

	@CSVColumn(value = "ultimoStatoDesc", nestedField = "ospiteStatoDesc", order = 502)
	String getUltimoStatoospiteStatoDesc();
	
	@CSVColumn(value = "ultimoStatoData", nestedField = "dataMovimento", order = 503)
	Date getUltimoStatoDataMovimento();

	@CSVExclude
	Boolean getPresenteConfiguratore();

	@CSVColumn(value = "dataPrimoIngresso", order = 700)
	Date getDataPrimoIngresso();

	@CSVColumn(value = "dataUltimaUscita", order = 800)
	Date getDataUltimaUscita();

	@CSVExclude
	ModelStrutturaSoggetto getStrutturaSoggetto();

	@CSVExclude
	ModelRuoloStrutturaSoggetto getRuoloStruttura();

	@CSVExclude
	ModelOspiteStatoUltimo getUltimoStato();

	@CSVExclude
	public Long getTotalCount();
}