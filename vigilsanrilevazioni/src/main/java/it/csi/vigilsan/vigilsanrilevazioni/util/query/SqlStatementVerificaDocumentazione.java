/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanrilevazioni.util.query;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

public class SqlStatementVerificaDocumentazione {

	public static final String PH_PK = "verificaDocumentazioneId";
	public static final String SELECT = "select vtvd.* from vigil_t_verifica_documentazione vtvd ";
	public static final String UPDATE = "update vigil_t_verifica_documentazione vtvd";
	public static final String PK_EQUALS = "vtvd.verifica_documentazione_id = :verificaDocumentazioneId";
	public static final String VALIDO = " vtvd.data_cancellazione is null "
			+ "AND now() BETWEEN vtvd.validita_inizio AND coalesce(vtvd.validita_fine, now()) ";

	public static final String UPDATE_CAMPI = """
			  documentazione_id = :documentazioneId,
			  dataora_verifica = :dataoraVerifica,
			  esito_verifica = :esitoVerifica,
			  note = :note,
			  notifica_id = :notificaId,
			  validita_inizio = :validitaInizio,
			  validita_fine = :validitaFine,
			  data_modifica = now(),
			  utente_modifica = :utenteModifica
			""";

	private static final String INSERT = """
			insert
			 into
			 vigilsan.vigil_t_verifica_documentazione
			(%s
			 documentazione_id,
			 dataora_verifica,
			 esito_verifica,
			 note,
			 notifica_id,
			 validita_inizio,
			 validita_fine,
			 data_creazione,
			 data_modifica,
			 data_cancellazione,
			 utente_creazione,
			 utente_modifica,
			 utente_cancellazione)
			values(%s
			:documentazioneId,
			now(),
			:esitoVerifica,
			:note,
			:notificaId,
			 coalesce(:validitaInizio, now()),
			 :validitaFine,
			 now(),
			 now(),
			 null,
			 :utenteCreazione,
			 :utenteCreazione,
			 null)
			""";
	public static final String INSERT_W_PK = String.format(INSERT, "verifica_documentazione_id,", ":" + PH_PK + ",");
	public static final String INSERT_GENERIC = String.format(INSERT, "", "");

	public static final String NEXTVAL_PK_ID = "select nextval('vigil_t_verifica_documentazione_verifica_documentazione_id_seq'::regclass) ";
	public static final String UPDATE_GENERIC = UPDATE + SqlStatementCommon.SET + UPDATE_CAMPI
			+ SqlStatementCommon.WHERE + PK_EQUALS;
	public static final String DELETE = UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE + PK_EQUALS;
	public static final String SELECT_ALL = SELECT + SqlStatementCommon.WHERE + VALIDO;
	public static final String SELECT_BY_ID = SELECT + SqlStatementCommon.WHERE + PK_EQUALS;

	public static final String DELETE_OLD = UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE
			+ " vtvd.documentazione_id = :documentazioneId" + SqlStatementCommon.AND + VALIDO;
	public static final String SELECT_BY_DOCUMENTAZIONE_ID = SELECT + SqlStatementCommon.WHERE
			+ " vtvd.documentazione_id = :documentazioneId" + SqlStatementCommon.AND + VALIDO;

	private SqlStatementVerificaDocumentazione() {
		throw new IllegalStateException("Utility class");
	}
}
