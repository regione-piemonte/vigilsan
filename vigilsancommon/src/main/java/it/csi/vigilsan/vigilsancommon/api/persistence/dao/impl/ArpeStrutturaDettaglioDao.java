/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.ArpeStrutturaDettaglioMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.ArpeStrutturaDettaglio;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.ArpeStrutturaDettaglioEsteso;
import it.csi.vigilsan.vigilsancommon.util.LogMessageEnum;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementArpeStrutturaDettaglio;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementStruttura;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;
import jakarta.validation.constraints.NotNull;

@Repository
public class ArpeStrutturaDettaglioDao extends AbstractPersistence implements Dao<ArpeStrutturaDettaglio> {

	private Integer getId(ArpeStrutturaDettaglio t) {
		return t.getArpeStrDettId();
	}

	private void setSpecificParameter(ArpeStrutturaDettaglio t, MapSqlParameterSource params) {
		params.addValue("arpeStrDettId", t.getArpeStrDettId());
		params.addValue(SqlStatementStruttura.PH_PK, t.getStrutturaId());
		params.addValue("arpeStrutturaTipoId", t.getArpeStrutturaTipoId());
		params.addValue("arpeAssistenzaTipoId", t.getArpeAssistenzaTipoId());
		params.addValue("arpeAttivitaId", t.getArpeAttivitaId());
		params.addValue("arpeAttivitaClasseId", t.getArpeAttivitaClasseId());
		params.addValue("arpeDisciplinaId", t.getArpeDisciplinaId());
		params.addValue("arpe_data_attivazione_struttura", t.getArpeDataAttivazioneStruttura());
		params.addValue("arpe_data_attivazione_assistenza", t.getArpeDataAttivazioneAssistenza());
		params.addValue("arpe_data_attivazione_attivita", t.getArpeDataAttivazioneAttivita());
	}

	@Override
	public ArpeStrutturaDettaglio get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementArpeStrutturaDettaglio.PH_PK, id);
		String sql = SqlStatementArpeStrutturaDettaglio.SELECT + SqlStatementCommon.WHERE
				+ SqlStatementArpeStrutturaDettaglio.PK_EQUALS;
		return queryForObject(sql, params, new ArpeStrutturaDettaglioMapper<>(ArpeStrutturaDettaglio.class));
	}

	@Override
	public List<ArpeStrutturaDettaglio> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementArpeStrutturaDettaglio.SELECT;
		return query(sql, params, new ArpeStrutturaDettaglioMapper<>(ArpeStrutturaDettaglio.class));
	}

	@Override
	public void insert(ArpeStrutturaDettaglio t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementArpeStrutturaDettaglio.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (Objects.isNull(getId(t))) {
			sql = SqlStatementArpeStrutturaDettaglio.INSERT;
		} else {
			params.addValue(SqlStatementArpeStrutturaDettaglio.PH_PK, getId(t));
			sql = SqlStatementArpeStrutturaDettaglio.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementArpeStrutturaDettaglio.NEXTVAL_PK_ID;
		return queryForObject(sql, params, Integer.class);
	}

	@Override
	public void update(ArpeStrutturaDettaglio t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementArpeStrutturaDettaglio.PH_PK, id);
		setSpecificParameter(t, params);

		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementArpeStrutturaDettaglio.UPDATE + SqlStatementCommon.SET
				+ SqlStatementArpeStrutturaDettaglio.UPDATE_CAMPI + SqlStatementCommon.WHERE
				+ SqlStatementArpeStrutturaDettaglio.PK_EQUALS;
		update(sql, params);
	}

	@Override
	public void delete(ArpeStrutturaDettaglio t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementArpeStrutturaDettaglio.PH_PK, getId(t));
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementArpeStrutturaDettaglio.UPDATE + SqlStatementCommon.LOGIC_DELETE
				+ SqlStatementCommon.WHERE + SqlStatementArpeStrutturaDettaglio.PK_EQUALS;
		update(sql, params);
	}

	// vigil_d_arpe_struttura_tipo
	public ArpeStrutturaDettaglio getArpeStrutturaDettaglioByCod(ArpeStrutturaDettaglio inStrutturaDettaglio) {
		var methodName = "getArpeStrutturaDettaglioByCod";
		logDebug(null,methodName, LogMessageEnum.BEGIN.getMessage());

		var params = new MapSqlParameterSource();

		ArpeStrutturaDettaglio arpeStrutturaDettaglioReturn = null;
		params.addValue(SqlStatementStruttura.PH_PK, inStrutturaDettaglio.getStrutturaId());
		params.addValue("arpeAssistenzaTipoId", inStrutturaDettaglio.getArpeAssistenzaTipoId());
		params.addValue("arpeStrutturaTipoId", inStrutturaDettaglio.getArpeStrutturaTipoId());
		params.addValue("arpeAttivitaId", inStrutturaDettaglio.getArpeAttivitaId());
		params.addValue("arpeAttivitaClasseId", inStrutturaDettaglio.getArpeAttivitaClasseId());
		params.addValue("arpeDisciplinaId", inStrutturaDettaglio.getArpeDisciplinaId());
		
		String sql = SqlStatementArpeStrutturaDettaglio.SELECT_STRUTTURA_DETTAGLIO_BY_ARPE_STRUTTURA_DETTAGLIO_ID;
		arpeStrutturaDettaglioReturn = queryForObject(sql, params, new ArpeStrutturaDettaglioMapper<>(ArpeStrutturaDettaglio.class));
		
		logDebug(null, methodName, "ArpeStrutturaDettaglio Id found : "+arpeStrutturaDettaglioReturn.getArpeStrDettId());
		logDebug(null, methodName, "ArpeDataAttivazioneAssistenza value : "+arpeStrutturaDettaglioReturn.getArpeDataAttivazioneAssistenza());
		logDebug(null, methodName, "ArpeDataAttivazioneAttivita value : "+arpeStrutturaDettaglioReturn.getArpeDataAttivazioneAttivita());
		logDebug(null, methodName, "ArpeDataAttivazioneStruttura value : "+arpeStrutturaDettaglioReturn.getArpeDataAttivazioneStruttura());
		logDebug(null, methodName, "StrutturaId value : "+arpeStrutturaDettaglioReturn.getStrutturaId());		
		logDebug(null, methodName, "ArpeAttivitaId value : "+arpeStrutturaDettaglioReturn.getArpeAttivitaId());			
		return arpeStrutturaDettaglioReturn;
	}
	
	public void updateRefreshModifica(ArpeStrutturaDettaglio inStrutturaDettaglio) {
		var params = new MapSqlParameterSource();

		params.addValue("arpe_data_attivazione_struttura", inStrutturaDettaglio.getArpeDataAttivazioneStruttura());
		params.addValue("arpe_data_attivazione_assistenza", inStrutturaDettaglio.getArpeDataAttivazioneAssistenza());
		params.addValue("arpe_data_attivazione_attivita", inStrutturaDettaglio.getArpeDataAttivazioneAttivita());
		params.addValue(SqlStatementCommon.PH_DATA_MODIFICA, inStrutturaDettaglio.getDataModifica());
		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, inStrutturaDettaglio.getUtenteModifica());
		params.addValue("arpeStrDettId", inStrutturaDettaglio.getArpeStrDettId());
		String sql = SqlStatementArpeStrutturaDettaglio.UPDATE_REFRESH_STRUTTURA_DETTAGLIO_BY_ARPE_STRUTTURA_DETTAGLIO_ID;
		update(sql, params);
		
	}

	public void deleteStruttureDettaglio(Date inDataInizioElaborazione) {
		var params = new MapSqlParameterSource();
		params.addValue("datainput", inDataInizioElaborazione);

		String sql = SqlStatementArpeStrutturaDettaglio.DELETE_STRUTTURE_DETTAGLIO;
		update(sql, params);
	}

	public List<ArpeStrutturaDettaglioEsteso> getArpeStrutturaDettaglioByStrutturaId(@NotNull Integer strutturaId) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementStruttura.PH_PK, strutturaId);
		String sql = SqlStatementArpeStrutturaDettaglio.SELECT_BY_STRUTTURA_ID_LISTA;
		return query(sql, params, new ArpeStrutturaDettaglioMapper<>(ArpeStrutturaDettaglioEsteso.class));
	}

}
