/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.util;

import it.csi.vigilsan.vigilsancommon.api.generated.dto.ModelParametro;
import it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl.StrutturaDao;

public enum StrutturaParametroEnum {

	FLG_LUNGODEGENZE("flgLungoDegenze", "SAN_RES_LUNGODEG"),
	FLG_CAVS("flgCavs", "SAN_RES_CAVS"),
	FLG_HOSPICE("flgHospice", "SAN_RES_HOSPICE");

	private final String key;
	private final String strutturaCategoriaCod;

	private StrutturaParametroEnum(String key, String strutturaCategoriaCod) {
		this.key = key;
		this.strutturaCategoriaCod = strutturaCategoriaCod;
	}

	public ModelParametro getResponse(StrutturaDao dao, Integer strutturaId) {
		ModelParametro res = new ModelParametro();
		res.setChiave(getKey());
		res.setValore(getParametroBooleanString(dao, strutturaId, getStrutturaCategoriaCod()));
		return res;
	}

	private static String getParametroBooleanString(StrutturaDao dao, Integer strutturaId, String strutturaCategoriaCod) {
		return dao.getParametroBoolean(strutturaId, strutturaCategoriaCod).toString();
	}

	public String getKey() {
		return key;
	}

	public String getStrutturaCategoriaCod() {
		return strutturaCategoriaCod;
	}
}