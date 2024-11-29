/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.util.query;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

public class SqlStatementStruttura {
 

 public static final String PH_PK = "strutturaId";
 public static final String SELECT_STRUTTURA = "select vts.* from vigil_t_struttura vts ";
 public static final String UPDATE_STRUTTURA = "update vigil_t_struttura vts ";
 public static final String STRUTTURA_ID_EQUALS = "vts.struttura_id = :strutturaId";
 public static final String STRUTTURA_COD_EQUALS = "vts.struttura_cod = :strutturaCod";
 public static final String STRUTTURA_COD_ARPE_EQUALS = "vts.struttura_cod_arpe = :strutturaCodArpe";
 public static final String STRUTTURA_VALIDO = "vts.data_cancellazione is null " +
         "AND now() BETWEEN vts.validita_inizio AND coalesce(vts.validita_fine, now()) ";

 public static final String UPDATE_STRUTTURA_CAMPI =   
   "struttura_cod = :strutturaCod, " +
   "struttura_cod_configuratore = :strutturaCodConfiguratore, " +
   "struttura_cod_arpe = :strutturaCodArpe, " +
   "struttura_desc = :strutturaDesc, " +
   "struttura_tipo_id = :strutturaTipoId, " +
   "comune_id = :comuneId, " +
   "indirizzo = :indirizzo, " +
   "coord_srid = :coordSrid, " +
   "coord_x = :coordX, " +
   "coord_y = :coordY, " +
   "validita_inizio = :validitaInizio, " +
   "validita_fine = :validitaFine, " +
   "data_modifica = now(), " +
   "utente_modifica = :utenteModifica ";
 
 public static final String INSERT_STRUTTURA = "insert " +
   " into " +
   " vigil_t_struttura (struttura_cod, " +
   " struttura_cod_configuratore, " +
   " struttura_cod_arpe, " +
   " struttura_desc, " +
   " struttura_tipo_id, " +
   " comune_id, " +
   " indirizzo, " +
   " cap, " +    
   " coord_srid, " +
   " coord_x, " +
   " coord_y, " +
   " struttura_natura_id, " +  
   " struttura_accreditamento_id, " +  
   " struttura_titolarita_id, " +  
   " validita_inizio, " +
   " validita_fine, " +
   " data_creazione, " +
   " data_modifica, " +
   " data_cancellazione, " +
   " utente_creazione, " +
   " utente_modifica, " +
   " utente_cancellazione) " +
   " values(:strutturaCod, " +
   " :strutturaCodConfiguratore, " +
   " :strutturaCodArpe, " +
   " :strutturaDesc, " +
   " :strutturaTipoId, " +
   " :comuneId, " +
   " :indirizzo, " +
   " :cap, " +    
   " :coordSrid, " +
   " :coordX, " +
   " :coordY, " +
   " :strutturaNaturaId, "+
   " :strutturaAccreditamentoId, "+
   " :strutturaTitolaritaId, "+
   " coalesce(:validitaInizio, now()), " +
   " :validitaFine, " +
   " now(), " +
   " now(), " +
   " null, " +
   " :utenteCreazione, " +
   " :utenteCreazione, " +
   "  null) ";
 
 public static final String UPDATE_STRUTTURA_BATCH = " UPDATE vigil_t_struttura vts SET "+
     "struttura_desc = :strutturaDesc, " +
     "struttura_tipo_id = :strutturaTipoId, " +
     "comune_id = :comuneId, " +
     "indirizzo = :indirizzo, " +
     "cap = :cap, " +     
     "coord_srid = :coordSrid, " +
     "struttura_cod_configuratore = :strutturaCodConfiguratore, " +
     "coord_x = :coordX, " +
     "coord_y = :coordY, " +
     "struttura_natura_id = :strutturaNaturaId, " +  
     "struttura_accreditamento_id = :strutturaAccreditamentoId, " +  
     "struttura_titolarita_id = :strutturaTitolaritaId, " +  
     "data_cancellazione = null, " +
     "utente_cancellazione = null, " +
     "data_modifica = now(), " +
     "utente_modifica = :utenteModifica  where " +
     "struttura_id = :strutturaId";
 
 public static final String DELETE_STRUTTURE = " update vigil_t_struttura vts set "+ 
     "data_cancellazione = :datainput " +
     "WHERE (data_creazione <= :datainput " +
     "AND data_modifica <= :datainput) " +
     "AND struttura_cod_arpe is not null " +
     "AND data_cancellazione is null"; 
 
 public static final String GET_ACTUAL_DATE = " SELECT now() AS data_attuale "; 
 
 public static final String SELECT_STRUTTURA_BY_STRUTTURA_COD_ARPE = "select * " +
 " from vigil_t_struttura vts " +
 " where vts.struttura_cod_arpe = :cod_arpe ";
 
 public static final String SELECT_RICALCOLA_STRUTTURA = "SELECT * FROM fnc_ricalcola_struttura_tc()";
 public static final String FUNCTION_RICALCOLA_STRUTTURA = "fnc_ricalcola_struttura_tc";

 public static final String SELECT_STRUTTURA_BY_STRUTTURA_COD_ARPE_VALIDA = 
        SqlStatementStruttura.SELECT_STRUTTURA_BY_STRUTTURA_COD_ARPE
   + SqlStatementCommon.AND
   + SqlStatementStruttura.STRUTTURA_VALIDO;
 
 public static final String NEXTVAL_PK_ID =  "select nextval('vigil_t_struttura_struttura_id_seq'::regclass) ";
 public static final String SELECT_PARAMETRO_BOOLEAN = """
		  select exists(select
		 1
		from
		 vigil_r_struttura_categoria vrsc
		join vigil_d_struttura_categoria vdsc on
		 vdsc.struttura_categoria_id = vrsc.struttura_categoria_id
		 and vdsc.data_cancellazione is null
		 and now() between vdsc.validita_inizio and coalesce(vdsc.validita_fine,
		 now())
		where
		 struttura_id = :strutturaId
		 and vdsc.struttura_categoria_cod = :strutturaCategoriaCod) as flag
		  """;
	public static final String SELECT_STRUTTURE_BY_ENTE_ID = """
SELECT s.*, COUNT(*) OVER() AS total_count
FROM      vigil_t_struttura s
LEFT JOIN vigil_t_comune    c  ON c.comune_id = s.comune_id AND c.data_cancellazione IS NULL
WHERE s.data_cancellazione IS NULL 
""";
 public static final String SELECT_STRUTTURE_BY_ENTE_ID_FILTER = """
  AND (s.struttura_cod      ILIKE '%'||:filter||'%'
    OR s.struttura_cod_arpe ILIKE '%'||:filter||'%'
    OR s.struttura_desc     ILIKE '%'||:filter||'%'
    OR s.indirizzo          ILIKE '%'||:filter||'%'
    OR c.comune_desc        ILIKE '%'||:filter||'%'
  )
""";

	public static final String SELECT_STRUTTURE_BY_ENTE_ID_PROFILO_FILTER = """
AND EXISTS (
SELECT 1
FROM vigil_r_ente_struttura       es
JOIN vigil_d_ruolo_ente_struttura res ON res.ruolo_ente_struttura_id = es.ruolo_ente_struttura_id AND res.ruolo_ente_struttura_cod = :ruoloEnteStrutturaCod 
WHERE es.ente_id = :enteId
AND es.struttura_id = s.struttura_id
AND es.data_cancellazione IS NULL
) 
""";
	public static final String SELECT_STRUTTURE_BY_ENTE_ID_PROFILO_STRUTTURA_ID = """
 AND s.struttura_id = :strutturaId
""";
    private SqlStatementStruttura() {
      throw new IllegalStateException("Utility class");
    }
}
