/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.util.query;

public class SqlStatementOperazioneProfilo {
 

public static final String SELECT_APPLICATIVO_OPERAZIONE_BY_APPLICATIVO_ID_PROFILO_ID = """
SELECT o.operazione_cod, p.profilo_id,
 (SELECT op.abilitazione
 FROM vigil_r_operazione_profilo op
 WHERE op.operazione_id = o.operazione_id
  AND op.profilo_id = p.profilo_id
  AND (op.struttura_tipo_id is null or op.struttura_tipo_id = (select struttura_tipo_id from vigil_t_struttura where struttura_id = :strutturaId )) 
  AND op.data_cancellazione IS NULL
  AND NOW() BETWEEN op.validita_inizio AND COALESCE(op.validita_fine, NOW())
 )
FROM vigil_d_operazione o, vigil_d_profilo p
WHERE o.applicativo_id = :applicativoId
 AND o.data_cancellazione IS NULL
 AND NOW() BETWEEN o.validita_inizio AND COALESCE(o.validita_fine, NOW())
 AND p.profilo_id = :profiloId
 AND p.data_cancellazione IS NULL
 AND NOW() BETWEEN p.validita_inizio AND COALESCE(p.validita_fine, NOW())
ORDER BY operazione_ord
""";
public static final String SELECT_APPLICATIVO_OPERAZIONE_RIDOTTO_BY_APPLICATIVO_ID_PROFILO_ID = """
WITH
a AS (
    SELECT o.operazione_cod, o.operazione_ord, p.profilo_id,
      COALESCE((SELECT op.abilitazione
      FROM vigil_r_operazione_profilo op
      WHERE op.operazione_id = o.operazione_id
        AND op.profilo_id = p.profilo_id
        AND (op.struttura_tipo_id is null or op.struttura_tipo_id = :strutturaTipoId) 
        AND op.data_cancellazione IS NULL
        AND NOW() BETWEEN op.validita_inizio AND COALESCE(op.validita_fine, NOW())
      ),'') abilitazione
    FROM vigil_d_operazione o, vigil_d_profilo p
    WHERE o.applicativo_id = :applicativoId
      AND o.data_cancellazione IS NULL
      AND NOW() BETWEEN o.validita_inizio AND COALESCE(o.validita_fine, NOW())
      AND p.profilo_id = :profiloId
      AND p.data_cancellazione IS NULL
      AND NOW() BETWEEN p.validita_inizio AND COALESCE(p.validita_fine, NOW())
  ),
  ap AS (
    SELECT DISTINCT ON (a1.operazione_cod) a1.*, COALESCE(a2.operazione_cod,'') operazione_cod_padre, COALESCE(a2.abilitazione,'') abilitazione_padre
    FROM      a a1
    LEFT JOIN a a2 ON a1.operazione_cod LIKE a2.operazione_cod||'-%'
    ORDER BY a1.operazione_cod, LENGTH(a2.operazione_cod) DESC
  )
SELECT *
FROM ap
WHERE abilitazione != abilitazione_padre
ORDER BY operazione_ord
""";

  private SqlStatementOperazioneProfilo() {
   throw new IllegalStateException("Utility class");
  }
}
