/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.util.query;

public class SqlStatementModuloCompilatoDettaglio {
 

 public static final String SELECT = "select vtmcd.* from vigil_t_modulo_compilato_dettaglio vtmcdd";
 public static final String UPDATE = "update vigil_t_modulo_compilato_dettaglio vtmcdd";
 public static final String PK_EQUALS = "vtmcd.modulo_compilato_dettaglio_id = :moduloCompilatoDettaglioId";
 public static final String VALIDO = "vtmcd.data_cancellazione is null " +
         "AND now() BETWEEN vtmcd.validita_inizio AND coalesce(vtmcd.validita_fine, now()) ";

 public static final String UPDATE_CAMPI =   
   "valore = :valore, " +    
   "note = :note, " +    
   "modulo_compilato_id = :moduloCompilatoId, " +    
   "modulo_voce_id = :moduloVoceId, " +    
   "modulo_lista_valore_id = :moduloListaValoreId, " +    
   "file_id = :fileId, " + 
   "validita_inizio = :validitaInizio, " +
   "validita_fine = :validitaFine, " +
   "data_modifica = now(), " +
   "utente_modifica = :utenteModifica ";
 
 public static final String INSERT = 
   "insert " +
     " into " +
     " vigil_t_modulo_compilato_dettaglio (valore, " +
     " note, " +
     " modulo_compilato_id, " +
     " modulo_voce_id, " +
     " modulo_lista_valore_id, " +
     " file_id, " +
     " flg_check, " +
     " validita_inizio, " +
     " validita_fine, " +
     " data_creazione, " +
     " data_modifica, " +
     " data_cancellazione, " +
     " utente_creazione, " +
     " utente_modifica, " +
     " utente_cancellazione) " +
     "values(:valore, " +
     ":note, " +
     ":moduloCompilatoId, " +
     ":moduloVoceId, " +
     ":moduloListaValoreId, " +
     ":fileId, " +
     ":flgCheck, " +
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
 "values(moduloCompilatoId, moduloId, " +
 "note, " +
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
 
   private SqlStatementModuloCompilatoDettaglio() {
      throw new IllegalStateException("Utility class");
    }
}
