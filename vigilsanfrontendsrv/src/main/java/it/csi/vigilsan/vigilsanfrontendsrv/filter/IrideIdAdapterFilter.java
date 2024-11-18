/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.filter;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import it.csi.iride2.policy.entity.Identita;
import it.csi.iride2.policy.exceptions.MalformedIdTokenException;

@Component
@Order(1)
public class IrideIdAdapterFilter  implements Filter {
	private static final Logger LOG = LoggerFactory.getLogger(IrideIdAdapterFilter.class);

	public static final String IRIDE_ID_REQ_ATTR = "iride2_id";
	public static final String AUTH_ID_MARKER = "Shib-Iride-IdentitaDigitale";
	public static final String AUTH_ID_MARKER2 = "shib-iride-identitadigitale";
	public static final String UTENTE_REQ_ATTR = "utente";
	public static final String DEVMODE_INIT_PARAM = "devmode";
	private boolean devmode = false;

	
	@Override
	public void init(FilterConfig fc) throws ServletException {
		String devmodeParam = fc.getInitParameter(DEVMODE_INIT_PARAM);
		
		this.devmode = "true".equalsIgnoreCase(devmodeParam);
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain fchn)
			throws IOException, ServletException {
		
		if (!(req instanceof HttpServletRequest)) {
			fchn.doFilter(req, resp);
			return;
		}
		HttpServletRequest hreq = (HttpServletRequest) req;
		HttpServletResponse hresp = (HttpServletResponse) resp;

		
		
		String token = getToken(hreq);
		LOG.debug("doFilter - token: {} for url: {} ", token, hreq.getRequestURL());
		
		if (token == null) {
			// il marcatore deve sempre essere presente altrimenti e' una
			// condizione di errore (escluse le pagine home e di servizio)
			if (mustCheckPage(hreq.getRequestURI())) {
				// LOG.error("[IrideIdAdapterFilter::doFilter] Tentativo di accesso a pagina non
				// home e non di servizio senza token di sicurezza");
				hresp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				PrintWriter pw = hresp.getWriter();
				pw.append("{\"error\": \"Tentativo di accesso a pagina non home e non di servizio senza token di sicurezza\"}");
				
				return;
			}
			fchn.doFilter(hreq, hresp);
			return;
		}

		Identita identita;
		try {
			identita = new Identita(normalizeToken(token));
		} catch (MalformedIdTokenException e) {
			LOG.error("Token iride non valido: {} - {}", token, e.getMessage(), e);
			hresp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			hresp.setContentType("text/json");
			PrintWriter pw = hresp.getWriter();
			pw.append("{\"error\": \"Token di sicurezza non valido\"}");
			return;
		}
		

		hreq.setAttribute(IRIDE_ID_REQ_ATTR, identita);

		fchn.doFilter(hreq, hresp);
	}

	private boolean mustCheckPage(String requestURI) {
		return requestURI.startsWith("/") && !requestURI.startsWith("/lgspaweb/api/test");
	}
	
	@Override
	public void destroy() {
		LOG.debug("destroy");
	}

	public String getToken(HttpServletRequest httpreq) {
		String marker = (String) httpreq.getHeader(AUTH_ID_MARKER);
		if (marker == null) marker =  (String) httpreq.getHeader(AUTH_ID_MARKER2);
		if (marker == null && devmode) {
			return getTokenDevMode(httpreq);
		}
		return marker;
	}

	private String getTokenDevMode(HttpServletRequest httpreq) {
		String marker = (String) httpreq.getParameter(AUTH_ID_MARKER);
		if (marker == null) marker =  (String) httpreq.getParameter(AUTH_ID_MARKER2);
		return marker;
	}
	private String normalizeToken(String token) {
		return token;
	}
	

}
