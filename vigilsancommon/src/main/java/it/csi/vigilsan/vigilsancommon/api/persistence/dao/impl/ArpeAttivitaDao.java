/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.ArpeAttivitaMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.ArpeAttivita;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementArpeAttivita;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class ArpeAttivitaDao extends AbstractPersistence implements Dao<ArpeAttivita> {


	private Integer getId(ArpeAttivita t) {
		return t.getArpeAttivitaId();
	}

	private void setSpecificParameter(ArpeAttivita t, MapSqlParameterSource params) {
		params.addValue("arpeAttivitaCod", t.getArpeAttivitaCod());
		params.addValue("arpeAttivitaDesc", t.getArpeAttivitaDesc());
	}

	
	@Override
	public ArpeAttivita get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementArpeAttivita.PH_PK, id);
		String sql = SqlStatementArpeAttivita.SELECT + SqlStatementCommon.WHERE
				+ SqlStatementArpeAttivita.PK_EQUALS;
		return queryForObject(sql, params, new ArpeAttivitaMapper());
	}

	@Override
	public List<ArpeAttivita> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementArpeAttivita.SELECT;
		return query(sql, params, new ArpeAttivitaMapper());
	}
	
	@Override
	public void insert(ArpeAttivita t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementArpeAttivita.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if ( Objects.isNull(getId(t))) {
			sql = SqlStatementArpeAttivita.INSERT;
		} else {
			params.addValue(SqlStatementArpeAttivita.PH_PK, getId(t));
			sql = SqlStatementArpeAttivita.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementArpeAttivita.NEXTVAL_PK_ID;
		return queryForObject(sql, params,Integer.class);
	}

	@Override
	public void update(ArpeAttivita t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementArpeAttivita.PH_PK, id);
		setSpecificParameter(t, params);
		
		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementArpeAttivita.UPDATE + SqlStatementCommon.SET
				+ SqlStatementArpeAttivita.UPDATE_CAMPI + SqlStatementCommon.WHERE
				+ SqlStatementArpeAttivita.PK_EQUALS;
		update(sql, params);
	}
	
	@Override
	public void delete(ArpeAttivita t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementArpeAttivita.PH_PK, getId(t));
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementArpeAttivita.UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE
				+ SqlStatementArpeAttivita.PK_EQUALS;
		update(sql, params);
	}
	
	
	// vigil_d_arpe_attivita
	public ArpeAttivita getArpeAttivitaByCod(String inCodArpe) {
		var params = new MapSqlParameterSource();

		params.addValue("cod_arpe", inCodArpe);
		String sql = SqlStatementArpeAttivita.SELECT_ATTIVITA_BY_ARPE_ATTIVITA_COD;
		return queryForObject(sql, params, new ArpeAttivitaMapper());

	}
}
