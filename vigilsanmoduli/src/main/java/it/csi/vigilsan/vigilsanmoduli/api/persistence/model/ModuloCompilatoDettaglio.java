/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.api.persistence.model;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.model.GenericColumntInterface;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.model.GenericTablePojo;

public class ModuloCompilatoDettaglio extends GenericTablePojo implements GenericColumntInterface
{

	private Integer moduloCompilatoDettaglioId;
	private String valore;
	private String note;
	private Integer moduloCompilatoId;
	private Integer moduloVoceId;
	private Integer moduloListaValoreId;
	private Boolean flgCheck;
	private Integer fileId;
	
	public Integer getModuloCompilatoDettaglioId() {
		return moduloCompilatoDettaglioId;
	}
	public void setModuloCompilatoDettaglioId(Integer moduloCompilatoDettaglioId) {
		this.moduloCompilatoDettaglioId = moduloCompilatoDettaglioId;
	}
	public String getValore() {
		return valore;
	}
	public void setValore(String valore) {
		this.valore = valore;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Integer getModuloCompilatoId() {
		return moduloCompilatoId;
	}
	public void setModuloCompilatoId(Integer moduloCompilatoId) {
		this.moduloCompilatoId = moduloCompilatoId;
	}
	public Integer getModuloVoceId() {
		return moduloVoceId;
	}
	public void setModuloVoceId(Integer moduloVoceId) {
		this.moduloVoceId = moduloVoceId;
	}
	public Integer getModuloListaValoreId() {
		return moduloListaValoreId;
	}
	public void setModuloListaValoreId(Integer moduloListaValoreId) {
		this.moduloListaValoreId = moduloListaValoreId;
	}
	public Integer getFileId() {
		return fileId;
	}
	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}
	public Boolean getFlgCheck() {
		return flgCheck;
	}
	public void setFlgCheck(Boolean flgCheck) {
		this.flgCheck = flgCheck;
	}
}
