/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.util.query;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

public class SqlStatementNotifica {

 public static final String PH_PK = "notificaId";
 public static final String SELECT = "select vtn.* from vigil_t_notifica vtn ";
 public static final String UPDATE = "update vigil_t_notifica vtn";
 public static final String PK_EQUALS = "vtn.notifica_id = :notificaId";
 public static final String VALIDO = " vtn.data_cancellazione is null "
   + "AND now() BETWEEN vtn.validita_inizio AND coalesce(vtn.validita_fine, now()) ";

 public static final String UPDATE_CAMPI = """
 profilo_id = :profiloId,
 ente_id = :enteId,
 struttura_id = :strutturaId,
 soggetto_id = :soggettoId,
 title = :title,
 body_text_short = :bodyTextShort,
 body_text_long = :bodyTextLong,
 body_html = :bodyHtml,
     validita_inizio = :validitaInizio,
     validita_fine = :validitaFine,
     data_modifica = now(),
     utente_modifica = :utenteModifica
   """;

 private static final String INSERT = """
insert
 into
 vigilsan.vigil_t_notifica
(%s
 profilo_id,
 ente_id,
 struttura_id,
 soggetto_id,
 title,
 body_text_short,
 body_text_long,
 body_html,
 validita_inizio,
 validita_fine,
 data_creazione,
 data_modifica,
 data_cancellazione,
 utente_creazione,
 utente_modifica,
 utente_cancellazione)
values(%s
:profiloId,
:enteId,
:strutturaId,
:soggettoId,
:title,
:bodyTextShort,
:bodyTextLong,
:bodyHtml,
    coalesce(:validitaInizio, now()),
    :validitaFine,
    now(),
    now(),
    null,
    :utenteCreazione,
    :utenteCreazione,
    null)
""";

 public static final String INSERT_W_PK = String.format(INSERT, "notifica_id,", ":" + PH_PK + ",");
 public static final String INSERT_GENERIC = String.format(INSERT, "", "");

 public static final String NEXTVAL_PK_ID = "select nextval('vigil_t_notifica_notifica_id_seq'::regclass)";
 public static final String UPDATE_GENERIC = UPDATE + SqlStatementCommon.SET + UPDATE_CAMPI
   + SqlStatementCommon.WHERE + PK_EQUALS;

 public static final String INVALIDA_BY_ID = UPDATE + SqlStatementCommon.INVALIDA + SqlStatementCommon.WHERE
   + PK_EQUALS;
 public static final String DELETE = UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE + PK_EQUALS;
 public static final String SELECT_ALL = SELECT + SqlStatementCommon.WHERE + VALIDO;
 public static final String SELECT_BY_ID = SELECT + SqlStatementCommon.WHERE + PK_EQUALS;

 public static final String SELECT_NOTIFICHE_DA_INVIARE = """
select
	vtn.*
from
	vigil_t_notifica vtn
where
	vtn.data_cancellazione is null
	and not exists (select 1 from vigil_t_invio_notifica  vtin 
	where vtin.notifica_id = vtn.notifica_id and vtin.data_cancellazione is null 
	and (vtin.esito_invio is null or vtin.esito_invio)
	)
 		""";
 private SqlStatementNotifica() {
  throw new IllegalStateException("Utility class");
 }
}
