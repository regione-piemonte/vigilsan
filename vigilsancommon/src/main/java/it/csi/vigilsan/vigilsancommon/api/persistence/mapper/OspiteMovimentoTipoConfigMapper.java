/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsancommon.api.generated.dto.ModelOspiteMovimentoTipoConfig;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.OspiteMovimentoTipoConfig;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class OspiteMovimentoTipoConfigMapper extends GenericTableMapper implements RowMapper<ModelOspiteMovimentoTipoConfig> {

	@Override
	public ModelOspiteMovimentoTipoConfig mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new OspiteMovimentoTipoConfig();
			res.setOspiteMovimentoTipoId(rs.getObject("ospite_movimento_tipo_id", Integer.class));
			res.setOspiteStatoId(rs.getObject("ospite_stato_id", Integer.class));
			res.setOspiteMovimentoTipoCfgId(rs.getObject("ospite_movimento_tipo_cfg_id", Integer.class));
		generalMapRow(rs, res);
		return res;
	}

}
