/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.vigilsan.vigilsanpratiche.api.generated.PraticaApi;
import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelChiaveValore;
import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelChiaveValoreList;
import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelPraticaClreqPost;
import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelPraticaDettaglio;
import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelPraticaDettaglioClreq;
import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelPraticaPost;
import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelPraticaScadenza;
import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelZipLista;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.AppuntamentoEsteso;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.AzioniEstese;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaEstesa;
import it.csi.vigilsan.vigilsanpratiche.api.service.impl.PraticaServiceImpl;
import it.csi.vigilsan.vigilsanpratiche.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.util.ApiHeaderParamEnum;
import it.csi.vigilsan.vigilsanutil.generic.util.paginazione.Paginazione;
import it.csi.vigilsan.vigilsanutil.generic.util.paginazione.PaginazioneUtils;
import it.csi.vigilsan.vigilsanutil.generic.util.pdf.dto.FileCustom;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.SecurityContext;

@Component
public class PraticaApiImpl extends RESTBaseService implements PraticaApi {

	@Autowired
	PraticaServiceImpl praticaService;

	@Override
	public Response funzionalitaPraticaStatoGet(Integer sId, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaPraticaStatoGet";
		try {
			return Response.ok().entity(praticaService.getPraticaStatoDecodifica(sId)).build();

		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaPraticaTipoListaGet(Integer sId, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaPraticaTipoListaGet";
		try {
			return Response.ok().entity(praticaService.getPraticaTipoDecodifica(sId)).build();

		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaPraticaInseribilieGet(Integer sId, Integer enteId, Integer profiloId,
			Integer praticaTipoId, Integer strutturaTipoId, String dataChiusuraMax, String filterStruttura,
			Long pageNumber, Long rowPerPage, Boolean descending, String orderBy, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaPraticaInseribilieGet";
		try {
			RESTErrorUtil.checkNotNull(enteId, ErrorCodeEnum.ENTE_ID_OBBLIGATORIO);

			var paginazione = new Paginazione(orderBy, rowPerPage, pageNumber, descending);
			var res = praticaService.getPraticaInseribili(enteId, profiloId, praticaTipoId, strutturaTipoId,
					filterStruttura, dataChiusuraMax, paginazione, sId);
			return Response.ok().entity(res)
					.header(ApiHeaderParamEnum.ROWS_NUMBER.getCode(), PaginazioneUtils.getTotalCountFromList(res))
					.build();

		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaPraticaPost(ModelPraticaPost body, String shibIdentitaCodiceFiscale, Integer sId,
			Integer enteId, Integer profiloId, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaPraticaPost";
		try {
			ModelPraticaPost res = praticaService.praticaPost(body, enteId, profiloId, shibIdentitaCodiceFiscale, sId);

			return Response.status(Status.CREATED).entity(res).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override

	public Response funzionalitaPraticaDettaglioPost(ModelPraticaDettaglio body, String shibIdentitaCodiceFiscale,
			Integer sId, Integer profiloId, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaPraticaPost";
		try {
			ModelPraticaDettaglio res = praticaService.praticaDettaglioPost(body, profiloId, shibIdentitaCodiceFiscale,
					sId);

			return Response.status(Status.CREATED).entity(res).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaPraticaListaGet(Integer sId, Integer enteId, Integer strutturaId, Integer profiloId,
			Integer tipoPraticaId, Integer statoPraticaId, Integer tipoPrescrizioneId, Integer statoPrescrizioneId,
			String dataAperturaPrescrizioneDa, String dataAperturaPrescrizioneA, String dataChiusuraPrescrizioneDa,
			String dataChiusuraPrescrizioneA, Integer tipoAppuntamentoId, Integer statoAppuntamentoId,
			String dataInizioAppuntamentoDa, String dataInizioAppuntamentoA, String dataFineAppuntamentoDa,
			String dataFineAppuntamentoA, String dataPraticaAperturaDa, String dataPraticaAperturaA,
			String dataPraticaChiusuraDa, String dataPraticaChiusuraA, Long pageNumber, Long rowPerPage,
			Boolean descending, String orderBy, String filterStruttura, String filterUtente,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaPraticaInseribilieGet";
		try {

			var paginazione = new Paginazione(orderBy, rowPerPage, pageNumber, descending);
			var res = praticaService.getPratiche(enteId, strutturaId, profiloId, tipoPraticaId, statoPraticaId,
					tipoPrescrizioneId, statoPrescrizioneId, dataAperturaPrescrizioneDa, dataAperturaPrescrizioneA,
					dataChiusuraPrescrizioneDa, dataChiusuraPrescrizioneA, tipoAppuntamentoId, statoAppuntamentoId,
					dataInizioAppuntamentoDa, dataInizioAppuntamentoA, dataFineAppuntamentoDa, dataFineAppuntamentoA,
					dataPraticaAperturaDa, dataPraticaAperturaA, dataPraticaChiusuraDa, dataPraticaChiusuraA,
					filterStruttura, filterUtente, paginazione, sId);
			return Response.ok().entity(res)
					.header(ApiHeaderParamEnum.ROWS_NUMBER.getCode(), PaginazioneUtils.getTotalCountFromList(res))
					.build();

		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}

	}

	@Override
	public Response funzionalitaPraticaAzioneGet(Integer sId, Integer profiloId, Integer praticaTipoId,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaPraticaAzioneGet";
		try {
			AzioniEstese res = praticaService.praticaAzioniGet(profiloId, praticaTipoId, sId);

			return Response.ok(res).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaPraticaDettaglioScadenzeGet(Integer sId, Integer profiloId, Integer enteId,
			Integer strutturaId, String dataDa, String dataA, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaPraticaDettaglioScadenzeGet";
		try {
			RESTErrorUtil.checkNotNull(dataDa, ErrorCodeEnum.DATA_DA_OBBLIGATORIA);
			RESTErrorUtil.checkNotNull(dataA, ErrorCodeEnum.DATA_A_OBBLIGATORIA);

			List<ModelPraticaScadenza> res = praticaService.praticaDettaglioPost(profiloId, enteId, strutturaId, dataDa,
					dataA, sId);

			return Response.ok(res).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaPraticaGet(Integer sId, Integer praticaId, Integer profiloId, Integer enteId,
			Integer strutturaId, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaPraticaGet";
		try {
			RESTErrorUtil.checkNotNull(praticaId, ErrorCodeEnum.PRATICA_ID_OBBLIGATORIA);

			PraticaEstesa res = praticaService.praticaGet(praticaId, profiloId, enteId, strutturaId, null, sId);

			return Response.ok(res).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaPraticaModuloPost(ModelPraticaPost body, String shibIdentitaCodiceFiscale, Integer sId,
			Integer enteId, Integer profiloId, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaPraticaModuloPost";
		try {
			ModelPraticaPost res = praticaService.praticaModificaPost(body, enteId, profiloId,
					shibIdentitaCodiceFiscale, sId);

			return Response.status(Status.CREATED).entity(res).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaPraticaGeneraDiairioGet(ModelChiaveValoreList body, Integer sId, Integer praticaId,
			Integer profiloId, Integer enteId, Integer strutturaId, String filtroData, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaPraticaGeneraDiairioGet";
		try {
			RESTErrorUtil.checkNotNull(praticaId, ErrorCodeEnum.PRATICA_ID_OBBLIGATORIA);

			FileCustom fileCustom = praticaService.generaDiario(body, praticaId, filtroData, sId);

			return Response.status(Status.CREATED).entity(fileCustom.getFile()).type(fileCustom.getContentType())
					.header(ApiHeaderParamEnum.CONTENT_LENGTH.getCode(), String.valueOf(fileCustom.getContentLength()))
					.header(ApiHeaderParamEnum.CONTENT_DISPOSITION.getCode(),
							"attachment; filename=\"" + fileCustom.getFileName() + "\"")
					.build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaPraticaModuliZipListaGet(Integer sId, Integer praticaId, Integer profiloId,
			Integer enteId, Integer strutturaId, String filtroData, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaPraticaModuliZipListaGet";
		try {
			RESTErrorUtil.checkNotNull(praticaId, ErrorCodeEnum.PRATICA_ID_OBBLIGATORIA);

			PraticaEstesa pratica = praticaService.praticaGet(praticaId, profiloId, enteId, strutturaId, filtroData,
					sId);
			ModelZipLista res = praticaService.getModuliZip(pratica);

			return Response.ok(res).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaPraticaRequisitiGet(Integer sId, Integer praticaId, Integer prescrizioneId,
			Integer praticaDetId, Integer clreqId, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaPraticaRequisitiGet";
		try {
			RESTErrorUtil.checkNotNull(clreqId, ErrorCodeEnum.CLREQ_ID_OBBLIGATORIO);

			return Response.ok()
					.entity(praticaService.getPraticaRequisito(praticaId, prescrizioneId, praticaDetId, clreqId, sId))
					.build();

		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaPraticaRequisitiListaGet(Integer sId, Integer praticaId, Integer prescrizioneId,
			Integer praticaDetId, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaPraticaRequisitiListaGet";
		try {

			return Response.ok()
					.entity(praticaService.getPraticaRequisitiLista(praticaId, prescrizioneId, praticaDetId, sId))
					.build();

		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaPraticaRequisitiPost(ModelPraticaClreqPost body, String shibIdentitaCodiceFiscale,
			Integer sId, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaPraticaRequisitiPost";
		try {
			RESTErrorUtil.checkNotNull(body.getPraticaId(), ErrorCodeEnum.PRATICA_ID_OBBLIGATORIA);

			ModelPraticaClreqPost res = praticaService.praticaRequisitiPost(body, shibIdentitaCodiceFiscale, sId);

			return Response.status(Status.CREATED).entity(res).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaPraticaDettaglioRequisitiDeletePost(ModelPraticaDettaglioClreq body,
			String shibIdentitaCodiceFiscale, Integer sId, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaPraticaDettaglioRequisitiDeletePost";
		try {
			RESTErrorUtil.checkNotNull(body.getPraticaDetId(), ErrorCodeEnum.PRATICA_DET_ID_OBBLIGATORIO);
			RESTErrorUtil.checkNotNull(body.getClreqId(), ErrorCodeEnum.CLREQ_ID_OBBLIGATORIO);

			ModelPraticaDettaglioClreq res = praticaService.praticaDettaglioRequisitiDeletePost(body,
					shibIdentitaCodiceFiscale, sId);

			return Response.status(Status.CREATED).entity(res).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaPraticaDettaglioRequisitiPost(ModelPraticaDettaglioClreq body,
			String shibIdentitaCodiceFiscale, Integer sId, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaPraticaDettaglioRequisitiPost";
		try {
			RESTErrorUtil.checkNotNull(body.getPraticaDetId(), ErrorCodeEnum.PRATICA_DET_ID_OBBLIGATORIO);
			RESTErrorUtil.checkNotNull(body.getClreqId(), ErrorCodeEnum.CLREQ_ID_OBBLIGATORIO);

			ModelPraticaDettaglioClreq res = praticaService.praticaDettaglioRequisitiPost(body,
					shibIdentitaCodiceFiscale, sId);

			return Response.status(Status.CREATED).entity(res).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaPraticaDettaglioRequisitiListaGet(Integer sId, Integer praticaDetId,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaPraticaDettaglioRequisitiListaGet";
		try {
			RESTErrorUtil.checkNotNull(praticaDetId, ErrorCodeEnum.PRATICA_DET_ID_OBBLIGATORIO);

			return Response.ok().entity(praticaService.getPraticaDettaglioRequisitiLista(praticaDetId, sId)).build();

		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaPraticaRequisitiEsitoListaGet(Integer sId, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaPraticaRequisitiEsitoListaGet";
		try {

			return Response.ok().entity(praticaService.getPraticaRequisitiEsitoLista(sId)).build();

		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}
}