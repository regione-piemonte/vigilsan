/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.api.persistence.repository.impl;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsanmoduli.api.generated.dto.ModelFileGruppo;
import it.csi.vigilsan.vigilsanmoduli.api.generated.dto.ModelFileTipo;
import it.csi.vigilsan.vigilsanmoduli.api.persistence.mapper.FileGruppoMapper;
import it.csi.vigilsan.vigilsanmoduli.api.persistence.mapper.ModelFileTipoMapper;
import it.csi.vigilsan.vigilsanmoduli.util.query.SqlStatementFile;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;

@Repository
public class FileRepository extends AbstractPersistence {

	public ModelFileGruppo getFileGruppoFromFileGruppoId(Integer id) {

		var params = new MapSqlParameterSource();

		params.addValue("fileGruppoId", id);
		String sql = SqlStatementFile.SELECT_FILE_GRUPPO_BY_ID;
		return queryForObject(sql, params, new FileGruppoMapper());
	}

	public List<ModelFileTipo> getFileTipoFromModuloVoceId(Integer moduloVoceId, Integer moduloCompilatoId) {

		var params = new MapSqlParameterSource();

		params.addValue("moduloVoceId", moduloVoceId);
		params.addValue("moduloCompilatoId", moduloCompilatoId);
		
		String sql = SqlStatementFile.SELECT_FILE_TIPO_BY_FILE_GRUPPO_ID;
		return query(sql, params, new ModelFileTipoMapper());
	}
}
