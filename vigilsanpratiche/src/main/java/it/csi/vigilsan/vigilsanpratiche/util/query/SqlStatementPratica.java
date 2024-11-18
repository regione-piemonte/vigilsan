/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.util.query;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

public class SqlStatementPratica {

 public static final String PH_PK = "praticaId";
 public static final String SELECT = "select vtp.* from vigil_t_pratica vtp ";
 public static final String UPDATE = "update vigil_t_pratica vtp";
 public static final String PK_EQUALS = "vtp.pratica_id = :praticaId";
 public static final String VALIDO = " vtp.data_cancellazione is null "
   + "AND now() BETWEEN vtp.validita_inizio AND coalesce(vtp.validita_fine, now()) ";

 public static final String UPDATE_CAMPI = """
     ente_id = :enteId,
     struttura_id = :strutturaId,
     pratica_tipo_id = :praticaTipoId,
     dataora_apertura = :dataoraApertura,
     dataora_chiusura = :dataoraChiusura,
     pratica_numero = :praticaNumero,
     validita_inizio = :validitaInizio,
     validita_fine = :validitaFine,
     data_modifica = now(),
     utente_modifica = :utenteModifica
   """;

 private static final String INSERT = """
insert
 into
 vigilsan.vigil_t_pratica
(%s
 ente_id,
 struttura_id,
 pratica_tipo_id,
 dataora_apertura,
 dataora_chiusura,
 pratica_numero,
 validita_inizio,
 validita_fine,
 data_creazione,
 data_modifica,
 data_cancellazione,
 utente_creazione,
 utente_modifica,
 utente_cancellazione)
values(%s
:enteId,
:strutturaId,
:praticaTipoId,
:dataoraApertura,
:dataoraChiusura,
:praticaNumero,
 coalesce(:validitaInizio, now()),
 :validitaFine,
 now(),
 now(),
 null,
 :utenteCreazione,
 :utenteCreazione,
 null)
""";
 


public static final String SELECT_PRATICHE_INSERIBILI = """
SELECT
  e.ente_id,
  s.struttura_id,
  pc.pratica_tipo_id,
  pw.azione_id AS azione_id_iniziale,
  pw.modulo_id,
  (SELECT MAX(z.dataora_chiusura)
  FROM vigil_t_pratica z
  WHERE z.ente_id = e.ente_id
    AND z.struttura_id = s.struttura_id
    AND z.pratica_tipo_id = pc.pratica_tipo_id
    AND z.data_cancellazione IS NULL
    AND z.validita_fine IS NULL
  ) dataora_chiusura,
  COUNT(*) OVER() AS total_count
FROM      vigil_t_ente                 e
JOIN      vigil_r_pratica_config       pc  ON pc.ente_tipo_id = e.ente_tipo_id AND pc.data_cancellazione IS NULL
JOIN      vigil_r_pratica_workflow     pw  ON pw.pratica_tipo_id = pc.pratica_tipo_id AND pw.pratica_stato_id_iniziale IS NULL AND pw.data_cancellazione IS NULL
JOIN      vigil_r_azione_config        ac  ON ac.azione_id = pw.azione_id and ac.pratica_tipo_id = pc.pratica_tipo_id AND ac.profilo_id = :profiloId AND ac.abilitazione = 'W' AND ac.data_cancellazione IS NULL
JOIN      vigil_t_struttura            s   ON s.struttura_tipo_id = pc.struttura_tipo_id AND s.data_cancellazione IS NULL
JOIN      vigil_r_ente_struttura       es  ON es.struttura_id = s.struttura_id AND es.ente_id = e.ente_id AND es.data_cancellazione IS NULL
JOIN      vigil_d_ruolo_ente_struttura res ON res.ruolo_ente_struttura_id = es.ruolo_ente_struttura_id AND res.ruolo_ente_struttura_cod = 'ENTE_VIGIL'
LEFT JOIN vigil_t_comune               c   ON c.comune_id = s.comune_id AND c.data_cancellazione IS NULL
WHERE e.ente_id = :enteId
  AND NOT EXISTS (
    SELECT 1
    FROM vigil_t_pratica z
    WHERE z.ente_id = e.ente_id
      AND z.struttura_id = s.struttura_id
      AND z.pratica_tipo_id = pc.pratica_tipo_id
      AND z.data_cancellazione IS NULL
      AND z.validita_fine IS NULL
""";

public static final String SELECT_PRATICHE_INSERIBILI_2 = """
  )
""";

public static final String SELECT_PRATICHE_INSERIBILI_PRATICA_TIPO_ID = """
  AND pc.pratica_tipo_id = :praticaTipoId
  """;
public static final String SELECT_PRATICHE_INSERIBILI_STRUTTURA_TIPO_ID = """
  AND pc.struttura_tipo_id = :strutturaTipoId
""";
public static final String SELECT_PRATICHE_INSERIBILI_FILTER_STRUTTURA = """
  AND (s.struttura_cod      ILIKE '%'||:filterStruttura||'%'
    OR s.struttura_cod_arpe ILIKE '%'||:filterStruttura||'%'
    OR s.struttura_desc     ILIKE '%'||:filterStruttura||'%'
    OR s.indirizzo          ILIKE '%'||:filterStruttura||'%'
    OR c.comune_desc        ILIKE '%'||:filterStruttura||'%'
  )
""";
public static final String SELECT_PRATICHE_INSERIBILI_DATA_CHIUSURA_MAX = """
AND (z.dataora_chiusura IS NULL OR z.dataora_chiusura >= :dataChiusuraMax)
""";
public static final String SELECT_PRATICHE_INSERIBILI_DATA_CHIUSURA_MAX_IS_NULL = """
AND z.dataora_chiusura IS NULL
""";
 public static final String INSERT_W_PK = String.format(INSERT, "pratica_id,", ":" + PH_PK + ",");
 public static final String INSERT_GENERIC = String.format(INSERT, "", "");

 public static final String NEXTVAL_PK_ID = "select nextval('vigil_t_pratica_pratica_id_seq'::regclass)";
 public static final String UPDATE_GENERIC = UPDATE + SqlStatementCommon.SET + UPDATE_CAMPI
   + SqlStatementCommon.WHERE + PK_EQUALS;
 public static final String DELETE = UPDATE + SqlStatementCommon.LOGIC_DELETE + SqlStatementCommon.WHERE + PK_EQUALS;
 public static final String SELECT_ALL = SELECT + SqlStatementCommon.WHERE + VALIDO;
 public static final String SELECT_BY_ID = SELECT + SqlStatementCommon.WHERE + PK_EQUALS;
 public static final String SELECT_BY_ID_VALIDO = SELECT + SqlStatementCommon.WHERE + PK_EQUALS + SqlStatementCommon.AND + VALIDO;
 
 
 
public static final String SELECT_PRATICHE_LISTA = """
select
 *, COUNT(*) OVER() AS total_count
from
 vigil_t_pratica vtp
 join (select distinct on (vtpd.pratica_Id) vtpd.pratica_Id, vtpd.pratica_stato_id
  from vigil_t_pratica_dettaglio vtpd
  join vigil_t_pratica vtp2 on vtp2.pratica_id = vtpd.pratica_id
     join vigil_r_azione_config vrac on vrac.azione_id = vtpd.azione_id
       and vrac.pratica_tipo_id = vtp2.pratica_tipo_id
       and vrac.profilo_id = :profiloId
  where vtpd.data_cancellazione is null
     and vtpd.validita_fine is null 
     and vtpd.flg_scadenza is null 
     order by vtpd.pratica_id, vtpd.dataora_azione desc
 ) st on st.pratica_id = vtp.pratica_id 
LEFT JOIN vigil_t_struttura s  ON s.struttura_id = vtp.struttura_id 
LEFT JOIN vigil_t_comune    c  ON c.comune_id = s.comune_id AND c.data_cancellazione IS NULL
where
 vtp.data_cancellazione is null
 and now() between vtp.validita_inizio and coalesce(vtp.validita_fine,
 now())
""";

public static final String SELECT_PRATICHE_LISTA_FILTER_STRUTTURA = """
 AND (s.struttura_cod      ILIKE '%'||:filterStruttura||'%'
   OR s.struttura_cod_arpe ILIKE '%'||:filterStruttura||'%'
   OR s.struttura_desc     ILIKE '%'||:filterStruttura||'%'
   OR s.indirizzo          ILIKE '%'||:filterStruttura||'%'
   OR c.comune_desc        ILIKE '%'||:filterStruttura||'%'
 )
""";
public static final String SELECT_PRATICHE_LISTA_ENTE = """
 and vtp.ente_id = :enteId 
		""";
public static final String SELECT_PRATICHE_LISTA_STRUTTURA = """
and vtp.struttura_id = :strutturaId
		""";
public static final String SELECT_PRATICHE_LISTA_PRATICA_TIPO = """
 and vtp.pratica_tipo_id = :tipoPraticaId
		""";
public static final String SELECT_PRATICHE_LISTA_PRATICA_STATO = """
and st.pratica_stato_id = :statoPraticaId
""";
public static final String SELECT_PRATICHE_LISTA_PRATICA_APERTURA_DA = """
and vtp.dataora_apertura >= :dataPraticaAperturaDa::TIMESTAMP
""";
public static final String SELECT_PRATICHE_LISTA_PRATICA_APERTURA_A = """
and vtp.dataora_apertura <= :dataPraticaAperturaA::TIMESTAMP
""";
public static final String SELECT_PRATICHE_LISTA_PRATICA_CHIUSURA_A = """
  and vtp.dataora_chiusura <= :dataPraticaChiusuraA::TIMESTAMP
""";
public static final String SELECT_PRATICHE_LISTA_PRATICA_CHIUSURA_DA = """
and vtp.dataora_chiusura >= :dataPraticaChiusuraDa::TIMESTAMP
""";

public static final String SELECT_PRATICHE_LISTA_APPUNTAMENTO = """
 and exists (select true
  from vigil_t_appuntamento z 
    left join (select distinct on (vtpd.appuntamento_id) vtpd.appuntamento_id, vtpd.appuntamento_stato_id
      from vigil_t_pratica_dettaglio vtpd
      join vigil_t_pratica vtp2 on vtp2.pratica_id = vtpd.pratica_id
         join vigil_r_azione_config vrac on vrac.azione_id = vtpd.azione_id
          and vrac.pratica_tipo_id = vtp2.pratica_tipo_id
          and vrac.profilo_id = :profiloId

      where vtpd.data_cancellazione is null
     and vtpd.validita_fine is null 
     order by vtpd.appuntamento_id, vtpd.dataora_azione desc
         ) st on st.appuntamento_id = z.appuntamento_id 
         
  where z.pratica_id = vtp.pratica_id 
  and z.data_cancellazione is null
     and z.validita_fine is null
		""";
public static final String SELECT_PRATICHE_LISTA_APPUNTAMENTO_FINE = """
 ) 
		""";
public static final String SELECT_PRATICHE_LISTA_APPUNTAMENTO_TIPO = """
and z.appuntamento_tipo_id = :tipoAppuntamentoId
		""";
public static final String SELECT_PRATICHE_LISTA_APPUNTAMENTO_INIZIO_DA = """
and z.dataora_inizio >= :dataInizioAppuntamentoDa::TIMESTAMP
		""";
public static final String SELECT_PRATICHE_LISTA_APPUNTAMENTO_INIZIO_A = """
and z.dataora_inizio <= :dataInizioAppuntamentoA::TIMESTAMP
		""";
public static final String SELECT_PRATICHE_LISTA_APPUNTAMENTO_FINE_A = """
  and z.dataora_fine <= :dataFineAppuntamentoA::TIMESTAMP
		""";
public static final String SELECT_PRATICHE_LISTA_APPUNTAMENTO_FINE_DA = """
and z.dataora_fine >= :dataFineAppuntamentoDa::TIMESTAMP
		""";
public static final String SELECT_PRATICHE_LISTA_APPUNTAMENTO_STATO = """
  and st.appuntamento_stato_id = :statoAppuntamentoId
		""";
public static final String SELECT_PRATICHE_LISTA_PRESCRIZIONE = """
 and exists (select true from vigil_t_prescrizione z 
    left join (select distinct on (vtpd.prescrizione_id) vtpd.prescrizione_id, vtpd.prescrizione_stato_id
      from vigil_t_pratica_dettaglio vtpd
      join vigil_t_pratica vtp2 on vtp2.pratica_id = vtpd.pratica_id
         join vigil_r_azione_config vrac on vrac.azione_id = vtpd.azione_id
          and vrac.pratica_tipo_id = vtp2.pratica_tipo_id
     
             and vrac.profilo_id = :profiloId

      where vtpd.data_cancellazione is null
     and vtpd.validita_fine is null 
     order by vtpd.prescrizione_id, vtpd.dataora_azione desc
         ) st on st.prescrizione_id = z.prescrizione_id 
    where
  z.pratica_id = vtp.pratica_id 
  and z.data_cancellazione is null
     and z.validita_fine is null
		""";
public static final String SELECT_PRATICHE_LISTA_PRESCRIZIONE_FINE = """
) 
""";
public static final String SELECT_PRATICHE_LISTA_PRESCRIZIONE_TIPO = """
and z.prescrizione_tipo_id = :tipoPrescrizioneId
""";
public static final String SELECT_PRATICHE_LISTA_PRESCRIZIONE_STATO = """
and st.prescrizione_stato_id = :statoPrescrizioneId
""";
public static final String SELECT_PRATICHE_LISTA_PRESCRIZIONE_APERTURA_DA= """
  and z.dataora_apertura >= :dataAperturaPrescrizioneDa::TIMESTAMP
""";
public static final String SELECT_PRATICHE_LISTA_PRESCRIZIONE_APERTURA_A= """
  and z.dataora_apertura <= :dataAperturaPrescrizioneA::TIMESTAMP
""";
public static final String SELECT_PRATICHE_LISTA_PRESCRIZIONE_CHIUSURA_DA= """
  and z.dataora_chiusura >= :dataChiusuraPrescrizioneDa::TIMESTAMP
""";
public static final String SELECT_PRATICHE_LISTA_PRESCRIZIONE_CHIUSURA_A= """
  and z.dataora_chiusura <= :dataChiusuraPrescrizioneA::TIMESTAMP
""";
public static final String SELECT_PRATICA_ESTESA_BY_ID = """
SELECT DISTINCT ON (vtpd.pratica_id) vtp.*, 
  vtpd.modulo_compilato_id, 
  vrpw.modulo_id
FROM vigil_t_pratica_dettaglio     vtpd
JOIN vigil_t_pratica               vtp  ON vtp.pratica_id = vtpd.pratica_id
LEFT JOIN vigil_r_pratica_workflow vrpw ON vrpw.pratica_tipo_id = vtp.pratica_tipo_id AND vrpw.azione_id = vtpd.azione_id
WHERE vtpd.pratica_id = :praticaId
  AND vtpd.data_cancellazione IS NULL
ORDER BY vtpd.pratica_id, vtpd.dataora_azione
""";

 private SqlStatementPratica() {
  throw new IllegalStateException("Utility class");
 }
}
