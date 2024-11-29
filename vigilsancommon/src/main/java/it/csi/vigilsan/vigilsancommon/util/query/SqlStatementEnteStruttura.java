/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.util.query;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

public class SqlStatementEnteStruttura {
 

 public static final String PH_PK = "enteStrId";
 public static final String SELECT_ENTE_STRUTTURA = "select ves.* from vigil_r_ente_struttura ves ";
 public static final String UPDATE_ENTE_STRUTTURA = "update vigil_r_ente_struttura ves ";
 public static final String ENTE_STRUTTURA_ID_EQUALS = "ves.ente_str_id = :enteStrId";
 public static final String ENTE_STRUTTURA_VALIDO = "ves.data_cancellazione is null " +
         "AND now() BETWEEN ves.validita_inizio AND coalesce(ves.validita_fine, now()) ";

 public static final String UPDATE_ENTE_STRUTTURA_CAMPI =   
   "ente_id = :enteId, " +
   "validita_inizio = :validitaInizio, " +
   "validita_fine = :validitaFine, " +
   "data_modifica = now(), " +
   "utente_modifica = :utenteModifica ";
 
 public static final String INSERT_STRUTTURA = "insert " +
   " into " +
   " vigil_r_ente_struttura (ente_id, " +
   " struttura_id, " +
   " ruolo_ente_struttura_id, "+ 
   " validita_inizio, " +
   " validita_fine, " +
   " data_creazione, " +
   " data_modifica, " +
   " data_cancellazione, " +
   " utente_creazione, " +
   " utente_modifica, " +
   " utente_cancellazione) " +
   "values(:enteId, " +
   " :strutturaId, " +
   " :ruoloentestrutturaId, "+
   " coalesce(:validitaInizio, now()), " +
   " :validitaFine, " +
   " now(), " +
   " now(), " +
   " null, " +
   " :utenteCreazione, " +
   " :utenteCreazione, " +
   "  null) ";

 public static final String SELECT_ENTE_STRUTTURA_BY_STRUTTURA_ID = "select * " +
 " from vigil_r_ente_struttura ves " +
 " where ves.struttura_id = :struttura_id " +
 "and ves.ruolo_ente_struttura_id = :ruolo_ente_struttura_id " +
 "and ves.data_cancellazione is null " +
 "and now() between ves.validita_inizio and coalesce(ves.validita_fine, now())";
 
 public static final String UPDATE_ENTE_STRUTTURA_BATCH = " UPDATE vigil_r_ente_struttura ves SET "+
     "ente_id = :enteId, " +
     "struttura_id = :strutturaId, " + 
     "data_cancellazione = null, " +
     "utente_cancellazione = null, " +
     "data_modifica = now(), " +
     "utente_modifica = :utenteModifica  where " +
     "ente_str_id = :enteStrId";
 
 public static final String DELETE_ENTE_STRUTTURA = " update vigil_r_ente_struttura ves set "+ 
     " ves.data_cancellazione = :datainput," +
     " ves.utente_cancellazione = :utentecancellazione " +
     " where struttura_id = :strutturaId " +
     " and ves.ruolo_ente_struttura_id = :ruoloentestrutturaid " +
     " and ves.data_cancellazione is null "+
	 " and now() between ves.validita_inizio and coalesce(ves.validita_fine, now())";   
  
 public static final String REFRESH_ENTE_STRUTTURA = " update vigil_r_ente_struttura ves set "+ 
	     "data_modifica = :datainput," +
	     "utente_modifica = :utentemodifica " +
	     "where struttura_id = :strutturaId " +
	     "and ruolo_ente_struttura_id = :ruoloentestrutturaId " +
	     "and ves.data_cancellazione is null "+
		 "and now() between ves.validita_inizio and coalesce(ves.validita_fine, now())";   
	   
 
 public static final String NEXTVAL_PK_ID =  "select nextval('vigil_r_ente_struttura_ente_str_id_seq'::regclass) ";

 
    private SqlStatementEnteStruttura() {
      throw new IllegalStateException("Utility class");
    }
}