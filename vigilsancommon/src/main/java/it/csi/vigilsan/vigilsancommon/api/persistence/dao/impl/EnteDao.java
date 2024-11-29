/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.EnteMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.Ente;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementEnte;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementStruttura;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class EnteDao extends AbstractPersistence implements Dao<Ente> {

	@Override
	public Ente get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue("enteId", id);
		String sql = SqlStatementEnte.SELECT_ENTE + SqlStatementCommon.WHERE + SqlStatementEnte.ENTE_ID_EQUALS;
		return queryForObject(sql, params, new EnteMapper());
	}

	public List<Ente> getEnteAslByStrutturaId(Integer strutturaId) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementStruttura.PH_PK, strutturaId);
		String sql = SqlStatementEnte.SELECT_ASL_BY_STRUTTURA_ID;
		return query(sql, params, new EnteMapper());
	}
	
	
	public Ente getEnteAslByEnteCod(String enteCod) {
		
		var params = new MapSqlParameterSource();

		params.addValue("enteCod", enteCod);
		String sql = SqlStatementEnte.SELECT_ASL_BY_ENTE_COD;
		return queryForObject(sql, params, new EnteMapper());
	}

	@Override
	public List<Ente> getAll() {
		// Do nothing
		return null;
	}

	@Override
	public void insert(Ente t) {
		// Do nothing
	}

	@Override
	public void update(Ente t, int id) {
		// Do nothing
	}

	@Override
	public void delete(Ente t) {
		// Do nothing
	}
}
