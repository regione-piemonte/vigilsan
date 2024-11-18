/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.util.query;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

public class SqlStatementPraticaDettaglio {

	public static final String PH_PK = "praticaDetId";
	public static final String SELECT = "select vtpd.* from vigil_t_pratica_dettaglio vtpd ";
	public static final String UPDATE = "update vigil_t_pratica_dettaglio vtpd";
	public static final String PK_EQUALS = "vtpd.pratica_det_id = :praticaDetId";
	public static final String VALIDO = " vtpd.data_cancellazione is null "
			+ "AND now() BETWEEN vtpd.validita_inizio AND coalesce(vtpd.validita_fine, now()) ";

	public static final String UPDATE_CAMPI = """
			pratica_id = :praticaId,
			pratica_stato_id = :praticaStatoId,
			prescrizione_id = :prescrizioneId,
			prescrizione_stato_id = :prescrizioneStatoId,
			appuntamento_id = :appuntamentoId,
			appuntamento_stato_id = :appuntamentoStatoId,
			azione_id = :azioneId,
			dataora_azione = :dataoraAzione,
			modulo_compilato_id = :moduloCompilatoId,
			note = :note,
			profilo_id = :profiloId,
			profilo_id_scadenza = :profiloIdScadenza,
			flg_scadenza = :flgScadenza,
			    validita_inizio = :validitaInizio,
			    validita_fine = :validitaFine,
			    data_modifica = now(),
			    utente_modifica = :utenteModifica
			  """;

	private static final String INSERT = """
			insert
			 into
			 vigilsan.vigil_t_pratica_dettaglio
			(%s
			 pratica_id,
			 pratica_stato_id,
			 prescrizione_id,
			 prescrizione_stato_id,
			 appuntamento_id,
			 appuntamento_stato_id,
			 azione_id,
			 dataora_azione,
			 modulo_compilato_id,
			 note,
			 profilo_id,
			 profilo_id_scadenza,
			 flg_scadenza,
			 validita_inizio,
			 validita_fine,
			 data_creazione,
			 data_modifica,
			 data_cancellazione,
			 utente_creazione,
			 utente_modifica,
			 utente_cancellazione)
			values(%s
			:praticaId,
			:praticaStatoId,
			:prescrizioneId,
			:prescrizioneStatoId,
			:appuntamentoId,
			:appuntamentoStatoId,
			:azioneId,
			:dataoraAzione,
			:moduloCompilatoId,
			:note,
			:profiloId,
			:profiloIdScadenza,
			:flgScadenza,
			 coalesce(:validitaInizio, now()),
			 :validitaFine,
			 now(),
			 now(),
			 null,
			 :utenteCreazione,
			 :utenteCreazione,
			 null)
			""";
	public static final String SELECT_SCADENZE = """
			with
			  sc as (
			select
			 vtpd.dataora_azione dataora_scadenza,
			 vtpd.flg_scadenza,
			 vda.azione_desc scadenza_desc,
			 vtpd.pratica_id,
			 vtpd.prescrizione_id,
			 vtpd.appuntamento_id
			from
			 vigil_t_pratica_dettaglio vtpd
			join vigil_t_pratica vtpa on
			 vtpa.pratica_id = vtpd.pratica_id
			join vigil_d_azione vda on
			 vda.azione_id = vtpd.azione_id
			where
			 vtpd.flg_scadenza is not null
			 and vtpd.profilo_id_scadenza = :profiloId
			 and DATE_TRUNC('day',
			 vtpd.dataora_azione) >= :dataDa::DATE
			 and DATE_TRUNC('day',
			 vtpd.dataora_azione) <= :dataA::DATE
			 and vtpd.data_cancellazione is null
			 and NOW() between vtpd.validita_inizio and coalesce(vtpd.validita_fine,
			 NOW())
			      %s
			  ),
			  sl as (
			select
			 vta.dataora_inizio dataora_scadenza,
			 'S' flg_scadenza,
			 vdat.appuntamento_tipo_desc scadenza_desc,
			 vta.pratica_id,
			 null::INTEGER prescrizione_id,
			 vta.appuntamento_id
			from
			 vigil_t_appuntamento vta
			join vigil_t_pratica vtpa on
			 vtpa.pratica_id = vta.pratica_id
			join vigil_d_appuntamento_tipo vdat on
			 vdat.appuntamento_tipo_id = vta.appuntamento_tipo_id
			where
			 DATE_TRUNC('day',
			 vta.dataora_inizio) >= :dataDa::DATE
			  and DATE_TRUNC('day',
			  vta.dataora_inizio) <= :dataA::DATE
			   and vta.data_cancellazione is null
			   and NOW() between vta.validita_inizio and coalesce(vta.validita_fine,
			   NOW())
			     %s
			  ),
			  s as ( select * from sc
			union
			select * from sl )
			select
			 s.*,
			 vtpa.ente_id,
			 vte.ente_cod,
			 vte.ente_desc,
			 vtpa.struttura_id,
			 vts.struttura_cod,
			 vts.struttura_cod_arpe,
			 vts.struttura_desc,
			 vtpa.pratica_tipo_id,
			 vdpat.pratica_tipo_cod,
			 vdpat.pratica_tipo_desc,
			 vtpa.dataora_apertura,
			 vtpa.dataora_chiusura,
			 vtpe.prescrizione_tipo_id,
			 vdpet.prescrizione_tipo_cod,
			 vdpet.prescrizione_tipo_desc,
			 vtap.appuntamento_tipo_id,
			 vdapt.appuntamento_tipo_cod,
			 vdapt.appuntamento_tipo_desc
			from
			 s
			join vigil_t_pratica vtpa on
			 vtpa.pratica_id = s.pratica_id and vtpa.data_cancellazione is null
			join vigil_t_ente vte on
			 vte.ente_id = vtpa.ente_id
			join vigil_t_struttura vts on
			 vts.struttura_id = vtpa.struttura_id
			join vigil_d_pratica_tipo vdpat on
			 vdpat.pratica_tipo_id = vtpa.pratica_tipo_id
			left join vigil_t_prescrizione vtpe on
			 vtpe.prescrizione_id = s.prescrizione_id
			left join vigil_d_prescrizione_tipo vdpet on
			 vdpet.prescrizione_tipo_id = vtpe.prescrizione_tipo_id
			left join vigil_t_appuntamento vtap on
			 vtap.appuntamento_id = s.appuntamento_id
			left join vigil_d_appuntamento_tipo vdapt on
			 vdapt.appuntamento_tipo_id = vtap.appuntamento_tipo_id
			order by
			 s.dataora_scadenza
			""";
	public static final String SELECT_SCADENZE_ENTE = """
			 and vtpa.ente_id = :enteId
			""";
	public static final String SELECT_SCADENZE_STRUTTURA = """
			 and vtpa.struttura_id = :strutturaId
			""";
	public static final String SELECT_BY_ID_AND_PROFILO = """
WITH a AS (
  SELECT vtpd.*, vda.azione_desc, COALESCE(vrpw.modulo_id, vraw.modulo_id, vrpew.modulo_id) modulo_id,
    (vtpd.flg_scadenza IS NULL AND vrac.flg_clreq_esito) flg_clreq_esito,
    (vtpd.flg_scadenza IS NULL AND vrac.flg_bloccante) flg_bloccante
  FROM vigil_t_pratica_dettaglio vtpd
  JOIN vigil_d_azione            vda  ON vda.azione_id = vtpd.azione_id
  JOIN vigil_t_pratica           vtp  ON vtp.pratica_id = vtpd.pratica_id
  LEFT JOIN vigil_t_appuntamento vta  ON vta.appuntamento_id = vtpd.appuntamento_id
  LEFT JOIN vigil_t_prescrizione vtpe ON vtpe.prescrizione_id = vtpd.prescrizione_id
  LEFT JOIN (
    SELECT pratica_tipo_id, azione_id, MAX(modulo_id) modulo_id
    FROM vigil_r_pratica_workflow
    WHERE data_cancellazione IS NULL
    GROUP BY 1,2
  ) vrpw ON vrpw.pratica_tipo_id = vtp.pratica_tipo_id AND vrpw.azione_id = vtpd.azione_id
  LEFT JOIN (
    SELECT pratica_tipo_id, appuntamento_tipo_id, azione_id, MAX(modulo_id) modulo_id
    FROM vigil_r_appuntamento_workflow
    WHERE data_cancellazione IS NULL
    GROUP BY 1,2,3
  ) vraw ON vraw.pratica_tipo_id = vtp.pratica_tipo_id AND vraw.appuntamento_tipo_id = vta.appuntamento_tipo_id AND vraw.azione_id = vtpd.azione_id
  LEFT JOIN (
    SELECT pratica_tipo_id, prescrizione_tipo_id, azione_id, MAX(modulo_id) modulo_id
    FROM vigil_r_prescrizione_workflow
    WHERE data_cancellazione IS NULL
    GROUP BY 1,2,3
  ) vrpew ON vrpew.pratica_tipo_id = vtp.pratica_tipo_id AND vrpew.prescrizione_tipo_id = vtpe.prescrizione_tipo_id AND vrpew.azione_id = vtpd.azione_id
  LEFT JOIN (
    SELECT DISTINCT ON (pratica_tipo_id, azione_id) pratica_tipo_id, azione_id, flg_clreq_esito, flg_bloccante
    FROM vigil_r_azione_config
    WHERE abilitazione = 'W' AND data_cancellazione IS NULL
    ORDER BY pratica_tipo_id, azione_id
  ) vrac ON vrac.pratica_tipo_id = vtp.pratica_tipo_id AND vrac.azione_id = vtpd.azione_id
  WHERE vtp.pratica_id = :praticaId
    AND vtp.data_cancellazione IS NULL
    AND vtpd.data_cancellazione IS NULL 
     AND now() BETWEEN vtpd.validita_inizio AND coalesce(vtpd.validita_fine, now()) 
     %s 
)
SELECT *, (EXISTS (SELECT 1 FROM a z WHERE z.flg_bloccante AND z.dataora_azione >= a.dataora_azione AND z.pratica_det_id <> a.pratica_det_id)) flg_bloccato
FROM a
ORDER BY dataora_azione
""";
	public static final String SELECT_BY_ID_AND_PROFILO_ORDER_BY = " order by vtpd.dataora_azione";

	public static final String SELECT_BY_ID_AND_PROFILO_PER_DIARIO = """
			SELECT vtpd.*, vta.appuntamento_numero, vtpe.prescrizione_numero,vda.azione_desc, COALESCE(vrpw.modulo_id, vraw.modulo_id, vrpew.modulo_id) modulo_id,
			(vtpd.flg_scadenza IS NULL AND vrac.flg_clreq_esito) flg_clreq_esito
			FROM vigil_t_pratica_dettaglio vtpd
			JOIN vigil_d_azione            vda  ON vda.azione_id = vtpd.azione_id
			JOIN vigil_t_pratica           vtp  ON vtp.pratica_id = vtpd.pratica_id
			LEFT JOIN vigil_t_appuntamento vta  ON vta.appuntamento_id = vtpd.appuntamento_id
			LEFT JOIN vigil_t_prescrizione vtpe ON vtpe.prescrizione_id = vtpd.prescrizione_id
			LEFT JOIN (
			  SELECT pratica_tipo_id, azione_id, MAX(modulo_id) modulo_id
			  FROM vigil_r_pratica_workflow
			  WHERE data_cancellazione IS NULL
			  GROUP BY 1,2
			) vrpw ON vrpw.pratica_tipo_id = vtp.pratica_tipo_id AND vrpw.azione_id = vtpd.azione_id
			LEFT JOIN (
			  SELECT pratica_tipo_id, appuntamento_tipo_id, azione_id, MAX(modulo_id) modulo_id
			  FROM vigil_r_appuntamento_workflow
			  WHERE data_cancellazione IS NULL
			  GROUP BY 1,2,3
			) vraw ON vraw.pratica_tipo_id = vtp.pratica_tipo_id AND vraw.appuntamento_tipo_id = vta.appuntamento_tipo_id AND vraw.azione_id = vtpd.azione_id
			LEFT JOIN (
			  SELECT pratica_tipo_id, prescrizione_tipo_id, azione_id, MAX(modulo_id) modulo_id
			  FROM vigil_r_prescrizione_workflow
			  WHERE data_cancellazione IS NULL
			  GROUP BY 1,2,3
			) vrpew ON vrpew.pratica_tipo_id = vtp.pratica_tipo_id AND vrpew.prescrizione_tipo_id = vtpe.prescrizione_tipo_id AND vrpew.azione_id = vtpd.azione_id
			LEFT JOIN (
			  SELECT DISTINCT ON (pratica_tipo_id, azione_id) pratica_tipo_id, azione_id, flg_clreq_esito
			  FROM vigil_r_azione_config
			  WHERE abilitazione = 'W' AND data_cancellazione IS NULL
			  ORDER BY pratica_tipo_id, azione_id
			) vrac ON vrac.pratica_tipo_id = vtp.pratica_tipo_id AND vrac.azione_id = vtpd.azione_id
			WHERE vtp.pratica_id = :praticaId
			  AND vtp.data_cancellazione IS NULL
			  AND vtpd.data_cancellazione IS NULL
			"""
			+ SqlStatementCommon.AND + VALIDO;
	public static final String SELECT_BY_ID_AND_PROFILO_PER_DIARIO_FILTER_DATE = SqlStatementCommon.AND
			+ " vtpd.dataora_azione <= :filterDate ";
	public static final String SELECT_BY_ID_AND_PROFILO_PER_DIARIO_ORDER_BY = " order by vtpd.dataora_azione";
	// JOIN vigil_r_azione_config vrac ON vrac.azione_id = vtpd.azione_id AND
	// vrac.pratica_tipo_id = vtp.pratica_tipo_id AND vrac.profilo_id = :profiloId
	// AND vrac.data_cancellazione IS NULL

	public static final String INSERT_W_PK = String.format(INSERT, "pratica_det_id,", ":" + PH_PK + ",");
	public static final String INSERT_GENERIC = String.format(INSERT, "", "");

	public static final String NEXTVAL_PK_ID = "select nextval('vigil_t_pratica_dettaglio_pratica_det_id_seq'::regclass) ";
	public static final String UPDATE_GENERIC = UPDATE + SqlStatementCommon.SET + UPDATE_CAMPI
			+ SqlStatementCommon.WHERE + PK_EQUALS;
	public static final String UPDATE_CAMPI_POST_PRATICHE = """
			pratica_id = :praticaId,
			pratica_stato_id = :praticaStatoId,
			prescrizione_id = :prescrizioneId,
			prescrizione_stato_id = :prescrizioneStatoId,
			appuntamento_id = :appuntamentoId,
			appuntamento_stato_id = :appuntamentoStatoId,
			azione_id = :azioneId,
			dataora_azione = :dataoraAzione,
			modulo_compilato_id = :moduloCompilatoId,
			note = :note,
			profilo_id = :profiloId,
			profilo_id_scadenza = :profiloIdScadenza,
			flg_scadenza = :flgScadenza,
			    validita_inizio = NOW(),
			    validita_fine = :validitaFine,
			    data_creazione = now(),
			    data_modifica = now(),
			    data_cancellazione = null,
			    utente_creazione = :utenteCreazione,
			    utente_modifica = :utenteCreazione,
			    utente_cancellazione = null
			  """;

	public static final String UPDATE_SPECIFICA_POST_PRATICHE = UPDATE + SqlStatementCommon.SET
			+ UPDATE_CAMPI_POST_PRATICHE + SqlStatementCommon.WHERE + PK_EQUALS;
	public static final String DELETE = UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE + PK_EQUALS;
	public static final String SELECT_ALL = SELECT + SqlStatementCommon.WHERE + VALIDO;
	public static final String SELECT_BY_ID = SELECT + SqlStatementCommon.WHERE + PK_EQUALS;
	public static final String SELECT_BY_PRATICA_ID_PRATICA_DET_ID_PROFILO_ID = SELECT + SqlStatementCommon.WHERE
			+ PK_EQUALS + SqlStatementCommon.AND
			+ " vtpd.pratica_id = :praticaId and vtpd.profilo_id = :profiloId and vtpd.abilitazione = 'W' "
			+ SqlStatementCommon.AND + VALIDO;
	public static final String SELECT_BY_PRATICA_ID_PRATICA_DET_ID = SELECT + SqlStatementCommon.WHERE + PK_EQUALS
			+ SqlStatementCommon.AND + " vtpd.pratica_id = :praticaId " + SqlStatementCommon.AND + VALIDO;

	public static final String SELECT_PRATICHE_DETTAGLIO_FOR_POST = """
			  SELECT x.* FROM (
			     SELECT
			       pd.pratica_det_id,
			       pd.pratica_id,
			       pd.pratica_stato_id,
			       pd.prescrizione_id,
			       pd.prescrizione_stato_id,
			       pd.appuntamento_id,
			       pd.appuntamento_stato_id,
			       pd.azione_id,
			       pd.dataora_azione,
			       pd.modulo_compilato_id,
			       pd.note,
			       pd.profilo_id,
			       pd.profilo_id_scadenza,
			       pd.flg_scadenza,
			       pd.validita_inizio,
			       pd.validita_fine,
			       pd.data_creazione,
			       pd.data_modifica,
			       pd.data_cancellazione,
			       pd.utente_creazione,
			       pd.utente_modifica,
			       pd.utente_cancellazione,
			       vtp.pratica_numero,
			       vta.appuntamento_numero,
			       vtpr.prescrizione_numero,
			       CASE WHEN pd.pratica_det_id = :praticaDetId THEN 'D' ELSE '-' END
			update_status
			     FROM vigil_t_pratica_dettaglio pd
			     JOIN vigil_t_pratica vtp on vtp.pratica_id = pd.pratica_id
			     LEFT JOIN vigil_t_appuntamento vta on vta.appuntamento_id = pd.appuntamento_id
			     LEFT JOIN vigil_t_prescrizione vtpr on vtpr.prescrizione_id = pd.prescrizione_id
			     WHERE pd.pratica_id = :praticaId
			       AND pd.data_cancellazione IS NULL
			       AND pd.validita_fine IS NULL
			       AND (pd.flg_scadenza IS NULL OR pd.flg_scadenza = 'M')
			       %s
			   ) x ORDER BY dataora_azione, data_modifica
			 """;

	public static final String SELECT_PRATICHE_DETTAGLIO_FOR_POST_INSERIMENTO = """
			      UNION
			      SELECT
			        -1 pratica_det_id,
			        :praticaId pratica_id,
			        null::INTEGER pratica_stato_id,
			        :prescrizioneId prescrizione_id,
			        null::INTEGER prescrizione_stato_id,
			        :appuntamentoId appuntamento_id,
			        null::INTEGER appuntamento_stato_id,
			        :azioneId azione_id,
			        :dataoraAzione dataora_azione,
			        :moduloCompilatoId modulo_compilato_id,
			        :note note,
			        :profiloId profilo_id,
			          :profiloIdScadenza profilo_id_scadenza,
			        :flgScadenza flg_scadenza,
			        NOW() validita_inizio,
			        NULL validita_fine,
			        NOW() data_creazione,
			        NOW() data_modifica,
			        NULL data_cancellazione,
			        :utente utente_creazione,
			        :utente utente_modifica,
			        NULL utente_cancellazione,
			        :praticaNumero pratica_numero,
			        :appuntamentoNumero appuntamento_numero,
			        :prescrizioneNumero prescrizione_numero,
			        'I' update_status
			""";
	public static final String SELECT_SCADENZE_ESISTENTI = """
			       SELECT pd.*, 'D' update_status,
			       null::TEXT pratica_numero,
			       null::TEXT appuntamento_numero,
			       null::TEXT prescrizione_numero
			       FROM vigil_t_pratica_dettaglio pd
			        WHERE pd.pratica_id = :praticaId
			          AND pd.flg_scadenza = 'A'
			""";

	private SqlStatementPraticaDettaglio() {
		throw new IllegalStateException("Utility class");
	}
}
