/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.util.query;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

public class SqlStatementModulo {
 public static final String SELECT_MODULO =
   "select " +
   " vdm.modulo_id, " +
   " vdm.modulo_cod, " +
   " vdm.modulo_desc, " +
   " vdm.modulo_ord, " +
   " vdm.modulo_id_padre " +
   "from " +
   " vigil_d_modulo vdm " +
   "where " +
   " vdm.modulo_id = :moduloId ";
 public static final String SELECT_SEZIONI_MODULO =
   """
   select
   modulo_id,
   modulo_cod,
   modulo_desc,
   modulo_ord,
   modulo_id_padre
  from
   fnc_modulo(:moduloId::INTEGER)
  where
   modulo_id_padre is not null
   """;

 private static final String SELECT_MODULO_VOCE = """
   SELECT
    vdmv.modulo_id,
    vdmv.modulo_voce_id,
    vdmv.modulo_voce_cod,
    %s
    vdmv.modulo_voce_ord,
    vdmv.modulo_voce_hint,
    vdmv.modulo_voce_id_padre,
    vdmv.modulo_voce_tipo_dati,
    vdmv.modulo_voce_unita_misura,
    vdmv.modulo_lista_id,
    vdmv.file_gruppo_id,
    vdmv.modulo_voce_desc_check,
    vtmcd.valore,
    vtmcd.flg_check,
    vtmcd.note
    FROM vigil_d_modulo_voce vdmv
    left JOIN vigil_t_modulo_compilato_dettaglio vtmcd on
     vdmv.modulo_lista_id is null
    and vdmv.file_gruppo_id is null
    and vtmcd.modulo_voce_id = vdmv.modulo_voce_id
    and vtmcd.modulo_compilato_id = :moduloCompilatoId
    WHERE vdmv.modulo_id = :moduloId
    %s
    ORDER BY vdmv.modulo_voce_id_padre nulls first,vdmv.modulo_voce_ord
   """;
 public static final String SELECT_MODULO_VOCE_INTERA = String.format(SELECT_MODULO_VOCE, "vdmv.modulo_voce_desc, ",
   "");
 public static final String SELECT_MODULO_VOCE_RIDOTTA = String.format(SELECT_MODULO_VOCE,
   "vdmv.modulo_voce_desc_lista as modulo_voce_desc,",
   " and vdmv.modulo_voce_desc_lista" + SqlStatementCommon.IS_NOT_NULL);

  public static final String SELECT_MODULO_LISTA_BY_MODULO_LISTA_ID =
"""
select * from vigil_d_modulo_lista vdml 
where vdml.modulo_lista_id = :moduloListaId
""";
 
 public static final String SELECT_MODULO_LISTA_VALORE_BY_MODULO_LISTA_ID =
"""
select vdmlv.modulo_lista_valore_id,  
 vdmlv.modulo_lista_valore_cod,  
 vdmlv.modulo_lista_valore_desc,  
 vdmlv.modulo_lista_valore_ord,  
 vdmlv.modulo_lista_valore_hint, 
 (vtmcd.modulo_lista_valore_id is not null) as valore
from vigil_d_modulo_voce vdmv 
join vigil_d_modulo_lista_valore vdmlv on vdmlv.modulo_lista_id =vdmv.modulo_lista_id 
left join vigil_t_modulo_compilato_dettaglio vtmcd on 
vtmcd.modulo_compilato_id = :moduloCompilatoId 
and vtmcd.modulo_voce_id = vdmv.modulo_voce_id 
and vtmcd.modulo_lista_valore_id = vdmlv.modulo_lista_valore_id 
and vtmcd.data_cancellazione is null 
where 
vdmv.modulo_voce_id = :moduloVoceId  
order by vdmlv.modulo_lista_valore_ord
""";

 public static final String SELECT_MODULO_VOCE_REGOLA_BY_ID = """
SELECT
  vdmvr.modulo_voce_regola_ord,
  vdmvr.modulo_voce_regola_tipo,
  REPLACE(REPLACE(LOWER(vdmvr.modulo_voce_regola_exec),'today$',TO_CHAR(NOW(), 'yyyy-mm-dd$')),'now$',TO_CHAR(NOW(), 'yyyy-mm-ddThh24:mi:ss$')) modulo_voce_regola_exec,
  vdmvr.modulo_voce_regola_errore
FROM vigil_d_modulo_voce_regola vdmvr
WHERE modulo_voce_id = :moduloVoceRegolaId
ORDER BY vdmvr.modulo_voce_regola_tipo, vdmvr.modulo_voce_regola_ord
""";
 public static final String SELECT_MODULO_REGOLA_BY_MODULO_ID = """
select
 *
from
 vigil_d_modulo_regola vdmr
where
 vdmr.modulo_id = :moduloId
 and 
 vdmr.data_cancellazione is null
 and now() between vdmr.validita_inizio and coalesce(vdmr.validita_fine,
 now()) 
 order by vdmr.modulo_regola_ord
""";

 
 
 private SqlStatementModulo() {
  super();
 }

 }
