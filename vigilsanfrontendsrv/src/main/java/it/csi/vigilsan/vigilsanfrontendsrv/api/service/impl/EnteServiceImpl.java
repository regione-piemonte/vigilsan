/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.api.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelEnteSoggetto;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelEnteSoggettoEsteso;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelRuoloEnteSoggetto;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.VigilsanCommonGetListApiEnum;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.VigilsanCommonPostApiEnum;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.dto.ModelProfiloUtente;
import it.csi.vigilsan.vigilsanfrontendsrv.util.OkhttpCallUtils;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.vigilsancommon.impl.VigilsanCommonService;
import jakarta.ws.rs.core.HttpHeaders;

@Service
public class EnteServiceImpl {

	@Autowired
	private VigilsanCommonService vigilsanCommonService;

	public List<ModelEnteSoggettoEsteso> getEnteSoggettoLista(Integer enteRuoloId, ModelProfiloUtente profiloUtente,
			HttpHeaders httpHeaders) throws IOException {

		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		var qp = new HashMap<String, Object>();
		OkhttpCallUtils.setQueryParam("ente_ruolo_id", enteRuoloId, qp);
		OkhttpCallUtils.setQueryParam("ente_id", profiloUtente.getEnteId(), qp);
		return vigilsanCommonService.getGenericGet(VigilsanCommonGetListApiEnum.GET_ENTE_STRUTTURA_LISTA, headers, qp,
				profiloUtente.getSessioneId());
	}

	public List<ModelRuoloEnteSoggetto> getRuoloEnteSoggettoDecodifica(ModelProfiloUtente profiloUtente,
			HttpHeaders httpHeaders) throws IOException {

		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		var qp = new HashMap<String, Object>();
		return vigilsanCommonService.getGenericGet(
				VigilsanCommonGetListApiEnum.GET_ENTE_STRUTTURA_RUOLO_DECODIFICA_LISTA, headers, qp,
				profiloUtente.getSessioneId());
	}

	public Object postEnteSoggetto(ModelEnteSoggetto body, ModelProfiloUtente profiloUtente, HttpHeaders httpHeaders)
			throws IOException {
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		body.setEnteId(profiloUtente.getEnteId());
		var qp = new HashMap<String, Object>();
		return vigilsanCommonService.getGenericPost(VigilsanCommonPostApiEnum.POST_ENTE_SOGGETTO, body, headers, qp,
				profiloUtente.getSessioneId());
	}

	public Object postInvalidaEnteSoggetto(ModelEnteSoggetto body, ModelProfiloUtente profiloUtente,
			HttpHeaders httpHeaders) throws IOException {
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		var qp = new HashMap<String, Object>();
		return vigilsanCommonService.getGenericPost(VigilsanCommonPostApiEnum.POST_ENTE_SOGGETTO_INVALIDA, body, headers, qp,
				profiloUtente.getSessioneId());
	}

	public Object postCancellaEnteSoggetto(ModelEnteSoggetto body, ModelProfiloUtente profiloUtente,
			HttpHeaders httpHeaders) throws IOException {
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		var qp = new HashMap<String, Object>();
		return vigilsanCommonService.getGenericPost(VigilsanCommonPostApiEnum.POST_ENTE_SOGGETTO_DELETE, body, headers, qp,
				profiloUtente.getSessioneId());
	}

}
