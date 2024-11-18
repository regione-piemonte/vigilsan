/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.api.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.PraticheApi;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelAppuntamentoSoggetto;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelAzioniEstese;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelPraticaClreqPost;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelPraticaDettaglioClreq;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelPraticaDettaglioClreqEsteso;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelPraticaEstesa;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelPraticaForPost;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelPraticaRidottaEstesa;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelPraticaScadenza;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.impl.PraticheServiceImpl;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.dto.ModelProfiloUtente;
import it.csi.vigilsan.vigilsanfrontendsrv.util.ListaGenericaPaginataWrapper;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.dto.FileCustom;
import it.csi.vigilsan.vigilsanutil.generic.util.ApiHeaderParamEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.SecurityContext;

@Component
public class PraticheApiImpl extends RESTBaseService implements PraticheApi {

	@Autowired
	private PraticheServiceImpl praticheService;

	@Override
	public Response funzionalitaAppuntamentoStatoGet(String xAccessToken, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaAppuntamentoStatoGet";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			return Response.ok().entity(praticheService.getAppuntamentoStato(profiloUtente, httpHeaders))
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

	@Override
	public Response funzionalitaAppuntamentoTipoListaGet(String xAccessToken, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaAppuntamentoTipoListaGet";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			return Response.ok().entity(praticheService.getAppuntamentoTipo(profiloUtente, httpHeaders))
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

	@Override
	public Response funzionalitaPraticaStatoGet(String xAccessToken, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaPraticaStatoGet";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			return Response.ok().entity(praticheService.getPraticaStato(profiloUtente, httpHeaders))
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

	@Override
	public Response funzionalitaPraticaTipoListaGet(String xAccessToken, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaPraticaTipoListaGet";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			return Response.ok().entity(praticheService.getPraticaTipo(profiloUtente, httpHeaders))
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

	@Override
	public Response funzionalitaPraticaInseribilieGet(String xAccessToken, Integer praticaTipoId,
			Integer strutturaTipoId, String dataChiusuraMax, String filterStruttura, Long pageNumber, Long rowPerPage,
			Boolean descending, String orderBy, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaPraticaInseribilieGet";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			ListaGenericaPaginataWrapper<ModelPraticaRidottaEstesa> res = praticheService.getPraticaInseribileLista(
					profiloUtente, praticaTipoId, strutturaTipoId, dataChiusuraMax, filterStruttura, pageNumber,
					rowPerPage, descending, orderBy, httpHeaders);

			return Response.ok().entity(res.getGenericObject()).type(MediaType.APPLICATION_JSON)
					.header(ApiHeaderParamEnum.ROWS_NUMBER.getCode(), res.getRowNumbers()).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

	@Override
	public Response funzionalitaPrescrizioneStatoGet(String xAccessToken, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaPrescrizioneStatoGet";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			return Response.ok().entity(praticheService.getPrescrizioneStato(profiloUtente, httpHeaders))
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

	@Override
	public Response funzionalitaPrescrizioneTipoListaGet(String xAccessToken, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaPrescrizioneTipoListaGet";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			return Response.ok().entity(praticheService.getPrescrizioneTipo(profiloUtente, httpHeaders))
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

	@Override
	public Response funzionalitaNuovapraticaPraticaTipoListaGet(String xAccessToken, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaNuovapraticaPraticaTipoListaGet";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			return Response.ok().entity(praticheService.getNuovapraticaPraticaTipo(profiloUtente, httpHeaders))
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

	@Override
	public Response funzionalitaNuovapraticaStrutturaTipoListaGet(String xAccessToken, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaNuovapraticaStrutturaTipoListaGet";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			return Response.ok().entity(praticheService.getNuovapraticaStrutturaTipo(profiloUtente, httpHeaders))
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

	@Override
	public Response funzionalitaPraticaListaGet(String xAccessToken, Integer tipoPraticaId, Integer statoPraticaId,
			Integer tipoPrescrizioneId, Integer statoPrescrizioneId, String dataAperturaPrescrizioneDa,
			String dataAperturaPrescrizioneA, String dataChiusuraPrescrizioneDa, String dataChiusuraPrescrizioneA,
			Integer tipoAppuntamentoId, Integer statoAppuntamentoId, String dataInizioAppuntamentoDa,
			String dataInizioAppuntamentoA, String dataFineAppuntamentoDa, String dataFineAppuntamentoA,
			String dataPraticaAperturaDa, String dataPraticaAperturaA, String dataPraticaChiusuraDa,
			String dataPraticaChiusuraA, Long pageNumber, Long rowPerPage, Boolean descending, String orderBy,
			String filterStruttura, String filterUtente, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {

		var methodName = "funzionalitaPraticaListaGet";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			ListaGenericaPaginataWrapper<ModelPraticaEstesa> res = praticheService.getPraticaLista(profiloUtente,
					tipoPraticaId, statoPraticaId, tipoPrescrizioneId, statoPrescrizioneId, dataAperturaPrescrizioneDa,
					dataAperturaPrescrizioneA, dataChiusuraPrescrizioneDa, dataChiusuraPrescrizioneA,
					tipoAppuntamentoId, statoAppuntamentoId, dataInizioAppuntamentoDa, dataInizioAppuntamentoA,
					dataFineAppuntamentoDa, dataFineAppuntamentoA, dataPraticaAperturaDa, dataPraticaAperturaA,
					dataPraticaChiusuraDa, dataPraticaChiusuraA, pageNumber, rowPerPage, descending, orderBy,
					filterStruttura, filterUtente, httpHeaders);
			return Response.ok().entity(res.getGenericObject()).type(MediaType.APPLICATION_JSON)
					.header(ApiHeaderParamEnum.ROWS_NUMBER.getCode(), res.getRowNumbers()).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

	@Override
	public Response funzionalitaPraticaAzioneGet(String xAccessToken, Integer praticaTipoId,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

		var methodName = "funzionalitaPraticaListaGet";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			ModelAzioniEstese res = praticheService.getazioni(profiloUtente, praticaTipoId, httpHeaders);
			return Response.ok(res).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

	@Override
	public Response funzionalitaPraticaDettaglioScadenzeGet(String xAccessToken, String dataDa, String dataA,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

		var methodName = "funzionalitaPraticaDettaglioScadenzeGet";
		ModelProfiloUtente profiloUtente = null;
		try {

			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			List<ModelPraticaScadenza> res = praticheService.getPraticaDettaglioScadenze(profiloUtente, dataDa, dataA,
					httpHeaders);
			return Response.ok(res).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

	@Override
	public Response funzionalitaPraticaGet(String xAccessToken, Integer praticaId, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

		var methodName = "funzionalitaPraticaDettaglioScadenzeGet";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			ModelPraticaEstesa res = praticheService.getPratica(profiloUtente, praticaId, httpHeaders);
			return Response.ok(res).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

	@Override
	public Response funzionalitaPraticaPost(ModelPraticaForPost body, String xAccessToken,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaPraticaPost";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			return Response.status(Status.CREATED).entity(praticheService.postPratica(body, profiloUtente, httpHeaders))
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

	@Override
	public Response funzionalitaPraticaDettaglioModuloPost(ModelPraticaForPost body, String xAccessToken,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaPraticaDettaglioModuloPost";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			return Response.status(Status.CREATED)
					.entity(praticheService.postPraticaModulo(body, profiloUtente, httpHeaders))
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

	@Override
	public Response funzionalitaPraticheParametroGet(String shibIdentitaCodiceFiscale, String xAccessToken,
			Integer strutturaId, String code, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaPraticheParametroGet";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			return Response.ok().entity(praticheService.getParametri(profiloUtente, strutturaId, code, httpHeaders))
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

	@Override
	public Response funzionalitaAppuntamentoSoggettoListaGet(String shibIdentitaCodiceFiscale, String xAccessToken,
			Integer appuntamentoId, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaAppuntamentoSoggettoListaGet";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			return Response.ok()
					.entity(praticheService.getAppuntamentoSoggettoLista(profiloUtente, appuntamentoId, httpHeaders))
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

	@Override
	public Response funzionalitaAppuntamentoSoggettoRuoloDecodificaListaGet(String shibIdentitaCodiceFiscale,
			String xAccessToken, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaAppuntamentoSoggettoRuoloDecodificaListaGet";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			return Response.ok()
					.entity(praticheService.getRuoloAppuntamentoSoggettoDecodifica(profiloUtente, httpHeaders))
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

	@Override
	public Response funzionalitaAppuntamentoSoggettoDeletePost(ModelAppuntamentoSoggetto body,
			String shibIdentitaCodiceFiscale, String xAccessToken, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaAppuntamentoSoggettoDeletePost";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			return Response.status(Status.CREATED)
					.entity(praticheService.postAppuntamentoSoggettoDelete(body, profiloUtente, httpHeaders))
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

	@Override
	public Response funzionalitaAppuntamentoSoggettoPost(ModelAppuntamentoSoggetto body,
			String shibIdentitaCodiceFiscale, String xAccessToken, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaAppuntamentoSoggettoPost";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			return Response.status(Status.CREATED)
					.entity(praticheService.postAppuntamentoSoggetto(body, profiloUtente, httpHeaders))
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

	@Override
	public Response funzionalitaPraticaZipGet(String xAccessToken, Integer praticaId, String filtroData,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaPraticaZipGet";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);

			FileCustom fileCustom = praticheService.getPraticaZip(profiloUtente, praticaId, filtroData, httpHeaders);

			return Response.ok().entity(fileCustom.getFile()).type(fileCustom.getContentType())
					.header(ApiHeaderParamEnum.CONTENT_LENGTH.getCode(), fileCustom.getContentLength())
					.header(ApiHeaderParamEnum.CONTENT_DISPOSITION.getCode(), fileCustom.getContentDisposition())
					.build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}
	}

	@Override
	public Response funzionalitaPraticaDettaglioRequisitiDeletePost(ModelPraticaDettaglioClreq body,
			String shibIdentitaCodiceFiscale, String xAccessToken, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaPraticaDettaglioRequisitiDeletePost";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			return Response.status(Status.CREATED)
					.entity(praticheService.postPraticaDettaglioRequisitiDelete(body, profiloUtente, httpHeaders))
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

	@Override
	public Response funzionalitaPraticaDettaglioRequisitiListaGet(String shibIdentitaCodiceFiscale, String xAccessToken,
			Integer praticaDetId, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaPraticaDettaglioRequisitiListaGet";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			return Response.ok()
					.entity(praticheService.getPraticaDettaglioRequisitiLista(praticaDetId, profiloUtente, httpHeaders))
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

	@Override
	public Response funzionalitaPraticaDettaglioRequisitiPost(ModelPraticaDettaglioClreqEsteso body,
			String shibIdentitaCodiceFiscale, String xAccessToken, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaPraticaDettaglioRequisitiPost";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			return Response.status(Status.CREATED)
					.entity(praticheService.postPraticaDettaglioRequisiti(body, profiloUtente, httpHeaders))
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

	@Override
	public Response funzionalitaPraticaRequisitiEsitoListaGet(String shibIdentitaCodiceFiscale, String xAccessToken,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaPraticaRequisitiEsitoListaGet";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			return Response.ok().entity(praticheService.getPraticaRequisitiEsitoDecodifica(profiloUtente, httpHeaders))
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}


	@Override
	public Response funzionalitaPraticaRequisitiListaGet(String shibIdentitaCodiceFiscale, String xAccessToken,
			Integer praticaId, Integer prescrizioneId, Integer praticaDetId, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaPraticaRequisitiListaGet";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			return Response.ok().entity(praticheService.getPraticaRequisitiLista(praticaId,
					prescrizioneId, praticaDetId, profiloUtente, httpHeaders)).type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

	@Override
	public Response funzionalitaPraticaRequisitiGet(String shibIdentitaCodiceFiscale, String xAccessToken,
			Integer praticaId, Integer prescrizioneId, Integer praticaDetId, Integer clreqId,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaPraticaRequisitiGet";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			return Response.ok().entity(praticheService.getPraticaRequisiti(praticaId,
					prescrizioneId, praticaDetId, clreqId, profiloUtente, httpHeaders)).type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

	@Override
	public Response funzionalitaPraticaRequisitiPost(ModelPraticaClreqPost body, String shibIdentitaCodiceFiscale,
			String xAccessToken, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaPraticaRequisitiPost";
		ModelProfiloUtente profiloUtente = null;
		try {
			profiloUtente = getModelProfiloUtente(methodName, httpRequest);
			return Response.status(Status.CREATED)
					.entity(praticheService.postPraticaRequisiti(body, profiloUtente, httpHeaders))
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

}