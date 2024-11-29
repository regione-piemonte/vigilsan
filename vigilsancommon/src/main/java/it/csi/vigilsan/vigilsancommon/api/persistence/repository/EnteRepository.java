/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.EnteMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.Ente;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementEnte;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class EnteRepository extends AbstractPersistence {

	public Ente getByEnteCodConfiguratore(String enteCodConfiguratore) {

		var params = new MapSqlParameterSource();

		params.addValue("enteCodConfiguratore", enteCodConfiguratore);
		String sql = SqlStatementEnte.SELECT_ENTE + SqlStatementCommon.WHERE
				+ SqlStatementEnte.ENTE_COD_CONFIGURATORE_EQUALS + SqlStatementCommon.AND
				+ SqlStatementEnte.ENTE_VALIDO;
		return queryForObject(sql, params, new EnteMapper());

	}
}
