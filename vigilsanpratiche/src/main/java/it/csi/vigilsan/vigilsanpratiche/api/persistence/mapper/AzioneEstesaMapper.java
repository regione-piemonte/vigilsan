/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.Azione;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.AzioneEstesa;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.mapper.GenericTableMapper;

public class AzioneEstesaMapper extends GenericTableMapper implements RowMapper<AzioneEstesa> {

	@Override
	public AzioneEstesa mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new AzioneEstesa();
		res.setAzioneId(rs.getObject("azione_id", Integer.class));
		res.setAzioneCod(rs.getString("azione_cod"));
		res.setAzioneDesc(rs.getString("azione_desc"));
		res.setAppuntamentoTipoId(rs.getObject("appuntamento_tipo_id", Integer.class));
		res.setPrescrizioneTipoId(rs.getObject("prescrizione_tipo_id", Integer.class));
		res.setAzioneInizialeAppuntamento(rs.getBoolean("azione_iniziale_appuntamento"));
		res.setAzioneInizialePrescrizione(rs.getBoolean("azione_iniziale_prescrizione"));
		res.setAzioneInizialePratica(rs.getBoolean("azione_iniziale_pratica"));
		res.setStatoIdLIsta(rs.getString("stato_id_lista"));
		res.setModuloId(rs.getObject("modulo_id", Integer.class));
		generalMapRow(rs, res);
		return res;
	}

}
