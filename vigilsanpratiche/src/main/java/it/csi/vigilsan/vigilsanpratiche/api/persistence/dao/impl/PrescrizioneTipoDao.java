/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.dao.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.PrescrizioneTipoMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PrescrizioneTipo;
import it.csi.vigilsan.vigilsanpratiche.util.query.SqlStatementPrescrizioneTipo;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class PrescrizioneTipoDao extends AbstractPersistence implements Dao<PrescrizioneTipo> {

	private Integer getId(PrescrizioneTipo t) {
		return t.getPrescrizioneTipoId();
	}

	private void setSpecificParameter(PrescrizioneTipo t, MapSqlParameterSource params) {
		params.addValue("prescrizioneTipoCod", t.getPrescrizioneTipoCod());
		params.addValue("prescrizioneTipoDesc", t.getPrescrizioneTipoDesc());
	}

	@Override
	public PrescrizioneTipo get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPrescrizioneTipo.PH_PK, id);
		String sql = SqlStatementPrescrizioneTipo.SELECT_BY_ID;
		return queryForObject(sql, params, new PrescrizioneTipoMapper());
	}

	@Override
	public List<PrescrizioneTipo> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementPrescrizioneTipo.SELECT_ALL;
		return query(sql, params, new PrescrizioneTipoMapper());
	}

	@Override
	public void insert(PrescrizioneTipo t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPrescrizioneTipo.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (Objects.isNull(getId(t))) {
			sql = SqlStatementPrescrizioneTipo.INSERT_GENERIC;
		} else {
			params.addValue(SqlStatementPrescrizioneTipo.PH_PK, getId(t));
			sql = SqlStatementPrescrizioneTipo.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementPrescrizioneTipo.NEXTVAL_PK_ID;
		return queryForObject(sql, params, Integer.class);
	}

	@Override
	public void update(PrescrizioneTipo t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPrescrizioneTipo.PH_PK, id);
		setSpecificParameter(t, params);

		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementPrescrizioneTipo.UPDATE_GENERIC;
		update(sql, params);
	}

	@Override
	public void delete(PrescrizioneTipo t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementPrescrizioneTipo.PH_PK, getId(t));
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementPrescrizioneTipo.DELETE;
		update(sql, params);
	}

}
