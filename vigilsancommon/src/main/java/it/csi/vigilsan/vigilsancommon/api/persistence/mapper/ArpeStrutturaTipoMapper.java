/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsancommon.api.persistence.model.ArpeStrutturaTipo;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class ArpeStrutturaTipoMapper extends GenericTableMapper implements RowMapper<ArpeStrutturaTipo> {

	@Override
	public ArpeStrutturaTipo mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new ArpeStrutturaTipo();
		res.setArpeStrutturaTipoId(rs.getObject("arpe_struttura_tipo_id", Integer.class));
		res.setArpeStrutturaTipoCod(rs.getString("arpe_struttura_tipo_cod"));
		res.setArpeStrutturaTipoDesc(rs.getString("arpe_struttura_tipo_desc"));
		generalMapRow(rs, res);
		return res;
	}

}
