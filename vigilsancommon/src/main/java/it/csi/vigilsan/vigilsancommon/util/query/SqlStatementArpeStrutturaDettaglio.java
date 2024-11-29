/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.util.query;

public class SqlStatementArpeStrutturaDettaglio {
 

 public static final String PH_PK = "arpeStrDettId";
 public static final String SELECT = "select * from vigil_r_arpe_struttura_dettaglio vrasd ";
 public static final String UPDATE = "update vigil_r_arpe_struttura_dettaglio vrasd";
 public static final String PK_EQUALS = "vrasd.arpe_str_dett_id = :arpeStrDettId";
 public static final String VALIDO = "vrasd.data_cancellazione is null " +
         "AND now() BETWEEN vrasd.validita_inizio AND coalesce(vrasd.validita_fine, now()) ";

 
 public static final String UPDATE_CAMPI =   
   "arpe_str_dett_id = :arpeStrDettId, " +
   "struttura_id = :strutturaId, " +
   "arpe_struttura_tipo_id = :arpeStrutturaTipoId, " +
   "arpe_assistenza_tipo_id = :arpeAssistenzaTipoId, " +
   "arpe_attivita_id = :arpeAttivitaId, " +
   "arpe_attivita_classe_id = :arpeAttivitaClasseId, " +   
   "validita_inizio = :validitaInizio, " +
   "validita_fine = :validitaFine, " +
   "data_modifica = now(), " +
   "utente_modifica = :utenteModifica ";
 
 
 public static final String INSERT = 
   """
insert
 into
 vigil_r_arpe_struttura_dettaglio (arpe_str_dett_id,
 struttura_id,
 arpe_struttura_tipo_id,
 arpe_assistenza_tipo_id,
 arpe_attivita_id,
 arpe_attivita_classe_id,
 arpe_disciplina_id,
 validita_inizio,
 validita_fine,
 data_creazione,
 data_modifica,
 data_cancellazione,
 utente_creazione,
 utente_modifica,
 utente_cancellazione,
 arpe_data_attivazione_struttura,
 arpe_data_attivazione_assistenza,
 arpe_data_attivazione_attivita)
values(:arpeStrDettId, 
:strutturaId,
:arpeStrutturaTipoId,
:arpeAssistenzaTipoId,
:arpeAttivitaId,
:arpeAttivitaClasseId,
:arpeDisciplinaId,
coalesce(:validitaInizio, now()), 
:validitaFine, 
now(), 
now(), 
null, 
:utenteCreazione, 
:utenteCreazione, 
null,
:arpe_data_attivazione_struttura,
:arpe_data_attivazione_assistenza,
:arpe_data_attivazione_attivita
) 
     """;

 
 
 public static final String INSERT_W_PK = 
   """
insert
 into
 vigil_r_arpe_struttura_dettaglio (arpe_str_dett_id,
 struttura_id,
 arpe_struttura_tipo_id,
 arpe_assistenza_tipo_id,
 arpe_attivita_id,
 arpe_attivita_classe_id,
 arpe_disciplina_id,
 validita_inizio,
 validita_fine,
 data_creazione,
 data_modifica,
 data_cancellazione,
 utente_creazione,
 utente_modifica,
 utente_cancellazione,
 arpe_data_attivazione_struttura,
 arpe_data_attivazione_assistenza,
 arpe_data_attivazione_attivita)
values(:arpeStrDettId, 
:strutturaId,
:arpeStrutturaTipoId,
:arpeAssistenzaTipoId,
:arpeAttivitaId,
:arpeAttivitaClasseId,
:arpeDisciplinaId,
coalesce(:validitaInizio, now()), 
:validitaFine, 
now(), 
now(), 
null, 
:utenteCreazione, 
:utenteCreazione, 
null,
:arpe_data_attivazione_struttura,
:arpe_data_attivazione_assistenza,
:arpe_data_attivazione_attivita) 
     """;
 

// vigil_d_arpe_struttura_tipo
public static final String SELECT_STRUTTURA_DETTAGLIO_BY_ARPE_STRUTTURA_DETTAGLIO_ID = """
select * 
from vigil_r_arpe_struttura_dettaglio vrasd 
where vrasd.struttura_id = :strutturaId 
and coalesce(vrasd.arpe_struttura_tipo_id, -1) = coalesce(:arpeStrutturaTipoId, -1)
and coalesce(vrasd.arpe_assistenza_tipo_id, -1) = coalesce(:arpeAssistenzaTipoId, -1)
and coalesce(vrasd.arpe_attivita_id, -1) = coalesce(:arpeAttivitaId, -1)
and coalesce(vrasd.arpe_attivita_classe_id, -1) = coalesce(:arpeAttivitaClasseId, -1)
and coalesce(vrasd.arpe_disciplina_id, -1) = coalesce(:arpeDisciplinaId, -1)
and vrasd.data_cancellazione is null 
and now() between vrasd.validita_inizio and coalesce(vrasd.validita_fine, now())
""";  

public static final String UPDATE_REFRESH_STRUTTURA_DETTAGLIO_BY_ARPE_STRUTTURA_DETTAGLIO_ID = """
UPDATE vigil_r_arpe_struttura_dettaglio vrasd 
SET data_modifica = now(),
    utente_modifica = :utenteModifica,
    arpe_data_attivazione_struttura = :arpe_data_attivazione_struttura,
    arpe_data_attivazione_assistenza = :arpe_data_attivazione_assistenza,
    arpe_data_attivazione_attivita = :arpe_data_attivazione_attivita
where vrasd.arpe_str_dett_id = :arpeStrDettId 
and vrasd.data_cancellazione is null 
and now() between vrasd.validita_inizio and coalesce(vrasd.validita_fine, now())
""";  

public static final String NEXTVAL_PK_ID = 
"select nextval('vigil_r_arpe_struttura_dettaglio_arpe_str_dett_id_seq'::regclass) ";

public static final String DELETE_STRUTTURE_DETTAGLIO = " update vigil_r_arpe_struttura_dettaglio vrasd set "+	
		   "data_cancellazione = :datainput " +
		   "WHERE data_creazione <= :datainput " +
		   "AND data_modifica <= :datainput " +
		   "AND data_cancellazione is null";

public static final String SELECT_BY_STRUTTURA_ID_LISTA = """
SELECT vrasd.*
FROM      vigil_r_arpe_struttura_dettaglio vrasd
LEFT JOIN vigil_d_arpe_struttura_tipo      vdast ON
vdast.arpe_struttura_tipo_id = vrasd.arpe_struttura_tipo_id
LEFT JOIN vigil_d_arpe_assistenza_tipo     vdaat ON
vdaat.arpe_assistenza_tipo_id = vrasd.arpe_assistenza_tipo_id
LEFT JOIN vigil_d_arpe_attivita            vdaa  ON
vdaa.arpe_attivita_id = vrasd.arpe_attivita_id
LEFT JOIN vigil_d_arpe_attivita_classe     vdaac ON
vdaac.arpe_attivita_classe_id = vrasd.arpe_attivita_classe_id
LEFT JOIN vigil_d_arpe_disciplina          vdad  ON
vdad.arpe_disciplina_id = vrasd.arpe_disciplina_id
WHERE vrasd.struttura_id = :strutturaId
   AND vrasd.data_cancellazione is null
   AND NOW() BETWEEN vrasd.validita_inizio AND
coalesce(vrasd.validita_fine, NOW())
ORDER BY vdast.arpe_struttura_tipo_cod, vdaat.arpe_assistenza_tipo_cod,
vdaa.arpe_attivita_cod, vdaac.arpe_attivita_classe_cod,
vdad.arpe_disciplina_cod
"""; 


   private SqlStatementArpeStrutturaDettaglio() {
      throw new IllegalStateException("Utility class");
    }
}

