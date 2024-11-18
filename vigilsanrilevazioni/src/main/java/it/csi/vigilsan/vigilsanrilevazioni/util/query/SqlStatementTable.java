/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanrilevazioni.util.query;

public class SqlStatementTable {

	public static final String SELECT_TABLE_OVER_32_CHAR = "SELECT table_name as id FROM information_schema.tables WHERE table_schema = 'vigilsan' AND LENGTH(table_name) > 32";
	public static final String SELECT_ALL_GENERIC_TABLE = 
			"SELECT * FROM %s";

	private SqlStatementTable() {
		throw new IllegalStateException("Utility class");
	}

}
