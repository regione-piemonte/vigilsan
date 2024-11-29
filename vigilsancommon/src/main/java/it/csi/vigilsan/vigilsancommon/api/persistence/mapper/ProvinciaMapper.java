/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsancommon.api.persistence.model.ProvinciaInterface;
import it.csi.vigilsan.vigilsancommon.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;

public class ProvinciaMapper<T extends ProvinciaInterface> extends GenericTableMapper implements RowMapper<T> {

    private Class<T> clazz; // Tipo generico T

    // Costruttore che richiede il parametro di tipo Class<T>
    public ProvinciaMapper(Class<T> clazz) {
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
		res.setProvinciaId(rs.getObject("provincia_id", Integer.class));
		res.setProvinciaCod(rs.getString("provincia_cod"));
		res.setProvinciaSigla(rs.getString("provincia_sigla"));
		res.setProvinciaDesc(rs.getString("provincia_desc"));
		res.setRegioneId(rs.getObject("regione_id", Integer.class));
		generalMapRow(rs, res);
		return res;
	}

}
