/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity( 	
		  jsr250Enabled = true)
public class SpringSecurityConfig {
	
	@Bean
	protected SecurityFilterChain configureHttpBasic(HttpSecurity http) throws Exception {
		
		http
			// Portezione CSRF diabilitata in quanto questo progetto è inteso per essere utilizzato come 
			// esposizione servizi via API, non come BFF (Backend for frontend) e quindi non accessibile 
			// direttamente da Browser.
			.csrf(csrf -> csrf.disable())

			// TODO eventuale basic auht o autorizzazioni sulle request
			//.authorizeHttpRequests(ahr -> ahr
			//		  .anyRequest().authenticated()
			//)
			//.httpBasic(hb -> {})
			
			;
		
		
		return http.build();
	}
	

}