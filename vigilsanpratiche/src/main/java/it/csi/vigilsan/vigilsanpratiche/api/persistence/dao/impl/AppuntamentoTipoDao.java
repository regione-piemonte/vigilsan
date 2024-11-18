/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.dao.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.AppuntamentoTipoMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.AppuntamentoTipo;
import it.csi.vigilsan.vigilsanpratiche.util.query.SqlStatementAppuntamentoTipo;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class AppuntamentoTipoDao extends AbstractPersistence implements Dao<AppuntamentoTipo> {

	private Integer getId(AppuntamentoTipo t) {
		return t.getAppuntamentoTipoId();
	}

	private void setSpecificParameter(AppuntamentoTipo t, MapSqlParameterSource params) {
		params.addValue("appuntamentoTipoCod", t.getAppuntamentoTipoCod());
		params.addValue("appuntamentoTipoDesc", t.getAppuntamentoTipoDesc());
	}

	@Override
	public AppuntamentoTipo get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementAppuntamentoTipo.PH_PK, id);
		String sql = SqlStatementAppuntamentoTipo.SELECT_BY_ID;
		return queryForObject(sql, params, new AppuntamentoTipoMapper());
	}

	@Override
	public List<AppuntamentoTipo> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementAppuntamentoTipo.SELECT_ALL;
		return query(sql, params, new AppuntamentoTipoMapper());
	}

	@Override
	public void insert(AppuntamentoTipo t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementAppuntamentoTipo.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (Objects.isNull(getId(t))) {
			sql = SqlStatementAppuntamentoTipo.INSERT_GENERIC;
		} else {
			params.addValue(SqlStatementAppuntamentoTipo.PH_PK, getId(t));
			sql = SqlStatementAppuntamentoTipo.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementAppuntamentoTipo.NEXTVAL_PK_ID;
		return queryForObject(sql, params, Integer.class);
	}

	@Override
	public void update(AppuntamentoTipo t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementAppuntamentoTipo.PH_PK, id);
		setSpecificParameter(t, params);

		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementAppuntamentoTipo.UPDATE_GENERIC;
		update(sql, params);
	}

	@Override
	public void delete(AppuntamentoTipo t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementAppuntamentoTipo.PH_PK, getId(t));
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementAppuntamentoTipo.DELETE;
		update(sql, params);
	}

}
