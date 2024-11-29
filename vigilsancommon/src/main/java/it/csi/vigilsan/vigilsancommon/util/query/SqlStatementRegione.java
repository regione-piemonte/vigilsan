/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.util.query;

public class SqlStatementRegione {
 

  public static final String PH_PK = "regioneId";
 public static final String SELECT = "select vtr.* from vigil_t_regione vtr ";
 public static final String UPDATE = "update vigil_t_regione vtr";
 public static final String PK_EQUALS = "vtr.regione_id = :regioneId";
 public static final String VALIDO = "vtr.data_cancellazione is null " +
         "AND now() BETWEEN vtr.validita_inizio AND coalesce(vtr.validita_fine, now()) ";

 public static final String UPDATE_CAMPI =   
   "regione_cod = :regioneCod, " +
   "regione_desc = :regioneDesc, " +
   "validita_inizio = :validitaInizio, " +
   "validita_fine = :validitaFine, " +
   "data_modifica = now(), " +
   "utente_modifica = :utenteModifica ";
 
 public static final String INSERT = 
   """
insert
 into
 vigil_t_regione (regione_cod,
 regione_desc,
 validita_inizio,
 validita_fine,
 data_creazione,
 data_modifica,
 data_cancellazione,
 utente_creazione,
 utente_modifica,
 utente_cancellazione)
values(:regioneCod,
:regioneDesc,
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
 vigil_t_regione (regione_id, regione_cod,
 regione_desc,
 validita_inizio,
 validita_fine,
 data_creazione,
 data_modifica,
 data_cancellazione,
 utente_creazione,
 utente_modifica,
 utente_cancellazione)
values(:regioneId, :regioneCod,
:regioneDesc,
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
 "select nextval('vigil_t_regione_regione_id_seq'::regclass)";
 
public static final String SELECT_BY_STRUTTRA_ID = null;

   private SqlStatementRegione() {
      throw new IllegalStateException("Utility class");
    }
}
