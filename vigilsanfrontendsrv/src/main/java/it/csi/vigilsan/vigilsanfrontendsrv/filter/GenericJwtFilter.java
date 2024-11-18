/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.filter;

import java.io.IOException;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import it.csi.iride2.policy.entity.Identita;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.impl.JWTokenServiceImpl;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.dto.ModelProfiloUtente;
import it.csi.vigilsan.vigilsanfrontendsrv.util.ApiHeaderUtils;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTException;
import it.csi.vigilsan.vigilsanutil.generic.util.ApiHeaderParamEnum;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(2)
public class GenericJwtFilter implements Filter {
	private static final Logger LOG = LoggerFactory.getLogger(GenericJwtFilter.class);

	public static final String JWT_ID_MARKER = "x-access-token";
	public static final String JWT_PROFILE_ATTR = "profilo-utente";

	@Value("${server.servlet.context-path}")
	private String basePath;

	private boolean devmode = false;

	@Autowired
	private JWTokenServiceImpl jWTokenService;

	@Override
	public void init(FilterConfig fc) throws ServletException {
		var devmodeParam = fc.getInitParameter(IrideIdAdapterFilter.DEVMODE_INIT_PARAM);

		this.devmode = "true".equalsIgnoreCase(devmodeParam);
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain fchn)
			throws IOException, ServletException {

		if (!(req instanceof HttpServletRequest)) {
			fchn.doFilter(req, resp);
			return;
		}
		var hreq = (HttpServletRequest) req;
		var hresp = (HttpServletResponse) resp;
		if (mustCheckPage(hreq.getRequestURI())) {

			var i = (Identita) hreq.getAttribute(IrideIdAdapterFilter.IRIDE_ID_REQ_ATTR);
			var xForwardedFor = hreq.getHeader(ApiHeaderParamEnum.X_FORWARDED_FOR.getCode());
			var jwtToken = getToken(hreq);

			ModelProfiloUtente profilo;
			try {
				var ip = ApiHeaderUtils.getFrontEndIpCaller(xForwardedFor);
				profilo = jWTokenService.readJWT(jwtToken, ip, i.getCodFiscale());

			} catch (RESTException e) {
				var gson = new Gson();
				hresp.setStatus(e.getResponse().getStatus());
				hresp.setContentType("application/json");
				PrintWriter pw = hresp.getWriter();
				pw.append(gson.toJson(e.getResponse().getEntity()));
				return;
			}
			hreq.setAttribute(JWT_PROFILE_ATTR, profilo);
		}
		fchn.doFilter(hreq, hresp);
	}

	private String getToken(HttpServletRequest httpreq) {
		return httpreq.getHeader(JWT_ID_MARKER);
	}

	private boolean mustCheckPage(String requestURI) {
		return requestURI.startsWith("/") && !requestURI.startsWith(basePath + "/lgspaweb/api/test")
				&& !requestURI.startsWith(basePath + "/bff/utente/");
	}

	@Override
	public void destroy() {
		LOG.debug("destroy");
	}
}
