/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.util.query;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

public class SqlStatementPraticaClreq {

	public static final String PH_PK = "praticaClreqId";
	public static final String SELECT = "select vrpc.* from vigil_r_pratica_clreq vrpc ";
	public static final String UPDATE = "update vigil_r_pratica_clreq vrpc";
	public static final String PK_EQUALS = "vrpc.pratica_clreq_id = :praticaClreqId";
	public static final String VALIDO = " vrpc.data_cancellazione is null "
			+ "AND now() BETWEEN vrpc.validita_inizio AND coalesce(vrpc.validita_fine, now()) ";

	public static final String UPDATE_CAMPI = """
			  pratica_id = :praticaId,
			  prescrizione_id = :prescrizioneId,
			  appuntamento_id = :appuntamentoId,
			  clreq_id = :clreqId,
			  validita_inizio = :validitaInizio,
			  validita_fine = :validitaFine,
			  data_modifica = now(),
			  utente_modifica = :utenteModifica
			""";

	private static final String INSERT = """
			insert
			 into
			 vigilsan.vigil_r_pratica_clreq
			(%s
			 pratica_id,
			 prescrizione_id,
			 appuntamento_id,
			 clreq_id,
			 validita_inizio,
			 validita_fine,
			 data_creazione,
			 data_modifica,
			 data_cancellazione,
			 utente_creazione,
			 utente_modifica,
			 utente_cancellazione)
			values(%s
			:praticaId,
			:prescrizioneId,
			:appuntamentoId,
			:clreqId,
			 coalesce(:validitaInizio, now()),
			 :validitaFine,
			 now(),
			 now(),
			 null,
			 :utenteCreazione,
			 :utenteCreazione,
			 null)
			""";

	public static final String INSERT_W_PK = String.format(INSERT, "pratica_clreq_id,", ":" + PH_PK + ",");
	public static final String INSERT_GENERIC = String.format(INSERT, "", "");

	public static final String NEXTVAL_PK_ID = "select nextval('vigil_r_pratica_clreq_pratica_clreq_id_seq'::regclass)";
	public static final String UPDATE_GENERIC = UPDATE + SqlStatementCommon.SET + UPDATE_CAMPI
			+ SqlStatementCommon.WHERE + PK_EQUALS;
	public static final String DELETE = UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE + PK_EQUALS;
	public static final String SELECT_ALL = SELECT + SqlStatementCommon.WHERE + VALIDO;
	public static final String SELECT_BY_ID = SELECT + SqlStatementCommon.WHERE + PK_EQUALS;
	public static final String SELECT_BY_ID_VALIDO = SELECT + SqlStatementCommon.WHERE + PK_EQUALS
			+ SqlStatementCommon.AND + VALIDO;
	public static final String SELECT_ALL_BY_PRATICA_ID = SELECT + SqlStatementCommon.WHERE
			+ " pratica_id = :praticaId and " + VALIDO;
	public static final String SELECT_ALL_BY_PRATICA_ID_PRESCRIZIONE_ID = " AND prescrizione_id = :prescrizioneId ";
	public static final String SELECT_ALL_BY_PRATICA_ID_APPUNTAMENTO_ID = " AND appuntamento_id = :appuntamentoId ";
	public static final String INSERT_POST_INSERIMENTO_PRATICA = """
INSERT INTO vigil_r_pratica_clreq (pratica_id, clreq_id, utente_creazione, utente_modifica)
SELECT p.pratica_id, fnc.clreq_id, p.utente_creazione, p.utente_creazione
FROM fnc_checklist_req((
  SELECT vdpt.clreq_id
  FROM vigil_t_pratica vtp
  JOIN vigil_d_pratica_tipo vdpt ON vtp.pratica_tipo_id = vdpt.pratica_tipo_id
  WHERE vtp.pratica_id = :praticaId
)) fnc,
vigil_t_pratica p
WHERE fnc.modulo_id IS NOT NULL
  AND p.pratica_id = :praticaId
  AND p.data_cancellazione IS NULL
""";

	private SqlStatementPraticaClreq() {
		throw new IllegalStateException("Utility class");
	}
}
