/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.EnteTipoMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.EnteTipo;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementEnteTipo;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class EnteTipoDao extends AbstractPersistence implements Dao<EnteTipo> {

	private Integer getId(EnteTipo t) {
		return t.getEnteTipoId();
	}

	private void setSpecificParameter(EnteTipo t, MapSqlParameterSource params) {
		params.addValue("enteTipoDesc", t.getEnteTipoDesc());
		params.addValue("enteTipoCod", t.getEnteTipoCod());
	}

	@Override
	public EnteTipo get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementEnteTipo.PH_PK, id);
		String sql = SqlStatementEnteTipo.SELECT_BY_ID;
		return queryForObject(sql, params, new EnteTipoMapper());
	}

	@Override
	public List<EnteTipo> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementEnteTipo.SELECT_ALL;
		return query(sql, params, new EnteTipoMapper());
	}

	@Override
	public void insert(EnteTipo t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementEnteTipo.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (Objects.isNull(getId(t))) {
			sql = SqlStatementEnteTipo.INSERT_GENERIC;
		} else {
			params.addValue(SqlStatementEnteTipo.PH_PK, getId(t));
			sql = SqlStatementEnteTipo.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementEnteTipo.NEXTVAL_PK_ID;
		return queryForObject(sql, params, Integer.class);
	}

	@Override
	public void update(EnteTipo t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementEnteTipo.PH_PK, id);
		setSpecificParameter(t, params);

		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementEnteTipo.UPDATE_GENERIC;
		update(sql, params);
	}

	@Override
	public void delete(EnteTipo t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementEnteTipo.PH_PK, getId(t));
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementEnteTipo.DELETE;
		update(sql, params);
	}

}
