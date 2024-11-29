/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.util.query;

public class SqlStatementProfilo {
	
public static final String PH_PK = "profiloId";
 public static final String SELECT = "select vdp.* from vigil_d_profilo vdp";
 public static final String UPDATE = "update vigil_d_profilo vts";
 public static final String PK_EQUALS = "vdp.profilo_id = :profiloId";
 public static final String PROFILO_COD_EQUALS = "vdp.profilo_cod = :profiloCod";
 public static final String VALIDO = "vdp.data_cancellazione is null " +
         "AND now() BETWEEN vdp.validita_inizio AND coalesce(vdp.validita_fine, now()) ";

 public static final String UPDATE_CAMPI =   
   "profilo_desc = :profiloDesc, " +
   "validita_inizio = :validitaInizio, " +
   "validita_fine = :validitaFine, " +
   "data_modifica = now(), " +
   "utente_modifica = :utenteModifica ";
 
 public static final String INSERT = 
   "insert " +
   " into " +
   " vigil_d_ruolo (profilo_cod, " +
   " profilo_desc, " +
   " validita_inizio, " +
   " validita_fine, " +
   " data_creazione, " +
   " data_modifica, " +
   " data_cancellazione, " +
   " utente_creazione, " +
   " utente_modifica, " +
   " utente_cancellazione) " +
   "values(:profiloCod, " +
   ":profiloDesc, " +
         "coalesce(:validitaInizio, now()), "+
      ":validitaFine, "+
      "now(), "+
      "now(), "+
      "null, "+
      ":utenteCreazione, "+
      ":utenteCreazione, "+
   "null) " ;

   private SqlStatementProfilo() {
      throw new IllegalStateException("Utility class");
    }
}
