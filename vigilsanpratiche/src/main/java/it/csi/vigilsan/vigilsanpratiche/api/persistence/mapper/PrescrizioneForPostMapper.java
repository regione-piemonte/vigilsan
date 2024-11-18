/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PrescrizioneForPost;
import it.csi.vigilsan.vigilsanpratiche.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.util.DateUtils;

public class PrescrizioneForPostMapper extends PrescrizioneMapper<PrescrizioneForPost> {

    public PrescrizioneForPostMapper() {
       super(PrescrizioneForPost.class);
    }
	@Override
	public PrescrizioneForPost mapRow(ResultSet rs, int rowNum) throws SQLException {

		PrescrizioneForPost res = super.mapRow(rs, rowNum);
		res.setUpdateStatus(rs.getString("update_status"));
		res.setStatoId(rs.getObject("stato_id", Integer.class));
		return res;
	}

}
