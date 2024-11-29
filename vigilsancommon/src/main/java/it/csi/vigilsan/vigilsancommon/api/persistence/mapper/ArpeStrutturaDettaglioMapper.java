/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsancommon.api.persistence.model.ArpeStrutturaDettaglioInterface;
import it.csi.vigilsan.vigilsancommon.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.util.DateUtils;

public class ArpeStrutturaDettaglioMapper<T extends ArpeStrutturaDettaglioInterface> extends GenericTableMapper implements RowMapper<T> {


    private Class<T> clazz; // Tipo generico T

    // Costruttore che richiede il parametro di tipo Class<T>
    public ArpeStrutturaDettaglioMapper(Class<T> clazz) {
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
		res.setArpeStrDettId(rs.getObject("arpe_str_dett_id", Integer.class));
		res.setStrutturaId(rs.getObject("struttura_id", Integer.class));
		res.setArpeStrutturaTipoId(rs.getObject("arpe_struttura_tipo_id", Integer.class));
		res.setArpeAssistenzaTipoId(rs.getObject("arpe_assistenza_tipo_id", Integer.class));
		res.setArpeAttivitaId(rs.getObject("arpe_attivita_id", Integer.class));
		res.setArpeAttivitaClasseId(rs.getObject("arpe_attivita_classe_id", Integer.class));
		res.setArpeDataAttivazioneStruttura(DateUtils.dateFromSqlDate(rs.getDate("arpe_data_attivazione_struttura")));
		res.setArpeDataAttivazioneAssistenza(DateUtils.dateFromSqlDate(rs.getDate("arpe_data_attivazione_assistenza")));
		res.setArpeDataAttivazioneAttivita(DateUtils.dateFromSqlDate(rs.getDate("arpe_data_attivazione_attivita")));
		generalMapRow(rs, res);
		return res;
	}


	
	
}
