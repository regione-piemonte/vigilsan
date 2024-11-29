/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.ApplicativoMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.Applicativo;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementApplicativo;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class ApplicativoRepository extends AbstractPersistence {

	public Applicativo getByApplicativoCod(String applicativoCod) {

		var params = new MapSqlParameterSource();

		params.addValue("applicativoCod", applicativoCod);
		String sql = SqlStatementApplicativo.SELECT + SqlStatementCommon.WHERE + SqlStatementApplicativo.COD_EQUALS
				+ SqlStatementCommon.AND + SqlStatementApplicativo.VALIDO;
		return queryForObject(sql, params, new ApplicativoMapper());

	}
}
