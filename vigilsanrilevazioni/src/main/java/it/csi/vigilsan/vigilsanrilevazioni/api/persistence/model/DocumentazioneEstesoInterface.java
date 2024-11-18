/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanrilevazioni.api.persistence.model;

import it.csi.vigilsan.vigilsanrilevazioni.api.generated.dto.ModelModuloRidotto;

public interface DocumentazioneEstesoInterface extends DocumentazioneInterface {

	ModelModuloRidotto getModulo();

	void setModulo(ModelModuloRidotto modulo);

	String getModuloConfigCod();

	void setModuloConfigCod(String moduloConfigCod);

	String getModuloConfigDesc();

	void setModuloConfigDesc(String moduloConfigDesc);

}