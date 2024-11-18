/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanmoduli.api.persistence.model.File;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class FileMapper extends GenericTableMapper implements RowMapper<File> {

	@Override
	public File mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new File();
		res.setFileId(rs.getObject("file_id", Integer.class));
		res.setFileTipoId(rs.getObject("file_tipo_id", Integer.class));
		res.setFileName(rs.getString("file_name"));
		res.setFileSize(rs.getObject("file_size", Integer.class));
		res.setFileContentTypeId(rs.getObject("file_content_type_id", Integer.class));
		res.setFilePath(rs.getString("file_path"));
		res.setCfFirmaVerificata(rs.getString("cf_firma_verificata"));
		res.setFileNameOrig(rs.getString("file_name_orig"));
		generalMapRow(rs, res);
		return res;
	}

}
