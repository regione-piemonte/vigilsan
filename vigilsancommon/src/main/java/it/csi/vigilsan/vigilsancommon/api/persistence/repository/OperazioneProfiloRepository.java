/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.repository;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.ApplicativoOperazioneRidottoCustomMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.ApplicativoOperazioneRidottoCustom;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementOperazioneProfilo;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;

@Repository
public class OperazioneProfiloRepository extends AbstractPersistence {

	public List<ApplicativoOperazioneRidottoCustom> selectApplicativoOperazioneByApplicativoIdAndProfiloId(Integer applicativoId, Integer profiloId, Integer strutturaId) {

		var params = new MapSqlParameterSource();

		params.addValue("applicativoId", applicativoId);
		params.addValue("strutturaId", strutturaId);
		params.addValue("profiloId", profiloId);
		String sql = SqlStatementOperazioneProfilo.SELECT_APPLICATIVO_OPERAZIONE_BY_APPLICATIVO_ID_PROFILO_ID;
		return query(sql, params, new ApplicativoOperazioneRidottoCustomMapper());

	}

	public List<ApplicativoOperazioneRidottoCustom> selectApplicativoOperazioneRidottoByApplicativoIdAndProfiloId(
			Integer applicativoId, Integer profiloId, Integer strutturaTipoId) {

		var params = new MapSqlParameterSource();

		params.addValue("applicativoId", applicativoId);
		params.addValue("strutturaTipoId", strutturaTipoId);
		params.addValue("profiloId", profiloId);
		String sql = SqlStatementOperazioneProfilo.SELECT_APPLICATIVO_OPERAZIONE_RIDOTTO_BY_APPLICATIVO_ID_PROFILO_ID;
		return query(sql, params, new ApplicativoOperazioneRidottoCustomMapper());

	}
}
