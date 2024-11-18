/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.dao.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.AppuntamentoConfigMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.AppuntamentoWorkflowMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.AppuntamentoWorkflow;
import it.csi.vigilsan.vigilsanpratiche.util.query.SqlStatementAppuntamentoConfig;
import it.csi.vigilsan.vigilsanpratiche.util.query.SqlStatementAppuntamentoWorkflow;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class AppuntamentoWorkflowDao extends AbstractPersistence implements Dao<AppuntamentoWorkflow> {

	private Integer getId(AppuntamentoWorkflow t) {
		return t.getAppuntamentoWorkflowId();
	}

	private void setSpecificParameter(AppuntamentoWorkflow t, MapSqlParameterSource params) {
		params.addValue("praticaTipoId", t.getPraticaTipoId());
		params.addValue("appuntamentoTipoId", t.getAppuntamentoTipoId());
		params.addValue("azioneId", t.getAzioneId());
		params.addValue("moduloId", t.getModuloId());
		params.addValue("appuntamentoStatoIdIniziale", t.getAppuntamentoStatoIdIniziale());
		params.addValue("appuntamentoStatoIdFinale", t.getAppuntamentoStatoIdFinale());
	}

	@Override
	public AppuntamentoWorkflow get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementAppuntamentoWorkflow.PH_PK, id);
		String sql = SqlStatementAppuntamentoWorkflow.SELECT_BY_ID;
		return queryForObject(sql, params, new AppuntamentoWorkflowMapper());
	}

	@Override
	public List<AppuntamentoWorkflow> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementAppuntamentoWorkflow.SELECT_ALL;
		return query(sql, params, new AppuntamentoWorkflowMapper());
	}

	@Override
	public void insert(AppuntamentoWorkflow t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementAppuntamentoWorkflow.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (Objects.isNull(getId(t))) {
			sql = SqlStatementAppuntamentoWorkflow.INSERT_GENERIC;
		} else {
			params.addValue(SqlStatementAppuntamentoWorkflow.PH_PK, getId(t));
			sql = SqlStatementAppuntamentoWorkflow.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementAppuntamentoWorkflow.NEXTVAL_PK_ID;
		return queryForObject(sql, params, Integer.class);
	}

	@Override
	public void update(AppuntamentoWorkflow t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementAppuntamentoWorkflow.PH_PK, id);
		setSpecificParameter(t, params);

		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementAppuntamentoWorkflow.UPDATE_GENERIC;
		update(sql, params);
	}

	@Override
	public void delete(AppuntamentoWorkflow t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementAppuntamentoWorkflow.PH_PK, getId(t));
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementAppuntamentoWorkflow.DELETE;
		update(sql, params);
	}

	public List<AppuntamentoWorkflow> getByPraticaTipoId(Integer praticaTipoId) {
		var params = new MapSqlParameterSource();
		params.addValue("praticaTipoId", praticaTipoId);
		String sql = SqlStatementAppuntamentoWorkflow.SELECT_BY_PRATICA_TIPO_ID;
		return query(sql, params, new AppuntamentoWorkflowMapper());
	}

}
