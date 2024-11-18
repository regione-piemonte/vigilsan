/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.util;

public enum ErrorMessageEnum {
	
	INTERNAL_SERVER_ERROR("Internal server error"), 
	ENTE_ID_OBBLIGATORIO("ente_id obbligatorio"), 
	PRATICA_TIPO_ID_OBBLIGATORIO("pratica_tipo_id obbligatoria"), 
	STRUTTURA_TIPO_ID_OBBLIGATORIO("struttura_tipo_id obbligatoria"),
	ERRORE_CREAZIONE_OGGETTO_QUERY("Errore interno in gestione query"), 
	PRATICA_TIPO_NON_TROVATA("pratica tipo non trovata"),
	AZIONE_NON_TROVATA("azione non trovata"),
	ERRORE_INSERIMENTO_PRATICA("errore inserimento pratica"),
	ERRORE_INSERIMENTO_PRATICA_DETTAGLIO("errore inserimento pratica dettaglio"), 
	DATA_A_OBBLIGATORIA("data a obbligatoria"), 
	DATA_DA_OBBLIGATORIA("data da obbligatoria"), PRATICA_ID_OBBLIGATORIA("pratica id obbligatorio"),
	PRATICA_NON_TROVATA("pratica non trovata"), 
	PRATICA_INSERIMENTO_NON_ABILITATA("Utente non abilitato all'inserimento/modifica dell'attività %s"), 
	AZIONE_ATTIVITA_DIVERSA("L'attività svolta non può avere un profilo scadenza diverso dal profilo utente"),
	AZIONE_NON_ABILITATO_INSMOD("Utente non abilitato all'inserimento/modifica della scadenza manuale %s per il profilo %s"),
	AZIONE_ATTIVITA_NON_TROVATA("Attività %s da modificare/cancellare non trovata"),
	AZIONE_NON_ABILITATO_SCADENZE_AUTOMATICHE("Utente non abilitato alla modifica/cancellazione di scadenze automatiche"),
	AZIONE_PROFILO_NON_ABILITATO("L'Attività da modificare/cancellare è stata inserita da utenti con un profilo diverso"),
	PRATICA_TIPO_ID_UGUALE("L'attività %s genera una pratica di tipo non previsto"),
	SECONDA_NUOVA_PRATICA("L'attività %s genererebbe una seconda nuova pratica"),
	PRATICA_ID_NULL("L'attività %s deve essere associata ad una pratica, quando non ne genera una nuova"),
	PRESCRIZIONE_ASSOCIATA_NON_TROVATA("L'attività %s è associata ad una prescrizione sconosciuta"),
	ATTIVITA_SOPRALLUOGO_GENEREREREBBE_PRESCRIZIONE("L'attività %s, associata al sopralluogo %s genererebbe anche una nuova prescrizione"),
	ATTIVITA_SECONDA_PRESCRIZIONE("L'attività %s genererebbe una seconda nuova prescrizione"),
	ATTIVITA_NON_COMPATIBILE_PRESCRIZIONE("Attività %s non compatibile con prescrizione %s chiusa"),
	ATTIVITA_NON_COMPATIBILE_PRESCRIZIONE_STATO("Attività %s non compatibile con prescrizione %s in stato %s"),
	ATTIVITA_SOPRALLUOGO_SCONOSCIUTO("L'attività %s associata ad un sopralluogo sconosciuto"),
	ATTIVITA_GENERA_UN_SOPRALLUOGO("L'attività %s, associata alla prescrizione %s genererebbe anche un nuovo sopralluogo"),
	ATTIVITA_GENERA_SECONDO_SOPRALLUOGO("L'attività %s genererebbe un secondo nuovo sopralluogo"), 
	ATTIVITA_GENERA_SOPRALLUOGO_NO_ORA_INIZIO("L'attività %s genererebbe un nuovo sopralluogo, ma manca la data/ora inizio"),
	ATTIVITA_GENERA_SOPRALLUOGO_NO_ORA_FINE("L'attività %s genererebbe un nuovo sopralluogo, ma manca la data/ora fine"),
	ATTIVITA_GENERA_SOPRALLUOGO_DIVERSO("L'attività %s genera un sopralluogo %s diverso da quella a cui è associato %s"),
	ATTIVITA_ASSOCITANA_NON_APERTA("L'attività %s è associata al sopralluogo %s che non risulta ancora aperto"),
	SOPRALLUOGO_NON_COMPATIBILE("Sopralluogo %s non compatibile con pratica %s in stato %s"),
	ATTIVITA_NON_COMATIBILE_CON_SOPRALLUOGO("Attività %s non compatibile con sopralluogo %s chiuso"),
	ATTIVITA_NON_COMATIBILE_CON_SOPRALLUOGO_STATO("Attività %s non compatibile con sopralluogo %s in stato %s"),
	ATTIVITA_NON_COMPATIBILE_PRATICA_CHIUSA("Attività %s non compatibile con pratica %s chiusa"),
	ATTIVITA_NON_COMPATIBILE_PRATICA_STATO("Attività %s non compatibile con pratica %s in stato %s"),
	ATTIVITA_NON_PUO_CAMBIARE_STATO("L'attività %s non può cambiare lo stato della pratica quando c'è una prescrizione %s aperta"),
	ATTIVITA_PRATICA_CON_SOPRALLUOGO("L'attività %s non può cambiare lo stato della pratica quando c'è un sopralluogo %s aperto"),
	ATTIVITA_GENERA_PRESCRIZIONE_DIVERSA("L'attività %s genera una prescrizione %s diversa da quella a cui è associata %s"),
	ATTIVITA_NON_ASSOCIATA_PRESCRIZIONE("L'attività %s è associata alla prescrizione %s che non risulta ancora aperta"),
	PRESCRIZIONE_NON_COMPATIBILE("Prescrizione %s non compatibile con pratica %s in stato %s"), 
	PRATICA_TIPO_ID_NON_TROVATA("pratica_id non trovata"),
	PRATICA_DETTAGLIO_NON_TROVATA("pratica_dettaglio non trovata"), 
	AZIONE_ID_NON_TROVATA("azione_id non trovata"), 
	AZIONE_INIZIALE_NON_TROVATA("azione iniziale non trovata"),
	DATA_ORA_AZIONE_NON_TROVATA("dataora_azione non trovata"),
	UTENTE_NON_ABILITATO_PER_SCADENZE_AUTOMATICHE("Utente non abilitato all'inserimento/modifica/cancellazione di scadenze automatiche"), 
	FLAG_SCADENZA_NON_TROVATO("flag_scadenza non trovato"), 
	PRATICA_DET_ID_OBBLIGATORIO("pratica_det_id obbligatorio"),
	PAYLOAD_OBBLIGATORIO("payload obbligatorio"),
	APPUNTAMENTO_ID_OBBLIGATORIO("appuntamento id obbligatorio"),
	SOGGETTO_ID_OBBLIGATORIO("soggetto id obbligatorio"),
	APPUNTAMENTO_SOGGETTO_ID_OBBLIGATORIO("appuntamento soggeto id obbligatorio"),
	UTENTE_NON_ABILITATO_INSERIMENTO("Utente non abilitato all'inserimento/modifica dell'attività %s"),
	ATTIVITA_GENEREREBBE_NUOVA_PRESCRIIONE("L'attività %s genererebbe una seconda nuova prescrizione"),
	DATA_ORA_AZIONE_NEL_FUTURO("Data ora azione nel futuro"),
	APPUNTAMENTO_SOGGETTO_NON_VALIDO("appuntamento sggetto non valido"),
	APPUNTAMENTO_SOGGETTO_ASSOCIAZIONE_NON_VALIDA("appuntamento soggetto associazione non valida"), 
	APPUNTAMENTO_SOGGETTO_ASSOCIAZIONE_ESISTENTE("appuntamento soggetto gia associato"), 
	CLREQ_ID_OBBLIGATORIO("clreqId obbligatorio")
	;
	
	private final String message;

	private ErrorMessageEnum(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}