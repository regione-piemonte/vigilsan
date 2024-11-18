/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.util.query;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

public class SqlStatementAppuntamentoConfig {

 public static final String PH_PK = "appuntamentoConfigId";
 public static final String SELECT = "select vrac.* from vigil_r_appuntamento_config vrac ";
 public static final String UPDATE = "update vigil_r_appuntamento_config vrac";
 public static final String PK_EQUALS = "vrac.appuntamento_config_id = :appuntamentoConfigId";
 public static final String VALIDO = " vrac.data_cancellazione is null "
   + "AND now() BETWEEN vrac.validita_inizio AND coalesce(vrac.validita_fine, now()) ";

 public static final String UPDATE_CAMPI = """
     appuntamento_tipo_id = :appuntamentoTipoId,
     pratica_tipo_id = :praticaTipoId,
     pratica_stato_id = :praticaStatoId,
     validita_inizio = :validitaInizio,
     validita_fine = :validitaFine,
     data_modifica = now(),
     utente_modifica = :utenteModifica
   """;

 private static final String INSERT = """

insert
 into
 vigilsan.vigil_r_appuntamento_config
(%s
 appuntamento_tipo_id,
 pratica_tipo_id,
 pratica_stato_id,
 validita_inizio,
 validita_fine,
 data_creazione,
 data_modifica,
 data_cancellazione,
 utente_creazione,
 utente_modifica,
 utente_cancellazione)
values(%s
:appuntamentoTipoId,
:praticaTipoId,
:praticaStatoId,
 coalesce(:validitaInizio, now()),
 :validitaFine,
 now(),
 now(),
 null,
 :utenteCreazione,
 :utenteCreazione,
 null)
""";
 
 public static final String INSERT_W_PK = String.format(INSERT, "appuntamento_config_id,", ":" + PH_PK + ",");
 public static final String INSERT_GENERIC = String.format(INSERT, "", "");

 public static final String NEXTVAL_PK_ID = "select nextval('vigil_r_appuntamento_config_appuntamento_config_id_seq'::regclass)";
 public static final String UPDATE_GENERIC = UPDATE + SqlStatementCommon.SET + UPDATE_CAMPI
   + SqlStatementCommon.WHERE + PK_EQUALS;
 public static final String DELETE = UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE + PK_EQUALS;
 public static final String SELECT_ALL = SELECT + SqlStatementCommon.WHERE + VALIDO;
 public static final String SELECT_BY_ID = SELECT + SqlStatementCommon.WHERE + PK_EQUALS;
 public static final String SELECT_BY_PRATICA_TIPO_ID = SELECT_ALL + SqlStatementCommon.AND + " pratica_tipo_id = :praticaTipoId ";

 private SqlStatementAppuntamentoConfig() {
  throw new IllegalStateException("Utility class");
 }
}
