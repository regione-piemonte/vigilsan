/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.api.persistence.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsanmoduli.api.persistence.mapper.FileTipoMapper;
import it.csi.vigilsan.vigilsanmoduli.api.persistence.model.FileTipo;
import it.csi.vigilsan.vigilsanmoduli.util.query.SqlStatementFileTipo;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class FileTipoDao extends AbstractPersistence implements Dao<FileTipo> {

	@Override
	public FileTipo get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementFileTipo.PH_PK, id);
		String sql = SqlStatementFileTipo.SELECT + SqlStatementCommon.WHERE
				+ SqlStatementFileTipo.PK_EQUALS;
		return queryForObject(sql, params, new FileTipoMapper());
	}

	@Override
	public List<FileTipo> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementFileTipo.SELECT;
		return query(sql, params, new FileTipoMapper());
	}

	@Override
	public void insert(FileTipo t) {
		var params = new MapSqlParameterSource();

		params.addValue("fileTipoCod", t.getFileTipoCod());
		params.addValue("fileTipoDesc", t.getFileTipoDesc());
		params.addValue("fileTipoOrd", t.getFileTipoOrd());
		params.addValue("fileTipoHint", t.getFileTipoHint());
		params.addValue("fileTipoObbligatorio", t.getFileTipoObbligatorio());
		params.addValue("fileTipoFirmaRichiesta", t.getFileTipoFirmaRichiesta());
		params.addValue("fileGruppoId", t.getFileGruppoId());

		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (t.getFileTipoId() == null) {
			sql = SqlStatementFileTipo.INSERT;
		} else {
			params.addValue("fileTipoId", t.getFileTipoId());
			sql = SqlStatementFileTipo.INSERT_W_PK;

		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementFileTipo.NEXTVAL_PK_ID;
		return queryForObject(sql, params,Integer.class);
	}

	@Override
	public void update(FileTipo t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementFileTipo.PH_PK, id);
		params.addValue("fileTipoId", t.getFileTipoId());
		params.addValue("fileTipoCod", t.getFileTipoCod());
		params.addValue("fileTipoDesc", t.getFileTipoDesc());
		params.addValue("fileTipoOrd", t.getFileTipoOrd());
		params.addValue("fileTipoHint", t.getFileTipoHint());
		params.addValue("fileTipoObbligatorio", t.getFileTipoObbligatorio());
		params.addValue("fileTipoFirmaRichiesta", t.getFileTipoFirmaRichiesta());
		params.addValue("fileGruppoId", t.getFileGruppoId());
		
		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementFileTipo.UPDATE + SqlStatementCommon.SET
				+ SqlStatementFileTipo.UPDATE_CAMPI + SqlStatementCommon.WHERE
				+ SqlStatementFileTipo.PK_EQUALS;
		update(sql, params);
	}

	@Override
	public void delete(FileTipo t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementFileTipo.PH_PK, t.getFileTipoId());
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementFileTipo.UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE
				+ SqlStatementFileTipo.PK_EQUALS;
		update(sql, params);

	}

	public FileTipo getByCod(String fileTipoCod) {

		var params = new MapSqlParameterSource();

		params.addValue("fileTipoCod", fileTipoCod);
		String sql = SqlStatementFileTipo.SELECT_BY_COD;
		return queryForObject(sql, params, new FileTipoMapper());
	}
}
