/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsancommon.api.persistence.model.EnteStruttura;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class EnteStrutturaMapper extends GenericTableMapper implements RowMapper<EnteStruttura> {

	@Override
	public EnteStruttura mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new EnteStruttura();
		res.setEnteStrId(rs.getObject("ente_str_id", Integer.class));
		res.setEnteId(rs.getObject("ente_id", Integer.class));
		res.setStrutturaId(rs.getObject("struttura_id", Integer.class));
		generalMapRow(rs, res);
		return res;
	}

}
