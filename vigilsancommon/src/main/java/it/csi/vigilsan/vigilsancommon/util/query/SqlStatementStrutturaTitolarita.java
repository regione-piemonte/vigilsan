/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.util.query;

public class SqlStatementStrutturaTitolarita {
 

  public static final String PH_PK = "strutturaTitolaritaId";
 public static final String SELECT = "select vdst.* from vigil_d_struttura_titolarita vdst ";
 public static final String UPDATE = "update vigil_d_struttura_titolarita vdst";
 public static final String PK_EQUALS = "vdst.struttura_titolarita_id = :strutturaTitolaritaId";
 public static final String VALIDO = "vdst.data_cancellazione is null " +
         "AND now() BETWEEN vdst.validita_inizio AND coalesce(vdst.validita_fine, now()) ";

 public static final String UPDATE_CAMPI =   
   "struttura_titolarita_cod = :strutturaTitolaritaCod, " +
   "struttura_titolarita_cod_arpe = :strutturaTitolaritaCodArpe, " +
   "struttura_titolarita_desc = :strutturaTitolaritaDesc, " +
   "validita_inizio = :validitaInizio, " +
   "validita_fine = :validitaFine, " +
   "data_modifica = now(), " +
   "utente_modifica = :utenteModifica ";
 
 public static final String INSERT = 
   """
insert
 into
 vigil_d_struttura_titolarita (struttura_titolarita_cod,
 struttura_titolarita_cod_arpe,
 struttura_titolarita_desc,
 validita_inizio,
 validita_fine,
 data_creazione,
 data_modifica,
 data_cancellazione,
 utente_creazione,
 utente_modifica,
 utente_cancellazione)
values(:strutturaTitolaritaCod,
:strutturaTitolaritaCodArpe,
:strutturaTitolaritaDesc,
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
 vigil_d_struttura_titolarita (struttura_titolarita_id, 
 struttura_titolarita_cod,
 struttura_titolarita_cod_arpe,
 struttura_titolarita_desc,
 validita_inizio,
 validita_fine,
 data_creazione,
 data_modifica,
 data_cancellazione,
 utente_creazione,
 utente_modifica,
 utente_cancellazione)
values(:strutturaTitolaritaId, 
:strutturaTitolaritaCod,
:strutturaTitolaritaCodArpe,
:strutturaTitolaritaDesc,
coalesce(:validitaInizio, now()), 
:validitaFine, 
now(), 
now(), 
null, 
:utenteCreazione, 
:utenteCreazione, 
null) 
    """;
 
public static final String SELECT_STRUTTURA_TITOLARITA_BY_STRUTTURA_TITOLARITA_COD_ARPE = """
 select * 
 from vigil_d_struttura_titolarita vdst 
 where vdst.struttura_titolarita_cod_arpe = :cod_arpe
 and vdst.data_cancellazione is null 
 and now() between vdst.validita_inizio and coalesce(vdst.validita_fine, now())
 """; 

 public static final String NEXTVAL_PK_ID = 
 "select nextval('vigil_d_struttura_titolarita_struttura_titolarita_id_seq'::regclass) ";
 
public static final String SELECT_BY_STRUTTRA_ID = null;











   private SqlStatementStrutturaTitolarita() {

    }
}
