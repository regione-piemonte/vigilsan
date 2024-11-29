/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.ArpeAttivitaClasseMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.ArpeAttivitaClasse;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementArpeAttivitaClasse;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class ArpeAttivitaClasseDao extends AbstractPersistence implements Dao<ArpeAttivitaClasse> {


	private Integer getId(ArpeAttivitaClasse t) {
		return t.getArpeAttivitaClasseId();
	}

	private void setSpecificParameter(ArpeAttivitaClasse t, MapSqlParameterSource params) {
		params.addValue("arpeAttivitaClasseCod", t.getArpeAttivitaClasseCod());
		params.addValue("arpeAttivitaClasseDesc", t.getArpeAttivitaClasseDesc());
	}

	
	@Override
	public ArpeAttivitaClasse get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementArpeAttivitaClasse.PH_PK, id);
		String sql = SqlStatementArpeAttivitaClasse.SELECT + SqlStatementCommon.WHERE
				+ SqlStatementArpeAttivitaClasse.PK_EQUALS;
		return queryForObject(sql, params, new ArpeAttivitaClasseMapper());
	}

	@Override
	public List<ArpeAttivitaClasse> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementArpeAttivitaClasse.SELECT;
		return query(sql, params, new ArpeAttivitaClasseMapper());
	}
	
	@Override
	public void insert(ArpeAttivitaClasse t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementArpeAttivitaClasse.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if ( Objects.isNull(getId(t))) {
			sql = SqlStatementArpeAttivitaClasse.INSERT;
		} else {
			params.addValue(SqlStatementArpeAttivitaClasse.PH_PK, getId(t));
			sql = SqlStatementArpeAttivitaClasse.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementArpeAttivitaClasse.NEXTVAL_PK_ID;
		return queryForObject(sql, params,Integer.class);
	}

	@Override
	public void update(ArpeAttivitaClasse t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementArpeAttivitaClasse.PH_PK, id);
		setSpecificParameter(t, params);
		
		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementArpeAttivitaClasse.UPDATE + SqlStatementCommon.SET
				+ SqlStatementArpeAttivitaClasse.UPDATE_CAMPI + SqlStatementCommon.WHERE
				+ SqlStatementArpeAttivitaClasse.PK_EQUALS;
		update(sql, params);
	}
	
	@Override
	public void delete(ArpeAttivitaClasse t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementArpeAttivitaClasse.PH_PK, getId(t));
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementArpeAttivitaClasse.UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE
				+ SqlStatementArpeAttivitaClasse.PK_EQUALS;
		update(sql, params);
	}
	
	
	// vigil_d_arpe_attivita_classe
	public ArpeAttivitaClasse getArpeAttivitaClasseByCod(String inCodArpe) {
		var params = new MapSqlParameterSource();

		params.addValue("cod_arpe", inCodArpe);
		String sql = SqlStatementArpeAttivitaClasse.SELECT_ATTIVITA_CLASSE_BY_ARPE_ATTIVITA_CLASSE_COD;
		return queryForObject(sql, params, new ArpeAttivitaClasseMapper());

	}
}