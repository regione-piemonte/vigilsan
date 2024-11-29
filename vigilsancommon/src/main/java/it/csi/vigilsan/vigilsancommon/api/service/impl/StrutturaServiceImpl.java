/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl.StrutturaDao;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.Struttura;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.StrutturaPaginata;
import it.csi.vigilsan.vigilsancommon.api.persistence.repository.StrutturaRepository;
import it.csi.vigilsan.vigilsancommon.api.service.StrutturaService;
import it.csi.vigilsan.vigilsancommon.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsancommon.util.LogMessageEnum;
import it.csi.vigilsan.vigilsancommon.util.StrutturaParametroEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTException;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableWPersistenceApiServiceImpl;
import it.csi.vigilsan.vigilsanutil.generic.util.AuditOperazioneEnum;
import it.csi.vigilsan.vigilsanutil.generic.util.paginazione.Paginazione;

@Service
public class StrutturaServiceImpl extends AuditableWPersistenceApiServiceImpl implements StrutturaService {

	@Autowired
	private StrutturaRepository strutturaRepository;

	@Autowired
	private StrutturaDao strutturaDao;

	@Override
	public Struttura getStrutturaByStrutturaCodArpe(Integer sId, String strutturaCodArpe) {
		try {
			return strutturaRepository.getByStrutturaCodConfiguratore(strutturaCodArpe);
		} catch (EmptyResultDataAccessException e) {
			logError(sId, "getStrutturaByStrutturaCodConfiguratore", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.STRUTTURA_NON_TROVATA);
		}
	}

	@Override
	public Struttura getStrutturaByStrutturaId(Integer sId, Integer strutturaId) {
		try {
			return strutturaDao.get(strutturaId);
		} catch (EmptyResultDataAccessException e) {
			logError(sId, "getStrutturaByStrutturaId", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.STRUTTURA_NON_TROVATA);
		}
	}

	@Override
	public void updateStrutturaByStrutturaId(Integer sId, Struttura strutturaId) {
		try {
			strutturaDao.update(strutturaId, strutturaId.getStrutturaId());
			logAuditDb(sId, AuditOperazioneEnum.UPDATE, "vigil_t_struttura", strutturaId.getStrutturaId().toString());
		} catch (Exception e) {
			logError(sId, "getStrutturaByStrutturaId", e.getMessage(), e);
		}
	}

	@Override
	public Struttura getStrutturaByCodArpe(Integer sId, String inCodArpe) {
		var methodName = "getStrutturaByCodArpe";
		logDebug(sId, methodName, LogMessageEnum.BEGIN.getMessage());

		try {
			logDebug(sId, methodName, "Codice arpe : " + inCodArpe);
			return strutturaDao.getStrutturaByCodArpe(inCodArpe);
		} catch (EmptyResultDataAccessException e) {
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.STRUTTURA_NON_TROVATA);
		} finally {
			logDebug(sId, methodName, LogMessageEnum.BEGIN.getMessage());
		}
	}

	@Override
	public Struttura getStrutturaByCodArpeValida(Integer sId, String inCodArpe) {
		var methodName = "getStrutturaByCodArpe";
		logDebug(sId, methodName, LogMessageEnum.BEGIN.getMessage());

		try {
			logDebug(sId, methodName, "Codice arpe : " + inCodArpe);
			return strutturaDao.getStrutturaByCodArpeValida(inCodArpe);
		} catch (EmptyResultDataAccessException e) {
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.STRUTTURA_NON_TROVATA);
		} finally {
			logDebug(sId, methodName, LogMessageEnum.BEGIN.getMessage());
		}
	}

	@Override
	public Struttura gestioneStruttura(Integer sId, Struttura inStrutturaToManage, String inStrutturaCod,
			String inUtente) {
		var methodName = "gestioneStruttura";
		logDebug(sId, methodName, LogMessageEnum.BEGIN.getMessage());

		try {
			logDebug(sId, methodName, "COD_STRUTTURA : " + inStrutturaCod);
			Struttura strutturaCheck = getStrutturaByCodArpe(sId, inStrutturaCod);

			logDebug(sId, methodName, "Id struttura : " + strutturaCheck.getStrutturaId());
			logDebug(sId, methodName, "Code arpe struttura : " + strutturaCheck.getStrutturaCodArpe());

			inStrutturaToManage.setDataModifica(new Date());
			inStrutturaToManage.setUtenteModifica(inUtente);
			strutturaDao.update(inStrutturaToManage, strutturaCheck.getStrutturaId());
			return strutturaCheck;
		} catch (RESTException strutturaException) {
			logDebug(sId, methodName, "Code error : " + strutturaException.getCode());

			if (ErrorCodeEnum.STRUTTURA_NON_TROVATA.getCode().equals(strutturaException.getCode())) {
				logDebug(sId, methodName, "COD_STRUTTURA NON TROVATO: " + inStrutturaCod);

//				// Creo la struttura
				logDebug(sId, methodName, " COD ARPE : " + inStrutturaCod);
				inStrutturaToManage.setStrutturaCodArpe(inStrutturaCod);
				logDebug(sId, methodName, " COD : " + inStrutturaCod);
				inStrutturaToManage.setStrutturaCod(inStrutturaCod);
				logDebug(sId, methodName, " UTENTE CREAZIONE : " + inUtente);
				inStrutturaToManage.setUtenteCreazione(inUtente);
				// Inserisco la struttura
				strutturaDao.insert(inStrutturaToManage);

				Struttura strutturaCheck = getStrutturaByCodArpe(sId, inStrutturaCod);
				if (strutturaCheck != null) {
					logDebug(sId, methodName, " STRUTTURA ID INSERTED : " + strutturaCheck.getStrutturaId());
				}
				return strutturaCheck;
			}

			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.ERRORE_INSERIMENTO_STRUTTURA);
		} catch (Exception e) {
			logError(sId, methodName, e.getMessage(), e);
			throw e;
		} finally {
			logDebug(sId, methodName, LogMessageEnum.END.getMessage());
		}

	}

	public void cancellaStrutture(Integer sId, Date inDataInizioElaborazione) {
		var methodName = "cancellaStrutture";
		logDebug(sId, methodName, LogMessageEnum.BEGIN.getMessage());

		try {
			strutturaDao.deleteStrutture(inDataInizioElaborazione);
		} catch (Exception e) {
			logError(sId, methodName, e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.ERRORE_CANCELLAZIONE_STRUTTURA);
		} finally {
			logDebug(sId, methodName, LogMessageEnum.END.getMessage());
		}
	}

	public Date getActualDate(Integer sId) {
		var methodName = "getDataAttuale";
		logDebug(sId, methodName, LogMessageEnum.BEGIN.getMessage());

		try {
			return strutturaDao.getActualDate();
		} catch (Exception e) {
			logError(sId, methodName, e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.ERRORE_RECUPERO_DATA);
		} finally {
			logDebug(sId, methodName, LogMessageEnum.END.getMessage());
		}
	}

	/**
	 * Metodo per il richiamo della function per il ricalcolo della struttura
	 * 
	 * @param sId
	 */
	public void ricalcolaStruttura(Integer sId) {
		var methodName = "ricalcolaStruttura";
		logDebug(sId, methodName, LogMessageEnum.BEGIN.getMessage());

		try {
			strutturaDao.ricalcolaStruttura();
		} catch (Exception e) {
			logError(sId, methodName, e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.ERRORE_RICALCOLA_STRUTTURA);
		} finally {
			logDebug(sId, methodName, LogMessageEnum.END.getMessage());
		}
	}

	@Override
	public Object getStrutturaParametro(Integer sId, Integer strutturaId, String parametroCod) {
		try {
			return StrutturaParametroEnum.valueOf(parametroCod).getResponse(strutturaDao, strutturaId);
		} catch (Exception e) {
			logError(sId, "getDocumentazioneParametro", "parametroCod: " + parametroCod + " " + e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public List<StrutturaPaginata> getStrutturaByEnteId(Integer sId, Integer enteId, String ruoloEnteStrutturaCod,
			String filter, Integer strutturaId, Paginazione paginazione) {
		return strutturaDao.getStruttureByEnteId(enteId, ruoloEnteStrutturaCod, filter, strutturaId, paginazione);
	}

}
