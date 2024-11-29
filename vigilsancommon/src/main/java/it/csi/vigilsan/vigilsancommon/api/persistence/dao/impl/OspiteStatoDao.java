/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.OspiteStatoMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.OspiteStato;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementOspiteStato;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class OspiteStatoDao extends AbstractPersistence implements Dao<OspiteStato> {

	private Integer getId(OspiteStato t) {
		return t.getOspiteStatoId();
	}

	private void setSpecificParameter(OspiteStato t, MapSqlParameterSource params) {
		params.addValue("ospiteStatoCod", t.getOspiteStatoCod());
		params.addValue("ospiteStatoDesc", t.getOspiteStatoDesc());
		params.addValue("ospiteStatoDesc", t.getOspiteStatoOrd());
		params.addValue("ospiteStatoOrd", t.getOspiteStatoHint());
		params.addValue("flgPosto", t.isFlgPosto());
		params.addValue("flgPresenza", t.isFlgPresenza());

	}

	@Override
	public OspiteStato get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementOspiteStato.PH_PK, id);
		String sql = SqlStatementOspiteStato.SELECT + SqlStatementCommon.WHERE + SqlStatementOspiteStato.PK_EQUALS;
		return queryForObject(sql, params, new OspiteStatoMapper());
	}

	@Override
	public List<OspiteStato> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementOspiteStato.SELECT + SqlStatementCommon.WHERE + SqlStatementOspiteStato.VALIDO;
		return query(sql, params, new OspiteStatoMapper());
	}

	@Override
	public void insert(OspiteStato t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementOspiteStato.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (Objects.isNull(getId(t))) {
			sql = SqlStatementOspiteStato.INSERT;
		} else {
			params.addValue(SqlStatementOspiteStato.PH_PK, getId(t));
			sql = SqlStatementOspiteStato.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementOspiteStato.NEXTVAL_PK_ID;
		return queryForObject(sql, params, Integer.class);
	}

	@Override
	public void update(OspiteStato t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementOspiteStato.PH_PK, id);
		setSpecificParameter(t, params);

		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementOspiteStato.UPDATE + SqlStatementCommon.SET + SqlStatementOspiteStato.UPDATE_CAMPI
				+ SqlStatementCommon.WHERE + SqlStatementOspiteStato.PK_EQUALS;
		update(sql, params);
	}

	@Override
	public void delete(OspiteStato t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementOspiteStato.PH_PK, getId(t));
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementOspiteStato.UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE
				+ SqlStatementOspiteStato.PK_EQUALS;
		update(sql, params);
	}

}
