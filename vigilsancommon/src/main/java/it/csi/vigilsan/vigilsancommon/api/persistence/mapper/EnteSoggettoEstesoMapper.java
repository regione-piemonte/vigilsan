/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsancommon.api.generated.dto.ModelRuoloEnteSoggetto;
import it.csi.vigilsan.vigilsancommon.api.generated.dto.ModelSoggetto;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.EnteSoggettoEsteso;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;
import it.csi.vigilsan.vigilsanutil.generic.util.DateUtils;

public class EnteSoggettoEstesoMapper extends GenericTableMapper implements RowMapper<EnteSoggettoEsteso> {

	@Override
	public EnteSoggettoEsteso mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new EnteSoggettoEsteso();
		res.setEnteId(rs.getObject("ente_id", Integer.class));
		res.setSoggettoId(rs.getObject("soggetto_id", Integer.class));
		res.setEnteSoggId(rs.getObject("ente_sogg_id", Integer.class));
		res.setRuoloEnteSoggettoId(rs.getObject("ruolo_ente_soggetto_id", Integer.class));
		ModelRuoloEnteSoggetto ruoloEnteSoggetto = new ModelRuoloEnteSoggetto();
		ruoloEnteSoggetto.setRuoloEnteSoggettoId(rs.getObject("ruolo_ente_soggetto_id", Integer.class));
		ruoloEnteSoggetto.setRuoloEnteSoggettoCod(rs.getString("ruolo_ente_soggetto_cod"));
		ruoloEnteSoggetto.setRuoloEnteSoggettoDesc(rs.getString("ruolo_ente_soggetto_desc"));
		res.setRuoloEnteSoggetto(ruoloEnteSoggetto);

		ModelSoggetto soggetto = new ModelSoggetto();
		soggetto.setSoggettoId(rs.getObject("soggetto_id", Integer.class));
		soggetto.setNome(rs.getString("nome"));
		soggetto.setCognome(rs.getString("cognome"));
		soggetto.setCodiceFiscale(rs.getString("codice_fiscale"));
		soggetto.setDataNascita(DateUtils.dateFromSqlDate(rs.getDate("data_nascita")));
		res.setSoggetto(soggetto);
		generalMapRow(rs, res);
		return res;
	}

}
