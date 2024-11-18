/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.util.query;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

public class SqlStatementPraticaScadenzaAnnullata {

 public static final String PH_PK = "praticaScadenzaAnnullataId";
 public static final String SELECT = "select vrpsa.* from vigil_r_pratica_scadenza_annullata vrpsa ";
 public static final String UPDATE = "update vigil_r_pratica_scadenza_annullata vrpsa";
 public static final String PK_EQUALS = "vrpsa.pratica_scadenza_annullata_id = :praticaScadenzaAnnullataId";
 public static final String VALIDO = " vrpsa.data_cancellazione is null "
   + "AND now() BETWEEN vrpsa.validita_inizio AND coalesce(vrpsa.validita_fine, now()) ";

 public static final String UPDATE_CAMPI = """
     pratica_tipo_id = :praticaTipoId,
     azione_id = :azioneId,
     azione_id_scadenza = :azioneIdScadenza,
     validita_inizio = :validitaInizio,
     validita_fine = :validitaFine,
     data_modifica = now(),
     utente_modifica = :utenteModifica
   """;

 private static final String INSERT = """
insert
 into
 vigilsan.vigil_r_pratica_scadenza_annullata
(%s
 pratica_tipo_id,
 azione_id,
 azione_id_scadenza,
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
 coalesce(:validitaInizio, now()),
 :validitaFine,
 now(),
 now(),
 null,
 :utenteCreazione,
 :utenteCreazione,
 null)
""";
 public static final String INSERT_W_PK = String.format(INSERT, "pratica_scadenza_annullata_id,", ":" + PH_PK + ",");
 public static final String INSERT_GENERIC = String.format(INSERT, "", "");

 public static final String NEXTVAL_PK_ID = "select nextval('vigil_r_pratica_scadenza_annu_pratica_scadenza_annullata_id_seq'::regclass)";
 public static final String UPDATE_GENERIC = UPDATE + SqlStatementCommon.SET + UPDATE_CAMPI
   + SqlStatementCommon.WHERE + PK_EQUALS;
 public static final String DELETE = UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE + PK_EQUALS;
 public static final String SELECT_ALL = SELECT + SqlStatementCommon.WHERE + VALIDO;
 public static final String SELECT_BY_ID = SELECT + SqlStatementCommon.WHERE + PK_EQUALS;
 public static final String SELECT_BY_PRATICA_TIPO_ID = SELECT_ALL + SqlStatementCommon.AND + " pratica_tipo_id = :praticaTipoId ";

 private SqlStatementPraticaScadenzaAnnullata() {
  throw new IllegalStateException("Utility class");
 }
}
