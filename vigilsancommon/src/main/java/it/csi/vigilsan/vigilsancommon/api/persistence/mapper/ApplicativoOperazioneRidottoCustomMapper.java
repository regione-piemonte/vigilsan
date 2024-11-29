/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsancommon.api.persistence.model.ApplicativoOperazioneRidottoCustom;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class ApplicativoOperazioneRidottoCustomMapper implements RowMapper<ApplicativoOperazioneRidottoCustom> {

	@Override
	public ApplicativoOperazioneRidottoCustom mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new ApplicativoOperazioneRidottoCustom();
		res.setFunzione(rs.getString("operazione_cod"));
		res.setPermesso(rs.getString("abilitazione"));
		return res;
	}

}
