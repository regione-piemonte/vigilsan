/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsancommon.api.persistence.model.StruttraInterface;
import it.csi.vigilsan.vigilsancommon.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;

public class StrutturaMapper<T extends StruttraInterface> extends GenericTableMapper implements RowMapper<T> {

    private Class<T> clazz; // Tipo generico T

    // Costruttore che richiede il parametro di tipo Class<T>
    public StrutturaMapper(Class<T> clazz) {
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
		res.setStrutturaId(rs.getObject("struttura_id", Integer.class));
		res.setStrutturaCod(rs.getString("struttura_cod"));
		res.setStrutturaCodConfiguratore(rs.getString("struttura_cod_configuratore"));
		res.setStrutturaCodArpe(rs.getString("struttura_cod_arpe"));
		res.setStrutturaDesc(rs.getString("struttura_desc"));
		res.setStrutturaTipoId(rs.getObject("struttura_tipo_id", Integer.class));
		res.setComuneId(rs.getObject("comune_id", Integer.class));
		res.setIndirizzo(rs.getString("indirizzo"));
		res.setCoordSrid(rs.getString("coord_srid"));
		res.setCoordX(rs.getString("coord_x"));
		res.setCoordY(rs.getString("coord_y"));
		res.setCap(rs.getString("cap"));
		res.setStrutturaAccreditamentoId(rs.getObject("struttura_accreditamento_id", Integer.class));
		res.setStrutturaNaturaId(rs.getObject("struttura_natura_id", Integer.class));
		res.setStrutturaTitolaritaId(rs.getObject("struttura_titolarita_id", Integer.class));
		generalMapRow(rs, res);
		return res;
	}

}
