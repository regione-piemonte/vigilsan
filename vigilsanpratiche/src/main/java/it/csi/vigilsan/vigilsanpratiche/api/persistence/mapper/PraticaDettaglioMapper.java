/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelPraticaDettaglio;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaDettaglioInterface;
import it.csi.vigilsan.vigilsanpratiche.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.util.DateUtils;

public class PraticaDettaglioMapper<T extends ModelPraticaDettaglio & PraticaDettaglioInterface,V extends ModelPraticaDettaglio>
		extends GenericTableMapper implements RowMapper<V> {

	private Class<T> clazz; // Tipo generico T

	public PraticaDettaglioMapper(Class<T> clazz) {
		this.clazz = clazz;
		

	}

	@Override
	public V mapRow(ResultSet rs, int rowNum) throws SQLException {

		T res;
		try {
			res = clazz.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.ERRORE_CREAZIONE_OGGETTO_QUERY);
		}
		res.setPraticaDetId(rs.getObject("pratica_det_id", Integer.class));
		res.setPraticaId(rs.getObject("pratica_id", Integer.class));
		res.setPraticaStatoId(rs.getObject("pratica_stato_id", Integer.class));
		res.setPrescrizioneId(rs.getObject("prescrizione_id", Integer.class));
		res.setPrescrizioneStatoId(rs.getObject("prescrizione_stato_id", Integer.class));
		res.setAppuntamentoId(rs.getObject("appuntamento_id", Integer.class));
		res.setAppuntamentoStatoId(rs.getObject("appuntamento_stato_id", Integer.class));
		res.setAzioneId(rs.getObject("azione_id", Integer.class));
		res.setModuloCompilatoId(rs.getObject("modulo_compilato_id", Integer.class));
		res.setProfiloId(rs.getObject("profilo_id", Integer.class));
		res.setNote(rs.getString("note"));
		res.setFlgScadenza(rs.getString("flg_scadenza"));
		res.setProfiloIdScadenza(rs.getObject("profilo_id_scadenza", Integer.class));
		res.setDataoraAzione(DateUtils.dateFromSqlTimestamp(rs.getTimestamp("dataora_azione")));
		generalMapRow(rs, res);	    
		
		return (V) res;
	}

}
