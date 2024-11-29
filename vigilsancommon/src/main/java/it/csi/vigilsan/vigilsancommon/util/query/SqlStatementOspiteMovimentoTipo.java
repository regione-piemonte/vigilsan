/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.util.query;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

public class SqlStatementOspiteMovimentoTipo {

 public static final String PH_PK = "ospiteMovimentoTipoId";
 public static final String SELECT = "select * from vigil_d_ospite_movimento_tipo vdomt ";
 public static final String UPDATE = "update vigil_d_ospite_movimento_tipo vdomt ";
 public static final String PK_EQUALS = "vdomt.ospite_movimento_tipo_id = :ospiteMovimentoTipoId ";
 public static final String VALIDO = " vdomt.data_cancellazione is null  "
   + " and now() between vdomt.validita_inizio and coalesce(vdomt.validita_fine, now()) ";

 public static final String UPDATE_CAMPI = """
     ospite_movimento_tipo_cod = :ospiteMovimentoTipoCod,
     ospite_movimento_tipo_desc = :ospiteMovimentoTipoDesc,
     ospite_movimento_tipo_ord = :ospiteMovimentoTipoOrd,
     ospite_movimento_tipo_hint = :ospiteMovimentoTipoHint,
     validita_inizio = :validitaInizio,
     validita_fine = :validitaFine,
     data_modifica = now(),
     utente_modifica = :utenteModifica
   """;

 private static final String INSERT = """
insert
 into
 vigilsan.vigil_d_ospite_movimento_tipo
( %s
 ospite_movimento_tipo_cod,
 ospite_movimento_tipo_desc,
 ospite_movimento_tipo_ord,
 ospite_movimento_tipo_hint,
 validita_inizio,
 validita_fine,
 data_creazione,
 data_modifica,
 data_cancellazione,
 utente_creazione,
 utente_modifica,
 utente_cancellazione,
 ospite_stato_id)
values( %s
:ospiteMovimentoTipoCod,
:ospiteMovimentoTipoDesc,
:ospiteMovimentoTipoOrd,
:ospiteMovimentoTipoHint,
 coalesce(:validitaInizio, now()),
 :validitaFine,
 now(),
 now(),
 null,
 :utenteCreazione,
 :utenteCreazione,
 null)
    """;
 public static final String INSERT_W_PK = String.format(INSERT, "ospite_movimento_tipo_id,", ":" + PH_PK + ",");
 public static final String INSERT_GENERIC = String.format(INSERT, "", "");

 public static final String NEXTVAL_PK_ID = "select nextval('vigil_d_ospite_movimento_tipo_ospite_movimento_tipo_id_seq'::regclass)";
 
 public static final String UPDATE_GENERIC = UPDATE + SqlStatementCommon.SET + UPDATE_CAMPI
   + SqlStatementCommon.WHERE + PK_EQUALS;
 public static final String DELETE = UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE + PK_EQUALS;
 public static final String SELECT_ALL = SELECT + SqlStatementCommon.WHERE + VALIDO;
 public static final String SELECT_BY_ID = SELECT + SqlStatementCommon.WHERE + PK_EQUALS;

 private SqlStatementOspiteMovimentoTipo() {
  throw new IllegalStateException("Utility class");
 }
}
