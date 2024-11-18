/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.api.persistence.model;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.model.GenericColumntInterface;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.model.GenericTablePojo;

public class ModuloCompilato extends GenericTablePojo implements GenericColumntInterface
{

	private Integer moduloCompilatoId;
	private Integer moduloId;
	private String note;
	
	public Integer getModuloCompilatoId() {
		return moduloCompilatoId;
	}
	public void setModuloCompilatoId(Integer moduloCompilatoId) {
		this.moduloCompilatoId = moduloCompilatoId;
	}
	public Integer getModuloId() {
		return moduloId;
	}
	public void setModuloId(Integer moduloId) {
		this.moduloId = moduloId;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	
}
