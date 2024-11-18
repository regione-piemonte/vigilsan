/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.PraticaEstesaMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.PraticaEstesaSingolaMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.PraticaEstesaSingolaMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.PraticaMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.PraticaRidottaEstesaMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.Pratica;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaEstesa;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaForPost;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaInterface;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaPost;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaRidottaEstesa;
import it.csi.vigilsan.vigilsanpratiche.util.query.SqlStatementPratica;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.PersistenceUtil;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;
import it.csi.vigilsan.vigilsanutil.generic.util.paginazione.Paginazione;
import it.csi.vigilsan.vigilsanutil.generic.util.paginazione.PaginazioneUtils;

@Repository
public class PraticaDao extends AbstractPersistence implements Dao<Pratica> {

	private Integer getId(Pratica t) {
		return t.getPraticaId();
	}

	private void setSpecificParameter(Pratica t, MapSqlParameterSource params) {
		params.addValue("praticaTipoId", t.getPraticaTipoId());
		params.addValue("enteId", t.getEnteId());
		params.addValue("strutturaId", t.getStrutturaId());
		params.addValue("dataoraChiusura", t.getDataoraChiusura());
		params.addValue("dataoraApertura", t.getDataoraApertura());
		params.addValue("praticaNumero", t.getPraticaNumero());
	}

	@Override
	public Pratica get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPratica.PH_PK, id);
		String sql = SqlStatementPratica.SELECT_BY_ID;
		return queryForObject(sql, params, new PraticaMapper<>(Pratica.class));
	}

	@Override
	public List<Pratica> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementPratica.SELECT_ALL;
		return query(sql, params, new PraticaMapper<>(Pratica.class));
	}

	@Override
	public void insert(Pratica t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPratica.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (Objects.isNull(getId(t))) {
			sql = SqlStatementPratica.INSERT_GENERIC;
		} else {
			params.addValue(SqlStatementPratica.PH_PK, getId(t));
			sql = SqlStatementPratica.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementPratica.NEXTVAL_PK_ID;
		return queryForObject(sql, params, Integer.class);
	}

	@Override
	public void update(Pratica t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPratica.PH_PK, id);
		setSpecificParameter(t, params);

		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementPratica.UPDATE_GENERIC;
		update(sql, params);
	}

	@Override
	public void delete(Pratica t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementPratica.PH_PK, getId(t));
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementPratica.DELETE;
		update(sql, params);
	}

	public List<PraticaRidottaEstesa> selectPraticheInseribili(Integer enteId, Integer profiloId, Integer praticaTipoId,
			Integer strutturaTipoId, String filterStruttura, Date dataChiusuraMax, Paginazione paginazione) {
		var params = new MapSqlParameterSource();
		params.addValue("enteId", enteId);
		params.addValue("profiloId", profiloId);

		var sql = new StringBuilder(SqlStatementPratica.SELECT_PRATICHE_INSERIBILI);
		if (Objects.nonNull(dataChiusuraMax)) {
			params.addValue("dataChiusuraMax", dataChiusuraMax);
			sql.append(SqlStatementPratica.SELECT_PRATICHE_INSERIBILI_DATA_CHIUSURA_MAX);
		} else {
			sql.append(SqlStatementPratica.SELECT_PRATICHE_INSERIBILI_DATA_CHIUSURA_MAX_IS_NULL);
		}
		sql.append(SqlStatementPratica.SELECT_PRATICHE_INSERIBILI_2);
		if (Objects.nonNull(praticaTipoId)) {
			params.addValue("praticaTipoId", praticaTipoId);
			sql.append(SqlStatementPratica.SELECT_PRATICHE_INSERIBILI_PRATICA_TIPO_ID);
		}
		if (Objects.nonNull(strutturaTipoId)) {
			params.addValue("strutturaTipoId", strutturaTipoId);
			sql.append(SqlStatementPratica.SELECT_PRATICHE_INSERIBILI_STRUTTURA_TIPO_ID);

		}
		if (Objects.nonNull(filterStruttura)) {
			params.addValue("filterStruttura", filterStruttura);
			sql.append(SqlStatementPratica.SELECT_PRATICHE_INSERIBILI_FILTER_STRUTTURA);
		}

		PaginazioneUtils.setPaginazioneParam(paginazione, params);
		sql.append(PaginazioneUtils.generateSqlPaginazione(paginazione));

		String sqlString = sql.toString();
		return query(sqlString, params, new PraticaRidottaEstesaMapper());
	}

	public List<PraticaEstesa> getPratiche(Integer enteId, Integer strutturaId, Integer profiloId,
			Integer tipoPraticaId, Integer statoPraticaId, Integer tipoPrescrizioneId, Integer statoPrescrizioneId,
			String dataAperturaPrescrizioneDa, String dataAperturaPrescrizioneA, String dataChiusuraPrescrizioneDa,
			String dataChiusuraPrescrizioneA, Integer tipoAppuntamentoId, Integer statoAppuntamentoId,
			String dataInizioAppuntamentoDa, String dataInizioAppuntamentoA, String dataFineAppuntamentoDa,
			String dataFineAppuntamentoA, String dataPraticaAperturaDa, String dataPraticaAperturaA,
			String dataPraticaChiusuraDa, String dataPraticaChiusuraA, String filterStruttura, String filterUtente,
			Paginazione paginazione) {

		dataAperturaPrescrizioneDa = PersistenceUtil.sanitizeInput(dataAperturaPrescrizioneDa);
		dataAperturaPrescrizioneA = PersistenceUtil.sanitizeInput(dataAperturaPrescrizioneA);
		dataChiusuraPrescrizioneDa = PersistenceUtil.sanitizeInput(dataChiusuraPrescrizioneDa);
		dataChiusuraPrescrizioneA = PersistenceUtil.sanitizeInput(dataChiusuraPrescrizioneA);

		dataInizioAppuntamentoDa = PersistenceUtil.sanitizeInput(dataInizioAppuntamentoDa);
		dataInizioAppuntamentoA = PersistenceUtil.sanitizeInput(dataInizioAppuntamentoA);
		dataFineAppuntamentoDa = PersistenceUtil.sanitizeInput(dataFineAppuntamentoDa);
		dataFineAppuntamentoA = PersistenceUtil.sanitizeInput(dataFineAppuntamentoA);

		dataPraticaAperturaDa = PersistenceUtil.sanitizeInput(dataPraticaAperturaDa);
		dataPraticaAperturaA = PersistenceUtil.sanitizeInput(dataPraticaAperturaA);
		dataPraticaChiusuraDa = PersistenceUtil.sanitizeInput(dataPraticaChiusuraDa);
		dataPraticaChiusuraA = PersistenceUtil.sanitizeInput(dataPraticaChiusuraA);

		var params = new MapSqlParameterSource();
		params.addValue("profiloId", profiloId);

		var sql = new StringBuilder(SqlStatementPratica.SELECT_PRATICHE_LISTA);
		if (Objects.nonNull(filterStruttura)) {
			params.addValue("filterStruttura", filterStruttura);
			sql.append(SqlStatementPratica.SELECT_PRATICHE_LISTA_FILTER_STRUTTURA);
		}
		if (Objects.nonNull(enteId)) {
			params.addValue("enteId", enteId);
			sql.append(SqlStatementPratica.SELECT_PRATICHE_LISTA_ENTE);
		}
		if (Objects.nonNull(strutturaId)) {
			params.addValue("strutturaId", strutturaId);
			sql.append(SqlStatementPratica.SELECT_PRATICHE_LISTA_STRUTTURA);
		}
		if (Objects.nonNull(tipoPraticaId)) {
			params.addValue("tipoPraticaId", tipoPraticaId);
			sql.append(SqlStatementPratica.SELECT_PRATICHE_LISTA_PRATICA_TIPO);
		}
		if (Objects.nonNull(statoPraticaId)) {
			params.addValue("statoPraticaId", statoPraticaId);
			sql.append(SqlStatementPratica.SELECT_PRATICHE_LISTA_PRATICA_STATO);
		}

		if (Objects.nonNull(dataPraticaAperturaDa)) {
			params.addValue("dataPraticaAperturaDa", dataPraticaAperturaDa);
			sql.append(SqlStatementPratica.SELECT_PRATICHE_LISTA_PRATICA_APERTURA_DA);
		}
		if (Objects.nonNull(dataPraticaAperturaA)) {
			params.addValue("dataPraticaAperturaA", dataPraticaAperturaA);
			sql.append(SqlStatementPratica.SELECT_PRATICHE_LISTA_PRATICA_APERTURA_A);
		}
		if (Objects.nonNull(dataPraticaChiusuraDa)) {
			params.addValue("dataPraticaChiusuraDa", dataPraticaChiusuraDa);
			sql.append(SqlStatementPratica.SELECT_PRATICHE_LISTA_PRATICA_CHIUSURA_DA);
		}
		if (Objects.nonNull(dataPraticaChiusuraA)) {
			params.addValue("dataPraticaChiusuraA", dataPraticaChiusuraA);
			sql.append(SqlStatementPratica.SELECT_PRATICHE_LISTA_PRATICA_CHIUSURA_A);
		}

		if (Objects.nonNull(tipoAppuntamentoId) || Objects.nonNull(dataInizioAppuntamentoDa)
				|| Objects.nonNull(dataInizioAppuntamentoA) || Objects.nonNull(dataFineAppuntamentoDa)
				|| Objects.nonNull(dataFineAppuntamentoA) || Objects.nonNull(statoAppuntamentoId)) {

			sql.append(SqlStatementPratica.SELECT_PRATICHE_LISTA_APPUNTAMENTO);
			if (Objects.nonNull(tipoAppuntamentoId)) {
				params.addValue("tipoAppuntamentoId", tipoAppuntamentoId);
				sql.append(SqlStatementPratica.SELECT_PRATICHE_LISTA_APPUNTAMENTO_TIPO);
			}
			if (Objects.nonNull(dataInizioAppuntamentoDa)) {
				params.addValue("dataInizioAppuntamentoDa", dataInizioAppuntamentoDa);
				sql.append(SqlStatementPratica.SELECT_PRATICHE_LISTA_APPUNTAMENTO_INIZIO_DA);
			}
			if (Objects.nonNull(dataInizioAppuntamentoA)) {
				params.addValue("dataInizioAppuntamentoA", dataInizioAppuntamentoA);
				sql.append(SqlStatementPratica.SELECT_PRATICHE_LISTA_APPUNTAMENTO_INIZIO_A);
			}
			if (Objects.nonNull(dataFineAppuntamentoDa)) {
				params.addValue("dataFineAppuntamentoDa", dataFineAppuntamentoDa);
				sql.append(SqlStatementPratica.SELECT_PRATICHE_LISTA_APPUNTAMENTO_FINE_DA);
			}
			if (Objects.nonNull(dataFineAppuntamentoA)) {
				params.addValue("dataFineAppuntamentoA", dataFineAppuntamentoA);
				sql.append(SqlStatementPratica.SELECT_PRATICHE_LISTA_APPUNTAMENTO_FINE_A);
			}
			if (Objects.nonNull(statoAppuntamentoId)) {
				params.addValue("statoAppuntamentoId", statoAppuntamentoId);
				sql.append(SqlStatementPratica.SELECT_PRATICHE_LISTA_APPUNTAMENTO_STATO);
			}
			sql.append(SqlStatementPratica.SELECT_PRATICHE_LISTA_APPUNTAMENTO_FINE);
		}

		if (Objects.nonNull(tipoPrescrizioneId) || Objects.nonNull(dataAperturaPrescrizioneDa)
				|| Objects.nonNull(dataAperturaPrescrizioneA) || Objects.nonNull(dataChiusuraPrescrizioneDa)
				|| Objects.nonNull(dataChiusuraPrescrizioneA) || Objects.nonNull(statoPrescrizioneId)) {

			sql.append(SqlStatementPratica.SELECT_PRATICHE_LISTA_PRESCRIZIONE);
			if (Objects.nonNull(tipoPrescrizioneId)) {
				params.addValue("tipoPrescrizioneId", tipoPrescrizioneId);
				sql.append(SqlStatementPratica.SELECT_PRATICHE_LISTA_PRESCRIZIONE_TIPO);
			}
			if (Objects.nonNull(dataAperturaPrescrizioneDa)) {
				params.addValue("dataAperturaPrescrizioneDa", dataAperturaPrescrizioneDa);
				sql.append(SqlStatementPratica.SELECT_PRATICHE_LISTA_PRESCRIZIONE_APERTURA_DA);
			}
			if (Objects.nonNull(dataAperturaPrescrizioneA)) {
				params.addValue("dataAperturaPrescrizioneA", dataAperturaPrescrizioneA);
				sql.append(SqlStatementPratica.SELECT_PRATICHE_LISTA_PRESCRIZIONE_APERTURA_A);
			}
			if (Objects.nonNull(dataChiusuraPrescrizioneDa)) {
				params.addValue("dataChiusuraPrescrizioneDa", dataChiusuraPrescrizioneDa);
				sql.append(SqlStatementPratica.SELECT_PRATICHE_LISTA_PRESCRIZIONE_CHIUSURA_DA);
			}
			if (Objects.nonNull(dataChiusuraPrescrizioneA)) {
				params.addValue("dataChiusuraPrescrizioneA", dataChiusuraPrescrizioneA);
				sql.append(SqlStatementPratica.SELECT_PRATICHE_LISTA_PRESCRIZIONE_CHIUSURA_A);
			}
			if (Objects.nonNull(statoPrescrizioneId)) {
				params.addValue("statoPrescrizioneId", statoPrescrizioneId);
				sql.append(SqlStatementPratica.SELECT_PRATICHE_LISTA_PRESCRIZIONE_STATO);
			}
			sql.append(SqlStatementPratica.SELECT_PRATICHE_LISTA_PRESCRIZIONE_FINE);
		}

		PaginazioneUtils.setPaginazioneParam(paginazione, params);
		sql.append(PaginazioneUtils.generateSqlPaginazione(paginazione));
		return query(sql.toString(), params, new PraticaEstesaMapper());
	}

	public <T extends PraticaInterface> T get(Integer id, Class<T> class1) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPratica.PH_PK, id);
		String sql = SqlStatementPratica.SELECT_BY_ID;
		return queryForObject(sql, params, new PraticaMapper<>(class1));
	}

	public PraticaForPost getPraticaValida(Integer praticaId) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPratica.PH_PK, praticaId);
		String sql = SqlStatementPratica.SELECT_BY_ID_VALIDO;
		return queryForObject(sql, params, new PraticaMapper<>(PraticaForPost.class));
	}

	public PraticaPost getForModulo(Integer praticaId) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPratica.PH_PK, praticaId);
		String sql = SqlStatementPratica.SELECT_BY_ID_VALIDO;
		return queryForObject(sql, params, new PraticaMapper<>(PraticaPost.class));
	}

	public PraticaEstesa getPraticaEstesa(Integer id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPratica.PH_PK, id);
		String sql = SqlStatementPratica.SELECT_PRATICA_ESTESA_BY_ID;
		return queryForObject(sql, params, new PraticaEstesaSingolaMapper());
	}
}
