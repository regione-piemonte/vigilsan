/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaTipo;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PrescrizioneTipo;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class PraticaTipoMapper extends GenericTableMapper implements RowMapper<PraticaTipo> {

	@Override
	public PraticaTipo mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new PraticaTipo();
		res.setPraticaTipoId(rs.getObject("pratica_tipo_id", Integer.class));
		res.setPraticaTipoCod(rs.getString("pratica_tipo_cod"));
		res.setPraticaTipoDesc(rs.getString("pratica_tipo_desc"));
		generalMapRow(rs, res);
		return res;
	}

}
