/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsancommon.api.persistence.model.Ente;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class EnteMapper extends GenericTableMapper implements RowMapper<Ente> {

	@Override
	public Ente mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new Ente();
		res.setEnteId(rs.getObject("ente_id", Integer.class));
		res.setEnteCod(rs.getString("ente_cod"));
		res.setEnteCodConfiguratore(rs.getString("ente_cod_configuratore"));
		res.setEnteDesc(rs.getString("ente_desc"));
		res.setEnteTipoId(rs.getObject("ente_tipo_id", Integer.class));
		generalMapRow(rs, res);
		return res;
	}

}
