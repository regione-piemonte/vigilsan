/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanmoduli.api.generated.dto.ModelVoce;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class VoceMapper extends GenericTableMapper implements RowMapper<ModelVoce> {

	@Override
	public ModelVoce mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new ModelVoce();
		res.setModuloId(rs.getObject("modulo_id",Integer.class));
		res.setModuloVoceId(rs.getObject("modulo_voce_id",Integer.class));
		res.setModuloVoceIdPadre(rs.getObject("modulo_voce_id_padre",Integer.class));
		res.setModuloVoceCod(rs.getString("modulo_voce_cod"));
		res.setModuloVoceDesc(rs.getString("modulo_voce_desc"));
		res.setModuloVoceHint(rs.getString("modulo_voce_hint"));
		res.setModuloVoceDescCheck(rs.getString("modulo_voce_desc_check"));
		res.setFlgCheck(rs.getBoolean("flg_check"));
		res.setModuloVoceTipoDati(rs.getString("modulo_voce_tipo_dati"));
		res.setModuloVoceUnitaMisura(rs.getString("modulo_voce_unita_misura"));
		res.setFileGruppoId(rs.getObject("file_gruppo_id",Integer.class));
		res.setModuloListaId(rs.getObject("modulo_lista_id",Integer.class));
		res.setValore(rs.getString("valore"));
		res.setNote(rs.getString("note"));
		return res;
	}

}
