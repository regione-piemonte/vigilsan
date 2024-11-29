/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.vigilsan.vigilsancommon.api.generated.dto.ModelInputFileInformation;
import it.csi.vigilsan.vigilsancommon.api.service.RpviarService;
import it.csi.vigilsan.vigilsancommon.api.service.StrutturaService;
import it.csi.vigilsan.vigilsancommon.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsancommon.util.LogMessageEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTException;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableWPersistenceApiServiceImpl;
import it.csi.vigilsan.vigilsanutil.generic.util.AuditOperazioneEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.SecurityContext;

@Service
public class RpviarServiceImpl extends AuditableWPersistenceApiServiceImpl implements RpviarService {

	@Autowired
	private StrutturaService strutturaService;
	@Autowired
	private RpviarServiceTransactionalImpl rpviarServiceTransactionalImpl;

	@Override
	public void funzionalitaBatchrpviar(Integer sId, ModelInputFileInformation body, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws Exception {
		var methodName = "funzionalitaBatchrpviar";
		try {
			Date dateBatch = null;

			try {
				// Calcolo la data di inizio elaborazione
				dateBatch = strutturaService.getActualDate(sId);
				logDebug(sId, methodName, "Data inizio elaborazione : " + dateBatch);
			} catch (RESTException enteException) {
				if (ErrorCodeEnum.ERRORE_RECUPERO_DATA.getCode().equals(enteException.getCode())) {
					logError(sId, methodName, "Errore nel recupero della data inizio elaborazione : ", enteException);
					logAuditDb(sId, AuditOperazioneEnum.BATCH, "Data non trovata",
							ErrorCodeEnum.ERRORE_RECUPERO_DATA.getCode());
				}
				throw enteException;
			}

			logDebug(sId, methodName, "Body : " + body);
			logDebug(sId, methodName, "Path File : " + body.getPathFile());
			RESTErrorUtil.checkNotNull(body.getPathFile(), ErrorCodeEnum.BATCH_RPVIAR_FILE_IN_INPUT_NULL);

			String pathFile = body.getPathFile();
			logDebug(sId, methodName, "Path file in input : " + pathFile);

			rpviarServiceTransactionalImpl.batchRipvar(sId, dateBatch, pathFile);

		} catch (RESTException exception) {
			logError(sId, methodName, LogMessageEnum.ECCEZIONE_GENERICA.getMessage() + exception.getMessage(),
					exception);
			logErrorMail(sId, methodName, LogMessageEnum.ECCEZIONE_GENERICA.getMessage() + exception.getMessage(),
					exception);

			throw exception;
		} catch (Exception exception) {
			logError(sId, methodName, LogMessageEnum.ECCEZIONE_GENERICA.getMessage() + exception.getMessage(),
					exception);
			logErrorMail(sId, methodName, LogMessageEnum.ECCEZIONE_GENERICA.getMessage() + exception.getMessage(),
					exception);
			throw exception;
		} finally {
			logDebug(sId, methodName, LogMessageEnum.END.getMessage());
		}
	}

}
