/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.OspiteMovimentoMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.OspiteMovimento;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.OspiteMovimentoEsteso;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementOspiteMovimento;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementSoggetto;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementStruttura;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementStrutturaSoggetto;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class OspiteMovimentoDao extends AbstractPersistence implements Dao<OspiteMovimento> {

	private Integer getId(OspiteMovimento t) {
		return t.getOspiteMovimentoId();
	}

	private void setSpecificParameter(OspiteMovimento t, MapSqlParameterSource params) {
			params.addValue("dataMovimento", t.getDataMovimento());
			params.addValue("strSoggId", t.getStrSoggId());
			params.addValue("ospiteMovimentoTipoId", t.getOspiteMovimentoTipoId());
			params.addValue("ospiteCondizioniId", t.getOspiteCondizioniId());
			params.addValue("strutturaIdOd", t.getStrutturaIdOd());
			params.addValue("note", t.getNote());
			params.addValue("strutturaCategoriaId", t.getStrutturaCategoriaId());
	}

	@Override
	public OspiteMovimento get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementOspiteMovimento.PH_PK, id);
		String sql = SqlStatementOspiteMovimento.SELECT + SqlStatementCommon.WHERE + SqlStatementOspiteMovimento.PK_EQUALS;
		return queryForObject(sql, params, new OspiteMovimentoMapper<>(OspiteMovimento.class));
	}

	@Override
	public List<OspiteMovimento> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementOspiteMovimento.SELECT + SqlStatementCommon.WHERE + SqlStatementOspiteMovimento.VALIDO;
		return query(sql, params, new OspiteMovimentoMapper<>(OspiteMovimento.class));
	}

	@Override
	public void insert(OspiteMovimento t) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementOspiteMovimento.PH_PK, getId(t));
		setSpecificParameter(t, params);
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (Objects.isNull(getId(t))) {
			sql = SqlStatementOspiteMovimento.INSERT;
		} else {
			params.addValue(SqlStatementOspiteMovimento.PH_PK, getId(t));
			sql = SqlStatementOspiteMovimento.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementOspiteMovimento.NEXTVAL_PK_ID;
		return queryForObject(sql, params, Integer.class);
	}

	@Override
	public void update(OspiteMovimento t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementOspiteMovimento.PH_PK, id);
		setSpecificParameter(t, params);

		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementOspiteMovimento.UPDATE + SqlStatementCommon.SET + SqlStatementOspiteMovimento.UPDATE_CAMPI
				+ SqlStatementCommon.WHERE + SqlStatementOspiteMovimento.PK_EQUALS;
		update(sql, params);
	}

	@Override
	public void delete(OspiteMovimento t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementOspiteMovimento.PH_PK, getId(t));
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementOspiteMovimento.UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE
				+ SqlStatementOspiteMovimento.PK_EQUALS;
		update(sql, params);
	}

	public List<OspiteMovimentoEsteso> getBySoggettoIdAndStrutturaIdValido(Integer strutturaId, Integer soggettoId) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementStruttura.PH_PK, strutturaId);
		params.addValue(SqlStatementSoggetto.PH_PK, soggettoId);
		String sql = SqlStatementOspiteMovimento.SELECT_BY_SOGGETTO_ID_STRUTTURA_ID_VALIDI;
		return query(sql, params, new OspiteMovimentoMapper<>(OspiteMovimentoEsteso.class));
	}
	
	public List<OspiteMovimento> getByStrSoggIdValidi(Integer strSoggId) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementStrutturaSoggetto.PH_PK, strSoggId);
		String sql = SqlStatementOspiteMovimento.SELECT_BY_STR_SOGG_ID_VALIDO;
		return query(sql, params, new OspiteMovimentoMapper<>(OspiteMovimento.class));
	}

}
