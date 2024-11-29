/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.RuoloEnteStrutturaMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.RuoloMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.Ruolo;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.RuoloEnteStruttura;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementRuolo;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementRuoloEnteStruttura;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class RuoloEnteStrutturaDao extends AbstractPersistence implements Dao<RuoloEnteStruttura> {

	@Override
	public RuoloEnteStruttura get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementRuolo.PH_PK, id);
		String sql = SqlStatementRuolo.SELECT + SqlStatementCommon.WHERE
				+ SqlStatementRuolo.PK_EQUALS;
		return queryForObject(sql, params, new RuoloEnteStrutturaMapper());
	}
	
	@Override
	public List<RuoloEnteStruttura> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementRuoloEnteStruttura.SELECT;
		return query(sql, params, new RuoloEnteStrutturaMapper());
	}

	@Override
	public void insert(RuoloEnteStruttura t) {
		var params = new MapSqlParameterSource();
		
		params.addValue("ruoloCod", t.getRuoloEnteStrutturaCod());
		params.addValue("ruoloDesc", t.getRuoloEnteStrutturaDesc());
		params.addValue("validitaFine", t.getValiditaFine());
		params.addValue("validitaInizio", t.getValiditaInizio());
		params.addValue("utenteCreazione", t.getUtenteCreazione());
		
		String sql = SqlStatementRuoloEnteStruttura.INSERT;
		update(sql, params);

	}
	
	@Override
	public void update(RuoloEnteStruttura t,int id) {
		var params = new MapSqlParameterSource();
		
		params.addValue(SqlStatementRuolo.PH_PK, id);
		params.addValue("ruoloDesc", t.getRuoloEnteStrutturaDesc());
		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue("validitaFine", t.getValiditaFine());
		params.addValue("validitaInizio", t.getValiditaInizio());
		
		String sql = SqlStatementRuoloEnteStruttura.UPDATE + SqlStatementCommon.SET
				+ SqlStatementRuoloEnteStruttura.UPDATE_CAMPI + SqlStatementCommon.WHERE
				+ SqlStatementRuoloEnteStruttura.PK_EQUALS;
		update(sql, params);

	}
	
	public RuoloEnteStruttura getCod(String inEnteStrutturaCod) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementRuoloEnteStruttura.PH_COD, inEnteStrutturaCod);
		String sql = SqlStatementRuoloEnteStruttura.SELECT + SqlStatementCommon.WHERE
				+ SqlStatementRuoloEnteStruttura.RUOLO_ENTE_STRUTTURA_COD_EQUALS;
		return queryForObject(sql, params, new RuoloEnteStrutturaMapper());
	}
	
	@Override
	public void delete(RuoloEnteStruttura t) {


	}
	
}
