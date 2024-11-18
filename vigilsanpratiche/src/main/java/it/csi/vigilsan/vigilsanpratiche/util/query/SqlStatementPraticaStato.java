/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.util.query;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

public class SqlStatementPraticaStato {

 public static final String PH_PK = "praticaStatoId";
 public static final String SELECT = "select vdps.* from vigil_d_pratica_stato vdps ";
 public static final String UPDATE = "update vigil_d_pratica_stato vdps";
 public static final String PK_EQUALS = "vdps.pratica_stato_id = :praticaStatoId";
 public static final String VALIDO = " vdps.data_cancellazione is null "
   + "AND now() BETWEEN vdps.validita_inizio AND coalesce(vdps.validita_fine, now()) ";

 public static final String UPDATE_CAMPI = """
     pratica_stato_cod = :praticaStatoCod,
     pratica_stato_desc = :praticaStatoDesc,
     pratica_stato_finale = :praticaStatoFinale,
     validita_inizio = :validitaInizio,
     validita_fine = :validitaFine,
     data_modifica = now(),
     utente_modifica = :utenteModifica
   """;

 private static final String INSERT = """
insert
 into
 vigilsan.vigil_d_pratica_stato
(%s
 pratica_stato_cod,
 pratica_stato_desc,
 pratica_stato_finale,
 validita_inizio,
 validita_fine,
 data_creazione,
 data_modifica,
 data_cancellazione,
 utente_creazione,
 utente_modifica,
 utente_cancellazione)
values(%s
:praticaStatoCod,
:praticaStatoDesc,
:praticaStatoFinale,
 coalesce(:validitaInizio, now()),
 :validitaFine,
 now(),
 now(),
 null,
 :utenteCreazione,
 :utenteCreazione,
 null)
""";
 public static final String INSERT_W_PK = String.format(INSERT, "pratica_stato_id,", ":" + PH_PK + ",");
 public static final String INSERT_GENERIC = String.format(INSERT, "", "");

 public static final String NEXTVAL_PK_ID = "select nextval('vigil_d_pratica_stato_pratica_stato_id_seq'::regclass)";
 public static final String UPDATE_GENERIC = UPDATE + SqlStatementCommon.SET + UPDATE_CAMPI
   + SqlStatementCommon.WHERE + PK_EQUALS;
 public static final String DELETE = UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE + PK_EQUALS;
 public static final String SELECT_ALL = SELECT + SqlStatementCommon.WHERE + VALIDO;
 public static final String SELECT_BY_ID = SELECT + SqlStatementCommon.WHERE + PK_EQUALS;

 private SqlStatementPraticaStato() {
  throw new IllegalStateException("Utility class");
 }
}
