/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanmoduli.api.generated.dto.ModelVoceListaValore;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class VoceListaValoreMapper extends GenericTableMapper implements RowMapper<ModelVoceListaValore> {

	@Override
	public ModelVoceListaValore mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new ModelVoceListaValore();
		res.setModuloListaValoreId(rs.getObject("modulo_lista_valore_id",Integer.class));
		res.setModuloListaValoreCod(rs.getString("modulo_lista_valore_cod"));
		res.setModuloListaValoreDesc(rs.getString("modulo_lista_valore_desc"));
		res.setModuloListaValoreHint(rs.getString("modulo_lista_valore_hint"));
		res.setValore(rs.getBoolean("valore"));
		return res;
	}

}
