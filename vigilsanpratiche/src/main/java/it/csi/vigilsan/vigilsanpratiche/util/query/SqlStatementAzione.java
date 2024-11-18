/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.util.query;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

public class SqlStatementAzione {

 public static final String PH_PK = "azioneId";
 public static final String SELECT = "select vda.* from vigil_d_azione vda ";
 public static final String UPDATE = "update vigil_d_azione vda";
 public static final String PK_EQUALS = "vda.azione_id = :azioneId";
 public static final String VALIDO = " vda.data_cancellazione is null "
   + "AND now() BETWEEN vda.validita_inizio AND coalesce(vda.validita_fine, now()) ";

 public static final String UPDATE_CAMPI = """
     pratica_stato_cod = :praticaStatoCod,
     pratica_stato_desc = :praticaStatoDesc,
     pratica_stato_finale = :praticaStatoFinale,
     validita_inizio = :validitaInizio,
     validita_fine = :validitaFine,
     data_modifica = now(),
     utente_modifica = :utenteModifica
   """;

 private static final String INSERT = """
   insert
    into
    vigilsan.vigil_d_pratica_stato
   (%s
    pratica_stato_cod,
    pratica_stato_desc,
    pratica_stato_finale,
    validita_inizio,
    validita_fine,
    data_creazione,
    data_modifica,
    data_cancellazione,
    utente_creazione,
    utente_modifica,
    utente_cancellazione)
   values(%s
   :praticaStatoCod,
   :praticaStatoDesc,
   :praticaStatoFinale,
    coalesce(:validitaInizio, now()),
    :validitaFine,
    now(),
    now(),
    null,
    :utenteCreazione,
    :utenteCreazione,
    null)
   """;
 public static final String INSERT_W_PK = String.format(INSERT, "azione_id,", ":" + PH_PK + ",");
 public static final String INSERT_GENERIC = String.format(INSERT, "", "");

 public static final String NEXTVAL_PK_ID = "select nextval('vigil_d_pratica_stato_pratica_stato_id_seq'::regclass)";
 public static final String UPDATE_GENERIC = UPDATE + SqlStatementCommon.SET + UPDATE_CAMPI
   + SqlStatementCommon.WHERE + PK_EQUALS;
 public static final String DELETE = UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE + PK_EQUALS;
 public static final String SELECT_ALL = SELECT + SqlStatementCommon.WHERE + VALIDO;
 public static final String SELECT_BY_ID = SELECT + SqlStatementCommon.WHERE + PK_EQUALS;

 public static final String SELECT_AZIONE_INIZIALE_BY_ID = """
WITH
     paw AS (
       SELECT *
       FROM vigil_r_pratica_workflow
       WHERE pratica_tipo_id = :praticaTipoId
         AND data_cancellazione IS NULL
         AND NOW() BETWEEN validita_inizio AND COALESCE(validita_fine, NOW())
     ),
     pew AS (
       SELECT *
       FROM vigil_r_prescrizione_workflow
       WHERE pratica_tipo_id = :praticaTipoId
         AND data_cancellazione IS NULL
         AND NOW() BETWEEN validita_inizio AND COALESCE(validita_fine, NOW())
     ),
     apw AS (
       SELECT *
       FROM vigil_r_appuntamento_workflow
       WHERE pratica_tipo_id = :praticaTipoId
         AND data_cancellazione IS NULL
         AND NOW() BETWEEN validita_inizio AND COALESCE(validita_fine, NOW())
     ),
     w AS (
       SELECT
         azione_id,
         MAX(modulo_id) modulo_id,
         ARRAY_TO_STRING(ARRAY_AGG(pratica_stato_id_iniziale),',') stato_id_lista
       FROM paw
       GROUP BY 1
       ORDER BY 1
     )
     SELECT
        vda.*, 
        NULL::INTEGER prescrizione_tipo_id,
        NULL::INTEGER appuntamento_tipo_id,
        NULL::INTEGER modulo_id,
        NULL::INTEGER stato_id_lista,
        EXISTS (SELECT 1 FROM paw z WHERE z.azione_id = vda.azione_id AND z.pratica_stato_id_iniziale      IS NULL) azione_iniziale_pratica,
        EXISTS (SELECT 1 FROM pew z WHERE z.azione_id = vda.azione_id AND z.prescrizione_stato_id_iniziale IS NULL) azione_iniziale_prescrizione,
        EXISTS (SELECT 1 FROM apw z WHERE z.azione_id = vda.azione_id AND z.appuntamento_stato_id_iniziale IS NULL) azione_iniziale_appuntamento
      from vigil_d_azione vda 
"""
   + SqlStatementCommon.WHERE + PK_EQUALS;

 public static final String SELECT_AZIONI_APPUNTAMENTO = """
     WITH
     w AS (
       SELECT
         azione_id, appuntamento_tipo_id,
         MAX(modulo_id) modulo_id,
         ARRAY_TO_STRING(ARRAY_AGG(appuntamento_stato_id_iniziale),',') stato_id_lista
       FROM vigil_r_appuntamento_workflow
       WHERE pratica_tipo_id = :praticaTipoId
         AND data_cancellazione IS NULL
         AND NOW() BETWEEN validita_inizio AND COALESCE(validita_fine, NOW())
         AND appuntamento_stato_id_iniziale IS NOT NULL
       GROUP BY 1,2
       ORDER BY 1,2
     )
   SELECT a.*,
     NULL::INTEGER prescrizione_tipo_id,
     w.appuntamento_tipo_id,
     w.modulo_id,
     w.stato_id_lista,
     FALSE azione_iniziale_pratica,
     FALSE azione_iniziale_prescrizione,
     FALSE azione_iniziale_appuntamento
   FROM vigil_d_azione a JOIN w ON w.azione_id = a.azione_id
   WHERE a.data_cancellazione IS NULL
     AND NOW() BETWEEN a.validita_inizio AND COALESCE(a.validita_fine,NOW())
     AND a.azione_id IN (
       SELECT ac.azione_id
       FROM vigil_r_azione_config ac
       WHERE ac.pratica_tipo_id = :praticaTipoId
         AND ac.profilo_id = :profiloId
         AND ac.abilitazione = 'W'
     )
   ORDER BY a.azione_desc, w.appuntamento_tipo_id
     """;
 public static final String SELECT_AZIONI_PRATICA = """
     WITH
     paw AS (
       SELECT *
       FROM vigil_r_pratica_workflow
       WHERE pratica_tipo_id = :praticaTipoId
         AND data_cancellazione IS NULL
         AND NOW() BETWEEN validita_inizio AND COALESCE(validita_fine, NOW())
     ),
     pew AS (
       SELECT *
       FROM vigil_r_prescrizione_workflow
       WHERE pratica_tipo_id = :praticaTipoId
         AND data_cancellazione IS NULL
         AND NOW() BETWEEN validita_inizio AND COALESCE(validita_fine, NOW())
     ),
     apw AS (
       SELECT *
       FROM vigil_r_appuntamento_workflow
       WHERE pratica_tipo_id = :praticaTipoId
         AND data_cancellazione IS NULL
         AND NOW() BETWEEN validita_inizio AND COALESCE(validita_fine, NOW())
     ),
     w AS (
       SELECT
         azione_id,
         MAX(modulo_id) modulo_id,
         ARRAY_TO_STRING(ARRAY_AGG(pratica_stato_id_iniziale),',') stato_id_lista
       FROM paw
       GROUP BY 1
       ORDER BY 1
     )
   SELECT a.*,
     NULL::INTEGER prescrizione_tipo_id,
     NULL::INTEGER appuntamento_tipo_id,
     w.modulo_id,
     w.stato_id_lista,
     EXISTS (SELECT 1 FROM paw z WHERE z.azione_id = a.azione_id AND z.pratica_stato_id_iniziale      IS NULL) azione_iniziale_pratica,
     EXISTS (SELECT 1 FROM pew z WHERE z.azione_id = a.azione_id AND z.prescrizione_stato_id_iniziale IS NULL) azione_iniziale_prescrizione,
     EXISTS (SELECT 1 FROM apw z WHERE z.azione_id = a.azione_id AND z.appuntamento_stato_id_iniziale IS NULL) azione_iniziale_appuntamento
   FROM vigil_d_azione a JOIN w ON w.azione_id = a.azione_id
   WHERE a.data_cancellazione IS NULL
     AND NOW() BETWEEN a.validita_inizio AND COALESCE(a.validita_fine,NOW())
     AND a.azione_id IN (
       SELECT ac.azione_id
       FROM vigil_r_azione_config ac
       WHERE ac.pratica_tipo_id = :praticaTipoId
         AND ac.profilo_id = :profiloId
         AND ac.abilitazione = 'W'
     )
   ORDER BY a.azione_desc
     """;
 public static final String SELECT_AZIONI_PRESCRIZIONE = """
     WITH
     w AS (
       SELECT
         azione_id, prescrizione_tipo_id,
         MAX(modulo_id) modulo_id,
         ARRAY_TO_STRING(ARRAY_AGG(prescrizione_stato_id_iniziale),',') stato_id_lista
       FROM vigil_r_prescrizione_workflow
       WHERE pratica_tipo_id = :praticaTipoId
         AND data_cancellazione IS NULL
         AND NOW() BETWEEN validita_inizio AND COALESCE(validita_fine, NOW())
         AND prescrizione_stato_id_iniziale IS NOT NULL
       GROUP BY 1,2
       ORDER BY 1,2
     )
   SELECT a.*,
     w.prescrizione_tipo_id,
     NULL::INTEGER appuntamento_tipo_id,
     w.modulo_id,
     w.stato_id_lista,
     FALSE azione_iniziale_pratica,
     FALSE azione_iniziale_prescrizione,
     FALSE azione_iniziale_appuntamento
   FROM vigil_d_azione a JOIN w ON w.azione_id = a.azione_id
   WHERE a.data_cancellazione IS NULL
     AND NOW() BETWEEN a.validita_inizio AND COALESCE(a.validita_fine,NOW())
     AND a.azione_id IN (
       SELECT ac.azione_id
       FROM vigil_r_azione_config ac
       WHERE ac.pratica_tipo_id = :praticaTipoId
         AND ac.profilo_id = :profiloId
         AND ac.abilitazione = 'W'
     )
   ORDER BY a.azione_desc, w.prescrizione_tipo_id
     """;

 private SqlStatementAzione() {
  throw new IllegalStateException("Utility class");
 }
}
