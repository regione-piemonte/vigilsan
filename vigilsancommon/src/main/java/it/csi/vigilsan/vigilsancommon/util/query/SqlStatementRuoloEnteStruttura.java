/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.util.query;

public class SqlStatementRuoloEnteStruttura {
 	
 public static final String PH_PK = "ruoloEnteStrutturaId";
 public static final String PH_COD = "ruoloEnteStrutturaCod";
 public static final String SELECT = "select vdrest.* from vigil_d_ruolo_ente_struttura vdrest";
 public static final String UPDATE = "update vigil_d_ruolo_ente_struttura vdrest";
 public static final String PK_EQUALS = "vdrest.ruolo_ente_struttura_id = :ruoloEnteStrutturaId";
 public static final String RUOLO_ENTE_STRUTTURA_COD_EQUALS = "vdrest.ruolo_ente_struttura_cod = :ruoloEnteStrutturaCod";
 public static final String VALIDO = "vdrest.data_cancellazione is null " +
         "AND now() BETWEEN vdrest.validita_inizio AND coalesce(vdrest.validita_fine, now()) ";
  
   public static final String UPDATE_CAMPI =   
		   "ruolo_ente_struttura_desc = :ruoloEnteStrutturaDesc, " +
		   "validita_inizio = :validitaInizio, " +
		   "validita_fine = :validitaFine, " +
		   "data_modifica = now(), " +
		   "utente_modifica = :utenteModifica ";   

	public static final String INSERT = 
			"insert " +
			" into " +
			" vigil_d_ruolo_ente_struttura (ruolo_ente_struttura_cod, " +
			" ruolo_ente_struttura_desc, " +
			" validita_inizio, " +
			" validita_fine, " +
			" data_creazione, " +
			" data_modifica, " +
			" data_cancellazione, " +
			" utente_creazione, " +
			" utente_modifica, " +
			" utente_cancellazione) " +
			"values(:ruoloEnteStrutturaCod, " +
			":ruoloEnteStrutturaDesc, " +
			"coalesce(:validitaInizio, now()), "+
			":validitaFine, "+
			"now(), "+
			"now(), "+
			"null, "+
			":utenteCreazione, "+
			":utenteCreazione, "+
			"null) " ;
	   
	private SqlStatementRuoloEnteStruttura() {
	    throw new IllegalStateException("Utility class");
	}
}
