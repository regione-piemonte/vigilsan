/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsancommon.api.persistence.model.StrutturaSoggetto;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class StrutturaSoggettoMapper extends GenericTableMapper implements RowMapper<StrutturaSoggetto> {

	@Override
	public StrutturaSoggetto mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new StrutturaSoggetto();
		res.setStrSoggId(rs.getObject("str_sogg_id", Integer.class));
		res.setStrutturaId(rs.getObject("struttura_id", Integer.class));
		res.setSoggettoId(rs.getObject("soggetto_id", Integer.class));
		res.setRuoloStrutturaSoggettoId(rs.getObject("ruolo_struttura_soggetto_id", Integer.class));
		generalMapRow(rs, res);
		return res;
	}

}
