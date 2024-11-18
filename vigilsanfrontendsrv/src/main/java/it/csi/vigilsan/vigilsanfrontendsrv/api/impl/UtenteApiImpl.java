/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.api.impl;

import java.util.HashMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.iride2.policy.entity.Identita;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.UtenteApi;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.impl.JWTokenServiceImpl;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.impl.UtenteServiceImpl;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.VigilsanCommonGetApiEnum;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.dto.ModelProfiloUtente;
import it.csi.vigilsan.vigilsanfrontendsrv.filter.IrideIdAdapterFilter;
import it.csi.vigilsan.vigilsanfrontendsrv.util.ApiHeaderUtils;
import it.csi.vigilsan.vigilsanfrontendsrv.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanfrontendsrv.util.OkhttpCallUtils;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.impl.VigilsanServiceAbstract;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Component
public class UtenteApiImpl extends RESTBaseService implements UtenteApi {

	@Autowired
	private JWTokenServiceImpl jWTokenService;

	@Autowired
	private VigilsanServiceAbstract vigilsanCommonService;

	@Autowired
	private UtenteServiceImpl utenteService;

	@Override
	public Response getJwtToken(String xForwardedFor, @NotNull String token, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		var methodName = "getJwtToken";
		try {
			String ip = ApiHeaderUtils.getFrontEndIpCaller(xForwardedFor);
			Identita i = (Identita) httpRequest.getAttribute(IrideIdAdapterFilter.IRIDE_ID_REQ_ATTR);

			var queryParam = new HashMap<String, Object>();
			OkhttpCallUtils.setQueryParam("token", token, queryParam);
			ModelProfiloUtente profilo = vigilsanCommonService.getGenericGet(VigilsanCommonGetApiEnum.GET_USER_AUTH,
					OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders), queryParam, null);

			verificaPermessiConfiguratoreConPermessiDb(profilo);
			
			var res = utenteService.getModelDecodificaJwt(profilo, httpHeaders, profilo.getSessioneId());
			return Response.ok().entity(res).type(MediaType.APPLICATION_JSON)
					.header("x-access-token", jWTokenService.generateJWT(ip, i.getCodFiscale(), profilo)).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, null, e);
		}

	}

	private void verificaPermessiConfiguratoreConPermessiDb(ModelProfiloUtente profilo) {
		var funzionalitaDb = profilo.getAuth().stream().map(e -> e.getF().toUpperCase()).toList();
		var funzionalitaConfiguratore = profilo.getProfili().stream()
				.filter(p -> p.getProfiloId().equals(profilo.getProfiloId()))
				.collect(Collectors.reducing((a, b) -> null));
		if (!funzionalitaConfiguratore.isPresent()
				|| !funzionalitaDb.stream().allMatch(funzionalitaConfiguratore.get().getFunzione()::contains)) {
//			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.ERRORE_VALIDAZIONE_CONFIGURATORE);
		}

		profilo.getProfili().forEach(p -> p.setFunzione(null));
	}

	@Override
	public Response getJwtTokenWhitProfilo(String shibIdentitaCodiceFiscale, String xAccessToken, String xForwardedFor,
			@NotNull Integer profiloId, Integer strutturaId, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "getJwtTokenWhitProfilo";
		ModelProfiloUtente profiloUtente = null;
		try {
			String ip = ApiHeaderUtils.getFrontEndIpCaller(xForwardedFor);
			Identita i = (Identita) httpRequest.getAttribute(IrideIdAdapterFilter.IRIDE_ID_REQ_ATTR);

			profiloUtente = jWTokenService.readJWT(xAccessToken, ip, i.getCodFiscale());

			profiloUtente = utenteService.aggiornaProfiloUtente(profiloId, strutturaId, profiloUtente, httpHeaders);

			verificaPermessiConfiguratoreConPermessiDb(profiloUtente);
			// controllare struttura se di mia competenza la mantengo
			var res = utenteService.getModelDecodificaJwt(profiloUtente, httpHeaders, profiloUtente.getSessioneId());
			return Response.ok(res)
					.header("x-access-token", jWTokenService.generateJWT(ip, i.getCodFiscale(), profiloUtente)).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}

	@Override
	public Response funzionalitaUtenteLogoutGet(String shibIdentitaCodiceFiscale, String xAccessToken,
			String xForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		var methodName = "funzionalitaUtenteLogoutGet";
		ModelProfiloUtente profiloUtente = null;
		try {
			String ip = ApiHeaderUtils.getFrontEndIpCaller(xForwardedFor);
			Identita i = (Identita) httpRequest.getAttribute(IrideIdAdapterFilter.IRIDE_ID_REQ_ATTR);

			profiloUtente = jWTokenService.readJWTlogout(xAccessToken, ip, i.getCodFiscale());
			return Response.ok().entity(utenteService.getLogout(httpHeaders, profiloUtente.getSessioneId()))
					.type(MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return generalExceptionReturn(methodName, profiloUtente, e);
		}

	}
}