/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanmoduli.api.generated.dto.ModelModuloRegolaRidotto;

public class ModelModuloRegolaRidottoMapper implements RowMapper<ModelModuloRegolaRidotto> {

	@Override
	public ModelModuloRegolaRidotto mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new ModelModuloRegolaRidotto();
		res.setModuloRegolaId(rs.getObject("modulo_regola_id", Integer.class));
		res.setModuloRegolaErrore(rs.getString("modulo_regola_errore"));
		res.setModuloRegolaTipo(rs.getString("modulo_regola_tipo"));
		res.setModuloRegolaExec(rs.getString("modulo_regola_exec"));
		return res;
	}

}
