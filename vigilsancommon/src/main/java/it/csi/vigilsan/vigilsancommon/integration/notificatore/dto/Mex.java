/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.integration.notificatore.dto;

public class Mex {
	private String title;
	private String body;
	private String call_to_action;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getCall_to_action() {
		return call_to_action;
	}

	public void setCall_to_action(String call_to_action) {
		this.call_to_action = call_to_action;
	}

}