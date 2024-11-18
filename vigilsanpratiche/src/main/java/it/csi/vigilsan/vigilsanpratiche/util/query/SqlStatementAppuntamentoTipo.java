/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.util.query;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

public class SqlStatementAppuntamentoTipo {

 public static final String PH_PK = "appuntamentoTipoId";
 public static final String SELECT = "select vdat.* from vigil_d_appuntamento_tipo vdat ";
 public static final String UPDATE = "update vigil_d_appuntamento_tipo vdat";
 public static final String PK_EQUALS = "vdat.appuntamento_tipo_id = :appuntamentoTipoId";
 public static final String VALIDO = " vdat.data_cancellazione is null "
   + "AND now() BETWEEN vdat.validita_inizio AND coalesce(vdat.validita_fine, now()) ";

 public static final String UPDATE_CAMPI = """
     appuntamento_tipo_cod = :appuntamentoTipoCod,
     appuntamento_tipo_desc = :appuntamentoTipoDesc,
     validita_inizio = :validitaInizio,
     validita_fine = :validitaFine,
     data_modifica = now(),
     utente_modifica = :utenteModifica
   """;

 private static final String INSERT = """
insert
 into
 vigilsan.vigil_d_appuntamento_tipo
(%s
 appuntamento_tipo_cod,
 appuntamento_tipo_desc,
 validita_inizio,
 validita_fine,
 data_creazione,
 data_modifica,
 data_cancellazione,
 utente_creazione,
 utente_modifica,
 utente_cancellazione)
values(%s
:appuntamentoTipoCod,
:appuntamentoTipoDesc,
 coalesce(:validitaInizio, now()),
 :validitaFine,
 now(),
 now(),
 null,
 :utenteCreazione,
 :utenteCreazione,
 null)
""";
 public static final String INSERT_W_PK = String.format(INSERT, "appuntamento_tipo_id,", ":" + PH_PK + ",");
 public static final String INSERT_GENERIC = String.format(INSERT, "", "");

 public static final String NEXTVAL_PK_ID = "select nextval('vigil_d_appuntamento_tipo_appuntamento_tipo_id_seq'::regclass)";
 public static final String UPDATE_GENERIC = UPDATE + SqlStatementCommon.SET + UPDATE_CAMPI
   + SqlStatementCommon.WHERE + PK_EQUALS;
 public static final String DELETE = UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE + PK_EQUALS;
 public static final String SELECT_ALL = SELECT + SqlStatementCommon.WHERE + VALIDO;
 public static final String SELECT_BY_ID = SELECT + SqlStatementCommon.WHERE + PK_EQUALS;

 private SqlStatementAppuntamentoTipo() {
  throw new IllegalStateException("Utility class");
 }
}
