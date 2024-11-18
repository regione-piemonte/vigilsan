/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.api.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelParametro;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelSoggettoEstesoLista;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.VigilsanCommonGetApiEnum;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.VigilsanCommonGetListApiEnum;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.dto.ModelProfiloUtente;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsanrilevazioni.VigilsanRilevazioniGetApiEnum;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsanrilevazioni.VigilsanRilevazioniGetListApiEnum;
import it.csi.vigilsan.vigilsanfrontendsrv.util.ListaGenericaPaginataWrapper;
import it.csi.vigilsan.vigilsanfrontendsrv.util.OkhttpCallUtils;
import it.csi.vigilsan.vigilsanfrontendsrv.util.ProfiloCodEnum;
import it.csi.vigilsan.vigilsanfrontendsrv.util.RuoloEnteStrutturaCodEnum;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.vigilsancommon.impl.VigilsanCommonService;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.vigilsanrilevazioni.impl.VigilsanRilevazioniService;
import it.csi.vigilsan.vigilsanutil.generic.util.ApiHeaderParamEnum;
import it.csi.vigilsan.vigilsanutil.generic.util.paginazione.PaginazioneUtils;
import jakarta.ws.rs.core.HttpHeaders;
import okhttp3.Headers;

@Service
public class StrutturaServiceImpl {

	@Autowired
	private VigilsanCommonService vigilsanCommonService;

	@Autowired
	private VigilsanRilevazioniService vigilsanRilevazioniService;

	@Autowired
	private ProfiloIdCodCache profiloCod;

	public List<ModelParametro> getParametriStruttura(ModelProfiloUtente profiloUtente, Integer strutturaId,
			HttpHeaders httpHeaders) throws IOException {
		List<ModelParametro> res = new ArrayList<>();
		Integer struttura = (strutturaId == null ? profiloUtente.getStrutturaId() : strutturaId);
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		res.add(getStrutturaParametro(profiloUtente.getSessioneId(), struttura, "FLG_LUNGODEGENZE", headers));
		res.add(getStrutturaParametro(profiloUtente.getSessioneId(), struttura, "FLG_CAVS", headers));
		res.add(getStrutturaParametro(profiloUtente.getSessioneId(), struttura, "FLG_HOSPICE", headers));

		res.add(getDocumnetazioneParametro(profiloUtente.getSessioneId(), struttura, "FLG_SOLLIEVO", headers));
		res.add(getDocumnetazioneParametro(profiloUtente.getSessioneId(), struttura, "FLG_DIMISSIONI_PROTETTE",
				headers));
		return res;
	}

	public ModelParametro getStrutturaParametro(Integer sId, Integer strutturaId, String parametroCod, Headers headers)
			throws IOException {

		var qp = new HashMap<String, Object>();
		OkhttpCallUtils.setQueryParam("struttura_id", strutturaId, qp);
		OkhttpCallUtils.setQueryParam("parametro_cod", parametroCod, qp);
		return vigilsanCommonService.getGenericGet(VigilsanCommonGetApiEnum.GET_STRUTTURA_PARAMETRO, headers, qp, sId);
	}

	public ModelParametro getDocumnetazioneParametro(Integer sId, Integer strutturaId, String parametroCod,
			Headers headers) throws IOException {

		var qp = new HashMap<String, Object>();
		OkhttpCallUtils.setQueryParam("struttura_id", strutturaId, qp);
		OkhttpCallUtils.setQueryParam("parametro_cod", parametroCod, qp);
		return vigilsanRilevazioniService.getGenericGet(VigilsanRilevazioniGetApiEnum.GET_DOCUMENTAZIONE_PARAMETRO,
				headers, qp, sId);
	}

	public List<ModelParametro> getDocumnetazioneParametroLista(Integer sId, Integer strutturaId, String parametroCod,
			Headers headers) throws IOException {

		var qp = new HashMap<String, Object>();
		OkhttpCallUtils.setQueryParam("struttura_id", strutturaId, qp);
		OkhttpCallUtils.setQueryParam("parametro_cod", parametroCod, qp);
		return vigilsanRilevazioniService.getGenericGet(VigilsanRilevazioniGetListApiEnum.GET_DOCUMENTAZIONE_PARAMETRO_LISTA,
				headers, qp, sId);
	}

	public ListaGenericaPaginataWrapper<ModelSoggettoEstesoLista> getStrutturaListaByEnteId(
			ModelProfiloUtente profiloUtente, String filter, Integer strutturaId, Long pageNumber, Long rowPerPage,
			Boolean descending, String orderBy, HttpHeaders httpHeaders) throws IOException, ExecutionException {
		ProfiloCodEnum profiloCorrente = profiloCod.get(profiloUtente, httpHeaders);
		RuoloEnteStrutturaCodEnum ruoloEnteStrutturaCodEnum = profiloCorrente.getRuoloEnteStruttura();
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);

		var qp = new HashMap<String, Object>();
		OkhttpCallUtils.setQueryParam("ente_id", profiloUtente.getEnteId(), qp);
		OkhttpCallUtils.setQueryParam("filter", filter, qp);
		OkhttpCallUtils.setQueryParam("ruolo_ente_struttura_cod",
				Objects.isNull(ruoloEnteStrutturaCodEnum) ? null : ruoloEnteStrutturaCodEnum.getCode(), qp);
		OkhttpCallUtils.setQueryParam("struttura_id", strutturaId, qp);
		PaginazioneUtils.setQueryParamPaginazione(pageNumber, rowPerPage, descending, orderBy, qp);

		List<ModelSoggettoEstesoLista> list = vigilsanCommonService.getGenericGet(
				VigilsanCommonGetListApiEnum.GET_STRUTTURA_LISTA, headers, qp, profiloUtente.getSessioneId());

		return new ListaGenericaPaginataWrapper<>(
				Long.parseLong((String) qp.get(ApiHeaderParamEnum.ROWS_NUMBER.getCode())), list);
	}

}
