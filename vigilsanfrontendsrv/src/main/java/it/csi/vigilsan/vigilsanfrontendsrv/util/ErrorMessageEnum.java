/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.util;

public enum ErrorMessageEnum {

	INTERNAL_SERVER_ERROR("Internal server error"),
	IP_NON_VALIDO("Ip non valido"),
	TOKEN_SCADUTO("token scaduto da troppo tempo"),
	STRUTTURA_ID_NON_VALIDA("struttura id  non valida"),
	PROFILO_UTENTE_NON_VALIDO("Profilo id selezionato non valido per utente corrente"),
	X_FORWARDED_FOR_OBBLIGATORIO("x-forwarded-for obbligatorio"),
	ERRORE_SERVIZIO_ESTERNO_GESTRUTTURE("errore in servizio esterno gestruttureres"),
	UTENTE_NON_ABILITAT_SERVIZIO_ESTERNO("utente non abilitato a servizio esterno"),
	DATA_FORMATO_SBAGLIATO("Data %s non ha il formato corretto"), 
	CHIAMATA_NON_PERMESSA_PER_PROFILO("chiamata non abilitata a profilo corrente"), 
	ERRORE_VALIDAZIONE_CONFIGURATORE("Errore nella configurazione del profilo su configuratore"), 
	NESSUNA_NON_CONFORMITA("Nessuna non conformità da notificare"),
	NON_CONFORMITA_NOTIFICATA("Non conformità gia notificata/e")

	;

	private final String message;

	private ErrorMessageEnum(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}