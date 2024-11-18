/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.dao.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.AzioneConfigMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.PraticaWorkflowMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaWorkflow;
import it.csi.vigilsan.vigilsanpratiche.util.query.SqlStatementAzioneConfig;
import it.csi.vigilsan.vigilsanpratiche.util.query.SqlStatementPraticaWorkflow;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class PraticaWorkflowDao extends AbstractPersistence implements Dao<PraticaWorkflow> {

	private Integer getId(PraticaWorkflow t) {
		return t.getPraticaWorkflowId();
	}

	private void setSpecificParameter(PraticaWorkflow t, MapSqlParameterSource params) {
		params.addValue("praticaTipoId", t.getPraticaTipoId());
		params.addValue("azioneId", t.getAzioneId());
		params.addValue("moduloId", t.getModuloId());
		params.addValue("praticaStatoIdIniziale", t.getPraticaStatoIdIniziale());
		params.addValue("praticaStatoIdFinale", t.getPraticaStatoIdFinale());
	}

	@Override
	public PraticaWorkflow get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPraticaWorkflow.PH_PK, id);
		String sql = SqlStatementPraticaWorkflow.SELECT_BY_ID;
		return queryForObject(sql, params, new PraticaWorkflowMapper());
	}

	@Override
	public List<PraticaWorkflow> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementPraticaWorkflow.SELECT_ALL;
		return query(sql, params, new PraticaWorkflowMapper());
	}

	@Override
	public void insert(PraticaWorkflow t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPraticaWorkflow.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (Objects.isNull(getId(t))) {
			sql = SqlStatementPraticaWorkflow.INSERT_GENERIC;
		} else {
			params.addValue(SqlStatementPraticaWorkflow.PH_PK, getId(t));
			sql = SqlStatementPraticaWorkflow.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementPraticaWorkflow.NEXTVAL_PK_ID;
		return queryForObject(sql, params, Integer.class);
	}

	@Override
	public void update(PraticaWorkflow t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPraticaWorkflow.PH_PK, id);
		setSpecificParameter(t, params);

		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementPraticaWorkflow.UPDATE_GENERIC;
		update(sql, params);
	}

	@Override
	public void delete(PraticaWorkflow t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementPraticaWorkflow.PH_PK, getId(t));
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementPraticaWorkflow.DELETE;
		update(sql, params);
	}

	public List<PraticaWorkflow> getByPraticaTipoId(Integer praticaTipoId) {
		var params = new MapSqlParameterSource();
		params.addValue("praticaTipoId", praticaTipoId);
		String sql = SqlStatementPraticaWorkflow.SELECT_BY_PRATICA_TIPO_ID;
		return query(sql, params, new PraticaWorkflowMapper());
	}

}
