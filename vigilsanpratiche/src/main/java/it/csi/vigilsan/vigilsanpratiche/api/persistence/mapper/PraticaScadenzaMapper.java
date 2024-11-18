/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelPraticaScadenza;
import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelPrescrizioneEsteso;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PrescrizioneEstesa;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PrescrizioneStato;
import it.csi.vigilsan.vigilsanutil.generic.util.DateUtils;

public class PraticaScadenzaMapper implements RowMapper<ModelPraticaScadenza> {

	@Override
	public ModelPraticaScadenza mapRow(ResultSet rs, int rowNum) throws SQLException {

		ModelPraticaScadenza res = new ModelPraticaScadenza();
		res.setDataoraScadenza(DateUtils.dateFromSqlTimestamp(rs.getTimestamp("dataora_scadenza")));
		res.setFlgScadenza(rs.getString("flg_scadenza"));
		res.setScadenzaDesc(rs.getString("scadenza_desc"));
		res.setPraticaId(rs.getObject("pratica_id", Integer.class));
		res.setPrescrizioneId(rs.getObject("prescrizione_id", Integer.class));
		res.setAppuntamentoId(rs.getObject("appuntamento_id", Integer.class));
		res.setEnteId(rs.getObject("ente_id", Integer.class));
		res.setEnteCod(rs.getString("ente_cod"));
		res.setEnteDesc(rs.getString("ente_desc"));
		res.setStrutturaId(rs.getObject("struttura_id", Integer.class));
		res.setStrutturaCod(rs.getString("struttura_cod"));
		res.setStrutturaCodArpe(rs.getString("struttura_cod_arpe"));
		res.setStrutturaDesc(rs.getString("struttura_desc"));
		res.setPraticaTipoId(rs.getObject("pratica_tipo_id", Integer.class));
		res.setPraticaTipoCod(rs.getString("pratica_tipo_cod"));
		res.setPraticaTipoDesc(rs.getString("pratica_tipo_desc"));
		res.setDataoraApertura(DateUtils.dateFromSqlTimestamp(rs.getTimestamp("dataora_apertura")));
		res.setDataoraChiusura(DateUtils.dateFromSqlTimestamp(rs.getTimestamp("dataora_chiusura")));
		res.setPrescrizioneTipoId(rs.getObject("prescrizione_tipo_id", Integer.class));
		res.setPrescrizioneTipoCod(rs.getString("prescrizione_tipo_cod"));
		res.setPrescrizioneTipoDesc(rs.getString("prescrizione_tipo_desc"));
		res.setAppuntamentoTipoId(rs.getObject("appuntamento_tipo_id", Integer.class));
		res.setAppuntamentoTipoCod(rs.getString("appuntamento_tipo_cod"));
		res.setAppuntamentoTipoDesc(rs.getString("appuntamento_tipo_desc"));

		return res;
	}

}
