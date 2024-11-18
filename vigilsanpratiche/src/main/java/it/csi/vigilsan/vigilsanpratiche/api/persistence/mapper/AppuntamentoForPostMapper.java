/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelAppuntamento;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.AppuntamentoForPost;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.AppuntamentoInterface;
import it.csi.vigilsan.vigilsanpratiche.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.util.DateUtils;

public class AppuntamentoForPostMapper extends AppuntamentoMapper<AppuntamentoForPost> {

    public AppuntamentoForPostMapper() {
        super(AppuntamentoForPost.class);
    }
	@Override
	public AppuntamentoForPost mapRow(ResultSet rs, int rowNum) throws SQLException {

		AppuntamentoForPost res = super.mapRow(rs, rowNum);
		res.setUpdateStatus(rs.getString("update_status"));
		res.setStatoId(rs.getObject("stato_id", Integer.class));
		
		return res;

	}

}
