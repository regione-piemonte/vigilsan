/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanmoduli.api.generated.dto.ModelFileTipo;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class ModelFileTipoMapper extends GenericTableMapper implements RowMapper<ModelFileTipo> {

	@Override
	public ModelFileTipo mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new ModelFileTipo();
		res.setFileTipoId(rs.getObject("file_tipo_id", Integer.class));
		res.setFileTipoCod(rs.getString("file_tipo_cod"));
		res.setFileTipoDesc(rs.getString("file_tipo_desc"));
		res.setFileTipoHint(rs.getString("file_tipo_hint"));
		res.setFileTipoObbligatorio(rs.getBoolean("file_tipo_obbligatorio"));
		res.setFileTipoFirmaRichiesta(rs.getBoolean("file_tipo_firma_richiesta"));
		res.setFileId(rs.getObject("file_id", Integer.class));
		res.setFileName(rs.getString("file_name"));
		res.setNote(rs.getString("note"));
		return res;
	}

}
