/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.dao.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.PrescrizioneStatoMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PrescrizioneStato;
import it.csi.vigilsan.vigilsanpratiche.util.query.SqlStatementPrescrizioneStato;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class PrescrizioneStatoDao extends AbstractPersistence implements Dao<PrescrizioneStato> {

	private Integer getId(PrescrizioneStato t) {
		return t.getPrescrizioneStatoId();
	}

	private void setSpecificParameter(PrescrizioneStato t, MapSqlParameterSource params) {
		params.addValue("prescrizioneStatoCod", t.getPrescrizioneStatoCod());
		params.addValue("prescrizioneStatoDesc", t.getPrescrizioneStatoDesc());
		params.addValue("prescrizioneStatoFinale", t.isPrescrizioneStatoFinale());
	}

	@Override
	public PrescrizioneStato get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPrescrizioneStato.PH_PK, id);
		String sql = SqlStatementPrescrizioneStato.SELECT_BY_ID;
		return queryForObject(sql, params, new PrescrizioneStatoMapper());
	}

	@Override
	public List<PrescrizioneStato> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementPrescrizioneStato.SELECT_ALL;
		return query(sql, params, new PrescrizioneStatoMapper());
	}

	@Override
	public void insert(PrescrizioneStato t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPrescrizioneStato.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (Objects.isNull(getId(t))) {
			sql = SqlStatementPrescrizioneStato.INSERT_GENERIC;
		} else {
			params.addValue(SqlStatementPrescrizioneStato.PH_PK, getId(t));
			sql = SqlStatementPrescrizioneStato.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementPrescrizioneStato.NEXTVAL_PK_ID;
		return queryForObject(sql, params, Integer.class);
	}

	@Override
	public void update(PrescrizioneStato t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPrescrizioneStato.PH_PK, id);
		setSpecificParameter(t, params);

		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementPrescrizioneStato.UPDATE_GENERIC;
		update(sql, params);
	}

	@Override
	public void delete(PrescrizioneStato t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementPrescrizioneStato.PH_PK, getId(t));
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementPrescrizioneStato.DELETE;
		update(sql, params);
	}

}
