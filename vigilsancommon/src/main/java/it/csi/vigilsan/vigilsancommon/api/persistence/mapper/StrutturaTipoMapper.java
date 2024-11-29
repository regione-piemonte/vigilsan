/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsancommon.api.persistence.model.StrutturaTipo;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class StrutturaTipoMapper extends GenericTableMapper implements RowMapper<StrutturaTipo> {

	@Override
	public StrutturaTipo mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new StrutturaTipo();
		res.setStrutturaTipoId(rs.getObject("struttura_tipo_id", Integer.class));
		res.setStrutturaTipoCod(rs.getString("struttura_tipo_cod"));
		res.setStrutturaTipoDesc(rs.getString("struttura_tipo_desc"));
		generalMapRow(rs, res);
		return res;
	}

}
