/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsancommon.api.persistence.model.ArpeAssistenzaTipo;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class ArpeAssistenzaTipoMapper extends GenericTableMapper implements RowMapper<ArpeAssistenzaTipo> {

	@Override
	public ArpeAssistenzaTipo mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new ArpeAssistenzaTipo();
		res.setArpeAssistenzaTipoId(rs.getObject("arpe_assistenza_tipo_id", Integer.class));
		res.setArpeAssistenzaTipoCod(rs.getString("arpe_assistenza_tipo_cod"));
		res.setArpeAssistenzaTipoDesc(rs.getString("arpe_assistenza_tipo_desc"));
		generalMapRow(rs, res);
		return res;
	}

}
