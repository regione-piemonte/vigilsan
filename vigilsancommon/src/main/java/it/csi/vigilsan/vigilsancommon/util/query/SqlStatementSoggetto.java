/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.util.query;

public class SqlStatementSoggetto {
 

  public static final String PH_PK = "soggettoId";
 public static final String SELECT = "select vts.* from vigil_t_soggetto vts";
 public static final String UPDATE = "update vigil_t_soggetto vts";
 public static final String PK_EQUALS = "vts.soggetto_id = :soggettoId";
 public static final String VALIDO = "vts.data_cancellazione is null " +
         "AND now() BETWEEN vts.validita_inizio AND coalesce(vts.validita_fine, now()) ";

 public static final String UPDATE_CAMPI =   
 "codice_fiscale = :codiceFiscale, " +
 "data_nascita = :dataNascita, " +
 "cognome = :cognome, " +
 "nome = :nome, " +
 "presente_configuratore = :presenteConfiguratore, " +
 "validita_inizio = :validitaInizio, " +
 "validita_fine = :validitaFine, " +
 "data_modifica = now(), " +
 "utente_modifica = :utenteModifica ";
 
 public static final String INSERT = 
   """
insert 
 into 
 vigil_t_soggetto (codice_fiscale, 
 data_nascita, 
 cognome, 
 nome, 
 presente_configuratore, 
 validita_inizio, 
 validita_fine, 
 data_creazione, 
 data_modifica, 
 data_cancellazione, 
 utente_creazione, 
 utente_modifica, 
 utente_cancellazione) 
values(:codiceFiscale, 
:dataNascita,
:cognome, 
:nome, 
:presenteConfiguratore, 
coalesce(:validitaInizio, now()), 
:validitaFine, 
now(), 
now(), 
null, 
:utenteCreazione, 
:utenteCreazione, 
null)
     """;
 
 public static final String INSERT_W_PK = 
   """
insert 
 into 
 vigil_t_soggetto (soggetto_id, codice_fiscale, 
 data_nascita, 
 cognome, 
 nome, 
 presente_configuratore, 
 validita_inizio, 
 validita_fine, 
 data_creazione, 
 data_modifica, 
 data_cancellazione, 
 utente_creazione, 
 utente_modifica, 
 utente_cancellazione) 
values(:soggettoId, :codiceFiscale, 
:dataNascita,
:cognome, 
:nome, 
:presenteConfiguratore, 
coalesce(:validitaInizio, now()), 
:validitaFine, 
now(), 
now(), 
null, 
:utenteCreazione, 
:utenteCreazione, 
null)
""";
 
public static final String NEXTVAL_PK_ID = 
 "select nextval('vigil_t_soggetto_soggetto_id_seq'::regclass)";
 



public static final String CF_EQUALS = "vts.codice_fiscale = :codiceFiscale";
public static final String SELECT_SOGGETTO_LISTA = """
  WITH
   ss AS (
     SELECT
       ss.str_sogg_id,
       ss.struttura_id,
       ss.soggetto_id,
       s.*,
       rs.ruolo_struttura_soggetto_cod,
       rs.ruolo_struttura_soggetto_desc
     FROM vigil_r_struttura_soggetto ss
     JOIN vigil_d_ruolo_struttura_soggetto    rs ON rs.ruolo_struttura_soggetto_id =
ss.ruolo_struttura_soggetto_id AND rs.ruolo_struttura_soggetto_cod = 'OSPITE'
     JOIN vigil_t_soggetto           s  ON s.soggetto_id = ss.soggetto_id
     WHERE ss.data_cancellazione IS NULL
       AND ss.struttura_id =:strutturaId
       %s
   ),
   om AS (
     SELECT
       om.str_sogg_id,
       om.data_movimento,
       om.ospite_movimento_tipo_id,
       omt.ospite_movimento_tipo_cod,
       omt.ospite_movimento_tipo_desc,
       omt.ospite_stato_id,
       os.ospite_stato_cod,
       os.ospite_stato_desc,
       os.ospite_stato_ord,
       os.flg_presenza,
       vdsc.struttura_categoria_desc,
       os.flg_posto
     FROM vigil_t_ospite_movimento      om
     JOIN vigil_d_ospite_movimento_tipo omt ON
omt.ospite_movimento_tipo_id = om.ospite_movimento_tipo_id
     JOIN vigil_d_ospite_stato          os  ON os.ospite_stato_id =
omt.ospite_stato_id
	 LEFT JOIN vigil_d_struttura_categoria  vdsc  ON vdsc.struttura_categoria_id =
om.struttura_categoria_id
     WHERE om.str_sogg_id IN (SELECT str_sogg_id FROM ss)
     AND om.data_cancellazione IS NULL
   )
SELECT
   ss.*,
   dpi.data_primo_ingresso,
   CASE when dpi.data_primo_ingresso is null or
   duu.data_ultima_uscita is null then null 
   when dpi.data_primo_ingresso > duu.data_ultima_uscita then null
   else 
   duu.data_ultima_uscita end as data_ultima_uscita,
   dus.data_movimento,
   dus.struttura_categoria_desc,
   dus.ospite_stato_id,
   dus.ospite_stato_cod,
   dus.ospite_stato_desc,
   dus.ospite_stato_ord,
   COUNT(*) OVER() AS total_count
FROM ss
LEFT JOIN (
 SELECT DISTINCT ON (str_sogg_id) str_sogg_id, data_movimento
data_primo_ingresso
   FROM om WHERE flg_presenza AND flg_posto
   ORDER BY str_sogg_id, data_movimento
) dpi ON dpi.str_sogg_id = ss.str_sogg_id
LEFT JOIN (
   SELECT DISTINCT ON (str_sogg_id) str_sogg_id, data_movimento
data_ultima_uscita
   FROM om WHERE NOT flg_presenza AND NOT flg_posto
   ORDER BY str_sogg_id, data_movimento DESC
) duu ON duu.str_sogg_id = ss.str_sogg_id
LEFT JOIN (
   SELECT DISTINCT ON (str_sogg_id) str_sogg_id, data_movimento, struttura_categoria_desc,
   ospite_stato_id, ospite_stato_cod, ospite_stato_desc, ospite_stato_ord
   FROM om
   ORDER BY str_sogg_id, data_movimento DESC
) dus ON dus.str_sogg_id = ss.str_sogg_id
""";

public static final String SELECT_SOGGETTO_LISTA_FILTER = """
    (s.codice_fiscale ILIKE '%'||:filter||'%'
    OR s.cognome        ILIKE '%'||:filter||'%'
    OR s.nome           ILIKE '%'||:filter||'%'
    )
""";
public static final String SELECT_SOGGETTO_OSPITE_STATO_ID_EQUALS = "dus.ospite_stato_id = :ospiteStatoId ";
public static final String SELECT_SOGGETTO_DATA_PRIMO_INGRESSO_DA = "dpi.data_primo_ingresso >= :dataPrimoIngressoDa ";
public static final String SELECT_SOGGETTO_DATA_PRIMO_INGRESSO_A = "dpi.data_primo_ingresso <= :dataPrimoIngressoA ";
public static final String SELECT_SOGGETTO_DATA_ULTIMA_USCITA_DA = "duu.data_ultima_uscita >= :dataUltimaUscitaDa ";
public static final String SELECT_SOGGETTO_DATA_ULTIMA_USCITA_A = "duu.data_ultima_uscita <= :dataUltimaUscitaA ";

   private SqlStatementSoggetto() {
      throw new IllegalStateException("Utility class");
    }
}
