/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsancommon.api.persistence.model.OspiteStato;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class OspiteStatoMapper extends GenericTableMapper implements RowMapper<OspiteStato> {

	@Override
	public OspiteStato mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new OspiteStato();
		res.setOspiteStatoId(rs.getObject("ospite_stato_id", Integer.class));
		res.setOspiteStatoCod(rs.getString("ospite_stato_cod"));
		res.setOspiteStatoDesc(rs.getString("ospite_stato_desc"));
		res.setOspiteStatoHint(rs.getString("ospite_stato_hint"));
		res.setOspiteStatoOrd(rs.getString("ospite_stato_ord"));
		res.setFlgPosto(rs.getBoolean("flg_posto"));
		res.setFlgPresenza(rs.getBoolean("flg_presenza"));
		generalMapRow(rs, res);
		return res;
	}

}
