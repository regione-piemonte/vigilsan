/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.util.query;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

public class SqlStatementAppuntamentoWorkflow {

 public static final String PH_PK = "appuntamentoWorkflowId";
 public static final String SELECT = "select vraw.* from vigil_r_appuntamento_workflow vraw ";
 public static final String UPDATE = "update vigil_r_appuntamento_workflow vraw";
 public static final String PK_EQUALS = "vraw.appuntamento_workflow_id = :appuntamentoWorkflowId";
 public static final String VALIDO = " vraw.data_cancellazione is null "
   + "AND now() BETWEEN vraw.validita_inizio AND coalesce(vraw.validita_fine, now()) ";

 public static final String UPDATE_CAMPI = """
     appuntamento_stato_cod = :appuntamentoStatoCod,
     appuntamento_stato_desc = :appuntamentoStatoDesc,
     appuntamento_stato_finale = :appuntamentoStatoFinale,
     validita_inizio = :validitaInizio,
     validita_fine = :validitaFine,
     data_modifica = now(),
     utente_modifica = :utenteModifica
   """;

 private static final String INSERT = """
insert
	into
	vigilsan.vigil_r_appuntamento_workflow
(%s
	pratica_tipo_id,
	appuntamento_tipo_id,
	azione_id,
	modulo_id,
	appuntamento_stato_id_iniziale,
	appuntamento_stato_id_finale,
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
:appuntamentoTipoId,
:azioneId,
:moduloId,
:appuntametoStatoIdIniziale,
:appuntametoStatoIdFinale,
 coalesce(:validitaInizio, now()),
 :validitaFine,
 now(),
 now(),
 null,
 :utenteCreazione,
 :utenteCreazione,
 null)
""";
 public static final String INSERT_W_PK = String.format(INSERT, "appuntamento_workflow_id,", ":" + PH_PK + ",");
 public static final String INSERT_GENERIC = String.format(INSERT, "", "");

 public static final String NEXTVAL_PK_ID = "select nextval('vigil_r_appuntamento_workflow_appuntamento_workflow_id_seq'::regclass)";
 public static final String UPDATE_GENERIC = UPDATE + SqlStatementCommon.SET + UPDATE_CAMPI
   + SqlStatementCommon.WHERE + PK_EQUALS;
 public static final String DELETE = UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE + PK_EQUALS;
 public static final String SELECT_ALL = SELECT + SqlStatementCommon.WHERE + VALIDO;
 public static final String SELECT_BY_ID = SELECT + SqlStatementCommon.WHERE + PK_EQUALS;
 public static final String SELECT_BY_PRATICA_TIPO_ID = SELECT_ALL + SqlStatementCommon.AND + " pratica_tipo_id = :praticaTipoId ";

 private SqlStatementAppuntamentoWorkflow() {
  throw new IllegalStateException("Utility class");
 }
}
