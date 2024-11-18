/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanmoduli.api.persistence.model.ModuloCompilato;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class ModuloCompilatoMapper extends GenericTableMapper implements RowMapper<ModuloCompilato> {

	@Override
	public ModuloCompilato mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new ModuloCompilato();
		res.setModuloCompilatoId(rs.getObject("modulo_compilato_id", Integer.class));
		res.setModuloId(rs.getObject("modulo_id", Integer.class));
		res.setNote(rs.getString("note"));
		generalMapRow(rs, res);
		return res;
	}

}
