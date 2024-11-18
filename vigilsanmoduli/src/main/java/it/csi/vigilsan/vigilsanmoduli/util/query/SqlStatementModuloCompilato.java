/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.util.query;

public class SqlStatementModuloCompilato {
 

 public static final String SELECT = "select vtmc.* from vigil_t_modulo_compilato vtmc";
 public static final String UPDATE = "update vigil_t_modulo_compilato vtmc";
 public static final String PK_EQUALS = "vtmc.modulo_compilato_id = :moduloCompilatoId";
 public static final String VALIDO = "vtmc.data_cancellazione is null " +
         "AND now() BETWEEN vtmc.validita_inizio AND coalesce(vtmc.validita_fine, now()) ";

 public static final String UPDATE_CAMPI =   
   "note = :note, " + 
   "modulo_id = :moduloId, " +
   "validita_inizio = :validitaInizio, " +
   "validita_fine = :validitaFine, " +
   "data_modifica = now(), " +
   "utente_modifica = :utenteModifica ";
 
 public static final String INSERT = "insert " +
 " into " +
 " vigil_t_modulo_compilato (modulo_id, " +
 " note, " +
 " validita_inizio, " +
 " validita_fine, " +
 " data_creazione, " +
 " data_modifica, " +
 " data_cancellazione, " +
 " utente_creazione, " +
 " utente_modifica, " +
 " utente_cancellazione) " +
 "values( :moduloId, " +
 ":note, " +
 "coalesce(:validitaInizio, now()), "+
 ":validitaFine, "+
 "now(), "+
 "now(), "+
 "null, "+
 ":utenteCreazione, "+
 ":utenteCreazione, "+
 "null) " ;
 
 public static final String INSERT_W_PK = "insert " +
 " into " +
 " vigil_t_modulo_compilato (modulo_compilato_id, modulo_id, " +
 " note, " +
 " validita_inizio, " +
 " validita_fine, " +
 " data_creazione, " +
 " data_modifica, " +
 " data_cancellazione, " +
 " utente_creazione, " +
 " utente_modifica, " +
 " utente_cancellazione) " +
 "values(:moduloCompilatoId, :moduloId, " +
 ":note, " +
 "coalesce(:validitaInizio, now()), "+
 ":validitaFine, "+
 "now(), "+
 "now(), "+
 "null, "+
 ":utenteCreazione, "+
 ":utenteCreazione, "+
 "null) " ;

 public static final String NEXTVAL_MODULO_COMPILATO_ID = 
 "select nextval('vigil_t_modulo_compilato_modulo_compilato_id_seq'::regclass) ";
 public static final String SELECT_NOTE_MODULO_COMPLIATO = """
		 select vtmcd.note  from  vigil_t_modulo_compilato_dettaglio vtmcd where
		  vtmcd.modulo_voce_id = :moduloVoceId
		  and vtmcd.modulo_compilato_id = :moduloCompilatoId limit 1
		 """;
 public static final String SELECT_PLACE_HOLDER_MODULO = """
SELECT DISTINCT mvr.modulo_voce_regola_exec load_key
FROM fnc_modulo(:moduloId)      m
JOIN vigil_d_modulo_voce        mv ON mv.modulo_id = m.modulo_id AND mv.data_cancellazione IS NULL
JOIN vigil_d_modulo_voce_regola mvr ON mvr.modulo_voce_id = mv.modulo_voce_id AND mvr.modulo_voce_regola_tipo = 'load' AND mvr.data_cancellazione IS NULL
ORDER BY 1
""";
 
   private SqlStatementModuloCompilato() {
      throw new IllegalStateException("Utility class");
    }
}
