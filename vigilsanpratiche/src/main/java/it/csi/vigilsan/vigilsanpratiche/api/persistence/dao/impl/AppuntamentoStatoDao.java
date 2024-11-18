/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.dao.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.AppuntamentoStatoMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.AppuntamentoStato;
import it.csi.vigilsan.vigilsanpratiche.util.query.SqlStatementAppuntamentoStato;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class AppuntamentoStatoDao extends AbstractPersistence implements Dao<AppuntamentoStato> {

	private Integer getId(AppuntamentoStato t) {
		return t.getAppuntamentoStatoId();
	}

	private void setSpecificParameter(AppuntamentoStato t, MapSqlParameterSource params) {
		params.addValue("appuntamentoStatoCod", t.getAppuntamentoStatoCod());
		params.addValue("appuntamentoStatoDesc", t.getAppuntamentoStatoDesc());
		params.addValue("appuntamentoStatoFinale", t.isAppuntamentoStatoFinale());
	}

	@Override
	public AppuntamentoStato get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementAppuntamentoStato.PH_PK, id);
		String sql = SqlStatementAppuntamentoStato.SELECT_BY_ID;
		return queryForObject(sql, params, new AppuntamentoStatoMapper());
	}

	@Override
	public List<AppuntamentoStato> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementAppuntamentoStato.SELECT_ALL;
		return query(sql, params, new AppuntamentoStatoMapper());
	}

	@Override
	public void insert(AppuntamentoStato t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementAppuntamentoStato.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (Objects.isNull(getId(t))) {
			sql = SqlStatementAppuntamentoStato.INSERT_GENERIC;
		} else {
			params.addValue(SqlStatementAppuntamentoStato.PH_PK, getId(t));
			sql = SqlStatementAppuntamentoStato.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementAppuntamentoStato.NEXTVAL_PK_ID;
		return queryForObject(sql, params, Integer.class);
	}

	@Override
	public void update(AppuntamentoStato t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementAppuntamentoStato.PH_PK, id);
		setSpecificParameter(t, params);

		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementAppuntamentoStato.UPDATE_GENERIC;
		update(sql, params);
	}

	@Override
	public void delete(AppuntamentoStato t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementAppuntamentoStato.PH_PK, getId(t));
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementAppuntamentoStato.DELETE;
		update(sql, params);
	}

	public List<AppuntamentoStato> getByPraticaTipoId(Integer praticaTipoId) {
		var params = new MapSqlParameterSource();
		params.addValue("praticaTipoId", praticaTipoId);
		String sql = SqlStatementAppuntamentoStato.SELECT_ALL;
		return query(sql, params, new AppuntamentoStatoMapper());
	}

}
