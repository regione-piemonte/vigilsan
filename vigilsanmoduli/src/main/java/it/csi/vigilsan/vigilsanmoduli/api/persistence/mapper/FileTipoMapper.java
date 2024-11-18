/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanmoduli.api.persistence.model.FileTipo;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class FileTipoMapper extends GenericTableMapper implements RowMapper<FileTipo> {

	@Override
	public FileTipo mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new FileTipo();
		res.setFileTipoId(rs.getObject("file_tipo_id", Integer.class));
		res.setFileTipoCod(rs.getString("file_tipo_cod"));
		res.setFileTipoOrd(rs.getString("file_tipo_ord"));
		res.setFileTipoDesc(rs.getString("file_tipo_desc"));
		res.setFileTipoHint(rs.getString("file_tipo_hint"));
		res.setFileTipoObbligatorio(rs.getBoolean("file_tipo_obbligatorio"));
		res.setFileTipoFirmaRichiesta(rs.getBoolean("file_tipo_firma_richiesta"));
		res.setFileGruppoId(rs.getObject("file_gruppo_id", Integer.class));

		generalMapRow(rs, res);
		return res;
	}

}
