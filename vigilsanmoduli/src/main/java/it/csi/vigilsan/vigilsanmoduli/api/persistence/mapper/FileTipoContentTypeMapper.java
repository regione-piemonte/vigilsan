/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanmoduli.api.persistence.model.FileTipoContentType;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class  FileTipoContentTypeMapper extends GenericTableMapper implements RowMapper<FileTipoContentType> {
	
	@Override
	public FileTipoContentType mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new FileTipoContentType();
		res.setFileTipoContentTypeId(rs.getObject("file_tipo_content_type_id", Integer.class));
		res.setFileContentTypeId(rs.getObject("file_content_type_id", Integer.class));
		res.setFileTipoId(rs.getObject("file_tipo_id", Integer.class));
		generalMapRow(rs, res);
		return  res;
	}

}
