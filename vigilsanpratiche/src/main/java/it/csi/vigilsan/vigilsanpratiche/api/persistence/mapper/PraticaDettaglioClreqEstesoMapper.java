/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelClreqEsito;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.ChecklistReq;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaDettaglioClreqEsteso;

public class PraticaDettaglioClreqEstesoMapper extends PraticaDettaglioClreqMapper<PraticaDettaglioClreqEsteso> {

	private final ChecklistReqMapper<ChecklistReq> checkListReqMapper = new ChecklistReqMapper<>(ChecklistReq.class);
	
	public PraticaDettaglioClreqEstesoMapper() {
		super(PraticaDettaglioClreqEsteso.class);
		
	}

	@Override
	public PraticaDettaglioClreqEsteso mapRow(ResultSet rs, int rowNum) throws SQLException {

		var res = new PraticaDettaglioClreqEsteso();
		res.setPraticaDetClreqId(rs.getObject("pratica_det_clreq_id", Integer.class));
		res.setPraticaDetId(rs.getObject("pratica_det_id", Integer.class));
		res.setClreqId(rs.getObject("clreq_id", Integer.class));
		res.setClreqEsitoId(rs.getObject("clreq_esito_id", Integer.class));
		res.setModuloCompilatoId(rs.getObject("modulo_compilato_id", Integer.class));
		res.setNote(rs.getString("note"));

		res.setChecklistReq(checkListReqMapper.mapRow(rs, rowNum));
		
		ModelClreqEsito esito = new ModelClreqEsito();
		esito.setClreqEsitoId(rs.getObject("clreq_esito_id", Integer.class));
		esito.setClreqEsitoCod(rs.getString("clreq_esito_cod"));
		esito.setClreqEsitoDesc(rs.getString("clreq_esito_desc"));

		res.setClreqEsito(esito);
		return res;
	}

}
