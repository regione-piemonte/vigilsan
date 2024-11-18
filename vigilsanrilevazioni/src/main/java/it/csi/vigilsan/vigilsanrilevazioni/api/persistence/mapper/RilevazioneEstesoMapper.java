/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanrilevazioni.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import it.csi.vigilsan.vigilsanrilevazioni.api.generated.dto.ModelModuloRidotto;
import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.model.RilevazioneEstesa;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.model.GenericColumntInterface;
import it.csi.vigilsan.vigilsanutil.generic.util.DateUtils;

public class RilevazioneEstesoMapper extends RilevazioneMapper<RilevazioneEstesa> {

	public RilevazioneEstesoMapper() {
		super(RilevazioneEstesa.class);
	}

	@Override
	public RilevazioneEstesa mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = super.mapRow(rs, rowNum);
		var modulo = new ModelModuloRidotto();
		GenericModuloEstesoMapper.setModuloId(rs, modulo);
		GenericModuloEstesoMapper.setParametriModuloCompilato(rs, modulo);
		res.setModulo(modulo);
		res.setTotalCount(rs.getObject("total_count", Long.class));
		return res;
	}

	@Override
	protected <T extends GenericColumntInterface> void generalMapRow(ResultSet rs, T res) throws SQLException {
		// non sono da compilare i valori generici
		res.setValiditaInizio(DateUtils.dateFromSqlTimestamp(rs.getTimestamp("validita_inizio")));
		res.setValiditaFine(DateUtils.dateFromSqlTimestamp(rs.getTimestamp("validita_fine")));
	}
}
