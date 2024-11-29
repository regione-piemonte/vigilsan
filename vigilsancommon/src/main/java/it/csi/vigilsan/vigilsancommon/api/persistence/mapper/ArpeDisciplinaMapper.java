/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsancommon.api.persistence.model.ArpeDisciplina;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class ArpeDisciplinaMapper extends GenericTableMapper implements RowMapper<ArpeDisciplina> {

	@Override
	public ArpeDisciplina mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new ArpeDisciplina();
		res.setArpeDisciplinaId(rs.getObject("arpe_disciplina_id", Integer.class));
		res.setArpeDisciplinaCod(rs.getString("arpe_disciplina_cod"));
		res.setArpeDisciplinaDesc(rs.getString("arpe_disciplina_desc"));
		generalMapRow(rs, res);
		return res;
	}

}
