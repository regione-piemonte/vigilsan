/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.util.query;

public class SqlStatementArpeDisciplina {
 

  public static final String PH_PK = "arpeDisciplinaId";
 public static final String SELECT = "select * from vigil_d_arpe_disciplina vdad  ";
 public static final String UPDATE = "update vigil_d_arpe_disciplina vdad ";
 public static final String PK_EQUALS = "vdad.arpe_disciplina_id = :arpeDisciplinaId ";
 public static final String VALIDO = "and vdad.data_cancellazione is null  \r\n"
 		+ " and now() between vdad.validita_inizio and coalesce(vdad.validita_fine, now()) ";

 
 public static final String UPDATE_CAMPI =   
   "arpe_disciplina_cod = :arpeDisciplinaCod, " +
   "arpe_disciplina_desc = :arpeDisciplinaDesc, " +
   "validita_inizio = :validitaInizio, " +
   "validita_fine = :validitaFine, " +
   "data_modifica = now(), " +
   "utente_modifica = :utenteModifica ";
 
 public static final String INSERT = 
   """
insert
 into
 vigil_d_arpe_disciplina (arpe_disciplina_cod,
 arpe_disciplina_desc,
 validita_inizio,
 validita_fine,
 data_creazione,
 data_modifica,
 data_cancellazione,
 utente_creazione,
 utente_modifica,
 utente_cancellazione)
values(:arpeDisciplinaCod,
:arpeDisciplinaDesc,
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
 vigil_d_arpe_disciplina (arpe_disciplina_id, 
 arpe_disciplina_cod,
 arpe_disciplina_desc,
 validita_inizio,
 validita_fine,
 data_creazione,
 data_modifica,
 data_cancellazione,
 utente_creazione,
 utente_modifica,
 utente_cancellazione)
values(:arpeDisciplinaId, 
:arpeDisciplinaCod,
:arpeDisciplinaDesc,
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
public static final String SELECT_DISCIPLINA_BY_ARPE_DISCIPLINA_COD = """
select * 
from vigil_d_arpe_disciplina vdad  
where vdad.arpe_disciplina_cod = :cod_arpe 
and vdad.data_cancellazione is null 
and now() between vdad.validita_inizio and coalesce(vdad.validita_fine, now())
""";  

public static final String NEXTVAL_PK_ID = 
"select nextval('vigil_d_arpe_disciplina_arpe_disciplina_id_seq'::regclass) ";


   private SqlStatementArpeDisciplina() {
      throw new IllegalStateException("Utility class");
    }
}
