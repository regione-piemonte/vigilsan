/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanmoduli.api.persistence.model.ModuloCompilatoDettaglio;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class ModuloCompilatoDettaglioMapper extends GenericTableMapper implements RowMapper<ModuloCompilatoDettaglio> {

	@Override
	public ModuloCompilatoDettaglio mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new ModuloCompilatoDettaglio();
		res.setModuloCompilatoDettaglioId(rs.getObject("modulo_compilato_dettaglio_id", Integer.class));
		res.setModuloCompilatoId(rs.getObject("modulo_compilato_id", Integer.class));
		res.setModuloVoceId(rs.getObject("modulo_voce_id", Integer.class));
		res.setModuloListaValoreId(rs.getObject("modulo_lista_valore_id", Integer.class));
		res.setFileId(rs.getObject("file_id", Integer.class));
		res.setNote(rs.getString("note"));
		res.setValore(rs.getString("valore"));
		generalMapRow(rs, res);
		return res;
	}

}
