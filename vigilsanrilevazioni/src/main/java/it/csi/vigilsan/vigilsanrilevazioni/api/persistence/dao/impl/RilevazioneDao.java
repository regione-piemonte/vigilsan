/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanrilevazioni.api.persistence.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsanrilevazioni.api.generated.dto.ModelRilevazione;
import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.mapper.RilevazioneMapper;
import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.model.Rilevazione;
import it.csi.vigilsan.vigilsanrilevazioni.util.query.SqlStatementRilevazione;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.Dao;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class RilevazioneDao extends AbstractPersistence implements Dao<Rilevazione> {

	@Override
	public Rilevazione get(int id) {

		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementRilevazione.PH_PK, id);
		String sql = SqlStatementRilevazione.SELECT + SqlStatementCommon.WHERE + SqlStatementRilevazione.PK_EQUALS;
		return queryForObject(sql, params, new RilevazioneMapper<>(Rilevazione.class));
	}

	@Override
	public List<Rilevazione> getAll() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementRilevazione.SELECT;
		return query(sql, params, new RilevazioneMapper<>(Rilevazione.class));
	}

	@Override
	public void insert(Rilevazione t) {
		var params = new MapSqlParameterSource();
		params.addValue("strutturaId", t.getStrutturaId());
		params.addValue("strCatId", t.getStrCatId());
		params.addValue("moduloCompilatoId", t.getModuloCompilatoId());
		params.addValue("moduloConfigId", t.getModuloConfigId());
		params.addValue("dataoraRilevazione", t.getDataoraRilevazione());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_UTENTE_CREAZIONE, t.getUtenteCreazione());

		String sql;
		if (t.getRilevazioneId() == null) {
			sql = SqlStatementRilevazione.INSERT;
		} else {
			params.addValue(SqlStatementRilevazione.PH_PK, t.getRilevazioneId());
			sql = SqlStatementRilevazione.INSERT_W_PK;
		}
		update(sql, params);

	}

	public Integer getSequence() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementRilevazione.NEXTVAL_PK_ID;
		return queryForObject(sql, params, Integer.class);
	}

	@Override
	public void update(Rilevazione t, int id) {
		var params = new MapSqlParameterSource();

		params.addValue(SqlStatementRilevazione.PH_PK, id);
		params.addValue("strutturaId", t.getStrutturaId());
		params.addValue("strCatId", t.getStrCatId());
		params.addValue("moduloCompilatoId", t.getModuloCompilatoId());
		params.addValue("moduloConfigId", t.getModuloConfigId());
		params.addValue("dataoraRilevazione", t.getDataoraRilevazione());
		params.addValue(SqlStatementCommon.PH_UTENTE_MODIFICA, t.getUtenteModifica());
		params.addValue(SqlStatementCommon.PH_VALIDITA_FINE, t.getValiditaFine());
		params.addValue(SqlStatementCommon.PH_VALIDITA_INIZIO, t.getValiditaInizio());

		String sql = SqlStatementRilevazione.UPDATE + SqlStatementCommon.SET + SqlStatementRilevazione.UPDATE_CAMPI
				+ SqlStatementCommon.WHERE + SqlStatementRilevazione.PK_EQUALS;
		update(sql, params);
	}

	@Override
	public void delete(Rilevazione t) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementRilevazione.PH_PK, t.getRilevazioneId());
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, t.getUtenteCancellazione());
		String sql = SqlStatementRilevazione.UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE
				+ SqlStatementRilevazione.PK_EQUALS;
		update(sql, params);
	}

	public void deleteOldRilevazioni(ModelRilevazione body, String shibIdentitaCodiceFiscale) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementCommon.PH_UTENTE_CANCELLAZIONE, shibIdentitaCodiceFiscale);
		params.addValue("moduloConfigId", body.getModuloConfigId());
		params.addValue("strutturaId", body.getStrutturaId());
		params.addValue("strCatId", body.getStrCatId());
		params.addValue("dataoraRilevazione", body.getDataoraRilevazione());
		String sql = SqlStatementRilevazione.DELETE_OLD_RILEVAZIONI;
		update(sql, params);
	}
}
