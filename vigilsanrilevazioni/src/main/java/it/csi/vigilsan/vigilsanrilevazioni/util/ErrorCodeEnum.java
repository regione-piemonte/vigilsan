/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanrilevazioni.util;

import it.csi.vigilsan.vigilsanutil.generic.services.error.ErrorCodeInterface;
import jakarta.ws.rs.core.Response.Status;

public enum ErrorCodeEnum implements ErrorCodeInterface {


	INTERNAL_SERVER_ERROR("VIGILSANRILEVAZIONI_1", ErrorMessageEnum.INTERNAL_SERVER_ERROR, Status.INTERNAL_SERVER_ERROR),
	STRUTTURA_ID_OBBLIGATORIA("VIGILSANRILEVAZIONI_2", ErrorMessageEnum.STRUTTURA_ID_OBBLIGATORIA),
	PAYLOAD_OBBLIGATORIO("VIGILSANRILEVAZIONI_3", ErrorMessageEnum.PAYLOAD_OBBLIGATORIO),
	MOD_CONFIG_ID_OBBLIGATORIA("VIGILSANRILEVAZIONI_4", ErrorMessageEnum.MOD_CONFIG_ID_OBBLIGATORIA),
	MOD_CONFIG_NON_TROVATO("VIGILSANRILEVAZIONI_5", ErrorMessageEnum.MOD_CONFIG_NON_TROVATO),
	DATA_ORA_DOCUMENTAZIONE_OBBLIGATORIA("VIGILSANRILEVAZIONI_6", ErrorMessageEnum.DATA_ORA_DOCUMENTAZIONE_OBBLIGATORIA),
	MODULO_COMPILATO_ID_OBBLIGATORIO("VIGILSANRILEVAZIONI_7", ErrorMessageEnum.MODULO_COMPILATO_ID_OBBLIGATORIO),
	DATA_ORA_RILEVAZIONE_OBBLIGATORIA("VIGILSANRILEVAZIONI_8", ErrorMessageEnum.DATA_ORA_RILEVAZIONE_OBBLIGATORIA),
	MODULO_CONFIG_COD_OBBLIGATORIO("VIGILSANRILEVAZIONI_9", ErrorMessageEnum.MODULO_CONFIG_COD_OBBLIGATORIO), 
	ERRORE_CREAZIONE_OGGETTO_QUERY("VIGILSANRILEVAZIONI_10", ErrorMessageEnum.ERRORE_CREAZIONE_OGGETTO_QUERY,
			Status.INTERNAL_SERVER_ERROR),
	VALIDITA_FINE_OBBGLIATORIA("VIGILSANRILEVAZIONI_11", ErrorMessageEnum.VALIDITA_FINE_OBBLIGATORIA),
	VALIDITA_INIZIO_OBBGLIATORIA("VIGILSANRILEVAZIONI_12", ErrorMessageEnum.VALIDITA_INIZIO_OBBLIGATORIA),
	MODULO_CONFIG_COD_NON_COERENTE("VIGILSANRILEVAZIONI_13", ErrorMessageEnum.MODULO_CONFIG_COD_OBBLIGATORIO),
	DOCUMENTAZIONE_ID_OBBLIGATORIO("VIGILSANRILEVAZIONI_14", ErrorMessageEnum.DOCUMENTAZIONE_ID_OBBLIGATORIO), 
	ESITO_VERIFICA_OBBLIGATORIO("VIGILSANRILEVAZIONI_15", ErrorMessageEnum.ESITO_VERIFICA_OBBLIGATORIO), 
	MODULO_CONFIG_GRUPPO_COD_OBBLIGATORIO("VIGILSANRILEVAZIONI_16", ErrorMessageEnum.MODULO_CONFIG_GRUPPO_COD_OBBLIGATORIO), 

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