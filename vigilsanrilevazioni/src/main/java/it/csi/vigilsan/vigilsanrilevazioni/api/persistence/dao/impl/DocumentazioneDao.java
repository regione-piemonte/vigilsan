/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanrilevazioni.api.persistence.dao.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsanrilevazioni.api.generated.dto.ModelDocumentazione;
import it.csi.vigilsan.vigilsanrilevazioni.api.generated.dto.ModelParametro;
import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.mapper.DocumentazioneMapper;
import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.mapper.ModelParametroMapper;
import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.model.Documentazione;
import it.csi.vigilsan.vigilsanrilevazioni.util.query.SqlStatementDocumentazione;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class DocumentazioneDao extends AbstractPersistence implements Dao<Documentazione> {


	private Integer getId(Documentazione t) {
		return t.getDocumentazioneId();
	}

	private void setSpecificParameter(Documentazione t, MapSqlParameterSource params) {
		params.addValue("strutturaId", t.getStrutturaId());
		params.addValue("strCatId", t.getStrCatId());
		params.addValue("moduloCompilatoId", t.getModuloCompilatoId());
		params.addValue("moduloConfigId", t.getModuloConfigId());
		params.addValue("dataoraDocumentazione", t.getDataoraDocumentazione());
		params.addValue("dataoraScadenza", t.getDataoraScadenza());
		params.addValue("occorrenza", t.getOccorrenza());
	}

	@Override
	public Documentazione get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementDocumentazione.PH_PK, id);
		String sql = SqlStatementDocumentazione.SELECT_BY_ID;
		return queryForObject(sql, params, new DocumentazioneMapper<>(Documentazione.class));
	}

	@Override
	public List<Documentazione> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementDocumentazione.SELECT_ALL;
		return query(sql, params, new DocumentazioneMapper<>(Documentazione.class));
	}

	@Override
	public void insert(Documentazione t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementDocumentazione.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (Objects.isNull(getId(t))) {
			sql = SqlStatementDocumentazione.INSERT_GENERIC;
		} else {
			params.addValue(SqlStatementDocumentazione.PH_PK, getId(t));
			sql = SqlStatementDocumentazione.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementDocumentazione.NEXTVAL_PK_ID;
		return queryForObject(sql, params, Integer.class);
	}

	@Override
	public void update(Documentazione t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementDocumentazione.PH_PK, id);
		setSpecificParameter(t, params);

		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementDocumentazione.UPDATE_GENERIC;
		update(sql, params);
	}

	@Override
	public void delete(Documentazione t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementDocumentazione.PH_PK, getId(t));
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementDocumentazione.DELETE;
		update(sql, params);
	}

	public void invalida(Documentazione t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementDocumentazione.PH_PK, t.getDocumentazioneId());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		String sql = SqlStatementDocumentazione.UPDATE + SqlStatementCommon.INVALIDA + SqlStatementCommon.WHERE
				+ SqlStatementDocumentazione.PK_EQUALS;
		update(sql, params);

	}

	public String getParametroBoolean(Integer strutturaId, String moduloCod, String valore) {
		var params = new MapSqlParameterSource();
		params.addValue("strutturaId", strutturaId);
		String sql = String.format(SqlStatementDocumentazione.SELECT_PARAMETRO_BOOLEAN,valore,moduloCod);
		return queryForObject(sql, params, String.class);

	}

//	public List<ModelParametro> getParametroBooleanLista(Integer strutturaId, String moduloCod,String  valori) {
//		var params = new MapSqlParameterSource();
//		params.addValue("strutturaId", strutturaId);
//		String sql = String.format(SqlStatementDocumentazione.SELECT_PARAMEdTRO_BOOLEAN,valori,moduloCod);
//		return queryForObject(sql, params, new ModelParametroMapper());
//
//	}

	public void deleteOldDocumenti(ModelDocumentazione doc, String shibIdentitaCodiceFiscale) {
		var params = new MapSqlParameterSource();
		params.addValue("strutturaId", doc.getStrutturaId());
		params.addValue("moduloConfigId", doc.getModuloConfigId());
		params.addValue("strCatId", doc.getStrCatId());
		params.addValue("occorrenza", doc.getOccorrenza());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, doc.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, shibIdentitaCodiceFiscale);
		String sql = SqlStatementDocumentazione.UPDATE + SqlStatementCommon.INVALIDA + SqlStatementCommon.WHERE
				+ SqlStatementDocumentazione.DELETE_OLDS;
		update(sql, params);

	}


}
