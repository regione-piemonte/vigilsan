/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.util.query;

public class SqlStatementApplicativo {
 

 public static final String SELECT = "select vda.* from vigil_d_applicativo vda";
 public static final String UPDATE = "update vigil_t_ente vda";
 public static final String PK_ID_EQUALS = "vda.applicativo_id = :applicativoId";
 public static final String COD_EQUALS = "vda.applicativo_cod = :applicativoCod";
 public static final String VALIDO = "vda.data_cancellazione is null " +
         "AND now() BETWEEN vda.validita_inizio AND coalesce(vda.validita_fine, now()) ";

   private SqlStatementApplicativo() {
      throw new IllegalStateException("Utility class");
    }
}
