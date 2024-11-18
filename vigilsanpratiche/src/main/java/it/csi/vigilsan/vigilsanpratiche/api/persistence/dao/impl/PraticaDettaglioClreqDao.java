/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.dao.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.ClreqEsitoMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.PraticaDettaglioClreqEstesoMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.PraticaDettaglioClreqMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.ClreqEsito;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaDettaglioClreq;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaDettaglioClreqEsteso;
import it.csi.vigilsan.vigilsanpratiche.util.query.SqlStatementPraticaDettaglioClreq;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class PraticaDettaglioClreqDao extends AbstractPersistence implements Dao<PraticaDettaglioClreq> {

	private Integer getId(PraticaDettaglioClreq t) {
		return t.getPraticaDetClreqId();
	}

	private void setSpecificParameter(PraticaDettaglioClreq t, MapSqlParameterSource params) {
		params.addValue("praticaDetId", t.getPraticaDetId());
		params.addValue("clreqId", t.getClreqId());
		params.addValue("clreqEsitoId", t.getClreqEsitoId());
		params.addValue("moduloCompilatoId", t.getModuloCompilatoId());
		params.addValue("note", t.getNote());
	}

	@Override
	public PraticaDettaglioClreq get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPraticaDettaglioClreq.PH_PK, id);
		String sql = SqlStatementPraticaDettaglioClreq.SELECT_BY_ID;
		return queryForObject(sql, params, new PraticaDettaglioClreqMapper<>(PraticaDettaglioClreq.class));
	}

	@Override
	public List<PraticaDettaglioClreq> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementPraticaDettaglioClreq.SELECT_ALL;
		return query(sql, params, new PraticaDettaglioClreqMapper<>(PraticaDettaglioClreq.class));
	}

	@Override
	public void insert(PraticaDettaglioClreq t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPraticaDettaglioClreq.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (Objects.isNull(getId(t))) {
			sql = SqlStatementPraticaDettaglioClreq.INSERT_GENERIC;
		} else {
			params.addValue(SqlStatementPraticaDettaglioClreq.PH_PK, getId(t));
			sql = SqlStatementPraticaDettaglioClreq.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementPraticaDettaglioClreq.NEXTVAL_PK_ID;
		return queryForObject(sql, params, Integer.class);
	}

	@Override
	public void update(PraticaDettaglioClreq t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPraticaDettaglioClreq.PH_PK, id);
		setSpecificParameter(t, params);

		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementPraticaDettaglioClreq.UPDATE_GENERIC;
		update(sql, params);
	}

	@Override
	public void delete(PraticaDettaglioClreq t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementPraticaDettaglioClreq.PH_PK, getId(t));
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementPraticaDettaglioClreq.DELETE;
		update(sql, params);
	}

	public PraticaDettaglioClreq getValido(Integer id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPraticaDettaglioClreq.PH_PK, id);
		String sql = SqlStatementPraticaDettaglioClreq.SELECT_BY_ID + SqlStatementCommon.AND + SqlStatementPraticaDettaglioClreq.VALIDO;
		return queryForObject(sql, params, new PraticaDettaglioClreqMapper<>(PraticaDettaglioClreq.class));
	}

	public List<PraticaDettaglioClreqEsteso> getByPraticaDetId(Integer praticaDetId) {
		var params = new MapSqlParameterSource();
		params.addValue("praticaDetId", praticaDetId);
		String sql = SqlStatementPraticaDettaglioClreq.SELECT_BY_PRATICA_DET_ID;
		return query(sql, params, new PraticaDettaglioClreqEstesoMapper());
	}

	public List<ClreqEsito> getDecodificaClreqEsito() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementPraticaDettaglioClreq.SELECT_CLRQ_ESITO_DECODIFICA;
		return query(sql, params, new ClreqEsitoMapper());
		
	}

	public List<PraticaDettaglioClreq> getValidoByPraticaDetIdAndClreqId(Integer praticaDetId, Integer clreqId) {
		var params = new MapSqlParameterSource();
		params.addValue("praticaDetId", praticaDetId);
		params.addValue("clreqId", clreqId);
		String sql = SqlStatementPraticaDettaglioClreq.SELECT_BY_PRATICA_DET_ID_AND_CLREQ_ID_VALIDO;
		return query(sql, params, new PraticaDettaglioClreqMapper<>(PraticaDettaglioClreq.class));
	}

}
