/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsancommon.api.persistence.model.OspiteCondizioni;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class  OspiteCondzioniMapper extends GenericTableMapper implements RowMapper<OspiteCondizioni> {
	
	@Override
	public OspiteCondizioni mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new OspiteCondizioni();
			res.setOspiteCondizioniId(rs.getObject("ospite_condizioni_id", Integer.class));
			res.setOspiteCondizioniCod(rs.getString("ospite_condizioni_cod"));
			res.setOspiteCondizioniDesc(rs.getString("ospite_condizioni_desc"));
			res.setOspiteCondizioniOrd(rs.getString("ospite_condizioni_ord"));
			res.setOspiteCondizioniHint(rs.getString("ospite_condizioni_hint"));
		generalMapRow(rs, res);
		return  res;
	}

}
