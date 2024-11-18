/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.util;

import org.apache.commons.lang3.StringUtils;

import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;

public class ApiHeaderUtils {


	private ApiHeaderUtils() {
		super();
	}

	public static String getFrontEndIpCaller(String xForwardedFor) {
		RESTErrorUtil.checkNotNull(xForwardedFor, ErrorCodeEnum.X_FORWARDED_FOR_OBBLIGATORIO);
		return StringUtils.split(xForwardedFor, ",")[0].trim();
	}
}
