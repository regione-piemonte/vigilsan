/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.api.persistence.dao.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsanmoduli.api.persistence.mapper.FileTipoContentTypeMapper;
import it.csi.vigilsan.vigilsanmoduli.api.persistence.model.FileTipoContentType;
import it.csi.vigilsan.vigilsanmoduli.util.query.SqlStatementFileTipoContentType;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class FileTipoContentTypeDao extends AbstractPersistence implements Dao<FileTipoContentType> {

	private Integer getId(FileTipoContentType t) {
		return t.getFileTipoContentTypeId();
	}

	private void setSpecificParameter(FileTipoContentType t, MapSqlParameterSource params) {
		params.addValue("fileTipoId", t.getFileTipoId());
		params.addValue("fileContentTypeId", t.getFileContentTypeId());
	}

	@Override
	public FileTipoContentType get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementFileTipoContentType.PH_PK, id);
		String sql = SqlStatementFileTipoContentType.SELECT_BY_ID;
		return queryForObject(sql, params, new FileTipoContentTypeMapper());
	}

	@Override
	public List<FileTipoContentType> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementFileTipoContentType.SELECT_ALL;
		return query(sql, params, new FileTipoContentTypeMapper());
	}
	
	@Override
	public void insert(FileTipoContentType t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementFileTipoContentType.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (Objects.isNull(getId(t))) {
			sql = SqlStatementFileTipoContentType.INSERT_GENERIC;
		} else {
			params.addValue(SqlStatementFileTipoContentType.PH_PK, getId(t));
			sql = SqlStatementFileTipoContentType.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementFileTipoContentType.NEXTVAL_PK_ID;
		return queryForObject(sql, params, Integer.class);
	}

	@Override
	public void update(FileTipoContentType t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementFileTipoContentType.PH_PK, id);
		setSpecificParameter(t, params);

		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementFileTipoContentType.UPDATE_GENERIC;
		update(sql, params);
	}

	@Override
	public void delete(FileTipoContentType t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementFileTipoContentType.PH_PK, getId(t));
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementFileTipoContentType.DELETE;
		update(sql, params);
	}

	public List<FileTipoContentType> getListByFileTipoId(Integer fileTipoId) {
		var params = new MapSqlParameterSource();
		params.addValue("fileTipoId", fileTipoId);
		String sql = SqlStatementFileTipoContentType.SELECT_BY_FILE_TIPO_ID;
		return query(sql, params, new FileTipoContentTypeMapper());
	}

}
