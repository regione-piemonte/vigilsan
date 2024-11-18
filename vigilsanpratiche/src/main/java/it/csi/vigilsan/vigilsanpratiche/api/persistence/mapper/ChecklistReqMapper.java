/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.ChecklistReqInterface;
import it.csi.vigilsan.vigilsanpratiche.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;

public class ChecklistReqMapper<T extends ChecklistReqInterface	> extends GenericTableMapper implements RowMapper<T> {

    private Class<T> clazz; // Tipo generico T

    // Costruttore che richiede il parametro di tipo Class<T>
    public ChecklistReqMapper(Class<T> clazz) {
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
		res.setClreqId(rs.getObject("clreq_id", Integer.class));
		res.setClreqCod(rs.getString("clreq_cod"));
		res.setClreqDesc(rs.getString("clreq_desc"));
		res.setClreqOrd(rs.getString("clreq_ord"));
		res.setClreqHint(rs.getString("clreq_hint"));
		res.setClreqIdPadre(rs.getObject("clreq_id_padre", Integer.class));
		res.setModuloId(rs.getObject("modulo_id", Integer.class));

		generalMapRow(rs, res);
		return res;
	}

}
