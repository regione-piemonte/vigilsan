/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.service.integration.configuratore;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import it.csi.vigilsan.vigilsancommon.api.service.integration.configuratore.dto.ErroreConfiguratore;
import it.csi.vigilsan.vigilsancommon.api.service.integration.configuratore.dto.TokenInformazione;
import it.csi.vigilsan.vigilsancommon.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableApiServiceImpl;
import it.csi.vigilsan.vigilsanutil.generic.util.ApiHeaderParamEnum;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

@Service
public class ConfiguratoreService extends AuditableApiServiceImpl {

	private static final String LOGIN_TOKEN_INFORMATION = "v1/login/token-information2";
	private static final String CODICE_SERVIZIO_VALORE = "VIGILSAN";
	private static final String BASIC_AUTH = "Basic";

	private static final String ERRORE_IP_NON_CONGRUENTE = "AUTH_ER_602";
	
	@Value("${configuratore.base.url}")
	private String baseUrl;

	@Value("${configuratore.auth.base64}")
	private String base64;

	public TokenInformazione sendGetTokenInformation(String shibIdentitaCodiceFiscale, String xRequestId,
			String xForwadedFor, String token) throws IOException {
		var methodName = "sendGetTokenInformation";

		var urlBuilder = HttpUrl.parse(baseUrl + LOGIN_TOKEN_INFORMATION).newBuilder();
		var url = urlBuilder.build().toString();

		OkHttpClient client = new OkHttpClient.Builder().build();

		String xForwadedForInHeader = extractXForwadedFor(xForwadedFor);
		var request = new Request.Builder().url(url)
				.addHeader(ApiHeaderParamEnum.SHIB_IDENTITA_CODICEFISCALE.getCode(), shibIdentitaCodiceFiscale)
				.addHeader(ApiHeaderParamEnum.X_REQUEST_ID.getCode(), xRequestId)
				.addHeader(ApiHeaderParamEnum.X_FORWARDED_FOR.getCode(), xForwadedForInHeader)
				.addHeader(ApiHeaderParamEnum.X_CODICE_SERVIZIO.getCode(), CODICE_SERVIZIO_VALORE)
				.addHeader(ApiHeaderParamEnum.TOKEN.getCode(), token)
				.addHeader(ApiHeaderParamEnum.AUTHORIZATION.getCode(), getBasicAuth()).build();
		var call = client.newCall(request);
		try {
			var response = call.execute();
			var gson = new Gson();
			if (response.code() == 200) {
				return gson.fromJson(response.body().string(), TokenInformazione.class);
			} else {
				logError(null, methodName, "errore chiamata esterna per configurate code: " + response.code(), null);

				ErroreConfiguratore errore = gson.fromJson(response.body().string(), ErroreConfiguratore.class);
				logError(null, methodName, "errore chiamata esterna per configurate codice: " + errore.getCodice(),
						null);
				if (ERRORE_IP_NON_CONGRUENTE.equals(errore.getCodice())) {
					throw RESTErrorUtil
							.generateGenericRestException(ErrorCodeEnum.CONFIGURATORE_ERRORE_IP_NON_CONGRUENTE);
				}
				throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.CONFIGURATORE_ERRORE_GENERICO);
			}
		} catch (IOException e) {
			logError(null, methodName, e.getMessage(), e);

			throw e;
		}

	}

	private static String extractXForwadedFor(String xForwadedFor) {
		var result = "";
		if (xForwadedFor.contains(",")) {
			result = xForwadedFor.split(",")[0].trim();
		} else {
			result = xForwadedFor;
		}
		return result;
	}

	private String getBasicAuth() {
		var sb = new StringBuilder();
		sb.append(BASIC_AUTH).append(" ").append(base64);
		return sb.toString();
	}

}
