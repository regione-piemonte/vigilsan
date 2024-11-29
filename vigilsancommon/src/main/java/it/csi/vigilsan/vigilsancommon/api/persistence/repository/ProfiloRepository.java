/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.ProfiloMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.Profilo;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementProfilo;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class ProfiloRepository extends AbstractPersistence {

	public Profilo getProfiloByProfiloCod(String profiloCod) {

		var params = new MapSqlParameterSource();

		params.addValue("profiloCod", profiloCod);
		String sql = SqlStatementProfilo.SELECT + SqlStatementCommon.WHERE
				+ SqlStatementProfilo.PROFILO_COD_EQUALS + SqlStatementCommon.AND
				+ SqlStatementProfilo.VALIDO;
		return queryForObject(sql, params, new ProfiloMapper());

	}
}
