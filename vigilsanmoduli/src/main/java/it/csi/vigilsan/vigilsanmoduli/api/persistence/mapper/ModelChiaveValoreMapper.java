/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanmoduli.api.generated.dto.ModelChiaveValore;
import it.csi.vigilsan.vigilsanmoduli.api.generated.dto.ModelModuloRegolaRidotto;

public class ModelChiaveValoreMapper implements RowMapper<ModelChiaveValore> {

	@Override
	public ModelChiaveValore mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new ModelChiaveValore();
		res.setChiave(rs.getString("load_key"));
		return res;
	}

}
