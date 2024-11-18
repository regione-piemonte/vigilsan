/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.dao.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.PrescrizioneConfigMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.PrescrizioneWorkflowMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PrescrizioneWorkflow;
import it.csi.vigilsan.vigilsanpratiche.util.query.SqlStatementPrescrizioneConfig;
import it.csi.vigilsan.vigilsanpratiche.util.query.SqlStatementPrescrizioneWorkflow;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class PrescrizioneWorkflowDao extends AbstractPersistence implements Dao<PrescrizioneWorkflow> {

	private Integer getId(PrescrizioneWorkflow t) {
		return t.getPrescrizioneWorkflowId();
	}

	private void setSpecificParameter(PrescrizioneWorkflow t, MapSqlParameterSource params) {
		params.addValue("praticaTipoId", t.getPraticaTipoId());
		params.addValue("prescrizioneTipoId", t.getPrescrizioneTipoId());
		params.addValue("azioneId", t.getAzioneId());
		params.addValue("moduloId", t.getModuloId());
		params.addValue("prescrizioneStatoIdIniziale", t.getPrescrizioneStatoIdIniziale());
		params.addValue("prescrizioneStatoIdFinale", t.getPrescrizioneStatoIdFinale());
	}

	@Override
	public PrescrizioneWorkflow get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPrescrizioneWorkflow.PH_PK, id);
		String sql = SqlStatementPrescrizioneWorkflow.SELECT_BY_ID;
		return queryForObject(sql, params, new PrescrizioneWorkflowMapper());
	}

	@Override
	public List<PrescrizioneWorkflow> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementPrescrizioneWorkflow.SELECT_ALL;
		return query(sql, params, new PrescrizioneWorkflowMapper());
	}

	@Override
	public void insert(PrescrizioneWorkflow t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPrescrizioneWorkflow.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (Objects.isNull(getId(t))) {
			sql = SqlStatementPrescrizioneWorkflow.INSERT_GENERIC;
		} else {
			params.addValue(SqlStatementPrescrizioneWorkflow.PH_PK, getId(t));
			sql = SqlStatementPrescrizioneWorkflow.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementPrescrizioneWorkflow.NEXTVAL_PK_ID;
		return queryForObject(sql, params, Integer.class);
	}

	@Override
	public void update(PrescrizioneWorkflow t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPrescrizioneWorkflow.PH_PK, id);
		setSpecificParameter(t, params);

		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementPrescrizioneWorkflow.UPDATE_GENERIC;
		update(sql, params);
	}

	@Override
	public void delete(PrescrizioneWorkflow t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementPrescrizioneWorkflow.PH_PK, getId(t));
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementPrescrizioneWorkflow.DELETE;
		update(sql, params);
	}

	public List<PrescrizioneWorkflow> getByPraticaTipoId(Integer praticaTipoId) {
		var params = new MapSqlParameterSource();
		params.addValue("praticaTipoId", praticaTipoId);
		String sql = SqlStatementPrescrizioneWorkflow.SELECT_BY_PRATICA_TIPO_ID;
		return query(sql, params, new PrescrizioneWorkflowMapper());
	}

}
