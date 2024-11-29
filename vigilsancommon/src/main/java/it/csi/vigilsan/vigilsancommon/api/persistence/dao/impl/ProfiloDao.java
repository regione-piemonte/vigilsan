/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.ProfiloMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.Profilo;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementProfilo;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class ProfiloDao extends AbstractPersistence implements Dao<Profilo> {

	@Override
	public Profilo get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementProfilo.PH_PK, id);
		String sql = SqlStatementProfilo.SELECT + SqlStatementCommon.WHERE + SqlStatementProfilo.PK_EQUALS;
		return queryForObject(sql, params, new ProfiloMapper());
	}

	@Override
	public List<Profilo> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementProfilo.SELECT + SqlStatementCommon.WHERE + SqlStatementProfilo.VALIDO;
		return query(sql, params, new ProfiloMapper());
	}

	@Override
	public void insert(Profilo t) {
		var params = new MapSqlParameterSource();

		params.addValue("profiloCod", t.getProfiloCod());
		params.addValue("profiloDesc", t.getProfiloDesc());
		params.addValue("validitaFine", t.getValiditaFine());
		params.addValue("validitaInizio", t.getValiditaInizio());
		params.addValue("utenteCreazione", t.getUtenteCreazione());

		String sql = SqlStatementProfilo.INSERT;
		update(sql, params);

	}

	@Override
	public void update(Profilo t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementProfilo.PH_PK, id);
		params.addValue("profiloDesc", t.getProfiloDesc());
		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue("validitaFine", t.getValiditaFine());
		params.addValue("validitaInizio", t.getValiditaInizio());

		String sql = SqlStatementProfilo.UPDATE + SqlStatementCommon.SET + SqlStatementProfilo.UPDATE_CAMPI
				+ SqlStatementCommon.WHERE + SqlStatementProfilo.PK_EQUALS;
		update(sql, params);

	}

	@Override
	public void delete(Profilo t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementProfilo.PH_PK, t.getProfiloId());
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementProfilo.UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE
				+ SqlStatementProfilo.PK_EQUALS;
		update(sql, params);

	}
}
