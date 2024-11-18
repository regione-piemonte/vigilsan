/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaScadennzaAnnullata;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class PraticaScadezaAnnullataMapper extends GenericTableMapper implements RowMapper<PraticaScadennzaAnnullata> {

	@Override
	public PraticaScadennzaAnnullata mapRow(ResultSet rs, int rowNum) throws SQLException {


		var res = new PraticaScadennzaAnnullata();
		res.setPraticaScadenzaAnnullataId(rs.getObject("pratica_scadenza_annullata_id", Integer.class));
		res.setAzioneId(rs.getObject("azione_id", Integer.class));
		res.setPraticaTipoId(rs.getObject("pratica_tipo_id", Integer.class));
		res.setAzioneIdScadenza(rs.getObject("azione_id_Scadenza", Integer.class));

		generalMapRow(rs, res);
		return res;
	}

}
