/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaDettaglioEstesaPraticaPerDiario;

public class PraticaDettaglioEstesaPraticaPerDiarioMapper extends PraticaDettaglioMapper<PraticaDettaglioEstesaPraticaPerDiario, PraticaDettaglioEstesaPraticaPerDiario> {

	public PraticaDettaglioEstesaPraticaPerDiarioMapper() {
		super(PraticaDettaglioEstesaPraticaPerDiario.class);
	}

	@Override
	public PraticaDettaglioEstesaPraticaPerDiario mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = super.mapRow(rs, rowNum);
		res.setAzioneDesc(rs.getString("azione_desc"));
		res.setModuloId(rs.getObject("modulo_id", Integer.class));
		res.setAppuntamentoNumero(rs.getString("appuntamento_numero"));
		res.setPrescrizioneNumero(rs.getString("prescrizione_numero"));
		return res;
	}

}
