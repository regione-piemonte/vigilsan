/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.util.query;

public class SqlStatementOspiteStato {
	 

	  public static final String PH_PK = "ospiteStatoId";
	 public static final String SELECT = "select * from vigil_d_ospite_stato vdos ";
	 public static final String UPDATE = "update vigil_d_ospite_stato vdos ";
	 public static final String PK_EQUALS = "vdos.ospite_stato_id = :ospiteStatoId ";
	 public static final String VALIDO = " vdos.data_cancellazione is null  "
	   + " and now() between vdos.validita_inizio and coalesce(vdos.validita_fine, now()) ";

	 
	 public static final String UPDATE_CAMPI =   
	   """
	 ospite_stato_cod = :ospiteStatoCod,
	 ospite_stato_desc = :ospiteStatoDesc,
	 ospite_stato_ord = :ospiteStatoOrd,
	 ospite_stato_hint = :ospiteStatoHint,
	 flg_presenza = :flgPresenza,
	 flg_posto = :flgPosto,
	 validita_inizio = :validitaInizio,
	 validita_fine = :validitaFine, 
	 data_modifica = now(), 
	 utente_modifica = :utenteModifica
	     """;
	 
	 public static final String INSERT = 
	   """
	insert
	 into
	 vigilsan.vigil_d_ospite_stato
	(
	 ospite_stato_cod,
	 ospite_stato_desc,
	 ospite_stato_ord,
	 ospite_stato_hint,
	 flg_presenza,
	 flg_posto,
	 validita_inizio,
	 validita_fine,
	 data_creazione,
	 data_modifica,
	 data_cancellazione,
	 utente_creazione,
	 utente_modifica,
	 utente_cancellazione)
	values(
	:ospiteStatoCod,
	:ospiteStatoDesc,
	:ospiteStatoOrd,
	:ospiteStatoHint,
	:flgPresenza,
	:flgPosto,
	coalesce(:validitaInizio, now()), 
	:validitaFine, 
	now(), 
	now(), 
	null, 
	:utenteCreazione, 
	:utenteCreazione, 
	null) 
	     """;
	 
	 public static final String INSERT_W_PK = 
	   """
	insert
	 into
	 vigilsan.vigil_d_ospite_stato
	(ospite_stato_id,
	 ospite_stato_cod,
	 ospite_stato_desc,
	 ospite_stato_ord,
	 ospite_stato_hint,
	 flg_presenza,
	 flg_posto,
	 validita_inizio,
	 validita_fine,
	 data_creazione,
	 data_modifica,
	 data_cancellazione,
	 utente_creazione,
	 utente_modifica,
	 utente_cancellazione)
	values(:ospiteStatoId,
	:ospiteStatoCod,
	:ospiteStatoDesc,
	:ospiteStatoOrd,
	:ospiteStatoHint,
	:flgPresenza,
	:flgPosto,
	coalesce(:validitaInizio, now()), 
	:validitaFine, 
	now(), 
	now(), 
	null, 
	:utenteCreazione, 
	:utenteCreazione, 
	null) 
	     """;
	 
	public static final String NEXTVAL_PK_ID = 
	"select nextval('vigil_d_ospite_stato_ospite_stato_id_seq'::regclass) ";


	   private SqlStatementOspiteStato() {
	      throw new IllegalStateException("Utility class");
	    }
}
