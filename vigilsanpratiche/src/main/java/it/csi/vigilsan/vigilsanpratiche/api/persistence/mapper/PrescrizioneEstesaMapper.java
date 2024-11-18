/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelPrescrizioneEsteso;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.Prescrizione;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PrescrizioneEstesa;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PrescrizioneStato;

public class PrescrizioneEstesaMapper implements RowMapper<ModelPrescrizioneEsteso> {


	private PrescrizioneMapper<PrescrizioneEstesa> prescrizioneMapper;
	
	public PrescrizioneEstesaMapper() {
		super();
		this.prescrizioneMapper = new PrescrizioneMapper<>(PrescrizioneEstesa.class);
	}

	@Override
	public ModelPrescrizioneEsteso mapRow(ResultSet rs, int rowNum) throws SQLException {

		PrescrizioneEstesa res = prescrizioneMapper.mapRow(rs, rowNum);

		var stato = new PrescrizioneStato();
		res.setPrescrizioneStato(stato);
		stato.setPrescrizioneStatoId(rs.getObject("prescrizione_stato_id", Integer.class));
		stato.setPrescrizioneStatoCod(rs.getString("prescrizione_stato_cod"));
		stato.setPrescrizioneStatoDesc(rs.getString("prescrizione_stato_desc"));
		stato.setPrescrizioneStatoFinale(rs.getBoolean("prescrizione_stato_finale"));
		stato.setPrescrizioneStatoIniziale(rs.getBoolean("prescrizione_stato_iniziale"));
		res.setAzioneDesc(rs.getString("azione_desc"));
		res.setModuloCompilatoId(rs.getObject("modulo_compilato_id", Integer.class));
		res.setModuloId(rs.getObject("modulo_id", Integer.class));

		return res;
	}

}
