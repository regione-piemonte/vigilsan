/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.util.query;

public class SqlStatementStrutturaAccreditamento {
 

  public static final String PH_PK = "strutturaAccreditamentoId";
 public static final String SELECT = "select vdsa.* from vigil_d_struttura_accreditamento vdsa ";
 public static final String UPDATE = "update vigil_d_struttura_accreditamento vdsa";
 public static final String PK_EQUALS = "vdsa.struttura_accreditamento_id = :strutturaAccreditamentoId";
 public static final String VALIDO = "vdsa.data_cancellazione is null " +
         "AND now() BETWEEN vdsa.validita_inizio AND coalesce(vdsa.validita_fine, now()) ";

 public static final String UPDATE_CAMPI =   
   "struttura_accreditamento_cod = :strutturaAccreditamentoCod, " +
   "struttura_accreditamento_cod_arpe = :strutturaAccreditamentoCodArpe, " +
   "struttura_accreditamento_desc = :strutturaAccreditamentoDesc, " +
   "validita_inizio = :validitaInizio, " +
   "validita_fine = :validitaFine, " +
   "data_modifica = now(), " +
   "utente_modifica = :utenteModifica ";
 
 public static final String INSERT = 
   """
insert
 into
 vigil_d_struttura_accreditamento (struttura_accreditamento_cod,
 struttura_accreditamento_cod_arpe,
 struttura_accreditamento_desc,
 validita_inizio,
 validita_fine,
 data_creazione,
 data_modifica,
 data_cancellazione,
 utente_creazione,
 utente_modifica,
 utente_cancellazione)
values(:strutturaAccreditamentoCod,
:strutturaAccreditamentoCodArpe,
:strutturaAccreditamentoDesc,
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
 vigil_d_struttura_accreditamento (struttura_accreditamento_id, struttura_accreditamento_cod,
 struttura_accreditamento_cod_arpe,
 struttura_accreditamento_desc,
 validita_inizio,
 validita_fine,
 data_creazione,
 data_modifica,
 data_cancellazione,
 utente_creazione,
 utente_modifica,
 utente_cancellazione)
values(:strutturaAccreditamentoId, :strutturaAccreditamentoCod,
:strutturaAccreditamentoCodArpe,
:strutturaAccreditamentoDesc,
coalesce(:validitaInizio, now()), 
:validitaFine, 
now(), 
now(), 
null, 
:utenteCreazione, 
:utenteCreazione, 
null) 
    """;
 
public static final String SELECT_STRUTTURA_ACCREDITAMENTO_BY_STRUTTURA_ACCREDITAMENTO_COD_ARPE = """
 select * 
 from vigil_d_struttura_accreditamento vdsa 
 where vdsa.struttura_accreditamento_cod_arpe = :cod_arpe
 and vdsa.data_cancellazione is null 
 and now() between vdsa.validita_inizio and coalesce(vdsa.validita_fine, now())
 """; 

 public static final String NEXTVAL_PK_ID = 
 "select nextval('vigil_d_struttura_accreditament_struttura_accreditamento_id_seq'::regclass) ";
 
public static final String SELECT_BY_STRUTTRA_ID = null;











   private SqlStatementStrutturaAccreditamento() {

    }
}
