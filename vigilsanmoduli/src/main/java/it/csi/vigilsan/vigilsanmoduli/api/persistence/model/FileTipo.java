/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.api.persistence.model;

import it.csi.vigilsan.vigilsanutil.be.services.persistence.model.GenericTablePojo;

public class FileTipo extends GenericTablePojo {

	private Integer fileTipoId;
	private String fileTipoCod;
	private String fileTipoDesc;
	private String fileTipoOrd;
	private String fileTipoHint;
	private Boolean fileTipoObbligatorio;
	private Boolean fileTipoFirmaRichiesta;
	private Integer fileGruppoId;
	
	public Integer getFileTipoId() {
		return fileTipoId;
	}
	public void setFileTipoId(Integer fileTipoId) {
		this.fileTipoId = fileTipoId;
	}
	public String getFileTipoCod() {
		return fileTipoCod;
	}
	public void setFileTipoCod(String fileTipoCod) {
		this.fileTipoCod = fileTipoCod;
	}
	public String getFileTipoDesc() {
		return fileTipoDesc;
	}
	public void setFileTipoDesc(String fileTipoDesc) {
		this.fileTipoDesc = fileTipoDesc;
	}
	public String getFileTipoOrd() {
		return fileTipoOrd;
	}
	public void setFileTipoOrd(String fileTipoOrd) {
		this.fileTipoOrd = fileTipoOrd;
	}
	public String getFileTipoHint() {
		return fileTipoHint;
	}
	public void setFileTipoHint(String fileTipoHint) {
		this.fileTipoHint = fileTipoHint;
	}
	public Boolean getFileTipoObbligatorio() {
		return fileTipoObbligatorio;
	}
	public void setFileTipoObbligatorio(Boolean fileTipoObbligatorio) {
		this.fileTipoObbligatorio = fileTipoObbligatorio;
	}
	public Boolean getFileTipoFirmaRichiesta() {
		return fileTipoFirmaRichiesta;
	}
	public void setFileTipoFirmaRichiesta(Boolean fileTipoFirmaRichiesta) {
		this.fileTipoFirmaRichiesta = fileTipoFirmaRichiesta;
	}
	public Integer getFileGruppoId() {
		return fileGruppoId;
	}
	public void setFileGruppoId(Integer fileGruppoId) {
		this.fileGruppoId = fileGruppoId;
	}
	
}
