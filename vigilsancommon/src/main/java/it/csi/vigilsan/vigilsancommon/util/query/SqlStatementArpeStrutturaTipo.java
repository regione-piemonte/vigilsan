/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.util.query;

public class SqlStatementArpeStrutturaTipo {
 

  public static final String PH_PK = "arpeStrutturaTipoId";
 public static final String SELECT = "select * from vigil_d_arpe_struttura_tipo vdast ";
 public static final String UPDATE = "update vigil_d_arpe_struttura_tipo vdast";
 public static final String PK_EQUALS = "vdast.arpe_struttura_tipo_id = :arpeStrutturaTipoId";
 public static final String VALIDO = "vdast.data_cancellazione is null " +
         "AND now() BETWEEN vdast.validita_inizio AND coalesce(vdast.validita_fine, now()) ";

 
 public static final String UPDATE_CAMPI =   
   "arpe_struttura_tipo_cod = :arpeStrutturaTipoCod, " +
   "arpe_struttura_tipo_desc = :arpeStrutturaTipoDesc, " +
   "validita_inizio = :validitaInizio, " +
   "validita_fine = :validitaFine, " +
   "data_modifica = now(), " +
   "utente_modifica = :utenteModifica ";
 
 
 public static final String INSERT = 
   """
insert
 into
 vigil_d_arpe_struttura_tipo (arpe_struttura_tipo_cod,
 arpe_struttura_tipo_desc,
 validita_inizio,
 validita_fine,
 data_creazione,
 data_modifica,
 data_cancellazione,
 utente_creazione,
 utente_modifica,
 utente_cancellazione)
values(:arpeStrutturaTipoCod,
:arpeStrutturaTipoDesc,
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
 vigil_d_arpe_struttura_tipo (arpe_struttura_tipo_id, arpe_struttura_tipo_cod,
 arpe_struttura_tipo_desc,
 validita_inizio,
 validita_fine,
 data_creazione,
 data_modifica,
 data_cancellazione,
 utente_creazione,
 utente_modifica,
 utente_cancellazione)
values(:arpeStrutturaTipoId, :arpeStrutturaTipoCod,
:arpeStrutturaTipoDesc,
coalesce(:validitaInizio, now()), 
:validitaFine, 
now(), 
now(), 
null, 
:utenteCreazione, 
:utenteCreazione, 
null) 
     """;
 

// vigil_d_arpe_struttura_tipo
public static final String SELECT_STRUTTURA_TIPO_BY_ARPE_STRUTTURA_TIPO_COD = """
select * 
from vigil_d_arpe_struttura_tipo vdast 
where vdast.arpe_struttura_tipo_cod = :cod_arpe 
and vdast.data_cancellazione is null 
and now() between vdast.validita_inizio and coalesce(vdast.validita_fine, now())
""";  

public static final String NEXTVAL_PK_ID = 
"select nextval('vigil_d_arpe_struttura_tipo_arpe_struttura_tipo_id_seq'::regclass) ";


   private SqlStatementArpeStrutturaTipo() {
      throw new IllegalStateException("Utility class");
    }
}
