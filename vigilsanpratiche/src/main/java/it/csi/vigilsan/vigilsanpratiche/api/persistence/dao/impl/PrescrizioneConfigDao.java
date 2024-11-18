/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.dao.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.AppuntamentoWorkflowMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.PrescrizioneConfigMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PrescrizioneConfig;
import it.csi.vigilsan.vigilsanpratiche.util.query.SqlStatementAppuntamentoWorkflow;
import it.csi.vigilsan.vigilsanpratiche.util.query.SqlStatementPrescrizioneConfig;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class PrescrizioneConfigDao extends AbstractPersistence implements Dao<PrescrizioneConfig> {

	private Integer getId(PrescrizioneConfig t) {
		return t.getPrescrizioneConfigId();
	}

	private void setSpecificParameter(PrescrizioneConfig t, MapSqlParameterSource params) {
		params.addValue("prescrizioneTipoId", t.getPrescrizioneTipoId());
		params.addValue("praticaTipoId", t.getPraticaTipoId());
		params.addValue("praticaStatoId", t.getPraticaStatoId());
	}
	
	@Override
	public PrescrizioneConfig get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPrescrizioneConfig.PH_PK, id);
		String sql = SqlStatementPrescrizioneConfig.SELECT_BY_ID;
		return queryForObject(sql, params, new PrescrizioneConfigMapper());
	}

	@Override
	public List<PrescrizioneConfig> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementPrescrizioneConfig.SELECT_ALL;
		return query(sql, params, new PrescrizioneConfigMapper());
	}

	@Override
	public void insert(PrescrizioneConfig t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPrescrizioneConfig.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (Objects.isNull(getId(t))) {
			sql = SqlStatementPrescrizioneConfig.INSERT_GENERIC;
		} else {
			params.addValue(SqlStatementPrescrizioneConfig.PH_PK, getId(t));
			sql = SqlStatementPrescrizioneConfig.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementPrescrizioneConfig.NEXTVAL_PK_ID;
		return queryForObject(sql, params, Integer.class);
	}

	@Override
	public void update(PrescrizioneConfig t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPrescrizioneConfig.PH_PK, id);
		setSpecificParameter(t, params);

		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementPrescrizioneConfig.UPDATE_GENERIC;
		update(sql, params);
	}

	@Override
	public void delete(PrescrizioneConfig t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementPrescrizioneConfig.PH_PK, getId(t));
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementPrescrizioneConfig.DELETE;
		update(sql, params);
	}

	public List<PrescrizioneConfig> getByPraticaTipoId(Integer praticaTipoId) {
		var params = new MapSqlParameterSource();
		params.addValue("praticaTipoId", praticaTipoId);
		String sql = SqlStatementPrescrizioneConfig.SELECT_BY_PRATICA_TIPO_ID;
		return query(sql, params, new PrescrizioneConfigMapper());
	}


}
