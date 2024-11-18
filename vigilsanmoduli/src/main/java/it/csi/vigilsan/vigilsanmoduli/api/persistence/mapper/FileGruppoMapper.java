/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanmoduli.api.generated.dto.ModelFileGruppo;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class FileGruppoMapper extends GenericTableMapper implements RowMapper<ModelFileGruppo> {

	@Override
	public ModelFileGruppo mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new ModelFileGruppo();
		res.setFileGruppoCod(rs.getString("file_gruppo_cod"));
		res.setFileGruppoDesc(rs.getString("file_gruppo_desc"));
		return res;
	}

}
