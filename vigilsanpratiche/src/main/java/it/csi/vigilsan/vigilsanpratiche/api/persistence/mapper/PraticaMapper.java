/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaInterface;
import it.csi.vigilsan.vigilsanpratiche.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.util.DateUtils;

public class PraticaMapper<T extends PraticaInterface> extends GenericTableMapper implements RowMapper<T> {

    private Class<T> clazz; // Tipo generico T

    // Costruttore che richiede il parametro di tipo Class<T>
    public PraticaMapper(Class<T> clazz) {
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
		res.setPraticaId(rs.getObject("pratica_id", Integer.class));
		res.setStrutturaId(rs.getObject("struttura_id", Integer.class));
		res.setEnteId(rs.getObject("ente_id", Integer.class));
		res.setPraticaTipoId(rs.getObject("pratica_tipo_id", Integer.class));
		res.setDataoraApertura(DateUtils.dateFromSqlTimestamp(rs.getTimestamp("dataora_apertura")));
		res.setDataoraChiusura(DateUtils.dateFromSqlTimestamp(rs.getTimestamp("dataora_chiusura")));
		res.setPraticaTipoId(rs.getObject("pratica_tipo_id", Integer.class));
		res.setPraticaNumero(rs.getString("pratica_numero"));
		generalMapRow(rs, res);
		return res;
	}

}
