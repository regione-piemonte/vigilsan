/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.StrutturaCategoriaMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.StrutturaCategoria;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementNatura;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementStruttura;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementStrutturaCategoria;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class StrutturaCategoriaDao extends AbstractPersistence implements Dao<StrutturaCategoria> {


	private Integer getId(StrutturaCategoria t) {
		return t.getStrutturaCategoriaId();
	}

	private void setSpecificParameter(StrutturaCategoria t, MapSqlParameterSource params) {
		params.addValue("strutturaCategoriaCod", t.getStrutturaCategoriaCod());
		params.addValue("strutturaCategoriaDesc", t.getStrutturaCategoriaDesc());
	}

	
	@Override
	public StrutturaCategoria get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementStrutturaCategoria.PH_PK, id);
		String sql = SqlStatementStrutturaCategoria.SELECT + SqlStatementCommon.WHERE
				+ SqlStatementStrutturaCategoria.PK_EQUALS;
		return queryForObject(sql, params, new StrutturaCategoriaMapper());
	}

	public List<StrutturaCategoria> getByStrutturaId(Integer strutturaId) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementStruttura.PH_PK, strutturaId);
		String sql = SqlStatementStrutturaCategoria.SELECT_BY_STRUTTRA_ID;
		return query(sql, params, new StrutturaCategoriaMapper());
	}
	
	public StrutturaCategoria getByStrCatId(Integer strCatId) {
		var params = new MapSqlParameterSource();
		params.addValue("strCatId", strCatId);
		String sql = SqlStatementStrutturaCategoria.SELECT_BY_STR_CAT_ID;
		return queryForObject(sql, params, new StrutturaCategoriaMapper());
	}
	

	@Override
	public List<StrutturaCategoria> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementStrutturaCategoria.SELECT;
		return query(sql, params, new StrutturaCategoriaMapper());
	}
	
	@Override
	public void insert(StrutturaCategoria t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementStrutturaCategoria.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if ( Objects.isNull(getId(t))) {
			sql = SqlStatementStrutturaCategoria.INSERT;
		} else {
			params.addValue(SqlStatementNatura.PH_PK, getId(t));
			sql = SqlStatementStrutturaCategoria.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementStrutturaCategoria.NEXTVAL_PK_ID;
		return queryForObject(sql, params,Integer.class);
	}

	@Override
	public void update(StrutturaCategoria t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementStrutturaCategoria.PH_PK, id);
		setSpecificParameter(t, params);
		
		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementStrutturaCategoria.UPDATE + SqlStatementCommon.SET
				+ SqlStatementStrutturaCategoria.UPDATE_CAMPI + SqlStatementCommon.WHERE
				+ SqlStatementStrutturaCategoria.PK_EQUALS;
		update(sql, params);
	}
	
	@Override
	public void delete(StrutturaCategoria t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementStrutturaCategoria.PH_PK, getId(t));
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementStrutturaCategoria.UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE
				+ SqlStatementStrutturaCategoria.PK_EQUALS;
		update(sql, params);

	}
}
