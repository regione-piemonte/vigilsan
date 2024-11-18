/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.dao.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.AzioneEstesaMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.AzioneMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.Azione;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.AzioneEstesa;
import it.csi.vigilsan.vigilsanpratiche.util.query.SqlStatementAzione;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class AzioneDao extends AbstractPersistence implements Dao<Azione> {

	private Integer getId(Azione t) {
		return t.getAzioneId();
	}

	private void setSpecificParameter(Azione t, MapSqlParameterSource params) {
		params.addValue("azioneDesc", t.getAzioneDesc());
		params.addValue("azioneCod", t.getAzioneCod());
	}

	@Override
	public Azione get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementAzione.PH_PK, id);
		String sql = SqlStatementAzione.SELECT_BY_ID;
		return queryForObject(sql, params, new AzioneMapper());
	}

	@Override
	public List<Azione> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementAzione.SELECT_ALL;
		return query(sql, params, new AzioneMapper());
	}

	@Override
	public void insert(Azione t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementAzione.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (Objects.isNull(getId(t))) {
			sql = SqlStatementAzione.INSERT_GENERIC;
		} else {
			params.addValue(SqlStatementAzione.PH_PK, getId(t));
			sql = SqlStatementAzione.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementAzione.NEXTVAL_PK_ID;
		return queryForObject(sql, params, Integer.class);
	}

	@Override
	public void update(Azione t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementAzione.PH_PK, id);
		setSpecificParameter(t, params);

		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementAzione.UPDATE_GENERIC;
		update(sql, params);
	}

	@Override
	public void delete(Azione t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementAzione.PH_PK, getId(t));
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementAzione.DELETE;
		update(sql, params);
	}

	public List<AzioneEstesa> getAzioniAppuntamento(Integer profiloId, Integer praticaTipoId) {
		var params = new MapSqlParameterSource();
		params.addValue("profiloId", profiloId);
		params.addValue("praticaTipoId", praticaTipoId);
		
		String sql = SqlStatementAzione.SELECT_AZIONI_APPUNTAMENTO;
		return query(sql, params, new AzioneEstesaMapper());
	}

	public List<AzioneEstesa> getAzioniPratica(Integer profiloId, Integer praticaTipoId) {
		var params = new MapSqlParameterSource();
		params.addValue("profiloId", profiloId);
		params.addValue("praticaTipoId", praticaTipoId);
		
		String sql = SqlStatementAzione.SELECT_AZIONI_PRATICA;
		return query(sql, params, new AzioneEstesaMapper());
	}

	public List<AzioneEstesa> getAzioniPrescrizione(Integer profiloId, Integer praticaTipoId) {
		var params = new MapSqlParameterSource();
		params.addValue("profiloId", profiloId);
		params.addValue("praticaTipoId", praticaTipoId);
		
		String sql = SqlStatementAzione.SELECT_AZIONI_PRESCRIZIONE;
		return query(sql, params, new AzioneEstesaMapper());
	}

	public AzioneEstesa getAzioneIniziale(Integer id, Integer praticaTipoId) {

		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementAzione.PH_PK, id);
		params.addValue("praticaTipoId", praticaTipoId);
		String sql = SqlStatementAzione.SELECT_AZIONE_INIZIALE_BY_ID;
		return queryForObject(sql, params, new AzioneEstesaMapper());
	}

}
