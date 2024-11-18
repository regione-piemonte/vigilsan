/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.util.query;

public class SqlStatementFileTipo {
 

  public static final String PH_PK = "fileTipoId";
 public static final String SELECT = "select vdft.* from vigil_d_file_tipo vdft";
 public static final String UPDATE = "update vigil_d_file_tipo vdft";
 public static final String PK_EQUALS = "vdft.file_tipo_id = :fileTipoId";
 public static final String VALIDO = "vdft.data_cancellazione is null " +
         "AND now() BETWEEN vdft.validita_inizio AND coalesce(vdft.validita_fine, now()) ";

 public static final String UPDATE_CAMPI =   
   "file_tipo_cod = :fileTipoCod, " +
   "file_tipo_desc = :fileTipoDesc, " +
   "file_tipo_ord = :fileTipoOrd, " +
   "file_tipo_hint = :fileTipoHint, " +
   "file_tipo_obbligatorio = :fileTipoObbligatorio, " +
   "file_tipo_firma_richiesta = :fileTipoFirmaRichiesta, " +
   "file_gruppo_id = :fileGruppoId, " +
   "validita_inizio = :validitaInizio, " +
   "validita_fine = :validitaFine, " +
   "data_modifica = now(), " +
   "utente_modifica = :utenteModifica ";
 
 public static final String INSERT = 
 "insert " +
 " into " +
 " vigil_d_file_tipo (file_tipo_cod, " +
 " file_tipo_desc, " +
 " file_tipo_ord, " +
 " file_tipo_hint, " +
 " file_tipo_obbligatorio, " +
 " file_tipo_firma_richiesta, " +
 " file_gruppo_id, " +
 " validita_inizio, " +
 " validita_fine, " +
 " data_creazione, " +
 " data_modifica, " +
 " data_cancellazione, " +
 " utente_creazione, " +
 " utente_modifica, " +
 " utente_cancellazione) " +
 "values(':fileTipoCod', " +
 ":fileTipoDesc, " +
 ":fileTipoOrd, " +
 ":fileTipoHint, " +
 ":fileTipoObbligatorio, " +
 ":fileTipoFirmaRichiesta, " +
 ":fileGruppoId, " +
 "coalesce(:validitaInizio, now()), "+
 ":validitaFine, "+
 "now(), "+
 "now(), "+
 "null, "+
 ":utenteCreazione, "+
 ":utenteCreazione, "+
 "null) " ;
 
 public static final String INSERT_W_PK = 
 "insert " +
 " into " +
 " vigil_d_file_tipo (file_tipo_id, file_tipo_cod, " +
 " file_tipo_desc, " +
 " file_tipo_ord, " +
 " file_tipo_hint, " +
 " file_tipo_obbligatorio, " +
 " file_tipo_firma_richiesta, " +
 " file_gruppo_id, " +
 " validita_inizio, " +
 " validita_fine, " +
 " data_creazione, " +
 " data_modifica, " +
 " data_cancellazione, " +
 " utente_creazione, " +
 " utente_modifica, " +
 " utente_cancellazione) " +
 "values(:fileTipoId, :fileTipoCod, " +
 ":fileTipoDesc, " +
 ":fileTipoOrd, " +
 ":fileTipoHint, " +
 ":fileTipoObbligatorio, " +
 ":fileTipoFirmaRichiesta, " +
 ":fileGruppoId, " +
 "coalesce(:validitaInizio, now()), "+
 ":validitaFine, "+
 "now(), "+
 "now(), "+
 "null, "+
 ":utenteCreazione, "+
 ":utenteCreazione, "+
 "null) " ;
 

 public static final String NEXTVAL_PK_ID = 
 "select nextval('vigil_d_file_tipo_file_tipo_id_seq'::regclass) ";
 

 public static final String SELECT_BY_COD = "select vdft.* from vigil_d_file_tipo vdft where vdft.file_tipo_cod = :fileTipoCod and " +VALIDO;

   private SqlStatementFileTipo() {
      throw new IllegalStateException("Utility class");
    }
}
