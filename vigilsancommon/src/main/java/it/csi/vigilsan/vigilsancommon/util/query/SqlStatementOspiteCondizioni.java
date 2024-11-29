/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.util.query;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

public class SqlStatementOspiteCondizioni {

	public static final String PH_PK = "ospiteCondizioniId";
	public static final String SELECT = "select * from vigil_d_ospite_condizioni vdoc ";
	public static final String UPDATE = "update vigil_d_ospite_condizioni vdoc ";
	public static final String PK_EQUALS = "vdoc.ospite_condizioni_id = :ospiteCondizioniId ";
	public static final String VALIDO = " vdoc.data_cancellazione is null  "
			+ " and now() between vdoc.validita_inizio and coalesce(vdoc.validita_fine, now()) ";

	public static final String UPDATE_CAMPI = """
			  ospite_condizioni_cod = :ospiteCondizioniCod,
			  ospite_condizioni_desc = :ospiteCondizioniDesc,
			  ospite_condizioni_ord = :ospiteCondizioniOrd,
			  ospite_condizioni_hint = :ospiteCondizioniHint,
			  validita_inizio = :validitaInizio,
			  validita_fine = :validitaFine,
			  data_modifica = now(),
			  utente_modifica = :utenteModifica
			""";

	private static final String INSERT = """
			insert
			 into
			 vigilsan.vigil_d_ospite_condizioni
			( %s
			 ospite_condizioni_cod,
			 ospite_condizioni_desc,
			 ospite_condizioni_ord,
			 ospite_condizioni_hint,
			 validita_inizio,
			 validita_fine,
			 data_creazione,
			 data_modifica,
			 data_cancellazione,
			 utente_creazione,
			 utente_modifica,
			 utente_cancellazione)
			values( %s
			:ospiteCondizioniCod,
			:ospiteCondizioniDesc,
			:ospiteCondizioniOrd,
			:ospiteCondizioniHint,
			 coalesce(:validitaInizio, now()),
			 :validitaFine,
			 now(),
			 now(),
			 null,
			 :utenteCreazione,
			 :utenteCreazione,
			 null)
			 """;
	public static final String INSERT_W_PK = String.format(INSERT, "ospite_condizioni_id,", ":" + PH_PK + ",");
	public static final String INSERT_GENERIC = String.format(INSERT, "", "");

	public static final String NEXTVAL_PK_ID = "select nextval('vigil_d_ospite_condizioni_ospite_condizioni_id_seq'::regclass)";
	public static final String UPDATE_GENERIC = UPDATE + SqlStatementCommon.SET + UPDATE_CAMPI
			+ SqlStatementCommon.WHERE + PK_EQUALS;
	public static final String DELETE = UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE + PK_EQUALS;
	public static final String SELECT_ALL = SELECT + SqlStatementCommon.WHERE + VALIDO;
	public static final String SELECT_BY_ID = SELECT + SqlStatementCommon.WHERE + PK_EQUALS;

	private SqlStatementOspiteCondizioni() {
		throw new IllegalStateException("Utility class");
	}
}
