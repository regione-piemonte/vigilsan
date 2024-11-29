/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.RuoloMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.Ruolo;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementRuolo;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class RuoloRepository extends AbstractPersistence {

	public Ruolo getByRuoloCod(String ruoloCod) {

		var params = new MapSqlParameterSource();

		params.addValue("ruoloCod", ruoloCod);
		String sql = SqlStatementRuolo.SELECT + SqlStatementCommon.WHERE
				+ SqlStatementRuolo.RUOLO_COD_EUQALS + SqlStatementCommon.AND
				+ SqlStatementRuolo.VALIDO;
		return queryForObject(sql, params, new RuoloMapper());

	}
}
