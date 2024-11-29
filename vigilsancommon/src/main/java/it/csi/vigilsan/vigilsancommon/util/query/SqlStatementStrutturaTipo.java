/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.util.query;

public class SqlStatementStrutturaTipo {
 

  public static final String PH_PK = "strutturaTipoId";
 public static final String SELECT = "select vdst.* from vigil_d_struttura_tipo vdst ";
 public static final String UPDATE = "update vigil_d_struttura_tipo vdst";
 public static final String PK_EQUALS = "vdst.struttura_tipo_id = :strutturaTipoId";
 public static final String VALIDO = "vdst.data_cancellazione is null " +
         "AND now() BETWEEN vdst.validita_inizio AND coalesce(vdst.validita_fine, now()) ";

 public static final String UPDATE_CAMPI =   
   "struttura_tipo_cod = :srutturaTipoCod, " +
   "struttura_tipo_desc = :srutturaTipoDesc, " +
   "validita_inizio = :validitaInizio, " +
   "validita_fine = :validitaFine, " +
   "data_modifica = now(), " +
   "utente_modifica = :utenteModifica ";
 
 public static final String INSERT = 
   """
insert
 into
 vigil_d_struttura_tipo (struttura_tipo_cod,
 struttura_tipo_desc,
 validita_inizio,
 validita_fine,
 data_creazione,
 data_modifica,
 data_cancellazione,
 utente_creazione,
 utente_modifica,
 utente_cancellazione)
values(:strutturaTipoCod,
:strutturaTipoDesc,
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
 vigil_d_struttura_tipo (struttura_tipo_id, struttura_tipo_cod,
 struttura_tipo_desc,
 validita_inizio,
 validita_fine,
 data_creazione,
 data_modifica,
 data_cancellazione,
 utente_creazione,
 utente_modifica,
 utente_cancellazione)
values(:strutturaTipoid, :strutturaTipoCod,
:strutturaTipoDesc,
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
 "select nextval('vigil_d_struttura_tipo_struttura_tipo_id_seq'::regclass) ";
 
public static final String SELECT_BY_STRUTTRA_ID = null;

// vigil_d_arpe_assistenza_tipo
public static final String SELECT_STRUTTURA_TIPO_BY_ARPE_STRUTTURA_TIPO_COD = """
select * "+
" from vigil_d_arpe_assistenza_tipo vdaat "+
" where vdaat.arpe_assistenza_tipo_cod = :cod_arpe "+
" and vdaat.data_cancellazione is null  "+
" and now() between vdaat.validita_inizio and coalesce(vdaat.validita_fine, now())
""";  

public static final String NEXTVAL_PK_D_ARPE_STRUTTURA_TIPO_ID = 
"select nextval('vigil_d_struttura_tipo_struttura_tipo_id_seq'::regclass) ";


   private SqlStatementStrutturaTipo() {
      throw new IllegalStateException("Utility class");
    }
}
