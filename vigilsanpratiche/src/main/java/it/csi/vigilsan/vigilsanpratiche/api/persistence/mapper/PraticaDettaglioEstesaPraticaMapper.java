/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelPratica;
import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelPraticaDettaglioEstesaPratica;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaDettaglioEstesa;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaDettaglioEstesaPratica;
import it.csi.vigilsan.vigilsanutil.generic.util.DateUtils;

public class PraticaDettaglioEstesaPraticaMapper extends PraticaDettaglioMapper<PraticaDettaglioEstesaPratica, ModelPraticaDettaglioEstesaPratica> {

	public PraticaDettaglioEstesaPraticaMapper() {
		super(PraticaDettaglioEstesaPratica.class);
	}

	@Override
	public ModelPraticaDettaglioEstesaPratica mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = super.mapRow(rs, rowNum);
		res.setAzioneDesc(rs.getString("azione_desc"));
		res.setModuloId(rs.getObject("modulo_id", Integer.class));
		res.setFlgClreqEsito(rs.getBoolean("flg_clreq_esito"));
		res.setFlgBloccato(rs.getBoolean("flg_bloccato"));

		return res;
	}

}
