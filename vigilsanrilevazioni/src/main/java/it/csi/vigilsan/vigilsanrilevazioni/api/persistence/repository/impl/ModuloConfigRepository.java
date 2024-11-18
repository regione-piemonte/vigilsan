/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanrilevazioni.api.persistence.repository.impl;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.mapper.ModuloConfigRidottoMapper;
import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.model.ModuloConfigRidotto;
import it.csi.vigilsan.vigilsanrilevazioni.util.query.SqlStatementDocumentoRilevazione;
import it.csi.vigilsan.vigilsanrilevazioni.util.query.SqlStatementModuloConfig;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.StringMapper;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

@Repository
public class ModuloConfigRepository extends AbstractPersistence {

	public Boolean existModuloConfigByModuloConfigId(Integer moduloConfigId) {

		var params = new MapSqlParameterSource();

		params.addValue("moduloConfigId", moduloConfigId);
		String sql = SqlStatementDocumentoRilevazione.EXIST_MODULO_CONFIG_BY_MODULO_CONFIG_ID;
		return queryForObject(sql, params, Boolean.class);

	}
	


	public List<ModuloConfigRidotto> getRilevazioniModuliDisponibili(Integer strutturaId, String moduloConfigGruppoCod) {

		var params = new MapSqlParameterSource();
		params.addValue("strutturaId", strutturaId);
		params.addValue("moduloConfigGruppoCod", moduloConfigGruppoCod);
		
		String sql = SqlStatementDocumentoRilevazione.SELECT_MODULO_RIDOTTO_RILEVAZIONE;
		return query(sql, params, new ModuloConfigRidottoMapper());
	}


	public List<ModuloConfigRidotto> getDocumentazioneModuliDisponibili(Integer strutturaId, String moduloConfigGruppoCod) {

		var params = new MapSqlParameterSource();
		params.addValue("strutturaId", strutturaId);
		params.addValue("moduloConfigGruppoCod", moduloConfigGruppoCod);
		
		String sql = SqlStatementDocumentoRilevazione.SELECT_MODULO_RIDOTTO_DOCUMENTAZIONE;
		return query(sql, params, new ModuloConfigRidottoMapper());
	}

	public List<String> getDistincModuloConfigRilevazioniNonCancellati()  {

		var params = new MapSqlParameterSource();

		String sql = String.format(SqlStatementModuloConfig.SELECT_DISTICNT_NOT_DELETED,SqlStatementCommon.IS_NOT_NULL);
		return query(sql, params, new StringMapper("modulo_config_cod"));

	}

	public List<String> getDistincModuloConfigDocumentazioniNonCancellati()  {

		var params = new MapSqlParameterSource();

		String sql = String.format(SqlStatementModuloConfig.SELECT_DISTICNT_NOT_DELETED,SqlStatementCommon.IS_NULL);
		return query(sql, params, new StringMapper("modulo_config_cod"));

	}
	public Boolean existModuloConfigByModuloConfigCod(String moduloConfigCod)  {

		var params = new MapSqlParameterSource();

		params.addValue("moduloConfigCod", moduloConfigCod);
		String sql = SqlStatementDocumentoRilevazione.EXIST_MODULO_CONFIG_BY_MODULO_CONFIG_COD;
		return queryForObject(sql, params, Boolean.class);

	}
}
