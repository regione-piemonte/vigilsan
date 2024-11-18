/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanrilevazioni.util.query;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

public class SqlStatementDocumentazione {

	public static final String PH_PK = "documentazioneId";
	public static final String SELECT = "select * from vigil_t_documentazione vtd ";
	public static final String UPDATE = "update vigil_t_documentazione vtd ";
	public static final String PK_EQUALS = "vtd.documentazione_id = :documentazioneId ";
	public static final String VALIDO = " vtd.data_cancellazione is null  "
			+ " and now() between vtd.validita_inizio and coalesce(vtd.validita_fine, now()) ";

	public static final String UPDATE_CAMPI = """
			  struttura_id = :strutturaId,
			  str_cat_id = :strCatId,
			  modulo_compilato_id = :moduloCompilatoId,
			  modulo_config_id = :moduloConfigId,
			  dataora_documentazione = :dataoraDocumentazione,
			  dataora_scadenza = :dataoraScadenza,
			  occorrenza = :occorrenza,
			  validita_inizio = :validitaInizio,
			  validita_fine = :validitaFine,
			  data_modifica = now(),
			  utente_modifica = :utenteModifica
			""";

	private static final String INSERT = """
			   insert
			    into
			    vigil_t_documentazione (%s struttura_id,
			    str_cat_id,
			    modulo_compilato_id,
			    modulo_config_id,
			    dataora_documentazione,
			    dataora_scadenza,
			    occorrenza,
			    validita_inizio,
			    validita_fine,
			    data_creazione,
			    data_modifica,
			    data_cancellazione,
			    utente_creazione,
			    utente_modifica,
			    utente_cancellazione)
			    values(%s :strutturaId,
			    :strCatId,
			    :moduloCompilatoId,
			    :moduloConfigId,
			    :dataoraDocumentazione,
			    :dataoraScadenza,
			    :occorrenza,
			    coalesce(:validitaInizio, now()),
			    :validitaFine,
			    now(),
			    now(),
			    null,
			    :utenteCreazione,
			    :utenteCreazione,
			    null)
			""";
	public static final String INSERT_W_PK = String.format(INSERT, "documentazione_id,", ":" + PH_PK + ",");
	public static final String INSERT_GENERIC = String.format(INSERT, "", "");

	public static final String NEXTVAL_PK_ID = "select nextval('vigil_t_documentazione_documentazione_id_seq'::regclass) ";
	public static final String UPDATE_GENERIC = UPDATE + SqlStatementCommon.SET + UPDATE_CAMPI
			+ SqlStatementCommon.WHERE + PK_EQUALS;
	public static final String DELETE = UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE + PK_EQUALS;
	public static final String SELECT_ALL = SELECT + SqlStatementCommon.WHERE + VALIDO;
	public static final String SELECT_BY_ID = SELECT + SqlStatementCommon.WHERE + PK_EQUALS;

	public static final String TABLE = "vigil_t_documentazione";

	public static final String SELECT_DOCUMENTAZIONE_DA_COMPILARE_ESTESO_MAPPER = """
select
 fn.*,
 null::int4 as documentazione_id,
 vdmc.doc_flg_obbligatorio,
 vdmc.modulo_config_desc
from
 fnc_documentazione_inseribile_by_struttura(:strutturaId) as fn
 join vigil_d_modulo_config vdmc on vdmc.modulo_config_id = fn.modulo_config_id 
 order by vdmc.modulo_config_ord 
""";

	public static final String SELECT_DOCUMENTAZIONE_DA_COMPILARE_ESTESO_MAPPER_FILTER_MODULO_CONFIG_COD = """
 modulo_config_cod = :moduloConfigCod
""";
	public static final String SELECT_DOCUMENTAZIONE_COMPILATE_ESTESO_MAPPER = """
			  SELECT
			   d.documentazione_id,
			   d.struttura_id,
			   d.str_cat_id,
			   d.modulo_compilato_id,
			   d.modulo_config_id,
			   mc.modulo_config_cod,
			   mc.modulo_config_desc,
			   mc.doc_flg_obbligatorio,
			   d.modulo_config_id,
			   d.dataora_documentazione,
			   d.dataora_scadenza,
			   d.occorrenza,
			   mc.modulo_id,
			   m.modulo_cod,
			   m.modulo_desc,
			   m.modulo_ord,
			   vtvd.verifica_documentazione_id,
			   vtvd.esito_verifica,
			   vtvd.dataora_verifica,
			   vtvd.note as note_verifica,
			   vtvd.notifica_id,
			COUNT(*) OVER() AS total_count
			FROM vigil_t_struttura      s
			JOIN vigil_t_documentazione d  ON d.struttura_id = s.struttura_id AND
			d.data_cancellazione IS NULL
			LEFT JOIN vigil_t_verifica_documentazione vtvd  ON vtvd.documentazione_id = d.documentazione_id and vtvd.data_cancellazione is null 
			   AND now() BETWEEN vtvd.validita_inizio AND coalesce(vtvd.validita_fine, now())
			JOIN vigil_d_modulo_config  mc ON mc.modulo_config_id = d.modulo_config_id
			JOIN vigil_d_modulo         m  ON m.modulo_id = mc.modulo_id
			WHERE s.data_cancellazione IS NULL
			   AND s.data_cancellazione IS NULL
			   AND now() between d.validita_inizio and coalesce(d.validita_fine, now())
			   AND s.struttura_id = :strutturaId
			   %s 
			  """;
	public static final String SELECT_DOCUMENTAZIONE_COMPILATE_ESTESO_MAPPER_FILTER_MODULO_CONFIG_COD = """
			    AND mc.modulo_config_cod = :moduloConfigCod
			""";

	public static final String SELECT_PARAMETRO_BOOLEAN = """
			SELECT (%s)::TEXT valore
			 FROM vigilsan_rw.v_dwh_%s WHERE struttura_id = :strutturaId;
			""";
	public static final String DELETE_OLDS = """
 vtd.validita_fine IS NULL
  AND vtd.struttura_id = :strutturaId
  AND vtd.modulo_config_id = :moduloConfigId
  AND COALESCE(vtd.str_cat_id,-1) = COALESCE(:strCatId,-1)
  AND vtd.occorrenza = :occorrenza
 """;

	private SqlStatementDocumentazione() {
		throw new IllegalStateException("Utility class");
	}
}
