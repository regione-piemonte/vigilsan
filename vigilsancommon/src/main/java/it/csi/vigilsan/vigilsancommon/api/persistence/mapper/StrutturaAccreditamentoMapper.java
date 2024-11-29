/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsancommon.api.persistence.model.StrutturaAccreditamento;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class StrutturaAccreditamentoMapper extends GenericTableMapper implements RowMapper<StrutturaAccreditamento> {

	@Override
	public StrutturaAccreditamento mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new StrutturaAccreditamento();
		res.setStrutturaAccreditamentoId(rs.getObject("struttura_accreditamento_id", Integer.class));
		res.setStrutturaAccreditamentoCod(rs.getString("struttura_accreditamento_cod"));
		res.setStrutturaAccreditamentoCodArpe(rs.getString("struttura_accreditamento_cod_arpe"));
		res.setStrutturaAccreditamentoDesc(rs.getString("struttura_accreditamento_desc"));
		generalMapRow(rs, res);
		return res;
	}

}
