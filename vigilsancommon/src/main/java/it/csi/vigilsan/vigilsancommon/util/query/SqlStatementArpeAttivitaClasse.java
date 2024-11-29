/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.util.query;

public class SqlStatementArpeAttivitaClasse {
 

  public static final String PH_PK = "arpeAttivitaClasseId";
 public static final String SELECT = "select * from vigil_d_arpe_attivita_classe vdaac ";
 public static final String UPDATE = "update vigil_d_arpe_attivita_classe vdaac ";
 public static final String PK_EQUALS = "vdaac.arpe_attivita_classe_id = :arpeAttivitaClasseId ";
 public static final String VALIDO = "and vdaac.data_cancellazione is null  \r\n"
 		+ " and now() between vdaac.validita_inizio and coalesce(vdaac.validita_fine, now()) ";

 
 public static final String UPDATE_CAMPI =   
   "arpe_attivita_classe_cod = :arpeAttivitaClasseCod, " +
   "arpe_attivita_classe_desc = :arpeAttivitaClasseDesc, " +
   "validita_inizio = :validitaInizio, " +
   "validita_fine = :validitaFine, " +
   "data_modifica = now(), " +
   "utente_modifica = :utenteModifica ";
 
 public static final String INSERT = 
   """
insert
 into
 vigil_d_arpe_attivita_classe (arpe_attivita_classe_cod,
 arpe_attivita_classe_desc,
 validita_inizio,
 validita_fine,
 data_creazione,
 data_modifica,
 data_cancellazione,
 utente_creazione,
 utente_modifica,
 utente_cancellazione)
values(:arpeAttivitaClasseCod,
:arpeAttivitaClasseDesc,
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
 vigil_d_arpe_attivita_classe (arpe_attivita_classe_id, arpe_attivita_classe_cod,
 arpe_attivita_classe_desc,
 validita_inizio,
 validita_fine,
 data_creazione,
 data_modifica,
 data_cancellazione,
 utente_creazione,
 utente_modifica,
 utente_cancellazione)
values(:arpeAttivitaClasseId, 
:arpeAttivitaClasseCod,
:arpeAttivitaClasseDesc,
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
public static final String SELECT_ATTIVITA_CLASSE_BY_ARPE_ATTIVITA_CLASSE_COD = """
select * 
from vigil_d_arpe_attivita_classe vdaac 
where vdaac.arpe_attivita_classe_cod = :cod_arpe 
and vdaac.data_cancellazione is null 
and now() between vdaac.validita_inizio and coalesce(vdaac.validita_fine, now())
""";  

public static final String NEXTVAL_PK_ID = 
"select nextval('vigil_d_arpe_attivita_classe_arpe_attivita_classe_id_seq'::regclass) ";


   private SqlStatementArpeAttivitaClasse() {
      throw new IllegalStateException("Utility class");
    }
}
