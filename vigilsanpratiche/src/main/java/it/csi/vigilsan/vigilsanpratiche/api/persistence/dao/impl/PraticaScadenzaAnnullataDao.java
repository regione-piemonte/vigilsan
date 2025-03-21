/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.dao.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.PraticaScadezaAnnullataMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaScadennzaAnnullata;
import it.csi.vigilsan.vigilsanpratiche.util.query.SqlStatementPraticaScadenzaAnnullata;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class PraticaScadenzaAnnullataDao extends AbstractPersistence implements Dao<PraticaScadennzaAnnullata> {

	private Integer getId(PraticaScadennzaAnnullata t) {
		return t.getPraticaScadenzaAnnullataId();
	}

	private void setSpecificParameter(PraticaScadennzaAnnullata t, MapSqlParameterSource params) {
		params.addValue("praticaTipoId", t.getPraticaTipoId());
		params.addValue("azioneId", t.getAzioneId());
		params.addValue("azioneIdScadenza", t.getAzioneIdScadenza());
	}

	@Override
	public PraticaScadennzaAnnullata get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPraticaScadenzaAnnullata.PH_PK, id);
		String sql = SqlStatementPraticaScadenzaAnnullata.SELECT_BY_ID;
		return queryForObject(sql, params, new PraticaScadezaAnnullataMapper());
	}

	@Override
	public List<PraticaScadennzaAnnullata> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementPraticaScadenzaAnnullata.SELECT_ALL;
		return query(sql, params, new  PraticaScadezaAnnullataMapper());
	}

	@Override
	public void insert(PraticaScadennzaAnnullata t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPraticaScadenzaAnnullata.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (Objects.isNull(getId(t))) {
			sql = SqlStatementPraticaScadenzaAnnullata.INSERT_GENERIC;
		} else {
			params.addValue(SqlStatementPraticaScadenzaAnnullata.PH_PK, getId(t));
			sql = SqlStatementPraticaScadenzaAnnullata.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementPraticaScadenzaAnnullata.NEXTVAL_PK_ID;
		return queryForObject(sql, params, Integer.class);
	}

	@Override
	public void update(PraticaScadennzaAnnullata t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPraticaScadenzaAnnullata.PH_PK, id);
		setSpecificParameter(t, params);

		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementPraticaScadenzaAnnullata.UPDATE_GENERIC;
		update(sql, params);
	}

	@Override
	public void delete(PraticaScadennzaAnnullata t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementPraticaScadenzaAnnullata.PH_PK, getId(t));
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementPraticaScadenzaAnnullata.DELETE;
		update(sql, params);
	}

	public List<PraticaScadennzaAnnullata> getByPraticaTipoId(Integer praticaTipoId) {
		var params = new MapSqlParameterSource();
		params.addValue("praticaTipoId", praticaTipoId);
		String sql = SqlStatementPraticaScadenzaAnnullata.SELECT_BY_PRATICA_TIPO_ID;
		return query(sql, params, new PraticaScadezaAnnullataMapper());
	}

}
