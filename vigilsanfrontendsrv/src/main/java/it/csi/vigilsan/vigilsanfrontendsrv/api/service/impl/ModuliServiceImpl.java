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

import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelChiaveValoreList;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelFile;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelFileContentType;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelFilePostBff;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelFileTipo;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelModuloEsteso;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelModuloPost;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelZipLista;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.dto.ModelProfiloUtente;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsanmoduli.VigilsanModuliGetApiEnum;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsanmoduli.VigilsanModuliGetFileApiEnum;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsanmoduli.VigilsanModuliGetListApiEnum;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsanmoduli.VigilsanModuliPostApiEnum;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsanmoduli.VigilsanModuliPostFileApiEnum;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsanmoduli.dto.ModelFilePost;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsanrilevazioni.VigilsanRilevazioniPostApiEnum;
import it.csi.vigilsan.vigilsanfrontendsrv.util.OkhttpCallUtils;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.dto.FileCustom;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.vigilsanmoduli.impl.VigilsanModuliService;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.vigilsanrilevazioni.impl.VigilsanRilevazioniService;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.core.HttpHeaders;
import okhttp3.Headers;

@Service
public class ModuliServiceImpl {

	public static final Boolean IS_RIDOTTO = Boolean.TRUE;

	@Autowired
	private VigilsanModuliService vigilsanModuliService;
	@Autowired
	private VigilsanRilevazioniService vigilsanRilevazioniService;

	@Autowired
	private UtenteServiceImpl utenteService;

	public ModelModuloEsteso getModulo(@NotNull Integer moduloId, Integer moduloCompilatoId, Boolean isRidotto,
			ModelProfiloUtente profiloUtente, HttpHeaders httpHeaders) throws IOException {

		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		return getModulo(moduloId, moduloCompilatoId, isRidotto, profiloUtente.getSessioneId(), profiloUtente.getStrutturaId(),
				headers);
	}

	public ModelFile postFile(@NotNull ModelProfiloUtente profiloUtente, ModelFilePostBff body, HttpHeaders httpHeaders)
			throws IOException {
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		return postFile(profiloUtente, body, headers);
	}

	protected ModelFile postFile(ModelProfiloUtente profiloUtente, ModelFilePostBff body, Headers headers)
			throws IOException {
		var sId = profiloUtente.getSessioneId();

		var req = new ModelFilePost();
		req.setBase64File(body.getBase64File());
		req.setSoggettoId(body.getSoggettoId());
		req.setFileName(body.getFileName());
		req.setFileTipoId(body.getFileTipoId());
		req.setFileContentTypeId(body.getFileContentTypeId());
		req.setEnteCod(utenteService.getEnte(body.getEnteId(), sId, headers).getEnteCod());
		req.setStrutturaCod(utenteService.getStruttura(body.getStrutturaId(), sId, headers).getStrutturaCod());
		return postFile(sId, headers, req);
	}

	protected ModelFile postFile(Integer sessioneId, Headers headers, ModelFilePost bodyModulo) throws IOException {
		var qp = new HashMap<String, Object>();
		return vigilsanModuliService.getGenericPost(VigilsanModuliPostApiEnum.POST_FILE, bodyModulo, headers, qp,
				sessioneId);
	}

	protected ModelModuloEsteso getModulo(Integer moduloId, Integer moduloCompilatoId, Boolean isRidotto,
			Integer sId, Integer strutturaId, Headers headers) throws IOException {
		var qp = new HashMap<String, Object>();
		OkhttpCallUtils.setQueryParam("modulo_id", moduloId, qp);
		OkhttpCallUtils.setQueryParam("modulo_compilato_id", moduloCompilatoId, qp);
		OkhttpCallUtils.setQueryParam("ridotto", isRidotto, qp);
		ModelModuloEsteso res = vigilsanModuliService.getGenericGet(VigilsanModuliGetApiEnum.GET_MODULO, headers, qp,
				sId);
		if (res.getListaPlaceOlder() != null && !res.getListaPlaceOlder().isEmpty()) {
			OkhttpCallUtils.setQueryParam("struttura_id", strutturaId, qp);
			var body = new ModelChiaveValoreList();
			body.setChiaveValore(res.getListaPlaceOlder());
			body = vigilsanRilevazioniService.getGenericPost(
					VigilsanRilevazioniPostApiEnum.POST_DOCUMENTAZIONE_PARAMETRO_LISTA, body, headers, qp,
					sId);
			res.setListaPlaceOlder(body.getChiaveValore());
		}
		return res;
	}

	protected ModelModuloPost postModulo(Integer sessioneId, Headers headers, ModelModuloPost bodyModulo)
			throws IOException {
		var qp = new HashMap<String, Object>();
		return vigilsanModuliService.getGenericPost(VigilsanModuliPostApiEnum.POST_MODULO, bodyModulo, headers, qp,
				sessioneId);
	}

	public FileCustom getFile(Integer sessioneId, Integer fileId, HttpHeaders httpHeaders) throws IOException {
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		var qp = new HashMap<String, Object>();
		qp.put("file_id", fileId);
		return vigilsanModuliService.getGenericFile(VigilsanModuliGetFileApiEnum.GET_FILE, headers, qp, sessioneId);
	}

	public FileCustom getZip(Integer sessioneId, ModelZipLista body, HttpHeaders httpHeaders) throws IOException {
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		var qp = new HashMap<String, Object>();

		return vigilsanModuliService.postGenericFile(VigilsanModuliPostFileApiEnum.POST_ZIP, body, headers, qp,
				sessioneId);
	}

	public List<ModelFileContentType> getFileContentTypeLista(Integer sId, HttpHeaders httpHeaders) throws IOException {

		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		return getFileContentTypeLista(sId, headers);
	}

	protected List<ModelFileContentType> getFileContentTypeLista(Integer sId, Headers headers) throws IOException {
		var qp = new HashMap<String, Object>();
		return vigilsanModuliService.getGenericGet(VigilsanModuliGetListApiEnum.GET_FILE_CONTENT_TYPE_LISTA, headers,
				qp, sId);
	}

	public FileCustom getModuloPdf(ModelProfiloUtente profiloUtente, Integer moduloId, Integer moduloCompilatoId,
			Boolean isRidotto, HttpHeaders httpHeaders) throws IOException {
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		return getModuloPdf(profiloUtente.getStrutturaId(), profiloUtente.getSessioneId(), moduloId, moduloCompilatoId,
				isRidotto, headers);
	}

	private FileCustom getModuloPdf(Integer strutturaId, Integer sId, Integer moduloId, Integer moduloCompilatoId,
			Boolean isRidotto, Headers headers) throws IOException {
		var qp = new HashMap<String, Object>();
		OkhttpCallUtils.setQueryParam("modulo_id", moduloId, qp);
		OkhttpCallUtils.setQueryParam("modulo_compilato_id", moduloCompilatoId, qp);
		OkhttpCallUtils.setQueryParam("ridotto", isRidotto, qp);

		return vigilsanModuliService.getGenericFile(VigilsanModuliGetFileApiEnum.GET_MODULO_PDF, headers, qp, sId);
	}

	public ModelFileTipo getFileTipo(ModelProfiloUtente profiloUtente, HttpHeaders httpHeaders, String fileTipoCod)
			throws IOException {
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		return getFileTipo(profiloUtente, headers, fileTipoCod);
	}

	protected ModelFileTipo getFileTipo(ModelProfiloUtente profiloUtente, Headers headers, String fileTipoCod)
			throws IOException {
		var qp = new HashMap<String, Object>();

		OkhttpCallUtils.setQueryParam("file_tipo_cod", fileTipoCod, qp);
		return vigilsanModuliService.getGenericGet(VigilsanModuliGetApiEnum.GET_FILE_TIPO, headers, qp,
				profiloUtente.getSessioneId());
	}
}
