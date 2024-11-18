/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaStato;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class PraticaStatoMapper extends GenericTableMapper implements RowMapper<PraticaStato> {

	@Override
	public PraticaStato mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new PraticaStato();
		res.setPraticaStatoId(rs.getObject("pratica_stato_id", Integer.class));
		res.setPraticaStatoCod(rs.getString("pratica_stato_cod"));
		res.setPraticaStatoDesc(rs.getString("pratica_stato_desc"));
		res.setPraticaStatoFinale(rs.getBoolean("pratica_stato_finale"));
		generalMapRow(rs, res);
		return res;
	}

}
