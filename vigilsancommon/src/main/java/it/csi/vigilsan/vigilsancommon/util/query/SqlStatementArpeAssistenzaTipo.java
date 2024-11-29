/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.util.query;

public class SqlStatementArpeAssistenzaTipo {
 

  public static final String PH_PK = "arpeAssistenzaTipoId";
 public static final String SELECT = "select vdaat.* from vigil_d_arpe_assistenza_tipo vdaat ";
 public static final String UPDATE = "update vigil_d_arpe_assistenza_tipo vdaat";
 public static final String PK_EQUALS = "vdaat.arpe_assistenza_tipo_id = :arpeAssistenzaTipoId";
 public static final String VALIDO = "vdaat.data_cancellazione is null " +
         "AND now() BETWEEN vdaat.validita_inizio AND coalesce(vdaat.validita_fine, now()) ";

 public static final String UPDATE_CAMPI =   
   "arpe_assistenza_tipo_cod = :assistenzaTipoCodCod, " +
   "arpe_assistenza_tipo_desc = :assistenzaTipoCodDesc, " +
   "validita_inizio = :validitaInizio, " +
   "validita_fine = :validitaFine, " +
   "data_modifica = now(), " +
   "utente_modifica = :utenteModifica ";
 
 public static final String INSERT = 
   """
insert
 into
 vigil_d_arpe_assistenza_tipo (arpe_assistenza_tipo_cod,
 arpe_assistenza_tipo_desc,
 validita_inizio,
 validita_fine,
 data_creazione,
 data_modifica,
 data_cancellazione,
 utente_creazione,
 utente_modifica,
 utente_cancellazione)
values(:arpeAssistenzaTipoCod,
:arpeAssistenzaTipoDesc,
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
 vigil_d_arpe_assistenza_tipo (arpe_assistenza_tipo_id, arpe_assistenza_tipo_cod,
 arpe_assistenza_tipo_desc,
 validita_inizio,
 validita_fine,
 data_creazione,
 data_modifica,
 data_cancellazione,
 utente_creazione,
 utente_modifica,
 utente_cancellazione)
values(:arpeAssistenzaTipoId, :arpeAssistenzaTipoCod,
:arpeAssistenzaTipoDesc,
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
public static final String SELECT_ASSISTENZA_TIPO_BY_ARPE_ASSISTENZA_TIPO_COD = """
select * 
from vigil_d_arpe_assistenza_tipo vdaat
where vdaat.arpe_assistenza_tipo_cod = :cod_arpe 
and vdaat.data_cancellazione is null 
and now() between vdaat.validita_inizio and coalesce(vdaat.validita_fine, now())
""";  

public static final String NEXTVAL_PK_ID = 
"select nextval('vigil_d_arpe_assistenza_tipo_arpe_assistenza_tipo_id_seq'::regclass) ";


   private SqlStatementArpeAssistenzaTipo() {
      throw new IllegalStateException("Utility class");
    }
}
