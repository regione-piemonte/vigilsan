/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.dao.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelPraticaRequisiti;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.ChecklistReqEstesoMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.ChecklistReqMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.PraticaRequisitiMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.ChecklistReq;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.ChecklistReqEsteso;
import it.csi.vigilsan.vigilsanpratiche.util.query.SqlStatementChecklistReq;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class ChecklistReqDao extends AbstractPersistence implements Dao<ChecklistReq> {

	private Integer getId(ChecklistReq t) {
		return t.getClreqId();
	}

	private void setSpecificParameter(ChecklistReq t, MapSqlParameterSource params) {
		params.addValue("clreqCod", t.getClreqCod());
		params.addValue("clreqDesc", t.getClreqDesc());
		params.addValue("clreqOrd", t.getClreqOrd());
		params.addValue("clreqHint", t.getClreqHint());
		params.addValue("clreqIdPadre", t.getClreqIdPadre());
		params.addValue("moduloId", t.getModuloId());
	}

	@Override
	public ChecklistReq get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementChecklistReq.PH_PK, id);
		String sql = SqlStatementChecklistReq.SELECT_BY_ID;
		return queryForObject(sql, params, new ChecklistReqMapper<>(ChecklistReq.class));
	}

	@Override
	public List<ChecklistReq> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementChecklistReq.SELECT_ALL;
		return query(sql, params, new ChecklistReqMapper<>(ChecklistReq.class));
	}

	@Override
	public void insert(ChecklistReq t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementChecklistReq.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (Objects.isNull(getId(t))) {
			sql = SqlStatementChecklistReq.INSERT_GENERIC;
		} else {
			params.addValue(SqlStatementChecklistReq.PH_PK, getId(t));
			sql = SqlStatementChecklistReq.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementChecklistReq.NEXTVAL_PK_ID;
		return queryForObject(sql, params, Integer.class);
	}

	@Override
	public void update(ChecklistReq t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementChecklistReq.PH_PK, id);
		setSpecificParameter(t, params);

		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementChecklistReq.UPDATE_GENERIC;
		update(sql, params);
	}

	@Override
	public void delete(ChecklistReq t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementChecklistReq.PH_PK, getId(t));
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementChecklistReq.DELETE;
		update(sql, params);
	}

	public List<ChecklistReqEsteso> getFunctionByPraticaId(Integer praticaId, Integer prescrizioneId,
			Integer praticaDetId) {
		var params = new MapSqlParameterSource();

		StringBuilder sql = new StringBuilder();
		if (Objects.nonNull(praticaDetId)) {
			params.addValue("praticaDetId", praticaDetId);
			sql.append(SqlStatementChecklistReq.SELECT_ESTESO_BY_PRATICA_WHERE_PRATICA_DET);
		} else if (Objects.nonNull(prescrizioneId)) {
			params.addValue("prescrizioneId", prescrizioneId);
			sql.append(SqlStatementChecklistReq.SELECT_ESTESO_BY_PRATICA_WHERE_PRESCRIZIONE);
		} else {
			params.addValue("praticaId", praticaId);
			sql.append(SqlStatementChecklistReq.SELECT_ESTESO_BY_PRATICA_WHERE_PRATICA);
		}

		sql.append(SqlStatementChecklistReq.SELECT_ESTESO_BY_PRATICA_END);

		return query(sql.toString(), params, new ChecklistReqEstesoMapper());
	}

	public ModelPraticaRequisiti getPraticaRequisito(Integer praticaId, Integer prescrizioneId, Integer praticaDetId,
			Integer clreqId) {
		var params = new MapSqlParameterSource();
		params.addValue("clreqId", clreqId);

		StringBuilder sql = new StringBuilder();
		if (Objects.nonNull(praticaDetId)) {
			params.addValue("praticaDetId", praticaDetId);
			sql.append(SqlStatementChecklistReq.SELECT_PRATICA_REQUISITO_BY_PRATICA_WHERE_PRATICA_DET);
		} else if (Objects.nonNull(prescrizioneId)) {
			params.addValue("prescrizioneId", prescrizioneId);
			sql.append(SqlStatementChecklistReq.SELECT_PRATICA_REQUISITO_ESTESO_BY_PRATICA_WHERE_PRESCRIZIONE);
		} else {
			params.addValue("praticaId", praticaId);
			sql.append(SqlStatementChecklistReq.SELECT_PRATICA_REQUISITO_ESTESO_BY_PRATICA_WHERE_PRATICA);
		}

		sql.append(SqlStatementChecklistReq.SELECT_PRATICA_REQUISITO_ESTESO_BY_PRATICA_END);

		return queryForObject(sql.toString(), params, new PraticaRequisitiMapper());
	}

}
