/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import it.csi.vigilsan.vigilsancommon.api.persistence.model.StrutturaPaginata;

public class StrutturaPaginataMapper extends StrutturaMapper<StrutturaPaginata> {

	// Costruttore che richiede il parametro di tipo Class<T>
	public StrutturaPaginataMapper() {
		super(StrutturaPaginata.class);
	}

	@Override
	public StrutturaPaginata mapRow(ResultSet rs, int rowNum) throws SQLException {

		StrutturaPaginata res = super.mapRow(rs, rowNum);
		
		res.setTotalCount(rs.getObject("total_count", Long.class));
		return res;
	}

}
