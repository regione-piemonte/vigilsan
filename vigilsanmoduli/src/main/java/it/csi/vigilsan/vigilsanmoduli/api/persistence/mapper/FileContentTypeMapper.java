/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanmoduli.api.persistence.model.FileContentType;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class  FileContentTypeMapper extends GenericTableMapper implements RowMapper<FileContentType> {
	
	@Override
	public FileContentType mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new FileContentType();
			res.setFileContentTypeId(rs.getObject("file_content_type_id", Integer.class));
			res.setFileContentTypeCod(rs.getString("file_content_type_cod"));
			res.setFileContentTypeDesc(rs.getString("file_content_type_desc"));
			res.setFileContentTypeOrd(rs.getString("file_content_type_ord"));
			res.setFileContentTypeHint(rs.getString("file_content_type_hint"));
			res.setFileContentTypeMime(rs.getString("file_content_type_mime"));
			res.setFileContentTypeExt(rs.getString("file_content_type_ext"));
		generalMapRow(rs, res);
		return  res;
	}

}
