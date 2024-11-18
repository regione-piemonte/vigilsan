/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.vigilsan.vigilsanmoduli.api.persistence.dao.impl.ModuloCompilatoDao;
import it.csi.vigilsan.vigilsanmoduli.api.persistence.dao.impl.ModuloCompilatoDettaglioDao;
import it.csi.vigilsan.vigilsanmoduli.api.persistence.model.ModuloCompilato;
import it.csi.vigilsan.vigilsanmoduli.api.persistence.model.ModuloCompilatoDettaglio;
import it.csi.vigilsan.vigilsanmoduli.api.persistence.repository.impl.ModuloRepository;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableApiServiceImpl;

@Service
public class ModuloCompilatoServiceImpl extends AuditableApiServiceImpl {

	@Autowired
	private ModuloCompilatoDao moduloCompilatoDao;

	@Autowired
	private ModuloRepository moduloRepository;

	@Autowired
	private ModuloCompilatoDettaglioDao moduloCompilatoDettaglioService;

	public int[] saveModuloCompilatoDettaglio(Integer sId, List<ModuloCompilatoDettaglio> valori) {
		try {

			int[] res = moduloCompilatoDettaglioService.insert(valori);
			logInfo(sId, "saveModuloCompilatoDettaglio", "valori salvati: " + res);
			return res;
		} catch (Exception e) {
			logError(sId, "saveModuloCompilatoDettaglio", e.getMessage(), e);
			throw e;
		}

	}

	public void deleteModuloCompilato(Integer sId, Integer moduloCompilatoId, String utenteOperazione) {
		try {
			var mod = new ModuloCompilato();
			mod.setUtenteCancellazione(utenteOperazione);
			mod.setModuloCompilatoId(moduloCompilatoId);

			moduloCompilatoDao.delete(mod);
		} catch (Exception e) {
			logError(sId, "deleteModuloCompilato", e.getMessage(), e);
			throw e;
		}

	}

	public ModuloCompilato getModuloFromModuloCompilatoId(Integer sId, Integer moduloCompilatoId) {
		try {

			return moduloCompilatoDao.get(moduloCompilatoId);
		} catch (Exception e) {
			logError(sId, "getModuloFromModuloCompilatoId", e.getMessage(), e);
			throw e;
		}

	}

	@Transactional
	public Integer insertModuloCompilato(Integer sId, Integer moduloId, String note, String utenteCreazione) {
		try {
			Integer moduloCompilatoId = moduloRepository.getSequenceModuloCompilatoId();

			var mc = new ModuloCompilato();
			mc.setModuloCompilatoId(moduloCompilatoId);
			mc.setModuloId(moduloId);
			mc.setNote(note);
			mc.setUtenteCreazione(utenteCreazione);
			moduloCompilatoDao.insert(mc);

			return mc.getModuloCompilatoId();
		} catch (Exception e) {
			logError(sId, "insertModuloCompilato", e.getMessage(), e);
			throw e;
		}

	}
}
