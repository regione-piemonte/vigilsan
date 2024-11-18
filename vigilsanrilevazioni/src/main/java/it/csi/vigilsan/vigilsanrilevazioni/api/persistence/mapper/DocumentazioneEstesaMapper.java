/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanrilevazioni.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import it.csi.vigilsan.vigilsanrilevazioni.api.generated.dto.ModelModuloRidotto;
import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.model.DocumentazioneEstesoInterface;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.model.GenericColumntInterface;

public class DocumentazioneEstesaMapper<T extends DocumentazioneEstesoInterface> extends DocumentazioneMapper<T>  {

    // Costruttore che richiede il parametro di tipo Class<T>
    public DocumentazioneEstesaMapper(Class<T> clazz) {
		super(clazz);
    }
    
    
	@Override
	public T mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = super.mapRow(rs, rowNum);
		var modulo = new ModelModuloRidotto();
		GenericModuloEstesoMapper.setModuloId(rs, modulo);
		GenericModuloEstesoMapper.setParametriModuloCompilato(rs, modulo);
		res.setModulo(modulo);
		res.setModuloConfigDesc(rs.getString("modulo_config_desc"));
		res.setModuloConfigCod(rs.getString("modulo_config_cod"));
		return res;
	}

	@Override
	protected <T extends GenericColumntInterface> void generalMapRow(ResultSet rs, T res) throws SQLException {
		// non sono da compilare i valori generici
	}

}
