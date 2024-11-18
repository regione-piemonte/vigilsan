/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PrescrizioneStato;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class PrescrizioneStatoMapper extends GenericTableMapper implements RowMapper<PrescrizioneStato> {

	@Override
	public PrescrizioneStato mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new PrescrizioneStato();
		res.setPrescrizioneStatoId(rs.getObject("prescrizione_stato_id", Integer.class));
		res.setPrescrizioneStatoCod(rs.getString("prescrizione_stato_cod"));
		res.setPrescrizioneStatoDesc(rs.getString("prescrizione_stato_desc"));
		res.setPrescrizioneStatoFinale(rs.getBoolean("prescrizione_stato_finale"));
		generalMapRow(rs, res);
		return res;
	}

}
