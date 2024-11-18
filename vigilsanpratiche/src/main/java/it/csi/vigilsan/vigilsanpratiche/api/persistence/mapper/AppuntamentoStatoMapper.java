/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.AppuntamentoStato;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class AppuntamentoStatoMapper extends GenericTableMapper implements RowMapper<AppuntamentoStato> {

	@Override
	public AppuntamentoStato mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new AppuntamentoStato();
		res.setAppuntamentoStatoId(rs.getObject("appuntamento_stato_id", Integer.class));
		res.setAppuntamentoStatoCod(rs.getString("appuntamento_stato_cod"));
		res.setAppuntamentoStatoDesc(rs.getString("appuntamento_stato_desc"));
		res.setAppuntamentoStatoFinale(rs.getBoolean("appuntamento_stato_finale"));
		generalMapRow(rs, res);
		return res;
	}

}
