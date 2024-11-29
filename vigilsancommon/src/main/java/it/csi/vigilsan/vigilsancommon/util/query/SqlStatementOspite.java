/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.util.query;

public class SqlStatementOspite {
 
public static final String SELECT_ALL_OSPITE_TIPO_MOVIMENTO_VALID = """
select
 *
from
 vigil_d_ospite_movimento_tipo vdomt
where
  vdomt.data_cancellazione is null
 and now() between vdomt.validita_inizio and coalesce(vdomt.validita_fine,
 now())
  """;

public static final String SELECT_OSPITE_MOVIMENTO_TIPO_CONFIG_BY_OSP_MOV_TIPO_ID_VALID = """
select * from vigil_r_ospite_movimento_tipo_config vromtc 
where ospite_movimento_tipo_id = :ospiteMovimentoTipoId 
and vromtc.data_cancellazione is null
and now() between vromtc.validita_inizio and coalesce(vromtc.validita_fine,
 now())
  """;
public static final String SELECT_RUOLO_STRUTTURA_SOGGETTO_ID_BY_RUOLO_STRUTTURA_COD = """
select vdrs.ruolo_struttura_soggetto_id  from vigil_d_ruolo_struttura_soggetto vdrs 
where ruolo_struttura_soggetto_cod = :ruoloStrutturaCod
""";

private static final String SELECT_OSPITI_MOVIMENTI_TIPI_FROM_DATA_MOVIMENTO_STR_ID_SOGG_ID_NEW_OSPITE_MOV_TIPO = """
WITH
  om AS (
    SELECT x.*, omt.ospite_movimento_tipo_desc, omt.ospite_movimento_tipo_ord, omt.ospite_stato_id
    FROM (
      SELECT om.data_movimento, om.ospite_movimento_tipo_id, om.data_creazione 
      FROM vigil_t_ospite_movimento   om
      JOIN vigil_r_struttura_soggetto ss ON ss.str_sogg_id = om.str_sogg_id
      WHERE ss.struttura_id = :strutturaId
        AND ss.soggetto_id = :soggettoId
        AND om.data_cancellazione IS NULL 
        %s 
        %s 
      UNION
      SELECT '0001-01-01', NULL, '0001-01-01'
    ) x
    LEFT JOIN vigil_d_ospite_movimento_tipo omt ON omt.ospite_movimento_tipo_id = x.ospite_movimento_tipo_id
  ),
  omr AS (
    SELECT RANK() OVER (ORDER BY data_movimento, ospite_movimento_tipo_ord, data_creazione) seq, *
    FROM om
  )
SELECT
  (ospite_movimento_tipo_id IN (
    SELECT omtc.ospite_movimento_tipo_id
    FROM vigil_r_ospite_movimento_tipo_config omtc
    JOIN (
      SELECT x.ospite_stato_id FROM omr x
      WHERE x.seq = (
        SELECT MAX(y.seq) FROM omr y WHERE y.seq < omr.seq AND (y.ospite_stato_id IS NOT NULL OR y.seq = 1)
      )
    ) uos ON COALESCE(uos.ospite_stato_id,-1) = COALESCE(omtc.ospite_stato_id,-1)
    WHERE omtc.data_cancellazione IS NULL
  )) flg_valido,
  omr.*
FROM omr
WHERE seq > 1
""";
private static final String SELECT_OSPITI_MOVIMENTI_DELETE_MOVIMENTO = " AND om.ospite_movimento_id != :oldOspiteMovimentoId ";
private static final String SELECT_OSPITI_MOVIMENTI_ADD_MOVIMENTO = """
 UNION
SELECT :newDataMovimento, :newOspiteMovimentoTipoId, now() 
""";

public static final String SELECT_OSPITI_MOVIMENTI_TIPI_AGGIUNGERE_MOVIMENTO = String.format(
		SqlStatementOspite.SELECT_OSPITI_MOVIMENTI_TIPI_FROM_DATA_MOVIMENTO_STR_ID_SOGG_ID_NEW_OSPITE_MOV_TIPO,
		"", SqlStatementOspite.SELECT_OSPITI_MOVIMENTI_ADD_MOVIMENTO);

public static final String SELECT_OSPITI_MOVIMENTI_TIPI_RIMUOVERE_MOVIMENTO = String.format(
		SqlStatementOspite.SELECT_OSPITI_MOVIMENTI_TIPI_FROM_DATA_MOVIMENTO_STR_ID_SOGG_ID_NEW_OSPITE_MOV_TIPO,
		SELECT_OSPITI_MOVIMENTI_DELETE_MOVIMENTO, "");
private SqlStatementOspite() {
      throw new IllegalStateException("Utility class");
    }
}
