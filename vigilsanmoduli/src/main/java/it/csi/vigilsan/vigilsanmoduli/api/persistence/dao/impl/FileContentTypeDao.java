/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.api.persistence.dao.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsanmoduli.api.persistence.mapper.FileContentTypeMapper;
import it.csi.vigilsan.vigilsanmoduli.api.persistence.model.FileContentType;
import it.csi.vigilsan.vigilsanmoduli.util.query.SqlStatementFileContentType;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.IntegerMapper;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class FileContentTypeDao extends AbstractPersistence implements Dao<FileContentType> {

	private Integer getId(FileContentType t) {
		return t.getFileContentTypeId();
	}

	private void setSpecificParameter(FileContentType t, MapSqlParameterSource params) {
		params.addValue("fileContentTypeCod", t.getFileContentTypeCod());
		params.addValue("fileContentTypeDesc", t.getFileContentTypeDesc());
		params.addValue("fileContentTypeOrd", t.getFileContentTypeOrd());
		params.addValue("fileContentTypeHint", t.getFileContentTypeHint());
		params.addValue("fileContentTypeMime", t.getFileContentTypeMime());
		params.addValue("fileContentTypeExt", t.getFileContentTypeExt());
	}

	@Override
	public FileContentType get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementFileContentType.PH_PK, id);
		String sql = SqlStatementFileContentType.SELECT_BY_ID;
		return queryForObject(sql, params, new FileContentTypeMapper());
	}

	@Override
	public List<FileContentType> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementFileContentType.SELECT_ALL;
		return query(sql, params, new FileContentTypeMapper());
	}

	@Override
	public void insert(FileContentType t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementFileContentType.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (Objects.isNull(getId(t))) {
			sql = SqlStatementFileContentType.INSERT_GENERIC;
		} else {
			params.addValue(SqlStatementFileContentType.PH_PK, getId(t));
			sql = SqlStatementFileContentType.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementFileContentType.NEXTVAL_PK_ID;
		return queryForObject(sql, params, Integer.class);
	}

	@Override
	public void update(FileContentType t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementFileContentType.PH_PK, id);
		setSpecificParameter(t, params);

		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementFileContentType.UPDATE_GENERIC;
		update(sql, params);
	}

	@Override
	public void delete(FileContentType t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementFileContentType.PH_PK, getId(t));
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementFileContentType.DELETE;
		update(sql, params);
	}


	public List<FileContentType> getListByFileTipoId(Integer fileTipoId) {
		var params = new MapSqlParameterSource();
		params.addValue("fileTipoId", fileTipoId);
		String sql = SqlStatementFileContentType.SELECT_BY_FILE_TIPO_ID;
		return query(sql, params, new FileContentTypeMapper());
	}


	public List<Integer> getListIdByFileTipoId(Integer fileTipoId) {
		var params = new MapSqlParameterSource();
		params.addValue("fileTipoId", fileTipoId);
		String sql = SqlStatementFileContentType.SELECT_BY_FILE_TIPO_ID;
		return query(sql, params, new IntegerMapper("file_content_type_id"));
	}

	public FileContentType getByCode(String code) {

		var params = new MapSqlParameterSource();

		params.addValue("fileContentTypeCod", code);
		String sql = SqlStatementFileContentType.SELECT_BY_COD;
		return queryForObject(sql, params, new FileContentTypeMapper());
	}

}
