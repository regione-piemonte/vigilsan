/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanbatchsrv.util;

public enum ErrorMessageEnum {

	INTERNAL_SERVER_ERROR("Internal server error")
	;

	private final String message;

	private ErrorMessageEnum(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}