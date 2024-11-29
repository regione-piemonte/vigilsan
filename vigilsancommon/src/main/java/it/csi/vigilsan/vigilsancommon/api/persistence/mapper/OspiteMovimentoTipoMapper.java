/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsancommon.api.persistence.model.OspiteMovimentoTipoInterface;
import it.csi.vigilsan.vigilsancommon.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;

public class OspiteMovimentoTipoMapper<T extends OspiteMovimentoTipoInterface> extends GenericTableMapper
		implements RowMapper<T> {

	private Class<T> clazz; // Tipo generico T

	// Costruttore che richiede il parametro di tipo Class<T>
	public OspiteMovimentoTipoMapper(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public T mapRow(ResultSet rs, int rowNum) throws SQLException {

		T res;
		try {
			res = clazz.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.ERRORE_CREAZIONE_OGGETTO_QUERY);
		}
		res.setOspiteMovimentoTipoId(rs.getObject("ospite_movimento_tipo_id", Integer.class));
		res.setOspiteMovimentoTipoCod(rs.getString("ospite_movimento_tipo_cod"));
		res.setOspiteMovimentoTipoDesc(rs.getString("ospite_movimento_tipo_desc"));
		res.setOspiteMovimentoTipoOrd(rs.getString("ospite_movimento_tipo_ord"));
		res.setOspiteMovimentoTipoHint(rs.getString("ospite_movimento_tipo_hint"));
		res.setOspiteStatoId(rs.getObject("ospite_stato_id", Integer.class));

		generalMapRow(rs, res);
		return res;
	}

}
