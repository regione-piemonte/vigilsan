/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.StrutturaMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.StrutturaPaginataMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.Struttura;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.StrutturaPaginata;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementStruttura;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;
import it.csi.vigilsan.vigilsanutil.generic.util.DateUtils;
import it.csi.vigilsan.vigilsanutil.generic.util.paginazione.Paginazione;
import it.csi.vigilsan.vigilsanutil.generic.util.paginazione.PaginazioneUtils;

@Repository
public class StrutturaDao extends AbstractPersistence implements Dao<Struttura> {

	@Override
	public Struttura get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementStruttura.PH_PK, id);
		String sql = SqlStatementStruttura.SELECT_STRUTTURA + SqlStatementCommon.WHERE + SqlStatementStruttura.STRUTTURA_ID_EQUALS;
		return queryForObject(sql, params, new StrutturaMapper<>(Struttura.class));
	}
  
	public Struttura getStrutturaByCodArpe(String inCodArpe) {

		var params = new MapSqlParameterSource();

		params.addValue("cod_arpe", inCodArpe);
		String sql = SqlStatementStruttura.SELECT_STRUTTURA_BY_STRUTTURA_COD_ARPE;
		return queryForObject(sql, params, new StrutturaMapper<>(Struttura.class));
	}

	public Struttura getStrutturaByCodArpeValida(String inCodArpe) {

		var params = new MapSqlParameterSource();

		params.addValue("cod_arpe", inCodArpe);
		String sql = SqlStatementStruttura.SELECT_STRUTTURA_BY_STRUTTURA_COD_ARPE_VALIDA;
		return queryForObject(sql, params, new StrutturaMapper<>(Struttura.class));
	}
	
	@Override
	public List<Struttura> getAll() {
		// Do nothing
		return null;
	}

	@Override
	public void insert(Struttura t) {
		var params = new MapSqlParameterSource();
		
//		Integer sequenceStruttura = getSequence();
//		params.addValue(SqlStatementStruttura.PH_PK, sequenceStruttura);
		params.addValue("strutturaCod", t.getStrutturaCod());
		params.addValue("strutturaCodConfiguratore", t.getStrutturaCodConfiguratore());
		params.addValue("strutturaCodArpe", t.getStrutturaCodArpe());
		params.addValue("strutturaDesc", t.getStrutturaDesc());
		params.addValue("strutturaTipoId", t.getStrutturaTipoId());
		params.addValue("comuneId", t.getComuneId());
		params.addValue("indirizzo", t.getIndirizzo());
		params.addValue("cap", t.getCap()); //Cap ???
		params.addValue("coordSrid", t.getCoordSrid());
		params.addValue("coordX", t.getCoordX());
		params.addValue("coordY", t.getCoordY());
		params.addValue("strutturaNaturaId", t.getStrutturaNaturaId());
		params.addValue("strutturaAccreditamentoId", t.getStrutturaAccreditamentoId());
		params.addValue("strutturaTitolaritaId", t.getStrutturaTitolaritaId());
		params.addValue(SqlStatementCommon.PH_DATA_CREAZIONE, t.getDataCreazione());
		params.addValue(SqlStatementCommon.PH_DATA_MODIFICA, t.getDataModifica());		
		params.addValue(SqlStatementCommon.PH_DATA_CANECLLAZIONE, t.getDataCancellazione());			
		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());		
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCreazione());			
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql = SqlStatementStruttura.INSERT_STRUTTURA;
		update(sql, params);	
	}	
	
	@Override
	public void update(Struttura t, int id) {
		var params = new MapSqlParameterSource();
		
		params.addValue("cap", t.getCap()); 
		params.addValue("coordSrid", t.getCoordSrid());
		params.addValue("coordX", t.getCoordX());
		params.addValue("coordY", t.getCoordY());		
		params.addValue("indirizzo", t.getIndirizzo());		
		params.addValue("strutturaDesc", t.getStrutturaDesc());	
		params.addValue("strutturaCod", t.getStrutturaCod());	
		params.addValue("strutturaCodConfiguratore", t.getStrutturaCodConfiguratore());					
		params.addValue("strutturaTipoId", t.getStrutturaTipoId());	
		params.addValue("comuneId", t.getComuneId());	
		params.addValue("strutturaNaturaId", t.getStrutturaNaturaId());
		params.addValue("strutturaAccreditamentoId", t.getStrutturaAccreditamentoId());
		params.addValue("strutturaTitolaritaId", t.getStrutturaTitolaritaId());
		params.addValue(SqlStatementCommon.PH_DATA_MODIFICA, t.getDataModifica());			
		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementStruttura.PH_PK, id);
		
		String sql = SqlStatementStruttura.UPDATE_STRUTTURA_BATCH;
		update(sql, params);			
	}

	public void delete(Struttura t) {
	// TO IMPLEMENT
	}
		
	public void deleteStrutture(Date inDataInizioElaborazione) {
		var params = new MapSqlParameterSource();
		params.addValue("datainput", inDataInizioElaborazione);
		
		String sql = SqlStatementStruttura.DELETE_STRUTTURE;
		update(sql, params);			
	}
	
	public Date getActualDate() {
		var params = new MapSqlParameterSource();
		
		String sql = SqlStatementStruttura.GET_ACTUAL_DATE;
		
		Timestamp utilDate = queryForObject(sql, params, Timestamp.class);	
		return DateUtils.dateFromSqlTimestamp(utilDate);
	}
	
	// Store procedure per calcolare la categoria della struttura
	public void ricalcolaStruttura() {
		var params = new MapSqlParameterSource();
		
		String sql = SqlStatementStruttura.SELECT_RICALCOLA_STRUTTURA;
		//update(sql, params);
		String result = queryForObject(sql, params, String.class);
		/*
		 * se non funge con params vuoto (passare in input al servizio SqlStatementStruttura.FUNCTION_RICALCOLA_STRUTTURA)
		 * forse da aggiungere su vigilsanutil il metodo executeFunction
		 */
		//JdbcTemplate jdbcTemplate = template.getJdbcTemplate();
		//SimpleJdbcCall simpleJdbcCallFunction = new SimpleJdbcCall(jdbcTemplate)
		//		.withFunctionName(SqlStatementStruttura.FUNCTION_RICALCOLA_STRUTTURA);
		//simpleJdbcCallFunction.executeFunction(String.class);
	}
	
	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementStruttura.NEXTVAL_PK_ID;
		return queryForObject(sql, params,Integer.class);
	}

	public Boolean getParametroBoolean(Integer strutturaId, String strutturaCategoriaCod) {
		var params = new MapSqlParameterSource();
		params.addValue("strutturaId", strutturaId);
		params.addValue("strutturaCategoriaCod", strutturaCategoriaCod);
		String sql = SqlStatementStruttura.SELECT_PARAMETRO_BOOLEAN;
		return queryForObject(sql, params, Boolean.class);

	}

	public List<StrutturaPaginata> getStruttureByEnteId(Integer enteId, String ruoloEnteStrutturaCod, String filter, Integer strutturaId, Paginazione paginazione) {
		var params = new MapSqlParameterSource();
		params.addValue("enteId", enteId);
		var sql = new StringBuilder(SqlStatementStruttura.SELECT_STRUTTURE_BY_ENTE_ID);


		if (Objects.nonNull(filter)) {
			params.addValue("filter", filter);
			sql.append(SqlStatementStruttura.SELECT_STRUTTURE_BY_ENTE_ID_FILTER);
		}

		if (Objects.nonNull(ruoloEnteStrutturaCod)) {
			params.addValue("ruoloEnteStrutturaCod", ruoloEnteStrutturaCod);
			sql.append(SqlStatementStruttura.SELECT_STRUTTURE_BY_ENTE_ID_PROFILO_FILTER);
		}

		if (Objects.nonNull(strutturaId)) {
			params.addValue("strutturaId", strutturaId);
			sql.append(SqlStatementStruttura.SELECT_STRUTTURE_BY_ENTE_ID_PROFILO_STRUTTURA_ID);
		}
		
		PaginazioneUtils.setPaginazioneParam(paginazione, params);
		sql.append(PaginazioneUtils.generateSqlPaginazione(paginazione));
		return query(sql.toString(), params, new StrutturaPaginataMapper());
	}
}
