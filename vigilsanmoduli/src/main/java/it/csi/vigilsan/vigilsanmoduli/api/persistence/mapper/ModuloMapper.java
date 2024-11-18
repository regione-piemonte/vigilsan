/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanmoduli.api.generated.dto.ModelModulo;
import it.csi.vigilsan.vigilsanmoduli.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;

public class ModuloMapper<T extends ModelModulo> extends GenericTableMapper implements RowMapper<T> {

    private Class<T> clazz; // Tipo generico T

    // Costruttore che richiede il parametro di tipo Class<T>
    public ModuloMapper(Class<T> clazz) {
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
		res.setModuloId(rs.getObject("modulo_id", Integer.class));
		res.setModuloCod(rs.getString("modulo_cod"));
		res.setModuloDesc(rs.getString("modulo_desc"));
		res.setModuloOrd(rs.getString("modulo_ord"));
		res.setModuloIdPadre(rs.getObject("modulo_id_padre", Integer.class));
		// generalMapRow(rs, res);
		return res;
	}

}
