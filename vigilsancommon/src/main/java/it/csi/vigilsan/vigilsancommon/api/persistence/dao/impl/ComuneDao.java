/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.ComuneMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.Comune;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.ComuneInterface;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementComune;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementNatura;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class ComuneDao extends AbstractPersistence implements Dao<Comune> {

	@Override
	public Comune get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementComune.PH_PK, id);
		String sql = SqlStatementComune.SELECT + SqlStatementCommon.WHERE
				+ SqlStatementComune.PK_EQUALS;
		return queryForObject(sql, params, new ComuneMapper<>(Comune.class));
	}
	
	public <T extends ComuneInterface> T get(int id, Class<T> clazz) {

		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementComune.PH_PK, id);
		String sql = SqlStatementComune.SELECT + SqlStatementCommon.WHERE
				+ SqlStatementComune.PK_EQUALS;
		return queryForObject(sql, params, new ComuneMapper<>(clazz));
	}

	@Override
	public List<Comune> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementComune.SELECT;
		return query(sql, params, new ComuneMapper<>(Comune.class));
	}

	@Override
	public void insert(Comune t) {
		var params = new MapSqlParameterSource();

		params.addValue("comuneCod", t.getComuneCod());
		params.addValue("comuneDesc", t.getComuneDesc());
		params.addValue("provinciaId", t.getProvinciaId());
		
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (t.getComuneId() == null) {
			sql = SqlStatementComune.INSERT;
		} else {
			params.addValue(SqlStatementNatura.PH_PK, t.getComuneId());
			sql = SqlStatementComune.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementComune.NEXTVAL_PK_ID;
		return queryForObject(sql, params,Integer.class);
	}

	@Override
	public void update(Comune t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementComune.PH_PK, id);
		params.addValue("comuneCod", t.getComuneCod());
		params.addValue("comuneDesc", t.getComuneDesc());
		params.addValue("provinciaId", t.getProvinciaId());
		
		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementComune.UPDATE + SqlStatementCommon.SET
				+ SqlStatementComune.UPDATE_CAMPI + SqlStatementCommon.WHERE
				+ SqlStatementComune.PK_EQUALS;
		update(sql, params);
	}

	@Override
	public void delete(Comune t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementComune.PH_PK, t.getComuneId());
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementComune.UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE
				+ SqlStatementComune.PK_EQUALS;
		update(sql, params);

	}
	
	// vigil_t_comune
	public Comune getComuneByCod(String inComuneCod) {
		var params = new MapSqlParameterSource();

		params.addValue("comune_cod", inComuneCod);
		String sql = SqlStatementComune.SELECT_COMUNE_BY_COMUNE_COD;
		return queryForObject(sql, params, new ComuneMapper<>(Comune.class));

	}
	
}
