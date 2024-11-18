/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelAppuntamentoEsteso;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.AppuntamentoEstesoMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.AppuntamentoEstesoPerDiarioMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.AppuntamentoForPostMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.AppuntamentoMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.Appuntamento;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.AppuntamentoEstesoPerDiario;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.AppuntamentoForPost;
import it.csi.vigilsan.vigilsanpratiche.util.query.SqlStatementAppuntamento;
import it.csi.vigilsan.vigilsanpratiche.util.query.SqlStatementPraticaDettaglio;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class AppuntamentoDao extends AbstractPersistence implements Dao<Appuntamento> {

	private Integer getId(Appuntamento t) {
		return t.getAppuntamentoId();
	}

	private void setSpecificParameter(Appuntamento t, MapSqlParameterSource params) {
		params.addValue("praticaId", t.getPraticaId());
		params.addValue("appuntamentoTipoId", t.getAppuntamentoTipoId());
		params.addValue("dataoraInizio", t.getDataoraInizio());
		params.addValue("dataoraFine", t.getDataoraFine());
		params.addValue("dataoraApertura", t.getDataoraApertura());
		params.addValue("dataoraChiusura", t.getDataoraChiusura());
		params.addValue("appuntamentoNumero", t.getAppuntamentoNumero());
		
	}

	@Override
	public Appuntamento get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementAppuntamento.PH_PK, id);
		String sql = SqlStatementAppuntamento.SELECT_BY_ID;
		return queryForObject(sql, params, new AppuntamentoMapper<>(Appuntamento.class));
	}

	@Override
	public List<Appuntamento> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementAppuntamento.SELECT_ALL;
		return query(sql, params, new AppuntamentoMapper<>(Appuntamento.class));
	}

	@Override
	public void insert(Appuntamento t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementAppuntamento.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (Objects.isNull(getId(t))) {
			sql = SqlStatementAppuntamento.INSERT_GENERIC;
		} else {
			params.addValue(SqlStatementAppuntamento.PH_PK, getId(t));
			sql = SqlStatementAppuntamento.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementAppuntamento.NEXTVAL_PK_ID;
		return queryForObject(sql, params, Integer.class);
	}

	@Override
	public void update(Appuntamento t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementAppuntamento.PH_PK, id);
		setSpecificParameter(t, params);

		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementAppuntamento.UPDATE_GENERIC;
		update(sql, params);
	}

	@Override
	public void delete(Appuntamento t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementAppuntamento.PH_PK, getId(t));
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementAppuntamento.DELETE;
		update(sql, params);
	}

	public List<ModelAppuntamentoEsteso> getListByPratica(Integer profiloId, Integer praticaId) {
		var params = new MapSqlParameterSource();

		params.addValue("praticaId", praticaId);
		params.addValue("profiloId", profiloId);
		String sql = SqlStatementAppuntamento.SELECT_PRATICHE_LISTA_APPUNTAMENTO;
		return query(sql, params, new AppuntamentoEstesoMapper());
	}

	public List<ModelAppuntamentoEsteso> getByListaAppuntamentoId(Integer praticaId) {
		var params = new MapSqlParameterSource();
		params.addValue("praticaId", praticaId);
		String sql = SqlStatementAppuntamento.SELECT_BY_PRATICA_ID;
		return query(sql, params, new AppuntamentoEstesoMapper());
	}
	public List<AppuntamentoEstesoPerDiario> getByListaAppuntamentoIdPerDiario(Integer praticaId, Date filterDate) {
		var params = new MapSqlParameterSource();
		params.addValue("praticaId", praticaId);
		StringBuilder sql = new StringBuilder(SqlStatementAppuntamento.SELECT_BY_PRATICA_ID);
		if(filterDate!=null) {
			sql.append(SqlStatementAppuntamento.SELECT_BY_PRATICA_ID_FILTER_DATE);
			params.addValue("filterDate", filterDate);
		}
		return query(sql.toString(), params, new AppuntamentoEstesoPerDiarioMapper());
	}

	public List<AppuntamentoForPost> getByPraticaIdForPost(Integer praticaId) {
		var params = new MapSqlParameterSource();
		params.addValue("praticaId", praticaId);
		String sql = SqlStatementAppuntamento.SELECT_ALL_BY_PRATICA_ID;
		return query(sql, params, new AppuntamentoForPostMapper());
	}

}
