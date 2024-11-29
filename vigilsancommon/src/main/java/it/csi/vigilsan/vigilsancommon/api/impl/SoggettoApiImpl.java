/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.impl;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.vigilsan.vigilsancommon.api.generated.SoggettoApi;
import it.csi.vigilsan.vigilsancommon.api.generated.dto.ModelSoggetto;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.Soggetto;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.SoggettoEstesoLista;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.interfacecsv.SoggettoEstesoListaForCsv;
import it.csi.vigilsan.vigilsancommon.api.service.SoggettoService;
import it.csi.vigilsan.vigilsancommon.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.util.ApiHeaderParamEnum;
import it.csi.vigilsan.vigilsanutil.generic.util.ContentTypeEnum;
import it.csi.vigilsan.vigilsanutil.generic.util.csv.CSVutils;
import it.csi.vigilsan.vigilsanutil.generic.util.paginazione.Paginazione;
import it.csi.vigilsan.vigilsanutil.generic.util.paginazione.PaginazioneUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.SecurityContext;

@Component
public class SoggettoApiImpl extends RESTBaseService implements SoggettoApi {

	private static final String FILE_NAME_OSPITI_CSV = "ospiti.csv";
	@Autowired
	private SoggettoService soggettoService;

	@Override
	public Response funzionalitaSoggettoGet(String shibIdentitaCodiceFiscale, String xRequestId, String xCodiceServizio,
			@NotNull Integer soggettoId, @NotNull Integer sId, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaSoggettoGet";
		try {
			RESTErrorUtil.checkNotNull(soggettoId, ErrorCodeEnum.SOGGETTOAPI_SOGG_ID_OBB);

			return Response.ok().entity(soggettoService.getSoggetto(soggettoId)).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaSoggettoPost(ModelSoggetto body, String shibIdentitaCodiceFiscale, Integer sId,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaSoggettoPost";
		try {
			Soggetto res;
			RESTErrorUtil.checkNotNull(body, ErrorCodeEnum.PAYLOAD_OBBLIGATORIO);
			if (Objects.isNull(body.getCodiceFiscale()) && Objects.isNull(body.getNome())
					&& Objects.isNull(body.getCognome())) {
				throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.CF_OBBLIGATORIO);
			} else if (Objects.isNull(body.getCodiceFiscale())) {
				// TODO: IMPLEMENTA QUANDO ABBIAMO DATA NASCITA
//				RESTErrorUtil.checkNotNull(body.getNome(), ErrorCodeEnum.PAYLOAD_OBBLIGATORIO);
//				RESTErrorUtil.checkNotNull(body.getCognome(), ErrorCodeEnum.PAYLOAD_OBBLIGATORIO);
//				Date dataNascita;
//				res = soggettoService.soggettoPost(body.getNome(), body.getCognome(), dataNascita);
				throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.CF_OBBLIGATORIO);
			} else {
				res = soggettoService.soggettoPost(sId, body.getCodiceFiscale(), shibIdentitaCodiceFiscale);
			}

			return Response.status(Status.CREATED).entity(res).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaSoggettoListaGet(String shibIdentitaCodiceFiscale, String xRequestId,
			String xCodiceServizio, Integer sId, Integer strutturaId, String filter, String ruoloStrutturaCod,
			Integer ospiteStatoId, String dataIngressoDa, String dataIngressoA, String dataUscitaDa, String dataUscitaA,
			Long pageNumber, Long rowPerPage, Boolean descending, String orderBy, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaSoggettoListaGet";
		try {
			RESTErrorUtil.checkNotNull(strutturaId, ErrorCodeEnum.STRUTTURA_ID_OBBLIGATORIO);
			if (Objects.nonNull(orderBy)) {
				switch (orderBy) {
				case "CF":
					orderBy = "codice_fiscale";
					break;
				case "NOME":
					orderBy = "cognome,nome,codice_fiscale";
					break;
				case "NASCITA":
					orderBy = "data_nascita,codice_fiscale";
					break;
				case "STATO":
					orderBy = "ospite_stato_ord,data_movimento,codice_fiscale";
					break;
				case "INGRESSO":
					orderBy = "data_primo_ingresso,codice_fiscale";
					break;
				case "USCITA":
					orderBy = "data_ultima_uscita,codice_fiscale";
					break;
				case "CATEGORIA":
					orderBy = "struttura_categoria_desc";
					break;
				}
			}
			var paginazione = new Paginazione(orderBy, rowPerPage, pageNumber, descending);

			List<SoggettoEstesoLista> res = soggettoService.getDocumentazioneCompilataList(sId, ruoloStrutturaCod,
					strutturaId, filter, ospiteStatoId, dataIngressoDa, dataIngressoA, dataUscitaDa, dataUscitaA,
					paginazione);
			return Response.ok().entity(res)
					.header(ApiHeaderParamEnum.ROWS_NUMBER.getCode(), PaginazioneUtils.getTotalCountFromList(res))
					.build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaSoggettoListaCsvGet(String shibIdentitaCodiceFiscale, String xRequestId,
			String xCodiceServizio, Integer sId, Integer strutturaId, String filter, String ruoloStrutturaCod,
			Integer ospiteStatoId, String dataIngressoDa, String dataIngressoA, String dataUscitaDa, String dataUscitaA,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaSoggettoListaCsvGet";
		try {
			RESTErrorUtil.checkNotNull(strutturaId, ErrorCodeEnum.STRUTTURA_ID_OBBLIGATORIO);

			List<SoggettoEstesoListaForCsv> lista = soggettoService.getDocumentazioneCompilataList(sId,
					ruoloStrutturaCod, strutturaId, filter, ospiteStatoId, dataIngressoDa, dataIngressoA, dataUscitaDa,
					dataUscitaA);
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			CSVutils.generateCSV(lista, byteArrayOutputStream, SoggettoEstesoListaForCsv.class);
			var length = byteArrayOutputStream.size();
			return Response.ok().entity(byteArrayOutputStream.toByteArray()).type(ContentTypeEnum.CSV.getCode())
					.header(ApiHeaderParamEnum.CONTENT_LENGTH.getCode(), String.valueOf(length))
					.header(ApiHeaderParamEnum.CONTENT_DISPOSITION.getCode(),
							"attachment; filename=\"" + FILE_NAME_OSPITI_CSV + "\"")
					.build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}
}