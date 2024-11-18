/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanrilevazioni.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import it.csi.vigilsan.vigilsanrilevazioni.api.generated.dto.ModelModuloRidotto;
import it.csi.vigilsan.vigilsanrilevazioni.api.generated.dto.ModelVerificaDocumentazione;
import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.model.DocumentoEstesoList;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.model.GenericColumntInterface;
import it.csi.vigilsan.vigilsanutil.generic.util.DateUtils;

public class DocumentazioneEstesaListMapper extends DocumentazioneEstesaMapper<DocumentoEstesoList> {

	public DocumentazioneEstesaListMapper() {
		super(DocumentoEstesoList.class);
	}

	@Override
	public DocumentoEstesoList mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = super.mapRow(rs, rowNum);
		ModelVerificaDocumentazione verifica = new ModelVerificaDocumentazione();
		verifica.setVerificaDocumentazioneId(rs.getObject("verifica_documentazione_id", Integer.class));
		verifica.setDocumentazioneId(rs.getObject("documentazione_id", Integer.class));
		verifica.setNote(rs.getString("note_verifica"));
		verifica.setEsitoVerifica(rs.getBoolean("esito_verifica"));
		verifica.setDataoraVerifica(DateUtils.dateFromSqlTimestamp(rs.getTimestamp("dataora_verifica")));
		verifica.setNotificaId(rs.getObject("notifica_id", Integer.class));
		res.setVerificaDocumentazione(verifica);

		res.setTotalCount(rs.getObject("total_count", Long.class));
		return res;
	}

	@Override
	protected <T extends GenericColumntInterface> void generalMapRow(ResultSet rs, T res) throws SQLException {
		// non sono da compilare i valori generici
	}

}
