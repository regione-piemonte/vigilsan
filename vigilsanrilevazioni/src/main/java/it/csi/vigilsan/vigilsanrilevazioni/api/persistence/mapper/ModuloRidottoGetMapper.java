/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanrilevazioni.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.model.ModuloRidottoGet;

public class ModuloRidottoGetMapper implements RowMapper<ModuloRidottoGet> {

	@Override
	public ModuloRidottoGet mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new ModuloRidottoGet();
		GenericModuloEstesoMapper.mapRaw(rs, res);
		
		res.setReccount(rs.getObject("reccount", Integer.class));

		return res;
	}

}
