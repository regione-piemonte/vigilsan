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

import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.SoggettoEstesoMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.SoggettoMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.Soggetto;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.SoggettoEstesoLista;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementSoggetto;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementStruttura;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.PersistenceUtil;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;
import it.csi.vigilsan.vigilsanutil.generic.util.paginazione.Paginazione;
import it.csi.vigilsan.vigilsanutil.generic.util.paginazione.PaginazioneUtils;

@Repository
public class SoggettoDao extends AbstractPersistence implements Dao<Soggetto> {

	private Integer getId(Soggetto t) {
		return t.getSoggettoId();
	}

	private void setSpecificParameter(Soggetto t, MapSqlParameterSource params) {
		params.addValue("codiceFiscale", t.getCodiceFiscale());
		params.addValue("cognome", t.getCognome());
		params.addValue("nome", t.getNome());
		params.addValue("presenteConfiguratore", t.isPresenteConfiguratore());
		params.addValue("dataNascita", t.getDataNascita());
	}

	@Override
	public Soggetto get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementSoggetto.PH_PK, id);
		String sql = SqlStatementSoggetto.SELECT + SqlStatementCommon.WHERE + SqlStatementSoggetto.PK_EQUALS;
		return queryForObject(sql, params, new SoggettoMapper());
	}

	@Override
	public List<Soggetto> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementSoggetto.SELECT;
		return query(sql, params, new SoggettoMapper());
	}

	@Override
	public void insert(Soggetto t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementSoggetto.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (Objects.isNull(getId(t))) {
			sql = SqlStatementSoggetto.INSERT;
		} else {
			params.addValue(SqlStatementSoggetto.PH_PK, getId(t));
			sql = SqlStatementSoggetto.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementSoggetto.NEXTVAL_PK_ID;
		return queryForObject(sql, params, Integer.class);
	}

	@Override
	public void update(Soggetto t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementSoggetto.PH_PK, id);
		setSpecificParameter(t, params);

		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementSoggetto.UPDATE + SqlStatementCommon.SET + SqlStatementSoggetto.UPDATE_CAMPI
				+ SqlStatementCommon.WHERE + SqlStatementSoggetto.PK_EQUALS;
		update(sql, params);
	}

	@Override
	public void delete(Soggetto t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementSoggetto.PH_PK, getId(t));
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementSoggetto.UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE
				+ SqlStatementSoggetto.PK_EQUALS;
		update(sql, params);
	}

	public <T extends SoggettoEstesoLista> List<T> getSoggettoListPaginataFromParameter(String ruoloStrutturaCod, Integer strutturaId,
			String filter, Integer ospiteStatoId, Date dataPrimoIngressoDa, Date dataPrimoIngressoA,
			Date dataUltimaUscitaDa, Date dataUltimaUscitaA, Paginazione paginazione, Class<T> clazz) {
		var sql = new StringBuilder();
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementStruttura.PH_PK, strutturaId);
		params.addValue("ruoloStrutturaCod", ruoloStrutturaCod);
		if (Objects.nonNull(filter)) {
			params.addValue("filter", PersistenceUtil.sanitizeInput(filter));

			sql.append(String.format(SqlStatementSoggetto.SELECT_SOGGETTO_LISTA,
					SqlStatementCommon.AND + SqlStatementSoggetto.SELECT_SOGGETTO_LISTA_FILTER));
		} else {
			sql.append(String.format(SqlStatementSoggetto.SELECT_SOGGETTO_LISTA, ""));

		}

		Boolean isFirst = Boolean.TRUE;
		isFirst = PersistenceUtil.aggiungiCondizione("ospiteStatoId", ospiteStatoId,
				SqlStatementSoggetto.SELECT_SOGGETTO_OSPITE_STATO_ID_EQUALS, sql, params, isFirst);
		isFirst = PersistenceUtil.aggiungiCondizione("dataPrimoIngressoDa", dataPrimoIngressoDa,
				SqlStatementSoggetto.SELECT_SOGGETTO_DATA_PRIMO_INGRESSO_DA, sql, params, isFirst);
		isFirst = PersistenceUtil.aggiungiCondizione("dataPrimoIngressoA", dataPrimoIngressoA,
				SqlStatementSoggetto.SELECT_SOGGETTO_DATA_PRIMO_INGRESSO_A, sql, params, isFirst);
		isFirst = PersistenceUtil.aggiungiCondizione("dataUltimaUscitaDa", dataUltimaUscitaDa,
				SqlStatementSoggetto.SELECT_SOGGETTO_DATA_ULTIMA_USCITA_DA, sql, params, isFirst);
		PersistenceUtil.aggiungiCondizione("dataUltimaUscitaA", dataUltimaUscitaA,
				SqlStatementSoggetto.SELECT_SOGGETTO_DATA_ULTIMA_USCITA_A, sql, params, isFirst);

		PaginazioneUtils.setPaginazioneParam(paginazione, params);
		sql.append(PaginazioneUtils.generateSqlPaginazione(paginazione));
		return query(sql.toString(), params, new SoggettoEstesoMapper<>(clazz));
	}
}
