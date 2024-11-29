/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.util.query;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

public class SqlStatementEnteSoggetto {

	public static final String PH_PK = "enteSoggId";
	public static final String SELECT = "select vres.* from vigil_r_ente_soggetto vres ";
	public static final String UPDATE = "update vigil_r_ente_soggetto vres";
	public static final String PK_EQUALS = "vres.ente_sogg_id = :enteSoggId";
	public static final String VALIDO = " vres.data_cancellazione is null "
			+ "AND now() BETWEEN vres.validita_inizio AND coalesce(vres.validita_fine, now()) ";

	public static final String UPDATE_CAMPI = """
			  ruolo_ente_soggetto_id = :ruoloEnteSoggettoId,
			  ente_id = :enteId,
			  soggetto_id = :soggettoId,
			  validita_inizio = :validitaInizio,
			  validita_fine = :validitaFine,
			  data_modifica = now(),
			  utente_modifica = :utenteModifica
			""";

	private static final String INSERT = """
			insert
			 into
			 vigilsan.vigil_r_ente_soggetto
			(%s
			 ente_id,
			 soggetto_id,
			 ruolo_ente_soggetto_id,
			 validita_inizio,
			 validita_fine,
			 data_creazione,
			 data_modifica,
			 data_cancellazione,
			 utente_creazione,
			 utente_modifica,
			 utente_cancellazione)
			values(%s
			:enteId,
			:soggettoId,
			:ruoloEnteSoggettoId,
			 coalesce(:validitaInizio, now()),
			 :validitaFine,
			 now(),
			 now(),
			 null,
			 :utenteCreazione,
			 :utenteCreazione,
			 null)
			""";

	public static final String INSERT_W_PK = String.format(INSERT, "ente_sogg_id,", ":" + PH_PK + ",");
	public static final String INSERT_GENERIC = String.format(INSERT, "", "");

	public static final String NEXTVAL_PK_ID = "select nextval('vigil_r_ente_soggetto_ente_sogg_id_seq'::regclass)";
	public static final String UPDATE_GENERIC = UPDATE + SqlStatementCommon.SET + UPDATE_CAMPI
			+ SqlStatementCommon.WHERE + PK_EQUALS;

	public static final String INVALIDA_BY_ID = UPDATE + SqlStatementCommon.INVALIDA + SqlStatementCommon.WHERE
			+ PK_EQUALS;
	public static final String DELETE = UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE + PK_EQUALS;
	public static final String SELECT_ALL = SELECT + SqlStatementCommon.WHERE + VALIDO;
	public static final String SELECT_BY_ID = SELECT + SqlStatementCommon.WHERE + PK_EQUALS;
	public static final String SELECT_BY_LISTA_ENTE_ID = """
			   select
			    vres.*,
			    vdres.ruolo_ente_soggetto_cod,
			    vdres.ruolo_ente_soggetto_desc,
				vts.codice_fiscale,
				vts.nome,
				vts.cognome,
				vts.data_nascita
			from
				vigil_r_ente_soggetto vres
			join vigil_d_ruolo_ente_soggetto vdres on
				vres.ruolo_ente_soggetto_id = vdres.ruolo_ente_soggetto_id
			join vigil_t_soggetto vts on
				vts.soggetto_id = vres.soggetto_id
			   where
			    vres.ente_id = :enteId
			      """ + SqlStatementCommon.AND + " vres.data_cancellazione is null order by validita_fine desc ";

	public static final String SELECT_BY_LISTA_ENTE_ID_FILTER_RUOLO = """
			and  vres.ruolo_ente_soggetto_id = :ruoloEnteSoggettoId
			      """;
	public static final String INVALIDA_BY_ENTE_SOGGETTO = """
			update
			 vigil_r_ente_soggetto
			set
			 validita_fine = now(),
			 data_modifica  = now(),
			 utente_modifica  = :utenteModifica
			where
			 ente_id = :enteId
			 and soggetto_id = :soggettoId
			 and  data_cancellazione is null AND now() BETWEEN validita_inizio AND coalesce(validita_fine, now())
			   """;
	public static String SELECT_BY_ENTE_SOGGETTO = """
			select
			 *
			from
			 vigil_r_ente_soggetto vres
			where
			 vres.ente_id = :enteId
			 and vres.soggetto_id = :soggettoId
			   """ + SqlStatementCommon.AND + VALIDO;

	public static final String SELECT_RUOLO_ALL = """
			select * from vigil_d_ruolo_ente_soggetto vdres where
			  vdres.data_cancellazione is null
			        AND now() BETWEEN vdres.validita_inizio AND coalesce(vdres.validita_fine, now())
			""";
	public static final String INSERT_INVALIDA_W_PK = """
			insert
			 into
			 vigilsan.vigil_r_ente_soggetto
			(ente_sogg_id,
			 ente_id,
			 soggetto_id,
			 ruolo_ente_soggetto_id,
			 validita_inizio,
			 validita_fine,
			 data_creazione,
			 data_modifica,
			 data_cancellazione,
			 utente_creazione,
			 utente_modifica,
			 utente_cancellazione)
			values(:enteSoggId,
			:enteId,
			:soggettoId,
			:ruoloEnteSoggettoId,
			 coalesce(:validitaInizio, now()),
			 now(),
			 now(),
			 now(),
			 null,
			 :utenteCreazione,
			 :utenteCreazione,
			 null)
			"""
;
	private SqlStatementEnteSoggetto() {
		throw new IllegalStateException("Utility class");
	}
}
