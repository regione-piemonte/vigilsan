/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.vigilsan.vigilsancommon.api.generated.StrutturaApi;
import it.csi.vigilsan.vigilsancommon.api.generated.dto.ModelStruttura;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.Struttura;
import it.csi.vigilsan.vigilsancommon.api.service.EnteService;
import it.csi.vigilsan.vigilsancommon.api.service.StrutturaNaturaService;
import it.csi.vigilsan.vigilsancommon.api.service.StrutturaService;
import it.csi.vigilsan.vigilsancommon.api.service.impl.StrutturaAccreditamentoServiceImpl;
import it.csi.vigilsan.vigilsancommon.api.service.impl.StrutturaCategoriaServiceImpl;
import it.csi.vigilsan.vigilsancommon.api.service.impl.StrutturaTipoServiceImpl;
import it.csi.vigilsan.vigilsancommon.api.service.impl.StrutturaTitolaritaServiceImpl;
import it.csi.vigilsan.vigilsancommon.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsancommon.util.LogMessageEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.util.ApiHeaderParamEnum;
import it.csi.vigilsan.vigilsanutil.generic.util.paginazione.Paginazione;
import it.csi.vigilsan.vigilsanutil.generic.util.paginazione.PaginazioneUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Component
public class StrutturaApiImpl extends RESTBaseService implements StrutturaApi {

	@Autowired
	private StrutturaService strutturaService;
	
	@Autowired
	private EnteService enteService;

	@Autowired
	private StrutturaNaturaService strutturaNaturaService;

	@Autowired
	private StrutturaAccreditamentoServiceImpl strutturaAccreditamentoService;

	@Autowired
	private StrutturaTipoServiceImpl strutturaTipoService;

	@Autowired
	private StrutturaCategoriaServiceImpl strutturaCategoriaService;

	@Autowired
	private StrutturaTitolaritaServiceImpl strutturaTitolaritaService;

	@Override
	public Response funzionalitaStrutturaGet(String shibIdentitaCodiceFiscale, String xRequestId,
			String xCodiceServizio, @NotNull Integer strutturaId, @NotNull Integer sId, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaStrutturaGet";
		try {
			RESTErrorUtil.checkNotNull(strutturaId, ErrorCodeEnum.STRUTTURA_ID_OBBLIGATORIO);
			var res = strutturaService.getStrutturaByStrutturaId(sId, strutturaId);
			return Response.ok().entity(res).build();
			
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaStrutturaPost(ModelStruttura body, String shibIdentitaCodiceFiscale, Integer sId,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

		var methodName = "funzionalitaStrutturaPost";
		try {
			// Controllo i campi obbligatori 
			checkCorrettezzaDati(body, sId);
			// Trasformo l'oggetto ModelStruttura in oggetto Struttura
			Struttura strutturaToInsert = creaStruttura(body, sId);
			
//AT			var res = strutturaService.postStrutturaInserimento(sId, shibIdentitaCodiceFiscale, strutturaToInsert);
//AT			return Response.ok().entity(res).build();
		    return Response.ok().entity(null).build();
			
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaStrutturaAslGet(String shibIdentitaCodiceFiscale, String xRequestId,
			String xCodiceServizio, @NotNull Integer strutturaId, Integer sId, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaStrutturaAslGet";
		try {
			RESTErrorUtil.checkNotNull(strutturaId, ErrorCodeEnum.STRUTTURA_ID_OBBLIGATORIO);
			var res = enteService.getAslByStrutturaId(sId, strutturaId);
			return Response.ok().entity(res).build();
			
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}


	@Override
	public Response funzionalitaStrutturaNaturaGet(String shibIdentitaCodiceFiscale, String xRequestId,
			String xCodiceServizio, @NotNull Integer strutturaNaturaId, Integer sId, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaStrutturaNaturaGet";
		try {
			RESTErrorUtil.checkNotNull(strutturaNaturaId, ErrorCodeEnum.STRUTTURA_NATURA_ID_OBBLIGATORIO);
			var res = strutturaNaturaService.getById(sId, strutturaNaturaId);
			return Response.ok().entity(res).build();
			
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaStrutturaAccreditamentoGet(String shibIdentitaCodiceFiscale, String xRequestId,
			String xCodiceServizio, @NotNull Integer strutturaAccreditamentoId, Integer sId,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaStrutturaAccreditamentoGet";
		try {
			RESTErrorUtil.checkNotNull(strutturaAccreditamentoId, ErrorCodeEnum.STRUTTURA_ACCREDITAMENTO_ID_OBBLIGATORIO);
			return Response.ok().entity(strutturaAccreditamentoService.getById(sId, strutturaAccreditamentoId)).build();
			
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaStrutturaTipoGet(String shibIdentitaCodiceFiscale, String xRequestId,
			String xCodiceServizio, @NotNull Integer strutturaTipoId, Integer sId, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaStrutturaTipoGet";
		try {
			RESTErrorUtil.checkNotNull(strutturaTipoId, ErrorCodeEnum.STRUTTURA_TIPO_ID_OBBLIGATORIO);
			return Response.ok().entity(strutturaTipoService.getById(sId, strutturaTipoId)).build();
			
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaStrutturaCategoriaListaGet(String shibIdentitaCodiceFiscale, String xRequestId,
			String xCodiceServizio, @NotNull Integer strutturaId, Integer sId, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaStrutturaCategoriaListaGet";
		try {
			RESTErrorUtil.checkNotNull(strutturaId, ErrorCodeEnum.STRUTTURA_ID_OBBLIGATORIO);
			return Response.ok().entity(strutturaCategoriaService.getByStrutturaId(sId, strutturaId)).build();
			
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	private void checkCorrettezzaDati(ModelStruttura body, Integer sId) {
		var methodName = "checkCorrettezzaDati";
		logDebug(null, methodName, LogMessageEnum.BEGIN.getMessage());
		
		try {
			logDebug(null, methodName, "Comune Id : ", body.getComuneId());
			RESTErrorUtil.checkNotNull(body.getComuneId(), ErrorCodeEnum.COMUNE_ID_OBBLIGATORIO);
			logDebug(null, methodName, "Struttura Cod : ", body.getStrutturaCod());
			RESTErrorUtil.checkNotNull(body.getStrutturaCod(), ErrorCodeEnum.STRUTTURA_COD_OBBLIGATORIO);
			logDebug(null, methodName, "Struttura Desc : ", body.getStrutturaDesc());
			RESTErrorUtil.checkNotNull(body.getStrutturaDesc(), ErrorCodeEnum.STRUTTURA_DESC_OBBLIGATORIO);
			logDebug(null, methodName, "Struttura Tipo Id : ", body.getStrutturaTipoId());
			RESTErrorUtil.checkNotNull(body.getStrutturaTipoId(), ErrorCodeEnum.STRUTTURA_TIPO_ID_OBBLIGATORIO);	
	
		} finally {
			logDebug(null, methodName, LogMessageEnum.END.getMessage());			
		}		
	}

	private Struttura creaStruttura(ModelStruttura inModelStruttura, Integer sId) {
		var methodName = "creaStruttura";
		logDebug(null, methodName, LogMessageEnum.BEGIN.getMessage());
		
		Struttura struttura = new Struttura();
		try {
			struttura.setComuneId(inModelStruttura.getComuneId());
			logDebug(null, methodName, "Comune Id : ", struttura.getComuneId());
			struttura.setCoordSrid(inModelStruttura.getCoordSrid());
			logDebug(null, methodName, "Coord Srid : ", struttura.getCoordSrid());
			struttura.setCoordX(inModelStruttura.getCoordX());
			logDebug(null, methodName, "Coord X : ", struttura.getCoordX());
			struttura.setCoordY(inModelStruttura.getCoordY());
			logDebug(null, methodName, "Coord Y : ", struttura.getCoordY());
			struttura.setIndirizzo(inModelStruttura.getIndirizzo());
			logDebug(null, methodName, "Indirizzo : ", struttura.getIndirizzo());
			struttura.setStrutturaCod(inModelStruttura.getStrutturaCod());
			logDebug(null, methodName, "Struttura Cod : ", struttura.getStrutturaCod());
			struttura.setStrutturaCodArpe(inModelStruttura.getStrutturaCodArpe());
			logDebug(null, methodName, "Struttura Cod Arpe : ", struttura.getStrutturaCodArpe());
			struttura.setStrutturaCodConfiguratore(inModelStruttura.getStrutturaCodConfiguratore());
			logDebug(null, methodName, "Struttura Cod Configuratore : ", struttura.getStrutturaCodConfiguratore());
			struttura.setStrutturaDesc(inModelStruttura.getStrutturaDesc());
			logDebug(null, methodName, "Struttura Desc : ", struttura.getStrutturaDesc());
			struttura.setStrutturaTipoId(inModelStruttura.getStrutturaTipoId());
			logDebug(null, methodName, "Struttura Tipo Id : ", struttura.getStrutturaTipoId());
			struttura.setStrutturaAccreditamentoId(inModelStruttura.getStrutturaAccreditamentoId());
			logDebug(null, methodName, "Struttura Accreditamento Id : ", struttura.getStrutturaAccreditamentoId());
			struttura.setStrutturaNaturaId(inModelStruttura.getStrutturaNaturaId());
			logDebug(null, methodName, "Struttura Natura Id : ", struttura.getStrutturaNaturaId());

			return struttura;
		} finally {
			logDebug(null, methodName, LogMessageEnum.END.getMessage());			
		}	
	}

	@Override
	public Response funzionalitaStrutturaCategoriaGet(String shibIdentitaCodiceFiscale, String xRequestId,
			String xCodiceServizio, @NotNull Integer strCatId, Integer sId, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaStrutturaCategoriaGet";
		try {
			RESTErrorUtil.checkNotNull(strCatId, ErrorCodeEnum.STR_CAT_ID_OBBLIGATORIO);
			return Response.ok().entity(strutturaCategoriaService.getByStrCatId(sId, strCatId)).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaStrutturaParametroGet(@NotNull String parametroCod, Integer sId, Integer strutturaId,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaDocumentazioneModuliGet";
		try {
			RESTErrorUtil.checkNotNull(strutturaId, ErrorCodeEnum.STRUTTURA_ID_OBBLIGATORIO);

			return Response.ok().entity(strutturaService.getStrutturaParametro(sId, strutturaId, parametroCod)).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaStrutturaTitolaritaGet(String shibIdentitaCodiceFiscale, String xRequestId,
			String xCodiceServizio, @NotNull Integer strutturaTitolaritaId, Integer sId,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaDocumentazioneModuliGet";
		try {
			RESTErrorUtil.checkNotNull(strutturaTitolaritaId, ErrorCodeEnum.STRUTTURA_TITOLARITA_OBBLIGATORIA);

			return Response.ok().entity(strutturaTitolaritaService.getById(sId, strutturaTitolaritaId)).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

	@Override
	public Response funzionalitaStrutturaListaGet(Integer sId, Integer enteId, Integer strutturaId,
			String ruoloEnteStrutturaCod, String filter, Long pageNumber, Long rowPerPage, Boolean descending,
			String orderBy, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "funzionalitaStrutturaGet";
		try {
			RESTErrorUtil.checkNotNull(enteId, ErrorCodeEnum.ENTE_ID_OBBLIGATORIO);
			
			var paginazione = new Paginazione(orderBy, rowPerPage, pageNumber, descending);
			var res = strutturaService.getStrutturaByEnteId(sId, enteId,ruoloEnteStrutturaCod, filter, strutturaId, paginazione);
			return Response.ok().entity(res)
					.header(ApiHeaderParamEnum.ROWS_NUMBER.getCode(), PaginazioneUtils.getTotalCountFromList(res))
					.build();
			
		} catch (Exception e) {
			return generalExceptionReturn(methodName, sId, e);
		}
	}

}