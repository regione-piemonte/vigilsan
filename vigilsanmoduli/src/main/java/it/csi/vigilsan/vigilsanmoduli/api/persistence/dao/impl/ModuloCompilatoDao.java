/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.api.persistence.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsanmoduli.api.persistence.mapper.ModuloCompilatoMapper;
import it.csi.vigilsan.vigilsanmoduli.api.persistence.model.ModuloCompilato;
import it.csi.vigilsan.vigilsanmoduli.util.query.SqlStatementModuloCompilato;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class ModuloCompilatoDao extends AbstractPersistence implements Dao<ModuloCompilato> {

	@Override
	public ModuloCompilato get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue("moduloCompilatoId", id);
		String sql = SqlStatementModuloCompilato.SELECT + SqlStatementCommon.WHERE
				+ SqlStatementModuloCompilato.PK_EQUALS;
		return queryForObject(sql, params, new ModuloCompilatoMapper());
	}

	@Override
	public List<ModuloCompilato> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementModuloCompilato.SELECT;
		return query(sql, params, new ModuloCompilatoMapper());
	}

	@Override
	public void insert(ModuloCompilato t) {
		var params = new MapSqlParameterSource();
		params.addValue("note", t.getNote());
		params.addValue("moduloId", t.getModuloId());
		params.addValue("validitaFine", t.getValiditaFine());
		params.addValue("validitaInizio", t.getValiditaInizio());
		params.addValue("utenteCreazione", t.getUtenteCreazione());

		String sql;
		if (t.getModuloCompilatoId() == null) {
			sql = SqlStatementModuloCompilato.INSERT;
		} else {
			params.addValue("moduloCompilatoId", t.getModuloCompilatoId());
			sql = SqlStatementModuloCompilato.INSERT_W_PK;

		}
		update(sql, params);

	}

	@Override
	public void update(ModuloCompilato t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue("moduloCompilatoId", id);
		params.addValue("note", t.getNote());
		params.addValue("moduloId", t.getModuloId());
		params.addValue("utenteModifica", t.getUtenteModifica());
		params.addValue("validitaFine", t.getValiditaFine());
		params.addValue("validitaInizio", t.getValiditaInizio());

		String sql = SqlStatementModuloCompilato.UPDATE + SqlStatementCommon.SET
				+ SqlStatementModuloCompilato.UPDATE_CAMPI + SqlStatementCommon.WHERE
				+ SqlStatementModuloCompilato.PK_EQUALS;
		update(sql, params);

	}

	@Override
	public void delete(ModuloCompilato t) {
		var params = new MapSqlParameterSource();
		params.addValue("moduloCompilatoId", t.getModuloCompilatoId());
		params.addValue("utenteCancellazione", t.getUtenteCancellazione());
		String sql = SqlStatementModuloCompilato.UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE
				+ SqlStatementModuloCompilato.PK_EQUALS;
		update(sql, params);

	}
}
