/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.util.query;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

public class SqlStatementInvioNotifica {

 public static final String PH_PK = "invioNotificaId";
 public static final String SELECT = "select vtin.* from vigil_t_invio_notifica vtin ";
 public static final String UPDATE = "update vigil_t_invio_notifica vtin";
 public static final String PK_EQUALS = "vtin.invio_notifica_id = :invioNotificaId";
 public static final String VALIDO = " vtin.data_cancellazione is null "
   + "AND now() BETWEEN vtin.validita_inizio AND coalesce(vtin.validita_fine, now()) ";

 public static final String UPDATE_CAMPI = """
notifica_id = :notificaId,
cf_destinatario = :cfDestinatario,
dataora_invio = :dataoraInvio,
esito_invio = :esitoInvio,
errore_invio = :erroreInvio,
request_content = :requestContent,
response_content = :responseContent,
response_result = :requestResult,
     validita_inizio = :validitaInizio,
     validita_fine = :validitaFine,
     data_modifica = now(),
     utente_modifica = :utenteModifica
   """;

 private static final String INSERT = """
insert
 into
 vigilsan.vigil_t_invio_notifica
(%s
 notifica_id,
 cf_destinatario,
 dataora_invio,
 esito_invio,
 errore_invio,
 request_content,
 response_content,
 response_result,
 validita_inizio,
 validita_fine,
 data_creazione,
 data_modifica,
 data_cancellazione,
 utente_creazione,
 utente_modifica,
 utente_cancellazione)
values(%s
:notificaId,
:cfDestinatario,
:dataoraInvio,
:esitoInvio,
:erroreInvio,
:requestContent,
:responseContent,
:responseResult,
    coalesce(:validitaInizio, now()),
    :validitaFine,
    now(),
    now(),
    null,
    :utenteCreazione,
    :utenteCreazione,
    null)
""";

 public static final String INSERT_W_PK = String.format(INSERT, "invio_notifica_id,", ":" + PH_PK + ",");
 public static final String INSERT_GENERIC = String.format(INSERT, "", "");

 public static final String NEXTVAL_PK_ID = "select nextval('vigil_t_invio_notifica_invio_notifica_id_seq'::regclass)";
 public static final String UPDATE_GENERIC = UPDATE + SqlStatementCommon.SET + UPDATE_CAMPI
   + SqlStatementCommon.WHERE + PK_EQUALS;

 public static final String INVALIDA_BY_ID = UPDATE + SqlStatementCommon.INVALIDA + SqlStatementCommon.WHERE
   + PK_EQUALS;
 public static final String DELETE = UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE + PK_EQUALS;
 public static final String SELECT_ALL = SELECT + SqlStatementCommon.WHERE + VALIDO;
 public static final String SELECT_BY_ID = SELECT + SqlStatementCommon.WHERE + PK_EQUALS;
 
 private SqlStatementInvioNotifica() {
  throw new IllegalStateException("Utility class");
 }
}
