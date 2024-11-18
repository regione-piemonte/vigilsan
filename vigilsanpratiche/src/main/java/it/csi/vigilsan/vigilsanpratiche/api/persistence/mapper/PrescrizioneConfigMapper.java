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
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PrescrizioneConfig;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class PrescrizioneConfigMapper extends GenericTableMapper implements RowMapper<PrescrizioneConfig> {

	@Override
	public PrescrizioneConfig mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new PrescrizioneConfig();
		res.setPrescrizioneConfigId(rs.getObject("prescrizione_config_id", Integer.class));
		res.setPrescrizioneTipoId(rs.getObject("prescrizione_tipo_id", Integer.class));
		res.setPraticaTipoId(rs.getObject("pratica_tipo_id", Integer.class));
		res.setPraticaStatoId(rs.getObject("pratica_stato_id", Integer.class));

		generalMapRow(rs, res);
		return res;
	}

}
