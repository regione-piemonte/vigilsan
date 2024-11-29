/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsancommon.api.persistence.model.OspiteMovimentoForModifica;
import it.csi.vigilsan.vigilsanutil.generic.util.DateUtils;

public class  OspiteMovimentoForModificaMapper implements RowMapper<OspiteMovimentoForModifica> {
	
	@Override
	public OspiteMovimentoForModifica mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new OspiteMovimentoForModifica();
		res.setFlgValido(rs.getBoolean("flg_valido"));
		res.setSeq(rs.getObject("seq", Long.class));
			res.setDataMovimento(DateUtils.dateFromSqlTimestamp(rs.getTimestamp("data_movimento")));
			res.setOspiteMovimentoTipoId(rs.getObject("ospite_movimento_tipo_id", Integer.class));
			res.setOspiteMovimentoTipoDesc(rs.getString("ospite_movimento_tipo_desc"));
			res.setOspiteMovimentoTipoOrd(rs.getString("ospite_movimento_tipo_ord"));
			res.setOspiteStatoId(rs.getObject("ospite_stato_id", Integer.class));
		return  res;
	}

}
