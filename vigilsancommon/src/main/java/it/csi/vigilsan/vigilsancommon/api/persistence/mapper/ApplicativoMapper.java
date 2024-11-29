/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsancommon.api.persistence.model.Applicativo;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class ApplicativoMapper extends GenericTableMapper implements RowMapper<Applicativo> {

	@Override
	public Applicativo mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new Applicativo();
		res.setApplicativoId(rs.getObject("applicativo_id", Integer.class));
		res.setApplicativoCod(rs.getString("applicativo_cod"));
		res.setApplicativoDesc(rs.getString("applicativo_desc"));
		generalMapRow(rs, res);
		return res;
	}

}
