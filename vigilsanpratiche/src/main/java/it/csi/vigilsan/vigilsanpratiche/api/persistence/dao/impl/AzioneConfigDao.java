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
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.AzioneConfig;
import it.csi.vigilsan.vigilsanpratiche.util.query.SqlStatementAzioneConfig;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class AzioneConfigDao extends AbstractPersistence implements Dao<AzioneConfig> {

	private Integer getId(AzioneConfig t) {
		return t.getAzioneConfigId();
	}

	private void setSpecificParameter(AzioneConfig t, MapSqlParameterSource params) {
		params.addValue("azioneId", t.getAzioneId());
		params.addValue("praticaTipoId", t.getPraticaTipoId());
		params.addValue("profiloId", t.getProfiloId());
		params.addValue("abilitazione", t.getAbilitazione());
	}

	@Override
	public AzioneConfig get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementAzioneConfig.PH_PK, id);
		String sql = SqlStatementAzioneConfig.SELECT_BY_ID;
		return queryForObject(sql, params, new AzioneConfigMapper());
	}

	@Override
	public List<AzioneConfig> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementAzioneConfig.SELECT_ALL;
		return query(sql, params, new AzioneConfigMapper());
	}

	@Override
	public void insert(AzioneConfig t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementAzioneConfig.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (Objects.isNull(getId(t))) {
			sql = SqlStatementAzioneConfig.INSERT_GENERIC;
		} else {
			params.addValue(SqlStatementAzioneConfig.PH_PK, getId(t));
			sql = SqlStatementAzioneConfig.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementAzioneConfig.NEXTVAL_PK_ID;
		return queryForObject(sql, params, Integer.class);
	}

	@Override
	public void update(AzioneConfig t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementAzioneConfig.PH_PK, id);
		setSpecificParameter(t, params);

		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementAzioneConfig.UPDATE_GENERIC;
		update(sql, params);
	}

	@Override
	public void delete(AzioneConfig t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementAzioneConfig.PH_PK, getId(t));
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementAzioneConfig.DELETE;
		update(sql, params);
	}

	public List<AzioneConfig> getByPraticaTipoId(Integer praticaTipoId) {
		var params = new MapSqlParameterSource();
		params.addValue("praticaTipoId", praticaTipoId);
		String sql = SqlStatementAzioneConfig.SELECT_BY_PRATICA_TIPO_ID;
		return query(sql, params, new AzioneConfigMapper());
	}

}
