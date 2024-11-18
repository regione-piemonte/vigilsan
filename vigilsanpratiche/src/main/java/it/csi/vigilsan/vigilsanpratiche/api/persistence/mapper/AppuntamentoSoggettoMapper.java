/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.AppuntamentoSoggetto;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class AppuntamentoSoggettoMapper extends GenericTableMapper implements RowMapper<AppuntamentoSoggetto> {

	@Override
	public AppuntamentoSoggetto mapRow(ResultSet rs, int rowNum) throws SQLException {

		AppuntamentoSoggetto res = new AppuntamentoSoggetto();
		res.setAppuntamentoSoggettoId(rs.getObject("appuntamento_soggetto_id", Integer.class));
		res.setAppuntamentoId(rs.getObject("appuntamento_id", Integer.class));
		res.setSoggettoId(rs.getObject("soggetto_id", Integer.class));
		generalMapRow(rs, res);
		return res;
	}

}
