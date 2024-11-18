/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.api.integration.gestruttureres.impl;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import it.csi.vigilsan.vigilsanfrontendsrv.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.api.generated.dto.Errore;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.dto.FileCustom;
import it.csi.vigilsan.vigilsanutil.generic.services.error.ErroreBuilder;
import it.csi.vigilsan.vigilsanutil.generic.services.error.GenericErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTException;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableApiServiceImpl;
import it.csi.vigilsan.vigilsanutil.generic.util.ApiHeaderParamEnum;
import okhttp3.Credentials;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class GestruttureresServiceStoricoRilevazioni extends AuditableApiServiceImpl {

	@Value("${gestruttureres.path}")
	protected String path;

	@Value("${gestruttureres.connection.timeout:10}")
	protected Long timeoutInternalRestCallSeconds;

	@Value("${gestruttureres.user.auth}")
	protected String user;

	@Value("${gestruttureres.pwd.auth}")
	protected String psw;

	public FileCustom getCsvEsterno(String applicazioneCodice, Map<String, Object> queryParam, Integer sessionId,
			String shibIdentita, String xRequestId, String xForwardedFor) {
		var response = genericGet(applicazioneCodice, queryParam, sessionId, shibIdentita, xRequestId, xForwardedFor);
		var fileCustom = new FileCustom();

		fileCustom.setContentDisposition(response.header(ApiHeaderParamEnum.CONTENT_DISPOSITION.getCode()));
		fileCustom.setContentType(response.header(ApiHeaderParamEnum.CONTENT_TYPE.getCode()));
		if (Objects.nonNull(response.header(ApiHeaderParamEnum.CONTENT_LENGTH.getCode()))) {
			fileCustom.setContentLength(Integer.valueOf(response.header(ApiHeaderParamEnum.CONTENT_LENGTH.getCode())));
			var body = response.body();
			if (body != null) {
				fileCustom.setFile(body.byteStream());
			} else {
				throw RESTErrorUtil.generateGenericRestException(GenericErrorCodeEnum.ERRORE_GET_FILE);
			}
		}

		return fileCustom;
	}

	protected Response genericGet(String applicazioneCodice, Map<String, Object> queryParam, Integer sessionId,
			String shibIdentita, String xRequestId, String xForwardedFor) {
		var methodName = "genericGet";

		var urlBuilder = HttpUrl.parse(path).newBuilder();
		if (queryParam != null) {
			for (Map.Entry<String, Object> param : queryParam.entrySet()) {
				urlBuilder.addQueryParameter(param.getKey(), param.getValue().toString());
			}
		}

		var url = urlBuilder.build().toString();

		OkHttpClient client = new OkHttpClient.Builder().readTimeout(timeoutInternalRestCallSeconds, TimeUnit.SECONDS)
				.build();

		// Imposta le credenziali per l'autenticazione Basic
		String credentials = Credentials.basic(user, psw);

		var request = new Request.Builder().url(url).header("Shib-Identita-CodiceFiscale", shibIdentita)
				.header("X-Request-Id", xRequestId).header("X-Forwarded-For", xForwardedFor)
				.header("X-Applicazione-Codice", applicazioneCodice).header("Authorization", "Bearer " + credentials)
				.build();

		var call = client.newCall(request);
		try {
			var response = call.execute();

			if (response.code() == 403) {
				throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.UTENTE_NON_ABILITAT_SERVIZIO_ESTERNO);
			} else if (response.code() != 200) {
				logError(sessionId, methodName, "response: " + response.code() + " body: "
						+ (Objects.isNull(response.body()) ? null : response.body().toString()), null);

				throw RESTErrorUtil
						.generateGenericRestException(ErrorCodeEnum.ERRORE_SERVIZIO_ESTERNO_GESTRUTTURE_SPECIFICO);
			}

			return response;
		} catch (RESTException e) {
			throw e;
		} catch (IOException e) {
			logError(sessionId, methodName, "IOException: " + e.getMessage(), e);

			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.ERRORE_SERVIZIO_ESTERNO_GESTRUTTURE);
		} catch (Exception e) {
			logError(sessionId, methodName, e.getMessage(), e);

			throw RESTErrorUtil
					.generateGenericRestException(ErrorCodeEnum.ERRORE_SERVIZIO_ESTERNO_GESTRUTTURE_GENERICO);
		}

	}

	protected void gestioneErrore(Integer sessionId, String methodName, Response response) throws IOException {
		var errore = new Gson().fromJson(response.body().string(), Errore.class);
		logError(sessionId, methodName, "errore: " + (Objects.nonNull(errore) ? errore.toString() : null), null);
		throw ErroreBuilder.from(errore).exception();
		// TODO: incapsula in generico errore. SE NON VA A BUON FINE IL MAPPING
	}
}