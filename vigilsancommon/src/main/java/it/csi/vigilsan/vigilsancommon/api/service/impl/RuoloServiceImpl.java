/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl.RuoloDao;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.Ruolo;
import it.csi.vigilsan.vigilsancommon.api.persistence.repository.RuoloRepository;
import it.csi.vigilsan.vigilsancommon.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;

@Service
public class RuoloServiceImpl {

	@Autowired
	private RuoloRepository ruoloRepository;

	@Autowired
	private RuoloDao ruoloDao;

	public Ruolo getRuoloByRuoloCod(String ruoloCod) {
		try {
			return ruoloRepository.getByRuoloCod(ruoloCod);
		} catch (EmptyResultDataAccessException e) {
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.RUOLO_NON_TROVATO);
		}
	}


	public Ruolo getRuoloByRuoloId(Integer ruoloId) {
		try {
			return ruoloDao.get(ruoloId);
		} catch (EmptyResultDataAccessException e) {
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.RUOLO_NON_TROVATO);
		}
	}

	private void insertRuolo(Ruolo ruolo) {
		ruoloDao.insert(ruolo);
	}

	public void insertRuolo(String ruoloCod, String ruoloDesc, String utenteOperazione) {
		var ruolo = new Ruolo();
		ruolo.setRuoloCod(ruoloCod);
		ruolo.setRuoloDesc(ruoloDesc);
		ruolo.setUtenteCreazione(utenteOperazione);
		ruolo.setUtenteModifica(utenteOperazione);
		this.insertRuolo(ruolo);
	}

}
