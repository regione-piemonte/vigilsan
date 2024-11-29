/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.util.query;

public class SqlStatementStrutturaSoggetto {

	public static final String PH_PK = "strSoggId";
	public static final String SELECT = "select * from vigil_r_struttura_soggetto vrss ";
	public static final String UPDATE = "update vigil_r_struttura_soggetto vrss ";
	public static final String PK_EQUALS = "vrss.str_sogg_id = :strSoggId ";
	public static final String VALIDO = " vrss.data_cancellazione is null  "
			+ " and now() between vrss.validita_inizio and coalesce(vrss.validita_fine, now()) ";

	public static final String UPDATE_CAMPI = """
			struttura_id = :strutturaId,
			soggetto_id = :soggettoId,
			ruolo_struttura_soggetto_id = :ruoloStrutturaSoggettoId,
			validita_inizio = :validitaInizio,
			validita_fine = :validitaFine,
			data_modifica = now(),
			utente_modifica = :utenteModifica
			    """;

	public static final String INSERT = """
			   insert
			 into
			 vigilsan.vigil_r_struttura_soggetto
			(
			 struttura_id,
			 soggetto_id,
			 ruolo_struttura_soggetto_id,
			 validita_inizio,
			 validita_fine,
			 data_creazione,
			 data_modifica,
			 data_cancellazione,
			 utente_creazione,
			 utente_modifica,
			 utente_cancellazione)
			values(
			:strutturaId,
			:soggettoId,
			:ruoloStrutturaSoggettoId,
			coalesce(:validitaInizio, now()),
			:validitaFine,
			now(),
			now(),
			null,
			:utenteCreazione,
			:utenteCreazione,
			null)
			  """;

	public static final String INSERT_W_PK = """
			   insert
			 into
			 vigilsan.vigil_r_struttura_soggetto
			(str_sogg_id,
			 struttura_id,
			 soggetto_id,
			 ruolo_struttura_soggetto_id,
			 validita_inizio,
			 validita_fine,
			 data_creazione,
			 data_modifica,
			 data_cancellazione,
			 utente_creazione,
			 utente_modifica,
			 utente_cancellazione)
			values(:strSoggId,
			:strutturaId,
			:soggettoId,
			:ruoloStrutturaSoggettoId,
			coalesce(:validitaInizio, now()),
			:validitaFine,
			now(),
			now(),
			null,
			:utenteCreazione,
			:utenteCreazione,
			null)
			   """;

	public static final String NEXTVAL_PK_ID = "select nextval('vigil_r_struttura_soggetto_str_sogg_id_seq'::regclass) ";

	public static final String SELECT_BY_STRUTTURA_ID_SOGGETTO_ID = """
			select vrss.* from vigil_r_struttura_soggetto vrss
			where
			vrss.soggetto_id = :soggettoId
			and vrss.struttura_id = :strutturaId
			""";

	private SqlStatementStrutturaSoggetto() {
		throw new IllegalStateException("Utility class");
	}
}
