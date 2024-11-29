/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsancommon.api.persistence.model.Soggetto;

public class SoggettoMapper extends SoggettoMapperGeneric implements RowMapper<Soggetto> {

	@Override
	public Soggetto mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		var res = new Soggetto();
		this.mapRow(rs, res);
		return res;
	}

}
