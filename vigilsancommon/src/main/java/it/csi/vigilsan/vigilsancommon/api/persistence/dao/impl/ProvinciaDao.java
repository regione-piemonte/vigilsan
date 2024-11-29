/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.ProvinciaMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.Provincia;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.ProvinciaInterface;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementNatura;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementProvincia;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class ProvinciaDao extends AbstractPersistence implements Dao<Provincia> {

	@Override
	public Provincia get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementProvincia.PH_PK, id);
		String sql = SqlStatementProvincia.SELECT + SqlStatementCommon.WHERE
				+ SqlStatementProvincia.PK_EQUALS;
		return queryForObject(sql, params, new ProvinciaMapper<>(Provincia.class));
	}
	
	public <T extends ProvinciaInterface> T get(int id, Class<T> clazz) {

		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementProvincia.PH_PK, id);
		String sql = SqlStatementProvincia.SELECT + SqlStatementCommon.WHERE
				+ SqlStatementProvincia.PK_EQUALS;
		return queryForObject(sql, params, new ProvinciaMapper<>(clazz));
	}

	@Override
	public List<Provincia> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementProvincia.SELECT;
		return query(sql, params, new ProvinciaMapper<>(Provincia.class));
	}

	@Override
	public void insert(Provincia t) {
		var params = new MapSqlParameterSource();

		params.addValue("provinciaCod", t.getProvinciaCod());
		params.addValue("provinciaSigla", t.getProvinciaSigla());
		params.addValue("provinciaDesc", t.getProvinciaDesc());
		params.addValue("regioneId", t.getRegioneId());
		
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (t.getProvinciaId() == null) {
			sql = SqlStatementProvincia.INSERT;
		} else {
			params.addValue(SqlStatementNatura.PH_PK, t.getProvinciaId());
			sql = SqlStatementProvincia.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementProvincia.NEXTVAL_PK_ID;
		return queryForObject(sql, params,Integer.class);
	}

	@Override
	public void update(Provincia t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementProvincia.PH_PK, id);

		params.addValue("provinciaCod", t.getProvinciaCod());
		params.addValue("provinciaSigla", t.getProvinciaSigla());
		params.addValue("provinciaDesc", t.getProvinciaDesc());
		params.addValue("regioneId", t.getRegioneId());
		
		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementProvincia.UPDATE + SqlStatementCommon.SET
				+ SqlStatementProvincia.UPDATE_CAMPI + SqlStatementCommon.WHERE
				+ SqlStatementProvincia.PK_EQUALS;
		update(sql, params);
	}

	@Override
	public void delete(Provincia t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementProvincia.PH_PK, t.getProvinciaId());
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementProvincia.UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE
				+ SqlStatementProvincia.PK_EQUALS;
		update(sql, params);

	}
}
