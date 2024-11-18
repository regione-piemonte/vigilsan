/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.api.persistence.repository.impl;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsanmoduli.api.generated.dto.ModelChiaveValore;
import it.csi.vigilsan.vigilsanmoduli.api.generated.dto.ModelModulo;
import it.csi.vigilsan.vigilsanmoduli.api.generated.dto.ModelModuloRegolaRidotto;
import it.csi.vigilsan.vigilsanmoduli.api.generated.dto.ModelVoce;
import it.csi.vigilsan.vigilsanmoduli.api.generated.dto.ModelVoceLista;
import it.csi.vigilsan.vigilsanmoduli.api.generated.dto.ModelVoceListaValore;
import it.csi.vigilsan.vigilsanmoduli.api.generated.dto.ModelVoceRegola;
import it.csi.vigilsan.vigilsanmoduli.api.persistence.mapper.ModelChiaveValoreMapper;
import it.csi.vigilsan.vigilsanmoduli.api.persistence.mapper.ModelModuloRegolaRidottoMapper;
import it.csi.vigilsan.vigilsanmoduli.api.persistence.mapper.ModuloMapper;
import it.csi.vigilsan.vigilsanmoduli.api.persistence.mapper.VoceListaMapper;
import it.csi.vigilsan.vigilsanmoduli.api.persistence.mapper.VoceListaValoreMapper;
import it.csi.vigilsan.vigilsanmoduli.api.persistence.mapper.VoceMapper;
import it.csi.vigilsan.vigilsanmoduli.api.persistence.mapper.VoceRegolaMapper;
import it.csi.vigilsan.vigilsanmoduli.util.query.SqlStatementModulo;
import it.csi.vigilsan.vigilsanmoduli.util.query.SqlStatementModuloCompilato;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.StringMapper;

@Repository
public class ModuloRepository extends AbstractPersistence {

	public <T extends ModelModulo> T getModulo(Integer id, Class<T> clazz) {

		var params = new MapSqlParameterSource();

		params.addValue("moduloId", id);
		String sql = SqlStatementModulo.SELECT_MODULO;
		return queryForObject(sql, params, new ModuloMapper<>(clazz));
	}

	public List<ModelModulo> getSezioniModulo(long id) {
		var params = new MapSqlParameterSource();

		params.addValue("moduloId", id);
		String sql = SqlStatementModulo.SELECT_SEZIONI_MODULO;
		return query(sql, params, new ModuloMapper<>(ModelModulo.class));
	}

	public List<ModelVoce> getModuloVoceByModuloId(Integer moduloId, Integer moduloCompilatoId, Boolean isRidotto) {
		var params = new MapSqlParameterSource();

		params.addValue("moduloId", moduloId);
		params.addValue("moduloCompilatoId", moduloCompilatoId);
		String sql;
		if (Boolean.TRUE.equals(isRidotto)) {
			sql = SqlStatementModulo.SELECT_MODULO_VOCE_RIDOTTA;
		} else {
			sql = SqlStatementModulo.SELECT_MODULO_VOCE_INTERA;
		}
		return query(sql, params, new VoceMapper());
	}

	public ModelVoceLista getModuloListaFromModuloListaId(long id) {
		var params = new MapSqlParameterSource();
		params.addValue("moduloListaId", id);
		String sql = SqlStatementModulo.SELECT_MODULO_LISTA_BY_MODULO_LISTA_ID;
		return queryForObject(sql, params, new VoceListaMapper());
	}

	public List<ModelVoceListaValore> getModuloListaValoreFromModuloListaId(Integer moduloVoceId,
			Integer moduloCompilatoId) {
		var params = new MapSqlParameterSource();
		params.addValue("moduloVoceId", moduloVoceId);
		params.addValue("moduloCompilatoId", moduloCompilatoId);
		String sql = SqlStatementModulo.SELECT_MODULO_LISTA_VALORE_BY_MODULO_LISTA_ID;
		return query(sql, params, new VoceListaValoreMapper());
	}

	public List<ModelVoceRegola> getModuloVoceRegolaByModuloVoceRegolaId(long id) {
		var params = new MapSqlParameterSource();
		params.addValue("moduloVoceRegolaId", id);
		String sql = SqlStatementModulo.SELECT_MODULO_VOCE_REGOLA_BY_ID;
		return query(sql, params, new VoceRegolaMapper());
	}

	public List<ModelModuloRegolaRidotto> getModuloRegolaByModuloId(Integer id) {
		var params = new MapSqlParameterSource();
		params.addValue("moduloId", id);
		String sql = SqlStatementModulo.SELECT_MODULO_REGOLA_BY_MODULO_ID;
		return query(sql, params, new ModelModuloRegolaRidottoMapper());
	}

	public Integer getSequenceModuloCompilatoId() {
		var params = new MapSqlParameterSource();
		String sql = SqlStatementModuloCompilato.NEXTVAL_MODULO_COMPILATO_ID;
		return queryForObject(sql, params, Integer.class);
	}

	public List<String> getModuloCompilatoVoceNota(Integer moduloCompilatoId, Integer moduloVoceId) {
		var params = new MapSqlParameterSource();
		params.addValue("moduloCompilatoId", moduloCompilatoId);
		params.addValue("moduloVoceId", moduloVoceId);
		String sql = SqlStatementModuloCompilato.SELECT_NOTE_MODULO_COMPLIATO;
		return query(sql, params, new StringMapper("note"));
	}

	public List<ModelChiaveValore> selectPlaceHolderModulo(Integer moduloId) {
		var params = new MapSqlParameterSource();
		params.addValue("moduloId", moduloId);
		String sql = SqlStatementModuloCompilato.SELECT_PLACE_HOLDER_MODULO;
		return query(sql, params, new ModelChiaveValoreMapper());
	}

}
