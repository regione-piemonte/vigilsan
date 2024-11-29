/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.StrutturaTitolaritaMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.StrutturaTitolarita;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementStrutturaTitolarita;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class StrutturaTitolaritaDao extends AbstractPersistence implements Dao<StrutturaTitolarita> {

	@Override
	public StrutturaTitolarita get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementStrutturaTitolarita.PH_PK, id);
		String sql = SqlStatementStrutturaTitolarita.SELECT + SqlStatementCommon.WHERE
				+ SqlStatementStrutturaTitolarita.PK_EQUALS;
		return queryForObject(sql, params, new StrutturaTitolaritaMapper());
	}

	@Override
	public List<StrutturaTitolarita> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementStrutturaTitolarita.SELECT;
		return query(sql, params, new StrutturaTitolaritaMapper());
	}
	
	@Override
	public void insert(StrutturaTitolarita t) {
		var params = new MapSqlParameterSource();

		params.addValue("strutturaTitolaritaId", t.getStrutturaTitolaritaId());
		params.addValue("strutturaTitolaritaCod", t.getStrutturaTitolaritaCod());
		params.addValue("strutturaTitolaritaCodArpe", t.getStrutturaTitolaritaCodArpe());
		params.addValue("strutturaTitolaritaDesc", t.getStrutturaTitolaritaDesc());
		
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (t.getStrutturaTitolaritaId() == null) {
			sql = SqlStatementStrutturaTitolarita.INSERT;
		} else {
			params.addValue(SqlStatementStrutturaTitolarita.PH_PK, t.getStrutturaTitolaritaId());
			sql = SqlStatementStrutturaTitolarita.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementStrutturaTitolarita.NEXTVAL_PK_ID;
		return queryForObject(sql, params,Integer.class);
	}

	@Override
	public void update(StrutturaTitolarita t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementStrutturaTitolarita.PH_PK, id);
		params.addValue("strutturaTitolaritaoCod", t.getStrutturaTitolaritaCod());
		params.addValue("strutturaTitolaritaCodArpe", t.getStrutturaTitolaritaCodArpe());
		params.addValue("strutturaTitolaritaDesc", t.getStrutturaTitolaritaDesc());
		
		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementStrutturaTitolarita.UPDATE + SqlStatementCommon.SET
				+ SqlStatementStrutturaTitolarita.UPDATE_CAMPI + SqlStatementCommon.WHERE
				+ SqlStatementStrutturaTitolarita.PK_EQUALS;
		update(sql, params);
	}

	@Override
	public void delete(StrutturaTitolarita t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementStrutturaTitolarita.PH_PK, t.getStrutturaTitolaritaId());
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementStrutturaTitolarita.UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE
				+ SqlStatementStrutturaTitolarita.PK_EQUALS;
		update(sql, params);
	}
	
	public StrutturaTitolarita getStrutturaTitolaritaByCodArpe(String inCodArpe) {
		var params = new MapSqlParameterSource();

		params.addValue("cod_arpe", inCodArpe);
		String sql = SqlStatementStrutturaTitolarita.SELECT_STRUTTURA_TITOLARITA_BY_STRUTTURA_TITOLARITA_COD_ARPE;
		return queryForObject(sql, params, new StrutturaTitolaritaMapper());

	}
	
}
