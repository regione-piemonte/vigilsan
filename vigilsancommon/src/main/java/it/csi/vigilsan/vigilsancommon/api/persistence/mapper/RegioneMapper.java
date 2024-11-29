/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsancommon.api.persistence.model.Regione;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class RegioneMapper extends GenericTableMapper implements RowMapper<Regione> {

	@Override
	public Regione mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new Regione();
		res.setRegioneId(rs.getObject("regione_id", Integer.class));
		res.setRegioneCod(rs.getString("regione_cod"));
		res.setRegioneDesc(rs.getString("regione_desc"));
		generalMapRow(rs, res);
		return res;
	}

}
