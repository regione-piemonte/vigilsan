/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsancommon.api.persistence.model.ArpeAttivita;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class ArpeAttivitaMapper extends GenericTableMapper implements RowMapper<ArpeAttivita> {

	@Override
	public ArpeAttivita mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new ArpeAttivita();
		res.setArpeAttivitaId(rs.getObject("arpe_attivita_id", Integer.class));
		res.setArpeAttivitaCod(rs.getString("arpe_attivita_cod"));
		res.setArpeAttivitaDesc(rs.getString("arpe_attivita_desc"));
		generalMapRow(rs, res);
		return res;
	}

}
