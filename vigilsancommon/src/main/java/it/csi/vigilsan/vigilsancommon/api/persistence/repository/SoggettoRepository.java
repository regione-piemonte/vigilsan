/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.SoggettoMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.Soggetto;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementSoggetto;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class SoggettoRepository extends AbstractPersistence  {

	public Soggetto getByCodiceFiscale(String codiceFiscale) {

		var params = new MapSqlParameterSource();

		params.addValue("codiceFiscale", codiceFiscale);
		String sql = SqlStatementSoggetto.SELECT + SqlStatementCommon.WHERE
				+ SqlStatementSoggetto.CF_EQUALS + SqlStatementCommon.AND
				+ SqlStatementSoggetto.VALIDO;
		return queryForObject(sql, params, new SoggettoMapper());
		
	}
}
