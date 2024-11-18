/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanrilevazioni.util.query;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

public class SqlStatementDocumentoRilevazione {

  public static final String SELECT_DOCUMENTO_ESTESO_MAPPER = """
with mst as (
select
 m.modulo_id, m.modulo_cod, m.modulo_desc, m.modulo_ord,
 m.modulo_titolo, mc.modulo_config_id, mc.modulo_config_cod, mc.modulo_config_desc, mc.doc_flg_obbligatorio, mc.dataora_origine,
 mc.doc_scadenza, mc.ril_frequenza, mc.ril_mod_inizio_origine,
 mc.ril_mod_inizio_scostamento, mc.ril_mod_fine_origine,
 mc.ril_mod_fine_scostamento, null::int4 as documentazione_id,
 null::int4 as struttura_id, null::int4 as str_cat_id,
 null::int4 as modulo_compilato_id, null::TIMESTAMP as dataora_documentazione,
 null::TIMESTAMP as dataora_scadenza
from
 vigil_t_struttura s
join vigil_r_struttura_tipo_modulo stm on stm.struttura_tipo_id = s.struttura_tipo_id
 and stm.data_cancellazione is null
 and NOW() between stm.validita_inizio and coalesce(stm.validita_fine, now())
join vigil_d_modulo_config mc on
 mc.modulo_config_id = stm.modulo_config_id and mc.ril_frequenza is null
 and mc.data_cancellazione is null
 and NOW() between mc.validita_inizio and coalesce(mc.validita_fine, now())
join vigil_d_modulo m on m.modulo_id = mc.modulo_id
 and m.data_cancellazione is null
 and NOW() between m.validita_inizio and coalesce(m.validita_fine, now())
where
 s.struttura_id = :strutturaId and s.data_cancellazione is null),  
msc as (
select
 m.modulo_id, m.modulo_cod, m.modulo_desc, m.modulo_ord,
 m.modulo_titolo, mc.modulo_config_id, mc.modulo_config_cod, mc.modulo_config_desc, mc.doc_flg_obbligatorio, mc.dataora_origine,
 mc.doc_scadenza, mc.ril_frequenza, mc.ril_mod_inizio_origine,
 mc.ril_mod_inizio_scostamento, mc.ril_mod_fine_origine,
 mc.ril_mod_fine_scostamento, null::int4 as documentazione_id,
 null::int4 as struttura_id, null::int4 as str_cat_id,
 null::int4 as modulo_compilato_id, null::TIMESTAMP as dataora_documentazione,
 null::TIMESTAMP as dataora_scadenza
from
 vigil_t_struttura s
join vigil_r_struttura_categoria sc on sc.struttura_id = s.struttura_id
 and sc.data_cancellazione is null
 and NOW() between sc.validita_inizio and coalesce(sc.validita_fine, now())
join vigil_r_struttura_categoria_modulo scm on
 scm.struttura_categoria_id = sc.struttura_categoria_id
  and scm.data_cancellazione is null
  and NOW() between scm.validita_inizio and coalesce(scm.validita_fine, now())
 join vigil_d_modulo_config mc on
  mc.modulo_config_id = scm.modulo_config_id and mc.ril_frequenza is null
   and mc.data_cancellazione is null
   and NOW() between mc.validita_inizio and coalesce(mc.validita_fine, now())
  join vigil_d_modulo m on
   m.modulo_id = mc.modulo_id and m.data_cancellazione is null
   and NOW() between m.validita_inizio and coalesce(m.validita_fine, now())
   where
    s.struttura_id = :strutturaId and s.data_cancellazione is null),  
doc as (
select
 m.modulo_id, m.modulo_cod, m.modulo_desc, m.modulo_ord,
 m.modulo_titolo, mc.modulo_config_id, mc.modulo_config_cod, mc.modulo_config_desc, mc.doc_flg_obbligatorio, mc.dataora_origine,
 mc.doc_scadenza, mc.ril_frequenza, mc.ril_mod_inizio_origine,
 mc.ril_mod_inizio_scostamento, mc.ril_mod_fine_origine,
 mc.ril_mod_fine_scostamento, d.documentazione_id, d.struttura_id,
 d.str_cat_id, d.modulo_compilato_id, d.dataora_documentazione,
 d.dataora_scadenza
from
 vigil_t_documentazione d
join vigil_d_modulo_config mc on mc.modulo_config_id = d.modulo_config_id
join vigil_d_modulo m on m.modulo_id = mc.modulo_id
where
 d.struttura_id = :strutturaId and d.data_cancellazione is null)  
select modulo_id, modulo_cod, modulo_desc,
 modulo_ord, modulo_titolo, modulo_config_id, documentazione_id,
 struttura_id, str_cat_id, modulo_compilato_id, dataora_documentazione,
 case
  when q.documentazione_id is not null then q.dataora_scadenza
  when q.dataora_origine is not null
  and q.doc_scadenza is not null then ( select max(dt) q.doc_scadenza
  from generate_series(q.dataora_origine, NOW()::TIMESTAMP, q.doc_scadenza) as t(dt))
  else null end dataora_scadenza
from ( select * from mst union select * from msc union select * from  doc) as q order by modulo_cod
""";

  public static final String EXIST_MODULO_CONFIG_BY_MODULO_CONFIG_ID = "select EXISTS(select 1 from vigil_d_modulo_config vdmc where modulo_config_id = :moduloConfigId)";
  public static final String EXIST_MODULO_CONFIG_BY_MODULO_CONFIG_COD = "select EXISTS(select 1 from vigil_d_modulo_config vdmc where modulo_config_cod = :moduloConfigCod and data_cancellazione is null)";

  public static final String SELECT_MODULO_RIDOTTO_FORMAT = """
      WITH
        o AS (
          SELECT mc.modulo_config_cod, vdmcg.modulo_config_gruppo_cod, COUNT(*) reccount
          FROM vigil_t_struttura     s
          JOIN %s   x  ON x.struttura_id = s.struttura_id AND x.data_cancellazione IS NULL
          JOIN vigil_d_modulo_config mc ON mc.modulo_config_id = x.modulo_config_id
          join vigil_d_modulo_config_gruppo vdmcg on vdmcg.modulo_config_gruppo_id = mc.modulo_config_gruppo_id 
          WHERE s.struttura_id = :strutturaId AND s.data_cancellazione IS NULL
          GROUP BY 1, 2
        ),
        n AS (
          SELECT mc.modulo_config_cod, vdmcg.modulo_config_gruppo_cod, 0 reccount
          FROM      vigil_t_struttura                  s
               JOIN vigil_r_struttura_tipo_modulo      stm ON stm.struttura_tipo_id = s.struttura_tipo_id AND stm.data_cancellazione IS NULL AND NOW() BETWEEN stm.validita_inizio AND COALESCE(stm.validita_fine,NOW())
          LEFT JOIN vigil_r_struttura_categoria        sc  ON sc.struttura_id = s.struttura_id AND sc.data_cancellazione IS NULL AND NOW() BETWEEN sc.validita_inizio AND COALESCE(sc.validita_fine,NOW())
          LEFT JOIN vigil_r_struttura_categoria_modulo scm ON scm.struttura_categoria_id = sc.struttura_categoria_id AND scm.data_cancellazione IS NULL AND NOW() BETWEEN scm.validita_inizio AND COALESCE(scm.validita_fine,NOW())
                     JOIN vigil_d_modulo_config              mc  ON mc.modulo_config_id  IN (stm.modulo_config_id,scm.modulo_config_id) AND  mc.ril_validita_frequenza %s AND mc.data_cancellazione IS NULL AND NOW() BETWEEN mc.validita_inizio AND COALESCE(mc.validita_fine,NOW())
                     join vigil_d_modulo_config_gruppo vdmcg on vdmcg.modulo_config_gruppo_id = mc.modulo_config_gruppo_id 
          WHERE s.struttura_id = :strutturaId AND s.data_cancellazione IS NULL
        )
      SELECT mc.*, u.reccount::INTEGER AS reccount
      FROM (
        SELECT modulo_config_cod, modulo_config_gruppo_cod, SUM(reccount) reccount
        FROM (SELECT * FROM o UNION SELECT * FROM n) x
        GROUP BY 1, 2 
      ) u
      JOIN (
        SELECT DISTINCT ON (modulo_config_cod) modulo_config_cod, modulo_config_ord, modulo_config_desc, modulo_config_id, doc_flg_obbligatorio
        FROM vigil_d_modulo_config
        WHERE data_cancellazione IS NULL
        ORDER BY modulo_config_cod, validita_inizio DESC
      ) mc ON mc.modulo_config_cod = u.modulo_config_cod
      %s
      ORDER BY mc.modulo_config_ord
      """;

  public static final String SELECT_MODULO_RIDOTTO_FORMAT_FILER_MODULO_CONFIG_GRUPPO_COD = """
      WHERE u.modulo_config_gruppo_cod = :moduloConfigGruppoCod
      """;

  public static final String SELECT_MODULO_RIDOTTO_DOCUMENTAZIONE = String.format(
      SqlStatementDocumentoRilevazione.SELECT_MODULO_RIDOTTO_FORMAT, SqlStatementDocumentazione.TABLE,
      SqlStatementCommon.IS_NULL, SELECT_MODULO_RIDOTTO_FORMAT_FILER_MODULO_CONFIG_GRUPPO_COD);

  public static final String SELECT_MODULO_RIDOTTO_RILEVAZIONE = String.format(
      SqlStatementDocumentoRilevazione.SELECT_MODULO_RIDOTTO_FORMAT, SqlStatementRilevazione.TABLE,
      SqlStatementCommon.IS_NOT_NULL, SELECT_MODULO_RIDOTTO_FORMAT_FILER_MODULO_CONFIG_GRUPPO_COD);

  
  
  
  public static final String SQL_PIVOT_RENDICONTAZIONI_QUERY_1_FLAT = """
'SELECT
    %s
  FROM      %s                r
          JOIN vigil_t_struttura                  s   ON s.struttura_id = r.struttura_id
          LEFT join vigil_t_comune vtc on vtc.comune_id = s.comune_id
          LEFT join vigil_t_provincia vtp on vtp.provincia_id = vtc.provincia_id
          JOIN vigil_d_struttura_tipo             st  ON st.struttura_tipo_id = s.struttura_tipo_id
          LEFT JOIN vigil_r_struttura_categoria        rsc ON rsc.str_cat_id = r.str_cat_id
          LEFT JOIN vigil_d_struttura_categoria        sc  ON sc.struttura_categoria_id = rsc.struttura_categoria_id
               JOIN vigil_r_ente_struttura             es  ON es.struttura_id = r.struttura_id AND es.data_cancellazione IS NULL
               JOIN vigil_d_ruolo_ente_struttura       res ON res.ruolo_ente_struttura_id = es.ruolo_ente_struttura_id AND res.ruolo_ente_struttura_cod = ''ASL_TERR''
               JOIN vigil_t_ente                       e   ON e.ente_id = es.ente_id
               JOIN vigil_d_ente_tipo                  et  ON et.ente_tipo_id = e.ente_tipo_id AND et.ente_tipo_cod = ''ASL''
                JOIN vigil_d_modulo_config              mcf ON mcf.modulo_config_id = r.modulo_config_id AND mcf.modulo_config_cod = ''%s''
                JOIN vigil_t_modulo_compilato           mc  ON mc.modulo_compilato_id = r.modulo_compilato_id AND mc.data_cancellazione IS NULL
                JOIN (
                  SELECT modulo_compilato_id, modulo_voce_id, ARRAY_TO_STRING(ARRAY_AGG(COALESCE(mcd.valore, mlv.modulo_lista_valore_desc, f.file_name)),''|'',''-'') valori
                  FROM      vigil_t_modulo_compilato_dettaglio mcd
                  LEFT JOIN vigil_d_modulo_lista_valore        mlv ON mlv.modulo_lista_valore_id = mcd.modulo_lista_valore_id
                  LEFT JOIN vigil_t_file                       f   ON f.file_id = mcd.file_id
                  WHERE mcd.data_cancellazione IS NULL
                  GROUP BY 1,2)                         mcd ON mcd.modulo_compilato_id = r.modulo_compilato_id
                JOIN fnc_modulo_voce_by_modulo_config(''%s'') mv ON mv.modulo_voce_id = mcd.modulo_voce_id
           WHERE r.data_cancellazione IS NULL %s
           ORDER BY 1
           '
""";
  public static final String SQL_PIVOT_RENDICONTAZIONI_VALIDITA_FINE_IS_NULL = " r.validita_fine IS NULL ";
  public static final String SQL_PIVOT_RENDICONTAZIONI_QUERY_1_FILTER_STRUTTURA_ID = " s.struttura_id = %d ";
  public static final String SQL_PIVOT_RENDICONTAZIONI_QUERY_1_FILTER_ENTE_ID = " e.ente_id = %d ";
  public static final String SQL_PIVOT_RENDICONTAZIONI_QUERY_1_FILTER_DATA_RILEVAZIONE_A = " r.dataora_rilevazione <= ''%s''::DATE ";
  public static final String SQL_PIVOT_RENDICONTAZIONI_QUERY_1_FILTER_DATA_RILEVAZIONE_DA = " r.dataora_rilevazione >= ''%s''::DATE ";
  public static final String SQL_PIVOT_RENDICONTAZIONI_QUERY_1_FILTER_STRUTTURA_CATEGORIA_COD = " sc.struttura_categoria_cod = ''%s'' ";
  private static final String SQL_PIVOT_RENDICONTAZIONI_QUERY_1_CAMPI_CSV_FLAT = """
       r.dataora_rilevazione, r.validita_inizio, r.validita_fine,
          s.struttura_cod, s.struttura_cod_arpe, s.struttura_desc,
           vtc.comune_desc , vtp.provincia_sigla, st.struttura_tipo_desc,
          sc.struttura_categoria_desc,
          e.ente_desc,
          et.ente_tipo_desc,
           mcf.modulo_config_desc,
          mv.modulo_voce_cod, mcd.valori
      """;
  
  private static final String SQL_PIVOT_RENDICONTAZIONI_QUERY_1_CAMPI_VIEW_FLAT = """
      r.rilevazione_id, r.dataora_rilevazione, r.validita_inizio, r.validita_fine, r.data_creazione, r.utente_creazione,
      s.struttura_id, s.struttura_cod, s.struttura_cod_arpe, s.struttura_desc,
      s.struttura_tipo_id, vtc.comune_desc, vtp.provincia_sigla,  st.struttura_tipo_cod, st.struttura_tipo_desc,
      sc.struttura_categoria_id, sc.struttura_categoria_cod, sc.struttura_categoria_desc,
      e.ente_id, e.ente_cod, e.ente_desc,
      et.ente_tipo_id, et.ente_tipo_cod, et.ente_tipo_desc,
      mcf.modulo_config_id, mcf.modulo_config_cod, mcf.modulo_config_desc,
      mc.modulo_compilato_id, mc.modulo_id, mv.modulo_voce_cod, mcd.valori
      """;
  private static final String SQL_PIVOT_DOCUMENTAZIONE_QUERY_1_CAMPI_CSV_FLAT = """
          r.documentazione_id, r.dataora_documentazione, r.dataora_scadenza, r.occorrenza,
          r.data_creazione, r.utente_creazione,
          s.struttura_id, s.struttura_cod, s.struttura_cod_arpe, s.struttura_desc,
          s.struttura_tipo_id, vtc.comune_desc, vtp.provincia_sigla, st.struttura_tipo_cod, st.struttura_tipo_desc,
          sc.struttura_categoria_id, sc.struttura_categoria_cod, sc.struttura_categoria_desc,
          e.ente_id, e.ente_cod, e.ente_desc,
          et.ente_tipo_id, et.ente_tipo_cod, et.ente_tipo_desc,
          mcf.modulo_config_id, mcf.modulo_config_cod, mcf.modulo_config_desc,
          mc.modulo_compilato_id, mc.modulo_id, mv.modulo_voce_cod, mcd.valori
      """;
  private static final String SQL_PIVOT_DOCUMENTAZIONE_QUERY_1_CAMPI_VIEW_FLAT = """
          r.documentazione_id, r.dataora_documentazione, r.dataora_scadenza, r.occorrenza,
          r.data_creazione, r.utente_creazione,
          s.struttura_id, s.struttura_cod, s.struttura_cod_arpe, s.struttura_desc,
          s.struttura_tipo_id, vtc.comune_desc, vtp.provincia_sigla, st.struttura_tipo_cod, st.struttura_tipo_desc,
          sc.struttura_categoria_id, sc.struttura_categoria_cod, sc.struttura_categoria_desc,
          e.ente_id, e.ente_cod, e.ente_desc,
          et.ente_tipo_id, et.ente_tipo_cod, et.ente_tipo_desc,
          mcf.modulo_config_id, mcf.modulo_config_cod, mcf.modulo_config_desc,
          mc.modulo_compilato_id, mc.modulo_id, mv.modulo_voce_cod, mcd.valori
      """;

  public static final String SQL_PIVOT_RENDICONTAZIONI_QUERY_1_CAMPI_CSV = SQL_PIVOT_RENDICONTAZIONI_QUERY_1_CAMPI_CSV_FLAT
      .replace("\n", "");
  public static final String SQL_PIVOT_RENDICONTAZIONI_QUERY_1_CAMPI_VIEW = SQL_PIVOT_RENDICONTAZIONI_QUERY_1_CAMPI_VIEW_FLAT
      .replace("\n", "");
  public static final String SQL_PIVOT_DOCUMENTAZIONE_QUERY_1_CAMPI_CSV = SQL_PIVOT_DOCUMENTAZIONE_QUERY_1_CAMPI_CSV_FLAT
      .replace("\n", "");
  public static final String SQL_PIVOT_DOCUMENTAZIONE_QUERY_1_CAMPI_VIEW = SQL_PIVOT_DOCUMENTAZIONE_QUERY_1_CAMPI_VIEW_FLAT
      .replace("\n", "");
  public static final String SQL_PIVOT_RENDICONTAZIONI_QUERY_1 = SQL_PIVOT_RENDICONTAZIONI_QUERY_1_FLAT.replace("\n",
      "");
  public static final String SQL_PIVOT_RENDICONTAZIONI_QUERY_2 = """
      'SELECT modulo_voce_colname FROM fnc_modulo_voce_columns_by_modulo_config(''%s'')'
      """;

  public static final String SQL_PIVOT_RENDICONTAZIONI_CAMPI_VARIABILI = """
      select * from fnc_modulo_voce_columns_by_modulo_config(:moduloConfigCod)
      """;

  public static final String SQL_PIVOT_RENDICONTAZIONE_COUNT_NOTE =""" 
       , ( SELECT COUNT(*) FROM vigil_t_modulo_compilato_dettaglio mcd WHERE mcd.modulo_compilato_id = t.modulo_compilato_id AND mcd.data_cancellazione IS NULL AND mcd.note IS NOT NULL ) cnt_note 
      """;
  public static final String SQL_PIVOT_RENDICONTAZIONI = """
      SELECT * %s 
      FROM CROSSTAB(
        %s
        ,
        %s
        )
      AS t(
        %s
        %s
        )
      """;
  public static final String SQL_PIVOT_RENDICONTAZIONI_CREA_VIEW = "create view vigilsan_rw.%s as (%s)";
  public static final String SQL_RENDICONTAZIONI_DROP_VIEW = "DROP VIEW IF EXISTS vigilsan_rw.%s";

  public static final String SQL_PIVOT_RENDICONTAZIONI_CAMPI_CSV = """
       dataora_rilevazione                        TIMESTAMP,
       validita_inizio                            TIMESTAMP,
       validita_fine                              TIMESTAMP,
       struttura_cod                              TEXT,
       struttura_cod_arpe                         TEXT,
       struttura_desc                             TEXT,
       comune_desc                                TEXT,
       provincia_sigla                            TEXT,
       struttura_tipo_desc                        TEXT,
       struttura_categoria_desc                   TEXT,
       ente_desc                                  TEXT,
       ente_tipo_desc                             TEXT,
       modulo_config_desc                         TEXT
      """;

  public static final String SQL_PIVOT_DOCUMENTAZIONI_CAMPI_VIEW = """
       documentazione_id                          INTEGER,
       dataora_documentazione                     TIMESTAMP,
       dataora_scadenza                           TIMESTAMP,
       occorrenza                                 INTEGER,
       data_creazione                             TIMESTAMP,
       utente_creazione                           TEXT,
       struttura_id                               INTEGER,
       struttura_cod                              TEXT,
       struttura_cod_arpe                         TEXT,
       struttura_desc                             TEXT,
       struttura_tipo_id                          INTEGER,
       comune_desc                                TEXT,
       provincia_sigla                            TEXT,
       struttura_tipo_cod                         TEXT,
       struttura_tipo_desc                        TEXT,
       struttura_categoria_id                     INTEGER,
       struttura_categoria_cod                    TEXT,
       struttura_categoria_desc                   TEXT,
       ente_id                                    INTEGER,
       ente_cod                                   TEXT,
       ente_desc                                  TEXT,
       ente_tipo_id                               INTEGER,
       ente_tipo_cod                              TEXT,
       ente_tipo_desc                             TEXT,
       modulo_config_id                           INTEGER,
       modulo_config_cod                          TEXT,
       modulo_config_desc                         TEXT,
       modulo_compilato_id                        INTEGER,
       modulo_id                                  INTEGER
      """;

  public static final String SQL_PIVOT_DOCUMENTAZIONI_CAMPI_CSV = """
       documentazione_id                          INTEGER,
       dataora_documentazione                     TIMESTAMP,
       dataora_scadenza                           TIMESTAMP,
       occorrenza                                 INTEGER,
       data_creazione                             TIMESTAMP,
       utente_creazione                           TEXT,
       struttura_id                               INTEGER,
       struttura_cod                              TEXT,
       struttura_cod_arpe                         TEXT,
       struttura_desc                             TEXT,
       struttura_tipo_id                          INTEGER,
       comune_desc                                TEXT,
       provincia_sigla                            TEXT,
       struttura_tipo_cod                         TEXT,
       struttura_tipo_desc                        TEXT,
       struttura_categoria_id                     INTEGER,
       struttura_categoria_cod                    TEXT,
       struttura_categoria_desc                   TEXT,
       ente_id                                    INTEGER,
       ente_cod                                   TEXT,
       ente_desc                                  TEXT,
       ente_tipo_id                               INTEGER,
       ente_tipo_cod                              TEXT,
       ente_tipo_desc                             TEXT,
       modulo_config_id                           INTEGER,
       modulo_config_cod                          TEXT,
       modulo_config_desc                         TEXT,
       modulo_compilato_id                        INTEGER,
       modulo_id                                  INTEGER
      """;

  public static final String SQL_PIVOT_RENDICONTAZIONI_CAMPI_VIEW = """
       rilevazione_id                             INTEGER,
       dataora_rilevazione                        TIMESTAMP,
       validita_inizio                            TIMESTAMP,
       validita_fine                              TIMESTAMP,
       data_creazione                             TIMESTAMP,
       utente_creazione                           TEXT,
       struttura_id                               INTEGER,
       struttura_cod                              TEXT,
       struttura_cod_arpe                         TEXT,
       struttura_desc                             TEXT,
       struttura_tipo_id                          INTEGER,
       comune_desc                                TEXT,
       provincia_sigla                            TEXT,
       struttura_tipo_cod                         TEXT,
       struttura_tipo_desc                        TEXT,
       struttura_categoria_id                     INTEGER,
       struttura_categoria_cod                    TEXT,
       struttura_categoria_desc                   TEXT,
       ente_id                                    INTEGER,
       ente_cod                                   TEXT,
       ente_desc                                  TEXT,
       ente_tipo_id                               INTEGER,
       ente_tipo_cod                              TEXT,
       ente_tipo_desc                             TEXT,
       modulo_config_id                           INTEGER,
       modulo_config_cod                          TEXT,
       modulo_config_desc                         TEXT,
       modulo_compilato_id                        INTEGER,
       modulo_id                                  INTEGER
      """;

  public static final String GRANT_ACCESS_VIEW_RENDICONTAZIONI_CREA_VIEW = "GRANT SELECT ON vigilsan_rw.%s TO %s";
  public static final String SQL_PIVOT_WRAP_DOCUMENTAZIONE_PER_PARAMETRI = """
SELECT d.*, vd.dataora_verifica, mlv.modulo_lista_valore_id esito_verifica_id
FROM      (%s)                            d
LEFT JOIN vigil_t_verifica_documentazione vd  ON vd.documentazione_id = d.documentazione_id AND vd.data_cancellazione IS NULL
LEFT JOIN vigil_d_modulo_lista            ml  ON ml.modulo_lista_cod = 'ver_conf_doc' AND ml.data_cancellazione IS NULL
LEFT JOIN vigil_d_modulo_lista_valore     mlv ON mlv.modulo_lista_id = ml.modulo_lista_id AND mlv.modulo_lista_valore_cod = COALESCE(vd.esito_verifica::TEXT,'null') AND mlv.data_cancellazione IS NULL
""";

  private SqlStatementDocumentoRilevazione() {
    throw new IllegalStateException("Utility class");
  }

}
