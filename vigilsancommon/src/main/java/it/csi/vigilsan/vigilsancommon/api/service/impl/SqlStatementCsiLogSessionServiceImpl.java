/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl.CsiLogSessionDao;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.CsiLogSession;
import it.csi.vigilsan.vigilsancommon.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableWPersistenceApiServiceImpl;

@Service
public class SqlStatementCsiLogSessionServiceImpl extends AuditableWPersistenceApiServiceImpl{

	@Autowired
	private CsiLogSessionDao csiLogSessionDao;

	public Integer insertLogSession(String ipAddress, String cfUtente, Boolean flgAccessoPua) {
		try {

			Integer sessionId = csiLogSessionDao.getSequence();

			var params = new MapSqlParameterSource();

			params.addValue("sessionId", sessionId);
			params.addValue("ipAddress", ipAddress);
			params.addValue("cfUtente", cfUtente);
			params.addValue("flgAccessoPua", flgAccessoPua);

			
			var session = new CsiLogSession();
			session.setSessionId(sessionId);
			session.setIpAddress(ipAddress);
			session.setCfUtente(cfUtente);
			session.setFlgAccessoPua(flgAccessoPua);
			csiLogSessionDao.insert(session);

			return sessionId;
		} catch (EmptyResultDataAccessException e) {
			logError(null,"insertLogSession", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.ERRORE_INSERIMENTO_LOG_SESSION);
		}
	
	}

	public void updateLogSession(Integer sessionId, Integer soggettoId, Integer ruoloId, Integer applicativoId,
			Integer profiloId, Integer strutturaId, Integer enteId) {
		try {
			var session = csiLogSessionDao.get(sessionId);
			session.setSoggettoId(soggettoId);
			session.setRuoloId(ruoloId);
			session.setApplicativoId(applicativoId);
			session.setProfiloId(profiloId);
			session.setStrutturaId(strutturaId);
			session.setEnteId(enteId);
			csiLogSessionDao.update(session, sessionId);
		} catch (EmptyResultDataAccessException e) {
			logError(sessionId,"updateLogSession", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.ERRORE_UPDATE_LOG_SESSION);
		}
	}
	


	public Integer insertSession(String ipAddress, String cfUtente, Boolean flgAccessoPua,Integer soggettoId, Integer ruoloId, Integer applicativoId,
			Integer profiloId, Integer strutturaId, Integer enteId) {
		try {

			Integer sessionId = csiLogSessionDao.getSequence();
			
			var session = new CsiLogSession();
			session.setSessionId(sessionId);
			session.setIpAddress(ipAddress);
			session.setCfUtente(cfUtente);
			session.setFlgAccessoPua(flgAccessoPua);
			session.setSoggettoId(soggettoId);
			session.setRuoloId(ruoloId);
			session.setApplicativoId(applicativoId);
			session.setProfiloId(profiloId);
			session.setStrutturaId(strutturaId);
			session.setEnteId(enteId);
			csiLogSessionDao.insert(session);

			return sessionId;
		} catch (EmptyResultDataAccessException e) {
			logError(null,"insertLogSession", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.ERRORE_INSERIMENTO_LOG_SESSION);
		}
	
	}

	public CsiLogSession getById(Integer sessionId) {
		try {
			return csiLogSessionDao.get(sessionId);
		} catch (EmptyResultDataAccessException e) {
			logError(sessionId,"getById", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.SESSIONE_NON_TROVATA);
		}
	}
	public void logout(Integer sessionId) {
		try {
			csiLogSessionDao.logout(sessionId);
		} catch (EmptyResultDataAccessException e) {
			logError(sessionId,"logout", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.ERRORE_UPDATE_LOG_SESSION);
		}
	}
}
