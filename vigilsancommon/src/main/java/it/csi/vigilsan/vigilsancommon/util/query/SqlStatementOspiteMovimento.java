/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.util.query;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.query.SqlStatementCommon;

public class SqlStatementOspiteMovimento {
 

  public static final String PH_PK = "ospiteMovimentoId";
 public static final String SELECT = "select * from vigil_t_ospite_movimento vtom ";
 public static final String UPDATE = "update vigil_t_ospite_movimento vtom ";
 public static final String PK_EQUALS = "vtom.ospite_movimento_id = :ospiteMovimentoId ";
 public static final String VALIDO = " vtom.data_cancellazione is null  "
   + " and now() between vtom.validita_inizio and coalesce(vtom.validita_fine, now()) ";

 
 public static final String UPDATE_CAMPI =   
   """
 data_movimento = :dataMovimento,
 str_sogg_id = :strSoggId,
 ospite_movimento_tipo_id = :ospiteMovimentoTipoId,
 ospite_condizioni_id = :ospiteCondizioniId,
 struttura_id_od = :strutturaIdOd,
 note = :note,
 struttura_categoria_id = :strutturaCategoriaId,
 validita_inizio = :validitaInizio,
 validita_fine = :validitaFine, 
 data_modifica = now(), 
 utente_modifica = :utenteModifica
     """;
 
 public static final String INSERT = 
   """
insert
 into
 vigilsan.vigil_t_ospite_movimento
(
 data_movimento,
 str_sogg_id,
 ospite_movimento_tipo_id,
 ospite_condizioni_id,
 struttura_id_od,
 note,
 struttura_categoria_id,
 validita_inizio,
 validita_fine,
 data_creazione,
 data_modifica,
 data_cancellazione,
 utente_creazione,
 utente_modifica,
 utente_cancellazione)
values(
:dataMovimento,
:strSoggId,
:ospiteMovimentoTipoId,
:ospiteCondizioniId,
:strutturaIdOd,
:note,
:strutturaCategoriaId,
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
 vigilsan.vigil_t_ospite_movimento
(ospite_movimento_id,
 data_movimento,
 str_sogg_id,
 ospite_movimento_tipo_id,
 ospite_condizioni_id,
 struttura_id_od,
 note,
 struttura_categoria_id,
 validita_inizio,
 validita_fine,
 data_creazione,
 data_modifica,
 data_cancellazione,
 utente_creazione,
 utente_modifica,
 utente_cancellazione)
values(:ospiteMovimentoId,
:dataMovimento,
:strSoggId,
:ospiteMovimentoTipoId,
:ospiteCondizioniId,
:strutturaIdOd,
:note,
:strutturaCategoriaId,
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
"select nextval('vigil_t_ospite_movimento_ospite_movimento_id_seq'::regclass)";

public static final String SELECT_BY_SOGGETTO_ID_STRUTTURA_ID_VALIDI = """
select
 vtom.*
from
 vigil_t_ospite_movimento vtom
join vigil_r_struttura_soggetto vrss on
 vrss.str_sogg_id = vtom.str_sogg_id
 and vrss.struttura_id = :strutturaId
 and vrss.soggetto_id = :soggettoId
 and vrss.data_cancellazione is null
where
 vtom.data_cancellazione is null
 order by data_movimento desc 
""";

private static final String SELECT_BY_STR_SOGG_ID = """
select
 vtom.*
from
 vigil_t_ospite_movimento vtom
where
 vtom.str_sogg_id = :strSoggId
""";
public static final String SELECT_BY_STR_SOGG_ID_VALIDO = SqlStatementOspiteMovimento.SELECT_BY_STR_SOGG_ID
		+ SqlStatementCommon.AND + SqlStatementOspiteMovimento.VALIDO;

   private SqlStatementOspiteMovimento() {
      throw new IllegalStateException("Utility class");
    }
}
