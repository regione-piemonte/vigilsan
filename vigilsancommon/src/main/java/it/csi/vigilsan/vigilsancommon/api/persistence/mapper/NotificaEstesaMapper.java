/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import it.csi.vigilsan.vigilsancommon.api.persistence.model.NotificaEstesa;

public class NotificaEstesaMapper extends NotificaMapper<NotificaEstesa> {

	public NotificaEstesaMapper() {
		super(NotificaEstesa.class);
	}

	@Override
	public NotificaEstesa mapRow(ResultSet rs, int rowNum) throws SQLException {
		var res = super.mapRow(rs, rowNum);
		return res;
	}

}
