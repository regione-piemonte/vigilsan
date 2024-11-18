/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanrilevazioni.api.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.vigilsan.vigilsanrilevazioni.api.generated.DocumentazioneApi;
import it.csi.vigilsan.vigilsanrilevazioni.api.generated.dto.ModelChiaveValoreList;
import it.csi.vigilsan.vigilsanrilevazioni.api.generated.dto.ModelDocumentazione;
import it.csi.vigilsan.vigilsanrilevazioni.api.generated.dto.ModelParametro;
import it.csi.vigilsan.vigilsanrilevazioni.api.generated.dto.ModelVerificaDocumentazione;
import it.csi.vigilsan.vigilsanrilevazioni.api.service.impl.DocumentazioneServiceImpl;
import it.csi.vigilsan.vigilsanrilevazioni.api.service.impl.ModuloConfigServiceImpl;
import it.csi.vigilsan.vigilsanrilevazioni.api.service.impl.VerificaDocumentazioneServiceImpl;
import it.csi.vigilsan.vigilsanrilevazioni.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.util.ApiHeaderParamEnum;
import it.csi.vigilsan.vigilsanutil.generic.util.paginazione.Paginazione;
import it.csi.vigilsan.vigilsanutil.generic.util.paginazione.PaginazioneUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.SecurityContext;

@Component
public class DocumentazioneApiImpl extends RESTBaseService implements DocumentazioneApi {

	@Autowired
	private DocumentazioneServiceImpl documentazioneService;
	@Autowired
	private ModuloConfigServiceImpl moduloConfigService;
	@Autowired
	private VerificaDocumentazioneServiceImpl verificaDocumentazioneService;

	@Override
	public Response funzionalitaDocumentazioneListGet(@NotNull Integer strutturaId, @NotNull String moduloConfigCod,
			Integer sId, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaDocumentazioneListGet";
		try {
			RESTErrorUtil.checkNotNull(strutturaId, ErrorCodeEnum.STRUTTURA_ID_OBBLIGATORIA);

			return Response.ok()
					.entity(documentazioneService.getDocumentazioneDaCompilare(sId, strutturaId, moduloConfigCod))
					.build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaDocumentazioneCompilaListaGet(@NotNull Integer strutturaId,
			@NotNull String moduloConfigCod, Integer sId, Long pageNumber, Long rowPerPage, Boolean descending,
			String orderBy, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaDocumentazioneCompilaListaGet";
		try {
			RESTErrorUtil.checkNotNull(strutturaId, ErrorCodeEnum.STRUTTURA_ID_OBBLIGATORIA);
			if (Objects.isNull(orderBy)) {
				orderBy = "mc.modulo_config_ord,d.dataora_documentazione";
			}
			if (Objects.isNull(descending)) {
				descending = Boolean.TRUE;
			}
			Paginazione paginazione = new Paginazione(orderBy, rowPerPage, pageNumber, descending);
			var res = documentazioneService.getDocumentazioneCompilataList(sId, strutturaId, moduloConfigCod,
					paginazione);
			return Response.ok().entity(res)
					.header(ApiHeaderParamEnum.ROWS_NUMBER.getCode(), PaginazioneUtils.getTotalCountFromList(res))
					.build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaDocumentazioneModuliGet(@NotNull String moduloConfigGruppoCod,
			@NotNull Integer strutturaId, Integer sId, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaDocumentazioneModuliGet";
		try {
			RESTErrorUtil.checkNotNull(strutturaId, ErrorCodeEnum.STRUTTURA_ID_OBBLIGATORIA);
			RESTErrorUtil.checkNotNull(moduloConfigGruppoCod, ErrorCodeEnum.MODULO_CONFIG_GRUPPO_COD_OBBLIGATORIO);

			return Response.ok()
					.entity(documentazioneService.getDocumentazioneModuli(sId, strutturaId, moduloConfigGruppoCod)).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaDocumentazionePost(ModelDocumentazione body, String shibIdentitaCodiceFiscale,
			Integer sId, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaDocumentazionePost";
		try {

			RESTErrorUtil.checkNotNull(body, ErrorCodeEnum.PAYLOAD_OBBLIGATORIO);
			RESTErrorUtil.checkNotNull(body.getModuloConfigId(), ErrorCodeEnum.MOD_CONFIG_ID_OBBLIGATORIA);
			RESTErrorUtil.checkCondition(moduloConfigService.existModuloConfig(sId, body.getModuloConfigId()),
					ErrorCodeEnum.MOD_CONFIG_NON_TROVATO);
			RESTErrorUtil.checkNotNull(body.getDataoraDocumentazione(),
					ErrorCodeEnum.DATA_ORA_DOCUMENTAZIONE_OBBLIGATORIA);
			RESTErrorUtil.checkNotNull(body.getStrutturaId(), ErrorCodeEnum.STRUTTURA_ID_OBBLIGATORIA);
			RESTErrorUtil.checkNotNull(body.getModuloCompilatoId(), ErrorCodeEnum.MODULO_COMPILATO_ID_OBBLIGATORIO);

			return Response.status(Status.CREATED)
					.entity(documentazioneService.updateOrInsertDocumentazione(sId, body, shibIdentitaCodiceFiscale))
					.build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaDocumentazioneParametroGet(String parametroCod, Integer sId, Integer strutturaId,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaDocumentazioneParametroGet";
		try {
			RESTErrorUtil.checkNotNull(strutturaId, ErrorCodeEnum.STRUTTURA_ID_OBBLIGATORIA);

			return Response.ok()
					.entity(documentazioneService.getDocumentazioneParametro(sId, strutturaId, parametroCod)).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaDocumentazioneParametroListaGet(@NotNull String parametroCod, Integer sId,
			Integer strutturaId, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaDocumentazioneParametroListaGet";
		try {
			RESTErrorUtil.checkNotNull(strutturaId, ErrorCodeEnum.STRUTTURA_ID_OBBLIGATORIA);

			return Response.ok()
					.entity(documentazioneService.getDocumentazioneParametroLista(sId, strutturaId, parametroCod)).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}


	@Override
	public Response funzionalitaDocumentazioneParametroPost(ModelChiaveValoreList body,
			String shibIdentitaCodiceFiscale, Integer sId, Integer strutturaId, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaDocumentazioneParametroPost";
		try {

			RESTErrorUtil.checkNotNull(strutturaId, ErrorCodeEnum.STRUTTURA_ID_OBBLIGATORIA);
			RESTErrorUtil.checkNotNull(body, ErrorCodeEnum.PAYLOAD_OBBLIGATORIO);

			return Response.status(Status.CREATED)
					.entity(documentazioneService.getDocumentazioneParametroLista(sId, strutturaId, body))
					.build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}


	@Override
	public Response funzionalitaDocumentazioneVerificaPost(ModelVerificaDocumentazione body,
			String shibIdentitaCodiceFiscale, Integer sId, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaDocumentazioneVerificaPost";
		try {

			RESTErrorUtil.checkNotNull(body, ErrorCodeEnum.PAYLOAD_OBBLIGATORIO);
			RESTErrorUtil.checkNotNull(body.getDocumentazioneId(), ErrorCodeEnum.DOCUMENTAZIONE_ID_OBBLIGATORIO);
			RESTErrorUtil.checkNotNull(body.isEsitoVerifica(), ErrorCodeEnum.ESITO_VERIFICA_OBBLIGATORIO);

			return Response.status(Status.CREATED)
					.entity(verificaDocumentazioneService.updateOrInsert(sId, body, shibIdentitaCodiceFiscale))
					.build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

}