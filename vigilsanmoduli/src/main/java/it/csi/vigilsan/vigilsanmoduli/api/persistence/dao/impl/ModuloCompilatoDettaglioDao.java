/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.api.persistence.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsanmoduli.api.persistence.mapper.ModuloCompilatoDettaglioMapper;
import it.csi.vigilsan.vigilsanmoduli.api.persistence.model.ModuloCompilatoDettaglio;
import it.csi.vigilsan.vigilsanmoduli.util.query.SqlStatementModuloCompilatoDettaglio;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class ModuloCompilatoDettaglioDao extends AbstractPersistence implements Dao<ModuloCompilatoDettaglio> {

	@Override
	public ModuloCompilatoDettaglio get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue("moduloCompilatoDettaglioId", id);
		String sql = SqlStatementModuloCompilatoDettaglio.SELECT + SqlStatementCommon.WHERE
				+ SqlStatementModuloCompilatoDettaglio.PK_EQUALS;
		return queryForObject(sql, params, new ModuloCompilatoDettaglioMapper());
	}

	@Override
	public List<ModuloCompilatoDettaglio> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementModuloCompilatoDettaglio.SELECT;
		return query(sql, params, new ModuloCompilatoDettaglioMapper());
	}

	@Override
	public void insert(ModuloCompilatoDettaglio t) {
		var params = new MapSqlParameterSource();
		params.addValue("note", t.getNote());
		params.addValue("valore", t.getValore());
		params.addValue("moduloCompilatoId", t.getModuloCompilatoId());
		params.addValue("moduloVoceId", t.getModuloVoceId());
		params.addValue("moduloListaValoreId", t.getModuloListaValoreId());
		params.addValue("fileId", t.getFileId());
		params.addValue("validitaFine", t.getValiditaFine());
		params.addValue("validitaInizio", t.getValiditaInizio());
		params.addValue("utenteCreazione", t.getUtenteCreazione());

		String sql = SqlStatementModuloCompilatoDettaglio.INSERT;
		update(sql, params);

	}

	public int[] insert(List<ModuloCompilatoDettaglio> t) {

		String sql = SqlStatementModuloCompilatoDettaglio.INSERT;
		return batchUpdate(sql, t);
	}

	@Override
	public void update(ModuloCompilatoDettaglio t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue("moduloCompilatoDettaglioId", id);
		params.addValue("note", t.getNote());
		params.addValue("valore", t.getValore());
		params.addValue("moduloCompilatoId", t.getModuloCompilatoId());
		params.addValue("moduloVoceId", t.getModuloVoceId());
		params.addValue("moduloListaValoreId", t.getModuloListaValoreId());
		params.addValue("fileId", t.getFileId());

		String sql = SqlStatementModuloCompilatoDettaglio.UPDATE + SqlStatementCommon.SET
				+ SqlStatementModuloCompilatoDettaglio.UPDATE_CAMPI + SqlStatementCommon.WHERE
				+ SqlStatementModuloCompilatoDettaglio.PK_EQUALS;
		update(sql, params);

	}

	@Override
	public void delete(ModuloCompilatoDettaglio t) {
		var params = new MapSqlParameterSource();
		params.addValue("moduloCompilatoDettaglioId", t.getModuloCompilatoDettaglioId());
		params.addValue("utenteCancellazione", t.getUtenteCancellazione());
		String sql = SqlStatementModuloCompilatoDettaglio.UPDATE + SqlStatementCommon.LOGIC_DELETE
				+ SqlStatementCommon.WHERE + SqlStatementModuloCompilatoDettaglio.PK_EQUALS;
		update(sql, params);

	}
}
