/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsancommon.api.persistence.model.Profilo;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class ProfiloMapper extends GenericTableMapper implements RowMapper<Profilo> {

	@Override
	public Profilo mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new Profilo();
		res.setProfiloId(rs.getObject("profilo_id", Integer.class));
		res.setProfiloCod(rs.getString("profilo_cod"));
		res.setProfiloDesc(rs.getString("profilo_desc"));
		generalMapRow(rs, res);
		return res;
	}

}
