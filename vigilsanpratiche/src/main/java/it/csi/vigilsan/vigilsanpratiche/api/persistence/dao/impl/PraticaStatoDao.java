/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.dao.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.PraticaStatoMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaStato;
import it.csi.vigilsan.vigilsanpratiche.util.query.SqlStatementPraticaStato;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class PraticaStatoDao extends AbstractPersistence implements Dao<PraticaStato> {

	private Integer getId(PraticaStato t) {
		return t.getPraticaStatoId();
	}

	private void setSpecificParameter(PraticaStato t, MapSqlParameterSource params) {
		params.addValue("praticaStatoCod", t.getPraticaStatoCod());
		params.addValue("praticaStatoDesc", t.getPraticaStatoDesc());
		params.addValue("praticaStatoFinale", t.isPraticaStatoFinale());
	}

	@Override
	public PraticaStato get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPraticaStato.PH_PK, id);
		String sql = SqlStatementPraticaStato.SELECT_BY_ID;
		return queryForObject(sql, params, new PraticaStatoMapper());
	}

	@Override
	public List<PraticaStato> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementPraticaStato.SELECT_ALL;
		return query(sql, params, new PraticaStatoMapper());
	}

	@Override
	public void insert(PraticaStato t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPraticaStato.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (Objects.isNull(getId(t))) {
			sql = SqlStatementPraticaStato.INSERT_GENERIC;
		} else {
			params.addValue(SqlStatementPraticaStato.PH_PK, getId(t));
			sql = SqlStatementPraticaStato.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementPraticaStato.NEXTVAL_PK_ID;
		return queryForObject(sql, params, Integer.class);
	}

	@Override
	public void update(PraticaStato t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPraticaStato.PH_PK, id);
		setSpecificParameter(t, params);

		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementPraticaStato.UPDATE_GENERIC;
		update(sql, params);
	}

	@Override
	public void delete(PraticaStato t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementPraticaStato.PH_PK, getId(t));
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementPraticaStato.DELETE;
		update(sql, params);
	}

	public List<PraticaStato> getByPraticaTipoId(Integer praticaTipoId) {
		var params = new MapSqlParameterSource();
		params.addValue("praticaTipoId", praticaTipoId);
		String sql = SqlStatementPraticaStato.SELECT_ALL;
		return query(sql, params, new PraticaStatoMapper());
	}

}
