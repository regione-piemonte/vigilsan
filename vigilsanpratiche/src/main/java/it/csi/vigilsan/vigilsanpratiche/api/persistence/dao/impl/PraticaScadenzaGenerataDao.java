/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.dao.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.PraticaScadenzaGenerataMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.PrescrizioneWorkflowMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaScadenzaGenerata;
import it.csi.vigilsan.vigilsanpratiche.util.query.SqlStatementPraticaScadenzaGenerata;
import it.csi.vigilsan.vigilsanpratiche.util.query.SqlStatementPrescrizioneWorkflow;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class PraticaScadenzaGenerataDao extends AbstractPersistence implements Dao<PraticaScadenzaGenerata> {

	private Integer getId(PraticaScadenzaGenerata t) {
		return t.getPraticaScadenzaGenerataId();
	}

	private void setSpecificParameter(PraticaScadenzaGenerata t, MapSqlParameterSource params) {
		params.addValue("praticaTipoId", t.getPraticaTipoId());
		params.addValue("azioneId", t.getAzioneId());
		params.addValue("azioneIdScadenza", t.getAzioneIdScadenza());
		params.addValue("giorniScadenza", t.getGiorniScadenza());
		params.addValue("profiloId", t.getProfiloId());
		params.addValue("prescrizioneTipoId", t.getPrescrizioneTipoId());
		params.addValue("appuntamentoTipoId", t.getAppuntamentoTipoId());
	}

	@Override
	public PraticaScadenzaGenerata get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPraticaScadenzaGenerata.PH_PK, id);
		String sql = SqlStatementPraticaScadenzaGenerata.SELECT_BY_ID;
		return queryForObject(sql, params, new PraticaScadenzaGenerataMapper());
	}

	@Override
	public List<PraticaScadenzaGenerata> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementPraticaScadenzaGenerata.SELECT_ALL;
		return query(sql, params, new PraticaScadenzaGenerataMapper());
	}

	@Override
	public void insert(PraticaScadenzaGenerata t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPraticaScadenzaGenerata.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (Objects.isNull(getId(t))) {
			sql = SqlStatementPraticaScadenzaGenerata.INSERT_GENERIC;
		} else {
			params.addValue(SqlStatementPraticaScadenzaGenerata.PH_PK, getId(t));
			sql = SqlStatementPraticaScadenzaGenerata.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementPraticaScadenzaGenerata.NEXTVAL_PK_ID;
		return queryForObject(sql, params, Integer.class);
	}

	@Override
	public void update(PraticaScadenzaGenerata t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPraticaScadenzaGenerata.PH_PK, id);
		setSpecificParameter(t, params);

		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementPraticaScadenzaGenerata.UPDATE_GENERIC;
		update(sql, params);
	}

	@Override
	public void delete(PraticaScadenzaGenerata t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementPraticaScadenzaGenerata.PH_PK, getId(t));
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementPraticaScadenzaGenerata.DELETE;
		update(sql, params);
	}

	public List<PraticaScadenzaGenerata> getByPraticaTipoId(Integer praticaTipoId) {
		var params = new MapSqlParameterSource();
		params.addValue("praticaTipoId", praticaTipoId);
		String sql = SqlStatementPraticaScadenzaGenerata.SELECT_BY_PRATICA_TIPO_ID;
		return query(sql, params, new PraticaScadenzaGenerataMapper());
	}

}
