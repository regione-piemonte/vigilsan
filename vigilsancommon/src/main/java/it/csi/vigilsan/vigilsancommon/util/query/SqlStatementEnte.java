/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.util.query;

public class SqlStatementEnte {
 

 public static final String SELECT_ENTE = "select vte.* from vigil_t_ente vte";
 public static final String UPDATE_ENTE = "update vigil_t_ente vte";
 public static final String ENTE_ID_EQUALS = "vte.ente_id = :enteId";
 public static final String ENTE_COD_EQUALS = "vte.ente_cod = :enteCod";
 public static final String ENTE_COD_CONFIGURATORE_EQUALS = "vte.ente_cod_configuratore = :enteCodConfiguratore";
 public static final String ENTE_VALIDO = "vte.data_cancellazione is null " +
         "AND now() BETWEEN vte.validita_inizio AND coalesce(vte.validita_fine, now()) ";

 public static final String UPDATE_ENTE_CAMPI =   
   "ente_cod = :enteCod, " +
   "ente_cod_configuratore = :enteCodConfiguratore, " +
   "ente_desc = :enteDesc, " +
   "ente_tipo_id = :enteTipoId, " +
   "validita_inizio = :validitaInizio, " +
   "validita_fine = :validitaFine, " +
   "data_modifica = now(), " +
   "utente_modifica = :utenteModifica ";


 public static final String INSERT_ENTE_CAMPI =   
    "insert " +
    " into " +
    " vigil_t_ente (ente_cod, " +
    " ente_cod_configuratore, " +
    " ente_desc, " +
    " ente_tipo_id, " +
    " validita_inizio, " +
    " validita_fine, " +
    " data_creazione, " +
    " data_modifica, " +
    " data_cancellazione, " +
    " utente_creazione, " +
    " utente_modifica, " +
    " utente_cancellazione) " +
    " values(:enteCod, " +
    " :enteCodConfiguratore, " +
    " :enteDesc, " +
    " :enteTipoId, " +
    " coalesce(:validitaInizio, now()), " +
    " :validitaFine, " +
    " now(), " +
    " now(), " +
    " null, " +
    " :utenteCreazione, " +
    " :utenteCreazione, " +
    " null) ";

 public static final String SELECT_ASL_BY_STRUTTURA_ID = """
select
 vte.*
from vigil_r_ente_struttura vres 
join vigil_t_ente vte on
 vres.ente_id = vte.ente_id
 and vte.data_cancellazione is null
 and now() between vte.validita_inizio and coalesce(vte.validita_fine, now())
JOIN vigil_d_ruolo_ente_struttura res on res.ruolo_ente_struttura_id = vres.ruolo_ente_struttura_id 
 and res.ruolo_ente_struttura_cod = 'ASL_TERR' 
where
 vres.struttura_id = :strutturaId
 and vres.data_cancellazione is null
 and now() between vres.validita_inizio and coalesce(vres.validita_fine, now())
""";
 
 public static final String SELECT_ASL_BY_ENTE_COD = """
select
 vte.*
from vigil_t_ente vte 
join vigil_d_ente_tipo vdet on
 vte.ente_tipo_id = vdet.ente_tipo_id
 and vdet.ente_tipo_cod = 'ASL'
 and vdet.data_cancellazione is null
 and now() between vdet.validita_inizio and coalesce(vdet.validita_fine, now())
where
 vte.ente_cod = :enteCod
 and vte.data_cancellazione is null
 and now() between vte.validita_inizio and coalesce(vte.validita_fine, now())
"""; 
 
 

 private SqlStatementEnte() {
    throw new IllegalStateException("Utility class");
  }
}
