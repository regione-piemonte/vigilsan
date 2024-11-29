/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.OspiteCondzioniMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.OspiteCondizioni;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementOspiteCondizioni;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class OspiteCondizioniDao extends AbstractPersistence implements Dao<OspiteCondizioni> {

	private Integer getId(OspiteCondizioni t) {
		return t.getOspiteCondizioniId();
	}

	private void setSpecificParameter(OspiteCondizioni t, MapSqlParameterSource params) {
		params.addValue("ospiteCondizioniId", t.getOspiteCondizioniId());
		params.addValue("ospiteCondizioniCod", t.getOspiteCondizioniCod());
		params.addValue("ospiteCondizioniDesc", t.getOspiteCondizioniDesc());
		params.addValue("ospiteCondizioniHint", t.getOspiteCondizioniHint());
		params.addValue("ospiteCondizioniOrd", t.getOspiteCondizioniOrd());
	}

	@Override
	public OspiteCondizioni get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementOspiteCondizioni.PH_PK, id);
		String sql = SqlStatementOspiteCondizioni.SELECT_BY_ID;
		return queryForObject(sql, params, new OspiteCondzioniMapper());
	}

	@Override
	public List<OspiteCondizioni> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementOspiteCondizioni.SELECT_ALL;
		return query(sql, params, new OspiteCondzioniMapper());
	}

	@Override
	public void insert(OspiteCondizioni t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementOspiteCondizioni.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (Objects.isNull(getId(t))) {
			sql = SqlStatementOspiteCondizioni.INSERT_GENERIC;
		} else {
			params.addValue(SqlStatementOspiteCondizioni.PH_PK, getId(t));
			sql = SqlStatementOspiteCondizioni.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementOspiteCondizioni.NEXTVAL_PK_ID;
		return queryForObject(sql, params, Integer.class);
	}

	@Override
	public void update(OspiteCondizioni t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementOspiteCondizioni.PH_PK, id);
		setSpecificParameter(t, params);

		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementOspiteCondizioni.UPDATE_GENERIC;
		update(sql, params);
	}

	@Override
	public void delete(OspiteCondizioni t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementOspiteCondizioni.PH_PK, getId(t));
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementOspiteCondizioni.DELETE;
		update(sql, params);
	}

}
