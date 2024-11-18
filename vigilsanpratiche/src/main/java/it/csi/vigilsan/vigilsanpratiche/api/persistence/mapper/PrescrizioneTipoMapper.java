/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PrescrizioneTipo;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class PrescrizioneTipoMapper extends GenericTableMapper implements RowMapper<PrescrizioneTipo> {

	@Override
	public PrescrizioneTipo mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new PrescrizioneTipo();
		res.setPrescrizioneTipoId(rs.getObject("prescrizione_tipo_id", Integer.class));
		res.setPrescrizioneTipoCod(rs.getString("prescrizione_tipo_cod"));
		res.setPrescrizioneTipoDesc(rs.getString("prescrizione_tipo_desc"));

		generalMapRow(rs, res);
		return res;
	}

}
