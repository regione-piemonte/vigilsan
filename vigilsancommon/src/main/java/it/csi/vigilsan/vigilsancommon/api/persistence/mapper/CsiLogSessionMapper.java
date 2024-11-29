/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsancommon.api.persistence.model.CsiLogSession;
import it.csi.vigilsan.vigilsanutil.generic.util.DateUtils;

public class CsiLogSessionMapper implements RowMapper<CsiLogSession> {

	@Override
	public CsiLogSession mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new CsiLogSession();
		res.setSessionId(rs.getObject("session_id", Integer.class));
		res.setSessionLogin(DateUtils.dateFromSqlTimestamp(rs.getTimestamp("session_login")));
		res.setSessionExpires(DateUtils.dateFromSqlTimestamp(rs.getTimestamp("session_expires")));
		res.setSessionLogout(DateUtils.dateFromSqlTimestamp(rs.getTimestamp("session_logout")));
		res.setIpAddress(rs.getString("ip_address"));
		res.setCfUtente(rs.getString("cf_utente"));
		res.setSoggettoId(rs.getObject("soggetto_id", Integer.class));
		res.setRuoloId(rs.getObject("ruolo_id", Integer.class));
		res.setApplicativoId(rs.getObject("applicativo_id", Integer.class));
		res.setStrutturaId(rs.getObject("struttura_id", Integer.class));
		res.setEnteId(rs.getObject("ente_id", Integer.class));
		res.setFlgAccessoPua(rs.getBoolean("flg_accesso_pua"));
		
		return res;
	}

}
