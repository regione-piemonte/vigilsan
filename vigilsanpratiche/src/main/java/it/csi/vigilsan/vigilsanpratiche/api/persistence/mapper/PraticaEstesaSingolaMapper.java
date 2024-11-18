/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaEstesa;

public class PraticaEstesaSingolaMapper extends PraticaMapper<PraticaEstesa> {

	public PraticaEstesaSingolaMapper() {
		super(PraticaEstesa.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public PraticaEstesa mapRow(ResultSet rs, int rowNum) throws SQLException {

		PraticaEstesa res = super.mapRow(rs, rowNum);
		res.setModuloId(rs.getObject("modulo_id", Integer.class));
		res.setModuloCompilatoId(rs.getObject("modulo_compilato_id", Integer.class));
		return res;
	}

}
