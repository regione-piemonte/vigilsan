/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.integration.notificatore.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import it.csi.vigilsan.vigilsancommon.integration.notificatore.dto.MyRequestObject;
import it.csi.vigilsan.vigilsancommon.integration.notificatore.dto.MyResponseObject;

@Service
public class NotificatoreApiClient {

	private WebClient webClient;

	@Autowired
	public NotificatoreApiClient(WebClient webClient) {
		this.webClient = webClient;
	}

	@Value("${notificatore.path}")
	private String basePath;

	@Value("${notificatore.auth}")
	private String jwtAuth;

	public MyResponseObject sendPostRequest(MyRequestObject requestObject) {
		return webClient.post().uri(basePath + "/v1/topics/messages").contentType(MediaType.APPLICATION_JSON)
				.header("x-authentication", jwtAuth).bodyValue(requestObject).retrieve()
				.bodyToMono(MyResponseObject.class).block();
	}

	public String sendPostRequestToJsonString(MyRequestObject requestObject) {
		return webClient.post().uri(basePath + "/v1/topics/messages").contentType(MediaType.APPLICATION_JSON)
				.header("x-authentication", jwtAuth).bodyValue(requestObject).retrieve().bodyToMono(String.class)
				.block();
	}
	public String sendPostRequestToJsonString(List<MyRequestObject> requestObject) {
		return webClient.post().uri(basePath + "/v1/topics/messages").contentType(MediaType.APPLICATION_JSON)
				.header("x-authentication", jwtAuth).bodyValue(requestObject).retrieve().bodyToMono(String.class)
				.block();
	}
}
