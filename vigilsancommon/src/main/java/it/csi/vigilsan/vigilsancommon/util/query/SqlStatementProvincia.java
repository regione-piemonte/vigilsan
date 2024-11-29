/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.util.query;

public class SqlStatementProvincia {
 

  public static final String PH_PK = "provinciaId";
 public static final String SELECT = "select vtp.* from vigil_t_provincia vtp ";
 public static final String UPDATE = "update vigil_t_provincia vtp";
 public static final String PK_EQUALS = "vtp.provincia_id = :provinciaId";
 public static final String VALIDO = "vtp.data_cancellazione is null " +
         "AND now() BETWEEN vtp.validita_inizio AND coalesce(vtp.validita_fine, now()) ";

 public static final String UPDATE_CAMPI =   
   "provincia_cod = :provinciaCod, " +
   "provincia_sigla = :provinciaSigla, " +
   "provincia_desc = :provinciaDesc, " +
   "regione_id = :regioneId, " +
   "validita_inizio = :validitaInizio, " +
   "validita_fine = :validitaFine, " +
   "data_modifica = now(), " +
   "utente_modifica = :utenteModifica ";
 
 public static final String INSERT = 
   """
insert
 into
 vigil_t_provincia (provincia_cod,
 provincia_sigla,
 provincia_desc,
 regione_id,
 validita_inizio,
 validita_fine,
 data_creazione,
 data_modifica,
 data_cancellazione,
 utente_creazione,
 utente_modifica,
 utente_cancellazione)
values(:provinciaCod,
:provinciaSigla,
:provinciaDesc,
:regioneId,
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
 vigil_t_provincia (provincia_id, provincia_cod,
 provincia_sigla,
 provincia_desc,
 regione_id,
 validita_inizio,
 validita_fine,
 data_creazione,
 data_modifica,
 data_cancellazione,
 utente_creazione,
 utente_modifica,
 utente_cancellazione)
values(:provinciaId, :provinciaCod,
:provinciaSigla,
:provinciaDesc,
:regioneId,
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
 "select nextval('vigil_t_provincia_provincia_id_seq'::regclass) ";

   private SqlStatementProvincia() {
      throw new IllegalStateException("Utility class");
    }
}
