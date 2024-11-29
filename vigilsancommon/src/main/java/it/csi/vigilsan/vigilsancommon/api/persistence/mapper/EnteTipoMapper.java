/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsancommon.api.persistence.model.Ente;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.EnteTipo;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class EnteTipoMapper extends GenericTableMapper implements RowMapper<EnteTipo> {

	@Override
	public EnteTipo mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new EnteTipo();
		res.setEnteTipoId(rs.getObject("ente_tipo_id", Integer.class));
		res.setEnteTipoCod(rs.getString("ente_tipo_cod"));
		res.setEnteTipoDesc(rs.getString("ente_tipo_desc"));
		generalMapRow(rs, res);
		return res;
	}

}
