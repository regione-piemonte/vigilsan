/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.util.query;

public class SqlStatementNatura {
 

  public static final String PH_PK = "strutturaNaturaId";
 public static final String SELECT = "select vdsn.* from vigil_d_struttura_natura vdsn";
 public static final String UPDATE = "update vigil_d_struttura_natura vdsn";
 public static final String PK_EQUALS = "vdsn.struttura_natura_id = :strutturaNaturaId";
 public static final String VALIDO = "vdsn.data_cancellazione is null " +
         "AND now() BETWEEN vdsn.validita_inizio AND coalesce(vdsn.validita_fine, now()) ";

 public static final String UPDATE_CAMPI =   
   "struttura_natura_cod = :strutturaNaturaCod, " +
   "struttura_natura_cod_arpe = :strutturaNaturaCodArpe, " +
   "struttura_natura_desc = :strutturaNaturaDesc, " +
   "validita_inizio = :validitaInizio, " +
   "validita_fine = :validitaFine, " +
   "data_modifica = now(), " +
   "utente_modifica = :utenteModifica ";
 
 public static final String INSERT = 
   """
insert
 into
 vigil_d_struttura_natura (struttura_natura_cod,
 struttura_natura_cod_arpe,
 struttura_natura_desc,
 validita_inizio,
 validita_fine,
 data_creazione,
 data_modifica,
 data_cancellazione,
 utente_creazione,
 utente_modifica,
 utente_cancellazione)
values(:strutturaNaturaCod,
:strutturaNaturaCodArpe,
:strutturaNaturaDesc,
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
 vigil_d_struttura_natura (struttura_natura_id, struttura_natura_cod,
 struttura_natura_cod_arpe,
 struttura_natura_desc,
 validita_inizio,
 validita_fine,
 data_creazione,
 data_modifica,
 data_cancellazione,
 utente_creazione,
 utente_modifica,
 utente_cancellazione)
values(:strutturaNaturaId, :strutturaNaturaCod,
:strutturaNaturaCodArpe,
:strutturaNaturaDesc,
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
 "select nextval('vigil_d_struttura_natura_struttura_natura_id_seq'::regclass) ";
 
public static final String SELECT_BY_STRUTTRA_ID = null;


public static final String SELECT_STRUTTURA_NATURA_BY_STRUTTURA_NATURA_COD_ARPE = """
select * 
from vigil_d_struttura_natura vdsn 
where struttura_natura_cod_arpe = :cod_arpe
and vdsn.data_cancellazione is null
and now() between vdsn.validita_inizio and coalesce(vdsn.validita_fine, now())
"""; 

private SqlStatementNatura() {
    throw new IllegalStateException("Utility class");
  }
}


