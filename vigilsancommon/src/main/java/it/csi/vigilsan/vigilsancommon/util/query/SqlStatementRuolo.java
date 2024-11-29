/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.util.query;

public class SqlStatementRuolo {
 

	
public static final String PH_PK = "ruoloId";
 public static final String SELECT = "select vdr.* from vigil_d_ruolo vdr";
 public static final String UPDATE = "update vigil_t_soggetto vts";
 public static final String PK_EQUALS = "vdr.ruolo_id = :ruoloId";
 public static final String RUOLO_COD_EUQALS = "vdr.ruolo_cod = :ruoloCod";
 public static final String VALIDO = "vdr.data_cancellazione is null " +
         "AND now() BETWEEN vdr.validita_inizio AND coalesce(vdr.validita_fine, now()) ";

 public static final String UPDATE_CAMPI =   
   "ruolo_desc = :ruoloDesc, " +
   "validita_inizio = :validitaInizio, " +
   "validita_fine = :validitaFine, " +
   "data_modifica = now(), " +
   "utente_modifica = :utenteModifica ";
 
 public static final String INSERT = 
   "insert " +
   " into " +
   " vigil_d_ruolo (ruolo_cod, " +
   " ruolo_desc, " +
   " validita_inizio, " +
   " validita_fine, " +
   " data_creazione, " +
   " data_modifica, " +
   " data_cancellazione, " +
   " utente_creazione, " +
   " utente_modifica, " +
   " utente_cancellazione) " +
   "values(:ruoloCod, " +
   ":ruoloDesc, " +
         "coalesce(:validitaInizio, now()), "+
      ":validitaFine, "+
      "now(), "+
      "now(), "+
      "null, "+
      ":utenteCreazione, "+
      ":utenteCreazione, "+
   "null) " ;

   private SqlStatementRuolo() {
      throw new IllegalStateException("Utility class");
    }
}
