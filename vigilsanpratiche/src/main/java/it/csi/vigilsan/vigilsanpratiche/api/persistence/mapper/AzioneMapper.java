/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.Azione;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaStato;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class AzioneMapper extends GenericTableMapper implements RowMapper<Azione> {

	@Override
	public Azione mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new Azione();
		res.setAzioneId(rs.getObject("azione_id", Integer.class));
		res.setAzioneCod(rs.getString("azione_cod"));
		res.setAzioneDesc(rs.getString("azione_desc"));
		generalMapRow(rs, res);
		return res;
	}

}
