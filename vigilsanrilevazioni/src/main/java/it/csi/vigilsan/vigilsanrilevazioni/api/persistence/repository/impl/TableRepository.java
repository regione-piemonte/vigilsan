/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanrilevazioni.api.persistence.repository.impl;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.csi.vigilsan.vigilsanrilevazioni.util.query.SqlStatementDocumentoRilevazione;
import it.csi.vigilsan.vigilsanrilevazioni.util.query.SqlStatementTable;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.AbstractPersistence;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.PersistenceUtil;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.StringMapper;

@Repository
public class TableRepository extends AbstractPersistence {

	public List<String> getTablesOver32CharLength() {

		var params = new MapSqlParameterSource();
		String sql = SqlStatementTable.SELECT_TABLE_OVER_32_CHAR;
		return query(sql, params, new StringMapper());
	}

	public void dropViewExport(String tableName) {
		tableName = PersistenceUtil.sanitizeInput(tableName);

		String sql = String.format(SqlStatementDocumentoRilevazione.SQL_RENDICONTAZIONI_DROP_VIEW, tableName);
		var params = new MapSqlParameterSource();

		update(sql, params);
	}

	public void grantAccessViewExport(String tableName, String user) {
		tableName = PersistenceUtil.sanitizeInput(tableName);

		String sql = String.format(SqlStatementDocumentoRilevazione.GRANT_ACCESS_VIEW_RENDICONTAZIONI_CREA_VIEW,
				tableName, user);
		var params = new MapSqlParameterSource();

		update(sql, params);
	}

	public void creaViewExport(String tableName, String tableNameOrig) {
		tableName = PersistenceUtil.sanitizeInput(tableName);
		tableNameOrig = PersistenceUtil.sanitizeInput(tableNameOrig);

		String sql = String.format(SqlStatementDocumentoRilevazione.SQL_PIVOT_RENDICONTAZIONI_CREA_VIEW, 
				tableName,String.format(SqlStatementTable.SELECT_ALL_GENERIC_TABLE, tableNameOrig));

		var params = new MapSqlParameterSource();

		update(sql, params);
	}
}
