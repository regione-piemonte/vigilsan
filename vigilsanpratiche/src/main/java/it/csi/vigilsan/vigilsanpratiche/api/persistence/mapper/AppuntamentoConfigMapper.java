/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.AppuntamentoConfig;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.Azione;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.AzioneConfig;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class AppuntamentoConfigMapper extends GenericTableMapper implements RowMapper<AppuntamentoConfig> {

	@Override
	public AppuntamentoConfig mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new AppuntamentoConfig();
		res.setAppuntamentoConfigId(rs.getObject("appuntamento_config_id", Integer.class));
		res.setPraticaTipoId(rs.getObject("pratica_tipo_id", Integer.class));
		res.setPraticaStatoId(rs.getObject("pratica_stato_id", Integer.class));
		res.setAppuntamentoTipoId(rs.getObject("appuntamento_tipo_id", Integer.class));
		  
		generalMapRow(rs, res);
		return res;
	}

}
