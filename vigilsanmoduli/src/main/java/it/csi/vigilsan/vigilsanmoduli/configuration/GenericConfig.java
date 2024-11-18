/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
@ComponentScan("it.csi.vigilsan.vigilsanutil.generic")
@ComponentScan("it.csi.vigilsan.vigilsanutil.be")
public class GenericConfig {


	@Bean
	public NamedParameterJdbcTemplate dmaccTemplate( DataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
	}



}
