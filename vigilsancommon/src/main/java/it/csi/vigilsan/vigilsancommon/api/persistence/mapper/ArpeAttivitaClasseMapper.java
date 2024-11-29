/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsancommon.api.persistence.model.ArpeAttivitaClasse;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class ArpeAttivitaClasseMapper extends GenericTableMapper implements RowMapper<ArpeAttivitaClasse> {

	@Override
	public ArpeAttivitaClasse mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new ArpeAttivitaClasse();
		res.setArpeAttivitaClasseId(rs.getObject("arpe_attivita_classe_id", Integer.class));
		res.setArpeAttivitaClasseCod(rs.getString("arpe_attivita_classe_cod"));
		res.setArpeAttivitaClasseDesc(rs.getString("arpe_attivita_classe_desc"));
		generalMapRow(rs, res);
		return res;
	}

}
