/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.vigilsan.vigilsanmoduli.api.service;

import it.csi.vigilsan.vigilsanmoduli.api.generated.dto.ModelModulo;
import it.csi.vigilsan.vigilsanmoduli.api.generated.dto.ModelModuloEsteso;
import it.csi.vigilsan.vigilsanmoduli.api.generated.dto.ModelModuloPost;
import it.csi.vigilsan.vigilsanutil.generic.util.pdf.dto.FileCustom;

public interface ModuloService {
	
	Integer getModuloIdFromModuloCompilatoId(Integer sId, Integer moduloCompilatoId);

	Integer saveModulo(Integer sId, ModelModuloPost body, String shibIdentitaCodiceFiscale);

	ModelModuloPost updateOrInsertModuloCompilato(ModelModuloPost body, String shibIdentitaCodiceFiscale, Integer sId);

	FileCustom getPdf(Integer sId, ModelModulo modulo);

	ModelModuloEsteso getModulo(Integer sId, Integer moduloId, Integer moduloCompilatoId, Boolean ridotto);

}