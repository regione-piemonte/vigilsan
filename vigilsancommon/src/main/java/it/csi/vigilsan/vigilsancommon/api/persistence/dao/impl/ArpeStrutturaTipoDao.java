/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.ArpeStrutturaTipoMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.ArpeStrutturaTipo;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementArpeStrutturaTipo;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class ArpeStrutturaTipoDao extends AbstractPersistence implements Dao<ArpeStrutturaTipo> {


	private Integer getId(ArpeStrutturaTipo t) {
		return t.getArpeStrutturaTipoId();
	}

	private void setSpecificParameter(ArpeStrutturaTipo t, MapSqlParameterSource params) {
		params.addValue("arpeStrutturaTipoCod", t.getArpeStrutturaTipoCod());
		params.addValue("arpeStrutturaTipoDesc", t.getArpeStrutturaTipoDesc());
	}

	
	@Override
	public ArpeStrutturaTipo get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementArpeStrutturaTipo.PH_PK, id);
		String sql = SqlStatementArpeStrutturaTipo.SELECT + SqlStatementCommon.WHERE
				+ SqlStatementArpeStrutturaTipo.PK_EQUALS;
		return queryForObject(sql, params, new ArpeStrutturaTipoMapper());
	}

	@Override
	public List<ArpeStrutturaTipo> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementArpeStrutturaTipo.SELECT;
		return query(sql, params, new ArpeStrutturaTipoMapper());
	}
	
	@Override
	public void insert(ArpeStrutturaTipo t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementArpeStrutturaTipo.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if ( Objects.isNull(getId(t))) {
			sql = SqlStatementArpeStrutturaTipo.INSERT;
		} else {
			params.addValue(SqlStatementArpeStrutturaTipo.PH_PK, getId(t));
			sql = SqlStatementArpeStrutturaTipo.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementArpeStrutturaTipo.NEXTVAL_PK_ID;
		return queryForObject(sql, params,Integer.class);
	}

	@Override
	public void update(ArpeStrutturaTipo t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementArpeStrutturaTipo.PH_PK, id);
		setSpecificParameter(t, params);
		
		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementArpeStrutturaTipo.UPDATE + SqlStatementCommon.SET
				+ SqlStatementArpeStrutturaTipo.UPDATE_CAMPI + SqlStatementCommon.WHERE
				+ SqlStatementArpeStrutturaTipo.PK_EQUALS;
		update(sql, params);
	}
	
	@Override
	public void delete(ArpeStrutturaTipo t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementArpeStrutturaTipo.PH_PK, getId(t));
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementArpeStrutturaTipo.UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE
				+ SqlStatementArpeStrutturaTipo.PK_EQUALS;
		update(sql, params);
	}
	
	
	// vigil_d_arpe_struttura_tipo
	public ArpeStrutturaTipo getArpeStrutturaTipoByCod(String inCodArpe) {
		var params = new MapSqlParameterSource();

		params.addValue("cod_arpe", inCodArpe);
		String sql = SqlStatementArpeStrutturaTipo.SELECT_STRUTTURA_TIPO_BY_ARPE_STRUTTURA_TIPO_COD;
		return queryForObject(sql, params, new ArpeStrutturaTipoMapper());

	}
}
