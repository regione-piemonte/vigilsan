/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.Azione;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaClreq;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class PraticaClreqMapper extends GenericTableMapper implements RowMapper<PraticaClreq> {

	@Override
	public PraticaClreq mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new PraticaClreq();
		res.setPraticaClreqId(rs.getObject("pratica_clreq_id", Integer.class));
		res.setPraticaId(rs.getObject("pratica_id", Integer.class));
		res.setPrescrizioneId(rs.getObject("prescrizione_id", Integer.class));
		res.setAppuntamentoId(rs.getObject("appuntamento_id", Integer.class));
		res.setClreqId(rs.getObject("clreq_id", Integer.class));
		generalMapRow(rs, res);
		return res;
	}

}
