/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.dao.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelStrutturaTipoRidotto;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.PraticaTipoMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper.StrutturaTipoRidottaMapper;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaTipo;
import it.csi.vigilsan.vigilsanpratiche.util.query.SqlStatementPraticaTipo;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class PraticaTipoDao extends AbstractPersistence implements Dao<PraticaTipo> {

	private Integer getId(PraticaTipo t) {
		return t.getPraticaTipoId();
	}

	private void setSpecificParameter(PraticaTipo t, MapSqlParameterSource params) {
		params.addValue("praticaTipoCod", t.getPraticaTipoCod());
		params.addValue("praticaTipoDesc", t.getPraticaTipoDesc());
	}

	@Override
	public PraticaTipo get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPraticaTipo.PH_PK, id);
		String sql = SqlStatementPraticaTipo.SELECT_BY_ID;
		return queryForObject(sql, params, new PraticaTipoMapper());
	}

	@Override
	public List<PraticaTipo> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementPraticaTipo.SELECT_ALL;
		return query(sql, params, new PraticaTipoMapper());
	}

	@Override
	public void insert(PraticaTipo t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPraticaTipo.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (Objects.isNull(getId(t))) {
			sql = SqlStatementPraticaTipo.INSERT_GENERIC;
		} else {
			params.addValue(SqlStatementPraticaTipo.PH_PK, getId(t));
			sql = SqlStatementPraticaTipo.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementPraticaTipo.NEXTVAL_PK_ID;
		return queryForObject(sql, params, Integer.class);
	}

	@Override
	public void update(PraticaTipo t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementPraticaTipo.PH_PK, id);
		setSpecificParameter(t, params);

		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementPraticaTipo.UPDATE_GENERIC;
		update(sql, params);
	}

	@Override
	public void delete(PraticaTipo t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementPraticaTipo.PH_PK, getId(t));
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementPraticaTipo.DELETE;
		update(sql, params);
	}

	public List<PraticaTipo> getPraticaTipoByEnteTipoId(Integer enteTipoId) {
		var params = new MapSqlParameterSource();
		params.addValue("enteTipoId", enteTipoId);
		String sql = SqlStatementPraticaTipo.SELECT_BY_ENTE_TIPO_ID;
		return query(sql, params, new PraticaTipoMapper());
	}

	public List<ModelStrutturaTipoRidotto> getStrutturaRidottaByEnteTipoId(Integer enteTipoId) {
		var params = new MapSqlParameterSource();
		params.addValue("enteTipoId", enteTipoId);
		String sql = SqlStatementPraticaTipo.SELECT_STRUTTURA_TIPO_RIDOTTA_BY_ENTE_TIPO_ID;
		return query(sql, params, new StrutturaTipoRidottaMapper());
	}
	

}
