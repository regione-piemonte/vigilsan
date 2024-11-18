/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelPratica;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaDettaglioEstesa;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaDettaglioForPost;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PrescrizioneForPost;
import it.csi.vigilsan.vigilsanutil.generic.util.DateUtils;

public class PraticaDettaglioForPostMapper extends PraticaDettaglioMapper<PraticaDettaglioForPost,PraticaDettaglioForPost> {

	public PraticaDettaglioForPostMapper() {
		super(PraticaDettaglioForPost.class);
	}

	@Override
	public PraticaDettaglioForPost mapRow(ResultSet rs, int rowNum) throws SQLException {

		PraticaDettaglioForPost res = super.mapRow(rs, rowNum);
		res.setUpdateStatus(rs.getString("update_status"));
		res.setPraticaNumero(rs.getString("pratica_numero"));
		res.setAppuntamentoNumero(rs.getString("appuntamento_numero"));
		res.setPrescrzioneNumero(rs.getString("prescrizione_numero"));

		return res;
	}

}
