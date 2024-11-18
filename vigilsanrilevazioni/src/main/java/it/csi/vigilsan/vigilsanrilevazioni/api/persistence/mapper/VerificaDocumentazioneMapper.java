/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanrilevazioni.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.model.VerificaDocumentazione;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;
import it.csi.vigilsan.vigilsanutil.generic.util.DateUtils;

public class VerificaDocumentazioneMapper extends GenericTableMapper implements RowMapper<VerificaDocumentazione> {

	@Override
	public VerificaDocumentazione mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new VerificaDocumentazione();
		res.setVerificaDocumentazioneId(rs.getObject("verifica_documentazione_id", Integer.class));
		res.setDocumentazioneId(rs.getObject("documentazione_id", Integer.class));
		res.setNote(rs.getString("note"));
		res.setEsitoVerifica(rs.getBoolean("esito_verifica"));
		res.setDataoraVerifica(DateUtils.dateFromSqlTimestamp(rs.getTimestamp("dataora_verifica")));
		generalMapRow(rs, res);
		return res;
	}

}
