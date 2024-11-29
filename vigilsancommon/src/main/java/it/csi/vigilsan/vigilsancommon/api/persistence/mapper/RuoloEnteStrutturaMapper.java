/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsancommon.api.persistence.model.RuoloEnteStruttura;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class RuoloEnteStrutturaMapper extends GenericTableMapper implements RowMapper<RuoloEnteStruttura> {

	@Override
	public RuoloEnteStruttura mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new RuoloEnteStruttura();
		res.setRuoloEnteStrutturaId(rs.getObject("ruolo_ente_struttura_id", Integer.class));
		res.setRuoloEnteStrutturaCod(rs.getString("ruolo_ente_struttura_cod"));
		res.setRuoloEnteStrutturaDesc(rs.getString("ruolo_ente_struttura_desc"));
		generalMapRow(rs, res);
		return res;
	}
}
