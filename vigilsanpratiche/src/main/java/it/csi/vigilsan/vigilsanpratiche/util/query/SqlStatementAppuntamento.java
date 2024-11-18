/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.util.query;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

public class SqlStatementAppuntamento {

 public static final String PH_PK = "appuntamentoId";
 public static final String SELECT = "select vta.* from vigil_t_appuntamento  vta ";
 public static final String UPDATE = "update vigil_t_appuntamento  vta";
 public static final String PK_EQUALS = "vta.appuntamento_id = :appuntamentoId";
 public static final String VALIDO = " vta.data_cancellazione is null "
   + "AND now() BETWEEN vta.validita_inizio AND coalesce(vta.validita_fine, now()) ";

 public static final String UPDATE_CAMPI = """
     appuntamento_tipo_id = :appuntamentoTipoId,
     pratica_id = :praticaId,
     dataora_inizio = :dataoraInizio,
     dataora_fine = :dataoraFine,
     dataora_apertura = :dataoraApertura,
     dataora_chiusura = :dataoraChiusura,
     appuntamento_numero = :appuntamentoNumero,
     validita_inizio = :validitaInizio,
     validita_fine = :validitaFine,
     data_modifica = now(),
     utente_modifica = :utenteModifica
   """;
 
 private static final String INSERT = """
insert
  into
  vigilsan.vigil_t_appuntamento
(%s
  appuntamento_tipo_id,
  pratica_id,
  dataora_inizio,
  dataora_fine,
    dataora_apertura,
    dataora_chiusura,
    appuntamento_numero,
  validita_inizio,
  validita_fine,
  data_creazione,
  data_modifica,
  data_cancellazione,
  utente_creazione,
  utente_modifica,
  utente_cancellazione)
values(%s
:appuntamentoTipoId,
:praticaId,
:dataoraInizio,
:dataoraFine,
:dataoraApertura,
:dataoraChiusura,
:appuntamentoNumero,
 coalesce(:validitaInizio, now()),
 :validitaFine,
 now(),
 now(),
 null,
 :utenteCreazione,
 :utenteCreazione,
 null)
""";
 
 public static final String INSERT_W_PK = String.format(INSERT, "appuntamento_id,", ":" + PH_PK + ",");
 public static final String INSERT_GENERIC = String.format(INSERT, "", "");

 public static final String NEXTVAL_PK_ID = "select nextval('vigil_t_appuntamento_appuntamento_id_seq'::regclass)";
 public static final String UPDATE_GENERIC = UPDATE + SqlStatementCommon.SET + UPDATE_CAMPI
   + SqlStatementCommon.WHERE + PK_EQUALS;
 public static final String DELETE = UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE + PK_EQUALS;
 public static final String SELECT_ALL = SELECT + SqlStatementCommon.WHERE + VALIDO;
 public static final String SELECT_BY_ID = SELECT + SqlStatementCommon.WHERE + PK_EQUALS;

 

public static final String SELECT_PRATICHE_LISTA_APPUNTAMENTO = """ 
select
  z.*, vdas.appuntamento_stato_id, vdas.appuntamento_stato_cod, vdas.appuntamento_stato_desc, 
  vdas.appuntamento_stato_iniziale, vdas.appuntamento_stato_finale,
  null::text azione_desc,
  null::INTEGER modulo_compilato_id,
  null::INTEGER modulo_id
from 
  vigil_t_appuntamento z
left join (
  select
    distinct on
    (vtpd.appuntamento_id) vtpd.appuntamento_id,
    vtpd.appuntamento_stato_id
  from
    vigil_t_pratica_dettaglio vtpd
  join vigil_t_pratica vtp2 on
    vtp2.pratica_id = vtpd.pratica_id
  join vigil_r_azione_config vrac on
    vrac.azione_id = vtpd.azione_id
    and vrac.pratica_tipo_id = vtp2.pratica_tipo_id
    and vrac.profilo_id = :profiloId
  where
    vtpd.data_cancellazione is null
    and vtpd.validita_fine is null
  order by
    vtpd.appuntamento_id,
    vtpd.dataora_azione desc
         ) st on
  st.appuntamento_id = z.appuntamento_id
left join vigil_d_appuntamento_stato vdas 
 on
  vdas.appuntamento_stato_id = st.appuntamento_stato_id

where
  z.pratica_id = :praticaId  and 
  z.data_cancellazione is null
  and z.validita_fine is null
""";
public static final String SELECT_BY_PRATICA_ID = """
select
  vta.*,
  au.appuntamento_stato_id,
  au.appuntamento_stato_cod,
  au.appuntamento_stato_desc,
  au.appuntamento_stato_iniziale,
  au.appuntamento_stato_finale,
  au.azione_desc,
  ap.modulo_id,
  ap.modulo_compilato_id
from
  vigil_t_appuntamento vta
left join (
  select
    distinct on
    (appuntamento_id) appuntamento_id,
    vdas.appuntamento_stato_id,
    vdas.appuntamento_stato_cod,
    vdas.appuntamento_stato_desc,
    vdas.appuntamento_stato_iniziale,
    vdas.appuntamento_stato_finale,
    vda.azione_desc
  from vigil_t_pratica_dettaglio vtpd
  join vigil_d_appuntamento_stato vdas on vdas.appuntamento_stato_id = vtpd.appuntamento_stato_id
  join vigil_d_azione vda on vda.azione_id = vtpd.azione_id
  where
    vtpd.pratica_id = :praticaId and vtpd.data_cancellazione is null
    and vtpd.appuntamento_id is not null
  order by vtpd.appuntamento_id, vtpd.dataora_azione desc

) au on
  au.appuntamento_id = vta.appuntamento_id
left join (
  select
    distinct on
    (vtpd.appuntamento_id) vtpd.appuntamento_id,
    vtpd.modulo_compilato_id,
    vraw.modulo_id
  from vigil_t_pratica_dettaglio vtpd
  join vigil_t_pratica vtp on vtp.pratica_id = vtpd.pratica_id
  join vigil_t_appuntamento vta on vta.appuntamento_id = vtpd.appuntamento_id
  left join vigil_r_appuntamento_workflow vraw on vraw.pratica_tipo_id = vtp.pratica_tipo_id
    and vraw.appuntamento_tipo_id = vta.appuntamento_tipo_id
    and vraw.azione_id = vtpd.azione_id
  where
    vtpd.pratica_id = :praticaId and vtpd.data_cancellazione is null
  order by vtpd.appuntamento_id, vtpd.dataora_azione) ap on ap.appuntamento_id = vta.appuntamento_id
where
  vta.pratica_id = :praticaId
  and vta.data_cancellazione is null
  """;
 
public static String SELECT_BY_PRATICA_ID_FILTER_DATE = SqlStatementCommon.AND + " vta.dataora_apertura <= :filterDate ";
public static String SELECT_ALL_BY_PRATICA_ID ="""
  SELECT p.appuntamento_id,
  p.appuntamento_tipo_id,
  p.pratica_id,
  p.dataora_inizio,
  p.dataora_fine,
  NULL::TIMESTAMP as dataora_apertura,
  NULL::TIMESTAMP as dataora_chiusura,
  p.appuntamento_numero,
  p.validita_inizio,
  p.validita_fine,
  p.data_creazione,
  p.data_modifica,
  p.data_cancellazione,
  p.utente_creazione,
  p.utente_modifica,
  p.utente_cancellazione,
  NULL::INTEGER stato_id, 'D' update_status
    FROM vigil_t_appuntamento p
       WHERE p.pratica_id = :praticaId
       AND p.data_cancellazione IS NULL
         AND p.validita_fine IS NULL
    """;

 private SqlStatementAppuntamento() {
  throw new IllegalStateException("Utility class");
 }
}
