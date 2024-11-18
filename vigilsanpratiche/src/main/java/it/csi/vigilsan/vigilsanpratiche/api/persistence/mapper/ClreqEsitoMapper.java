/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.ClreqEsito;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaClreq;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class ClreqEsitoMapper extends GenericTableMapper implements RowMapper<ClreqEsito> {

	@Override
	public ClreqEsito mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new ClreqEsito();
		res.setClreqEsitoId(rs.getObject("clreq_esito_id", Integer.class));
		res.setClreqEsitoCod(rs.getString("clreq_esito_cod"));
		res.setClreqEsitoDesc(rs.getString("clreq_esito_desc"));
		res.setClreqEsitoOrd(rs.getString("clreq_esito_ord"));
		generalMapRow(rs, res);
		return res;
	}

}
