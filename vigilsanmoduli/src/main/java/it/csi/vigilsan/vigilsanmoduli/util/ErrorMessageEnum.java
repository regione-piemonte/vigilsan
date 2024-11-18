/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.util;

public enum ErrorMessageEnum {

	MODULO_ID_OBB("modulo id obbligatorio"),
	FILE_ID_OBBLIGATORIO("file id obbligatorio"),
	MODULO_COMPILATO_ID_OBB("modulo compilato id obbligatorio"),
	MODULO_ASSOCIATO_NON_TROVATO("modulo associato non trovato"),
	FILE_TIPO_NON_TROVATO("file tipo associato non trovato"),
	FILE_NON_TROVATO("file associato non trovato"),
	FILE_CONTENT_TYPE_NON_TROVATO("file content type associato non trovato"),
	PAYLOAD_OBBLIGATORIO("payload obbligatorio"),
	MODULO_DA_MODIFICARE_CAMBIATO("non si puo' modificare l'identita del modulo associato"),
	CREAZIONE_DIR_ERRORE("errore in creazione directory necessarie"),
	SALVATAGGIO_FILE_ERRORE("errore in salvataggio file"),
	LETTURA_FILE_ERRORE("errore in lettura file"),
	INTERNAL_SERVER_ERROR("Internal server error"),
	FILE_NAME_OBBLIGATORIO("nome file obbligatorio"),
	ENTE_COD_OBBLIGATORIO("ente cod obbligatorio"),
	STRUTTURA_COD_OBBLIGATORIO("struttura cod obbligatorio"),
	FILE_TIPO_ID_OBBLIGATORIO("file tipo id obbligatorio"),
	FILE_CONTENT_TYPE_ID_NON_COERENTE("file content type id non coerente"),
	FILE_CONTENT_TYPE_NON_VERIFICABILE("file content type file non verificabile"),
	FILE_CONTENT_TYPE_ID_OBBLIGATORIO("file content type id obbligatorio"),
	CF_LIST_OBBLIGATORIA("lista codici fiscali non valorizzata"),
	ERRORE_CHIAMATA_GATEFIRE("errore in verifica firma"),
	FIRMA_NON_VALIDA("firma non valida per file: %s"),
	FIRMA_NON_TROVATA("firma non trovata per file: %s"),
	FIRMA_NON_VALIDA_PER_RAPPRESENTATE("firma valida ma non del rappresentante legale per file: %s"),
	FILE_FORMATO_SBAGLIATO("file non del formato richiesto: %s"),
	ERRORE_CREAZIONE_OGGETTO_QUERY("Errore interno in gestione query"),
	;
	private final String message;

	private ErrorMessageEnum(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}