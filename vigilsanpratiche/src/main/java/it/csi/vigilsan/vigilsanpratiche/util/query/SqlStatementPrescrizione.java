/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.util.query;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

public class SqlStatementPrescrizione {

 public static final String PH_PK = "prescrizioneId";
 public static final String SELECT = "select vtp.* from vigil_t_prescrizione vtp ";
 public static final String UPDATE = "update vigil_t_prescrizione vtp";
 public static final String PK_EQUALS = "vtp.prescrizione_id = :prescrizioneId";
 public static final String VALIDO = " vtp.data_cancellazione is null "
   + "AND now() BETWEEN vtp.validita_inizio AND coalesce(vtp.validita_fine, now()) ";

 public static final String UPDATE_CAMPI = """
     prescrizione_tipo_id = :prescrizioneTipoId,
     pratica_id = :praticaId,
     dataora_apertura = :dataoraApertura,
     dataora_chiusura = :dataoraChiusura,
     prescrizione_numero = :prescrizioneNumero,
     validita_inizio = :validitaInizio,
     validita_fine = :validitaFine,
     data_modifica = now(),
     utente_modifica = :utenteModifica
   """;

 private static final String INSERT = """
insert
  into
  vigilsan.vigil_t_prescrizione
(%s
  pratica_id,
  prescrizione_tipo_id,
  dataora_apertura,
  dataora_chiusura,
  prescrizione_numero,
  validita_inizio,
  validita_fine,
  data_creazione,
  data_modifica,
  data_cancellazione,
  utente_creazione,
  utente_modifica,
  utente_cancellazione)
values(%s
:praticaId,
:prescrizioneTipoId,
:dataoraApertura,
:dataoraChiusura,
:prescrizioneNumero,
 coalesce(:validitaInizio, now()),
 :validitaFine,
 now(),
 now(),
 null,
 :utenteCreazione,
 :utenteCreazione,
 null)
""";
 
    
public static final String SELECT_PRATICHE_LISTA_PRESCRIZIONE = """
select
  z.*, vdps.prescrizione_stato_id, vdps.prescrizione_stato_cod, vdps.prescrizione_stato_desc, 
  vdps.prescrizione_stato_iniziale, vdps.prescrizione_stato_finale,
  NULL::text azione_desc,
  NULL::INTEGER modulo_id,
  NULL::INTEGER modulo_compilato_id
from
  vigil_t_prescrizione z
left join (
  select
    distinct on
    (vtpd.prescrizione_id) vtpd.prescrizione_id,
    vtpd.prescrizione_stato_id
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
    vtpd.prescrizione_id,
    vtpd.dataora_azione desc
         ) st on st.prescrizione_id = z.prescrizione_id
    left join vigil_d_prescrizione_stato vdps on vdps.prescrizione_stato_id = st.prescrizione_stato_id
where
    z.pratica_id = :praticaId and
  z.data_cancellazione is null
  and z.validita_fine is null
""";
 
 public static final String INSERT_W_PK = String.format(INSERT, "prescrizione_id,", ":" + PH_PK + ",");
 public static final String INSERT_GENERIC = String.format(INSERT, "", "");

 public static final String NEXTVAL_PK_ID = "select nextval('vigil_t_prescrizione_prescrizione_id_seq'::regclass)";
 public static final String UPDATE_GENERIC = UPDATE + SqlStatementCommon.SET + UPDATE_CAMPI
   + SqlStatementCommon.WHERE + PK_EQUALS;
 public static final String DELETE = UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE + PK_EQUALS;
 public static final String SELECT_ALL = SELECT + SqlStatementCommon.WHERE + VALIDO;
 public static final String SELECT_BY_ID = SELECT + SqlStatementCommon.WHERE + PK_EQUALS;

 
  
public static final String SELECT_PRESCRIZIONI_BY_LISTA_PRESCRIZIONI_ID = """
select
  vtp.*,
  pu.prescrizione_stato_id,
  pu.prescrizione_stato_cod,
  pu.prescrizione_stato_desc,
  pu.prescrizione_stato_iniziale,
  pu.prescrizione_stato_finale,
  pu.azione_desc,
  pp.modulo_id,
  pp.modulo_compilato_id
from
  vigil_t_prescrizione vtp
left join (
  select
    distinct on
    (prescrizione_id) prescrizione_id,
    vdps.prescrizione_stato_id,
    vdps.prescrizione_stato_cod,
    vdps.prescrizione_stato_desc,
    vdps.prescrizione_stato_iniziale,
    vdps.prescrizione_stato_finale,
    vda.azione_desc
  from
    vigil_t_pratica_dettaglio vtpd
  join vigil_d_prescrizione_stato vdps on
    vdps.prescrizione_stato_id = vtpd.prescrizione_stato_id
  join vigil_d_azione vda on
    vda.azione_id = vtpd.azione_id
  where
    vtpd.pratica_id = :praticaId
    and vtpd.data_cancellazione is null
    and vtpd.prescrizione_id is not null
  order by
    vtpd.prescrizione_id,
    vtpd.dataora_azione desc

) pu on
  pu.prescrizione_id = vtp.prescrizione_id
left join (
  select
    distinct on
    (vtpd.prescrizione_id) vtpd.prescrizione_id,
    vtpd.modulo_compilato_id,
    vrpw.modulo_id
  from
    vigil_t_pratica_dettaglio vtpd
  join vigil_t_pratica vtp on
    vtp.pratica_id = vtpd.pratica_id
  join vigil_t_prescrizione vtpe on
    vtpe.prescrizione_id = vtpd.prescrizione_id
  left join vigil_r_prescrizione_workflow vrpw on
    vrpw.pratica_tipo_id = vtp.pratica_tipo_id
    and vrpw.prescrizione_tipo_id = vtpe.prescrizione_tipo_id
    and vrpw.azione_id = vtpd.azione_id
  where
    vtpd.pratica_id = :praticaId
    and vtpd.data_cancellazione is null
  order by
    vtpd.prescrizione_id,
    vtpd.dataora_azione

) pp on
  pp.prescrizione_id = vtp.prescrizione_id
where
  vtp.pratica_id = :praticaId
  and vtp.data_cancellazione is null
""";

public static String SELECT_PRESCRIZIONI_BY_LISTA_PRESCRIZIONI_ID_FILTER_DATE = SqlStatementCommon.AND + " vtp.dataora_apertura <= :filterDate ";
public static final String SELECT_ALL_BY_PRATICA_ID = """
             SELECT p.prescrizione_id,
              p.pratica_id,
              p.prescrizione_tipo_id,
              NULL::TIMESTAMP as dataora_apertura,
              NULL::TIMESTAMP as dataora_chiusura,
              p.prescrizione_numero,
              p.validita_inizio,
              p.validita_fine,
              p.data_creazione,
              p.data_modifica,
              p.data_cancellazione,
              p.utente_creazione,
              p.utente_modifica,
              p.utente_cancellazione,
               NULL::INTEGER stato_id, 
               'D' update_status
             FROM vigil_t_prescrizione p
             WHERE p.pratica_id = :praticaId
               AND p.data_cancellazione IS NULL
               AND p.validita_fine IS NULL
    """;
 
 
 private SqlStatementPrescrizione() {
  throw new IllegalStateException("Utility class");
 }
}
