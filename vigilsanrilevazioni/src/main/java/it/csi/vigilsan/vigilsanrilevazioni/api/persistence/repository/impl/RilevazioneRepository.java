/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanrilevazioni.api.persistence.repository.impl;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.mapper.RilevazioneEstesoDaInserireMapper;
import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.mapper.RilevazioneEstesoMapper;
import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.model.RilevazioneEstesa;
import it.csi.vigilsan.vigilsanrilevazioni.util.query.SqlStatementRilevazione;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.generic.util.paginazione.Paginazione;
import it.csi.vigilsan.vigilsanutil.generic.util.paginazione.PaginazioneUtils;
import jakarta.validation.constraints.NotNull;

@Repository
public class RilevazioneRepository extends AbstractPersistence {

	public List<RilevazioneEstesa> getRilevazioneDaCompilareByStrutturaId(Integer strutturaId, @NotNull String moduloConfigCod) {

		var params = new MapSqlParameterSource();

		params.addValue("strutturaId", strutturaId);
		params.addValue("moduloConfigCod", moduloConfigCod);
		String sql = SqlStatementRilevazione.SELECT_RILEVAZIONE_DA_COMPILARE_ESTESO_MAPPER;
		return query(sql, params, new  RilevazioneEstesoDaInserireMapper());

	}

	public List<RilevazioneEstesa> getRilevazioneCompilataByStrutturaIdAndModuloCod(Integer strutturaId,
			String moduloConfigCod, Paginazione paginazione) {

		var params = new MapSqlParameterSource();

		params.addValue("strutturaId", strutturaId);
		params.addValue("moduloConfigCod", moduloConfigCod);
		PaginazioneUtils.setPaginazioneParam(paginazione, params);

		String sql = SqlStatementRilevazione.SELECT_RILEVAZIONE_COMPILATE_ESTESO_MAPPER
				+ PaginazioneUtils.generateSqlPaginazione(paginazione);
		return query(sql, params, new RilevazioneEstesoMapper());
	}
}
