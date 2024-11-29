/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.InvioNotificaMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.InvioNotifica;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementInvioNotifica;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class InvioNotificaDao extends AbstractPersistence implements Dao<InvioNotifica> {

	private Integer getId(InvioNotifica t) {
		return t.getInvioNotificaId();
	}

	private void setSpecificParameter(InvioNotifica t, MapSqlParameterSource params) {

		params.addValue("notificaId", t.getNotificaId());
		params.addValue("cfDestinatario", t.getCfDestinatario());
		params.addValue("dataoraInvio", t.getDataoraInvio());
		params.addValue("esitoInvio", t.isEsitoInvio());
		params.addValue("erroreInvio", t.getErroreInvio());
		params.addValue("requestContent", t.getRequestContent());
		params.addValue("responseContent", t.getResponseContent());
		params.addValue("responseResult", t.getResponseResult());

	}

	@Override
	public InvioNotifica get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementInvioNotifica.PH_PK, id);
		String sql = SqlStatementInvioNotifica.SELECT_BY_ID;
		return queryForObject(sql, params, new InvioNotificaMapper<>(InvioNotifica.class));
	}

	@Override
	public List<InvioNotifica> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementInvioNotifica.SELECT_ALL;
		return query(sql, params, new InvioNotificaMapper<>(InvioNotifica.class));
	}

	@Override
	public void insert(InvioNotifica t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementInvioNotifica.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (Objects.isNull(getId(t))) {
			sql = SqlStatementInvioNotifica.INSERT_GENERIC;
		} else {
			params.addValue(SqlStatementInvioNotifica.PH_PK, getId(t));
			sql = SqlStatementInvioNotifica.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementInvioNotifica.NEXTVAL_PK_ID;
		return queryForObject(sql, params, Integer.class);
	}

	@Override
	public void update(InvioNotifica t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementInvioNotifica.PH_PK, id);
		setSpecificParameter(t, params);

		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementInvioNotifica.UPDATE_GENERIC;
		update(sql, params);
	}

	@Override
	public void delete(InvioNotifica t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementInvioNotifica.PH_PK, getId(t));
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementInvioNotifica.DELETE;
		update(sql, params);
	}

}
