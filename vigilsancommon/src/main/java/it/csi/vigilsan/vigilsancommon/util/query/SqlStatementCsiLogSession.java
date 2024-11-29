/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.util.query;

public class SqlStatementCsiLogSession {

 public static final String PH_PK = "sessionId";
 public static final String SELECT = "select cls.* from csi_log_session cls ";
 public static final String UPDATE = "update csi_log_session cls ";
 public static final String PK_EQUALS = "cls.session_id = :sessionId";

 public static final String UPDATE_CAMPI = """
    session_login = :sessionLogin,
    session_logout = :sessionLogout,
    session_expires = :sessionExpires,
    ip_address = :ipAddress,
    cf_utente = :cfUtente,
    flg_accesso_pua = :flgAccessoPua,
    soggetto_id = :soggettoId,
    ruolo_id = :ruoloId,
    applicativo_id = :applicativoId,
    profilo_id = :profiloId,
    struttura_id = :strutturaId,
    ente_id = :enteId
     """;

 public static final String INSERT = """
     insert
      into
      csi_log_session (session_login,
      session_logout,
      session_expires,
      ip_address,
      cf_utente,
      flg_accesso_pua,
      soggetto_id,
      ruolo_id,
      applicativo_id,
      profilo_id,
      struttura_id,
      ente_id)
     values(now(),
     :sessionLogout,
     :sessionExpires,
     :ipAddress,
     :cfUtente,
     :flgAccessoPua,
     :soggettoId,
     :ruoloId,
     :applicativoId,
     :profiloId,
     :strutturaId,
     :enteId)
     """;

 public static final String INSERT_W_PK =

   """
     insert
      into
      csi_log_session (session_id, session_login,
      session_logout,
      session_expires,
      ip_address,
      cf_utente,
      flg_accesso_pua,
      soggetto_id,
      ruolo_id,
      applicativo_id,
      profilo_id,
      struttura_id,
      ente_id)
     values(:sessionId, now(),
     :sessionLogout,
     :sessionExpires,
     :ipAddress,
     :cfUtente,
     :flgAccessoPua,
     :soggettoId,
     :ruoloId,
     :applicativoId,
     :profiloId,
     :strutturaId,
     :enteId)
     """;

 public static final String NEXTVAL_PK_ID = "select nextval('csi_log_session_session_id_seq'::regclass) as session_id ";

 public static final String LOGOUT = "update " + " csi_log_session " + "set " + " session_logout = now() " + "where "
   + " session_id = :sessionId ";

 private SqlStatementCsiLogSession() {
  throw new IllegalStateException("Utility class");
 }
}
