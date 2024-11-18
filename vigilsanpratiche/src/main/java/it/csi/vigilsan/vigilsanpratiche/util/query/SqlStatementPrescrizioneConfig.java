/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.util.query;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

public class SqlStatementPrescrizioneConfig {

 public static final String PH_PK = "prescrizioneConfigId";
 public static final String SELECT = "select vrpc.* from vigil_r_prescrizione_config vrpc ";
 public static final String UPDATE = "update vigil_r_prescrizione_config vrpc";
 public static final String PK_EQUALS = "vrpc.prescrizione_config_id = :prescrizioneConfigId";
 public static final String VALIDO = " vrpc.data_cancellazione is null "
   + "AND now() BETWEEN vrpc.validita_inizio AND coalesce(vrpc.validita_fine, now()) ";

 public static final String UPDATE_CAMPI = """
     prescrizione_tipo_id = :prescrizioneTipoId,
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
 vigilsan.vigil_r_prescrizione_config
(%s
 prescrizione_tipo_id,
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
:prescrizioneTipoId,
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
 
 public static final String INSERT_W_PK = String.format(INSERT, "prescrizione_config_id,", ":" + PH_PK + ",");
 public static final String INSERT_GENERIC = String.format(INSERT, "", "");

 public static final String NEXTVAL_PK_ID = "select nextval('vigil_r_appuntamento_config_appuntamento_config_id_seq'::regclass)";
 public static final String UPDATE_GENERIC = UPDATE + SqlStatementCommon.SET + UPDATE_CAMPI
   + SqlStatementCommon.WHERE + PK_EQUALS;
 public static final String DELETE = UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE + PK_EQUALS;
 public static final String SELECT_ALL = SELECT + SqlStatementCommon.WHERE + VALIDO;
 public static final String SELECT_BY_ID = SELECT + SqlStatementCommon.WHERE + PK_EQUALS;
 public static final String SELECT_BY_PRATICA_TIPO_ID = SELECT_ALL + SqlStatementCommon.AND + " pratica_tipo_id = :praticaTipoId ";

 private SqlStatementPrescrizioneConfig() {
  throw new IllegalStateException("Utility class");
 }
}
