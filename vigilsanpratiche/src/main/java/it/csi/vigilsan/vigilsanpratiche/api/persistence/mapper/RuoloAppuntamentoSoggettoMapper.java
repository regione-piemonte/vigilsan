/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelRuoloAppuntamentoSoggetto;
import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelRuoloEnteSoggetto;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.AppuntamentoSoggettoEsteso;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.RuoloAppuntamentoSoggetto;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class RuoloAppuntamentoSoggettoMapper extends GenericTableMapper implements RowMapper<RuoloAppuntamentoSoggetto> {

	@Override
	public RuoloAppuntamentoSoggetto mapRow(ResultSet rs, int rowNum) throws SQLException {

		RuoloAppuntamentoSoggetto res = new RuoloAppuntamentoSoggetto();
		
		res.setRuoloAppuntamentoSoggettoId(rs.getObject("ruolo_appuntamento_soggetto_id", Integer.class));
		res.setRuoloAppuntamentoSoggettoCod(rs.getString("ruolo_appuntamento_soggetto_cod"));
		res.setRuoloAppuntamentoSoggettoDesc(rs.getString("ruolo_appuntamento_soggetto_desc"));
		
		generalMapRow(rs, res);
		return res;
	}

}
