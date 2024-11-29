/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import it.csi.vigilsan.vigilsancommon.api.persistence.model.SoggettoInterface;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;
import it.csi.vigilsan.vigilsanutil.generic.util.DateUtils;

public class SoggettoMapperGeneric extends GenericTableMapper {
	


	protected <T extends SoggettoInterface>void mapRow(ResultSet rs, T res) throws SQLException {
        res.setSoggettoId(rs.getObject("soggetto_id", Integer.class));
        res.setCodiceFiscale(rs.getString("codice_fiscale"));
        res.setCognome(rs.getString("cognome"));
        res.setNome(rs.getString("nome"));
        res.setPresenteConfiguratore(rs.getBoolean("presente_configuratore"));
        res.setDataNascita(DateUtils.dateFromSqlDate(rs.getDate("data_nascita")));
        generalMapRow(rs, res);
		
	}

}