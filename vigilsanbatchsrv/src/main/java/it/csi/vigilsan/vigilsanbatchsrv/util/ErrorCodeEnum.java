/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanbatchsrv.util;

import it.csi.vigilsan.vigilsanutil.generic.services.error.ErrorCodeInterface;
import jakarta.ws.rs.core.Response.Status;

public enum ErrorCodeEnum implements ErrorCodeInterface {


	INTERNAL_SERVER_ERROR("VIGILSANBATCHSRV_1", ErrorMessageEnum.INTERNAL_SERVER_ERROR, Status.INTERNAL_SERVER_ERROR),
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