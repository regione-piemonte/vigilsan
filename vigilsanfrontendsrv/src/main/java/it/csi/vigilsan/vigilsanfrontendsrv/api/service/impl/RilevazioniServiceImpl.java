/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.api.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelDocumentazione;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelDocumentazioneEsteso;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelModuloConfigRidotto;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelModuloDocumentazione;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelModuloRilevazione;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelRilevazione;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelRilevazioneEsteso;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelStrutturaCategoria;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelVerificaDocumentazione;
import it.csi.vigilsan.vigilsanfrontendsrv.api.integration.gestruttureres.impl.GestruttureresServiceStoricoRilevazioni;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.dto.ModelProfiloUtente;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsanrilevazioni.VigilsanRilevazioniGetFileApiEnum;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsanrilevazioni.VigilsanRilevazioniGetListApiEnum;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsanrilevazioni.VigilsanRilevazioniPostApiEnum;
import it.csi.vigilsan.vigilsanfrontendsrv.util.ListaGenericaPaginataWrapper;
import it.csi.vigilsan.vigilsanfrontendsrv.util.OkhttpCallUtils;
import it.csi.vigilsan.vigilsanfrontendsrv.util.ProfiloCodEnum;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.dto.FileCustom;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.vigilsanrilevazioni.impl.VigilsanRilevazioniService;
import it.csi.vigilsan.vigilsanutil.generic.util.ApiHeaderParamEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.core.HttpHeaders;
import okhttp3.Headers;

@Service
public class RilevazioniServiceImpl {

	@Autowired
	private VigilsanRilevazioniService vigilsanRilevazioniService;

	@Autowired
	private ModuliServiceImpl moduliService;

	@Autowired
	private UtenteServiceImpl utenteService;

	@Autowired
	private GestruttureresServiceStoricoRilevazioni gestruttureresServiceStoricoRilevazioni;

	@Autowired
	private ProfiloIdCodCache profiloCod;

	public List<ModelRilevazioneEsteso> getRilevazioneCompilabileLista(Integer strutturaId, Integer sessioneId,
			@NotNull String moduloConfigCod, HttpHeaders httpHeaders) throws IOException {

		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		return getRilevazioneCompilabileLista(strutturaId, sessioneId, moduloConfigCod, headers);
	}

	public List<ModelDocumentazioneEsteso> getDocumentazioneCompilabileLista(Integer strutturaId, Integer sessioneId,
			String moduloConfigCod, HttpHeaders httpHeaders) throws IOException {

		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		return getDocumentazioneCompilabileLista(strutturaId, sessioneId, moduloConfigCod, headers);
	}

	private List<ModelDocumentazioneEsteso> getDocumentazioneCompilabileLista(Integer strutturaId, Integer sId,
			String moduloConfigCod, Headers headers) throws IOException {
		var qp = new HashMap<String, Object>();
		OkhttpCallUtils.setQueryParam("struttura_id", strutturaId, qp);
		OkhttpCallUtils.setQueryParam("modulo_config_cod", moduloConfigCod, qp);
		List<ModelDocumentazioneEsteso> res = vigilsanRilevazioniService.getGenericGet(
				VigilsanRilevazioniGetListApiEnum.GET_DOCUMENTAZIONE_COMPILABILE_LISTA, headers, qp, sId);

		var valori = new HashMap<Integer, ModelStrutturaCategoria>();
		for (ModelDocumentazioneEsteso rilevazione : res) {
			valorizzaStrutturaCategoriaPerDocumentazione(sId, headers, rilevazione, valori);
		}

		return res;
	}

	public List<ModelModuloConfigRidotto> getRilevazioneModuloListaGet(Integer strutturaId, Integer sessioneId,
			String moduloConfigGruppoCod, HttpHeaders httpHeaders) throws IOException {

		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		return getRilevazioneModuloRidottoGetLista(strutturaId, moduloConfigGruppoCod, sessioneId, headers);
	}

	private List<ModelModuloConfigRidotto> getRilevazioneModuloRidottoGetLista(Integer strutturaId,
			String moduloConfigGruppoCod, Integer sId, Headers headers) throws IOException {
		var qp = new HashMap<String, Object>();
		OkhttpCallUtils.setQueryParam("struttura_id", strutturaId, qp);
		OkhttpCallUtils.setQueryParam("modulo_config_gruppo_cod", moduloConfigGruppoCod, qp);
		return vigilsanRilevazioniService.getGenericGet(VigilsanRilevazioniGetListApiEnum.GET_RILEVAZIONE_MODULO_LISTA,
				headers, qp, sId);
	}

	public List<ModelModuloConfigRidotto> getDocumentazioneModuloListaGet(Integer strutturaId, Integer sessioneId,
			String moduloConfigGruppoCod, HttpHeaders httpHeaders) throws IOException {

		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		return getDocumentazioneModuloListaGet(strutturaId, sessioneId, moduloConfigGruppoCod, headers);
	}

	private List<ModelModuloConfigRidotto> getDocumentazioneModuloListaGet(Integer strutturaId, Integer sId,
			String moduloConfigGruppoCod, Headers headers) throws IOException {
		var qp = new HashMap<String, Object>();
		OkhttpCallUtils.setQueryParam("struttura_id", strutturaId, qp);
		OkhttpCallUtils.setQueryParam("modulo_config_gruppo_cod", moduloConfigGruppoCod, qp);
		return vigilsanRilevazioniService
				.getGenericGet(VigilsanRilevazioniGetListApiEnum.GET_DOCUMENTAZIONE_MODULO_LISTA, headers, qp, sId);
	}

	public ListaGenericaPaginataWrapper<ModelRilevazioneEsteso> getRilevazioneCompilataLista(Integer strutturaId,
			Integer sessioneId, String moduloConfigCod, Long pageNumber, Long rowPerPage, HttpHeaders httpHeaders)
			throws IOException {
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		return getRilevazioneCompilataLista(strutturaId, sessioneId, moduloConfigCod, pageNumber, rowPerPage, headers);
	}

	private ListaGenericaPaginataWrapper<ModelRilevazioneEsteso> getRilevazioneCompilataLista(Integer strutturaId,
			Integer sId, String moduloConfigCod, Long pageNumber, Long rowPerPage, Headers headers) throws IOException {
		var qp = new HashMap<String, Object>();
		OkhttpCallUtils.setQueryParam("struttura_id", strutturaId, qp);
		OkhttpCallUtils.setQueryParam("modulo_config_cod", moduloConfigCod, qp);
		OkhttpCallUtils.setQueryParam("page_number", pageNumber, qp);
		OkhttpCallUtils.setQueryParam("row_per_page", rowPerPage, qp);
		List<ModelRilevazioneEsteso> list = vigilsanRilevazioniService
				.getGenericGet(VigilsanRilevazioniGetListApiEnum.GET_RILEVAZIONE_COMPILATA_LISTA, headers, qp, sId);
		// ciclo primo livello che è sempre uguale a 1 o meno
		var valori = new HashMap<Integer, ModelStrutturaCategoria>();
		for (ModelRilevazioneEsteso v : list) {
			// per ogni rilevazione setto il moduloCompilatoRidotto
			v.getModulo().setModuloCompilatoRidotto(moduliService.getModulo(null, v.getModuloCompilatoId(),
					ModuliServiceImpl.IS_RIDOTTO, sId, strutturaId, headers));
			valorizzaStrutturaCategoriaPerRilevazione(sId, headers, v, valori);

		}

		return new ListaGenericaPaginataWrapper<>(
				Long.parseLong((String) qp.get(ApiHeaderParamEnum.ROWS_NUMBER.getCode())), list);
	}

	public ListaGenericaPaginataWrapper<ModelDocumentazioneEsteso> getDocumentazioneCompilataLista(Integer strutturaId,
			Integer sessioneId, String moduloConfigCod, HttpHeaders httpHeaders) throws IOException {
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		return getDocumentazioneCompilataLista(strutturaId, sessioneId, moduloConfigCod, headers);
	}

	private ListaGenericaPaginataWrapper<ModelDocumentazioneEsteso> getDocumentazioneCompilataLista(Integer strutturaId,
			Integer sId, String moduloConfigCod, Headers headers) throws IOException {
		var qp = new HashMap<String, Object>();
		OkhttpCallUtils.setQueryParam("struttura_id", strutturaId, qp);
		OkhttpCallUtils.setQueryParam("modulo_config_cod", moduloConfigCod, qp);

		List<ModelDocumentazioneEsteso> list = vigilsanRilevazioniService
				.getGenericGet(VigilsanRilevazioniGetListApiEnum.GET_DOCUMENTAZIONE_COMPILATA_LISTA, headers, qp, sId);

		var res = new ListaGenericaPaginataWrapper<>(
				Long.parseLong((String) qp.get(ApiHeaderParamEnum.ROWS_NUMBER.getCode())), list);
		var valori = new HashMap<Integer, ModelStrutturaCategoria>();

		for (ModelDocumentazioneEsteso v : list) {
			// per ogni rilevazione setto il moduloCompilatoRidotto
			v.getModulo().setModuloCompilatoRidotto(moduliService.getModulo(null, v.getModuloCompilatoId(),
					ModuliServiceImpl.IS_RIDOTTO, sId, strutturaId, headers));
			valorizzaStrutturaCategoriaPerDocumentazione(sId, headers, v, valori);

		}

		return res;
	}

	public ModelModuloDocumentazione postDocumentazione(ModelModuloDocumentazione body,
			ModelProfiloUtente profiloUtente, HttpHeaders httpHeaders) throws IOException {
		Integer sessioneId = profiloUtente.getSessioneId();
		var res = new ModelModuloDocumentazione();

		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);

		var response = moduliService.postModulo(sessioneId, headers, body.getModuloCompilato());
		res.setModuloCompilato(response);

		body.getDocumentazione().setModuloCompilatoId(response.getModuloCompilatoId());
		var responseDoc = postDocumentazione(sessioneId, headers, body.getDocumentazione());
		res.setDocumentazione(responseDoc);

		return res;
	}

	public ModelModuloRilevazione postRilevazione(ModelModuloRilevazione body, ModelProfiloUtente profiloUtente,
			HttpHeaders httpHeaders) throws IOException {
		Integer sessioneId = profiloUtente.getSessioneId();
		var res = new ModelModuloRilevazione();

		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);

		var response = moduliService.postModulo(sessioneId, headers, body.getModuloCompilato());
		res.setModuloCompilato(response);

		body.getRilevazione().setModuloCompilatoId(response.getModuloCompilatoId());
		var responseDoc = postRilevazione(sessioneId, headers, body.getRilevazione());
		res.setRilevazione(responseDoc);

		return res;
	}

	private ModelRilevazione postRilevazione(Integer sessioneId, Headers headers, ModelRilevazione documentazione)
			throws IOException {
		var qp = new HashMap<String, Object>();
		return vigilsanRilevazioniService.getGenericPost(VigilsanRilevazioniPostApiEnum.POST_RILEVAZIONE,
				documentazione, headers, qp, sessioneId);
	}

	private ModelDocumentazione postDocumentazione(Integer sessioneId, Headers headers,
			ModelDocumentazione documentazione) throws IOException {
		var qp = new HashMap<String, Object>();
		return vigilsanRilevazioniService.getGenericPost(VigilsanRilevazioniPostApiEnum.POST_DOCUMENTAZIONE,
				documentazione, headers, qp, sessioneId);
	}

	private List<ModelRilevazioneEsteso> getRilevazioneCompilabileLista(Integer strutturaId, Integer sId,
			String moduloConfigCod, Headers headers) throws IOException {
		var qp = new HashMap<String, Object>();
		OkhttpCallUtils.setQueryParam("struttura_id", strutturaId, qp);
		OkhttpCallUtils.setQueryParam("modulo_config_cod", moduloConfigCod, qp);
		List<ModelRilevazioneEsteso> res = vigilsanRilevazioniService
				.getGenericGet(VigilsanRilevazioniGetListApiEnum.GET_RILEVAZIONE_COMPILABILE_LISTA, headers, qp, sId);
		var valori = new HashMap<Integer, ModelStrutturaCategoria>();
		for (ModelRilevazioneEsteso rilevazione : res) {
			valorizzaStrutturaCategoriaPerRilevazione(sId, headers, rilevazione, valori);
		}
		return res;
	}

	private void valorizzaStrutturaCategoriaPerRilevazione(Integer sId, Headers headers,
			ModelRilevazioneEsteso rilevazione, HashMap<Integer, ModelStrutturaCategoria> valori) throws IOException {
		var strCatId = rilevazione.getStrCatId();
		if (Objects.nonNull(strCatId)) {
			if (valori.containsKey(strCatId)) {
				rilevazione.setStrutturaCategoria(valori.get(strCatId));
			} else {
				var strutturaCategoria = utenteService.getStrutturaCategoriaFromStrCatId(strCatId, sId, headers);
				valori.put(strCatId, strutturaCategoria);
				rilevazione.setStrutturaCategoria(strutturaCategoria);

			}
		}
	}

	private void valorizzaStrutturaCategoriaPerDocumentazione(Integer sId, Headers headers,
			ModelDocumentazioneEsteso rilevazione, HashMap<Integer, ModelStrutturaCategoria> valori)
			throws IOException {
		var strCatId = rilevazione.getStrCatId();
		if (Objects.nonNull(strCatId)) {
			if (valori.containsKey(strCatId)) {
				rilevazione.setStrutturaCategoria(valori.get(strCatId));
			} else {
				var strutturaCategoria = utenteService.getStrutturaCategoriaFromStrCatId(strCatId, sId, headers);
				valori.put(strCatId, strutturaCategoria);
				rilevazione.setStrutturaCategoria(strutturaCategoria);

			}
		}
	}

	public FileCustom getRilevazioneCompilataListaCsv(ModelProfiloUtente profiloUtente, @NotNull String moduloConfigCod,
			String dataRilevazioneDa, String dataRilevazioneA, String strutturaCategoriaCod, HttpHeaders httpHeaders)
			throws IOException, ExecutionException {
		var qp = new HashMap<String, Object>();

		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		ProfiloCodEnum profiloCorrente = profiloCod.get(profiloUtente, httpHeaders);
		OkhttpCallUtils.setQueryParam("struttura_id", profiloUtente.getStrutturaId(), qp);
		if (profiloUtente.getStrutturaId() != null && !(profiloCorrente.equals(ProfiloCodEnum.VIGIL_DIRMEI)
				|| profiloCorrente.equals(ProfiloCodEnum.VIGIL_REGIONE)
				|| profiloCorrente.equals(ProfiloCodEnum.VIGIL_CSI))) {
			OkhttpCallUtils.setQueryParam("ente_id", profiloUtente.getEnteId(), qp);

		}
		OkhttpCallUtils.setQueryParam("modulo_config_cod", moduloConfigCod, qp);
		OkhttpCallUtils.setQueryParam("data_rilevazione_da", dataRilevazioneDa, qp);
		OkhttpCallUtils.setQueryParam("data_rilevazione_a", dataRilevazioneA, qp);
		OkhttpCallUtils.setQueryParam("struttura_categoria_cod", strutturaCategoriaCod, qp);

		return vigilsanRilevazioniService.getGenericFile(
				VigilsanRilevazioniGetFileApiEnum.GET_RILEVAZIONE_COMPILATA_LISTA_CSV, headers, qp,
				profiloUtente.getSessioneId());
	}

	public FileCustom getRilevazioneStoricoCsv(ModelProfiloUtente profiloUtente, String tipo, String dataRilevazioneDa,
			String dataRilevazioneA, String shibIdentitaCodiceFiscale, String xRequestId, String xForwardedFor,
			HttpHeaders httpHeaders) throws IOException {
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeadersWhitoutAccept(httpHeaders);
		var struttura = utenteService.getStruttura(profiloUtente, profiloUtente.getSessioneId(), headers);
		var soggetto = utenteService.getSoggetto(profiloUtente, profiloUtente.getSessioneId(), headers);

		var qp = new HashMap<String, Object>();

		OkhttpCallUtils.setQueryParam("codiceStrutturaVigilsan", struttura.getStrutturaCodArpe(), qp);
		OkhttpCallUtils.setQueryParam("tipo", tipo, qp);
		OkhttpCallUtils.setQueryParam("dataRilevazioneDa", dataRilevazioneDa, qp);
		OkhttpCallUtils.setQueryParam("dataRilevazioneA", dataRilevazioneA, qp);
		OkhttpCallUtils.setQueryParam("webappOperazione", "COVIDRSA", qp);
		OkhttpCallUtils.setQueryParam("utenteOperazione", soggetto.getCodiceFiscale(), qp);
		return gestruttureresServiceStoricoRilevazioni.getCsvEsterno("vigilsanfrontendsrw", qp,
				profiloUtente.getSessioneId(), shibIdentitaCodiceFiscale, xRequestId, xForwardedFor);
	}

	public Object postDocumentazioneVerifica(ModelVerificaDocumentazione body, ModelProfiloUtente profiloUtente,
			HttpHeaders httpHeaders) throws IOException {
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeadersWhitoutAccept(httpHeaders);
		var qp = new HashMap<String, Object>();
		return vigilsanRilevazioniService.getGenericPost(VigilsanRilevazioniPostApiEnum.POST_DOCUMENTAZIONE_VERIFICA,
				body, headers, qp, profiloUtente.getSessioneId());
	}
}
