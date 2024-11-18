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
import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.AzioneConfigMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.AppuntamentoConfig;
import it.csi.vigilsan.vigilsanpratiche.util.query.SqlStatementAppuntamentoConfig;
import it.csi.vigilsan.vigilsanpratiche.util.query.SqlStatementAzioneConfig;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class AppuntamentoConfigDao extends AbstractPersistence implements Dao<AppuntamentoConfig> {

	private Integer getId(AppuntamentoConfig t) {
		return t.getAppuntamentoConfigId();
	}

	private void setSpecificParameter(AppuntamentoConfig t, MapSqlParameterSource params) {
		params.addValue("praticaStatoId", t.getPraticaStatoId());
		params.addValue("praticaTipoId", t.getPraticaTipoId());
		params.addValue("appuntamentoTipoId", t.getAppuntamentoTipoId());
	}

	@Override
	public AppuntamentoConfig get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementAppuntamentoConfig.PH_PK, id);
		String sql = SqlStatementAppuntamentoConfig.SELECT_BY_ID;
		return queryForObject(sql, params, new AppuntamentoConfigMapper());
	}

	@Override
	public List<AppuntamentoConfig> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementAppuntamentoConfig.SELECT_ALL;
		return query(sql, params, new AppuntamentoConfigMapper());
	}

	@Override
	public void insert(AppuntamentoConfig t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementAppuntamentoConfig.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (Objects.isNull(getId(t))) {
			sql = SqlStatementAppuntamentoConfig.INSERT_GENERIC;
		} else {
			params.addValue(SqlStatementAppuntamentoConfig.PH_PK, getId(t));
			sql = SqlStatementAppuntamentoConfig.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementAppuntamentoConfig.NEXTVAL_PK_ID;
		return queryForObject(sql, params, Integer.class);
	}

	@Override
	public void update(AppuntamentoConfig t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementAppuntamentoConfig.PH_PK, id);
		setSpecificParameter(t, params);

		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementAppuntamentoConfig.UPDATE_GENERIC;
		update(sql, params);
	}

	@Override
	public void delete(AppuntamentoConfig t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementAppuntamentoConfig.PH_PK, getId(t));
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementAppuntamentoConfig.DELETE;
		update(sql, params);
	}

	public List<AppuntamentoConfig> getByPraticaTipoId(Integer praticaTipoId) {
		var params = new MapSqlParameterSource();
		params.addValue("praticaTipoId", praticaTipoId);
		String sql = SqlStatementAppuntamentoConfig.SELECT_BY_PRATICA_TIPO_ID;
		return query(sql, params, new AppuntamentoConfigMapper());
	}

}
