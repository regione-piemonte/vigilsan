/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelStrutturaTipoRidotto;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class StrutturaTipoRidottaMapper extends GenericTableMapper implements RowMapper<ModelStrutturaTipoRidotto> {

	@Override
	public ModelStrutturaTipoRidotto mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new ModelStrutturaTipoRidotto();
		res.setStrutturaTipoId(rs.getObject("struttura_tipo_id", Integer.class));
		res.setStrutturaTipoCod(rs.getString("struttura_tipo_cod"));
		res.setStrutturaTipoDesc(rs.getString("struttura_tipo_desc"));
		return res;
	}

}
