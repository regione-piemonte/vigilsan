/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelAppuntamentoEsteso;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.AppuntamentoEsteso;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.AppuntamentoEstesoPerDiario;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.AppuntamentoStato;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PrescrizioneEstesa;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PrescrizioneStato;

public class AppuntamentoEstesoPerDiarioMapper implements RowMapper<AppuntamentoEstesoPerDiario> {


	private AppuntamentoMapper<AppuntamentoEstesoPerDiario> appuntamentoMapper;
	
	public AppuntamentoEstesoPerDiarioMapper() {
		super();
		this.appuntamentoMapper = new AppuntamentoMapper<>(AppuntamentoEstesoPerDiario.class);
	}

	@Override
	public AppuntamentoEstesoPerDiario mapRow(ResultSet rs, int rowNum) throws SQLException {

		AppuntamentoEstesoPerDiario res = appuntamentoMapper.mapRow(rs, rowNum);

		var stato = new AppuntamentoStato();
		res.setAppuntamentoStato(stato);
		stato.setAppuntamentoStatoId(rs.getObject("appuntamento_stato_id", Integer.class));
		stato.setAppuntamentoStatoCod(rs.getString("appuntamento_stato_cod"));
		stato.setAppuntamentoStatoDesc(rs.getString("appuntamento_stato_desc"));
		stato.setAppuntamentoStatoFinale(rs.getBoolean("appuntamento_stato_finale"));
		stato.setAppuntamentoStatoIniziale(rs.getBoolean("appuntamento_stato_iniziale"));
		res.setAzioneDesc(rs.getString("azione_desc"));
		res.setModuloId(rs.getObject("modulo_id", Integer.class));
		res.setModuloCompilatoId(rs.getObject("modulo_compilato_id", Integer.class));
		return res;
	}

}
