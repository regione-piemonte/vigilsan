/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.util.query;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

public class SqlStatementFileTipoContentType {

	public static final String PH_PK = "fileTipoContentTypeId";
	public static final String SELECT = "select * from vigil_r_file_tipo_content_type vrftct ";
	public static final String UPDATE = "update vigil_r_file_tipo_content_type vrftct ";
	public static final String PK_EQUALS = "vrftct.file_tipo_content_type_id = :fileTipoContentTypeId ";
	public static final String VALIDO = " vrftct.data_cancellazione is null  "
			+ " and now() between vrftct.validita_inizio and coalesce(vrftct.validita_fine, now()) ";

	public static final String UPDATE_CAMPI = """
			  file_content_type_id = :fileContentTypeId,
			  file_tipo_id = :fileTipoId,
			  validita_inizio = :validitaInizio,
			  validita_fine = :validitaFine,
			  data_modifica = now(),
			  utente_modifica = :utenteModifica
			""";

	private static final String INSERT = """
			insert
			 into
			 vigilsan.vigil_d_ospite_condizioni
			( %s
			 file_content_type_id,
			 file_tipo_id,
			 validita_inizio,
			 validita_fine,
			 data_creazione,
			 data_modifica,
			 data_cancellazione,
			 utente_creazione,
			 utente_modifica,
			 utente_cancellazione)
			values( %s
			:fileContentTypeId,
			:fileTipoId,
			 coalesce(:validitaInizio, now()),
			 :validitaFine,
			 now(),
			 now(),
			 null,
			 :utenteCreazione,
			 :utenteCreazione,
			 null)
			 """;
	public static final String INSERT_W_PK = String.format(INSERT, "file_tipo_content_type_id,", ":" + PH_PK + ",");
	public static final String INSERT_GENERIC = String.format(INSERT, "", "");

	public static final String NEXTVAL_PK_ID = "select nextval('vigil_r_file_tipo_content_type_file_tipo_content_type_id_seq'::regclass)";
	public static final String UPDATE_GENERIC = UPDATE + SqlStatementCommon.SET + UPDATE_CAMPI
			+ SqlStatementCommon.WHERE + PK_EQUALS;
	public static final String DELETE = UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE + PK_EQUALS;
	public static final String SELECT_ALL = SELECT + SqlStatementCommon.WHERE + VALIDO;
	public static final String SELECT_BY_ID = SELECT + SqlStatementCommon.WHERE + PK_EQUALS;

	public static final String SELECT_BY_FILE_TIPO_ID = """
			select
			    *
			from
			vigil_r_file_tipo_content_type vrftct
			where
			 vrftct.file_tipo_id = :fileTipoId
			""" + SqlStatementCommon.AND + VALIDO;

	private SqlStatementFileTipoContentType() {
		throw new IllegalStateException("Utility class");
	}
}
