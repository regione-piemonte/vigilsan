/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.integration.notificatore.dto;

public class Payload {
	private String id;
	private String user_id;
	private String bulk_id;
	private String correlation_id;
	private String applicazione;
	private String ruolo;
	private String collocazione;
	private String tag;
	private Push push;
	private Email email;
	private Sms sms;
	private Mex mex;
	private Memo memo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getBulk_id() {
		return bulk_id;
	}

	public void setBulk_id(String bulk_id) {
		this.bulk_id = bulk_id;
	}

	public String getCorrelation_id() {
		return correlation_id;
	}

	public void setCorrelation_id(String correlation_id) {
		this.correlation_id = correlation_id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Push getPush() {
		return push;
	}

	public void setPush(Push push) {
		this.push = push;
	}

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public Sms getSms() {
		return sms;
	}

	public void setSms(Sms sms) {
		this.sms = sms;
	}

	public Mex getMex() {
		return mex;
	}

	public void setMex(Mex mex) {
		this.mex = mex;
	}

	public Memo getMemo() {
		return memo;
	}

	public void setMemo(Memo memo) {
		this.memo = memo;
	}

	public String getApplicazione() {
		return applicazione;
	}

	public void setApplicazione(String applicazione) {
		this.applicazione = applicazione;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public String getCollocazione() {
		return collocazione;
	}

	public void setCollocazione(String collocazione) {
		this.collocazione = collocazione;
	}

}