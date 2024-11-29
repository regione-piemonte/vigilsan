/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.StrutturaTipoMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.StrutturaTipo;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementStrutturaTipo;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class StrutturaTipoDao extends AbstractPersistence implements Dao<StrutturaTipo> {


	private Integer getId(StrutturaTipo t) {
		return t.getStrutturaTipoId();
	}

	private void setSpecificParameter(StrutturaTipo t, MapSqlParameterSource params) {
		params.addValue("strutturaTipoCod", t.getStrutturaTipoCod());
		params.addValue("strutturaTipoDesc", t.getStrutturaTipoDesc());
	}

	
	@Override
	public StrutturaTipo get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementStrutturaTipo.PH_PK, id);
		String sql = SqlStatementStrutturaTipo.SELECT + SqlStatementCommon.WHERE
				+ SqlStatementStrutturaTipo.PK_EQUALS;
		return queryForObject(sql, params, new StrutturaTipoMapper());
	}

	@Override
	public List<StrutturaTipo> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementStrutturaTipo.SELECT;
		return query(sql, params, new StrutturaTipoMapper());
	}
	
	@Override
	public void insert(StrutturaTipo t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementStrutturaTipo.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if ( Objects.isNull(getId(t))) {
			sql = SqlStatementStrutturaTipo.INSERT;
		} else {
			params.addValue(SqlStatementStrutturaTipo.PH_PK, getId(t));
			sql = SqlStatementStrutturaTipo.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementStrutturaTipo.NEXTVAL_PK_ID;
		return queryForObject(sql, params,Integer.class);
	}

	@Override
	public void update(StrutturaTipo t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementStrutturaTipo.PH_PK, id);
		setSpecificParameter(t, params);
		
		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementStrutturaTipo.UPDATE + SqlStatementCommon.SET
				+ SqlStatementStrutturaTipo.UPDATE_CAMPI + SqlStatementCommon.WHERE
				+ SqlStatementStrutturaTipo.PK_EQUALS;
		update(sql, params);
	}
	
	@Override
	public void delete(StrutturaTipo t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementStrutturaTipo.PH_PK, getId(t));
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementStrutturaTipo.UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE
				+ SqlStatementStrutturaTipo.PK_EQUALS;
		update(sql, params);
	}
	
	public StrutturaTipo getStrutturaTipoByCodArpe(String inCodArpe) {
		var params = new MapSqlParameterSource();

		params.addValue("cod_arpe", inCodArpe);
		String sql = SqlStatementStrutturaTipo.SELECT_STRUTTURA_TIPO_BY_ARPE_STRUTTURA_TIPO_COD;
		return queryForObject(sql, params, new StrutturaTipoMapper());

	}
}
