/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelPratica;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaDettaglioEstesa;
import it.csi.vigilsan.vigilsanutil.generic.util.DateUtils;

public class PraticaDettaglioEstesaMapper extends PraticaDettaglioMapper<PraticaDettaglioEstesa,PraticaDettaglioEstesa> {

	public PraticaDettaglioEstesaMapper() {
		super(PraticaDettaglioEstesa.class);
	}

	@Override
	public PraticaDettaglioEstesa mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = super.mapRow(rs, rowNum);
		var pratica = new ModelPratica();
		pratica.setPraticaId(rs.getObject("pratica_id", Integer.class));
		pratica.setStrutturaId(rs.getObject("struttura_id", Integer.class));
		pratica.setPraticaTipoId(rs.getObject("pratica_tipo_id", Integer.class));
		pratica.setDataoraApertura(DateUtils.dateFromSqlTimestamp(rs.getTimestamp("dataora_apertura")));
		pratica.setDataoraChiusura(DateUtils.dateFromSqlTimestamp(rs.getTimestamp("dataora_chiusura")));
		res.setPratica(pratica);
		generalMapRow(rs, res);

		return res;
	}

}
