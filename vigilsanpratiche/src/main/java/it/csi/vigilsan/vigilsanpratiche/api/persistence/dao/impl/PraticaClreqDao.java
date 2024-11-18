/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.dao.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.PraticaClreqMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaClreq;
import it.csi.vigilsan.vigilsanpratiche.util.query.SqlStatementPraticaClreq;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class PraticaClreqDao extends AbstractPersistence implements Dao<PraticaClreq> {

	private Integer getId(PraticaClreq t) {
		return t.getPraticaClreqId();
	}

	private void setSpecificParameter(PraticaClreq t, MapSqlParameterSource params) {
		params.addValue("praticaId", t.getPraticaId());
		params.addValue("prescrizioneId", t.getPrescrizioneId());
		params.addValue("appuntamentoId", t.getAppuntamentoId());
		params.addValue("clreqId", t.getClreqId());
	}

	@Override
	public PraticaClreq get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPraticaClreq.PH_PK, id);
		String sql = SqlStatementPraticaClreq.SELECT_BY_ID;
		return queryForObject(sql, params, new PraticaClreqMapper());
	}

	@Override
	public List<PraticaClreq> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementPraticaClreq.SELECT_ALL;
		return query(sql, params, new PraticaClreqMapper());
	}

	@Override
	public void insert(PraticaClreq t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPraticaClreq.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (Objects.isNull(getId(t))) {
			sql = SqlStatementPraticaClreq.INSERT_GENERIC;
		} else {
			params.addValue(SqlStatementPraticaClreq.PH_PK, getId(t));
			sql = SqlStatementPraticaClreq.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementPraticaClreq.NEXTVAL_PK_ID;
		return queryForObject(sql, params, Integer.class);
	}

	@Override
	public void update(PraticaClreq t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPraticaClreq.PH_PK, id);
		setSpecificParameter(t, params);

		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementPraticaClreq.UPDATE_GENERIC;
		update(sql, params);
	}

	@Override
	public void delete(PraticaClreq t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementPraticaClreq.PH_PK, getId(t));
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementPraticaClreq.DELETE;
		update(sql, params);
	}

	public List<PraticaClreq> selectValidiByPraticaIdAppuntamentoIdPraticaId(Integer praticaId, Integer prescrizioneId,
			Integer appuntamentoId) {
		var params = new MapSqlParameterSource();
		params.addValue("praticaId", praticaId);
		StringBuilder sql = new StringBuilder(SqlStatementPraticaClreq.SELECT_ALL_BY_PRATICA_ID);
		if (Objects.nonNull(prescrizioneId)) {
			params.addValue("prescrizioneId", prescrizioneId);
			sql.append(SqlStatementPraticaClreq.SELECT_ALL_BY_PRATICA_ID_PRESCRIZIONE_ID);
		} else if (Objects.nonNull(appuntamentoId)) {
			params.addValue("appuntamentoId", appuntamentoId);
			sql.append(SqlStatementPraticaClreq.SELECT_ALL_BY_PRATICA_ID_APPUNTAMENTO_ID);

		}
		return query(sql.toString(), params, new PraticaClreqMapper());
	}

	public void insertPostInserimentoPratica(Integer praticaId) {
		var params = new MapSqlParameterSource();

		params.addValue("praticaId", praticaId);
		String sql = SqlStatementPraticaClreq.INSERT_POST_INSERIMENTO_PRATICA;

		update(sql, params);

	}

}
