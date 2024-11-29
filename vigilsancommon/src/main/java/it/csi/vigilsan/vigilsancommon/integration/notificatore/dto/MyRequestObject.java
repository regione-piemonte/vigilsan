/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.integration.notificatore.dto;

import java.util.Date;

public class MyRequestObject {
	private String uuid;
	private String priority;
	private Boolean trusted;
	private Boolean to_be_retried;
	private Date expire_at;
	private Payload payload;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Boolean getTrusted() {
		return trusted;
	}

	public void setTrusted(Boolean trusted) {
		this.trusted = trusted;
	}

	public Boolean getTo_be_retried() {
		return to_be_retried;
	}

	public void setTo_be_retried(Boolean to_be_retried) {
		this.to_be_retried = to_be_retried;
	}

	public Date getExpire_at() {
		return expire_at;
	}

	public void setExpire_at(Date expire_at) {
		this.expire_at = expire_at;
	}

	public Payload getPayload() {
		return payload;
	}

	public void setPayload(Payload payload) {
		this.payload = payload;
	}

}