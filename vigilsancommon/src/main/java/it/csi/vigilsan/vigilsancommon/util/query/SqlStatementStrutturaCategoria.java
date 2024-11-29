/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.util.query;

public class SqlStatementStrutturaCategoria {

 public static final String PH_PK = "strutturaCategoriaId";
 public static final String SELECT = "select vdsc.* from vigil_d_struttura_categoria vdsc ";
 public static final String UPDATE = "update vigil_d_struttura_categoria vdsc";
 public static final String PK_EQUALS = "vdsc.struttura_categoria_id = :strutturaCategoriaId";
 public static final String VALIDO = "vdsc.data_cancellazione is null "
   + "AND now() BETWEEN vdsc.validita_inizio AND coalesce(vdsc.validita_fine, now()) ";

 public static final String UPDATE_CAMPI = """
   struttura_categoria_cod = :strutturaCategoriaCod,
   struttura_categoria_desc = :strutturaCategoriaDesc,
   validita_inizio = :validitaInizio,
   validita_fine = :validitaFine,
   data_modifica = now(),
   utente_modifica = :utenteModifica
      """;

 public static final String INSERT = """
   insert
    into
    vigil_d_struttura_categoria (struttura_categoria_cod,
    struttura_categoria_desc,
    validita_inizio,
    validita_fine,
    data_creazione,
    data_modifica,
    data_cancellazione,
    utente_creazione,
    utente_modifica,
    utente_cancellazione)
   values(:strutturaCategoriaCod,
   :strutturaCategoriaDesc,
   coalesce(:validitaInizio, now()),
   :validitaFine,
   now(),
   now(),
   null,
   :utenteCreazione,
   :utenteCreazione,
   null)
        """;

 public static final String INSERT_W_PK = """
   insert
    into
    vigil_d_struttura_categoria (struttura_categoria_id, struttura_categoria_cod,
    struttura_categoria_desc,
    validita_inizio,
    validita_fine,
    data_creazione,
    data_modifica,
    data_cancellazione,
    utente_creazione,
    utente_modifica,
    utente_cancellazione)
   values(:strutturaCategoriaId, :strutturaCategoriaCod,
   :strutturaCategoriaDesc,
   coalesce(:validitaInizio, now()),
   :validitaFine,
   now(),
   now(),
   null,
   :utenteCreazione,
   :utenteCreazione,
   null)
        """;

 public static final String NEXTVAL_PK_ID = "select nextval('vigil_d_struttura_categoria_struttura_categoria_id_seq'::regclass) ";

 public static final String SELECT_BY_STRUTTRA_ID = """
Select
 vdsc.*
   from
 vigil_d_struttura_categoria vdsc
   join vigil_r_struttura_categoria vrsc on
 vrsc.struttura_categoria_id = vdsc.struttura_categoria_id
 and vrsc.struttura_id = :strutturaId
 and vrsc.data_cancellazione is null
 and now() between vrsc.validita_inizio and coalesce(vrsc.validita_fine, now())
 where
 vdsc.data_cancellazione is null
 and now() between vdsc.validita_inizio and coalesce(vdsc.validita_fine, now())
""";
 
 public static final String SELECT_BY_STR_CAT_ID = """
select 
 vdsc.*
   from
 vigil_d_struttura_categoria vdsc
 join vigil_r_struttura_categoria vrsc on vrsc.struttura_categoria_id = vdsc.struttura_categoria_id and 
vrsc.str_cat_id = :strCatId
""";

 private SqlStatementStrutturaCategoria() {
  throw new IllegalStateException("Utility class");
 }
}
