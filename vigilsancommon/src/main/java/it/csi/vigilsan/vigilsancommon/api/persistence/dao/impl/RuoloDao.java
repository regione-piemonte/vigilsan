/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.RuoloMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.Ruolo;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementRuolo;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class RuoloDao extends AbstractPersistence implements Dao<Ruolo> {

	@Override
	public Ruolo get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementRuolo.PH_PK, id);
		String sql = SqlStatementRuolo.SELECT + SqlStatementCommon.WHERE
				+ SqlStatementRuolo.PK_EQUALS;
		return queryForObject(sql, params, new RuoloMapper());
	}

	@Override
	public List<Ruolo> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementRuolo.SELECT;
		return query(sql, params, new RuoloMapper());
	}

	@Override
	public void insert(Ruolo t) {
		var params = new MapSqlParameterSource();
		
		params.addValue("ruoloCod", t.getRuoloCod());
		params.addValue("ruoloDesc", t.getRuoloDesc());
		params.addValue("validitaFine", t.getValiditaFine());
		params.addValue("validitaInizio", t.getValiditaInizio());
		params.addValue("utenteCreazione", t.getUtenteCreazione());
		
		String sql = SqlStatementRuolo.INSERT;
		update(sql, params);

	}

	@Override
	public void update(Ruolo t,int id) {
		var params = new MapSqlParameterSource();
		
		params.addValue(SqlStatementRuolo.PH_PK, id);
		params.addValue("ruoloDesc", t.getRuoloDesc());
		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue("validitaFine", t.getValiditaFine());
		params.addValue("validitaInizio", t.getValiditaInizio());
		
		String sql = SqlStatementRuolo.UPDATE + SqlStatementCommon.SET
				+ SqlStatementRuolo.UPDATE_CAMPI + SqlStatementCommon.WHERE
				+ SqlStatementRuolo.PK_EQUALS;
		update(sql, params);

	}

	@Override
	public void delete(Ruolo t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementRuolo.PH_PK, t.getRuoloId());
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementRuolo.UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE
				+ SqlStatementRuolo.PK_EQUALS;
		update(sql, params);

	}
}
