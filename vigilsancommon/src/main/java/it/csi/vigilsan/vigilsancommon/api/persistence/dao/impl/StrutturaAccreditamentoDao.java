/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.StrutturaAccreditamentoMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.StrutturaAccreditamento;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementStrutturaAccreditamento;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class StrutturaAccreditamentoDao extends AbstractPersistence implements Dao<StrutturaAccreditamento> {

	@Override
	public StrutturaAccreditamento get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementStrutturaAccreditamento.PH_PK, id);
		String sql = SqlStatementStrutturaAccreditamento.SELECT + SqlStatementCommon.WHERE
				+ SqlStatementStrutturaAccreditamento.PK_EQUALS;
		return queryForObject(sql, params, new StrutturaAccreditamentoMapper());
	}

	@Override
	public List<StrutturaAccreditamento> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementStrutturaAccreditamento.SELECT;
		return query(sql, params, new StrutturaAccreditamentoMapper());
	}
	
	@Override
	public void insert(StrutturaAccreditamento t) {
		var params = new MapSqlParameterSource();

		params.addValue("strutturaAccreditamentoId", t.getStrutturaAccreditamentoId());
		params.addValue("strutturaAccreditamentoCod", t.getStrutturaAccreditamentoCod());
		params.addValue("strutturaAccreditamentoCodArpe", t.getStrutturaAccreditamentoCodArpe());
		params.addValue("strutturaAccreditamentoDesc", t.getStrutturaAccreditamentoDesc());
		
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (t.getStrutturaAccreditamentoId() == null) {
			sql = SqlStatementStrutturaAccreditamento.INSERT;
		} else {
			params.addValue(SqlStatementStrutturaAccreditamento.PH_PK, t.getStrutturaAccreditamentoId());
			sql = SqlStatementStrutturaAccreditamento.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementStrutturaAccreditamento.NEXTVAL_PK_ID;
		return queryForObject(sql, params,Integer.class);
	}

	@Override
	public void update(StrutturaAccreditamento t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementStrutturaAccreditamento.PH_PK, id);
		params.addValue("strutturaAccreditamentoCod", t.getStrutturaAccreditamentoCod());
		params.addValue("strutturaAccreditamentoCodArpe", t.getStrutturaAccreditamentoCodArpe());
		params.addValue("strutturaAccreditamentoDesc", t.getStrutturaAccreditamentoDesc());
		
		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementStrutturaAccreditamento.UPDATE + SqlStatementCommon.SET
				+ SqlStatementStrutturaAccreditamento.UPDATE_CAMPI + SqlStatementCommon.WHERE
				+ SqlStatementStrutturaAccreditamento.PK_EQUALS;
		update(sql, params);
	}

	@Override
	public void delete(StrutturaAccreditamento t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementStrutturaAccreditamento.PH_PK, t.getStrutturaAccreditamentoId());
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementStrutturaAccreditamento.UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE
				+ SqlStatementStrutturaAccreditamento.PK_EQUALS;
		update(sql, params);
	}
	
	public StrutturaAccreditamento getStrutturaAccreditamentoByCodArpe(String inCodArpe) {
		var params = new MapSqlParameterSource();

		params.addValue("cod_arpe", inCodArpe);
		String sql = SqlStatementStrutturaAccreditamento.SELECT_STRUTTURA_ACCREDITAMENTO_BY_STRUTTURA_ACCREDITAMENTO_COD_ARPE;
		return queryForObject(sql, params, new StrutturaAccreditamentoMapper());

	}
	
}
