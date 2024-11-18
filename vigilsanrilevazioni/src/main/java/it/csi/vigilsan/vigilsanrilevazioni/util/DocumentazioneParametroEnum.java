/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanrilevazioni.util;

import org.springframework.dao.EmptyResultDataAccessException;

import it.csi.vigilsan.vigilsanrilevazioni.api.generated.dto.ModelParametro;
import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.dao.impl.DocumentazioneDao;

public enum DocumentazioneParametroEnum {
	
	FLG_SOLLIEVO("flgSollievo", "resinfo", "resinfo_dati_posti_sollievo IS NOT NULL AND resinfo_dati_posti_sollievo") ,
	FLG_DIMISSIONI_PROTETTE("flgDimissioniProtette", "resinfo", "resinfo_dati_posti_dim_prot is not null");
	
	private final String key;
	private final String moduloCod;
	private final String valore;

	private DocumentazioneParametroEnum(String key, String moduloCod, String valore) {
		this.key = key;
		this.moduloCod = moduloCod;
		this.valore = valore;
	}

	public ModelParametro getResponse(DocumentazioneDao dao, Integer strutturaId) {
		ModelParametro res = new ModelParametro();
		res.setChiave(getKey());
		res.setValore(getParametroBooleanString(dao, strutturaId, getModuloCod(), getValore()));
		return res;
	}

	private static String getParametroBooleanString(DocumentazioneDao dao, Integer strutturaId, String moduloCod,
			String valore) {
		try {
			return dao.getParametroBoolean(strutturaId, moduloCod, valore);
		} catch (EmptyResultDataAccessException e) {
			return "false";
		}
	}

	public String getKey() {
		return key;
	}

	public String getModuloCod() {
		return moduloCod;
	}

	public String getValore() {
		return valore;
	}

}