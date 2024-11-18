/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanrilevazioni.util.query;

public class SqlStatementRilevazione {
 

 public static final String PH_PK = "rilevazioneId";
 public static final String TABLE = "vigil_t_rilevazione";
 public static final String SELECT = "select vtr.* from vigil_t_rilevazione vtr";
 public static final String UPDATE = "update vigil_t_rilevazione vtr";
 public static final String PK_EQUALS = "vtr.rilevazione_id = :rilevazioneId";
 public static final String VALIDO = "vtr.data_cancellazione is null " +
     "AND now() BETWEEN vtr.validita_inizio AND coalesce(vtr.validita_fine, now()) ";

 public static final String UPDATE_CAMPI =  
  "struttura_id = :strutturaId, " +
  "str_cat_id = :strCatId, " +
  "modulo_compilato_id = :moduloCompilatoId, " +
  "modulo_config_id = :moduloConfigId, " +
  "dataora_rilevazione = :dataoraRilevazione, " +
  "validita_inizio = :validitaInizio, " +
  "validita_fine = :validitaFine, " +
  "data_modifica = now(), " +
  "utente_modifica = :utenteModifica ";
 
 public static final String INSERT = 
 "insert " +
 " into " +
 " vigil_t_rilevazione (struttura_id, " +
 " str_cat_id, " +
 " modulo_compilato_id, " +
 " modulo_config_id, " +
 " dataora_rilevazione, " +
 " validita_inizio, " +
 " validita_fine, " +
 " data_creazione, " +
 " data_modifica, " +
 " data_cancellazione, " +
 " utente_creazione, " +
 " utente_modifica, " +
 " utente_cancellazione) " +
 "values(:strutturaId, " +
 ":strCatId, " +
 ":moduloCompilatoId, " +
 ":moduloConfigId, " +
 ":dataoraRilevazione, " +
 "coalesce(:validitaInizio, now()), "+
 ":validitaFine, "+
 "now(), "+
 "now(), "+
 "null, "+
 ":utenteCreazione, "+
 ":utenteCreazione, "+
  "null) " ;
 
 public static final String INSERT_W_PK = 
 "insert " +
 " into " +
 " vigil_t_rilevazione (rilevazione_id, struttura_id, " +
 " str_cat_id, " +
 " modulo_compilato_id, " +
 " modulo_config_id, " +
 " dataora_rilevazione, " +
 " validita_inizio, " +
 " validita_fine, " +
 " data_creazione, " +
 " data_modifica, " +
 " data_cancellazione, " +
 " utente_creazione, " +
 " utente_modifica, " +
 " utente_cancellazione) " +
 "values(:rilevazioneId, :strutturaId, " +
 ":strCatId, " +
 ":moduloCompilatoId, " +
 ":moduloConfigId, " +
 ":dataoraRilevazione, " +
 "coalesce(:validitaInizio, now()), "+
 ":validitaFine, "+
 "now(), "+
 "now(), "+
 "null, "+
 ":utenteCreazione, "+
 ":utenteCreazione, "+
  "null) " ;
 

 public static final String NEXTVAL_PK_ID = 
 "select nextval('vigil_t_rilevazione_rilevazione_id_seq'::regclass) ";


 public static final String SELECT_RILEVAZIONE_DA_COMPILARE_ESTESO_MAPPER = 
  """
SELECT *, null::int4 rilevazione_id FROM fnc_rilevazione_inseribile_by_struttura(:strutturaId)
where modulo_config_cod = :moduloConfigCod
  """;

 public static final String SELECT_RILEVAZIONE_COMPILATE_ESTESO_MAPPER = 
"""
SELECT
  r.rilevazione_id,
  r.struttura_id,
  r.str_cat_id,
  r.modulo_compilato_id,
  r.modulo_config_id,
  r.dataora_rilevazione,
  r.validita_inizio,
  r.validita_fine,
  mc.modulo_id,
  m.modulo_cod,
  m.modulo_desc,
  m.modulo_ord,
  COUNT(*) OVER() AS total_count
FROM vigil_t_struttura   s
JOIN vigil_t_rilevazione  r ON r.struttura_id = s.struttura_id AND
r.data_cancellazione IS NULL
JOIN vigil_d_modulo_config mc ON mc.modulo_config_id = r.modulo_config_id
JOIN vigil_d_modulo    m ON m.modulo_id = mc.modulo_id
WHERE s.data_cancellazione IS NULL
  AND s.struttura_id = :strutturaId
  AND mc.modulo_config_cod = :moduloConfigCod
ORDER BY r.dataora_rilevazione DESC
   """;

public static final String DELETE_OLD_RILEVAZIONI = """
UPDATE vigil_t_rilevazione x set data_cancellazione = now(), utente_cancellazione = :utenteCancellazione 
 WHERE x.data_cancellazione IS NULL
 AND x.struttura_id = :strutturaId
 AND x.modulo_config_id = :moduloConfigId
 AND COALESCE(x.str_cat_id,-1) = COALESCE(:strCatId,-1)
 AND x.dataora_rilevazione = :dataoraRilevazione
""";

  private SqlStatementRilevazione() {
   throw new IllegalStateException("Utility class");
  }
}
