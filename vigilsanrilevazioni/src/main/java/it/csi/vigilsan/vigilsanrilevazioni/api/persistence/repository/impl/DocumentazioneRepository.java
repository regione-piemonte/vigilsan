/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanrilevazioni.api.persistence.repository.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.mapper.DocumentazioneEstesaDaInserireMapper;
import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.mapper.DocumentazioneEstesaListMapper;
import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.mapper.DocumentazioneEstesaMapper;
import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.model.DocumentoEsteso;
import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.model.DocumentoEstesoList;
import it.csi.vigilsan.vigilsanrilevazioni.util.query.SqlStatementDocumentazione;
import it.csi.vigilsan.vigilsanrilevazioni.util.query.SqlStatementDocumentoRilevazione;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;
import it.csi.vigilsan.vigilsanutil.generic.util.paginazione.Paginazione;
import it.csi.vigilsan.vigilsanutil.generic.util.paginazione.PaginazioneUtils;
import jakarta.validation.constraints.NotNull;

@Repository
public class DocumentazioneRepository extends AbstractPersistence {

	public List<DocumentoEsteso> getDocumentazioneByStrutturaId(Integer strutturaId) {

		var params = new MapSqlParameterSource();

		params.addValue("strutturaId", strutturaId);
		String sql = SqlStatementDocumentoRilevazione.SELECT_DOCUMENTO_ESTESO_MAPPER;
		return query(sql, params, new DocumentazioneEstesaMapper<>(DocumentoEsteso.class));

	}

	public List<DocumentoEsteso> getDocumentazioneDaCompilareByStrutturaId(Integer strutturaId,
			String moduloConfigCod) {

		var params = new MapSqlParameterSource();

		params.addValue("strutturaId", strutturaId);
		String sql = SqlStatementDocumentazione.SELECT_DOCUMENTAZIONE_DA_COMPILARE_ESTESO_MAPPER;
		if (moduloConfigCod != null) {
			params.addValue("moduloConfigCod", moduloConfigCod);
			sql = sql + SqlStatementCommon.WHERE
					+ SqlStatementDocumentazione.SELECT_DOCUMENTAZIONE_DA_COMPILARE_ESTESO_MAPPER_FILTER_MODULO_CONFIG_COD;

		}
		return query(sql, params, new DocumentazioneEstesaDaInserireMapper());

	}

	public List<DocumentoEstesoList> getDocumentazioneCompilataByStrutturaIdAndModuloCod(Integer strutturaId,
			String moduloConfigCod, Paginazione paginazione) {

		var params = new MapSqlParameterSource();

		params.addValue("strutturaId", strutturaId);
		String sql = SqlStatementDocumentazione.SELECT_DOCUMENTAZIONE_COMPILATE_ESTESO_MAPPER;
		if (Objects.nonNull(moduloConfigCod)) {
			params.addValue("moduloConfigCod", moduloConfigCod);
			sql = String.format(sql, SqlStatementDocumentazione.SELECT_DOCUMENTAZIONE_COMPILATE_ESTESO_MAPPER_FILTER_MODULO_CONFIG_COD);
		} else {
			sql = String.format(sql, "");
		}

		PaginazioneUtils.setPaginazioneParam(paginazione, params);
		sql = sql + PaginazioneUtils.generateSqlPaginazione(paginazione);
		return query(sql, params, new DocumentazioneEstesaListMapper());

	}
}
