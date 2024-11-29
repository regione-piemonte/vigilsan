/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.StrutturaNaturaMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.StrutturaNatura;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementNatura;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class StrutturaNaturaDao extends AbstractPersistence implements Dao<StrutturaNatura> {

	@Override
	public StrutturaNatura get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementNatura.PH_PK, id);
		String sql = SqlStatementNatura.SELECT + SqlStatementCommon.WHERE
				+ SqlStatementNatura.PK_EQUALS;
		return queryForObject(sql, params, new StrutturaNaturaMapper());
	}

	@Override
	public List<StrutturaNatura> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementNatura.SELECT;
		return query(sql, params, new StrutturaNaturaMapper());
	}

	@Override
	public void insert(StrutturaNatura t) {
		var params = new MapSqlParameterSource();

		params.addValue("strutturaNaturaCod", t.getStrutturaNaturaCod());
		params.addValue("strutturaNaturaCodArpe", t.getStrutturaNaturaCodArpe());
		params.addValue("strutturaNaturaDesc", t.getStrutturaNaturaDesc());
		params.addValue("strutturaNaturaId", t.getStrutturaNaturaId());
		
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (t.getStrutturaNaturaId() == null) {
			sql = SqlStatementNatura.INSERT;
		} else {
			params.addValue(SqlStatementNatura.PH_PK, t.getStrutturaNaturaId());
			sql = SqlStatementNatura.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementNatura.NEXTVAL_PK_ID;
		return queryForObject(sql, params,Integer.class);
	}

	@Override
	public void update(StrutturaNatura t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementNatura.PH_PK, id);
		params.addValue("strutturaNaturaCod", t.getStrutturaNaturaCod());
		params.addValue("strutturaNaturaCodArpe", t.getStrutturaNaturaCodArpe());
		params.addValue("strutturaNaturaDesc", t.getStrutturaNaturaDesc());
		params.addValue("strutturaNaturaId", t.getStrutturaNaturaId());
		
		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementNatura.UPDATE + SqlStatementCommon.SET
				+ SqlStatementNatura.UPDATE_CAMPI + SqlStatementCommon.WHERE
				+ SqlStatementNatura.PK_EQUALS;
		update(sql, params);
	}

	@Override
	public void delete(StrutturaNatura t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementNatura.PH_PK, t.getStrutturaNaturaId());
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementNatura.UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE
				+ SqlStatementNatura.PK_EQUALS;
		update(sql, params);

	}
	
	public StrutturaNatura getStrutturaNaturaByCodArpe(String inCodArpe) throws Exception {
		var params = new MapSqlParameterSource();

		params.addValue("cod_arpe", inCodArpe);
		String sql = SqlStatementNatura.SELECT_STRUTTURA_NATURA_BY_STRUTTURA_NATURA_COD_ARPE;
		return queryForObject(sql, params, new StrutturaNaturaMapper());

	}
	
	
	
}
