/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.util;

import java.util.Map;

import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MultivaluedMap;
import okhttp3.Headers;

public class OkhttpCallUtils {

	public static void setQueryParam(String key, Object value, Map<String, Object> queryParam) {
		if (value != null) {
			queryParam.put(key, value);
		}
	}

	public static Headers convertJakartaToOkHttpHeaders(HttpHeaders jakartaHeaders) {
		MultivaluedMap<String, String> headerMap = jakartaHeaders.getRequestHeaders();

		// Convert MultiValuedMap to OkHttp Headers
		var okhttpHeadersBuilder = new Headers.Builder();
		headerMap.keySet().forEach(key -> {
			headerMap.get(key).forEach(value -> okhttpHeadersBuilder.add(key, value));
		});
		return okhttpHeadersBuilder.build();
	}

	public static Headers convertJakartaToOkHttpHeadersWhitoutAccept(HttpHeaders jakartaHeaders) {
		MultivaluedMap<String, String> headerMap = jakartaHeaders.getRequestHeaders();

		// Convert MultiValuedMap to OkHttp Headers
		var okhttpHeadersBuilder = new Headers.Builder();
		headerMap.keySet().forEach(key -> {
			if (!key.equalsIgnoreCase("accept"))
				headerMap.get(key).forEach(value -> okhttpHeadersBuilder.add(key, value));
		});
		return okhttpHeadersBuilder.build();
	}

	private OkhttpCallUtils() {
		throw new IllegalStateException("Utility class");
	}
}
