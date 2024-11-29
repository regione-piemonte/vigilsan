/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.EnteSoggettoEstesoMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.EnteSoggettoMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.RuoloEnteSoggettoMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.EnteSoggetto;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.EnteSoggettoEsteso;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.RuoloEnteSoggetto;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementEnteSoggetto;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class EnteSoggettoDao extends AbstractPersistence implements Dao<EnteSoggetto> {

	private Integer getId(EnteSoggetto t) {
		return t.getEnteSoggId();
	}

	private void setSpecificParameter(EnteSoggetto t, MapSqlParameterSource params) {
		params.addValue("enteId", t.getEnteId());
		params.addValue("soggettoId", t.getSoggettoId());
		params.addValue("ruoloEnteSoggettoId", t.getRuoloEnteSoggettoId());
	}

	@Override
	public EnteSoggetto get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementEnteSoggetto.PH_PK, id);
		String sql = SqlStatementEnteSoggetto.SELECT_BY_ID;
		return queryForObject(sql, params, new EnteSoggettoMapper());
	}

	@Override
	public List<EnteSoggetto> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementEnteSoggetto.SELECT_ALL;
		return query(sql, params, new EnteSoggettoMapper());
	}

	@Override
	public void insert(EnteSoggetto t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementEnteSoggetto.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (Objects.isNull(getId(t))) {
			sql = SqlStatementEnteSoggetto.INSERT_GENERIC;
		} else {
			params.addValue(SqlStatementEnteSoggetto.PH_PK, getId(t));
			sql = SqlStatementEnteSoggetto.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementEnteSoggetto.NEXTVAL_PK_ID;
		return queryForObject(sql, params, Integer.class);
	}

	@Override
	public void update(EnteSoggetto t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementEnteSoggetto.PH_PK, id);
		setSpecificParameter(t, params);

		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementEnteSoggetto.UPDATE_GENERIC;
		update(sql, params);
	}

	@Override
	public void delete(EnteSoggetto t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementEnteSoggetto.PH_PK, getId(t));
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementEnteSoggetto.DELETE;
		update(sql, params);
	}

	public List<EnteSoggettoEsteso> getListByEnteId(Integer enteId, Integer enteRuoloId) {
		var params = new MapSqlParameterSource();
		params.addValue("enteId", enteId);

		String sql = SqlStatementEnteSoggetto.SELECT_BY_LISTA_ENTE_ID;
		if (enteRuoloId != null) {
			params.addValue("ruoloEnteSoggettoId", enteRuoloId);
			sql = sql + SqlStatementEnteSoggetto.SELECT_BY_LISTA_ENTE_ID_FILTER_RUOLO;

		}

		return query(sql, params, new EnteSoggettoEstesoMapper());

	}

	public List<EnteSoggetto> getByEnteAndSoggetto(Integer enteId, Integer soggettoId) {

		var params = new MapSqlParameterSource();

		params.addValue("enteId", enteId);
		params.addValue("soggettoId", soggettoId);
		String sql = SqlStatementEnteSoggetto.SELECT_BY_ENTE_SOGGETTO;
		return query(sql, params, new EnteSoggettoMapper());
	}

	public void invalidaByEnteESoggetto(Integer enteId, Integer soggettoId, String utenteModifica) {

		var params = new MapSqlParameterSource();

		params.addValue("enteId", enteId);
		params.addValue("soggettoId", soggettoId);
		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, utenteModifica);

		String sql = SqlStatementEnteSoggetto.INVALIDA_BY_ENTE_SOGGETTO;
		update(sql, params);
	}

	public List<RuoloEnteSoggetto> getRuoloEnteSoggettoDecodificaLista() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementEnteSoggetto.SELECT_RUOLO_ALL;
		return query(sql, params, new RuoloEnteSoggettoMapper());
	}

	public void invalida(Integer enteSoggId, String shibIdentitaCodiceFiscale) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementEnteSoggetto.PH_PK, enteSoggId);
		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, shibIdentitaCodiceFiscale);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, null);
		String sql = SqlStatementEnteSoggetto.INVALIDA_BY_ID;
		update(sql, params);
	}

	public void invalida(EnteSoggetto t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementEnteSoggetto.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		params.addValue(SqlStatementEnteSoggetto.PH_PK, getId(t));
		sql = SqlStatementEnteSoggetto.INSERT_INVALIDA_W_PK;
		
		update(sql, params);
	}

}
