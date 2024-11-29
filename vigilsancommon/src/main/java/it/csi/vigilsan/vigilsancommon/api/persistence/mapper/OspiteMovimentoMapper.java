/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsancommon.api.persistence.model.OspiteMovimentoInterface;
import it.csi.vigilsan.vigilsancommon.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.util.DateUtils;

public class OspiteMovimentoMapper<T extends OspiteMovimentoInterface> extends GenericTableMapper
		implements RowMapper<T> {

	private Class<T> clazz; // Tipo generico T

	// Costruttore che richiede il parametro di tipo Class<T>
	public OspiteMovimentoMapper(Class<T> clazz) {
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
		res.setOspiteMovimentoId(rs.getObject("ospite_movimento_id", Integer.class));
		res.setDataMovimento(DateUtils.dateFromSqlTimestamp(rs.getTimestamp("data_movimento")));
		res.setStrSoggId(rs.getObject("str_sogg_id", Integer.class));
		res.setOspiteMovimentoTipoId(rs.getObject("ospite_movimento_tipo_id", Integer.class));
		res.setOspiteCondizioniId(rs.getObject("ospite_condizioni_id", Integer.class));
		res.setStrutturaIdOd(rs.getObject("struttura_id_od", Integer.class));
		res.setNote(rs.getString("note"));
		res.setStrutturaCategoriaId(rs.getObject("struttura_categoria_id", Integer.class));

		generalMapRow(rs, res);
		return res;
	}

}
