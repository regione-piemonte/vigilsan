/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.api.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelAppuntamentoSoggetto;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelAppuntamentoSoggettoEsteso;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelAzioniEstese;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelChiaveValore;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelChiaveValoreList;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelFile;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelFileContentType;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelFilePostBff;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelModuloPost;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelParametro;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelPraticaClreqPost;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelPraticaDettaglioClreq;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelPraticaDettaglioClreqEsteso;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelPraticaEstesa;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelPraticaForPost;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelPraticaPost;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelPraticaRequisiti;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelPraticaRidottaEstesa;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelPraticaScadenza;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelStrutturaCategoria;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelZip;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelZipLista;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.dto.ModelProfiloUtente;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsanpratiche.VigilsanPraticheGetApiEnum;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsanpratiche.VigilsanPraticheGetListApiEnum;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsanpratiche.VigilsanPratichePostApiEnum;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsanpratiche.VigilsanPratichePostFileApiEnum;
import it.csi.vigilsan.vigilsanfrontendsrv.util.ListaGenericaPaginataWrapper;
import it.csi.vigilsan.vigilsanfrontendsrv.util.OkhttpCallUtils;
import it.csi.vigilsan.vigilsanfrontendsrv.util.ProfiloCodEnum;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.dto.FileCustom;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.vigilsanpratiche.VigilsanPraticheService;
import it.csi.vigilsan.vigilsanutil.generic.util.ApiHeaderParamEnum;
import it.csi.vigilsan.vigilsanutil.generic.util.FileUtils;
import it.csi.vigilsan.vigilsanutil.generic.util.paginazione.PaginazioneUtils;
import jakarta.ws.rs.core.HttpHeaders;
import okhttp3.Headers;

@Service
public class PraticheServiceImpl {

	@Autowired
	private VigilsanPraticheService vigilsanPraticheService;

	@Autowired
	private UtenteServiceImpl utenteService;

	@Autowired
	private ModuliServiceImpl moduliService;

	@Autowired
	private ProfiloIdCodCache profiloCod;

	@Autowired
	private StrutturaServiceImpl strutturaService;

	public Object getAppuntamentoStato(ModelProfiloUtente profiloUtente, HttpHeaders httpHeaders) throws IOException {
		var qp = new HashMap<String, Object>();
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		return vigilsanPraticheService.getGenericGet(VigilsanPraticheGetListApiEnum.GET_APPUNTAMENTO_STATO_LISTA,
				headers, qp, profiloUtente.getSessioneId());
	}

	public Object getAppuntamentoTipo(ModelProfiloUtente profiloUtente, HttpHeaders httpHeaders) throws IOException {
		var qp = new HashMap<String, Object>();
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		return vigilsanPraticheService.getGenericGet(VigilsanPraticheGetListApiEnum.GET_APPUNTAMENTO_TIPO_LISTA,
				headers, qp, profiloUtente.getSessioneId());
	}

	public Object getPraticaStato(ModelProfiloUtente profiloUtente, HttpHeaders httpHeaders) throws IOException {
		var qp = new HashMap<String, Object>();
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		return vigilsanPraticheService.getGenericGet(VigilsanPraticheGetListApiEnum.GET_PRATICA_STATO_LISTA, headers,
				qp, profiloUtente.getSessioneId());
	}

	public Object getPraticaTipo(ModelProfiloUtente profiloUtente, HttpHeaders httpHeaders) throws IOException {
		var qp = new HashMap<String, Object>();
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		return vigilsanPraticheService.getGenericGet(VigilsanPraticheGetListApiEnum.GET_PRATICA_TIPO_LISTA, headers, qp,
				profiloUtente.getSessioneId());
	}

	public Object getPrescrizioneStato(ModelProfiloUtente profiloUtente, HttpHeaders httpHeaders) throws IOException {
		var qp = new HashMap<String, Object>();
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		return vigilsanPraticheService.getGenericGet(VigilsanPraticheGetListApiEnum.GET_PRESCRIZIONE_STATO_LISTA,
				headers, qp, profiloUtente.getSessioneId());
	}

	public Object getPrescrizioneTipo(ModelProfiloUtente profiloUtente, HttpHeaders httpHeaders) throws IOException {
		var qp = new HashMap<String, Object>();
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		return vigilsanPraticheService.getGenericGet(VigilsanPraticheGetListApiEnum.GET_PRESCRIZIONE_TIPO_LISTA,
				headers, qp, profiloUtente.getSessioneId());
	}

	public Object getNuovapraticaPraticaTipo(ModelProfiloUtente profiloUtente, HttpHeaders httpHeaders)
			throws IOException {
		var qp = new HashMap<String, Object>();
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
//		var ente = utenteService.getEnte(profiloUtente.getEnteId(), profiloUtente.getSessioneId(), headers);
//		OkhttpCallUtils.setQueryParam("ente_tipo_id", ente.getEnteTipoId(), qp);
		OkhttpCallUtils.setQueryParam("ente_tipo_id", 2, qp);
		return vigilsanPraticheService.getGenericGet(VigilsanPraticheGetListApiEnum.GET_NUOVAPRATICA_PRAICA_TIPO_LISTA,
				headers, qp, profiloUtente.getSessioneId());
	}

	public Object getNuovapraticaStrutturaTipo(ModelProfiloUtente profiloUtente, HttpHeaders httpHeaders)
			throws IOException {
		var qp = new HashMap<String, Object>();
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
//		var ente = utenteService.getEnte(profiloUtente.getEnteId(), profiloUtente.getSessioneId(), headers);
//		OkhttpCallUtils.setQueryParam("ente_tipo_id", ente.getEnteTipoId(), qp);
		OkhttpCallUtils.setQueryParam("ente_tipo_id", 2, qp);
		return vigilsanPraticheService.getGenericGet(
				VigilsanPraticheGetListApiEnum.GET_NUOVAPRATICA_STRUTTURA_TIPO_LISTA, headers, qp,
				profiloUtente.getSessioneId());
	}

	public ListaGenericaPaginataWrapper<ModelPraticaRidottaEstesa> getPraticaInseribileLista(
			ModelProfiloUtente profiloUtente, Integer praticaTipoId, Integer strutturaTipoId, String dataChiusuraMax,
			String filterStruttura, Long pageNumber, Long rowPerPage, Boolean descending, String orderBy,
			HttpHeaders httpHeaders) throws IOException {
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		var qp = new HashMap<String, Object>();

		OkhttpCallUtils.setQueryParam("ente_id", profiloUtente.getEnteId(), qp);
		OkhttpCallUtils.setQueryParam("profilo_id", profiloUtente.getProfiloId(), qp);
		OkhttpCallUtils.setQueryParam("pratica_tipo_id", praticaTipoId, qp);
		OkhttpCallUtils.setQueryParam("struttura_tipo_id", strutturaTipoId, qp);
		OkhttpCallUtils.setQueryParam("data_chiusura_max", dataChiusuraMax, qp);
		OkhttpCallUtils.setQueryParam("filter_struttura", filterStruttura, qp);
		PaginazioneUtils.setQueryParamPaginazione(pageNumber, rowPerPage, descending, orderBy, qp);
		List<ModelPraticaRidottaEstesa> list = vigilsanPraticheService.getGenericGet(
				VigilsanPraticheGetListApiEnum.GET_PRATICA_INSERIBILI_LISTA, headers, qp,
				profiloUtente.getSessioneId());

		for (ModelPraticaRidottaEstesa l : list) {
			l.setStruttura(utenteService.getStruttura(l.getStrutturaId(), profiloUtente.getSessioneId(), headers));
			l.setEnte(utenteService.getEnte(l.getEnteId(), profiloUtente.getSessioneId(), headers));
		}
		return new ListaGenericaPaginataWrapper<>(
				Long.parseLong((String) qp.get(ApiHeaderParamEnum.ROWS_NUMBER.getCode())), list);
	}

	public ModelPraticaPost postPratica(ModelPraticaForPost body, ModelProfiloUtente profiloUtente,
			HttpHeaders httpHeaders) throws IOException {
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);

		if (body.getModuloCompilato() != null) {
			ModelModuloPost response = moduliService.postModulo(profiloUtente.getSessioneId(), headers,
					body.getModuloCompilato());
			body.getPraticaDettaglio().setModuloCompilatoId(response.getModuloCompilatoId());
		}
		body.setModuloCompilato(null);
		var qp = new HashMap<String, Object>();
		OkhttpCallUtils.setQueryParam("ente_id", profiloUtente.getEnteId(), qp);
		OkhttpCallUtils.setQueryParam("profilo_id", profiloUtente.getProfiloId(), qp);

		ModelPraticaPost res = vigilsanPraticheService.getGenericPost(VigilsanPratichePostApiEnum.POST_PRATICA, body,
				headers, qp, profiloUtente.getSessioneId());

		return res;
	}

	public ModelPraticaPost postPraticaModulo(ModelPraticaForPost body, ModelProfiloUtente profiloUtente,
			HttpHeaders httpHeaders) throws IOException {
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);

		ModelModuloPost response = moduliService.postModulo(profiloUtente.getSessioneId(), headers,
				body.getModuloCompilato());
		body.getPraticaDettaglio().setModuloCompilatoId(response.getModuloCompilatoId());

		body.setModuloCompilato(null);
		var qp = new HashMap<String, Object>();
		OkhttpCallUtils.setQueryParam("ente_id", profiloUtente.getEnteId(), qp);
		OkhttpCallUtils.setQueryParam("profilo_id", profiloUtente.getProfiloId(), qp);

		ModelPraticaPost res = vigilsanPraticheService.getGenericPost(VigilsanPratichePostApiEnum.POST_PRATICA_MODULO,
				body, headers, qp, profiloUtente.getSessioneId());

		return res;
	}

	public ListaGenericaPaginataWrapper<ModelPraticaEstesa> getPraticaLista(ModelProfiloUtente profiloUtente,
			Integer tipoPraticaId, Integer statoPraticaId, Integer tipoPrescrizioneId, Integer statoPrescrizioneId,
			String dataAperturaPrescrizioneDa, String dataAperturaPrescrizioneA, String dataChiusuraPrescrizioneDa,
			String dataChiusuraPrescrizioneA, Integer tipoAppuntamentoId, Integer statoAppuntamentoId,
			String dataInizioAppuntamentoDa, String dataInizioAppuntamentoA, String dataFineAppuntamentoDa,
			String dataFineAppuntamentoA, String dataPraticaAperturaDa, String dataPraticaAperturaA,
			String dataPraticaChiusuraDa, String dataPraticaChiusuraA, Long pageNumber, Long rowPerPage,
			Boolean descending, String orderBy, String filterStruttura, String filterUtente, HttpHeaders httpHeaders)
			throws ExecutionException, IOException {
		var qp = new HashMap<String, Object>();
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		OkhttpCallUtils.setQueryParam("profilo_id", profiloUtente.getProfiloId(), qp);
		OkhttpCallUtils.setQueryParam("tipo_pratica_id", tipoPraticaId, qp);
		OkhttpCallUtils.setQueryParam("stato_pratica_id", statoPraticaId, qp);
		OkhttpCallUtils.setQueryParam("tipo_prescrizione_id", tipoPrescrizioneId, qp);
		OkhttpCallUtils.setQueryParam("stato_prescrizione_id", statoPrescrizioneId, qp);
		OkhttpCallUtils.setQueryParam("data_apertura_prescrizione_da", dataAperturaPrescrizioneDa, qp);
		OkhttpCallUtils.setQueryParam("data_apertura_prescrizione_a", dataAperturaPrescrizioneA, qp);
		OkhttpCallUtils.setQueryParam("data_chiusura_prescrizione_da", dataChiusuraPrescrizioneDa, qp);
		OkhttpCallUtils.setQueryParam("data_chiusura_prescrizione_a", dataChiusuraPrescrizioneA, qp);
		OkhttpCallUtils.setQueryParam("tipo_appuntamento_id", tipoAppuntamentoId, qp);
		OkhttpCallUtils.setQueryParam("stato_appuntamento_id", statoAppuntamentoId, qp);
		OkhttpCallUtils.setQueryParam("data_inizio_appuntamento_da", dataInizioAppuntamentoDa, qp);
		OkhttpCallUtils.setQueryParam("data_inizio_appuntamento_a", dataInizioAppuntamentoA, qp);
		OkhttpCallUtils.setQueryParam("data_fine_appuntamento_da", dataFineAppuntamentoDa, qp);
		OkhttpCallUtils.setQueryParam("data_fine_appuntamento_a", dataFineAppuntamentoA, qp);

		OkhttpCallUtils.setQueryParam("data_pratica_apertura_da", dataPraticaAperturaDa, qp);
		OkhttpCallUtils.setQueryParam("data_pratica_apertura_a", dataPraticaAperturaA, qp);
		OkhttpCallUtils.setQueryParam("data_pratica_chiusura_da", dataPraticaChiusuraDa, qp);
		OkhttpCallUtils.setQueryParam("data_pratica_chiusura_a", dataPraticaChiusuraA, qp);
		PaginazioneUtils.setQueryParamPaginazione(pageNumber, rowPerPage, descending, orderBy, qp);

		OkhttpCallUtils.setQueryParam("filter_struttura", filterStruttura, qp);
		OkhttpCallUtils.setQueryParam("filter_utente", filterUtente, qp);
		ProfiloCodEnum profiloCorrente = profiloCod.get(profiloUtente, httpHeaders);
		if (!(profiloCorrente.equals(ProfiloCodEnum.VIGIL_DIRMEI)
				|| profiloCorrente.equals(ProfiloCodEnum.VIGIL_REGIONE)
				|| profiloCorrente.equals(ProfiloCodEnum.VIGIL_CSI))) {
			if (Objects.isNull(profiloUtente.getEnteId())) {
				OkhttpCallUtils.setQueryParam("struttura_id", profiloUtente.getStrutturaId(), qp);
			} else {
				OkhttpCallUtils.setQueryParam("ente_id", profiloUtente.getEnteId(), qp);
			}
		}
		List<ModelPraticaEstesa> list = vigilsanPraticheService.getGenericGet(
				VigilsanPraticheGetListApiEnum.GET_PRATICA_LISTA, headers, qp, profiloUtente.getSessioneId());

		for (ModelPraticaEstesa p : list) {
			p.setStruttura(utenteService.getStrutturaDue(p.getStrutturaId(), profiloUtente.getSessioneId(), headers));

		}
		return new ListaGenericaPaginataWrapper<>(
				Long.parseLong((String) qp.get(ApiHeaderParamEnum.ROWS_NUMBER.getCode())), list);
	}

	public ModelAzioniEstese getazioni(ModelProfiloUtente profiloUtente, Integer praticaTipoId, HttpHeaders httpHeaders)
			throws IOException {
		var qp = new HashMap<String, Object>();
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		OkhttpCallUtils.setQueryParam("pratica_tipo_id", praticaTipoId, qp);
		OkhttpCallUtils.setQueryParam("profilo_id", profiloUtente.getProfiloId(), qp);

		return vigilsanPraticheService.getGenericGet(VigilsanPraticheGetApiEnum.GET_PRATICA_AZIONE, headers, qp,
				profiloUtente.getSessioneId());
	}

	public List<ModelPraticaScadenza> getPraticaDettaglioScadenze(ModelProfiloUtente profiloUtente, String dataDa,
			String dataA, HttpHeaders httpHeaders) throws IOException, ExecutionException {
		var qp = new HashMap<String, Object>();
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);

		ProfiloCodEnum profiloCorrente = profiloCod.get(profiloUtente, httpHeaders);

		if (profiloCorrente.getConoVisiblita() != null) {
			OkhttpCallUtils.setQueryParam("ente_id", profiloUtente.getEnteId(), qp);
		}
		OkhttpCallUtils.setQueryParam("struttura_id", profiloUtente.getStrutturaId(), qp);
		OkhttpCallUtils.setQueryParam("profilo_id", profiloUtente.getProfiloId(), qp);
		OkhttpCallUtils.setQueryParam("data_da", dataDa, qp);
		OkhttpCallUtils.setQueryParam("data_a", dataA, qp);

		List<ModelPraticaScadenza> res = vigilsanPraticheService.getGenericGet(
				VigilsanPraticheGetListApiEnum.GET_PRATICA_DETTAGLIO_SCADENZE, headers, qp,
				profiloUtente.getSessioneId());
//		for (var r : res) {
//			r.setStruttura(utenteService.getStruttura(r.getPratica().getStrutturaId(), profiloUtente.getSessioneId(),
//					headers));
//		}

		return res;
	}

	public ModelPraticaEstesa getPratica(ModelProfiloUtente profiloUtente, Integer praticaId, HttpHeaders httpHeaders)
			throws IOException {

		var qp = new HashMap<String, Object>();
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		OkhttpCallUtils.setQueryParam("ente_id", profiloUtente.getEnteId(), qp);
		OkhttpCallUtils.setQueryParam("struttura_id", profiloUtente.getStrutturaId(), qp);
		OkhttpCallUtils.setQueryParam("profilo_id", profiloUtente.getProfiloId(), qp);
		OkhttpCallUtils.setQueryParam("pratica_id", praticaId, qp);

		ModelPraticaEstesa res = vigilsanPraticheService.getGenericGet(VigilsanPraticheGetApiEnum.GET_PRATICA, headers,
				qp, profiloUtente.getSessioneId());

		res.setStruttura(
				utenteService.getStrutturaEstesa(res.getStrutturaId(), profiloUtente.getSessioneId(), headers));
		res.setEnte(utenteService.getEnteEsteso(res.getEnteId(), profiloUtente.getSessioneId(), headers));
		return res;

	}

	public Object getParametri(ModelProfiloUtente profiloUtente, Integer strutturaId, String code,
			HttpHeaders httpHeaders) throws IOException {
		List<ModelParametro> res = new ArrayList<>();
		Integer struttura = (strutturaId == null ? profiloUtente.getStrutturaId() : strutturaId);
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		res = strutturaService.getDocumnetazioneParametroLista(profiloUtente.getSessioneId(), struttura, code, headers);
		return res;
	}

	public Object getAppuntamentoSoggettoLista(ModelProfiloUtente profiloUtente, Integer appuntamentoId,
			HttpHeaders httpHeaders) throws IOException {
		var qp = new HashMap<String, Object>();
		OkhttpCallUtils.setQueryParam("appuntamento_id", appuntamentoId, qp);
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		List<ModelAppuntamentoSoggettoEsteso> res = vigilsanPraticheService.getGenericGet(
				VigilsanPraticheGetListApiEnum.GET_APPUNTAMENTO_SOGGETTO_LISTA, headers, qp,
				profiloUtente.getSessioneId());
		for (ModelAppuntamentoSoggettoEsteso r : res) {
			r.setSoggetto(utenteService.getSoggetto(r.getSoggettoId(), appuntamentoId, headers));
		}

		return res;
	}

	public Object getRuoloAppuntamentoSoggettoDecodifica(ModelProfiloUtente profiloUtente, HttpHeaders httpHeaders)
			throws IOException {
		var qp = new HashMap<String, Object>();
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		return vigilsanPraticheService.getGenericGet(
				VigilsanPraticheGetListApiEnum.GET_APPUNTAMENTO_SOGGETTO_RUOLO_DECODIFICA_LISTA, headers, qp,
				profiloUtente.getSessioneId());
	}

	public Object postAppuntamentoSoggetto(ModelAppuntamentoSoggetto body, ModelProfiloUtente profiloUtente,
			HttpHeaders httpHeaders) throws IOException {
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		var qp = new HashMap<String, Object>();
		OkhttpCallUtils.setQueryParam("ente_id", profiloUtente.getEnteId(), qp);
		OkhttpCallUtils.setQueryParam("profilo_id", profiloUtente.getProfiloId(), qp);

		ModelAppuntamentoSoggetto res = vigilsanPraticheService.getGenericPost(
				VigilsanPratichePostApiEnum.POST_APPUNTAMENTO_SOGGETTO, body, headers, qp,
				profiloUtente.getSessioneId());

		return res;
	}

	public Object postAppuntamentoSoggettoDelete(ModelAppuntamentoSoggetto body, ModelProfiloUtente profiloUtente,
			HttpHeaders httpHeaders) throws IOException {
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);

		var qp = new HashMap<String, Object>();

		return vigilsanPraticheService.getGenericPost(VigilsanPratichePostApiEnum.POST_APPUNTAMENTO_SOGGETTO_DELETE,
				body, headers, qp, profiloUtente.getSessioneId());
	}

	public FileCustom getPraticaZip(ModelProfiloUtente profiloUtente, Integer praticaId, String filtroData, HttpHeaders httpHeaders)
			throws IOException {
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeadersWhitoutAccept(httpHeaders);
		var qp = new HashMap<String, Object>();

		OkhttpCallUtils.setQueryParam("ente_id", profiloUtente.getEnteId(), qp);
		OkhttpCallUtils.setQueryParam("struttura_id", profiloUtente.getStrutturaId(), qp);
		OkhttpCallUtils.setQueryParam("pratica_id", praticaId, qp);
		OkhttpCallUtils.setQueryParam("filtro_data", filtroData, qp);

		ModelPraticaEstesa pratica = vigilsanPraticheService.getGenericGet(VigilsanPraticheGetApiEnum.GET_PRATICA,
				headers, qp, profiloUtente.getSessioneId());

		ModelChiaveValoreList bodyForDiario = new ModelChiaveValoreList();

		var fileSalvato = generaESalvaDiario(profiloUtente, headers, qp, pratica, bodyForDiario);
		var diario = new ModelZip();
		diario.setFileId(fileSalvato.getFileId());

		ModelZipLista fileDaZippare = vigilsanPraticheService.getGenericGet(
				VigilsanPraticheGetApiEnum.GET_PRATICA_MODULI_ZIP_LISTA, headers, qp, profiloUtente.getSessioneId());
		fileDaZippare.getFiles().add(diario);

		return moduliService.getZip(profiloUtente.getSessioneId(), fileDaZippare, httpHeaders);
	}

	private ModelFile generaESalvaDiario(ModelProfiloUtente profiloUtente, Headers headers, HashMap<String, Object> qp,
			ModelPraticaEstesa pratica, ModelChiaveValoreList bodyForDiario) throws IOException {
		FileCustom file = generaFileDiario(profiloUtente, headers, qp, pratica, bodyForDiario);
		var fileSalvato = salvaDiario(profiloUtente, headers, pratica, file);
		return fileSalvato;
	}

	private ModelFile salvaDiario(ModelProfiloUtente profiloUtente, Headers headers, ModelPraticaEstesa pratica,
			FileCustom file) throws IOException {
		var postFile = new ModelFilePostBff();

		postFile.setBase64File(convertToBase64(file.getFile()));
		postFile.setFileName(FileUtils.extractFileName(file.getContentDisposition()));
		Optional<ModelFileContentType> contentTypeOptional = moduliService
				.getFileContentTypeLista(profiloUtente.getSessioneId(), headers).stream()
				.filter(c -> "pdf".equals(c.getFileContentTypeCod())).findFirst();
		contentTypeOptional.ifPresent(t -> postFile.setFileContentTypeId(t.getFileContentTypeId()));

		postFile.setFileTipoId(moduliService.getFileTipo(profiloUtente, headers, "pra_diario").getFileTipoId());

		postFile.setEnteId(pratica.getEnteId());
		postFile.setStrutturaId(pratica.getStrutturaId());

		var fileSalvato = moduliService.postFile(profiloUtente, postFile, headers);
		return fileSalvato;
	}

	private FileCustom generaFileDiario(ModelProfiloUtente profiloUtente, Headers headers, HashMap<String, Object> qp,
			ModelPraticaEstesa pratica, ModelChiaveValoreList bodyForDiario) throws IOException {
		var ente = utenteService.getEnteEsteso(pratica.getEnteId(), profiloUtente.getSessioneId(), headers);
		var struttura = utenteService.getStrutturaEstesa(pratica.getStrutturaId(), profiloUtente.getSessioneId(),
				headers);
		bodyForDiario.getChiaveValore().add(creaChiaveValore("ENTE_DESC", ente.getEnteDesc()));
		// bodyForDiario.getChiaveValore().add(creaChiaveValore("TIPO_PRATICA_DESC",
		// value));
		bodyForDiario.getChiaveValore().add(creaChiaveValore("STRUTTURA_COD_ARPE", struttura.getStrutturaCodArpe()));
		bodyForDiario.getChiaveValore().add(creaChiaveValore("STRUTTURA_DESC", struttura.getStrutturaDesc()));
		bodyForDiario.getChiaveValore().add(creaChiaveValore("STRUTTURA_INDIRIZZO", struttura.getIndirizzo()));
		bodyForDiario.getChiaveValore().add(creaChiaveValore("COMUNE", struttura.getComune().getComuneDesc()));
		bodyForDiario.getChiaveValore().add(creaChiaveValore("EMAIL", ""));
		bodyForDiario.getChiaveValore().add(creaChiaveValore("TELEFONO", ""));
		bodyForDiario.getChiaveValore()
				.add(creaChiaveValore("TIPO_STRUTTURA_DESC", struttura.getStrutturaTipo().getStrutturaTipoDesc()));
		for (ModelStrutturaCategoria cat : struttura.getCategorie()) {
			bodyForDiario.getChiaveValore()
					.add(creaChiaveValore("CATEGORIA_STRUTTURA", cat.getStrutturaCategoriaDesc()));
		}
		FileCustom file = vigilsanPraticheService.postGenericFile(
				VigilsanPratichePostFileApiEnum.GET_PRATICA_GENERA_DIARIO, bodyForDiario, headers, qp,
				profiloUtente.getSessioneId());
		return file;
	}

	private ModelChiaveValore creaChiaveValore(String key, String value) {
		var chiaveValore = new ModelChiaveValore();
		chiaveValore.setChiave(key);
		chiaveValore.setValore(value);
		return chiaveValore;
	}

	public static String convertToBase64(InputStream inputStream) throws IOException {
		// Leggi l'InputStream e scrivilo in un ByteArrayOutputStream
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int bytesRead;
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			byteArrayOutputStream.write(buffer, 0, bytesRead);
		}

		// Converti l'array di byte in una stringa Base64
		byte[] bytes = byteArrayOutputStream.toByteArray();
		return Base64.getEncoder().encodeToString(bytes);
	}

	public Object getPraticaRequisitiEsitoDecodifica(ModelProfiloUtente profiloUtente, HttpHeaders httpHeaders)
			throws IOException {
		var qp = new HashMap<String, Object>();
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		return vigilsanPraticheService.getGenericGet(
				VigilsanPraticheGetListApiEnum.GET_PRATICA_REQUISITI_ESITO_DECODIFICA_LISTA, headers, qp,
				profiloUtente.getSessioneId());
	}

	public Object getPraticaRequisitiLista(Integer praticaId, Integer prescrizioneId,
			Integer praticaDetId, ModelProfiloUtente profiloUtente, HttpHeaders httpHeaders) throws IOException {
		var qp = new HashMap<String, Object>();
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		OkhttpCallUtils.setQueryParam("pratica_id", praticaId, qp);
		OkhttpCallUtils.setQueryParam("prescrizione_id", prescrizioneId, qp);
		OkhttpCallUtils.setQueryParam("pratica_det_id", praticaDetId, qp);
		return vigilsanPraticheService.getGenericGet(VigilsanPraticheGetListApiEnum.GET_PRATICA_REQUISITI_LISTA,
				headers, qp, profiloUtente.getSessioneId());
	}

	public Object getPraticaDettaglioRequisitiLista(Integer praticaDetId, ModelProfiloUtente profiloUtente,
			HttpHeaders httpHeaders) throws IOException {
		var qp = new HashMap<String, Object>();
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		OkhttpCallUtils.setQueryParam("pratica_det_id", praticaDetId, qp);

		return vigilsanPraticheService.getGenericGet(
				VigilsanPraticheGetListApiEnum.GET_PRATICA_DETTAGLIO_REQUISITI_LISTA, headers, qp,
				profiloUtente.getSessioneId());
	}

	public ModelPraticaRequisiti getPraticaRequisiti(Integer praticaId, Integer prescrizioneId, Integer praticaDetId, Integer clreqId,
			ModelProfiloUtente profiloUtente, HttpHeaders httpHeaders) throws IOException {
		var qp = new HashMap<String, Object>();
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		OkhttpCallUtils.setQueryParam("pratica_id", praticaId, qp);
		OkhttpCallUtils.setQueryParam("prescrizione_id", prescrizioneId, qp);
		OkhttpCallUtils.setQueryParam("pratica_det_id", praticaDetId, qp);
		OkhttpCallUtils.setQueryParam("clreq_id", clreqId, qp);
		return vigilsanPraticheService.getGenericGet(VigilsanPraticheGetApiEnum.GET_PRATICA_REQUISITI,
				headers, qp, profiloUtente.getSessioneId());
	}


	public ModelPraticaClreqPost postPraticaRequisiti(ModelPraticaClreqPost body, ModelProfiloUtente profiloUtente,
			HttpHeaders httpHeaders) throws IOException {
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);

		var qp = new HashMap<String, Object>();
		OkhttpCallUtils.setQueryParam("ente_id", profiloUtente.getEnteId(), qp);
		OkhttpCallUtils.setQueryParam("profilo_id", profiloUtente.getProfiloId(), qp);

		return vigilsanPraticheService.getGenericPost(VigilsanPratichePostApiEnum.POST_PRATICA_REQUISITI, body, headers,
				qp, profiloUtente.getSessioneId());
	}

	public Object postPraticaDettaglioRequisiti(ModelPraticaDettaglioClreqEsteso body, ModelProfiloUtente profiloUtente,
			HttpHeaders httpHeaders) throws IOException {
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);

		if (body.getModuloCompilato() != null) {
			ModelModuloPost response = moduliService.postModulo(profiloUtente.getSessioneId(), headers,
					body.getModuloCompilato());
			body.setModuloCompilatoId(response.getModuloCompilatoId());
		}
		body.setModuloCompilato(null);
		var qp = new HashMap<String, Object>();
		OkhttpCallUtils.setQueryParam("ente_id", profiloUtente.getEnteId(), qp);
		OkhttpCallUtils.setQueryParam("profilo_id", profiloUtente.getProfiloId(), qp);

		ModelPraticaDettaglioClreq res = vigilsanPraticheService.getGenericPost(
				VigilsanPratichePostApiEnum.POST_PRATICA_DETTAGLIO_REQUISITI, body, headers, qp,
				profiloUtente.getSessioneId());

		return res;
	}

	public Object postPraticaDettaglioRequisitiDelete(ModelPraticaDettaglioClreq body, ModelProfiloUtente profiloUtente,
			HttpHeaders httpHeaders) throws IOException {
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);

		var qp = new HashMap<String, Object>();
		OkhttpCallUtils.setQueryParam("ente_id", profiloUtente.getEnteId(), qp);
		OkhttpCallUtils.setQueryParam("profilo_id", profiloUtente.getProfiloId(), qp);

		return vigilsanPraticheService.getGenericPost(
				VigilsanPratichePostApiEnum.POST_PRATICA_DETTAGLIO_REQUISITI_DELETE, body, headers, qp,
				profiloUtente.getSessioneId());
	}
}
