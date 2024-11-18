/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.util.query;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

public class SqlStatementPraticaTipo {

 public static final String PH_PK = "praticaTipoId";
 public static final String SELECT = "select vdpt.* from vigil_d_pratica_tipo vdpt ";
 public static final String UPDATE = "update vigil_d_pratica_tipo vdpt";
 public static final String PK_EQUALS = "vdpt.pratica_tipo_id = :praticaTipoId";
 public static final String VALIDO = " vdpt.data_cancellazione is null "
   + "AND now() BETWEEN vdpt.validita_inizio AND coalesce(vdpt.validita_fine, now()) ";

 public static final String UPDATE_CAMPI = """
     pratica_tipo_cod = :praticaTipoCod,
     pratica_tipo_desc = :praticaTipoDesc,
     validita_inizio = :validitaInizio,
     validita_fine = :validitaFine,
     data_modifica = now(),
     utente_modifica = :utenteModifica
   """;

 private static final String INSERT = """

insert
 into
 vigilsan.vigil_d_pratica_tipo
(%s
 pratica_tipo_cod,
 pratica_tipo_desc,
 validita_inizio,
 validita_fine,
 data_creazione,
 data_modifica,
 data_cancellazione,
 utente_creazione,
 utente_modifica,
 utente_cancellazione)
values(%s
:praticaTipoCod,
:praticaTipoDesc,
 coalesce(:validitaInizio, now()),
 :validitaFine,
 now(),
 now(),
 null,
 :utenteCreazione,
 :utenteCreazione,
 null)
""";
 public static final String INSERT_W_PK = String.format(INSERT, "pratica_tipo_id,", ":" + PH_PK + ",");
 public static final String INSERT_GENERIC = String.format(INSERT, "", "");

 public static final String NEXTVAL_PK_ID = "select ";
 public static final String UPDATE_GENERIC = UPDATE + SqlStatementCommon.SET + UPDATE_CAMPI
   + SqlStatementCommon.WHERE + PK_EQUALS;
 public static final String DELETE = UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE + PK_EQUALS;
 public static final String SELECT_ALL = SELECT + SqlStatementCommon.WHERE + VALIDO;
 public static final String SELECT_BY_ID = SELECT + SqlStatementCommon.WHERE + PK_EQUALS;


 public static final String SELECT_BY_ENTE_TIPO_ID = """
SELECT DISTINCT pt.*
FROM vigil_r_pratica_config pc
JOIN vigil_d_pratica_tipo   pt ON pt.pratica_tipo_id = pc.pratica_tipo_id  
WHERE pc.data_cancellazione IS NULL
 AND pc.validita_fine IS NULL
 AND pc.ente_tipo_id = :enteTipoId
ORDER BY pt.pratica_tipo_desc
""";
public static final String SELECT_STRUTTURA_TIPO_RIDOTTA_BY_ENTE_TIPO_ID = """
SELECT DISTINCT st.struttura_tipo_id, st.struttura_tipo_cod, st.struttura_tipo_desc
FROM vigil_r_pratica_config pc
JOIN vigil_d_struttura_tipo st ON st.struttura_tipo_id = pc.struttura_tipo_id
WHERE pc.data_cancellazione IS NULL
  AND pc.validita_fine IS NULL
  AND pc.ente_tipo_id = :enteTipoId
ORDER BY st.struttura_tipo_desc
""";


 private SqlStatementPraticaTipo() {
  throw new IllegalStateException("Utility class");
 }
}
