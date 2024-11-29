/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsancommon.api.persistence.model.Ente;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.EnteSoggetto;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class EnteSoggettoMapper extends GenericTableMapper implements RowMapper<EnteSoggetto> {

	@Override
	public EnteSoggetto mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new EnteSoggetto();
		res.setEnteId(rs.getObject("ente_id", Integer.class));
		res.setSoggettoId(rs.getObject("soggetto_id", Integer.class));
		res.setEnteSoggId(rs.getObject("ente_sogg_id", Integer.class));
		res.setRuoloEnteSoggettoId(rs.getObject("ruolo_ente_soggetto_id", Integer.class));
		generalMapRow(rs, res);
		return res;
	}

}
