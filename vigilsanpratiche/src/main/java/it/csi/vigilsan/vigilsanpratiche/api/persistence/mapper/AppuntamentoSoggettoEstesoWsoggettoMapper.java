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
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.AppuntamentoSoggettoEstesoWsoggetto;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class AppuntamentoSoggettoEstesoWsoggettoMapper extends GenericTableMapper implements RowMapper<AppuntamentoSoggettoEstesoWsoggetto> {

	@Override
	public AppuntamentoSoggettoEstesoWsoggetto mapRow(ResultSet rs, int rowNum) throws SQLException {

		AppuntamentoSoggettoEstesoWsoggetto res = new AppuntamentoSoggettoEstesoWsoggetto();
		
		res.setAppuntamentoSoggettoId(rs.getObject("appuntamento_soggetto_id", Integer.class));
		res.setAppuntamentoId(rs.getObject("appuntamento_id", Integer.class));
		res.setSoggettoId(rs.getObject("soggetto_id", Integer.class));
		res.setRuoloAppuntamentoSoggettoId(rs.getObject("ruolo_appuntamento_soggetto_id", Integer.class));
		
		ModelRuoloAppuntamentoSoggetto ruoloApp = new ModelRuoloAppuntamentoSoggetto();
		ruoloApp.setRuoloAppuntamentoSoggettoId(rs.getObject("ruolo_appuntamento_soggetto_id", Integer.class));
		ruoloApp.setRuoloAppuntamentoSoggettoCod(rs.getString("ruolo_appuntamento_soggetto_cod"));
		ruoloApp.setRuoloAppuntamentoSoggettoDesc(rs.getString("ruolo_appuntamento_soggetto_desc"));
		res.setRuoloAppuntamentoSoggetto(ruoloApp);
		
		ModelRuoloEnteSoggetto ruoloEnte = new ModelRuoloEnteSoggetto();
		ruoloEnte.setRuoloEnteSoggettoId(rs.getObject("ruolo_ente_soggetto_id", Integer.class));
		ruoloEnte.setRuoloEnteSoggettoCod(rs.getString("ruolo_ente_soggetto_cod"));
		ruoloEnte.setRuoloEnteSoggettoDesc(rs.getString("ruolo_ente_soggetto_desc"));
		res.setRuoloEnteSoggetto(ruoloEnte);
		generalMapRow(rs, res);
		
		res.setNome(rs.getString("nome"));
		res.setCognome(rs.getString("cognome"));
		return res;
	}

}
