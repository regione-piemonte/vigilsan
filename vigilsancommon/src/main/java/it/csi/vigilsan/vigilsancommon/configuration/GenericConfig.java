/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@ComponentScan("it.csi.vigilsan.vigilsanutil.generic")
@ComponentScan("it.csi.vigilsan.vigilsanutil.be")
@ComponentScan("it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.impl")
@ComponentScan("it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.vigilsanrilevazioni")
public class GenericConfig {

	@Bean
	public NamedParameterJdbcTemplate dmaccTemplate(DataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
	}

	//client per fare chiamate rest
	@Bean
	public WebClient webClient() {
	    return WebClient.builder().build();
	}
}
