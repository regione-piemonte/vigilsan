/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelAppuntamentoStato;
import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelAppuntamentoTipo;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.dao.impl.AppuntamentoStatoDao;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.dao.impl.AppuntamentoTipoDao;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.AppuntamentoStato;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.AppuntamentoTipo;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableWPersistenceApiServiceImpl;

@Service
public class AppuntamentoServiceImpl extends AuditableWPersistenceApiServiceImpl {

	@Autowired
	AppuntamentoStatoDao appuntamentoStatoDao;
	@Autowired
	AppuntamentoTipoDao appuntamentoTipoDao;

	public List<AppuntamentoStato> getAppuntamentoStatoDecodifica(Integer sId) {
		return appuntamentoStatoDao.getAll();
	}

	public List<AppuntamentoTipo> getAppuntamentoTipoDecodifica(Integer sId) {
		return appuntamentoTipoDao.getAll();
	}

	public ModelAppuntamentoTipo getAppuntamentoTipoDecodificaById(Integer appuntamentoTipoId, Integer sId) {
		return appuntamentoTipoDao.get(appuntamentoTipoId);
	}

	public ModelAppuntamentoStato getAppuntamentoStatoById(Integer appuntamentoStatoId, Integer sId) {
		return appuntamentoStatoDao.get(appuntamentoStatoId);
	}

	public List<AppuntamentoStato> getByPraticaTipoId(Integer praticaTipoId) {
		return appuntamentoStatoDao.getByPraticaTipoId(praticaTipoId);
	}
}
