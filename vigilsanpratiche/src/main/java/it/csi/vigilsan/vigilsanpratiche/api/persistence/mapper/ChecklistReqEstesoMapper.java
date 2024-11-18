/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.ChecklistReqEsteso;
import it.csi.vigilsan.vigilsanutil.generic.util.DateUtils;

public class ChecklistReqEstesoMapper extends ChecklistReqMapper<ChecklistReqEsteso> {

	public ChecklistReqEstesoMapper() {
		super(ChecklistReqEsteso.class);
	}

	@Override
	public ChecklistReqEsteso mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = super.mapRow(rs, rowNum);
		res.setFlgSelezionabile(rs.getBoolean("flg_selezionabile"));
		res.setFlgSelezionato(rs.getBoolean("flg_selezionato"));
		res.setFlgConforme(rs.getBoolean("flg_conforme"));

		return res;
	}

}
