/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaScadenzaGenerata;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class PraticaScadenzaGenerataMapper extends GenericTableMapper implements RowMapper<PraticaScadenzaGenerata> {

	@Override
	public PraticaScadenzaGenerata mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new PraticaScadenzaGenerata();
		res.setPraticaScadenzaGenerataId(rs.getObject("pratica_scadenza_generata_id", Integer.class));
		res.setAzioneId(rs.getObject("azione_id", Integer.class));
		res.setPraticaTipoId(rs.getObject("pratica_tipo_id", Integer.class));
		res.setAzioneIdScadenza(rs.getObject("azione_id_Scadenza", Integer.class));
		res.setGiorniScadenza(rs.getObject("giorni_scadenza", Integer.class));
		res.setProfiloId(rs.getObject("profilo_id", Integer.class));
		res.setPrescrizioneTipoId(rs.getObject("prescrizione_tipo_id", Integer.class));
		res.setAppuntamentoTipoId(rs.getObject("appuntamento_tipo_id", Integer.class));

		generalMapRow(rs, res);
		return res;
	}

}
