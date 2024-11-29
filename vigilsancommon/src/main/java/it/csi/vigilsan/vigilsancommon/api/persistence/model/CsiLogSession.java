/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.persistence.model;

import java.util.Date;

public class CsiLogSession {

	private Integer sessionId;
	private Date sessionLogin;
	private Date sessionLogout;
	private Date sessionExpires;
	private String ipAddress;
	private String cfUtente;
	private Boolean flgAccessoPua;
	private Integer soggettoId;
	private Integer ruoloId;
	private Integer applicativoId;
	private Integer profiloId;
	private Integer strutturaId;
	private Integer enteId;
	
	public Integer getSessionId() {
		return sessionId;
	}
	public void setSessionId(Integer sessionId) {
		this.sessionId = sessionId;
	}
	public Date getSessionLogin() {
		return sessionLogin;
	}
	public void setSessionLogin(Date sessionLogin) {
		this.sessionLogin = sessionLogin;
	}
	public Date getSessionExpires() {
		return sessionExpires;
	}
	public void setSessionExpires(Date sessionExpires) {
		this.sessionExpires = sessionExpires;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getCfUtente() {
		return cfUtente;
	}
	public void setCfUtente(String cfUtente) {
		this.cfUtente = cfUtente;
	}
	public Integer getSoggettoId() {
		return soggettoId;
	}
	public void setSoggettoId(Integer soggettoId) {
		this.soggettoId = soggettoId;
	}
	public Integer getRuoloId() {
		return ruoloId;
	}
	public void setRuoloId(Integer ruoloId) {
		this.ruoloId = ruoloId;
	}
	public Integer getApplicativoId() {
		return applicativoId;
	}
	public void setApplicativoId(Integer applicativoId) {
		this.applicativoId = applicativoId;
	}
	public Integer getProfiloId() {
		return profiloId;
	}
	public void setProfiloId(Integer profiloId) {
		this.profiloId = profiloId;
	}
	public Integer getStrutturaId() {
		return strutturaId;
	}
	public void setStrutturaId(Integer strutturaId) {
		this.strutturaId = strutturaId;
	}
	public Integer getEnteId() {
		return enteId;
	}
	public void setEnteId(Integer enteId) {
		this.enteId = enteId;
	}
	public Boolean getFlgAccessoPua() {
		return flgAccessoPua;
	}
	public void setFlgAccessoPua(Boolean flgAccessoPua) {
		this.flgAccessoPua = flgAccessoPua;
	}
	public Date getSessionLogout() {
		return sessionLogout;
	}
	public void setSessionLogout(Date sessionLogout) {
		this.sessionLogout = sessionLogout;
	}
}
