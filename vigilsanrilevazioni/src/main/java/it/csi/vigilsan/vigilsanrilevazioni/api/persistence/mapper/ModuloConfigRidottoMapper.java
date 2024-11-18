/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanrilevazioni.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.model.ModuloConfigRidotto;

public class ModuloConfigRidottoMapper implements RowMapper<ModuloConfigRidotto> {

	@Override
	public ModuloConfigRidotto mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new ModuloConfigRidotto();
		res.setModuloConfigId(rs.getObject("modulo_config_id", Integer.class));
		res.setModuloConfigOrd(rs.getString("modulo_config_ord"));
		res.setModuloConfigCod(rs.getString("modulo_config_cod"));
		res.setModuloConfigDesc(rs.getString("modulo_config_desc"));
		res.setReccount(rs.getObject("reccount", Integer.class));
		res.setDocFlgObbligatorio(rs.getBoolean("doc_flg_obbligatorio"));
		
		return res;
	}

}
