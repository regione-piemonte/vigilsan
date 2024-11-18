/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanrilevazioni.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import it.csi.vigilsan.vigilsanrilevazioni.api.generated.dto.ModelModuloRidotto;
import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.model.RilevazioneEstesa;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.model.GenericColumntInterface;
import it.csi.vigilsan.vigilsanutil.generic.util.DateUtils;

public class RilevazioneEstesoDaInserireMapper extends RilevazioneMapper<RilevazioneEstesa> {

	public RilevazioneEstesoDaInserireMapper() {
		super(RilevazioneEstesa.class);
	}

	@Override
	public RilevazioneEstesa mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = super.mapRow(rs, rowNum);
		res.setModuloConfigCod(rs.getString("modulo_config_cod"));
		var modulo = new ModelModuloRidotto();
		GenericModuloEstesoMapper.setModuloId(rs, modulo);
		res.setModulo(modulo);
		return res;
	}

	@Override
	protected <T extends GenericColumntInterface> void generalMapRow(ResultSet rs, T res) throws SQLException {
		// non sono da compilare i valori generici
		res.setValiditaInizio(DateUtils.dateFromSqlTimestamp(rs.getTimestamp("validita_inizio")));
		res.setValiditaFine(DateUtils.dateFromSqlTimestamp(rs.getTimestamp("validita_fine")));
	}

}
