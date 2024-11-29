/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsancommon.api.persistence.model.NotificaInterface;
import it.csi.vigilsan.vigilsancommon.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;

public class NotificaMapper<T extends NotificaInterface> extends GenericTableMapper implements RowMapper<T> {

	private Class<T> clazz; // Tipo generico T

//Costruttore che richiede il parametro di tipo Class<T>
	public NotificaMapper(Class<T> clazz) {
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
		res.setProfiloId(rs.getObject("profilo_id", Integer.class));
		res.setEnteId(rs.getObject("ente_id", Integer.class));
		res.setStrutturaId(rs.getObject("struttura_id", Integer.class));
		res.setSoggettoId(rs.getObject("soggetto_id", Integer.class));
		res.setTitle(rs.getString("title"));
		res.setBodyTextShort(rs.getString("body_text_short"));
		res.setBodyTextLong(rs.getString("body_text_long"));
		res.setBodyHtml(rs.getString("body_html"));

		generalMapRow(rs, res);
		return res;
	}

}
