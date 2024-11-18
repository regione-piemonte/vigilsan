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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelApplicativo;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelApplicativoOperazioneRidottoCustom;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelArpeStrutturaDettaglioEsteso;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelDecodificaJwt;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelEnte;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelEnteEsteso;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelLogout;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelProfilo;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelRuolo;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelSoggetto;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelStruttura;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelStrutturaCategoria;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelStrutturaEstesa;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelUtente;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.VigilsanCommonGetApiEnum;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.VigilsanCommonGetListApiEnum;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.VigilsanCommonPostApiEnum;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.dto.ModelProfiloUtente;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.dto.ModelProfiloUtenteProfili;
import it.csi.vigilsan.vigilsanfrontendsrv.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanfrontendsrv.util.OkhttpCallUtils;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.impl.VigilsanServiceAbstract;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableApiServiceImpl;
import jakarta.ws.rs.core.HttpHeaders;
import okhttp3.Headers;

@Service
public class UtenteServiceImpl extends AuditableApiServiceImpl {

	@Autowired
	private VigilsanServiceAbstract vigilsanCommonService;

	@Value("${applicativo.logout.url}")
	private String redirectLogout;

	@Autowired
	private StrutturaServiceImpl strutturaServiceImpl;

	public ModelUtente getModelUtente(ModelProfiloUtente profiloUtente, HttpHeaders httpHeaders, Integer sId)
			throws IOException {
		var res = new ModelUtente();

		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		res.setSoggetto(getSoggetto(profiloUtente, sId, headers));
		if (profiloUtente.getStrutturaId() != null) {
			res.setStruttura(getStruttura(profiloUtente, sId, headers));
		}
		if (profiloUtente.getEnteId() != null) {
			res.setEnte(getEnte(profiloUtente, sId, headers));
		}
		return res;
	}

	protected ModelStrutturaCategoria getStrutturaCategoriaFromStrCatId(Integer strCatId, Integer sId, Headers headers)
			throws IOException {
		var qp = new HashMap<String, Object>();
		OkhttpCallUtils.setQueryParam("str_cat_id", strCatId, qp);
		return vigilsanCommonService.getGenericGet(VigilsanCommonGetApiEnum.GET_STRUTTURA_CATEGORIA, headers, qp, sId);
	}

	protected ModelSoggetto getSoggetto(ModelProfiloUtente profiloUtente, Integer sId, Headers headers)
			throws IOException {
		var qp = new HashMap<String, Object>();
		OkhttpCallUtils.setQueryParam("soggetto_id", profiloUtente.getSoggettoId(), qp);
		return vigilsanCommonService.getGenericGet(VigilsanCommonGetApiEnum.GET_SOGGETTO, headers, qp, sId);
	}
	protected ModelSoggetto getSoggetto(Integer soggettoId, Integer sId, Headers headers)
			throws IOException {
		var qp = new HashMap<String, Object>();
		OkhttpCallUtils.setQueryParam("soggetto_id", soggettoId, qp);
		return vigilsanCommonService.getGenericGet(VigilsanCommonGetApiEnum.GET_SOGGETTO, headers, qp, sId);
	}

	protected ModelEnte getEnte(ModelProfiloUtente profiloUtente, Integer sId, Headers headers) throws IOException {
		return getEnte(profiloUtente.getEnteId(), sId, headers);
	}

	protected ModelEnte getEnte(Integer enteId, Integer sId, Headers headers) throws IOException {
		var qp = new HashMap<String, Object>();
		OkhttpCallUtils.setQueryParam("ente_id", enteId, qp);
		return vigilsanCommonService.getGenericGet(VigilsanCommonGetApiEnum.GET_ENTE, headers, qp, sId);
	}

	protected ModelEnteEsteso getEnteEsteso(ModelProfiloUtente profiloUtente, Integer sId, Headers headers)
			throws IOException {
		return getEnteEsteso(profiloUtente.getEnteId(), sId, headers);
	}

	protected ModelEnteEsteso getEnteEsteso(Integer enteId, Integer sId, Headers headers) throws IOException {
		var qp = new HashMap<String, Object>();
		OkhttpCallUtils.setQueryParam("ente_id", enteId, qp);
		ModelEnteEsteso res = vigilsanCommonService.getGenericGet(VigilsanCommonGetApiEnum.GET_ENTE_ESTESO, headers, qp,
				sId);

		if (Objects.nonNull(res.getEnteTipoId())) {
			qp = new HashMap<String, Object>();
			OkhttpCallUtils.setQueryParam("ente_tipo_id", res.getEnteTipoId(), qp);
			res.setEnteTipo(
					vigilsanCommonService.getGenericGet(VigilsanCommonGetApiEnum.GET_ENTE_TIPO, headers, qp, sId));
		}

		return res;
	}

	protected ModelStruttura getStruttura(ModelProfiloUtente profiloUtente, Integer sId, Headers headers)
			throws IOException {
		return getStruttura(profiloUtente.getStrutturaId(), sId, headers);
	}

	public Object getstrutturaEstesaIdEsterno(ModelProfiloUtente profiloUtente, Integer strutturaId, HttpHeaders httpHeaders) throws IOException {

		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		return getStrutturaEstesa(strutturaId, profiloUtente.getSessioneId(), headers);
		}
	protected ModelStrutturaEstesa getStrutturaEstesa(ModelProfiloUtente profiloUtente, Integer sId, Headers headers)
			throws IOException {
		return getStrutturaEstesa(profiloUtente.getStrutturaId(), sId, headers);
	}

	public ModelStrutturaEstesa getStrutturaEstesa(Integer strutturaId, Integer sId, Headers headers)
			throws IOException {
		var qp = new HashMap<String, Object>();
		OkhttpCallUtils.setQueryParam("struttura_id", strutturaId, qp);
		ModelStrutturaEstesa res = vigilsanCommonService.getGenericGet(VigilsanCommonGetApiEnum.GET_STRUTTURA_ESTESA,
				headers, qp, sId);
		res.setAsl(
				vigilsanCommonService.getGenericGet(VigilsanCommonGetListApiEnum.GET_STRUTTURA_ASL, headers, qp, sId));
		res.setCategorie(vigilsanCommonService.getGenericGet(VigilsanCommonGetListApiEnum.GET_STRUTTURA_CATEGORIA,
				headers, qp, sId));
		qp = new HashMap<String, Object>();
		OkhttpCallUtils.setQueryParam("struttura_natura_id", res.getStrutturaNaturaId(), qp);
		res.setNatura(
				vigilsanCommonService.getGenericGet(VigilsanCommonGetApiEnum.GET_STRUTTURA_NATURA, headers, qp, sId));
		if (Objects.nonNull(res.getComuneId())) {
			qp = new HashMap<String, Object>();
			OkhttpCallUtils.setQueryParam("comune_id", res.getComuneId(), qp);
			res.setComune(
					vigilsanCommonService.getGenericGet(VigilsanCommonGetApiEnum.GET_COMUNE_ESTESO, headers, qp, sId));
		}
		if (Objects.nonNull(res.getStrutturaAccreditamentoId())) {
			qp = new HashMap<String, Object>();
			OkhttpCallUtils.setQueryParam("struttura_accreditamento_id", res.getStrutturaAccreditamentoId(), qp);
			res.setStrutturaAccreditamento(vigilsanCommonService
					.getGenericGet(VigilsanCommonGetApiEnum.GET_STRUTTURA_ACCREDITAMENTO, headers, qp, sId));
		}
		if (Objects.nonNull(res.getStrutturaTipoId())) {
			qp = new HashMap<String, Object>();
			OkhttpCallUtils.setQueryParam("struttura_tipo_id", res.getStrutturaTipoId(), qp);
			res.setStrutturaTipo(
					vigilsanCommonService.getGenericGet(VigilsanCommonGetApiEnum.GET_STRUTTURA_TIPO, headers, qp, sId));
		}
		if (Objects.nonNull(res.getStrutturaTitolaritaId())) {
			qp = new HashMap<String, Object>();
			OkhttpCallUtils.setQueryParam("struttura_titolarita_id", res.getStrutturaTitolaritaId(), qp);
			res.setStrutturaTitolarita(vigilsanCommonService
					.getGenericGet(VigilsanCommonGetApiEnum.GET_STRUTTURA_TITOLARITA, headers, qp, sId));
		}
		return res;
	}

	private List<ModelApplicativoOperazioneRidottoCustom> getFunzioni(ModelProfiloUtente profiloUtente, Integer sId,
			Headers headers) throws IOException {
		var qp = new HashMap<String, Object>();
		OkhttpCallUtils.setQueryParam("applicativo_id", profiloUtente.getApplicativoId(), qp);
		OkhttpCallUtils.setQueryParam("struttura_id", profiloUtente.getStrutturaId(), qp);
		OkhttpCallUtils.setQueryParam("profilo_id", profiloUtente.getProfiloId(), qp);
		return vigilsanCommonService.getGenericGet(VigilsanCommonGetListApiEnum.GET_UTENTE_PERMESSI, headers, qp, sId);
	}

	protected ModelStruttura getStruttura(Integer strutturaId, Integer sId, Headers headers) throws IOException {
		var qp = new HashMap<String, Object>();
		OkhttpCallUtils.setQueryParam("struttura_id", strutturaId, qp);
		return vigilsanCommonService.getGenericGet(VigilsanCommonGetApiEnum.GET_STRUTTURA, headers, qp, sId);
	}
	protected ModelStrutturaEstesa getStrutturaDue(Integer strutturaId, Integer sId, Headers headers) throws IOException {
		var qp = new HashMap<String, Object>();
		OkhttpCallUtils.setQueryParam("struttura_id", strutturaId, qp);
		return vigilsanCommonService.getGenericGet(VigilsanCommonGetApiEnum.GET_STRUTTURA_ESTESA, headers, qp, sId);
	}

	protected ModelRuolo getRuolo(ModelProfiloUtente profiloUtente, Integer sId, Headers headers) throws IOException {
		var qp = new HashMap<String, Object>();
		OkhttpCallUtils.setQueryParam("ruolo_id", profiloUtente.getRuoloId(), qp);
		return vigilsanCommonService.getGenericGet(VigilsanCommonGetApiEnum.GET_RUOLO, headers, qp, sId);
	}

	protected ModelApplicativo getApplicativo(ModelProfiloUtente profiloUtente, Integer sId, Headers headers)
			throws IOException {
		var qp = new HashMap<String, Object>();
		OkhttpCallUtils.setQueryParam("applicativo_id", profiloUtente.getApplicativoId(), qp);
		return vigilsanCommonService.getGenericGet(VigilsanCommonGetApiEnum.GET_APPLICATIVO, headers, qp, sId);
	}

	protected ModelProfilo getProfilo(Integer profiloId, Integer sId, Headers headers) throws IOException {
		var qp = new HashMap<String, Object>();
		OkhttpCallUtils.setQueryParam("profilo_id", profiloId, qp);
		return vigilsanCommonService.getGenericGet(VigilsanCommonGetApiEnum.GET_PROFILO, headers, qp, sId);
	}

	public ModelDecodificaJwt getModelDecodificaJwt(ModelProfiloUtente profiloUtente, HttpHeaders httpHeaders,
			Integer sId) throws IOException {
		var res = new ModelDecodificaJwt();

		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		res.setSoggetto(getSoggetto(profiloUtente, sId, headers));
		if (profiloUtente.getStrutturaId() != null) {
			res.setStruttura(getStrutturaEstesa(profiloUtente, sId, headers));
		}
		if (profiloUtente.getEnteId() != null) {
			res.setEnte(getEnteEsteso(profiloUtente, sId, headers));
		}
		res.setApplicativo(getApplicativo(profiloUtente, sId, headers));
		res.setRuolo(getRuolo(profiloUtente, sId, headers));
		var profili = new ArrayList<ModelProfilo>();
		res.setProfili(profili);
		for (ModelProfiloUtenteProfili profilo : profiloUtente.getProfili()) {
			profili.add(getProfilo(profilo.getProfiloId(), sId, headers));
			if (profiloUtente.getProfiloId().equals(profilo.getProfiloId())) {
				res.setFunzioni(getFunzioni(profiloUtente, sId, headers));
			}
		}
		res.setProfiloId(profiloUtente.getProfiloId());
		return res;
	}

	public ModelLogout getLogout(HttpHeaders httpHeaders, Integer sessioneId) {
		final var methodName = "getLogout";
		try {
			postLogout(sessioneId, httpHeaders);
		} catch (IOException e) {
			logError(sessioneId, methodName, "IOException: ", e);
		} catch (Exception e) {
			logError(sessioneId, methodName, "Error: ", e);
		}
		var res = new ModelLogout();
		res.setRedirectUrl(redirectLogout);
		return res;
	}

	protected ModelApplicativo postLogout(Integer sId, HttpHeaders httpHeaders) throws IOException {
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		var qp = new HashMap<String, Object>();
		return vigilsanCommonService.getGenericPost(VigilsanCommonPostApiEnum.POST_UTENTE_LOGOUT, null, headers, qp,
				sId);
	}

	public void setProfiloUtente(ModelProfiloUtente profiloUtente, Integer profiloId) {
		if (profiloUtente.getProfili().stream().map(ModelProfiloUtenteProfili::getProfiloId)
				.anyMatch(id -> id.equals(profiloId))) {
			profiloUtente.setProfiloId(profiloId);
		} else {
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.PROFILO_UTENTE_NON_VALIDO);
		}

	}

	public List<ModelArpeStrutturaDettaglioEsteso> getArpeStrutturaDettaglio(ModelProfiloUtente profiloUtente,
			HttpHeaders httpHeaders) throws IOException {
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		var qp = new HashMap<String, Object>();
		OkhttpCallUtils.setQueryParam("struttura_id", profiloUtente.getStrutturaId(), qp);
		return vigilsanCommonService.getGenericGet(VigilsanCommonGetListApiEnum.GET_ARPE_STRUTTURA_DETTAGLIO_LISTA,
				headers, qp, profiloUtente.getSessioneId());
	}

	public ModelProfiloUtente aggiornaProfiloUtente(Integer profiloId, Integer strutturaId,
			ModelProfiloUtente profiloUtente, HttpHeaders httpHeaders) throws IOException, ExecutionException {
		setProfiloUtente(profiloUtente, profiloId);
		if (strutturaId != null) {
			var strutturaList = strutturaServiceImpl.getStrutturaListaByEnteId(profiloUtente, null, strutturaId, null,
					null, null, null, httpHeaders);
			if (strutturaList.getGenericObject().isEmpty()) {
				throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.STRUTTURA_ID_NON_VALIDA);
			}
		}
		profiloUtente.setStrutturaId(strutturaId);

		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		var qp = new HashMap<String, Object>();
		return vigilsanCommonService.getGenericPost(VigilsanCommonPostApiEnum.POST_UTENTE_MODIFICA_SESSIONE,
				profiloUtente, headers, qp, profiloUtente.getSessioneId());

	}

}
