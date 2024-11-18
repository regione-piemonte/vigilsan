/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.AppuntamentoTipo;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class AppuntamentoTipoMapper extends GenericTableMapper implements RowMapper<AppuntamentoTipo> {

	@Override
	public AppuntamentoTipo mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new AppuntamentoTipo();
		res.setAppuntamentoTipoId(rs.getObject("appuntamento_tipo_id", Integer.class));
		res.setAppuntamentoTipoCod(rs.getString("appuntamento_tipo_cod"));
		res.setAppuntamentoTipoDesc(rs.getString("appuntamento_tipo_desc"));

		generalMapRow(rs, res);
		return res;
	}

}
