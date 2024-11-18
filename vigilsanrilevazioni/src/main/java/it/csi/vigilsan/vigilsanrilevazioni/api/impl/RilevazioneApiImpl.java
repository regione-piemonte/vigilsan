/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanrilevazioni.api.impl;

import java.io.ByteArrayOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.vigilsan.vigilsanrilevazioni.api.generated.RilevazioneApi;
import it.csi.vigilsan.vigilsanrilevazioni.api.generated.dto.ModelRilevazione;
import it.csi.vigilsan.vigilsanrilevazioni.api.service.impl.ModuloConfigServiceImpl;
import it.csi.vigilsan.vigilsanrilevazioni.api.service.impl.RilevazioneDocumentazionePivotServiceImpl;
import it.csi.vigilsan.vigilsanrilevazioni.api.service.impl.RilevazioneServiceImpl;
import it.csi.vigilsan.vigilsanrilevazioni.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.util.ApiHeaderParamEnum;
import it.csi.vigilsan.vigilsanutil.generic.util.ContentTypeEnum;
import it.csi.vigilsan.vigilsanutil.generic.util.paginazione.Paginazione;
import it.csi.vigilsan.vigilsanutil.generic.util.paginazione.PaginazioneUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.SecurityContext;

@Component
public class RilevazioneApiImpl extends RESTBaseService implements RilevazioneApi {
	
	@Autowired
	private RilevazioneServiceImpl rivelazioneService;
	@Autowired
	private RilevazioneDocumentazionePivotServiceImpl rivelazioneCsvService;
	@Autowired
	private ModuloConfigServiceImpl moduloConfigService;

	@Override
	public Response funzionalitaRilevazioneListGet(@NotNull Integer strutturaId, @NotNull String moduloConfigCod,
			Integer sId, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaRilevazioneListGet";
		try {
			RESTErrorUtil.checkNotNull(moduloConfigCod, ErrorCodeEnum.MODULO_CONFIG_COD_OBBLIGATORIO);
			RESTErrorUtil.checkNotNull(strutturaId, ErrorCodeEnum.STRUTTURA_ID_OBBLIGATORIA);

			return Response.ok().entity(rivelazioneService.getRilevazioneDaCompilareList(sId, strutturaId, moduloConfigCod)).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaRilevazionePost(ModelRilevazione body, String shibIdentitaCodiceFiscale, Integer sId,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaRilevazionePost";
		try {

			RESTErrorUtil.checkNotNull(body, ErrorCodeEnum.PAYLOAD_OBBLIGATORIO);
			RESTErrorUtil.checkNotNull(body.getModuloConfigId(), ErrorCodeEnum.MOD_CONFIG_ID_OBBLIGATORIA);
			RESTErrorUtil.checkCondition(moduloConfigService.existModuloConfig(sId, body.getModuloConfigId()),
					ErrorCodeEnum.MOD_CONFIG_NON_TROVATO);
			RESTErrorUtil.checkNotNull(body.getDataoraRilevazione(), ErrorCodeEnum.DATA_ORA_RILEVAZIONE_OBBLIGATORIA);
			RESTErrorUtil.checkNotNull(body.getStrutturaId(), ErrorCodeEnum.STRUTTURA_ID_OBBLIGATORIA);
			RESTErrorUtil.checkNotNull(body.getModuloCompilatoId(), ErrorCodeEnum.MODULO_COMPILATO_ID_OBBLIGATORIO);
			RESTErrorUtil.checkNotNull(body.getValiditaFine(), ErrorCodeEnum.VALIDITA_FINE_OBBGLIATORIA);
			RESTErrorUtil.checkNotNull(body.getValiditaInizio(), ErrorCodeEnum.VALIDITA_INIZIO_OBBGLIATORIA);
			return Response.status(Status.CREATED)
					.entity(rivelazioneService.updateOrInsertRilevazione(sId, body, shibIdentitaCodiceFiscale)).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaRilevazioneCompilatiGet(@NotNull Integer strutturaId, @NotNull String moduloConfigCod,
			Integer sId, Long pageNumber, Long rowPerPage, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaRilevazioneCompilatiGet";
		try {
			RESTErrorUtil.checkNotNull(strutturaId, ErrorCodeEnum.STRUTTURA_ID_OBBLIGATORIA);
			RESTErrorUtil.checkNotNull(moduloConfigCod, ErrorCodeEnum.MODULO_CONFIG_COD_OBBLIGATORIO);
			var paginazione = new Paginazione(null, rowPerPage, pageNumber, null);

			var res = rivelazioneService.getRilevazioneCompilataList(sId, strutturaId, moduloConfigCod, paginazione);
			return Response.ok().entity(res)
					.header(ApiHeaderParamEnum.ROWS_NUMBER.getCode(), PaginazioneUtils.getTotalCountFromList(res))
					.build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaRilevazioneCompilataListaCsvGet(String shibIdentitaCodiceFiscale, String xRequestId,
			String xCodiceServizio, @NotNull String moduloConfigCod, Integer sId, Integer strutturaId, Integer enteId,
			String dataRilevazioneDa, String dataRilevazioneA, String strutturaCategoriaCod,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaRilevazioneCompilataListaCsvGet";
		try {
			RESTErrorUtil.checkNotNull(moduloConfigCod, ErrorCodeEnum.MODULO_CONFIG_COD_OBBLIGATORIO);

			ByteArrayOutputStream byteArrayOutputStream = rivelazioneCsvService.funzionalitaRilevazioneCompilataListaCsvGet(sId, moduloConfigCod, strutturaId, enteId, 
					dataRilevazioneDa, dataRilevazioneA, strutturaCategoriaCod);
			//GENERATE CSV
			var length = byteArrayOutputStream.size();
			return Response.ok().entity(byteArrayOutputStream.toByteArray()).type(ContentTypeEnum.CSV.getCode())
					.header(ApiHeaderParamEnum.CONTENT_LENGTH.getCode(), String.valueOf(length))
					.header(ApiHeaderParamEnum.CONTENT_DISPOSITION.getCode(),
							"attachment; filename=\"" + moduloConfigCod + ".csv\"")
					.build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaRilevazioneModuliGet(@NotNull String moduloConfigGruppoCod,
			@NotNull Integer strutturaId, Integer sId, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaRilevazioneModuliGet";
		try {
			RESTErrorUtil.checkNotNull(strutturaId, ErrorCodeEnum.STRUTTURA_ID_OBBLIGATORIA);
			RESTErrorUtil.checkNotNull(moduloConfigGruppoCod, ErrorCodeEnum.MODULO_CONFIG_GRUPPO_COD_OBBLIGATORIO);

			return Response.ok().entity(rivelazioneService.getRilevazioneModuli(sId, strutturaId, moduloConfigGruppoCod)).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}
}