/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanrilevazioni.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import it.csi.vigilsan.vigilsanrilevazioni.api.generated.dto.ModelModuloRidotto;
import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.model.DocumentoEsteso;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.model.GenericColumntInterface;
import it.csi.vigilsan.vigilsanutil.generic.util.DateUtils;

public class DocumentazioneEstesaDaInserireMapper extends DocumentazioneMapper<DocumentoEsteso> {

	public DocumentazioneEstesaDaInserireMapper() {
		super(DocumentoEsteso.class);
	}

	@Override
	public DocumentoEsteso mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = super.mapRow(rs, rowNum);
		res.setModuloConfigCod(rs.getString("modulo_config_cod"));
		res.setModuloConfigDesc(rs.getString("modulo_config_desc"));
		res.setDocFlgObbligatorio(rs.getBoolean("doc_flg_obbligatorio"));
		var modulo = new ModelModuloRidotto();
		GenericModuloEstesoMapper.setModuloId(rs, modulo);
		res.setModulo(modulo);
		return res;
	}

	@Override
	protected <T extends GenericColumntInterface> void generalMapRow(ResultSet rs, T res) throws SQLException {
		// non sono da compilare i valori generici
	}

}
