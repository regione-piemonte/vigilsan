/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.StrutturaSoggettoMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.StrutturaSoggetto;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementSoggetto;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementStruttura;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementStrutturaSoggetto;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class StrutturaSoggettoDao extends AbstractPersistence implements Dao<StrutturaSoggetto> {

	private Integer getId(StrutturaSoggetto t) {
		return t.getStrSoggId();
	}

	private void setSpecificParameter(StrutturaSoggetto t, MapSqlParameterSource params) {
		params.addValue(SqlStatementSoggetto.PH_PK, t.getSoggettoId());
		params.addValue(SqlStatementStruttura.PH_PK, t.getStrutturaId());
		params.addValue("ruoloStrutturaSoggettoId", t.getRuoloStrutturaSoggettoId());

	}

	@Override
	public StrutturaSoggetto get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementStrutturaSoggetto.PH_PK, id);
		String sql = SqlStatementStrutturaSoggetto.SELECT + SqlStatementCommon.WHERE + SqlStatementStrutturaSoggetto.PK_EQUALS;
		return queryForObject(sql, params, new StrutturaSoggettoMapper());
	}

	@Override
	public List<StrutturaSoggetto> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementStrutturaSoggetto.SELECT + SqlStatementCommon.WHERE + SqlStatementStrutturaSoggetto.VALIDO;
		return query(sql, params, new StrutturaSoggettoMapper());
	}

	@Override
	public void insert(StrutturaSoggetto t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementStrutturaSoggetto.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (Objects.isNull(getId(t))) {
			sql = SqlStatementStrutturaSoggetto.INSERT;
		} else {
			params.addValue(SqlStatementStrutturaSoggetto.PH_PK, getId(t));
			sql = SqlStatementStrutturaSoggetto.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementStrutturaSoggetto.NEXTVAL_PK_ID;
		return queryForObject(sql, params, Integer.class);
	}

	@Override
	public void update(StrutturaSoggetto t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementStrutturaSoggetto.PH_PK, id);
		setSpecificParameter(t, params);

		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementStrutturaSoggetto.UPDATE + SqlStatementCommon.SET + SqlStatementStrutturaSoggetto.UPDATE_CAMPI
				+ SqlStatementCommon.WHERE + SqlStatementStrutturaSoggetto.PK_EQUALS;
		update(sql, params);
	}

	@Override
	public void delete(StrutturaSoggetto t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementStrutturaSoggetto.PH_PK, getId(t));
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementStrutturaSoggetto.UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE
				+ SqlStatementStrutturaSoggetto.PK_EQUALS;
		update(sql, params);
	}

	public StrutturaSoggetto getBySoggettoIdAndStrutturaIdValid(Integer soggettoId, Integer strutturaId) {

		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementSoggetto.PH_PK, soggettoId);
		params.addValue(SqlStatementStruttura.PH_PK, strutturaId);

		String sql = SqlStatementStrutturaSoggetto.SELECT_BY_STRUTTURA_ID_SOGGETTO_ID + SqlStatementCommon.AND
				+ SqlStatementStrutturaSoggetto.VALIDO;
		return queryForObject(sql, params, new StrutturaSoggettoMapper());

	}

}
