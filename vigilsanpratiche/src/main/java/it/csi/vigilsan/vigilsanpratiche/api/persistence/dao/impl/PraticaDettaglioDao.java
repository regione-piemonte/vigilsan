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

import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelPraticaDettaglioEstesaPratica;
import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelPraticaPost;
import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelPraticaScadenza;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.PraticaDettaglioEstesaPraticaMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.PraticaDettaglioEstesaPraticaPerDiarioMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.PraticaDettaglioForPostMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.PraticaDettaglioMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.PraticaScadenzaMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaDettaglio;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaDettaglioEstesaPraticaPerDiario;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaDettaglioForPost;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaDettaglioPost;
import it.csi.vigilsan.vigilsanpratiche.api.service.impl.PraticaServiceImpl.UpdateStatus;
import it.csi.vigilsan.vigilsanpratiche.util.query.SqlStatementPraticaDettaglio;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.PersistenceUtil;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class PraticaDettaglioDao extends AbstractPersistence implements Dao<PraticaDettaglio> {

	private Integer getId(PraticaDettaglio t) {
		return t.getPraticaDetId();
	}

	private void setSpecificParameter(PraticaDettaglio t, MapSqlParameterSource params) {
		params.addValue("praticaId", t.getPraticaId());
		params.addValue("praticaStatoId", t.getPraticaStatoId());
		params.addValue("prescrizioneId", t.getPrescrizioneId());
		params.addValue("prescrizioneStatoId", t.getPrescrizioneStatoId());
		params.addValue("appuntamentoId", t.getAppuntamentoId());
		params.addValue("appuntamentoStatoId", t.getAppuntamentoStatoId());
		params.addValue("azioneId", t.getAzioneId());
		params.addValue("dataoraAzione", t.getDataoraAzione());
		params.addValue("moduloCompilatoId", t.getModuloCompilatoId());
		params.addValue("note", t.getNote());
		params.addValue("profiloId", t.getProfiloId());
		params.addValue("profiloIdScadenza", t.getProfiloIdScadenza());
		params.addValue("flgScadenza", t.getFlgScadenza());
	}

	@Override
	public PraticaDettaglio get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPraticaDettaglio.PH_PK, id);
		String sql = SqlStatementPraticaDettaglio.SELECT_BY_ID;
		return queryForObject(sql, params,
				new PraticaDettaglioMapper<PraticaDettaglio, PraticaDettaglio>(PraticaDettaglio.class));
	}

	public PraticaDettaglioPost getForPost(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPraticaDettaglio.PH_PK, id);
		String sql = SqlStatementPraticaDettaglio.SELECT_BY_ID;
		return queryForObject(sql, params,
				new PraticaDettaglioMapper<PraticaDettaglioPost, PraticaDettaglioPost>(PraticaDettaglioPost.class));
	}

	@Override
	public List<PraticaDettaglio> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementPraticaDettaglio.SELECT_ALL;
		return query(sql, params,
				new PraticaDettaglioMapper<PraticaDettaglio, PraticaDettaglio>(PraticaDettaglio.class));
	}

	@Override
	public void insert(PraticaDettaglio t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPraticaDettaglio.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (Objects.isNull(getId(t))) {
			sql = SqlStatementPraticaDettaglio.INSERT_GENERIC;
		} else {
			params.addValue(SqlStatementPraticaDettaglio.PH_PK, getId(t));
			sql = SqlStatementPraticaDettaglio.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementPraticaDettaglio.NEXTVAL_PK_ID;
		return queryForObject(sql, params, Integer.class);
	}

	@Override
	public void update(PraticaDettaglio t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPraticaDettaglio.PH_PK, id);
		setSpecificParameter(t, params);

		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementPraticaDettaglio.UPDATE_GENERIC;
		update(sql, params);
	}

	public void updateSpecificaPostPratiche(PraticaDettaglio t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPraticaDettaglio.PH_PK, id);
		setSpecificParameter(t, params);

		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementPraticaDettaglio.UPDATE_SPECIFICA_POST_PRATICHE;
		update(sql, params);
	}

	@Override
	public void delete(PraticaDettaglio t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementPraticaDettaglio.PH_PK, getId(t));
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementPraticaDettaglio.DELETE;
		update(sql, params);
	}

	public List<ModelPraticaScadenza> getScazende(Integer profiloId, Integer enteId, Integer strutturaId, String dataDa,
			String dataA) {
		dataDa = PersistenceUtil.sanitizeInput(dataDa);
		dataA = PersistenceUtil.sanitizeInput(dataA);

		var params = new MapSqlParameterSource();
		params.addValue("profiloId", profiloId);
		String sql = null;
		if (enteId != null) {
			params.addValue("enteId", enteId);
			sql = String.format(SqlStatementPraticaDettaglio.SELECT_SCADENZE,
					SqlStatementPraticaDettaglio.SELECT_SCADENZE_ENTE,
					SqlStatementPraticaDettaglio.SELECT_SCADENZE_ENTE);
		} else if (strutturaId != null) {
			params.addValue("strutturaId", strutturaId);
			sql = String.format(SqlStatementPraticaDettaglio.SELECT_SCADENZE,
					SqlStatementPraticaDettaglio.SELECT_SCADENZE_STRUTTURA,
					SqlStatementPraticaDettaglio.SELECT_SCADENZE_STRUTTURA);
		} else {
			sql = String.format(SqlStatementPraticaDettaglio.SELECT_SCADENZE, "", "");

		}
		params.addValue("dataDa", dataDa);
		params.addValue("dataA", dataA);
		return query(sql, params, new PraticaScadenzaMapper());
	}

	public List<ModelPraticaDettaglioEstesaPratica> getByPratica(Integer praticaId, Date filterDate) {
		var params = new MapSqlParameterSource();
		String sql;
		if (filterDate != null) {
			sql = String.format(SqlStatementPraticaDettaglio.SELECT_BY_ID_AND_PROFILO,
					SqlStatementPraticaDettaglio.SELECT_BY_ID_AND_PROFILO_PER_DIARIO_FILTER_DATE);
			params.addValue("filterDate", filterDate);
		} else {
			sql = String.format(SqlStatementPraticaDettaglio.SELECT_BY_ID_AND_PROFILO, "");
		}
		params.addValue("praticaId", praticaId);
		return query(sql, params, new PraticaDettaglioEstesaPraticaMapper());
	}

	public List<PraticaDettaglioEstesaPraticaPerDiario> getAttivitaByPratica(Integer praticaId, Date filterDate) {
		var params = new MapSqlParameterSource();
		params.addValue("praticaId", praticaId);
		StringBuilder sql = new StringBuilder(SqlStatementPraticaDettaglio.SELECT_BY_ID_AND_PROFILO_PER_DIARIO);
		if (filterDate != null) {
			sql.append(SqlStatementPraticaDettaglio.SELECT_BY_ID_AND_PROFILO_PER_DIARIO_FILTER_DATE);
			params.addValue("filterDate", filterDate);
		}
		return query(sql.append(SqlStatementPraticaDettaglio.SELECT_BY_ID_AND_PROFILO_PER_DIARIO_ORDER_BY).toString(),
				params, new PraticaDettaglioEstesaPraticaPerDiarioMapper());
	}

	public PraticaDettaglio getByPraticaDetIdPraticaIdProfiloId(Integer praticaDetId, Integer praticaId,
			Integer profiloId) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPraticaDettaglio.PH_PK, praticaDetId);
		params.addValue("praticaId", praticaId);
		params.addValue("profiloId", profiloId);
		String sql = SqlStatementPraticaDettaglio.SELECT_BY_PRATICA_ID_PRATICA_DET_ID_PROFILO_ID;
		return queryForObject(sql, params,
				new PraticaDettaglioMapper<PraticaDettaglio, PraticaDettaglio>(PraticaDettaglio.class));
	}

	public PraticaDettaglio getByPraticaDetIdPraticaId(Integer praticaDetId, Integer praticaId) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPraticaDettaglio.PH_PK, praticaDetId);
		params.addValue("praticaId", praticaId);
		String sql = SqlStatementPraticaDettaglio.SELECT_BY_PRATICA_ID_PRATICA_DET_ID;
		return queryForObject(sql, params,
				new PraticaDettaglioMapper<PraticaDettaglio, PraticaDettaglio>(PraticaDettaglio.class));
	}

	public List<PraticaDettaglioForPost> selectListaPraticheForPost(ModelPraticaPost body,
			String shibIdentitaCodiceFiscale, UpdateStatus operazione) {
		var modelPraticaDettaglio = body.getPraticaDettaglio();
		var params = new MapSqlParameterSource();
		params.addValue("praticaId", body.getPraticaId());
		params.addValue("praticaDetId", modelPraticaDettaglio.getPraticaDetId());
		if (UpdateStatus.INSERT.equals(operazione) || UpdateStatus.UPDATE.equals(operazione)) {
			params.addValue("prescrizioneId", modelPraticaDettaglio.getPrescrizioneId());
			params.addValue("appuntamentoId", modelPraticaDettaglio.getAppuntamentoId());
			params.addValue("azioneId", modelPraticaDettaglio.getAzioneId());
			params.addValue("dataoraAzione", modelPraticaDettaglio.getDataoraAzione());
			params.addValue("moduloCompilatoId", modelPraticaDettaglio.getModuloCompilatoId());
			params.addValue("note", modelPraticaDettaglio.getNote());
			params.addValue("profiloId", modelPraticaDettaglio.getProfiloId());
			params.addValue("profiloIdScadenza", modelPraticaDettaglio.getProfiloIdScadenza());
			params.addValue("prescrizioneId", modelPraticaDettaglio.getPrescrizioneId());
			params.addValue("flgScadenza", modelPraticaDettaglio.getFlgScadenza());

			params.addValue("prescrizioneNumero", modelPraticaDettaglio.getPrescrizioneNumero());
			params.addValue("appuntamentoNumero", modelPraticaDettaglio.getAppuntamentoNumero());
			params.addValue("praticaNumero", body.getPraticaNumero());

			params.addValue("utente", shibIdentitaCodiceFiscale);
		}

		String sql;
		if (UpdateStatus.INSERT.equals(operazione) || UpdateStatus.UPDATE.equals(operazione)) {
			sql = String.format(SqlStatementPraticaDettaglio.SELECT_PRATICHE_DETTAGLIO_FOR_POST,
					SqlStatementPraticaDettaglio.SELECT_PRATICHE_DETTAGLIO_FOR_POST_INSERIMENTO);

		} else {
			sql = String.format(SqlStatementPraticaDettaglio.SELECT_PRATICHE_DETTAGLIO_FOR_POST, "");

		}
		return query(sql, params, new PraticaDettaglioForPostMapper());
	}

	public List<PraticaDettaglioForPost> getScadenzeEsistenti(Integer praticaId) {

		var params = new MapSqlParameterSource();
		params.addValue("praticaId", praticaId);
		String sql = SqlStatementPraticaDettaglio.SELECT_SCADENZE_ESISTENTI;
		return query(sql, params, new PraticaDettaglioForPostMapper());

	}

}
