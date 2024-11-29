/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.util.query;

public class SqlStatementComune {
 

  public static final String PH_PK = "comuneId";
 public static final String SELECT = "select vtc.* from vigil_t_comune vtc ";
 public static final String UPDATE = "update vigil_t_comune vtc";
 public static final String PK_EQUALS = "vtc.comune_id = :comuneId";
 public static final String VALIDO = "vtc.data_cancellazione is null " +
         "AND now() BETWEEN vtc.validita_inizio AND coalesce(vtc.validita_fine, now()) ";

 public static final String UPDATE_CAMPI =   
   "comune_cod = :comuneCod, " +
   "comune_desc = :comuneDesc, " +
   "provincia_id = :provinciaId, " +
   "validita_inizio = :validitaInizio, " +
   "validita_fine = :validitaFine, " +
   "data_modifica = now(), " +
   "utente_modifica = :utenteModifica ";
 
 public static final String INSERT = 
   """
insert
 into
 vigil_t_comune (comune_cod,
 comune_desc,
 provincia_id,
 validita_inizio,
 validita_fine,
 data_creazione,
 data_modifica,
 data_cancellazione,
 utente_creazione,
 utente_modifica,
 utente_cancellazione)
values(:comuneDoc,
:comuneDesc,
:provinciaId,
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
 vigil_t_comune (comune_id, comune_cod,
 comune_desc,
 provincia_id,
 validita_inizio,
 validita_fine,
 data_creazione,
 data_modifica,
 data_cancellazione,
 utente_creazione,
 utente_modifica,
 utente_cancellazione)
values(:comuneId, :comuneDoc,
:comuneDesc,
:provinciaId,
coalesce(:validitaInizio, now()), 
:validitaFine, 
now(), 
now(), 
null, 
:utenteCreazione, 
:utenteCreazione, 
null) 
""";
 
//vigil_d_arpe_assistenza_tipo
public static final String SELECT_COMUNE_BY_COMUNE_COD = """
select * 
from vigil_t_comune vtc 
where vtc.comune_cod = :comune_cod 
and vtc.data_cancellazione is null 
and now() between vtc.validita_inizio and coalesce(vtc.validita_fine, now())
"""; 

 public static final String NEXTVAL_PK_ID = 
 "select nextval('vigil_t_comune_comune_id_seq'::regclass) ";
 
public static final String SELECT_BY_STRUTTRA_ID = null;

   private SqlStatementComune() {
      throw new IllegalStateException("Utility class");
    }
}
