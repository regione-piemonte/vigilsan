/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanrilevazioni.api.persistence.dao.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.mapper.VerificaDocumentazioneMapper;
import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.model.VerificaDocumentazione;
import it.csi.vigilsan.vigilsanrilevazioni.util.query.SqlStatementVerificaDocumentazione;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class VerificaDocumentazioneDao extends AbstractPersistence implements Dao<VerificaDocumentazione> {

	private Integer getId(VerificaDocumentazione t) {
		return t.getVerificaDocumentazioneId();
	}

	private void setSpecificParameter(VerificaDocumentazione t, MapSqlParameterSource params) {
		params.addValue("documentazioneId", t.getDocumentazioneId());
		params.addValue("note", t.getNote());
		params.addValue("dataoraVerifica", t.getDataoraVerifica());
		params.addValue("esitoVerifica", t.isEsitoVerifica());
		params.addValue("notificaId", t.getNotificaId());
	}

	@Override
	public VerificaDocumentazione get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementVerificaDocumentazione.PH_PK, id);
		String sql = SqlStatementVerificaDocumentazione.SELECT_BY_ID;
		return queryForObject(sql, params, new VerificaDocumentazioneMapper());
	}

	@Override
	public List<VerificaDocumentazione> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementVerificaDocumentazione.SELECT_ALL;
		return query(sql, params, new VerificaDocumentazioneMapper());
	}

	@Override
	public void insert(VerificaDocumentazione t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementVerificaDocumentazione.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (Objects.isNull(getId(t))) {
			sql = SqlStatementVerificaDocumentazione.INSERT_GENERIC;
		} else {
			params.addValue(SqlStatementVerificaDocumentazione.PH_PK, getId(t));
			sql = SqlStatementVerificaDocumentazione.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementVerificaDocumentazione.NEXTVAL_PK_ID;
		return queryForObject(sql, params, Integer.class);
	}

	@Override
	public void update(VerificaDocumentazione t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementVerificaDocumentazione.PH_PK, id);
		setSpecificParameter(t, params);

		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementVerificaDocumentazione.UPDATE_GENERIC;
		update(sql, params);
	}

	@Override
	public void delete(VerificaDocumentazione t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementVerificaDocumentazione.PH_PK, getId(t));
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementVerificaDocumentazione.DELETE;
		update(sql, params);
	}

	public VerificaDocumentazione getByDcoumentazioneId(Integer documentazioneId) {

		var params = new MapSqlParameterSource();

		params.addValue("documentazioneId", documentazioneId);
		String sql = SqlStatementVerificaDocumentazione.SELECT_BY_DOCUMENTAZIONE_ID;
		return queryForObject(sql, params, new VerificaDocumentazioneMapper());
	}

	public void deleteOldDocumenti(Integer documentazioneId, String shibIdentitaCodiceFiscale) {
		var params = new MapSqlParameterSource();
		params.addValue("documentazioneId", documentazioneId);
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, shibIdentitaCodiceFiscale);
		String sql = SqlStatementVerificaDocumentazione.DELETE_OLD;
		update(sql, params);
	}

}
