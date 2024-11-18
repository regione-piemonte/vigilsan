/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanrilevazioni.util.query;

public class SqlStatementModuloConfig {
 

  public static final String SELECT_DISTICNT_NOT_DELETED = """
    select distinct  VDMC.modulo_config_cod  from vigil_d_modulo_config vdmc 
    where VDMC.data_cancellazione is null 
    and vdmc.ril_validita_frequenza %s
""";
   private SqlStatementModuloConfig() {
      throw new IllegalStateException("Utility class");
    }
}
