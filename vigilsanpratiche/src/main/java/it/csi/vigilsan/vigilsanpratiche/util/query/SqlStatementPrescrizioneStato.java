/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.util.query;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

public class SqlStatementPrescrizioneStato {

 public static final String PH_PK = "prescrizioneStatoId";
 public static final String SELECT = "select vdps.* from vigil_d_prescrizione_stato vdps ";
 public static final String UPDATE = "update vigil_d_prescrizione_stato vdps";
 public static final String PK_EQUALS = "vdps.prescrizione_stato_id = :prescrizioneStatoId";
 public static final String VALIDO = " vdps.data_cancellazione is null "
   + "AND now() BETWEEN vdps.validita_inizio AND coalesce(vdps.validita_fine, now()) ";

 public static final String UPDATE_CAMPI = """
     prescrizione_stato_cod = :prescrizioneStatoCod,
     prescrizione_stato_desc = :prescrizioneStatoDesc,
     prescrizione_stato_finale = :prescrizioneStatoFinale,
     validita_inizio = :validitaInizio,
     validita_fine = :validitaFine,
     data_modifica = now(),
     utente_modifica = :utenteModifica
   """;

 private static final String INSERT = """
insert
 into
 vigilsan.vigil_d_prescrizione_stato
(%s
 prescrizione_stato_cod,
 prescrizione_stato_desc,
 prescrizione_stato_finale,
 validita_inizio,
 validita_fine,
 data_creazione,
 data_modifica,
 data_cancellazione,
 utente_creazione,
 utente_modifica,
 utente_cancellazione)
values(%s
 :prescrizioneStatoCod,
 :prescrizioneStatoDesc,
 :prescrizioneStatoFinale,
 coalesce(:validitaInizio, now()),
 :validitaFine,
 now(),
 now(),
 null,
 :utenteCreazione,
 :utenteCreazione,
 null)
""";
 public static final String INSERT_W_PK = String.format(INSERT, "prescrizione_stato_id,", ":" + PH_PK + ",");
 public static final String INSERT_GENERIC = String.format(INSERT, "", "");

 public static final String NEXTVAL_PK_ID = "select nextval('vigil_d_prescrizione_stato_prescrizione_stato_id_seq'::regclass)";
 public static final String UPDATE_GENERIC = UPDATE + SqlStatementCommon.SET + UPDATE_CAMPI
   + SqlStatementCommon.WHERE + PK_EQUALS;
 public static final String DELETE = UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE + PK_EQUALS;
 public static final String SELECT_ALL = SELECT + SqlStatementCommon.WHERE + VALIDO;
 public static final String SELECT_BY_ID = SELECT + SqlStatementCommon.WHERE + PK_EQUALS;

 private SqlStatementPrescrizioneStato() {
  throw new IllegalStateException("Utility class");
 }
}
