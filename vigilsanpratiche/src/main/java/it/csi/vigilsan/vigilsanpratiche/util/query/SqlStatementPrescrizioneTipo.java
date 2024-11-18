/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.util.query;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

public class SqlStatementPrescrizioneTipo {

 public static final String PH_PK = "prescrizioneTipoId";
 public static final String SELECT = "select vdpt.* from vigil_d_prescrizione_tipo vdpt ";
 public static final String UPDATE = "update vigil_d_prescrizione_tipo vdpt";
 public static final String PK_EQUALS = "vdpt.prescrizione_tipo_id = :prescrizioneTipoId";
 public static final String VALIDO = " vdpt.data_cancellazione is null "
   + "AND now() BETWEEN vdpt.validita_inizio AND coalesce(vdpt.validita_fine, now()) ";

 public static final String UPDATE_CAMPI = """
     prescrizione_tipo_cod = :prescrizioneTipoCod,
     prescrizione_tipo_desc = :prescrizioneTipoDesc,
     validita_inizio = :validitaInizio,
     validita_fine = :validitaFine,
     data_modifica = now(),
     utente_modifica = :utenteModifica
   """;

 private static final String INSERT = """
insert
 into
 vigilsan.vigil_d_prescrizione_tipo
(%s
 prescrizione_tipo_cod,
 prescrizione_tipo_desc,
 richiede_nuovo_appuntamento,
 validita_inizio,
 validita_fine,
 data_creazione,
 data_modifica,
 data_cancellazione,
 utente_creazione,
 utente_modifica,
 utente_cancellazione)
values(%s
 :prescrizioneTipoCod,
 :prescrizioneTipoDesc,
 coalesce(:validitaInizio, now()),
 :validitaFine,
 now(),
 now(),
 null,
 :utenteCreazione,
 :utenteCreazione,
 null)
""";
 public static final String INSERT_W_PK = String.format(INSERT, "prescrizione_tipo_id,", ":" + PH_PK + ",");
 public static final String INSERT_GENERIC = String.format(INSERT, "", "");

 public static final String NEXTVAL_PK_ID = "select nextval('vigil_d_prescrizione_tipo_prescrizione_tipo_id_seq'::regclass)";
 public static final String UPDATE_GENERIC = UPDATE + SqlStatementCommon.SET + UPDATE_CAMPI
   + SqlStatementCommon.WHERE + PK_EQUALS;
 public static final String DELETE = UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE + PK_EQUALS;
 public static final String SELECT_ALL = SELECT + SqlStatementCommon.WHERE + VALIDO;
 public static final String SELECT_BY_ID = SELECT + SqlStatementCommon.WHERE + PK_EQUALS;

 private SqlStatementPrescrizioneTipo() {
  throw new IllegalStateException("Utility class");
 }
}
