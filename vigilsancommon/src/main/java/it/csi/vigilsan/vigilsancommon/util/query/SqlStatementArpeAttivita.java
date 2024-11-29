/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.util.query;

public class SqlStatementArpeAttivita {
 

  public static final String PH_PK = "arpeAttivitaId";
 public static final String SELECT = "select * from vigil_d_arpe_attivita vdaa   ";
 public static final String UPDATE = "update vigil_d_arpe_attivita vdaa ";
 public static final String PK_EQUALS = "vdaa.arpe_attivita_id = :arpeAttivitaId";
 public static final String VALIDO = "vdaa.data_cancellazione is null " +
         "AND now() BETWEEN vdaa.validita_inizio AND coalesce(vdaa.validita_fine, now()) ";

 public static final String UPDATE_CAMPI =   
   "arpe_attivita_cod = :srutturaTipoCod, " +
   "arpe_attivita_desc = :srutturaTipoDesc, " +
   "validita_inizio = :validitaInizio, " +
   "validita_fine = :validitaFine, " +
   "data_modifica = now(), " +
   "utente_modifica = :utenteModifica ";
 
 public static final String INSERT = 
   """
insert
 into
 vigil_d_arpe_attivita (arpe_attivita_cod,
 arpe_attivita_desc,
 validita_inizio,
 validita_fine,
 data_creazione,
 data_modifica,
 data_cancellazione,
 utente_creazione,
 utente_modifica,
 utente_cancellazione)
values(:arpeAttivitaCod,
:arpeAttivitaDesc,
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
 vigil_d_arpe_attivita (arpe_attivita_id, arpe_attivita_cod,
 arpe_attivita_desc,
 validita_inizio,
 validita_fine,
 data_creazione,
 data_modifica,
 data_cancellazione,
 utente_creazione,
 utente_modifica,
 utente_cancellazione)
values(:arpeAttivitaId, :arpeAttivitaCod,
:arpeAttivitaDesc,
coalesce(:validitaInizio, now()), 
:validitaFine, 
now(), 
now(), 
null, 
:utenteCreazione, 
:utenteCreazione, 
null) 
     """;
 

// vigil_d_arpe_assistenza_tipo
public static final String SELECT_ATTIVITA_BY_ARPE_ATTIVITA_COD = """
select * 
from vigil_d_arpe_attivita vdaa 
where vdaa.arpe_attivita_cod = :cod_arpe
and vdaa.data_cancellazione is null 
and now() between vdaa.validita_inizio and coalesce(vdaa.validita_fine, now())
""";  

public static final String NEXTVAL_PK_ID = 
"select nextval('vigil_d_arpe_attivita_arpe_attivita_id_seq'::regclass) ";


   private SqlStatementArpeAttivita() {
      throw new IllegalStateException("Utility class");
    }
}
