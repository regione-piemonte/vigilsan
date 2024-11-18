/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaDettaglioClreqInterface;
import it.csi.vigilsan.vigilsanpratiche.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;

public class PraticaDettaglioClreqMapper<T extends PraticaDettaglioClreqInterface> extends GenericTableMapper
		implements RowMapper<T> {

	private Class<T> clazz; // Tipo generico T

	// Costruttore che richiede il parametro di tipo Class<T>
	public PraticaDettaglioClreqMapper(Class<T> clazz) {
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
		res.setPraticaDetClreqId(rs.getObject("pratica_det_clreq_id", Integer.class));
		res.setPraticaDetId(rs.getObject("pratica_det_id", Integer.class));
		res.setClreqId(rs.getObject("clreq_id", Integer.class));
		res.setClreqEsitoId(rs.getObject("clreq_esito_id", Integer.class));
		res.setModuloCompilatoId(rs.getObject("modulo_compilato_id", Integer.class));
		res.setNote(rs.getString("note"));
		generalMapRow(rs, res);
		return res;
	}

}
