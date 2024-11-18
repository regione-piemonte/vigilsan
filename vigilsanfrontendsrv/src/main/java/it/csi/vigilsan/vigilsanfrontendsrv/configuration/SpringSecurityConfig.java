/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {
	
	
	// Questo bean attiva la protezione da attacchi CSRF/XSRF: https://it.wikipedia.org/wiki/Cross-site_request_forgery
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		http.csrf(csrf -> csrf.csrfTokenRepository(
//				
//				CookieCsrfTokenRepository.withHttpOnlyFalse()
//				
//				// HttpOnly, di default è true, e NON permette l'accesso via Javascript al cookie XSRF-TOKEN.
//				// Il client Angular necessita per il funzionamento delle chiamate PUT/POST/DELETE di accedere via javascript 
//				// (più precisamente attraverso un interceptor definito nel modulo HttpClientXsrfModule) a
//				// questo Cookie, quindi è necessario impostare HttpOnly=false per permettere il corretto funzionamento di un client
//				// Angular.
//				
//				// https://angular.io/guide/http#security-xsrf-protection
//				
//				));

		http.csrf(csrf -> csrf.disable());

		return http.build();
	}
	
	
}