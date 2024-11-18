/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsanpratiche;

import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelAzioniEstese;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelPraticaEstesa;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelPraticaRequisiti;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelZipLista;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.impl.GetApiEnumInterface;

public enum VigilsanPraticheGetApiEnum implements GetApiEnumInterface{

	GET_PRATICA_AZIONE("/api/pratica/azione",ModelAzioniEstese.class),
	GET_PRATICA("/api/pratica",ModelPraticaEstesa.class),
	GET_PRATICA_MODULI_ZIP_LISTA("/api/pratica/moduli/zip/lista",ModelZipLista.class),
	GET_PRATICA_REQUISITI("/api/pratica/requisiti", ModelPraticaRequisiti.class),
	;
	
	private final String url;
	private final Class<?> clazz;

	private VigilsanPraticheGetApiEnum(String url) {
		this.url = url;
		this.clazz = null;
	}

	VigilsanPraticheGetApiEnum(String url, Class<?> clazz) {
		this.url = url;
		this.clazz = clazz;
	}

	public String getUrl() {
		return url;
	}

	public Class<?> getClazz() {
		return clazz;
	}

}
