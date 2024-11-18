/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.util.query;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

public class SqlStatementPraticaDettaglioClreq {

	public static final String PH_PK = "praticaDetClreqId";
	public static final String SELECT = "select vrpdc.* from vigil_r_pratica_dettaglio_clreq vrpdc ";
	public static final String UPDATE = "update vigil_r_pratica_dettaglio_clreq vrpdc";
	public static final String PK_EQUALS = "vrpdc.pratica_det_clreq_id = :praticaDetClreqId";
	public static final String VALIDO = " vrpdc.data_cancellazione is null "
			+ "AND now() BETWEEN vrpdc.validita_inizio AND coalesce(vrpdc.validita_fine, now()) ";

	public static final String UPDATE_CAMPI = """
			  pratica_det_id = :praticaDetId,
			  clreq_id = :clreqId,
			  clreq_esito_id = :clreqEsitoId,
			  modulo_compilato_id = :moduloCompilatoId,
			  note = :note,
			  validita_inizio = :validitaInizio,
			  validita_fine = :validitaFine,
			  data_modifica = now(),
			  utente_modifica = :utenteModifica
			""";

	private static final String INSERT = """
			insert
			 into
			 vigilsan.vigil_r_pratica_dettaglio_clreq
			(%s
			 pratica_det_id,
			 clreq_id,
			 clreq_esito_id,
			 modulo_compilato_id,
			 note,
			 validita_inizio,
			 validita_fine,
			 data_creazione,
			 data_modifica,
			 data_cancellazione,
			 utente_creazione,
			 utente_modifica,
			 utente_cancellazione)
			values(%s
			:praticaDetId,
			:clreqId,
			:clreqEsitoId,
			:moduloCompilatoId,
			:note,
			    coalesce(:validitaInizio, now()),
			    :validitaFine,
			    now(),
			    now(),
			    null,
			    :utenteCreazione,
			    :utenteCreazione,
			    null)
			   """;

	public static final String INSERT_W_PK = String.format(INSERT, "pratica_det_clreq_id,", ":" + PH_PK + ",");
	public static final String INSERT_GENERIC = String.format(INSERT, "", "");

	public static final String NEXTVAL_PK_ID = "select nextval('vigil_r_pratica_dettaglio_clreq_pratica_det_clreq_id_seq'::regclass)";
	public static final String UPDATE_GENERIC = UPDATE + SqlStatementCommon.SET + UPDATE_CAMPI
			+ SqlStatementCommon.WHERE + PK_EQUALS;
	public static final String DELETE = UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE + PK_EQUALS;
	public static final String SELECT_ALL = SELECT + SqlStatementCommon.WHERE + VALIDO;
	public static final String SELECT_BY_ID = SELECT + SqlStatementCommon.WHERE + PK_EQUALS;
	public static final String SELECT_BY_ID_VALIDO = SELECT + SqlStatementCommon.WHERE + PK_EQUALS
			+ SqlStatementCommon.AND + VALIDO;
	public static final String SELECT_BY_PRATICA_DET_ID = """
			SELECT fnc.*, vtpd.pratica_det_id, vrpdc.pratica_det_clreq_id, vrpdc.clreq_esito_id, vrpdc.modulo_compilato_id, vrpdc.note,
			vdce.clreq_esito_cod , vdce.clreq_esito_desc
			FROM vigil_t_pratica_dettaglio vtpd
			JOIN vigil_t_pratica           vtp   ON vtp.pratica_id = vtpd.pratica_id
			JOIN vigil_d_pratica_tipo      vdpt  ON vdpt.pratica_tipo_id = vtp.pratica_tipo_id
			JOIN fnc_checklist_req(vdpt.clreq_id) fnc ON TRUE
			JOIN vigil_r_pratica_clreq     vrpc  ON vrpc.clreq_id = fnc.clreq_id AND vrpc.pratica_id = vtpd.pratica_id AND vrpc.appuntamento_id IS NULL AND vrpc.prescrizione_id IS NULL AND vrpc.data_cancellazione IS NULL
			JOIN vigil_r_pratica_clreq     vrpcx ON vrpcx.clreq_id = fnc.clreq_id AND vrpcx.pratica_id = vtpd.pratica_id AND COALESCE(vrpcx.appuntamento_id,-1) = COALESCE(vtpd.appuntamento_id,-1) AND COALESCE(vrpcx.prescrizione_id,-1) = COALESCE(vtpd.prescrizione_id,-1) AND vrpc.data_cancellazione IS NULL
			LEFT JOIN vigil_r_pratica_dettaglio_clreq vrpdc ON vrpdc.pratica_det_id = vtpd.pratica_det_id AND vrpdc.clreq_id = fnc.clreq_id AND vrpdc.data_cancellazione IS null
			left join vigil_d_clreq_esito vdce on VDCE.clreq_esito_id = VRPDC.clreq_esito_id
			WHERE vtpd.pratica_det_id = :praticaDetId
			""";
	public static final String SELECT_CLRQ_ESITO_DECODIFICA = """
			select * from vigil_d_clreq_esito vdce where
			 vdce.data_cancellazione is null AND now() BETWEEN vdce.validita_inizio AND coalesce(vdce.validita_fine, now())
			""";
	public static final String SELECT_BY_PRATICA_DET_ID_AND_CLREQ_ID_VALIDO = SELECT + SqlStatementCommon.WHERE + """
 vrpdc.pratica_det_id = :praticaDetId and vrpdc.clreq_id = :clreqId and 
""" + VALIDO;

	private SqlStatementPraticaDettaglioClreq() {
		throw new IllegalStateException("Utility class");
	}
}
