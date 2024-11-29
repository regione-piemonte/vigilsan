/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.util;

public enum ErrorMessageEnum {

	SOGGETTO_ID_OBBLIGATORIO("soggetto_id obbligatorio"),
	ENTE_ID_OBBLIGATORIO("ente_id obbligatorio"),
	STRUTTURA_ID_OBBLIGATORIO("struttura_id obbligatorio"),
	OSPITE_MOVIMENTO_TIPO_ID_OBBLIGATORIO("ospite_movimento_tipo_id obbligatorio"),
	OSPITE_MOVIMENTO_ID_OBBLIGATORIO("ospite_movimento_id obbligatorio"),
	STATO_OSPITE_NON_CONSISTENTE("movimento non inseribile stato ospite non consistente"),
	STATO_OSPITE_NON_CONSISTENTE_DELETE("movimento non cancellabile stato ospite non consistente"),
	STR_CAT_ID_OBBLIGATORIO("str_cat_id obbligatorio"),
	STRUTTURA_ACCREDITAMENTO_ID_OBBLIGATORIO("struttura_accreditamento_id obbligatorio"),
	STRUTTURA_NATURA_ID_OBBLIGATORIO("struttura_natura_id obbligatorio"),
	APPLICATIVO_ID_OBBLIGATORIO("applicativo_id obbligatorio"),
	PROFILO_ID_OBBLIGATORIO("profilo_id obbligatorio"),
	RUOLO_ID_OBBLIGATORIO("ruolo_id obbligatorio"),
	SOGGETTO_ASSOCIATO_NON_TROVATO("soggetto non esistente"),
	TOKEN_OBBLIGATORIO("token obbligatorio"),
	SHIB_IDENTITA_OBBLIGATORIO("shib_identita obbligatorio"),
	SESSIONE_LOGOUT_GIA_AVVENUTA("logout effettuato in precedenza"),
	SESSION_ID_OBBLIGATORIO("session_id obbligatorio"),
	ERRORE_IP_NON_CONGRUENTE("ip non congruente per servizio esterno"),
	ERRORE_GENERICO_SERVIZIO_ESTERNO("Errore generico servizio esterno"),
	STRUTTURA_NON_TROVATA("struttura associata non trovata"),
	ENTE_NON_TROVATA("ente associato non trovato"),
	ENTE_TIPO_NON_TROVATO("ente tipo associato non trovato"),
	ENTE_TIPO_ID_OBBLIGATORIO("ente_tipo_id obbligatorio"),
	ENTE_SOGG_ID_OBBLIGATORIO("ente_sogg_id obbligatorio"),
	OSPITE_MOVIMENTO_NON_TROVATO("ospite movimento associato non trovato"),
	OSPITE_MOVIMENTO_TIPO_NON_TROVATO("ospite movimento tipo associato non trovato"),
	STRUTTURA_SOGGETTO_NON_TROVATO("struttura soggetto associato non trovato"),
	OSPITE_CONDIZIONI_NON_TROVATA("ospite condizioni associato non trovato"),
	STRUTTURA_NATURA_NON_TROVATA("struttura natura associata non trovato"),
	STRUTTURA_ACCREDITAMENTO_NON_TROVATO("struttura accreditamento associata non trovato"),
	STRUTTURA_TIPO_ID_NON_TROVATO("struttura tipo associata non trovata"),
	STRUTTURA_CATEGORIA_NON_TROVATO("struttura categoria associata non trovata"),
	ASL_NON_TROVATA("asl associata non trovato"),
	APPLICATIVO_NON_TROVATO("applicativo associato non trovato"),
	COLLOCAZIONE_NON_TROVATA("collocazione non trovata"),
	RUOLO_NON_TROVATO("ruolo associato non trovato"),
	SESSIONE_NON_TROVATA("Sessione Associata non trovata"),
	PROFILO_NON_TROVATO("profilo associato non trovato"),
	ERRORE_INSERIMENTO_LOG_SESSION("errore in inserimento log session"),
	ERRORE_LOGOUT_LOG_SESSION("errore in logout log session"),
	ERRORE_UPDATE_LOG_SESSION("errore in update log session"),
	INTERNAL_SERVER_ERROR("Internal server error"),
	ERRORE_CREAZIONE_OGGETTO_QUERY("Errore interno in gestione query"),
	REGIONE_NON_TROVATO("regione associata non trovata"),
	COMUNE_NON_TROVATO("comune associato non trovato"),
	PROVINCIA_NON_TROVATO("provincia associata non trovata"),
	STRUTTURA_ACCREDITAMENTO_NON_TROVATA("struttura natura associata non trovato"),
	STRUTTURA_TIPO_NON_TROVATA("tipo struttura associata non trovata"),
	ASSISTENZA_TIPO_NON_TROVATA("tipo assistenza associata non trovata"),
	ATTIVITA_NON_TROVATA("attivita associata non trovata"),
	ATTIVITA_CLASSE_NON_TROVATA("attivita classe associata non trovata"),
	ERRORE_CANCELLAZIONE_STRUTTURA_DETTAGLIO("errore nella cancellazione della struttura dettaglio"),
	ERRORE_CANCELLAZIONE_STRUTTURA("errore nella cancellazione della struttura"),	
	ERRORE_RECUPERO_DATA("errore nel calcolo della data inizio elaborazione"),
	PAYLOAD_OBBLIGATORIO("payload obbligatorio"),
	CF_OBBLIGATORIO("codice fiscale obbligatorio"), 
	NOME_OBBLIGATORIO("nome obbligatorio"), 
	COGNOME_OBBLIGATORIO("cognome obbligatorio"),
	DATA_NASCITA_OBBLIGATORIO("data di nascita obbligatoria"), 
	AURA_SOGGETTO_NON_TROVATO("soggetto non trovato su aura"), 
	AURA_SOGGETTO_NON_TROVATO_GENERICO("errore in ricerca soggetto su AURA"), 
	DISCIPLINA_NON_TROVATA("disciplina non trovata"),
	STRUTTURA_ID_NON_COERENTE("struttura_id non coerente per operazione"),
	OSPITE_MOVIMENTO_GIA_CANCELLATO("ospite movimento gia' cancellato logicamente"),
	ERRORE_RICALCOLA_STRUTTURA("errore nella function di ricalcola struttura"),
	ENTE_STRUTTURA_NON_TROVATA("ente struttura non trovata"),
	STRUTTURA_TITOLARITA_NON_TROVATA("struttura titolarita non trovata"),
	RUOLO_ENTE_STRUTTURA_NON_TROVATO("ruolo ente struttura non trovato"),
	STRUTTURA_TITOLARITA_OBBLIGATORIA("struttura titolarita id obbligatoria"),
	// Struttura 
	COMUNE_ID_OBBLIGATORIO("comune_id obbligatorio"),
	STRUTTURA_COD_OBBLIGATORIO("struttura_id obbligatorio"),
	STRUTTURA_DESC_OBBLIGATORIO("struttura_desc obbligatorio"),
	STRUTTURA_TIPO_ID_OBBLIGATORIO("struttura_tipo_id obbligatorio"),
	STRUTTURA_DETTAGLIO_NON_TROVATO("struttura_dettaglio non trovata"),
	ERRORE_INSERIMENTO_STRUTTURA("Errore inserimento struttura"),
	ENTE_SOGGETTO_NON_VALIDO("ente soggetto da modificare non valido"), 

	// Struttura natura
	ERRORE_INSERIMENTO_STRUTTURA_NATURA("Errore nella creazione struttura natura"),

	
	// Batch RPVIAR
	BATCH_RPVIAR_FILE_IN_INPUT_NULL("Path file in input null"),
	BATCH_RPVIAR_FILE_NOT_PRESENT("File strutture arpe non presente"),
	BATCH_RPVIAR_NUMERO_DI_CAMPI_INCORRETTO("Numero di campi struttura arpe non corretto"),
	BATCH_RPVIAR_FILE_NOT_READABLE("File strutture arpe non leggibile"), 
	;
	
	private final String message;

	private ErrorMessageEnum(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}