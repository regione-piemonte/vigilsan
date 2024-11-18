/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaRidottaEstesa;
import it.csi.vigilsan.vigilsanutil.generic.util.DateUtils;

public class PraticaRidottaEstesaMapper implements RowMapper<PraticaRidottaEstesa> {

	@Override
	public PraticaRidottaEstesa mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new PraticaRidottaEstesa();

		res.setStrutturaId(rs.getObject("struttura_id", Integer.class));
		res.setEnteId(rs.getObject("ente_id", Integer.class));
		res.setPraticaTipoId(rs.getObject("pratica_tipo_id", Integer.class));
		res.setAzioneIdIniziale(rs.getObject("azione_id_iniziale", Integer.class));
		res.setDataoraChiusura(DateUtils.dateFromSqlTimestamp(rs.getTimestamp("dataora_chiusura")));
		res.setTotalCount(rs.getObject("total_count", Long.class));		
		res.setModuloId(rs.getObject("modulo_id", Integer.class));
		
		return res;
	}

}
