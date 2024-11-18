/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.util.query;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

public class SqlStatementAppuntamentoSoggetto {

	public static final String PH_PK = "appuntamentoSoggettoId";
	public static final String SELECT = "select vras.* from vigil_r_appuntamento_soggetto vras ";
	public static final String UPDATE = "update vigil_r_appuntamento_soggetto vras";
	public static final String PK_EQUALS = "vras.appuntamento_soggetto_id = :appuntamentoSoggettoId";
	public static final String VALIDO = " vras.data_cancellazione is null "
			+ "AND now() BETWEEN vras.validita_inizio AND coalesce(vras.validita_fine, now()) ";

	public static final String UPDATE_CAMPI = """
			  appuntamento_id = :appuntamentoId,
			  soggetto_id = :soggettoId,
			  ruolo_appuntamento_soggetto_id = :ruoloAppuntamentoSoggettoId,
			  validita_inizio = :validitaInizio,
			  validita_fine = :validitaFine,
			  data_modifica = now(),
			  utente_modifica = :utenteModifica
			""";

	private static final String INSERT = """
			insert
			 into
			 vigilsan.vigil_r_appuntamento_soggetto
			(%s
			 appuntamento_id,
			 soggetto_id,
			 ruolo_appuntamento_soggetto_id,
			 validita_inizio,
			 validita_fine,
			 data_creazione,
			 data_modifica,
			 data_cancellazione,
			 utente_creazione,
			 utente_modifica,
			 utente_cancellazione)
			values(%s
			:appuntamentoId,
			:soggettoId,
			:ruoloAppuntamentoSoggettoId,
			 coalesce(:validitaInizio, now()),
			 :validitaFine,
			 now(),
			 now(),
			 null,
			 :utenteCreazione,
			 :utenteCreazione,
			 null)
			""";

	public static final String INSERT_W_PK = String.format(INSERT, "appuntamento_soggetto_id,", ":" + PH_PK + ",");
	public static final String INSERT_GENERIC = String.format(INSERT, "", "");

	public static final String NEXTVAL_PK_ID = "select nextval('vigil_r_appuntamento_soggetto_appuntamento_soggetto_id_seq'::regclass)";
	public static final String UPDATE_GENERIC = UPDATE + SqlStatementCommon.SET + UPDATE_CAMPI
			+ SqlStatementCommon.WHERE + PK_EQUALS;
	public static final String DELETE = UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE + PK_EQUALS;
	public static final String SELECT_ALL = SELECT + SqlStatementCommon.WHERE + VALIDO;
	public static final String SELECT_BY_ID = SELECT + SqlStatementCommon.WHERE + PK_EQUALS;
	public static final String SELECT_BY_SOGGETTO_ID_APPUNTAMENTO_ID = """
			select
			  vras.*
			from
			  vigil_r_appuntamento_soggetto vras
			where
			  vras.appuntamento_id = :appuntamentoId
			  and vras.soggetto_id = :soggettoId
			  and vras.ruolo_appuntamento_soggetto_id = :ruoloAppuntamentoSoggettoId
			  and vras.data_cancellazione is null
			  and now() between vras.validita_inizio and coalesce(vras.validita_fine,
			  now())
			""";
	public static final String SELECT_VERIFICA_ASSOCIAZIONE_ENTE_SOGGETTO_APPUNTAMENTO = """
			SELECT EXISTS (
			  SELECT 1
			  FROM vigil_t_appuntamento  vta
			  JOIN vigil_t_pratica       vtp  ON vtp.pratica_id = vta.pratica_id
			  JOIN vigil_r_ente_soggetto vres ON vres.ente_id = vtp.ente_id
			  WHERE vta.appuntamento_id = :appuntamentoId
			    AND vta.data_cancellazione IS NULL
			    AND vres.soggetto_id = :soggettoId
			    AND vres.data_cancellazione IS NULL
			    AND vta.dataora_inizio BETWEEN vres.validita_inizio AND COALESCE(vres.validita_fine,NOW())
			)
			""";
	public static final String SELECT_BY_APPUNTAMENTO = """
			select
			 vras.*,
			 vdres.ruolo_ente_soggetto_id,
			 vdres.ruolo_ente_soggetto_cod,
			 vdres.ruolo_ente_soggetto_desc,
			 vdras.ruolo_appuntamento_soggetto_id,
			 vdras.ruolo_appuntamento_soggetto_cod,
			 vdras.ruolo_appuntamento_soggetto_desc
			from
			 vigil_r_appuntamento_soggetto vras
			join vigil_t_appuntamento vta on
			 vta.appuntamento_id = vras.appuntamento_id
			join vigil_t_pratica vtp on
			 vtp.pratica_id = vta.pratica_id
			join vigil_r_ente_soggetto vres on
			  vres.ente_id = vtp.ente_id
			  and vres.soggetto_id = vras.soggetto_id
			  and vres.data_cancellazione is null
			  and now() between vres.validita_inizio and coalesce(vres.validita_fine,now())
			join vigil_d_ruolo_ente_soggetto vdres on
			 vdres.ruolo_ente_soggetto_id = vres.ruolo_ente_soggetto_id
			join vigil_d_ruolo_appuntamento_soggetto vdras on
			 vdras.ruolo_appuntamento_soggetto_id = vras.ruolo_appuntamento_soggetto_id
			where
			 vras.appuntamento_id = :appuntamentoId
			 and
			 vras.data_cancellazione is null
			 and now() between vras.validita_inizio and coalesce(vras.validita_fine,
			 now())
			  order by vdras.ruolo_appuntamento_soggetto_ord
			  """;

	public static final String SELECT_BY_APPUNTAMENTO_W_SOGGETTO = """
			select
			 vras.*,
			 vdres.ruolo_ente_soggetto_id,
			 vdres.ruolo_ente_soggetto_cod,
			 vdres.ruolo_ente_soggetto_desc,
			 vdras.ruolo_appuntamento_soggetto_id,
			 vdras.ruolo_appuntamento_soggetto_cod,
			 vdras.ruolo_appuntamento_soggetto_desc,
			 vts.nome,
			 vts.cognome 
			from
			 vigil_r_appuntamento_soggetto vras
			join vigil_t_appuntamento vta on
			 vta.appuntamento_id = vras.appuntamento_id
			join vigil_t_pratica vtp on
			 vtp.pratica_id = vta.pratica_id
			join vigil_r_ente_soggetto vres on
			  vres.ente_id = vtp.ente_id
			  and vres.soggetto_id = vras.soggetto_id
			  and vres.data_cancellazione is null
			  and now() between vres.validita_inizio and coalesce(vres.validita_fine,now())
			join vigil_d_ruolo_ente_soggetto vdres on
			 vdres.ruolo_ente_soggetto_id = vres.ruolo_ente_soggetto_id
			join vigil_d_ruolo_appuntamento_soggetto vdras on
			 vdras.ruolo_appuntamento_soggetto_id = vras.ruolo_appuntamento_soggetto_id
			join vigil_t_soggetto vts on vts.soggetto_id = vras.soggetto_id 
			where
			 vras.appuntamento_id = :appuntamentoId
			 and
			 vras.data_cancellazione is null
			 and now() between vras.validita_inizio and coalesce(vras.validita_fine,
			 now())
			  order by vdras.ruolo_appuntamento_soggetto_ord
			  """;

	public static final String SELECT_RUOLO_APPUNTAMENTO_SOGGETTO_DECODIFICA = """
			select
			  *
			from
			  vigil_d_ruolo_appuntamento_soggetto vdras
			where
			  vdras.data_cancellazione is null
			  and now() between vdras.validita_inizio and coalesce(vdras.validita_fine,
			  now())
			  order by vdras.ruolo_appuntamento_soggetto_ord
			""";

	private SqlStatementAppuntamentoSoggetto() {
		throw new IllegalStateException("Utility class");
	}
}
