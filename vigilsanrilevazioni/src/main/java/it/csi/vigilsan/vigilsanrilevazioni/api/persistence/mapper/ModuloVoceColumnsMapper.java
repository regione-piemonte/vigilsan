/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanrilevazioni.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.model.ModuloConfigRidotto;
import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.model.ModuloVoceColumns;

public class ModuloVoceColumnsMapper implements RowMapper<ModuloVoceColumns> {

	@Override
	public ModuloVoceColumns mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new ModuloVoceColumns();
		res.setModuloVoceColname(rs.getString("modulo_voce_colname"));
		res.setModuloVoceColtype(rs.getString("modulo_voce_coltype"));
		return res;
	}

}
