/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.StrutturaMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.Struttura;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementStruttura;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class StrutturaRepository extends AbstractPersistence {

	public Struttura getByStrutturaCodConfiguratore(String strutturaCodArpe) {

		var params = new MapSqlParameterSource();

		params.addValue("strutturaCodArpe", strutturaCodArpe);
		String sql = SqlStatementStruttura.SELECT_STRUTTURA + SqlStatementCommon.WHERE
				+ SqlStatementStruttura.STRUTTURA_COD_ARPE_EQUALS + SqlStatementCommon.AND
				+ SqlStatementStruttura.STRUTTURA_VALIDO;
		return queryForObject(sql, params, new StrutturaMapper<>(Struttura.class));

	}
}
