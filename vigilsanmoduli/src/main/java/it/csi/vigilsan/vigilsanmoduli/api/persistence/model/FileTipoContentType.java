/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.api.persistence.model;

import java.util.Objects;

import it.csi.vigilsan.vigilsanmoduli.api.generated.dto.ModelGenerico;
import it.csi.vigilsan.vigilsanutil.be.services.persistence.model.GenericColumntInterface;

public class FileTipoContentType extends ModelGenerico implements GenericColumntInterface {
	private Integer fileTipoContentTypeId;
	private Integer fileTipoId;
	private Integer fileContentTypeId;

	public Integer getFileTipoContentTypeId() {
		return fileTipoContentTypeId;
	}

	public void setFileTipoContentTypeId(Integer fileTipoContentTypeId) {
		this.fileTipoContentTypeId = fileTipoContentTypeId;
	}

	public Integer getFileTipoId() {
		return fileTipoId;
	}

	public void setFileTipoId(Integer fileTipoId) {
		this.fileTipoId = fileTipoId;
	}

	public Integer getFileContentTypeId() {
		return fileContentTypeId;
	}

	public void setFileContentTypeId(Integer fileContentTypeId) {
		this.fileContentTypeId = fileContentTypeId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		FileTipoContentType modelFileContentType = (FileTipoContentType) o;
		return  Objects.equals(fileTipoContentTypeId, modelFileContentType.fileTipoContentTypeId)
				&& Objects.equals(fileTipoId, modelFileContentType.fileTipoId)
				&& Objects.equals(fileContentTypeId, modelFileContentType.fileContentTypeId);
	}

}
