/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.util;

import it.csi.vigilsan.vigilsanutil.generic.services.error.ErrorCodeInterface;
import jakarta.ws.rs.core.Response.Status;

public enum ErrorCodeEnum implements ErrorCodeInterface {

	INTERNAL_SERVER_ERROR("VIGILSANCOMMON_1", ErrorMessageEnum.INTERNAL_SERVER_ERROR, Status.INTERNAL_SERVER_ERROR),
	SOGGETTOAPI_SOGG_ID_OBB("VIGILSANCOMMON_2", ErrorMessageEnum.SOGGETTO_ID_OBBLIGATORIO),
	SOGGETTOAPI_SOGG_NON_TROVATO("VIGILSANCOMMON_3", ErrorMessageEnum.SOGGETTO_ASSOCIATO_NON_TROVATO,
			Status.BAD_REQUEST),
	TOKEN_OBBLIGATORIO("VIGILSANCOMMON_4", ErrorMessageEnum.TOKEN_OBBLIGATORIO, Status.BAD_REQUEST),
	CONFIGURATORE_ERRORE_IP_NON_CONGRUENTE("VIGILSANCOMMON_5", ErrorMessageEnum.ERRORE_IP_NON_CONGRUENTE,
			Status.SERVICE_UNAVAILABLE),
	CONFIGURATORE_ERRORE_GENERICO("VIGILSANCOMMON_6", ErrorMessageEnum.ERRORE_GENERICO_SERVIZIO_ESTERNO,
			Status.SERVICE_UNAVAILABLE),
	SOGGETTOAPI_SOGG_NON_TROVATO_CF("VIGILSANCOMMON_7", ErrorMessageEnum.SOGGETTO_ASSOCIATO_NON_TROVATO,
			Status.BAD_REQUEST),
	STRUTTURA_NON_TROVATA("VIGILSANCOMMON_8", ErrorMessageEnum.STRUTTURA_NON_TROVATA, Status.BAD_REQUEST),
	ENTE_NON_TROVATO("VIGILSANCOMMON_9", ErrorMessageEnum.ENTE_NON_TROVATA, Status.BAD_REQUEST),
	COLLOCAZIONE_NON_TROVATA("VIGILSANCOMMON_10", ErrorMessageEnum.COLLOCAZIONE_NON_TROVATA,
			Status.SERVICE_UNAVAILABLE),
	RUOLO_NON_TROVATO("VIGILSANCOMMON_11", ErrorMessageEnum.RUOLO_NON_TROVATO, Status.BAD_REQUEST),
	PROFILO_NON_TROVATO("VIGILSANCOMMON_12", ErrorMessageEnum.PROFILO_NON_TROVATO, Status.BAD_REQUEST),
	ERRORE_INSERIMENTO_LOG_SESSION("VIGILSANCOMMON_13", ErrorMessageEnum.ERRORE_INSERIMENTO_LOG_SESSION,
			Status.INTERNAL_SERVER_ERROR),
	ERRORE_UPDATE_LOG_SESSION("VIGILSANCOMMON_14", ErrorMessageEnum.ERRORE_UPDATE_LOG_SESSION,
			Status.INTERNAL_SERVER_ERROR),
	APPLICATIVO_NON_TROVATO("VIGILSANCOMMON_15", ErrorMessageEnum.APPLICATIVO_NON_TROVATO, Status.BAD_REQUEST),
	ENTE_ID_OBBLIGATORIO("VIGILSANCOMMON_16", ErrorMessageEnum.ENTE_ID_OBBLIGATORIO),
	STRUTTURA_ID_OBBLIGATORIO("VIGILSANCOMMON_17", ErrorMessageEnum.STRUTTURA_ID_OBBLIGATORIO),
	APPLICATIVO_ID_OBBLIGATORIO("VIGILSANCOMMON_18", ErrorMessageEnum.APPLICATIVO_ID_OBBLIGATORIO),
	PROFILO_ID_OBBLIGATORIO("VIGILSANCOMMON_19", ErrorMessageEnum.PROFILO_ID_OBBLIGATORIO),
	RUOLO_ID_OBBLIGATORIO("VIGILSANCOMMON_20", ErrorMessageEnum.PROFILO_ID_OBBLIGATORIO),

	COMUNE_ID_OBBLIGATORIO("VIGILSANCOMMON_21", ErrorMessageEnum.COMUNE_ID_OBBLIGATORIO),
	STRUTTURA_COD_OBBLIGATORIO("VIGILSANCOMMON_22", ErrorMessageEnum.STRUTTURA_COD_OBBLIGATORIO),
	STRUTTURA_DESC_OBBLIGATORIO("VIGILSANCOMMON_23", ErrorMessageEnum.STRUTTURA_DESC_OBBLIGATORIO),
	STRUTTURA_TIPO_ID_OBBLIGATORIO("VIGILSANCOMMON_24", ErrorMessageEnum.STRUTTURA_TIPO_ID_OBBLIGATORIO),
	ERRORE_INSERIMENTO_STRUTTURA("VIGILSANCOMMON_25", ErrorMessageEnum.ERRORE_INSERIMENTO_STRUTTURA),

	STRUTTURA_NATURA_NON_TROVATA("VIGILSANCOMMON_27", ErrorMessageEnum.STRUTTURA_NATURA_NON_TROVATA,
			Status.BAD_REQUEST),
	STRUTTURA_NATURA_ID_OBBLIGATORIO("VIGILSANCOMMON_28", ErrorMessageEnum.STRUTTURA_ID_OBBLIGATORIO),
	ERRORE_LOGOUT_LOG_SESSION("VIGILSANCOMMON_29", ErrorMessageEnum.ERRORE_LOGOUT_LOG_SESSION,
			Status.INTERNAL_SERVER_ERROR),
	SESSION_ID_OBBLIGATORIO("VIGILSANCOMMON_30", ErrorMessageEnum.SESSION_ID_OBBLIGATORIO, Status.BAD_REQUEST),
	ERRORE_CREAZIONE_OGGETTO_QUERY("VIGILSANCOMMON_31", ErrorMessageEnum.ERRORE_CREAZIONE_OGGETTO_QUERY,
			Status.INTERNAL_SERVER_ERROR),
	REGIONE_NON_TROVATO("VIGILSANCOMMON_32", ErrorMessageEnum.REGIONE_NON_TROVATO, Status.BAD_REQUEST),
	COMUNE_NON_TROVATO("VIGILSANCOMMON_33", ErrorMessageEnum.COMUNE_NON_TROVATO, Status.BAD_REQUEST),
	PROVINCIA_NON_TROVATO("VIGILSANCOMMON_34", ErrorMessageEnum.PROVINCIA_NON_TROVATO, Status.BAD_REQUEST),
	STRUTTURA_ACCREDITAMENTO_ID_OBBLIGATORIO("VIGILSANCOMMON_35",
			ErrorMessageEnum.STRUTTURA_ACCREDITAMENTO_ID_OBBLIGATORIO),
	STRUTTURA_ACCREDITAMENTO_NON_TROVATO("VIGILSANCOMMON_36",
			ErrorMessageEnum.STRUTTURA_ACCREDITAMENTO_NON_TROVATO, Status.BAD_REQUEST),
	STRUTTURA_TIPO_ID_NON_TROVATO("VIGILSANCOMMON_38", ErrorMessageEnum.STRUTTURA_TIPO_ID_NON_TROVATO,
			Status.BAD_REQUEST),
	STRUTTURA_CATEGORIA_NON_TROVATO("VIGILSANCOMMON_39", ErrorMessageEnum.STRUTTURA_CATEGORIA_NON_TROVATO,
			Status.BAD_REQUEST),
	SESSIONE_NON_TROVATA("VIGILSANCOMMON_40", ErrorMessageEnum.SESSIONE_NON_TROVATA, Status.BAD_REQUEST),
	SESSIONE_LOGOUT_GIA_AVVENUTA("VIGILSANCOMMON_41", ErrorMessageEnum.SESSIONE_LOGOUT_GIA_AVVENUTA, Status.FORBIDDEN),
	ERRORE_INSERIMENTO_STRUTTURA_NATURA("VIGILSANCOMMON_42", ErrorMessageEnum.ERRORE_INSERIMENTO_STRUTTURA_NATURA),
	STRUTTURA_ACCREDITAMENTO_NON_TROVATA("VIGILSANCOMMON_43", ErrorMessageEnum.STRUTTURA_ACCREDITAMENTO_NON_TROVATA,
			Status.BAD_REQUEST),
	STRUTTURA_TIPO_NON_TROVATA("VIGILSANCOMMON_44", ErrorMessageEnum.STRUTTURA_TIPO_NON_TROVATA,
			Status.BAD_REQUEST),
	ASSISTENZA_TIPO_NON_TROVATA("VIGILSANCOMMON_45", ErrorMessageEnum.ASSISTENZA_TIPO_NON_TROVATA,
			Status.BAD_REQUEST),
	ATTIVITA_NON_TROVATA("VIGILSANCOMMON_46", ErrorMessageEnum.ATTIVITA_NON_TROVATA,
			Status.BAD_REQUEST),
	ATTIVITA_CLASSE_NON_TROVATA("VIGILSANCOMMON_47", ErrorMessageEnum.ATTIVITA_CLASSE_NON_TROVATA,
			Status.BAD_REQUEST),
	SHIB_IDENTITA_OBBLIGATORIO("VIGILSANCOMMON_48", ErrorMessageEnum.SHIB_IDENTITA_OBBLIGATORIO, Status.BAD_REQUEST),
	STRUTTURA_DETTAGLIO_NON_TROVATO("VIGILSANCOMMON_49", ErrorMessageEnum.STRUTTURA_DETTAGLIO_NON_TROVATO,
			Status.BAD_REQUEST),
	ERRORE_CANCELLAZIONE_STRUTTURA_DETTAGLIO("VIGILSANCOMMON_50", ErrorMessageEnum.ERRORE_CANCELLAZIONE_STRUTTURA_DETTAGLIO),
	ERRORE_CANCELLAZIONE_STRUTTURA("VIGILSANCOMMON_51", ErrorMessageEnum.ERRORE_CANCELLAZIONE_STRUTTURA),
	ERRORE_RECUPERO_DATA("VIGILSANCOMMON_52", ErrorMessageEnum.ERRORE_RECUPERO_DATA), 
	PAYLOAD_OBBLIGATORIO("VIGILSANCOMMON_53", ErrorMessageEnum.PAYLOAD_OBBLIGATORIO),
	CF_OBBLIGATORIO("VIGILSANCOMMON_54",ErrorMessageEnum.CF_OBBLIGATORIO),
	AURA_SOGGETTO_NON_TROVATO("VIGILSANCOMMON_55",ErrorMessageEnum.AURA_SOGGETTO_NON_TROVATO),
	AURA_ERRORE_SERVIZIO_ESTERNO_GENERICO("VIGILSANCOMMON_56",ErrorMessageEnum.AURA_SOGGETTO_NON_TROVATO_GENERICO, Status.SERVICE_UNAVAILABLE),
	AURA_ERRORE_SERVIZIO_ESTERNO_GENERICO_GESTITO("VIGILSANCOMMON_57",ErrorMessageEnum.AURA_SOGGETTO_NON_TROVATO_GENERICO, Status.SERVICE_UNAVAILABLE),
	STR_CAT_ID_OBBLIGATORIO("VIGILSANCOMMON_58", ErrorMessageEnum.STR_CAT_ID_OBBLIGATORIO),
	OSPITE_MOVIMENTO_TIPO_ID_OBBLIGATORIO("VIGILSANCOMMON_59", ErrorMessageEnum.OSPITE_MOVIMENTO_TIPO_ID_OBBLIGATORIO),
	STATO_OSPITE_NON_CONSISTENTE("VIGILSANCOMMON_60", ErrorMessageEnum.STATO_OSPITE_NON_CONSISTENTE, Status.FORBIDDEN),
	OSPITE_MOVIMENTO_ID_OBBLIGATORIO("VIGILSANCOMMON_61", ErrorMessageEnum.OSPITE_MOVIMENTO_ID_OBBLIGATORIO),
	COLLOCAZIONE_NON_TROVATA_CODICE_NON_VALORIZZATO("VIGILSANCOMMON_62", ErrorMessageEnum.COLLOCAZIONE_NON_TROVATA,
			Status.SERVICE_UNAVAILABLE),
	COLLOCAZIONE_NON_TROVATA_ENTE_NON_TROVATO("VIGILSANCOMMON_63", ErrorMessageEnum.COLLOCAZIONE_NON_TROVATA,
			Status.SERVICE_UNAVAILABLE),
	DISCIPLINA_NON_TROVATA("VIGILSANCOMMON_64", ErrorMessageEnum.DISCIPLINA_NON_TROVATA, Status.BAD_REQUEST),
	STATO_OSPITE_NON_CONSISTENTE_DELETE("VIGILSANCOMMON_65", ErrorMessageEnum.STATO_OSPITE_NON_CONSISTENTE, Status.FORBIDDEN),
	OSPITE_MOVIMENTO_NON_TROVATO("VIGILSANCOMMON_66", ErrorMessageEnum.OSPITE_MOVIMENTO_NON_TROVATO),
	STRUTTURA_SOGGETTO_NON_TROVATO("VIGILSANCOMMON_67", ErrorMessageEnum.STRUTTURA_SOGGETTO_NON_TROVATO),
	STRUTTURA_ID_NON_COERENTE("VIGILSANCOMMON_68", ErrorMessageEnum.STRUTTURA_ID_NON_COERENTE, Status.FORBIDDEN),
	OSPITE_MOVIMENTO_GIA_CANCELLATO("VIGILSANCOMMON_69", ErrorMessageEnum.OSPITE_MOVIMENTO_GIA_CANCELLATO),
	ERRORE_RICALCOLA_STRUTTURA("VIGILSANCOMMON_70", ErrorMessageEnum.ERRORE_RICALCOLA_STRUTTURA),
	OSPITE_CONDIZIONI_NON_TROVATA("VIGILSANCOMMON_71", ErrorMessageEnum.OSPITE_CONDIZIONI_NON_TROVATA),
	OSPITE_MOVIMENTO_TIPO_NON_TROVATO("VIGILSANCOMMON_72", ErrorMessageEnum.OSPITE_MOVIMENTO_TIPO_NON_TROVATO),
	ENTE_STRUTTURA_NON_TROVATA("VIGILSANCOMMON_73", ErrorMessageEnum.ENTE_STRUTTURA_NON_TROVATA, Status.BAD_REQUEST),
	STRUTTURA_TITOLARITA_NON_TROVATA("VIGILSANCOMMON_74", ErrorMessageEnum.STRUTTURA_TITOLARITA_NON_TROVATA,
			Status.BAD_REQUEST),
	RUOLO_ENTE_STRUTTURA_NON_TROVATO("VIGILSANCOMMON_75", ErrorMessageEnum.RUOLO_ENTE_STRUTTURA_NON_TROVATO, Status.BAD_REQUEST),
	STRUTTURA_TITOLARITA_OBBLIGATORIA("VIGILSANCOMMON_76", ErrorMessageEnum.STRUTTURA_TITOLARITA_OBBLIGATORIA, Status.BAD_REQUEST),
	ENTE_TIPO_NON_TROVATO("VIGILSANCOMMON_77", ErrorMessageEnum.ENTE_TIPO_NON_TROVATO, Status.BAD_REQUEST),
	ENTE_TIPO_ID_OBBLIGATORIO("VIGILSANCOMMON_78", ErrorMessageEnum.ENTE_TIPO_ID_OBBLIGATORIO, Status.BAD_REQUEST),
	ENTE_SOGGETTO_NON_VALIDO("VIGILSANCOMMON_79", ErrorMessageEnum.ENTE_SOGGETTO_NON_VALIDO), 
	ENTE_SOGG_ID_OBBLIGATORIO("VIGILSANCOMMON_80", ErrorMessageEnum.ENTE_SOGG_ID_OBBLIGATORIO, Status.BAD_REQUEST),

	
	// Batch RPVIAR
	BATCH_RPVIAR_FILE_IN_INPUT_NULL("BATCHRPVIAR_1", ErrorMessageEnum.BATCH_RPVIAR_FILE_IN_INPUT_NULL),
	BATCH_RPVIAR_FILE_NOT_PRESENT("BATCHRPVIAR_2", ErrorMessageEnum.BATCH_RPVIAR_FILE_NOT_PRESENT),
	BATCH_RPVIAR_NUMERO_DI_CAMPI_INCORRETTO("BATCHRPVIAR_3", ErrorMessageEnum.BATCH_RPVIAR_NUMERO_DI_CAMPI_INCORRETTO),
	BATCH_RPVIAR_FILE_NOT_READABLE("BATCHRPVIAR_4", ErrorMessageEnum.BATCH_RPVIAR_FILE_NOT_READABLE), 
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