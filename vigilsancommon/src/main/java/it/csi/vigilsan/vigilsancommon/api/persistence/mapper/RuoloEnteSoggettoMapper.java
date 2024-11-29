/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsancommon.api.persistence.model.EnteSoggetto;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.RuoloEnteSoggetto;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class RuoloEnteSoggettoMapper extends GenericTableMapper implements RowMapper<RuoloEnteSoggetto> {

	@Override
	public RuoloEnteSoggetto mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new RuoloEnteSoggetto();
		res.setRuoloEnteSoggettoId(rs.getObject("ruolo_ente_soggetto_id", Integer.class));
		res.setRuoloEnteSoggettoDesc(rs.getString("ruolo_ente_soggetto_desc"));
		res.setRuoloEnteSoggettoCod(rs.getString("ruolo_ente_soggetto_cod"));
		generalMapRow(rs, res);
		return res;
	}

}
