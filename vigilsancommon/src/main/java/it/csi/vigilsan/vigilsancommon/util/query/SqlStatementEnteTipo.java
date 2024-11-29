/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.util.query;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

public class SqlStatementEnteTipo {

 public static final String PH_PK = "enteTipoId";
 public static final String SELECT = "select vdet.* from vigil_d_ente_tipo vdet ";
 public static final String UPDATE = "update vigil_d_ente_tipo vdet";
 public static final String PK_EQUALS = "vdet.ente_tipo_id = :enteTipoId";
 public static final String VALIDO = " vdet.data_cancellazione is null "
   + "AND now() BETWEEN vdet.validita_inizio AND coalesce(vdet.validita_fine, now()) ";

 public static final String UPDATE_CAMPI = """
     ente_tipo_cod = :enteTipoCod,
     ente_tipo_desc = :enteTipoDesc,
     validita_inizio = :validitaInizio,
     validita_fine = :validitaFine,
     data_modifica = now(),
     utente_modifica = :utenteModifica
   """;

 private static final String INSERT = """
insert
 into
 vigilsan.vigil_d_ente_tipo
($s
 ente_tipo_cod,
 ente_tipo_desc,
 validita_inizio,
 validita_fine,
 data_creazione,
 data_modifica,
 data_cancellazione,
 utente_creazione,
 utente_modifica,
 utente_cancellazione)
values(%s 
:enteTipoCod,
:enteTipoDesc,
 coalesce(:validitaInizio, now()),
 :validitaFine,
 now(),
 now(),
 null,
 :utenteCreazione,
 :utenteCreazione,
 null)
""";
 public static final String INSERT_W_PK = String.format(INSERT, "ente_tipo_id,", ":" + PH_PK + ",");
 public static final String INSERT_GENERIC = String.format(INSERT, "", "");

 public static final String NEXTVAL_PK_ID = "select nextval('vigil_d_ente_tipo_ente_tipo_id_seq'::regclass) ";
 public static final String UPDATE_GENERIC = UPDATE + SqlStatementCommon.SET + UPDATE_CAMPI
   + SqlStatementCommon.WHERE + PK_EQUALS;
 public static final String DELETE = UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE + PK_EQUALS;
 public static final String SELECT_ALL = SELECT + SqlStatementCommon.WHERE + VALIDO;
 public static final String SELECT_BY_ID = SELECT + SqlStatementCommon.WHERE + PK_EQUALS;

 private SqlStatementEnteTipo() {
  throw new IllegalStateException("Utility class");
 }
}
