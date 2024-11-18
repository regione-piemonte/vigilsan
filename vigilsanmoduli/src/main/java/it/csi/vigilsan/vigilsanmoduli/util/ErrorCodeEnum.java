/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.util;

import it.csi.vigilsan.vigilsanutil.generic.services.error.ErrorCodeInterface;
import jakarta.ws.rs.core.Response.Status;

public enum ErrorCodeEnum implements ErrorCodeInterface {

	INTERNAL_SERVER_ERROR("VIGILSANMODULI_1", ErrorMessageEnum.INTERNAL_SERVER_ERROR, Status.INTERNAL_SERVER_ERROR),
	MODULO_ID_OBB("VIGILSANMODULI_2", ErrorMessageEnum.MODULO_ID_OBB),
	MODULO_COMPILATO_ID_OBB("VIGILSANMODULI_3", ErrorMessageEnum.MODULO_COMPILATO_ID_OBB),
	MODULO_NON_TROVATO("VIGILSANMODULI_4", ErrorMessageEnum.MODULO_ASSOCIATO_NON_TROVATO),
	PAYLOAD_OBBLIGATORIO("VIGILSANMODULI_5", ErrorMessageEnum.PAYLOAD_OBBLIGATORIO),
	MODULO_DA_MODIFICARE_CAMBIATO("VIGILSANMODULI_6", ErrorMessageEnum.MODULO_DA_MODIFICARE_CAMBIATO),
	CREAZIONE_DIR_ERRORE("VIGILSANMODULI_7", ErrorMessageEnum.CREAZIONE_DIR_ERRORE, Status.INTERNAL_SERVER_ERROR),
	SALVATAGGIO_FILE_ERRORE("VIGILSANMODULI_8", ErrorMessageEnum.SALVATAGGIO_FILE_ERRORE,
			Status.INTERNAL_SERVER_ERROR),
	FILE_TIPO_NON_TROVATO("VIGILSANMODULI_9", ErrorMessageEnum.FILE_TIPO_NON_TROVATO),
	FILE_NAME_OBBLIGATORIO("VIGILSANMODULI_10", ErrorMessageEnum.PAYLOAD_OBBLIGATORIO),
	ENTE_COD_OBBLIGATORIO("VIGILSANMODULI_11", ErrorMessageEnum.ENTE_COD_OBBLIGATORIO),
	STRUTTURA_COD_OBBLIGATORIO("VIGILSANMODULI_12", ErrorMessageEnum.STRUTTURA_COD_OBBLIGATORIO),
	FILE_TIPO_ID_OBBLIGATORIO("VIGILSANMODULI_13", ErrorMessageEnum.FILE_TIPO_ID_OBBLIGATORIO),
	ERRORE_CHIAMATA_GATEFIRE("VIGILSANMODULI_14", ErrorMessageEnum.ERRORE_CHIAMATA_GATEFIRE, Status.SERVICE_UNAVAILABLE),
	CF_LIST_OBBLIGATORIA("VIGILSANMODULI_15", ErrorMessageEnum.CF_LIST_OBBLIGATORIA),
	FIRMA_NON_VALIDA("VIGILSANMODULI_16", ErrorMessageEnum.FIRMA_NON_VALIDA),
	FIRMA_NON_TROVATA("VIGILSANMODULI_17", ErrorMessageEnum.FIRMA_NON_TROVATA),
	FIRMA_NON_VALIDA_PER_RAPPRESENTATE("VIGILSANMODULI_18", ErrorMessageEnum.FIRMA_NON_VALIDA_PER_RAPPRESENTATE, Status.NOT_ACCEPTABLE),
	FILE_ID_OBBLIGATORIO("VIGILSANMODULI_19", ErrorMessageEnum.FILE_ID_OBBLIGATORIO),
	FILE_NON_TROVATO("VIGILSANMODULI_20", ErrorMessageEnum.FILE_NON_TROVATO),
	FILE_NON_CARICATO_DA_SYSTEM("VIGILSANMODULI_21", ErrorMessageEnum.INTERNAL_SERVER_ERROR, Status.INTERNAL_SERVER_ERROR),
	ERRORE_ENCRYPT_FILE_SECURITY_EXC("VIGILSANMODULI_22", ErrorMessageEnum.SALVATAGGIO_FILE_ERRORE, Status.INTERNAL_SERVER_ERROR),
	ERRORE_ENCRYPT_FILE_IOE_EXC("VIGILSANMODULI_23", ErrorMessageEnum.SALVATAGGIO_FILE_ERRORE, Status.INTERNAL_SERVER_ERROR),
	ERRORE_DECRYPT_FILE_SECURITY_EXC("VIGILSANMODULI_24", ErrorMessageEnum.LETTURA_FILE_ERRORE, Status.INTERNAL_SERVER_ERROR),
	ERRORE_DECRYPT_FILE_IOE_EXC("VIGILSANMODULI_25", ErrorMessageEnum.LETTURA_FILE_ERRORE, Status.INTERNAL_SERVER_ERROR),
	ERRORE_CREAZIONE_OGGETTO_QUERY("VIGILSANMODULI_26", ErrorMessageEnum.ERRORE_CREAZIONE_OGGETTO_QUERY,
			Status.INTERNAL_SERVER_ERROR),
	FILE_FORMATO_SBAGLIATO("VIGILSANMODULI_27", ErrorMessageEnum.FILE_FORMATO_SBAGLIATO),
	FILE_CONTENT_TYPE_NON_TROVATO("VIGILSANMODULI_28", ErrorMessageEnum.FILE_CONTENT_TYPE_NON_TROVATO),
	FILE_CONTENT_TYPE_ID_OBBLIGATORIO("VIGILSANMODULI_29", ErrorMessageEnum.FILE_CONTENT_TYPE_ID_OBBLIGATORIO),
	FILE_CONTENT_TYPE_ID_NON_COERENTE("VIGILSANMODULI_30", ErrorMessageEnum.FILE_CONTENT_TYPE_ID_NON_COERENTE),
	FILE_CONTENT_TYPE_NON_VERIFICABILE("VIGILSANMODULI_31", ErrorMessageEnum.FILE_CONTENT_TYPE_NON_VERIFICABILE),

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