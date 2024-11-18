/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.api.persistence.dao.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsanmoduli.api.persistence.mapper.FileMapper;
import it.csi.vigilsan.vigilsanmoduli.api.persistence.model.File;
import it.csi.vigilsan.vigilsanmoduli.util.query.SqlStatementFile;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class FileDao extends AbstractPersistence implements Dao<File> {

	private Integer getId(File t) {
		return t.getFileId();
	}

	private void setSpecificParameter(File t, MapSqlParameterSource params) {
		params.addValue("fileTipoId", t.getFileTipoId());
		params.addValue("fileName", t.getFileName());
		params.addValue("fileSize", t.getFileSize());
		params.addValue("fileContentTypeId", t.getFileContentTypeId());
		params.addValue("filePath", t.getFilePath());
		params.addValue("cfFirmaVerificata", t.getCfFirmaVerificata());
		params.addValue("fileNameOrig", t.getFileNameOrig());
	}

	@Override
	public File get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementFile.PH_PK, id);
		String sql = SqlStatementFile.SELECT_BY_ID;
		return queryForObject(sql, params, new FileMapper());
	}

	@Override
	public List<File> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementFile.SELECT_ALL;
		return query(sql, params, new FileMapper());
	}

	@Override
	public void insert(File t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementFile.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (Objects.isNull(getId(t))) {
			sql = SqlStatementFile.INSERT_GENERIC;
		} else {
			params.addValue(SqlStatementFile.PH_PK, getId(t));
			sql = SqlStatementFile.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementFile.NEXTVAL_PK_ID;
		return queryForObject(sql, params, Integer.class);
	}

	@Override
	public void update(File t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementFile.PH_PK, id);
		setSpecificParameter(t, params);

		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementFile.UPDATE_GENERIC;
		update(sql, params);
	}

	@Override
	public void delete(File t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementFile.PH_PK, getId(t));
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementFile.DELETE;
		update(sql, params);
	}
}
