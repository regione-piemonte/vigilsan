/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsancommon.api.persistence.model.Ruolo;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class RuoloMapper extends GenericTableMapper implements RowMapper<Ruolo> {

	@Override
	public Ruolo mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new Ruolo();
		res.setRuoloId(rs.getObject("ruolo_id", Integer.class));
		res.setRuoloCod(rs.getString("ruolo_cod"));
		res.setRuoloDesc(rs.getString("ruolo_desc"));
		generalMapRow(rs, res);
		return res;
	}

}
