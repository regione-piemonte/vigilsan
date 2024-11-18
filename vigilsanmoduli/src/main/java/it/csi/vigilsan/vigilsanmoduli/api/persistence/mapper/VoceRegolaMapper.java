/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanmoduli.api.generated.dto.ModelVoceRegola;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class VoceRegolaMapper extends GenericTableMapper implements RowMapper<ModelVoceRegola> {

	@Override
	public ModelVoceRegola mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new ModelVoceRegola();
		res.setModuloVoceRegolaExec(rs.getString("modulo_voce_regola_exec"));
		res.setModuloVoceRegolaTipo(rs.getString("modulo_voce_regola_tipo"));
		res.setModuloVoceRegolaErrore(rs.getString("modulo_voce_regola_errore"));
		return res;
	}

}
