/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelPraticaRequisiti;
import it.csi.vigilsan.vigilsanutil.generic.util.DateUtils;

public class PraticaRequisitiMapper implements RowMapper<ModelPraticaRequisiti> {

	@Override
	public ModelPraticaRequisiti mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new ModelPraticaRequisiti();
		res.setClreqId(rs.getObject("clreq_id", Integer.class));
		res.setPraticaDetId(rs.getObject("pratica_det_id", Integer.class));
		res.setDataoraAzione(DateUtils.dateFromSqlTimestamp(rs.getTimestamp("dataora_azione")));
		res.setAzioneDesc(rs.getString("azione_desc"));
		res.setClreqEsitoId(rs.getObject("clreq_esito_id", Integer.class));
		res.setClreqEsitoCod(rs.getString("clreq_esito_cod"));
		res.setClreqEsitoDesc(rs.getString("clreq_esito_desc"));
		res.setModuloCompilatoId(rs.getObject("modulo_compilato_id", Integer.class));
		res.setNote(rs.getString("note"));
		res.setPrescrizioneId(rs.getObject("prescrizione_id", Integer.class));
		res.setPrescrizioneTipoId(rs.getObject("prescrizione_tipo_id", Integer.class));
		res.setPrescrizioneNumero(rs.getString("prescrizione_numero"));
		res.setPrescrizioneTipoDesc(rs.getString("prescrizione_tipo_desc"));

		res.setAppuntamentoId(rs.getObject("appuntamento_id", Integer.class));
		res.setAppuntamentoTipoId(rs.getObject("appuntamento_tipo_id", Integer.class));
		res.setAppuntamentoNumero(rs.getString("appuntamento_numero"));
		res.setAppuntamentoTipoDesc(rs.getString("appuntamento_tipo_desc"));
		res.setFlgModificabile(rs.getBoolean("flg_modificabile"));

		return res;
	}

}
