/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.EnteStrutturaMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.EnteStruttura;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementEnteStruttura;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class EnteStrutturaDao extends AbstractPersistence implements Dao<EnteStruttura> {

	@Override
	public EnteStruttura get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementEnteStruttura.PH_PK, id);
		String sql = SqlStatementEnteStruttura.SELECT_ENTE_STRUTTURA + SqlStatementCommon.WHERE + SqlStatementEnteStruttura.ENTE_STRUTTURA_ID_EQUALS;
		return queryForObject(sql, params, new EnteStrutturaMapper());
	}
  
	public EnteStruttura getEnteStrutturaByStrutturaId(EnteStruttura inEnteStruttura) {

		var params = new MapSqlParameterSource();

		params.addValue("struttura_id", inEnteStruttura.getStrutturaId());
		params.addValue("ruolo_ente_struttura_id", inEnteStruttura.getRuoloEnteStrutturaId());
		String sql = SqlStatementEnteStruttura.SELECT_ENTE_STRUTTURA_BY_STRUTTURA_ID;
		return queryForObject(sql, params, new EnteStrutturaMapper());
	}
	@Override
	public List<EnteStruttura> getAll() {
		// Do nothing
		return null;
	}

	@Override
	public void insert(EnteStruttura t) {
		var params = new MapSqlParameterSource();
		
		params.addValue("enteId", t.getEnteId());
		params.addValue("strutturaId", t.getStrutturaId());
		params.addValue("ruoloentestrutturaId", t.getRuoloEnteStrutturaId());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql = SqlStatementEnteStruttura.INSERT_STRUTTURA;
		update(sql, params);	
	}	
	
	
	@Override
	public void update(EnteStruttura t, int id) {
		var params = new MapSqlParameterSource();
		
		params.addValue("enteId", t.getEnteId());
		params.addValue("strutturaId", t.getStrutturaId());
		params.addValue(SqlStatementCommon.PH_DATA_MODIFICA, t.getDataModifica());			
		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementEnteStruttura.PH_PK, id);
		
		String sql = SqlStatementEnteStruttura.UPDATE_ENTE_STRUTTURA_BATCH;
		update(sql, params);			
	}

	public void delete(EnteStruttura t) {
	// TO IMPLEMENT
	}
		
	public void deleteEnteStruttura(Date inDataInizioElaborazione, EnteStruttura inEnteStruttura) {
		var params = new MapSqlParameterSource();
		params.addValue("datainput", inDataInizioElaborazione);
		params.addValue("strutturaId", inEnteStruttura.getStrutturaId());
		params.addValue("ruoloentestrutturaid", inEnteStruttura.getRuoloEnteStrutturaId());
		params.addValue("utentecancellazione", inEnteStruttura.getUtenteCreazione());
	
		String sql = SqlStatementEnteStruttura.DELETE_ENTE_STRUTTURA;
		update(sql, params);			
	}
	
	
	public void refreshEnteStruttura(Date inDataInizioElaborazione, EnteStruttura inEnteStruttura) {
		var params = new MapSqlParameterSource();
		params.addValue("datainput", inDataInizioElaborazione);
		params.addValue("strutturaId", inEnteStruttura.getStrutturaId());
		params.addValue("ruoloentestrutturaId", inEnteStruttura.getRuoloEnteStrutturaId());
		params.addValue("utentemodifica", inEnteStruttura.getUtenteCreazione());
		
		String sql = SqlStatementEnteStruttura.REFRESH_ENTE_STRUTTURA;
		update(sql, params);			
	}
	
	
	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementEnteStruttura.NEXTVAL_PK_ID;
		return queryForObject(sql, params,Integer.class);
	}
}
