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

import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelPrescrizioneEsteso;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.AppuntamentoForPostMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.PrescrizioneEstesaMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.PrescrizioneForPostMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.PrescrizioneMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.Prescrizione;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PrescrizioneForPost;
import it.csi.vigilsan.vigilsanpratiche.util.query.SqlStatementAppuntamento;
import it.csi.vigilsan.vigilsanpratiche.util.query.SqlStatementPrescrizione;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class PrescrizioneDao extends AbstractPersistence implements Dao<Prescrizione> {

	private Integer getId(Prescrizione t) {
		return t.getPrescrizioneId();
	}

	private void setSpecificParameter(Prescrizione t, MapSqlParameterSource params) {
		params.addValue("praticaId", t.getPraticaId());
		params.addValue("prescrizioneTipoId", t.getPrescrizioneTipoId());
		params.addValue("dataoraApertura", t.getDataoraApertura());
		params.addValue("dataoraChiusura", t.getDataoraChiusura());
		params.addValue("prescrizioneNumero", t.getPrescrizioneNumero());
	}

	@Override
	public Prescrizione get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPrescrizione.PH_PK, id);
		String sql = SqlStatementPrescrizione.SELECT_BY_ID;
		return queryForObject(sql, params, new PrescrizioneMapper<>(Prescrizione.class));
	}
	public PrescrizioneForPost getForPost(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPrescrizione.PH_PK, id);
		String sql = SqlStatementPrescrizione.SELECT_BY_ID;
		return queryForObject(sql, params, new PrescrizioneMapper<>(PrescrizioneForPost.class));
	}

	@Override
	public List<Prescrizione> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementPrescrizione.SELECT_ALL;
		return query(sql, params, new PrescrizioneMapper<>(Prescrizione.class));
	}

	@Override
	public void insert(Prescrizione t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPrescrizione.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (Objects.isNull(getId(t))) {
			sql = SqlStatementPrescrizione.INSERT_GENERIC;
		} else {
			params.addValue(SqlStatementPrescrizione.PH_PK, getId(t));
			sql = SqlStatementPrescrizione.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementPrescrizione.NEXTVAL_PK_ID;
		return queryForObject(sql, params, Integer.class);
	}

	@Override
	public void update(Prescrizione t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPrescrizione.PH_PK, id);
		setSpecificParameter(t, params);

		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementPrescrizione.UPDATE_GENERIC;
		update(sql, params);
	}

	@Override
	public void delete(Prescrizione t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementPrescrizione.PH_PK, getId(t));
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementPrescrizione.DELETE;
		update(sql, params);
	}

	public List<ModelPrescrizioneEsteso> getListByPratica(Integer profiloId, Integer praticaId) {
		var params = new MapSqlParameterSource();

		params.addValue("praticaId", praticaId);
		params.addValue("profiloId", profiloId);
		String sql = SqlStatementPrescrizione.SELECT_PRATICHE_LISTA_PRESCRIZIONE;
		return query(sql, params, new PrescrizioneEstesaMapper());
	}

	public  List<ModelPrescrizioneEsteso> getByListaPrescrizioneId(Integer praticaId, Date filterDate) {
		var params = new MapSqlParameterSource();

		params.addValue("praticaId", praticaId);
		StringBuilder sql = new StringBuilder(SqlStatementPrescrizione.SELECT_PRESCRIZIONI_BY_LISTA_PRESCRIZIONI_ID);
		if(filterDate!=null) {
			sql.append(SqlStatementPrescrizione.SELECT_PRESCRIZIONI_BY_LISTA_PRESCRIZIONI_ID_FILTER_DATE);
			params.addValue("filterDate", filterDate);
		}
		return query(sql.toString(), params, new PrescrizioneEstesaMapper());
	}

	public List<PrescrizioneForPost> getByPraticaIdForPost(Integer praticaId) {
		var params = new MapSqlParameterSource();
		params.addValue("praticaId", praticaId);
		String sql = SqlStatementPrescrizione.SELECT_ALL_BY_PRATICA_ID;
		return query(sql, params, new PrescrizioneForPostMapper());
	}

}
