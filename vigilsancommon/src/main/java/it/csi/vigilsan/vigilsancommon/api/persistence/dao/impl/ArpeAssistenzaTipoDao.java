/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.ArpeAssistenzaTipoMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.ArpeAssistenzaTipo;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementArpeAssistenzaTipo;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class ArpeAssistenzaTipoDao extends AbstractPersistence implements Dao<ArpeAssistenzaTipo> {


	private Integer getId(ArpeAssistenzaTipo t) {
		return t.getArpeAssistenzaTipoId();
	}

	private void setSpecificParameter(ArpeAssistenzaTipo t, MapSqlParameterSource params) {
		params.addValue("arpeAssistenzaTipoCod", t.getArpeAssistenzaTipoCod());
		params.addValue("arpeAssistenzaTipoDesc", t.getArpeAssistenzaTipoDesc());
	}

	
	@Override
	public ArpeAssistenzaTipo get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementArpeAssistenzaTipo.PH_PK, id);
		String sql = SqlStatementArpeAssistenzaTipo.SELECT + SqlStatementCommon.WHERE
				+ SqlStatementArpeAssistenzaTipo.PK_EQUALS;
		return queryForObject(sql, params, new ArpeAssistenzaTipoMapper());
	}

	@Override
	public List<ArpeAssistenzaTipo> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementArpeAssistenzaTipo.SELECT;
		return query(sql, params, new ArpeAssistenzaTipoMapper());
	}
	
	@Override
	public void insert(ArpeAssistenzaTipo t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementArpeAssistenzaTipo.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if ( Objects.isNull(getId(t))) {
			sql = SqlStatementArpeAssistenzaTipo.INSERT;
		} else {
			params.addValue(SqlStatementArpeAssistenzaTipo.PH_PK, getId(t));
			sql = SqlStatementArpeAssistenzaTipo.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementArpeAssistenzaTipo.NEXTVAL_PK_ID;
		return queryForObject(sql, params,Integer.class);
	}

	@Override
	public void update(ArpeAssistenzaTipo t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementArpeAssistenzaTipo.PH_PK, id);
		setSpecificParameter(t, params);
		
		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementArpeAssistenzaTipo.UPDATE + SqlStatementCommon.SET
				+ SqlStatementArpeAssistenzaTipo.UPDATE_CAMPI + SqlStatementCommon.WHERE
				+ SqlStatementArpeAssistenzaTipo.PK_EQUALS;
		update(sql, params);
	}
	
	@Override
	public void delete(ArpeAssistenzaTipo t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementArpeAssistenzaTipo.PH_PK, getId(t));
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementArpeAssistenzaTipo.UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE
				+ SqlStatementArpeAssistenzaTipo.PK_EQUALS;
		update(sql, params);
	}
	
	
	// vigil_d_arpe_assistenza_tipo
	public ArpeAssistenzaTipo getArpeAssistenzaTipoByCod(String inCodArpe) {
		var params = new MapSqlParameterSource();

		params.addValue("cod_arpe", inCodArpe);
		String sql = SqlStatementArpeAssistenzaTipo.SELECT_ASSISTENZA_TIPO_BY_ARPE_ASSISTENZA_TIPO_COD;
		return queryForObject(sql, params, new ArpeAssistenzaTipoMapper());

	}
}
