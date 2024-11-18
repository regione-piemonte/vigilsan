/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.AppuntamentoConfig;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.AppuntamentoWorkflow;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaWorkflow;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class PraticaWorkflowMapper extends GenericTableMapper implements RowMapper<PraticaWorkflow> {

	@Override
	public PraticaWorkflow mapRow(ResultSet rs, int rowNum) throws SQLException {
		  
		var res = new PraticaWorkflow();
		res.setPraticaWorkflowId(rs.getObject("pratica_workflow_id", Integer.class));
		res.setPraticaTipoId(rs.getObject("pratica_tipo_id", Integer.class));
		res.setAzioneId(rs.getObject("azione_id", Integer.class));
		res.setModuloId(rs.getObject("modulo_id", Integer.class));
		res.setPraticaStatoIdIniziale(rs.getObject("pratica_stato_id_iniziale", Integer.class));
		res.setPraticaStatoIdFinale(rs.getObject("pratica_stato_id_finale", Integer.class));

		generalMapRow(rs, res);
		return res;
	}

}
