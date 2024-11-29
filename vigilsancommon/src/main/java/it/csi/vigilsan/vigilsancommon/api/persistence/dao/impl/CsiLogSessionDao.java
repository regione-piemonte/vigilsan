/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.CsiLogSessionMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.CsiLogSession;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementCsiLogSession;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementNatura;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementProfilo;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementRuolo;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementSoggetto;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementStruttura;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class CsiLogSessionDao extends AbstractPersistence implements Dao<CsiLogSession> {


	private Integer getId(CsiLogSession t) {
		return t.getSessionId();
	}

	private void setSpecificParameter(CsiLogSession t, MapSqlParameterSource params) {
		params.addValue("sessionLogin", t.getSessionLogin());
		params.addValue("sessionLogout", t.getSessionLogout());
		params.addValue("sessionExpires", t.getSessionExpires());
		params.addValue("ipAddress", t.getIpAddress());
		params.addValue("cfUtente", t.getCfUtente());
		params.addValue("flgAccessoPua", t.getFlgAccessoPua());
		params.addValue(SqlStatementSoggetto.PH_PK, t.getSoggettoId());
		params.addValue(SqlStatementRuolo.PH_PK, t.getRuoloId());
		params.addValue("applicativoId", t.getApplicativoId());
		params.addValue(SqlStatementProfilo.PH_PK, t.getProfiloId());
		params.addValue(SqlStatementStruttura.PH_PK, t.getStrutturaId());
		params.addValue("enteId", t.getEnteId());
	}

	
	@Override
	public CsiLogSession get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementCsiLogSession.PH_PK, id);
		String sql = SqlStatementCsiLogSession.SELECT + SqlStatementCommon.WHERE
				+ SqlStatementCsiLogSession.PK_EQUALS;
		return queryForObject(sql, params, new CsiLogSessionMapper());
	}

	@Override
	public List<CsiLogSession> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementCsiLogSession.SELECT;
		return query(sql, params, new CsiLogSessionMapper());
	}
	
	@Override
	public void insert(CsiLogSession t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementCsiLogSession.PH_PK, getId(t));
		setSpecificParameter(t, params);

		String sql;
		if ( Objects.isNull(getId(t))) {
			sql = SqlStatementCsiLogSession.INSERT;
		} else {
			params.addValue(SqlStatementNatura.PH_PK, getId(t));
			sql = SqlStatementCsiLogSession.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementCsiLogSession.NEXTVAL_PK_ID;
		return queryForObject(sql, params,Integer.class);
	}

	@Override
	public void update(CsiLogSession t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementCsiLogSession.PH_PK, id);
		setSpecificParameter(t, params);
		
		String sql = SqlStatementCsiLogSession.UPDATE + SqlStatementCommon.SET
				+ SqlStatementCsiLogSession.UPDATE_CAMPI + SqlStatementCommon.WHERE
				+ SqlStatementCsiLogSession.PK_EQUALS;
		update(sql, params);
	}
	
	public void logout(Integer sessionId) {

		var params = new MapSqlParameterSource();

		params.addValue("sessionId", sessionId);

		String sql = SqlStatementCsiLogSession.LOGOUT;
		update(sql, params);

	}

	@Override
	public void delete(CsiLogSession t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementCsiLogSession.PH_PK, getId(t));
		String sql = SqlStatementCsiLogSession.UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE
				+ SqlStatementCsiLogSession.PK_EQUALS;
		update(sql, params);

	}
}
