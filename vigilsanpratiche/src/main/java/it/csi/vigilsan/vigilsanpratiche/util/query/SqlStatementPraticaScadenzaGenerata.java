/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.util.query;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

public class SqlStatementPraticaScadenzaGenerata {

 public static final String PH_PK = "praticaScadenzaGenerataId";
 public static final String SELECT = "select vrpsg.* from vigil_r_pratica_scadenza_generata vrpsg ";
 public static final String UPDATE = "update vigil_r_pratica_scadenza_generata vrpsg";
 public static final String PK_EQUALS = "vrpsg.pratica_scadenza_generata_id = :praticaScadenzaGenerataId";
 public static final String VALIDO = " vrpsg.data_cancellazione is null "
   + "AND now() BETWEEN vrpsg.validita_inizio AND coalesce(vrpsg.validita_fine, now()) ";

 public static final String UPDATE_CAMPI = """
     pratica_tipo_id = :praticaTipoId,
     azione_id = :azioneId,
     azione_id_scadenza = :azioneIdScadenza,
     giorni_scadenza = :giorniScadenza,
     profilo_id = :profiloId,
     prescrizione_tipo_id = :prescrizioneTipoId,
     appuntamento_tipo_id = :appuntamentoTipoId,
     validita_inizio = :validitaInizio,
     validita_fine = :validitaFine,
     data_modifica = now(),
     utente_modifica = :utenteModifica
   """;

 private static final String INSERT = """
insert
	into
	vigilsan.vigil_r_pratica_scadenza_generata
( %s
	pratica_tipo_id,
	azione_id,
	azione_id_scadenza,
	giorni_scadenza,
	profilo_id,
	prescrizione_tipo_id,
	appuntamento_tipo_id,
	validita_inizio,
	validita_fine,
	data_creazione,
	data_modifica,
	data_cancellazione,
	utente_creazione,
	utente_modifica,
	utente_cancellazione)
values(%s
:praticaTipoId,
:azioneId,
:azioneIdScadenza,
:giorniScadenza,
:profiloId,
:prescrizioneTipoId,
:appuntamentoTipoId,
 coalesce(:validitaInizio, now()),
 :validitaFine,
 now(),
 now(),
 null,
 :utenteCreazione,
 :utenteCreazione,
 null)
""";
 public static final String INSERT_W_PK = String.format(INSERT, "pratica_scadenza_generata_id,", ":" + PH_PK + ",");
 public static final String INSERT_GENERIC = String.format(INSERT, "", "");

 public static final String NEXTVAL_PK_ID = "select nextval('vigil_r_pratica_scadenza_gener_pratica_scadenza_generata_id_seq'::regclass)";
 public static final String UPDATE_GENERIC = UPDATE + SqlStatementCommon.SET + UPDATE_CAMPI
   + SqlStatementCommon.WHERE + PK_EQUALS;
 public static final String DELETE = UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE + PK_EQUALS;
 public static final String SELECT_ALL = SELECT + SqlStatementCommon.WHERE + VALIDO;
 public static final String SELECT_BY_ID = SELECT + SqlStatementCommon.WHERE + PK_EQUALS;
 public static final String SELECT_BY_PRATICA_TIPO_ID = SELECT_ALL + SqlStatementCommon.AND + " pratica_tipo_id = :praticaTipoId ";

 private SqlStatementPraticaScadenzaGenerata() {
  throw new IllegalStateException("Utility class");
 }
}
