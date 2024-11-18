/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanrilevazioni.util;

public enum ErrorMessageEnum {

	INTERNAL_SERVER_ERROR("Internal server error"),
	STRUTTURA_ID_OBBLIGATORIA("struttura_id obbligatorio"),
	MODULO_CONFIG_COD_OBBLIGATORIO("modulo config cod obbligatorio"),
	MODULO_CONFIG_GRUPPO_COD_OBBLIGATORIO("modulo config gruppo cod obbligatorio"),
	MODULO_CONFIG_COD_NON_COERENTE("modulo config cod non trovato"),
	PAYLOAD_OBBLIGATORIO("Payload obbligatorio"),
	MOD_CONFIG_ID_OBBLIGATORIA("Modulo configurazione id obbligatorio"),
	MOD_CONFIG_NON_TROVATO("Modulo configurazione non trovato"),
	DATA_ORA_DOCUMENTAZIONE_OBBLIGATORIA("Data e ora documentazione obbligatoria"),
	DATA_ORA_RILEVAZIONE_OBBLIGATORIA("Data e ora rilevazione obbligatoria"),
	MODULO_COMPILATO_ID_OBBLIGATORIO("Modulo compilato id obbligatorio"), 
	ERRORE_CREAZIONE_OGGETTO_QUERY("Errore interno in gestione query"),
	VALIDITA_INIZIO_OBBLIGATORIA("validita inizio obbligatoria"),
	VALIDITA_FINE_OBBLIGATORIA("validita fine obbligatoria"), 
	DOCUMENTAZIONE_ID_OBBLIGATORIO("documentazione id obbligatoria"),
	ESITO_VERIFICA_OBBLIGATORIO("esito verifica obbligatoria"),
	;

	private final String message;

	private ErrorMessageEnum(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}