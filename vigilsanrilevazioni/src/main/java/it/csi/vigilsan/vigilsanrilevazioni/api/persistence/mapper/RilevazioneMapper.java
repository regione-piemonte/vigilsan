/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanrilevazioni.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.model.rilevazioneInterface;
import it.csi.vigilsan.vigilsanrilevazioni.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.util.DateUtils;

public class RilevazioneMapper<T extends rilevazioneInterface> extends GenericTableMapper implements RowMapper<T> {

	private Class<T> clazz; // Tipo generico T

	// Costruttore che richiede il parametro di tipo Class<T>
	public RilevazioneMapper(Class<T> clazz) {
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

		res.setRilevazioneId(rs.getObject("rilevazione_id", Integer.class));
		res.setStrutturaId(rs.getObject("struttura_id", Integer.class));
		res.setStrCatId(rs.getObject("str_cat_id", Integer.class));
		res.setModuloCompilatoId(rs.getObject("modulo_compilato_id", Integer.class));
		res.setModuloConfigId(rs.getObject("modulo_config_id", Integer.class));
		res.setDataoraRilevazione(DateUtils.dateFromSqlTimestamp(rs.getTimestamp("dataora_rilevazione")));
		generalMapRow(rs, res);
		return res;
	}

}
