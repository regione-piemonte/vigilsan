/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsancommon.api.persistence.model.StrutturaNatura;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class StrutturaNaturaMapper extends GenericTableMapper implements RowMapper<StrutturaNatura> {

	@Override
	public StrutturaNatura mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new StrutturaNatura();
		res.setStrutturaNaturaId(rs.getObject("struttura_natura_id", Integer.class));
		res.setStrutturaNaturaCod(rs.getString("struttura_natura_cod"));
		res.setStrutturaNaturaCodArpe(rs.getString("struttura_natura_cod_arpe"));
		res.setStrutturaNaturaDesc(rs.getString("struttura_natura_desc"));
		generalMapRow(rs, res);
		return res;
	}

}
