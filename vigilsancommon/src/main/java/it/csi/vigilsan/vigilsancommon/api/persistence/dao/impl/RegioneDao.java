/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.RegioneMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.Regione;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementRegione;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class RegioneDao extends AbstractPersistence implements Dao<Regione> {

	@Override
	public Regione get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementRegione.PH_PK, id);
		String sql = SqlStatementRegione.SELECT + SqlStatementCommon.WHERE
				+ SqlStatementRegione.PK_EQUALS;
		return queryForObject(sql, params, new RegioneMapper());
	}

	@Override
	public List<Regione> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementRegione.SELECT;
		return query(sql, params, new RegioneMapper());
	}

	@Override
	public void insert(Regione t) {
		var params = new MapSqlParameterSource();

		params.addValue("regioneCod", t.getRegioneCod());
		params.addValue("regioneDesc", t.getRegioneDesc());
		
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (t.getRegioneId() == null) {
			sql = SqlStatementRegione.INSERT;
		} else {
			params.addValue(SqlStatementRegione.PH_PK, t.getRegioneId());
			sql = SqlStatementRegione.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementRegione.NEXTVAL_PK_ID;
		return queryForObject(sql, params,Integer.class);
	}

	@Override
	public void update(Regione t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementRegione.PH_PK, id);
		params.addValue("regioneCod", t.getRegioneCod());
		params.addValue("regioneDesc", t.getRegioneDesc());
		
		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementRegione.UPDATE + SqlStatementCommon.SET
				+ SqlStatementRegione.UPDATE_CAMPI + SqlStatementCommon.WHERE
				+ SqlStatementRegione.PK_EQUALS;
		update(sql, params);
	}

	@Override
	public void delete(Regione t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementRegione.PH_PK, t.getRegioneId());
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementRegione.UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE
				+ SqlStatementRegione.PK_EQUALS;
		update(sql, params);

	}
}
