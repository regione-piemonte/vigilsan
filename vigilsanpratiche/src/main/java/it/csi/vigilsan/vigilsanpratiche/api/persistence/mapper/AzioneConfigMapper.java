/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.Azione;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.AzioneConfig;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class AzioneConfigMapper extends GenericTableMapper implements RowMapper<AzioneConfig> {

	@Override
	public AzioneConfig mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new AzioneConfig();
		res.setAzioneConfigId(rs.getObject("azione_config_id", Integer.class));
		res.setAzioneId(rs.getObject("azione_id", Integer.class));
		res.setPraticaTipoId(rs.getObject("pratica_tipo_id", Integer.class));
		res.setProfiloId(rs.getObject("profilo_id", Integer.class));
		res.setAbilitazione(rs.getString("abilitazione"));		

		generalMapRow(rs, res);
		return res;
	}

}
