/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsancommon.api.persistence.model.StrutturaCategoria;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class StrutturaCategoriaMapper extends GenericTableMapper implements RowMapper<StrutturaCategoria> {

	@Override
	public StrutturaCategoria mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new StrutturaCategoria();
		res.setStrutturaCategoriaId(rs.getObject("struttura_categoria_id", Integer.class));
		res.setStrutturaCategoriaCod(rs.getString("struttura_categoria_cod"));
		res.setStrutturaCategoriaDesc(rs.getString("struttura_categoria_desc"));
		generalMapRow(rs, res);
		return res;
	}

}
