/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.util;

public enum LogMessageEnum {

	BEGIN("BEGIN"),
	END("END"),
	ECCEZIONE_GENERICA("Eccezione generica : "),
	;
	
	private final String message;

	private LogMessageEnum(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}