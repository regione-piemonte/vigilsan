/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.util.query;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

public class SqlStatementFile {

 public static final String PH_PK = "fileId";
 public static final String SELECT = "select * from vigil_t_file vtf";
 public static final String UPDATE = "update vigil_t_file vtf";
 public static final String PK_EQUALS = "vtf.file_id = :fileId";
 public static final String VALIDO = " vtf.data_cancellazione is null  "
   + " and now() between vtf.validita_inizio and coalesce(vtf.validita_fine, now()) ";

 public static final String UPDATE_CAMPI = """
    file_tipo_id = :fileTipoId,
    file_name = :fileName,
    file_name_orig = :fileNameOrig,
    file_size = :fileSize,
    file_content_type_id = :fileContentTypeId,
    file_path = :filePath,
    cf_firma_verificata = :cfFirmaVerificata,
    validita_inizio = :validitaInizio,
    validita_fine = :validitaFine,
    data_modifica = now(),
    utente_modifica = :utenteModifica
   """;

 private static final String INSERT = """
   insert
    into
    vigil_t_file (%s
    file_tipo_id,
    file_name,
    file_name_orig,
    file_size,
    file_content_type_id,
    file_path,
    cf_firma_verificata,
    validita_inizio,
    validita_fine,
    data_creazione,
    data_modifica,
    data_cancellazione,
    utente_creazione,
    utente_modifica,
    utente_cancellazione)
   values(%s
   :fileTipoId,
   :fileName,
   :fileNameOrig,
   :fileSize,
   :fileContentTypeId,
   :filePath,
   :cfFirmaVerificata,
   coalesce( :validitaInizio, now()),
   :validitaFine,
   now(),
   now(),
   null,
   :utenteCreazione,
   :utenteCreazione,
   null)
   """;
 public static final String INSERT_W_PK = String.format(INSERT, "file_id,", ":" + PH_PK + ",");
 public static final String INSERT_GENERIC = String.format(INSERT, "", "");

 public static final String NEXTVAL_PK_ID = "select nextval('vigil_t_file_file_id_seq'::regclass)";

 public static final String UPDATE_GENERIC = UPDATE + SqlStatementCommon.SET + UPDATE_CAMPI
   + SqlStatementCommon.WHERE + PK_EQUALS;
 public static final String DELETE = UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE + PK_EQUALS;
 public static final String SELECT_ALL = SELECT + SqlStatementCommon.WHERE + VALIDO;
 public static final String SELECT_BY_ID = SELECT + SqlStatementCommon.WHERE + PK_EQUALS;


  public static final String SELECT_FILE_GRUPPO_BY_ID =
      "select " +
      " vdfg.file_gruppo_id, " +
      " vdfg.file_gruppo_cod, " +
      " vdfg.file_gruppo_desc " +
      "from " +
      " vigil_d_file_gruppo vdfg  " +
      "where vdfg.file_gruppo_id = :fileGruppoId; ";

  public static final String SELECT_FILE_TIPO_BY_FILE_GRUPPO_ID =
 """
select
  vdft.file_tipo_id,
  vdft.file_tipo_cod,
  vdft.file_tipo_desc,
  vdft.file_tipo_hint,
  vdft.file_tipo_obbligatorio,
  vdft.file_tipo_firma_richiesta,
  mc.*
 FROM vigil_d_file_tipo   vdft
 JOIN vigil_d_modulo_voce vdmv ON vdmv.file_gruppo_id = vdft.file_gruppo_id AND vdmv.data_cancellazione IS NULL
 left join(select 
  vtmcd.file_id,
  vtmcd.note,
  vtf.file_name_orig as file_name,
  vtf.file_tipo_id
  from vigil_t_modulo_compilato_dettaglio vtmcd
 join vigil_t_file vtf ON 
  vtmcd.file_id = vtf.file_id 
 and vtf.data_cancellazione is NULL 
 and now() between vtf.validita_inizio and coalesce(vtf.validita_fine, now())
 where vtmcd.modulo_lista_valore_id IS NULL
   AND vtmcd.modulo_compilato_id = :moduloCompilatoId
   and vtmcd.modulo_voce_id = :moduloVoceId
   AND vtmcd.data_cancellazione IS NULL
   AND now() between vtmcd.validita_inizio and coalesce(vtmcd.validita_fine, now())) 
  as mc on mc.file_tipo_id = vdft.file_tipo_id  
 WHERE vdmv.modulo_voce_id = :moduloVoceId
 order BY vdft.file_tipo_ord
""";
  
 private SqlStatementFile() {
  throw new IllegalStateException("Utility class");
 }
}
