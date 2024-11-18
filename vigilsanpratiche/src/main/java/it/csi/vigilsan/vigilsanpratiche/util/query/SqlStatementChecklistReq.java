/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.util.query;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

public class SqlStatementChecklistReq {

	public static final String PH_PK = "clreqId";
	public static final String SELECT = "select vdcr.* from vigil_d_checklist_req vdcr ";
	public static final String UPDATE = "update vigil_d_checklist_req vdcr";
	public static final String PK_EQUALS = "vdcr.clreq_id = :clreqId";
	public static final String VALIDO = " vdcr.data_cancellazione is null "
			+ "AND now() BETWEEN vdcr.validita_inizio AND coalesce(vdcr.validita_fine, now()) ";

	public static final String UPDATE_CAMPI = """
			  clreq_cod = :clreqCod,
			  clreq_desc = :clreqDesc,
			  clreq_ord = :clreqOrd,
			  clreq_hint = :clreqHint,
			  clreq_id_padre = :clreqIdPadre,
			  modulo_id = :moduloId,
			  validita_inizio = :validitaInizio,
			  validita_fine = :validitaFine,
			  data_modifica = now(),
			  utente_modifica = :utenteModifica
			""";

	private static final String INSERT = """
			insert
			 into
			 vigilsan.vigil_d_checklist_req
			(%s
			 clreq_cod,
			 clreq_desc,
			 clreq_ord,
			 clreq_hint,
			 clreq_id_padre,
			 modulo_id,
			 validita_inizio,
			 validita_fine,
			 data_creazione,
			 data_modifica,
			 data_cancellazione,
			 utente_creazione,
			 utente_modifica,
			 utente_cancellazione)
			values(%s
			:clreqCod,
			:clreqDesc,
			:clreqOrd,
			:clreqHint,
			:clreqIdPadre,
			:moduloId,
			 coalesce(:validitaInizio, now()),
			 :validitaFine,
			 now(),
			 now(),
			 null,
			 :utenteCreazione,
			 :utenteCreazione,
			 null)
			""";

	public static final String INSERT_W_PK = String.format(INSERT, "clreq_id,", ":" + PH_PK + ",");
	public static final String INSERT_GENERIC = String.format(INSERT, "", "");

	public static final String NEXTVAL_PK_ID = "select nextval('vigil_d_checklist_req_clreq_id_seq'::regclass)";
	public static final String UPDATE_GENERIC = UPDATE + SqlStatementCommon.SET + UPDATE_CAMPI
			+ SqlStatementCommon.WHERE + PK_EQUALS;
	public static final String DELETE = UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE + PK_EQUALS;
	public static final String SELECT_ALL = SELECT + SqlStatementCommon.WHERE + VALIDO;
	public static final String SELECT_BY_ID = SELECT + SqlStatementCommon.WHERE + PK_EQUALS;
	public static final String SELECT_BY_ID_VALIDO = SELECT + SqlStatementCommon.WHERE + PK_EQUALS
			+ SqlStatementCommon.AND + VALIDO;

	public static final String SELECT_ESTESO_BY_PRATICA_END = """
  fnc.*
FROM fnc_checklist_req((SELECT clreq_id FROM k)) fnc
LEFT JOIN (
  SELECT pdc.clreq_id
  FROM      vigil_t_pratica_dettaglio       pd
       JOIN vigil_r_pratica_dettaglio_clreq pdc ON pdc.pratica_det_id = pd.pratica_det_id
       JOIN vigil_d_clreq_esito             ce  ON ce.clreq_esito_id = pdc.clreq_id 
       JOIN vigil_t_pratica                 pa  ON pa.pratica_id = pd.pratica_id
  LEFT JOIN vigil_t_prescrizione            pe  ON pe.prescrizione_id = pd.prescrizione_id
  LEFT JOIN vigil_t_appuntamento            ap  ON ap.appuntamento_id = pd.appuntamento_id
  WHERE pd.data_cancellazione IS NULL 
    AND pa.data_cancellazione IS NULL 
    AND ap.data_cancellazione IS NULL 
    AND pe.data_cancellazione IS NULL 
    AND pdc.data_cancellazione IS NULL 
    AND pd.pratica_id = (SELECT pratica_id FROM k)
    AND pd.dataora_azione <= (SELECT dataora_apertura FROM k)
    AND ce.clreq_esito_cod = 'OK'
  GROUP BY 1
) pdc_ok_ape ON pdc_ok_ape.clreq_id = fnc.clreq_id
LEFT JOIN (
  SELECT pdc.clreq_id
  FROM      vigil_t_pratica_dettaglio       pd
       JOIN vigil_r_pratica_dettaglio_clreq pdc ON pdc.pratica_det_id = pd.pratica_det_id
       JOIN vigil_d_clreq_esito             ce  ON ce.clreq_esito_id = pdc.clreq_id 
       JOIN vigil_t_pratica                 pa  ON pa.pratica_id = pd.pratica_id
  LEFT JOIN vigil_t_prescrizione            pe  ON pe.prescrizione_id = pd.prescrizione_id
  LEFT JOIN vigil_t_appuntamento            ap  ON ap.appuntamento_id = pd.appuntamento_id
  WHERE pd.data_cancellazione IS NULL 
    AND pa.data_cancellazione IS NULL 
    AND ap.data_cancellazione IS NULL 
    AND pe.data_cancellazione IS NULL 
    AND pdc.data_cancellazione IS NULL 
    AND pd.pratica_id = (SELECT pratica_id FROM k)
    AND pd.dataora_azione <= (SELECT dataora_chiusura FROM k)
    AND ce.clreq_esito_cod = 'OK'
  GROUP BY 1
) pdc_ok_chi ON pdc_ok_chi.clreq_id = fnc.clreq_id
LEFT JOIN (
  SELECT clreq_id
  FROM vigil_r_pratica_clreq
  WHERE data_cancellazione IS NULL
    AND pratica_id = (SELECT pratica_id FROM k)
    AND prescrizione_id IS NULL
) pac ON pac.clreq_id = fnc.clreq_id 
LEFT JOIN (
  SELECT clreq_id
  FROM vigil_r_pratica_clreq
  WHERE data_cancellazione IS NULL
    AND pratica_id = (SELECT pratica_id FROM k)
    AND prescrizione_id = (SELECT prescrizione_id FROM k)
) pec ON pec.clreq_id = fnc.clreq_id 
LEFT JOIN (
  SELECT pc1.clreq_id
  FROM vigil_r_pratica_clreq pc1
  JOIN vigil_r_pratica_clreq pc2 ON pc2.pratica_id = pc1.pratica_id AND pc2.clreq_id = pc1.clreq_id AND pc2.data_cancellazione IS NULL 
  JOIN vigil_t_prescrizione  p1  ON p1.prescrizione_id = pc1.prescrizione_id AND p1.data_cancellazione IS NULL 
  JOIN vigil_t_prescrizione  p2  ON p2.prescrizione_id = pc2.prescrizione_id AND p2.data_cancellazione IS NULL 
  WHERE pc1.data_cancellazione IS NULL
    AND pc1.pratica_id = (SELECT pratica_id FROM k)
    AND pc1.prescrizione_id = (SELECT prescrizione_id FROM k)
    AND pc1.prescrizione_id != pc2.prescrizione_id
    AND NOT (p1.dataora_apertura > p2.dataora_chiusura OR p1.dataora_chiusura < p2.dataora_apertura)
  GROUP BY 1
) pec_alt ON pec_alt.clreq_id = fnc.clreq_id
ORDER BY fnc.clreq_ord
""";
	public static final String SELECT_ESTESO_BY_PRATICA_WHERE_PRATICA = """
WITH 
  k AS (
    SELECT
      x.pratica_id,
      NULL::INTEGER prescrizione_id,
      NULL::INTEGER appuntamento_id,
      x.dataora_apertura,
      COALESCE(x.dataora_chiusura, NOW()) dataora_chiusura,
      pt.clreq_id,
      (x.data_cancellazione IS NULL) flg_valido
    FROM vigil_t_pratica      x
    JOIN vigil_d_pratica_tipo pt ON pt.pratica_tipo_id = x.pratica_tipo_id
    WHERE x.pratica_id = :praticaId
    )
SELECT
  ((SELECT flg_valido FROM k) AND fnc.modulo_id IS NOT NULL AND pac.clreq_id IS NOT NULL) flg_selezionato,
  ((SELECT flg_valido FROM k) AND fnc.modulo_id IS NOT NULL) flg_selezionabile,
  ((SELECT flg_valido FROM k) AND fnc.modulo_id IS NOT NULL AND pac.clreq_id IS NOT NULL AND pdc_ok_chi.clreq_id IS NOT NULL) flg_conforme,
""";
	public static final String SELECT_ESTESO_BY_PRATICA_WHERE_PRESCRIZIONE = """
WITH 
  k AS (
    SELECT
      x.pratica_id,
      x.prescrizione_id,
      NULL appuntamento_id,
      x.dataora_apertura,
      COALESCE(x.dataora_chiusura, NOW()) dataora_chiusura,
      pt.clreq_id,
      (x.data_cancellazione IS NULL AND pa.data_cancellazione IS NULL) flg_valido
    FROM vigil_t_prescrizione x
    JOIN vigil_t_pratica      pa ON pa.pratica_id = x.pratica_id
    JOIN vigil_d_pratica_tipo pt ON pt.pratica_tipo_id = pa.pratica_tipo_id
    WHERE x.prescrizione_id = :prescrizioneId
  )
SELECT
  ((SELECT flg_valido FROM k) AND fnc.modulo_id IS NOT NULL AND pdc_ok_ape.clreq_id IS NULL AND pac.clreq_id IS NOT NULL AND pec.clreq_id IS NOT NULL) flg_selezionato,
  ((SELECT flg_valido FROM k) AND fnc.modulo_id IS NOT NULL AND pdc_ok_ape.clreq_id IS NULL AND pac.clreq_id IS NOT NULL AND pec_alt.clreq_id IS NULL) flg_selezionabile,
  ((SELECT flg_valido FROM k) AND fnc.modulo_id IS NOT NULL AND pdc_ok_ape.clreq_id IS NULL AND pac.clreq_id IS NOT NULL AND pec.clreq_id IS NOT NULL AND pdc_ok_chi.clreq_id IS NOT NULL) flg_conforme,
""";
	public static final String SELECT_ESTESO_BY_PRATICA_WHERE_PRATICA_DET = """
WITH 
  k AS (
    SELECT
      x.pratica_id,
      x.prescrizione_id,
      x.appuntamento_id,
      x.dataora_azione dataora_apertura,
      x.dataora_azione dataora_chiusura,
      pt.clreq_id,
      (x.data_cancellazione IS NULL AND pa.data_cancellazione IS NULL AND pe.data_cancellazione IS NULL AND ap.data_cancellazione IS NULL) flg_valido
    FROM      vigil_t_pratica_dettaglio x
         JOIN vigil_t_pratica           pa ON pa.pratica_id = x.pratica_id
         JOIN vigil_d_pratica_tipo      pt ON pt.pratica_tipo_id = pa.pratica_tipo_id
    LEFT JOIN vigil_t_prescrizione      pe ON pe.prescrizione_id = x.prescrizione_id
    LEFT JOIN vigil_t_appuntamento      ap ON ap.appuntamento_id = x.appuntamento_id
    WHERE pratica_det_id = :praticaDetId
  )
SELECT
  ((SELECT flg_valido FROM k) AND fnc.modulo_id IS NOT NULL AND pdc_ok_ape.clreq_id IS NULL AND pac.clreq_id IS NOT NULL AND pec.clreq_id IS NOT NULL) flg_selezionato,
  FALSE flg_selezionabile,
  ((SELECT flg_valido FROM k) AND fnc.modulo_id IS NOT NULL AND pdc_ok_ape.clreq_id IS NULL AND pac.clreq_id IS NOT NULL AND pec.clreq_id IS NOT NULL AND pdc_ok_chi.clreq_id IS NOT NULL) flg_conforme,
 """;
	
	
	
	


	public static final String SELECT_PRATICA_REQUISITO_BY_PRATICA_WHERE_PRATICA_DET = """			
WITH 
  k AS (
    SELECT
      x.pratica_id,
      x.dataora_azione dataora_max,
      :clreqId clreq_id,
      TRUE flg_modificabile
    FROM vigil_t_pratica_dettaglio x 
    WHERE x.pratica_det_id = :praticaDetId
  ),
""";
	public static final String SELECT_PRATICA_REQUISITO_ESTESO_BY_PRATICA_WHERE_PRESCRIZIONE = """
WITH 
  k AS (
    SELECT
      x.pratica_id,
      COALESCE(x.dataora_chiusura, NOW()) dataora_max,
      :clreqId clreq_id,
      FALSE flg_modificabile
    FROM vigil_t_prescrizione x 
    WHERE x.prescrizione_id = :prescrizioneId
  ),
""";
	public static final String SELECT_PRATICA_REQUISITO_ESTESO_BY_PRATICA_WHERE_PRATICA = """ 
WITH 
  k AS (
    SELECT
      x.pratica_id,
      COALESCE(x.dataora_chiusura, NOW()) dataora_max,
      :clreqId clreq_id,
      FALSE flg_modificabile
    FROM vigil_t_pratica      x 
    WHERE x.pratica_id = :praticaId
  ),
""";
	public static final String SELECT_PRATICA_REQUISITO_ESTESO_BY_PRATICA_END = """ 
x AS (
    SELECT
      k.clreq_id,
      pd.pratica_det_id, pd.dataora_azione, a.azione_desc,
      pdc.clreq_esito_id, ce.clreq_esito_cod, ce.clreq_esito_desc,
      pdc.modulo_compilato_id, pdc.note,
      pe.prescrizione_id, pe.prescrizione_tipo_id, pe.prescrizione_numero, pet.prescrizione_tipo_desc,
      ap.appuntamento_id, ap.appuntamento_tipo_id, ap.appuntamento_numero, apt.appuntamento_tipo_desc,
      (ce.clreq_esito_cod = 'KO' AND k.flg_modificabile) flg_modificabile 
    FROM                                      k
         JOIN vigil_t_pratica_dettaglio       pd  ON pd.pratica_id = k.pratica_id
         JOIN vigil_d_azione                  a   ON a.azione_id = pd.azione_id 
         JOIN vigil_r_pratica_dettaglio_clreq pdc ON pdc.pratica_det_id = pd.pratica_det_id
                                                     and pdc.data_cancellazione is null
         JOIN vigil_d_clreq_esito             ce  ON ce.clreq_esito_id = pdc.clreq_esito_id 
         JOIN vigil_t_pratica                 pa  ON pa.pratica_id = pd.pratica_id
    LEFT JOIN vigil_t_prescrizione            pe  ON pe.prescrizione_id = pd.prescrizione_id
    LEFT JOIN vigil_d_prescrizione_tipo       pet ON pet.prescrizione_tipo_id = pe.prescrizione_tipo_id 
    LEFT JOIN vigil_t_appuntamento            ap  ON ap.appuntamento_id = pd.appuntamento_id
    LEFT JOIN vigil_d_appuntamento_tipo       apt ON apt.appuntamento_tipo_id = ap.appuntamento_tipo_id 
    WHERE pd.data_cancellazione IS NULL 
      AND pd.dataora_azione <= k.dataora_max
      AND pdc.clreq_id = k.clreq_id
      AND pa.data_cancellazione IS NULL 
      AND ap.data_cancellazione IS NULL 
      AND pe.data_cancellazione IS NULL 
  ),
  y AS (
    (SELECT DISTINCT ON (clreq_esito_cod) * FROM x WHERE clreq_esito_cod = 'OK' ORDER BY clreq_esito_cod, dataora_azione)
    UNION 
    (SELECT DISTINCT ON (clreq_esito_cod) * FROM x WHERE clreq_esito_cod = 'KO' ORDER BY clreq_esito_cod, dataora_azione DESC)
  )
SELECT DISTINCT ON (clreq_id) * FROM y ORDER BY clreq_id, dataora_azione
""";

	private SqlStatementChecklistReq() {
		throw new IllegalStateException("Utility class");
	}
}
