/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanmoduli.api.generated.dto.ModelVoceLista;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class VoceListaMapper extends GenericTableMapper implements RowMapper<ModelVoceLista> {

	@Override
	public ModelVoceLista mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new ModelVoceLista();
		res.setModuloListaId(rs.getObject("modulo_lista_id",Integer.class));
		res.setModuloListaCod(rs.getString("modulo_lista_cod"));
		res.setModuloListaDesc(rs.getString("modulo_lista_desc"));
		res.setModuloListaHint(rs.getString("modulo_lista_hint"));
		res.setModuloListaOccMin(rs.getObject("modulo_lista_occ_min",Integer.class));
		res.setModuloListaOccMax(rs.getObject("modulo_lista_occ_max",Integer.class));
		return res;
	}

}

