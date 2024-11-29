/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.ArpeDisciplinaMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.ArpeDisciplina;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementArpeDisciplina;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class ArpeDisciplinaDao extends AbstractPersistence implements Dao<ArpeDisciplina> {


	private Integer getId(ArpeDisciplina t) {
		return t.getArpeDisciplinaId();
	}

	private void setSpecificParameter(ArpeDisciplina t, MapSqlParameterSource params) {
		params.addValue("arpeDisciplinaCod", t.getArpeDisciplinaCod());
		params.addValue("arpeDisciplinaDesc", t.getArpeDisciplinaDesc());
	}

	
	@Override
	public ArpeDisciplina get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementArpeDisciplina.PH_PK, id);
		String sql = SqlStatementArpeDisciplina.SELECT + SqlStatementCommon.WHERE
				+ SqlStatementArpeDisciplina.PK_EQUALS;
		return queryForObject(sql, params, new ArpeDisciplinaMapper());
	}

	@Override
	public List<ArpeDisciplina> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementArpeDisciplina.SELECT;
		return query(sql, params, new ArpeDisciplinaMapper());
	}
	
	@Override
	public void insert(ArpeDisciplina t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementArpeDisciplina.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if ( Objects.isNull(getId(t))) {
			sql = SqlStatementArpeDisciplina.INSERT;
		} else {
			params.addValue(SqlStatementArpeDisciplina.PH_PK, getId(t));
			sql = SqlStatementArpeDisciplina.INSERT_W_PK;

		}
		update(sql, params);
	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementArpeDisciplina.NEXTVAL_PK_ID;
		return queryForObject(sql, params,Integer.class);
	}

	@Override
	public void update(ArpeDisciplina t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementArpeDisciplina.PH_PK, id);
		setSpecificParameter(t, params);
		
		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementArpeDisciplina.UPDATE + SqlStatementCommon.SET
				+ SqlStatementArpeDisciplina.UPDATE_CAMPI + SqlStatementCommon.WHERE
				+ SqlStatementArpeDisciplina.PK_EQUALS;
		update(sql, params);
	}
	
	@Override
	public void delete(ArpeDisciplina t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementArpeDisciplina.PH_PK, getId(t));
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementArpeDisciplina.UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE
				+ SqlStatementArpeDisciplina.PK_EQUALS;
		update(sql, params);
	}
	
	
	// vigil_d_arpe_attivita_classe
	public ArpeDisciplina getArpeDisciplinaByCod(String inCodArpe) {
		var params = new MapSqlParameterSource();

		params.addValue("cod_arpe", inCodArpe);
		String sql = SqlStatementArpeDisciplina.SELECT_DISCIPLINA_BY_ARPE_DISCIPLINA_COD;
		return queryForObject(sql, params, new ArpeDisciplinaMapper());

	}
}
