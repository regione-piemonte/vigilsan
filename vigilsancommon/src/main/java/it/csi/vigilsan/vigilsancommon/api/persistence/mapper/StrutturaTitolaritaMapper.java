/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsancommon.api.persistence.model.StrutturaAccreditamento;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.StrutturaTitolarita;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class StrutturaTitolaritaMapper extends GenericTableMapper implements RowMapper<StrutturaTitolarita> {

	@Override
	public StrutturaTitolarita mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new StrutturaTitolarita();
		res.setStrutturaTitolaritaId(rs.getObject("struttura_titolarita_id", Integer.class));
		res.setStrutturaTitolaritaCod(rs.getString("struttura_titolarita_cod"));
		res.setStrutturaTitolaritaCodArpe(rs.getString("struttura_titolarita_cod_arpe"));
		res.setStrutturaTitolaritaDesc(rs.getString("struttura_titolarita_desc"));
		generalMapRow(rs, res);
		return res;
	}

}