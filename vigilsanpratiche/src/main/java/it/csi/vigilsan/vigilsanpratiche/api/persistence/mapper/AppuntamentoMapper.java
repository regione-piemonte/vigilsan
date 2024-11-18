/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.AppuntamentoInterface;
import it.csi.vigilsan.vigilsanpratiche.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.util.DateUtils;

public class AppuntamentoMapper<T extends AppuntamentoInterface> extends GenericTableMapper implements RowMapper<T> {

    private Class<T> clazz; // Tipo generico T

    // Costruttore che richiede il parametro di tipo Class<T>
    public AppuntamentoMapper(Class<T> clazz) {
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
		res.setAppuntamentoId(rs.getObject("appuntamento_id", Integer.class));
		res.setAppuntamentoTipoId(rs.getObject("appuntamento_tipo_id", Integer.class));
		res.setPraticaId(rs.getObject("pratica_id", Integer.class));
		res.setDataoraInizio(DateUtils.dateFromSqlTimestamp(rs.getTimestamp("dataora_inizio")));
		res.setDataoraFine(DateUtils.dateFromSqlTimestamp(rs.getTimestamp("dataora_fine")));
		res.setDataoraApertura(DateUtils.dateFromSqlTimestamp(rs.getTimestamp("dataora_apertura")));
		res.setDataoraChiusura(DateUtils.dateFromSqlTimestamp(rs.getTimestamp("dataora_chiusura")));
		res.setAppuntamentoNumero(rs.getString("appuntamento_numero"));
		generalMapRow(rs, res);
		return res;
	}

}
