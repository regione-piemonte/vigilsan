/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.util;

import java.util.List;

public class ListaGenericaPaginataWrapper<T> {
    private Long rowNumbers;
    private List<T> genericObject;

    public ListaGenericaPaginataWrapper(Long rowNumbers, List<T> genericObject) {
        this.rowNumbers = rowNumbers;
        this.setGenericObject(genericObject);
    }

    // Metodi getter e setter per rowNumbers e genericObject
    public Long getRowNumbers() {
        return rowNumbers;
    }

    public void setRowNumbers(Long rowNumbers) {
        this.rowNumbers = rowNumbers;
    }

	public List<T> getGenericObject() {
		return genericObject;
	}

	public void setGenericObject(List<T> genericObject) {
		this.genericObject = genericObject;
	}
}