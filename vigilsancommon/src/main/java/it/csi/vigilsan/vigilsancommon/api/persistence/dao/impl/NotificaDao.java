/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.NotificaEstesaMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.NotificaMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.Notifica;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.NotificaEstesa;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementNotifica;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class NotificaDao extends AbstractPersistence implements Dao<Notifica> {

	private Integer getId(Notifica t) {
		return t.getNotificaId();
	}

	private void setSpecificParameter(Notifica t, MapSqlParameterSource params) {
		
		params.addValue("profiloId", t.getProfiloId());
		params.addValue("enteId", t.getEnteId());
		params.addValue("strutturaId", t.getStrutturaId());
		params.addValue("soggettoId", t.getSoggettoId());
		params.addValue("title", t.getTitle());
		params.addValue("bodyTextShort", t.getBodyTextShort());
		params.addValue("bodyTextLong", t.getBodyTextLong());
		params.addValue("bodyHtml", t.getBodyHtml());
	}

	@Override
	public Notifica get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementNotifica.PH_PK, id);
		String sql = SqlStatementNotifica.SELECT_BY_ID;
		return queryForObject(sql, params, new NotificaMapper<>(Notifica.class));
	}

	@Override
	public List<Notifica> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementNotifica.SELECT_ALL;
		return query(sql, params, new NotificaMapper<>(Notifica.class));
	}

	@Override
	public void insert(Notifica t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementNotifica.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (Objects.isNull(getId(t))) {
			sql = SqlStatementNotifica.INSERT_GENERIC;
		} else {
			params.addValue(SqlStatementNotifica.PH_PK, getId(t));
			sql = SqlStatementNotifica.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementNotifica.NEXTVAL_PK_ID;
		return queryForObject(sql, params, Integer.class);
	}

	@Override
	public void update(Notifica t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementNotifica.PH_PK, id);
		setSpecificParameter(t, params);

		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementNotifica.UPDATE_GENERIC;
		update(sql, params);
	}

	@Override
	public void delete(Notifica t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementNotifica.PH_PK, getId(t));
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementNotifica.DELETE;
		update(sql, params);
	}

	public List<NotificaEstesa> getNotificheDaInviare() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementNotifica.SELECT_NOTIFICHE_DA_INVIARE;
		return query(sql, params, new NotificaEstesaMapper());
	}

}
