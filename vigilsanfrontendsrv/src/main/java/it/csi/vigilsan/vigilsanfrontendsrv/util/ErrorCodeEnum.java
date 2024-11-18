/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.util;

import it.csi.vigilsan.vigilsanutil.generic.services.error.ErrorCodeInterface;
import jakarta.ws.rs.core.Response.Status;

public enum ErrorCodeEnum implements ErrorCodeInterface {


	INTERNAL_SERVER_ERROR("VIGILSANFRNTSRV_1", ErrorMessageEnum.INTERNAL_SERVER_ERROR, Status.INTERNAL_SERVER_ERROR),
	INTERNAL_SERVER_ERROR_TOKEN("VIGILSANFRNTSRV_2", ErrorMessageEnum.INTERNAL_SERVER_ERROR, Status.INTERNAL_SERVER_ERROR),
	IP_NON_VALIDO("VIGILSANFRNTSRV_2", ErrorMessageEnum.IP_NON_VALIDO, Status.UNAUTHORIZED),
	TOKEN_SCADUTO("VIGILSANFRNTSRV_3", ErrorMessageEnum.TOKEN_SCADUTO, Status.UNAUTHORIZED),
	STRUTTURA_ID_NON_VALIDA("VIGILSANFRNTSRV_4", ErrorMessageEnum.STRUTTURA_ID_NON_VALIDA),
	PROFILO_UTENTE_NON_VALIDO("VIGILSANFRNTSRV_5", ErrorMessageEnum.PROFILO_UTENTE_NON_VALIDO, Status.FORBIDDEN),
	X_FORWARDED_FOR_OBBLIGATORIO("VIGILSANFRNTSRV_6", ErrorMessageEnum.X_FORWARDED_FOR_OBBLIGATORIO, Status.FORBIDDEN),
	ERRORE_SERVIZIO_ESTERNO_GESTRUTTURE("VIGILSANFRNTSRV_7", ErrorMessageEnum.ERRORE_SERVIZIO_ESTERNO_GESTRUTTURE, Status.SERVICE_UNAVAILABLE),
	ERRORE_SERVIZIO_ESTERNO_GESTRUTTURE_SPECIFICO("VIGILSANFRNTSRV_8", ErrorMessageEnum.ERRORE_SERVIZIO_ESTERNO_GESTRUTTURE, Status.SERVICE_UNAVAILABLE),
	ERRORE_SERVIZIO_ESTERNO_GESTRUTTURE_GENERICO("VIGILSANFRNTSRV_9", ErrorMessageEnum.ERRORE_SERVIZIO_ESTERNO_GESTRUTTURE, Status.SERVICE_UNAVAILABLE),
	UTENTE_NON_ABILITAT_SERVIZIO_ESTERNO("VIGILSANFRNTSRV_10", ErrorMessageEnum.UTENTE_NON_ABILITAT_SERVIZIO_ESTERNO, Status.FORBIDDEN),
	DATA_FORMATO_SBAGLIATO("VIGILSANFRNTSRV_11", ErrorMessageEnum.DATA_FORMATO_SBAGLIATO, Status.BAD_REQUEST),
	CHIAMATA_NON_PERMESSA_PER_PROFILO("VIGILSANFRNTSRV_12", ErrorMessageEnum.CHIAMATA_NON_PERMESSA_PER_PROFILO, Status.FORBIDDEN),
	ERRORE_VALIDAZIONE_CONFIGURATORE("VIGILSANFRNTSRV_13", ErrorMessageEnum.ERRORE_VALIDAZIONE_CONFIGURATORE, Status.FORBIDDEN),
	NESSUNA_NON_CONFORMITA("VIGILSANFRNTSRV_14", ErrorMessageEnum.NESSUNA_NON_CONFORMITA),
	NON_CONFORMITA_NOTIFICATA("VIGILSANFRNTSRV_15", ErrorMessageEnum.NON_CONFORMITA_NOTIFICATA),
	;

	private final String code;
	private final String message;
	private final Status status;

	private ErrorCodeEnum(String code, ErrorMessageEnum messageEnum) {
		this.code = code;
		this.message = messageEnum.getMessage();
		this.status = Status.BAD_REQUEST;
	}

	ErrorCodeEnum(String code, ErrorMessageEnum messageEnum, Status status) {
		this.code = code;
		this.message = messageEnum.getMessage();
		this.status = status;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public Status getStatus() {
		return status;
	}

}