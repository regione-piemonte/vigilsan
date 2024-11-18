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

import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelOspiteCondizioni;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelOspiteEsteso;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelOspiteMovimento;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelOspiteMovimentoPost;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelOspiteMovimentoTipoEsteso;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelOspiteStato;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelSoggetto;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelSoggettoEstesoLista;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.VigilsanCommonGetApiEnum;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.VigilsanCommonGetFileApiEnum;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.VigilsanCommonGetListApiEnum;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.VigilsanCommonPostApiEnum;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.dto.ModelProfiloUtente;
import it.csi.vigilsan.vigilsanfrontendsrv.util.ListaGenericaPaginataWrapper;
import it.csi.vigilsan.vigilsanfrontendsrv.util.OkhttpCallUtils;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.dto.FileCustom;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.vigilsancommon.impl.VigilsanCommonService;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableApiServiceImpl;
import it.csi.vigilsan.vigilsanutil.generic.util.ApiHeaderParamEnum;
import it.csi.vigilsan.vigilsanutil.generic.util.paginazione.PaginazioneUtils;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.core.HttpHeaders;
import okhttp3.Headers;

@Service
public class OspiteServiceImpl extends AuditableApiServiceImpl {

	private static final String RUOLO_STRUTTURA_COD_OSPITE = "OSPITE";

	@Autowired
	private VigilsanCommonService vigilsanCommonService;

	public ModelSoggetto postSoggetto(ModelSoggetto body, ModelProfiloUtente profiloUtente, HttpHeaders httpHeaders)
			throws IOException {
		Integer sessioneId = profiloUtente.getSessioneId();
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);

		return postSoggetto(sessioneId, headers, body);
	}

	private ModelSoggetto postSoggetto(Integer sessioneId, Headers headers, ModelSoggetto bodyModulo)
			throws IOException {
		var qp = new HashMap<String, Object>();
		return vigilsanCommonService.getGenericPost(VigilsanCommonPostApiEnum.POST_SOGGETTO, bodyModulo, headers, qp,
				sessioneId);
	}

	public ListaGenericaPaginataWrapper<ModelSoggettoEstesoLista> getOspiteLista(ModelProfiloUtente profiloUtente,
			String filter, Integer ospiteStatoId, String dataIngressoDa, String dataIngressoA, String dataUscitaDa,
			String dataUscitaA, Long pageNumber, Long rowPerPage, Boolean descending, String orderBy,
			HttpHeaders httpHeaders) throws IOException {
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		return getOspiteLista(profiloUtente.getStrutturaId(), profiloUtente.getSessioneId(), filter, ospiteStatoId,
				dataIngressoDa, dataIngressoA, dataUscitaDa, dataUscitaA, pageNumber, rowPerPage, descending, orderBy,
				headers);
	}

	private ListaGenericaPaginataWrapper<ModelSoggettoEstesoLista> getOspiteLista(Integer strutturaId, Integer sId,
			String filter, Integer ospiteStatoId, String dataIngressoDa, String dataIngressoA, String dataUscitaDa,
			String dataUscitaA, Long pageNumber, Long rowPerPage, Boolean descending, String orderBy, Headers headers)
			throws IOException {
		var qp = new HashMap<String, Object>();

		OkhttpCallUtils.setQueryParam("struttura_id", strutturaId, qp);
		OkhttpCallUtils.setQueryParam("filter", filter, qp);
		OkhttpCallUtils.setQueryParam("ruolo_struttura_cod", RUOLO_STRUTTURA_COD_OSPITE, qp);
		OkhttpCallUtils.setQueryParam("ospite_stato_id", ospiteStatoId, qp);
		OkhttpCallUtils.setQueryParam("data_ingresso_da", dataIngressoDa, qp);
		OkhttpCallUtils.setQueryParam("data_ingresso_a", dataIngressoA, qp);
		OkhttpCallUtils.setQueryParam("data_uscita_da", dataUscitaDa, qp);
		OkhttpCallUtils.setQueryParam("data_uscita_a", dataUscitaA, qp);
		PaginazioneUtils.setQueryParamPaginazione(pageNumber, rowPerPage, descending, orderBy, qp);
		List<ModelSoggettoEstesoLista> list = vigilsanCommonService
				.getGenericGet(VigilsanCommonGetListApiEnum.GET_SOGGETTO_LISTA, headers, qp, sId);

		return new ListaGenericaPaginataWrapper<>(
				Long.parseLong((String) qp.get(ApiHeaderParamEnum.ROWS_NUMBER.getCode())), list);
	}

	public FileCustom getOspiteListaCsv(ModelProfiloUtente profiloUtente, String filter, Integer ospiteStatoId,
			String dataIngressoDa, String dataIngressoA, String dataUscitaDa, String dataUscitaA,
			HttpHeaders httpHeaders) throws IOException {
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		return getOspiteListaCsv(profiloUtente.getStrutturaId(), profiloUtente.getSessioneId(), filter, ospiteStatoId,
				dataIngressoDa, dataIngressoA, dataUscitaDa, dataUscitaA, headers);
	}

	private FileCustom getOspiteListaCsv(Integer strutturaId, Integer sId, String filter, Integer ospiteStatoId,
			String dataIngressoDa, String dataIngressoA, String dataUscitaDa, String dataUscitaA, Headers headers)
			throws IOException {
		var qp = new HashMap<String, Object>();

		OkhttpCallUtils.setQueryParam("struttura_id", strutturaId, qp);
		OkhttpCallUtils.setQueryParam("filter", filter, qp);
		OkhttpCallUtils.setQueryParam("ruolo_struttura_cod", RUOLO_STRUTTURA_COD_OSPITE, qp);
		OkhttpCallUtils.setQueryParam("ospite_stato_id", ospiteStatoId, qp);
		OkhttpCallUtils.setQueryParam("data_ingresso_da", dataIngressoDa, qp);
		OkhttpCallUtils.setQueryParam("data_ingresso_a", dataIngressoA, qp);
		OkhttpCallUtils.setQueryParam("data_uscita_da", dataUscitaDa, qp);
		OkhttpCallUtils.setQueryParam("data_uscita_a", dataUscitaA, qp);
		return vigilsanCommonService.getGenericFile(VigilsanCommonGetFileApiEnum.GET_SOGGETTO_LISTA_CSV, headers, qp,
				sId);
	}

	public List<ModelOspiteStato> getOspiteStatoLista(Integer sId, HttpHeaders httpHeaders) throws IOException {

		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		var qp = new HashMap<String, Object>();
		return vigilsanCommonService.getGenericGet(VigilsanCommonGetListApiEnum.GET_OSPITE_STATO_LISTA, headers, qp,
				sId);
	}

	public List<ModelOspiteCondizioni> getOspiteCondizioniLista(Integer sId, HttpHeaders httpHeaders)
			throws IOException {

		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		var qp = new HashMap<String, Object>();
		return vigilsanCommonService.getGenericGet(VigilsanCommonGetListApiEnum.GET_OSPITE_CONDIZIONI_LISTA, headers,
				qp, sId);
	}

	public List<ModelOspiteMovimentoTipoEsteso> getOspiteMovimentoTipoLista(Integer sId, HttpHeaders httpHeaders)
			throws IOException {

		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		var qp = new HashMap<String, Object>();
		return vigilsanCommonService.getGenericGet(VigilsanCommonGetListApiEnum.GET_OSPITE_MOVIMENTO_TIPO_LISTA,
				headers, qp, sId);
	}

	public ModelOspiteMovimento postMovimento(ModelOspiteMovimentoPost body, ModelProfiloUtente profiloUtente,
			HttpHeaders httpHeaders) throws IOException {
		Integer sessioneId = profiloUtente.getSessioneId();
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);

		return postMovimento(sessioneId, headers, body, profiloUtente.getStrutturaId());
	}

	private ModelOspiteMovimento postMovimento(Integer sessioneId, Headers headers, ModelOspiteMovimentoPost bodyModulo,
			Integer strutturaId) throws IOException {
		var qp = new HashMap<String, Object>();
		OkhttpCallUtils.setQueryParam("struttura_id", strutturaId, qp);

		return vigilsanCommonService.getGenericPost(VigilsanCommonPostApiEnum.POST_OSPITE_MOVIMENTO, bodyModulo,
				headers, qp, sessioneId);
	}

	public void deleteMovimento(Integer id, ModelProfiloUtente profiloUtente, HttpHeaders httpHeaders)
			throws IOException {
		Integer sessioneId = profiloUtente.getSessioneId();
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);

		ModelOspiteMovimento movimento = new ModelOspiteMovimento();
		movimento.setOspiteMovimentoId(id);
		deleteMovimento(sessioneId, headers, movimento, profiloUtente.getStrutturaId());
	}

	private ModelOspiteMovimento deleteMovimento(Integer sessioneId, Headers headers, ModelOspiteMovimento bodyModulo,
			Integer strutturaId) throws IOException {
		var qp = new HashMap<String, Object>();
		OkhttpCallUtils.setQueryParam("struttura_id", strutturaId, qp);

		return vigilsanCommonService.getGenericPost(VigilsanCommonPostApiEnum.POST_OSPITE_MOVIMENTO_DELETE, bodyModulo,
				headers, qp, sessioneId);
	}

	public ModelOspiteEsteso getOspite(ModelProfiloUtente profiloUtente, @NotNull Integer soggettoId,
			HttpHeaders httpHeaders) throws IOException {
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		var qp = new HashMap<String, Object>();
		OkhttpCallUtils.setQueryParam("soggetto_id", soggettoId, qp);
		OkhttpCallUtils.setQueryParam("struttura_id", profiloUtente.getStrutturaId(), qp);

		ModelOspiteEsteso res = vigilsanCommonService.getGenericGet(VigilsanCommonGetApiEnum.GET_OSPITE, headers, qp,
				profiloUtente.getSessioneId());

		res.setOspiteMovimento(vigilsanCommonService.getGenericGet(
				VigilsanCommonGetListApiEnum.GET_OSPITE_MOVIMENTO_LISTA, headers, qp, profiloUtente.getStrutturaId()));
		return res;
	}

}
