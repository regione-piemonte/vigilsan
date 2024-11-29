/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.ApplicativoMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.Applicativo;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementApplicativo;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class ApplicativoDao extends AbstractPersistence implements Dao<Applicativo> {

	@Override
	public Applicativo get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue("applicativoId", id);
		String sql = SqlStatementApplicativo.SELECT + SqlStatementCommon.WHERE + SqlStatementApplicativo.PK_ID_EQUALS;
		return queryForObject(sql, params, new ApplicativoMapper());
	}

	@Override
	public List<Applicativo> getAll() {
		// Do nothing
		return null;
	}

	@Override
	public void insert(Applicativo t) {
		// Do nothing
	}

	@Override
	public void update(Applicativo t, int id) {
		// Do nothing
	}

	@Override
	public void delete(Applicativo t) {
		// Do nothing
	}
}
