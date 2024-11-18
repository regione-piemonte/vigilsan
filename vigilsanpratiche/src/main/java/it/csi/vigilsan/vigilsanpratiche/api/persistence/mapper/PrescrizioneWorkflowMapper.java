/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PrescrizioneWorkflow;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class PrescrizioneWorkflowMapper extends GenericTableMapper implements RowMapper<PrescrizioneWorkflow> {

	@Override
	public PrescrizioneWorkflow mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		var res = new PrescrizioneWorkflow();
		res.setPrescrizioneWorkflowId(rs.getObject("prescrizione_workflow_id", Integer.class));
		res.setPraticaTipoId(rs.getObject("pratica_tipo_id", Integer.class));
		res.setPrescrizioneTipoId(rs.getObject("prescrizione_tipo_id", Integer.class));
		res.setAzioneId(rs.getObject("azione_id", Integer.class));
		res.setModuloId(rs.getObject("modulo_id", Integer.class));
		res.setPrescrizioneStatoIdIniziale(rs.getObject("prescrizione_stato_id_iniziale", Integer.class));
		res.setPrescrizioneStatoIdFinale(rs.getObject("prescrizione_stato_id_finale", Integer.class));

		generalMapRow(rs, res);
		return res;
	}

}
