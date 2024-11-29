/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.repository;

import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsancommon.api.generated.dto.ModelOspiteMovimentoTipoConfig;
import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.OspiteMovimentoForModificaMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.mapper.OspiteMovimentoTipoConfigMapper;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.OspiteMovimentoForModifica;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementOspite;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementSoggetto;
import it.csi.vigilsan.vigilsancommon.util.query.SqlStatementStruttura;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;

@Repository
public class OspiteRepository extends AbstractPersistence {

	public List<ModelOspiteMovimentoTipoConfig> getOspiteMovimentoTipoConfigByOspiteMovimentoTipoIdValid(
			Integer ospiteMovimentoTipoId) {
		var params = new MapSqlParameterSource();
		params.addValue("ospiteMovimentoTipoId", ospiteMovimentoTipoId);
		String sql = SqlStatementOspite.SELECT_OSPITE_MOVIMENTO_TIPO_CONFIG_BY_OSP_MOV_TIPO_ID_VALID;
		return query(sql, params, new OspiteMovimentoTipoConfigMapper());
	}

	public Integer getRuoloStrutturaIdByRuoloStrutturaCod(String ruoloStrutturaCod) {
		var params = new MapSqlParameterSource();
		params.addValue("ruoloStrutturaCod", ruoloStrutturaCod);
		String sql = SqlStatementOspite.SELECT_RUOLO_STRUTTURA_SOGGETTO_ID_BY_RUOLO_STRUTTURA_COD;
		return queryForObject(sql, params, Integer.class);
	}

	public List<OspiteMovimentoForModifica> getListaOspiteMovimentoIdFromDataMovimentoAndStrIdAndSoggId(
			Date newDataMovimento, Integer soggettoId, Integer strutturaId, Integer newOspiteMovimentoTipoId) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementStruttura.PH_PK, strutturaId);
		params.addValue(SqlStatementSoggetto.PH_PK, soggettoId);
		params.addValue("newDataMovimento", newDataMovimento);
		params.addValue("newOspiteMovimentoTipoId", newOspiteMovimentoTipoId);

		String sql = SqlStatementOspite.SELECT_OSPITI_MOVIMENTI_TIPI_AGGIUNGERE_MOVIMENTO;
		return query(sql, params, new OspiteMovimentoForModificaMapper());
	}

	public List<OspiteMovimentoForModifica> getListaOspiteMovimentoIdForDelete(Integer oldOspiteMovimentoId,
			Integer soggettoId, Integer strutturaId) {
		var params = new MapSqlParameterSource();
		params.addValue(SqlStatementStruttura.PH_PK, strutturaId);
		params.addValue(SqlStatementSoggetto.PH_PK, soggettoId);
		params.addValue("oldOspiteMovimentoId", oldOspiteMovimentoId);

		String sql = SqlStatementOspite.SELECT_OSPITI_MOVIMENTI_TIPI_RIMUOVERE_MOVIMENTO;
		return query(sql, params, new OspiteMovimentoForModificaMapper());
	}

}
