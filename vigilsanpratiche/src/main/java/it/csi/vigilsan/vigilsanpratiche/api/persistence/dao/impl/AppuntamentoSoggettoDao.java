/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.dao.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.AppuntamentoSoggettoEstesoMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.AppuntamentoSoggettoEstesoWsoggettoMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.AppuntamentoSoggettoMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.RuoloAppuntamentoSoggettoMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.AppuntamentoSoggetto;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.AppuntamentoSoggettoEsteso;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.AppuntamentoSoggettoEstesoWsoggetto;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.RuoloAppuntamentoSoggetto;
import it.csi.vigilsan.vigilsanpratiche.util.query.SqlStatementAppuntamentoSoggetto;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class AppuntamentoSoggettoDao extends AbstractPersistence implements Dao<AppuntamentoSoggetto> {

	private Integer getId(AppuntamentoSoggetto t) {
		return t.getAppuntamentoSoggettoId();
	}

	private void setSpecificParameter(AppuntamentoSoggetto t, MapSqlParameterSource params) {
		params.addValue("soggettoId", t.getSoggettoId());
		params.addValue("appuntamentoId", t.getAppuntamentoId());
		params.addValue("ruoloAppuntamentoSoggettoId", t.getRuoloAppuntamentoSoggettoId());
	}

	@Override
	public AppuntamentoSoggetto get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementAppuntamentoSoggetto.PH_PK, id);
		String sql = SqlStatementAppuntamentoSoggetto.SELECT_BY_ID;
		return queryForObject(sql, params, new AppuntamentoSoggettoMapper());
	}

	@Override
	public List<AppuntamentoSoggetto> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementAppuntamentoSoggetto.SELECT_ALL;
		return query(sql, params, new AppuntamentoSoggettoMapper());
	}

	@Override
	public void insert(AppuntamentoSoggetto t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementAppuntamentoSoggetto.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (Objects.isNull(getId(t))) {
			sql = SqlStatementAppuntamentoSoggetto.INSERT_GENERIC;
		} else {
			params.addValue(SqlStatementAppuntamentoSoggetto.PH_PK, getId(t));
			sql = SqlStatementAppuntamentoSoggetto.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementAppuntamentoSoggetto.NEXTVAL_PK_ID;
		return queryForObject(sql, params, Integer.class);
	}

	@Override
	public void update(AppuntamentoSoggetto t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementAppuntamentoSoggetto.PH_PK, id);
		setSpecificParameter(t, params);

		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementAppuntamentoSoggetto.UPDATE_GENERIC;
		update(sql, params);
	}

	@Override
	public void delete(AppuntamentoSoggetto t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementAppuntamentoSoggetto.PH_PK, getId(t));
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementAppuntamentoSoggetto.DELETE;
		update(sql, params);
	}

	public List<AppuntamentoSoggetto> selectBySoggettoIdAppuntamentoId(Integer soggettoId, Integer appuntamentoId, Integer ruoloAppuntamentoSoggettoId) {
		var params = new MapSqlParameterSource();
		params.addValue("soggettoId", soggettoId);
		params.addValue("appuntamentoId", appuntamentoId);
		params.addValue("ruoloAppuntamentoSoggettoId", ruoloAppuntamentoSoggettoId);
		
		String sql = SqlStatementAppuntamentoSoggetto.SELECT_BY_SOGGETTO_ID_APPUNTAMENTO_ID;
		return query(sql, params, new AppuntamentoSoggettoMapper());
	}

	public Boolean verificaAssociazioneEnteSoggetto(Integer soggettoId, Integer appuntamentoId) {
		var params = new MapSqlParameterSource();
		params.addValue("soggettoId", soggettoId);
		params.addValue("appuntamentoId", appuntamentoId);
		String sql = SqlStatementAppuntamentoSoggetto.SELECT_VERIFICA_ASSOCIAZIONE_ENTE_SOGGETTO_APPUNTAMENTO;
		return queryForObject(sql, params, Boolean.class);
	}

	public List<AppuntamentoSoggettoEsteso> getAppuntamentoSoggettoByAppuntamento(Integer appuntamentoId) {
		var params = new MapSqlParameterSource();
		params.addValue("appuntamentoId", appuntamentoId);

		String sql = SqlStatementAppuntamentoSoggetto.SELECT_BY_APPUNTAMENTO;
		return query(sql, params, new AppuntamentoSoggettoEstesoMapper());
	}
	public List<AppuntamentoSoggettoEstesoWsoggetto> getAppuntamentoSoggettoByAppuntamentoWsoggetto(Integer appuntamentoId) {
		var params = new MapSqlParameterSource();
		params.addValue("appuntamentoId", appuntamentoId);

		String sql = SqlStatementAppuntamentoSoggetto.SELECT_BY_APPUNTAMENTO_W_SOGGETTO;
		return query(sql, params, new AppuntamentoSoggettoEstesoWsoggettoMapper());
	}

	public List<RuoloAppuntamentoSoggetto> selectRuoloAppuntamentoSoggettoDecodifica() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementAppuntamentoSoggetto.SELECT_RUOLO_APPUNTAMENTO_SOGGETTO_DECODIFICA;
		return query(sql, params, new RuoloAppuntamentoSoggettoMapper());
	}

}
