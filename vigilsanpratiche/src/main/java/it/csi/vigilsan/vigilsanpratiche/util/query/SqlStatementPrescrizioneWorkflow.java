/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.util.query;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

public class SqlStatementPrescrizioneWorkflow {

 public static final String PH_PK = "prescrizioneWorkflowId";
 public static final String SELECT = "select vrpw.* from vigil_r_prescrizione_workflow vrpw ";
 public static final String UPDATE = "update vigil_r_prescrizione_workflow vrpw";
 public static final String PK_EQUALS = "vrpw.prescrizione_workflow_id = :prescrizioneWorkflowId";
 public static final String VALIDO = " vrpw.data_cancellazione is null "
   + "AND now() BETWEEN vrpw.validita_inizio AND coalesce(vrpw.validita_fine, now()) ";

 public static final String UPDATE_CAMPI = """
     pratica_tipo_id = :praticaTipoId,
     prescrizione_tipo_id = :prescrizioneTipoId,
     azione_id = :azioneId,
     modulo_id = :moduloId,
     prescrizione_stato_id_iniziale = :prescrizioneStatoIdIniziale,
     prescrizione_stato_id_finale = :prescrizioneStatoIdFinale,
     validita_inizio = :validitaInizio,
     validita_fine = :validitaFine,
     data_modifica = now(),
     utente_modifica = :utenteModifica
   """;

 private static final String INSERT = """
insert
 into
 vigilsan.vigil_r_prescrizione_workflow
(%s
 pratica_tipo_id,
 prescrizione_tipo_id,
 azione_id,
 modulo_id,
 prescrizione_stato_id_iniziale,
 prescrizione_stato_id_finale,
 validita_inizio,
 validita_fine,
 data_creazione,
 data_modifica,
 data_cancellazione,
 utente_creazione,
 utente_modifica,
 utente_cancellazione)
values(%s
:praticaTipoId,
:prescrizioneTipoId,
:azioneId,
:moduloId,
:prescrizioneStatoIdIniziale,
:prescrizioneStatoIdFinale,
 coalesce(:validitaInizio, now()),
 :validitaFine,
 now(),
 now(),
 null,
 :utenteCreazione,
 :utenteCreazione,
 null)
""";
 public static final String INSERT_W_PK = String.format(INSERT, "prescrizione_workflow_id,", ":" + PH_PK + ",");
 public static final String INSERT_GENERIC = String.format(INSERT, "", "");

 public static final String NEXTVAL_PK_ID = "select nextval('vigil_r_appuntamento_workflow_appuntamento_workflow_id_seq'::regclass)";
 public static final String UPDATE_GENERIC = UPDATE + SqlStatementCommon.SET + UPDATE_CAMPI
   + SqlStatementCommon.WHERE + PK_EQUALS;
 public static final String DELETE = UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE + PK_EQUALS;
 public static final String SELECT_ALL = SELECT + SqlStatementCommon.WHERE + VALIDO;
 public static final String SELECT_BY_ID = SELECT + SqlStatementCommon.WHERE + PK_EQUALS;
 public static final String SELECT_BY_PRATICA_TIPO_ID = SELECT_ALL + SqlStatementCommon.AND + " pratica_tipo_id = :praticaTipoId ";

 private SqlStatementPrescrizioneWorkflow() {
  throw new IllegalStateException("Utility class");
 }
}
