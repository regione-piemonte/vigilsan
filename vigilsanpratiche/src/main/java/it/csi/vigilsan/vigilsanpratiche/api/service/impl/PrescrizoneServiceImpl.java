/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelPrescrizioneTipo;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.dao.impl.PrescrizioneDao;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.dao.impl.PrescrizioneStatoDao;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.dao.impl.PrescrizioneTipoDao;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PrescrizioneForPost;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PrescrizioneStato;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PrescrizioneTipo;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableWPersistenceApiServiceImpl;

@Service
public class PrescrizoneServiceImpl extends AuditableWPersistenceApiServiceImpl {
	@Autowired
	PrescrizioneStatoDao prescrizioneStatoDao;
	@Autowired
	PrescrizioneTipoDao prescrizioneTipoDao;

	@Autowired
	PrescrizioneDao prescrizioneDao;

	public List<PrescrizioneStato> getPrescrizioneStatoDecodifica(Integer sId) {
		return prescrizioneStatoDao.getAll();
	}

	public List<PrescrizioneTipo> getPrescrizioneTipoDecodifica(Integer sId) {
		return prescrizioneTipoDao.getAll();
	}

	public ModelPrescrizioneTipo getPrescrizioneTipoDecodificaById(Integer prescrizioneTipoId, Integer sId) {
		return prescrizioneTipoDao.get(prescrizioneTipoId);
	}
	
	public PrescrizioneStato getPrescrizioneStatoById(Integer prescrizioneStatoId, Integer sId) {
		return prescrizioneStatoDao.get(prescrizioneStatoId);
	}

	public PrescrizioneForPost getPrescrizioneById(Integer prescrizioneStatoId, Integer sId) {
		return prescrizioneDao.getForPost(prescrizioneStatoId);
	}

}
