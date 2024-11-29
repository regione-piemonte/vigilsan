/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsancommon.api.persistence.model.InvioNotificaInterface;
import it.csi.vigilsan.vigilsancommon.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.util.DateUtils;

public class InvioNotificaMapper<T extends InvioNotificaInterface> extends GenericTableMapper
		implements RowMapper<T> {

	private Class<T> clazz; // Tipo generico T

// Costruttore che richiede il parametro di tipo Class<T>
	public InvioNotificaMapper(Class<T> clazz) {
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

		res.setNotificaId(rs.getObject("notifica_id", Integer.class));
		res.setCfDestinatario(rs.getString("cf_destinatario"));
		res.setDataoraInvio(DateUtils.dateFromSqlTimestamp(rs.getTimestamp("dataora_invio")));
		res.setEsitoInvio(rs.getBoolean("esito_invio"));
		res.setErroreInvio(rs.getString("errore_invio"));
		res.setRequestContent(rs.getString("request_content"));
		res.setResponseContent(rs.getString("response_content"));
		res.setResponseResult(rs.getString("response_result"));

		generalMapRow(rs, res);
		return res;
	}

}
