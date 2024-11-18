/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.util.query;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

public class SqlStatementFileContentType {

	public static final String PH_PK = "fileContentTypeId";
	public static final String SELECT = "select * from vigil_d_file_content_type  vdfct ";
	public static final String UPDATE = "update vigil_d_file_content_type  vdfct ";
	public static final String PK_EQUALS = "vdfct.file_content_type_id = :fileContentTypeId ";
	public static final String VALIDO = " vdfct.data_cancellazione is null  "
			+ " and now() between vdfct.validita_inizio and coalesce(vdfct.validita_fine, now()) ";

	public static final String UPDATE_CAMPI = """
			  file_content_type_cod = :fileContentTypeCod,
			  file_content_type_desc = :fileContentTypeDesc,
			  file_content_type_ord = :fileContentTypeOrd,
			  file_content_type_hint = :fileContentTypeHint,
			  file_content_type_mime = :fileContentTypeMime,
			  file_content_type_ext = :fileContentTypeExt,
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
			 file_content_type_cod,
			 file_content_type_desc,
			 file_content_type_ord,
			 file_content_type_hint,
			 file_content_type_mime,
			 file_content_type_ext,
			 validita_inizio,
			 validita_fine,
			 data_creazione,
			 data_modifica,
			 data_cancellazione,
			 utente_creazione,
			 utente_modifica,
			 utente_cancellazione)
			values( %s
			:fileContentTypeCod,
			:fileContentTypeDesc,
			:fileContentTypeOrd,
			:fileContentTypeHint,
			:fileContentTypeMime,
			:fileContentTypeExt,
			 coalesce(:validitaInizio, now()),
			 :validitaFine,
			 now(),
			 now(),
			 null,
			 :utenteCreazione,
			 :utenteCreazione,
			 null)
			 """;
	public static final String INSERT_W_PK = String.format(INSERT, "file_content_type_id,", ":" + PH_PK + ",");
	public static final String INSERT_GENERIC = String.format(INSERT, "", "");

	public static final String NEXTVAL_PK_ID = "select nextval('vigil_d_file_content_type_file_content_type_id_seq'::regclass)";
	public static final String UPDATE_GENERIC = UPDATE + SqlStatementCommon.SET + UPDATE_CAMPI
			+ SqlStatementCommon.WHERE + PK_EQUALS;
	public static final String DELETE = UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE + PK_EQUALS;
	public static final String SELECT_ALL = SELECT + SqlStatementCommon.WHERE + VALIDO;
	public static final String SELECT_BY_ID = SELECT + SqlStatementCommon.WHERE + PK_EQUALS;
	public static final String SELECT_BY_COD = SELECT + SqlStatementCommon.WHERE
			+ "vdfct.file_content_type_cod = :fileContentTypeCod " + SqlStatementCommon.AND + VALIDO;

	public static final String SELECT_BY_FILE_TIPO_ID = """
			select
			    vdfct.*
			from
			vigil_r_file_tipo_content_type vrftct
			join vigil_d_file_content_type  vdfct on vdfct.file_content_type_id = vrftct.file_content_type_id
			""" + SqlStatementCommon.AND + VALIDO + """
			where
			 vrftct.file_tipo_id = :fileTipoId
			""" + SqlStatementCommon.AND + SqlStatementFileTipoContentType.VALIDO;

	private SqlStatementFileContentType() {
		throw new IllegalStateException("Utility class");
	}
}
