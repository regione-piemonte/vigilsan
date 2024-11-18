/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanrilevazioni.api.persistence.repository.impl;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.mapper.ModelParametroMapper;
import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.mapper.ModuloVoceColumnsMapper;
import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.model.ModuloVoceColumns;
import it.csi.vigilsan.vigilsanrilevazioni.util.query.SqlStatementDocumentazione;
import it.csi.vigilsan.vigilsanrilevazioni.util.query.SqlStatementDocumentoRilevazione;
import it.csi.vigilsan.vigilsanrilevazioni.util.query.SqlStatementRilevazione;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.PersistenceUtil;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;
import it.csi.vigilsan.vigilsanutil.generic.util.csv.CSVutils;

@Repository
public class RilevazioneDocumentazioneCsvRepository extends AbstractPersistence {

	private static final String SPECIAL_VALUE_EXISTS = "exists";
	private static final String VIGIL_V_DWH_EXPORT = "v_dwh_";

	public ByteArrayOutputStream selectRilevazioneCompilataListaCsvGet(String moduloConfigCod, Integer strutturaId,
			Integer enteId, String dataRilevazioneDa, String dataRilevazioneA, String strutturaCategoriaCod) {
		moduloConfigCod = PersistenceUtil.sanitizeInput(moduloConfigCod);

		String sql = generateSqlForPivotModuloRilevazione(moduloConfigCod,
				SqlStatementDocumentoRilevazione.SQL_PIVOT_RENDICONTAZIONI_CAMPI_CSV, "",
				generateStringSqlQuery1ForRilevazionePivot(moduloConfigCod, strutturaId, enteId, dataRilevazioneDa,
						dataRilevazioneA, strutturaCategoriaCod,
						SqlStatementDocumentoRilevazione.SQL_PIVOT_RENDICONTAZIONI_QUERY_1_CAMPI_CSV));

		var params = new MapSqlParameterSource();

		// ritorna byteArray con csv all'interno
		return CSVutils.generateCsvFromRowSet(queryForSqlRowSet(sql, params));
	}

	public void selectModuliDocumentazione(String moduloConfigCod, Integer strutturaId, Integer enteId,
			String dataRilevazioneDa, String dataRilevazioneA, String strutturaCategoriaCod,
			Map<String, List<String>> result) {
		moduloConfigCod = PersistenceUtil.sanitizeInput(moduloConfigCod);

		String sql = String.format(SqlStatementDocumentoRilevazione.SQL_PIVOT_WRAP_DOCUMENTAZIONE_PER_PARAMETRI,
				generateSqlForPivotModuloRilevazione(moduloConfigCod,
						SqlStatementDocumentoRilevazione.SQL_PIVOT_DOCUMENTAZIONI_CAMPI_CSV,
						SqlStatementDocumentoRilevazione.SQL_PIVOT_RENDICONTAZIONE_COUNT_NOTE,
						generateStringSqlQuery1ForDocumentazionePivot(moduloConfigCod, strutturaId, enteId,
								dataRilevazioneDa, dataRilevazioneA, strutturaCategoriaCod,
								SqlStatementDocumentoRilevazione.SQL_PIVOT_DOCUMENTAZIONE_QUERY_1_CAMPI_VIEW)));

		var params = new MapSqlParameterSource();

		List<Map<String, Object>> rows = template.queryForList(sql, params);

		// Converti i risultati in formato JSON
		rowsToJson(rows, result);
	}

	public static void rowsToJson(List<Map<String, Object>> rows, Map<String, List<String>> result) {
		// Se ci sono risultati
		if (!rows.isEmpty()) {
			// Itera su ogni riga e aggiungi i valori alle liste appropriate
			for (Map<String, Object> row : rows) {
				for (Map.Entry<String, Object> entry : row.entrySet()) {
					if (result.containsKey(entry.getKey())) {
						Object value = entry.getValue();
						if (value != null)
							result.get(entry.getKey()).add(value.toString());

					}
				}
			}
			if (result.keySet().contains(SPECIAL_VALUE_EXISTS)) {
				result.get(SPECIAL_VALUE_EXISTS).add(Boolean.TRUE.toString());
			}
		} else {
			if (result.keySet().contains(SPECIAL_VALUE_EXISTS)) {
				result.get(SPECIAL_VALUE_EXISTS).add(Boolean.FALSE.toString());
			}
		}
	}

	public void creaViewDvhExportModuliRilevazione(String moduloConfigCod, Integer strutturaId, Integer enteId,
			String dataRilevazioneDa, String dataRilevazioneA, String strutturaCategoriaCod) {
		moduloConfigCod = PersistenceUtil.sanitizeInput(moduloConfigCod);

		String nomeView = VIGIL_V_DWH_EXPORT + moduloConfigCod;
		String sql = String.format(SqlStatementDocumentoRilevazione.SQL_PIVOT_RENDICONTAZIONI_CREA_VIEW, nomeView,
				generateSqlForPivotModuloRilevazione(moduloConfigCod,
						SqlStatementDocumentoRilevazione.SQL_PIVOT_RENDICONTAZIONI_CAMPI_VIEW,
						SqlStatementDocumentoRilevazione.SQL_PIVOT_RENDICONTAZIONE_COUNT_NOTE,
						generateStringSqlQuery1ForRilevazionePivot(moduloConfigCod, strutturaId, enteId,
								dataRilevazioneDa, dataRilevazioneA, strutturaCategoriaCod,
								SqlStatementDocumentoRilevazione.SQL_PIVOT_RENDICONTAZIONI_QUERY_1_CAMPI_VIEW)));

		var params = new MapSqlParameterSource();

		update(sql, params);
	}

	public void creaViewDvhExportModuliDocumentazione(String moduloConfigCod, Integer strutturaId, Integer enteId,
			String dataRilevazioneDa, String dataRilevazioneA, String strutturaCategoriaCod) {
		moduloConfigCod = PersistenceUtil.sanitizeInput(moduloConfigCod);

		String nomeView = VIGIL_V_DWH_EXPORT + moduloConfigCod;
		String sql = String.format(SqlStatementDocumentoRilevazione.SQL_PIVOT_RENDICONTAZIONI_CREA_VIEW, nomeView,
				generateSqlForPivotModuloRilevazione(moduloConfigCod,
						SqlStatementDocumentoRilevazione.SQL_PIVOT_DOCUMENTAZIONI_CAMPI_VIEW,
						SqlStatementDocumentoRilevazione.SQL_PIVOT_RENDICONTAZIONE_COUNT_NOTE,
						generateStringSqlQuery1ForDocumentazionePivot(moduloConfigCod, strutturaId, enteId,
								dataRilevazioneDa, dataRilevazioneA, strutturaCategoriaCod,
								SqlStatementDocumentoRilevazione.SQL_PIVOT_DOCUMENTAZIONE_QUERY_1_CAMPI_VIEW)));

		var params = new MapSqlParameterSource();

		update(sql, params);
	}

	public void dropViewDvhExportModuli(String moduloConfigCod) {
		moduloConfigCod = PersistenceUtil.sanitizeInput(moduloConfigCod);

		String nomeView = VIGIL_V_DWH_EXPORT + moduloConfigCod;
		String sql = String.format(SqlStatementDocumentoRilevazione.SQL_RENDICONTAZIONI_DROP_VIEW, nomeView);
		var params = new MapSqlParameterSource();

		update(sql, params);
	}

	public void grantAccessViewDvhExportModuliRilevazione(String moduloConfigCod, String user) {
		moduloConfigCod = PersistenceUtil.sanitizeInput(moduloConfigCod);

		String nomeView = VIGIL_V_DWH_EXPORT + moduloConfigCod;
		String sql = String.format(SqlStatementDocumentoRilevazione.GRANT_ACCESS_VIEW_RENDICONTAZIONI_CREA_VIEW,
				nomeView, user);

		var params = new MapSqlParameterSource();

		update(sql, params);
	}

	private String generateSqlForPivotModuloRilevazione(String moduloConfigCod, String campiDaEstrarre,
			String countNote, String stringSqlQuery1) {

		// pesca campi query generica
		// genera query generica
		return String.format(SqlStatementDocumentoRilevazione.SQL_PIVOT_RENDICONTAZIONI, countNote, stringSqlQuery1,
				String.format(SqlStatementDocumentoRilevazione.SQL_PIVOT_RENDICONTAZIONI_QUERY_2, moduloConfigCod),
				campiDaEstrarre, campiVariabiliPivotForCsv(moduloConfigCod));
	}

	private String generateStringSqlQuery1ForRilevazionePivot(String moduloConfigCod, Integer strutturaId,
			Integer enteId, String dataRilevazioneDa, String dataRilevazioneA, String strutturaCategoriaCod,
			String campiDaEstrarre) {
		var stringSqlFilter = settaParametriGenericiSqlStringSqlQuery1ForPivot(strutturaId, enteId, dataRilevazioneDa,
				dataRilevazioneA, strutturaCategoriaCod);

		return String.format(SqlStatementDocumentoRilevazione.SQL_PIVOT_RENDICONTAZIONI_QUERY_1, campiDaEstrarre,
				SqlStatementRilevazione.TABLE, moduloConfigCod, moduloConfigCod, stringSqlFilter.toString());
	}

	private String generateStringSqlQuery1ForDocumentazionePivot(String moduloConfigCod, Integer strutturaId,
			Integer enteId, String dataRilevazioneDa, String dataRilevazioneA, String strutturaCategoriaCod,
			String campiDaEstrarre) {
		var stringSqlFilter = settaParametriGenericiSqlStringSqlQuery1ForPivot(strutturaId, enteId, dataRilevazioneDa,
				dataRilevazioneA, strutturaCategoriaCod);
		stringSqlFilter.append(SqlStatementCommon.AND);
		stringSqlFilter.append(SqlStatementDocumentoRilevazione.SQL_PIVOT_RENDICONTAZIONI_VALIDITA_FINE_IS_NULL);

		return String.format(SqlStatementDocumentoRilevazione.SQL_PIVOT_RENDICONTAZIONI_QUERY_1, campiDaEstrarre,
				SqlStatementDocumentazione.TABLE, moduloConfigCod, moduloConfigCod, stringSqlFilter.toString());
	}

	private StringBuilder settaParametriGenericiSqlStringSqlQuery1ForPivot(Integer strutturaId, Integer enteId,
			String dataRilevazioneDa, String dataRilevazioneA, String strutturaCategoriaCod) {
		strutturaCategoriaCod = PersistenceUtil.sanitizeInput(strutturaCategoriaCod);
		dataRilevazioneDa = PersistenceUtil.sanitizeInput(dataRilevazioneDa);
		dataRilevazioneA = PersistenceUtil.sanitizeInput(dataRilevazioneA);

		var stringSqlFilter = new StringBuilder();
		addFilterToQuery(strutturaId, stringSqlFilter,
				SqlStatementDocumentoRilevazione.SQL_PIVOT_RENDICONTAZIONI_QUERY_1_FILTER_STRUTTURA_ID);
		addFilterToQuery(enteId, stringSqlFilter,
				SqlStatementDocumentoRilevazione.SQL_PIVOT_RENDICONTAZIONI_QUERY_1_FILTER_ENTE_ID);
		addFilterToQuery(dataRilevazioneA, stringSqlFilter,
				SqlStatementDocumentoRilevazione.SQL_PIVOT_RENDICONTAZIONI_QUERY_1_FILTER_DATA_RILEVAZIONE_A);
		addFilterToQuery(dataRilevazioneDa, stringSqlFilter,
				SqlStatementDocumentoRilevazione.SQL_PIVOT_RENDICONTAZIONI_QUERY_1_FILTER_DATA_RILEVAZIONE_DA);
		addFilterToQuery(strutturaCategoriaCod, stringSqlFilter,
				SqlStatementDocumentoRilevazione.SQL_PIVOT_RENDICONTAZIONI_QUERY_1_FILTER_STRUTTURA_CATEGORIA_COD);
		return stringSqlFilter;
	}

	private String campiVariabiliPivotForCsv(String moduloConfigCod) {

		var params = new MapSqlParameterSource();

		params.addValue("moduloConfigCod", moduloConfigCod);
		String sql = SqlStatementDocumentoRilevazione.SQL_PIVOT_RENDICONTAZIONI_CAMPI_VARIABILI;
		var moduloVoceColumns = query(sql, params, new ModuloVoceColumnsMapper());
		var res = new StringBuilder();
		for (ModuloVoceColumns voceColumns : moduloVoceColumns) {
			res.append("," + voceColumns.getModuloVoceColname() + " " + voceColumns.getModuloVoceColtype());
		}
		return res.toString();
	}

	private void addFilterToQuery(Object value, StringBuilder stringSqlFilter, String sql) {
		if (Objects.nonNull(value)) {
			stringSqlFilter.append(SqlStatementCommon.AND);
			stringSqlFilter.append(String.format(sql, value));
		}
	}
}
