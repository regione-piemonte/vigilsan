/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.OspiteMovimentoTipoMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.OspiteMovimentoTipo;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.OspiteMovimentoTipoEsteso;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.OspiteMovimentoTipoInterface;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementOspite;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementOspiteMovimentoTipo;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class OspiteMovimentoTipoDao extends AbstractPersistence implements Dao<OspiteMovimentoTipo> {

	private Integer getId(OspiteMovimentoTipo t) {
		return t.getOspiteMovimentoTipoId();
	}

	private void setSpecificParameter(OspiteMovimentoTipo t, MapSqlParameterSource params) {
			params.addValue("ospiteMovimentoTipoCod", t.getOspiteMovimentoTipoCod());
			params.addValue("ospiteMovimentoTipoDesc", t.getOspiteMovimentoTipoDesc());
			params.addValue("ospiteMovimentoTipoOrd", t.getOspiteMovimentoTipoId());
			params.addValue("ospiteMovimentoTipoHint", t.getOspiteMovimentoTipoHint());
			params.addValue("ospiteStatoId", t.getOspiteStatoId());
	}

	@Override
	public OspiteMovimentoTipo get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementOspiteMovimentoTipo.PH_PK, id);
		String sql = SqlStatementOspiteMovimentoTipo.SELECT_BY_ID;
		return queryForObject(sql, params, new OspiteMovimentoTipoMapper<>(OspiteMovimentoTipo.class));
	}
	
	public <T extends OspiteMovimentoTipoInterface> T get(int id, Class<T> clazz) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementOspiteMovimentoTipo.PH_PK, id);
		String sql = SqlStatementOspiteMovimentoTipo.SELECT_BY_ID;
		return queryForObject(sql, params, new OspiteMovimentoTipoMapper<>(clazz));
	}

	@Override
	public List<OspiteMovimentoTipo> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementOspiteMovimentoTipo.SELECT_ALL;
		return query(sql, params, new OspiteMovimentoTipoMapper<>(OspiteMovimentoTipo.class));
	}

	@Override
	public void insert(OspiteMovimentoTipo t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementOspiteMovimentoTipo.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (Objects.isNull(getId(t))) {
			sql = SqlStatementOspiteMovimentoTipo.INSERT_GENERIC;
		} else {
			params.addValue(SqlStatementOspiteMovimentoTipo.PH_PK, getId(t));
			sql = SqlStatementOspiteMovimentoTipo.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementOspiteMovimentoTipo.NEXTVAL_PK_ID;
		return queryForObject(sql, params, Integer.class);
	}

	@Override
	public void update(OspiteMovimentoTipo t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementOspiteMovimentoTipo.PH_PK, id);
		setSpecificParameter(t, params);

		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementOspiteMovimentoTipo.UPDATE_GENERIC;
		update(sql, params);
	}

	@Override
	public void delete(OspiteMovimentoTipo t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementOspiteMovimentoTipo.PH_PK, getId(t));
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementOspiteMovimentoTipo.DELETE;
		update(sql, params);
	}


	public List<OspiteMovimentoTipoEsteso> getOspiteMovimentoTipoAllValid() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementOspite.SELECT_ALL_OSPITE_TIPO_MOVIMENTO_VALID;
		return query(sql, params, new OspiteMovimentoTipoMapper<>(OspiteMovimentoTipoEsteso.class));
	}
}
