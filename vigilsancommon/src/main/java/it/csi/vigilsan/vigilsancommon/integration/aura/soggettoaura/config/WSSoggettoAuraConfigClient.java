/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.integration.aura.soggettoaura.config;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.wss4j.common.ConfigurationConstants;
import org.apache.wss4j.common.WSS4JConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;

import it.csi.vigilsan.vigilsancommon.integration.aura.configurazione.CustomLoggingInterceptor;
import it.csi.vigilsan.vigilsancommon.integration.aura.soggettoaura.client.SoggettoAuraServiceClient;
import jakarta.xml.bind.Marshaller;

@Configuration
public class WSSoggettoAuraConfigClient extends WsConfigurerAdapter {

	@Value("${aura.soggetto.user}")
	private String auraSoggettoUsername;

	@Value("${aura.soggetto.password}")
	private String auraSoggettoPassword;

	@Bean
	Jaxb2Marshaller auraSoggettoMarshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		Map<String, Object> configurazioni = new HashMap<String, Object>();
		configurazioni.put(Marshaller.JAXB_ENCODING, StandardCharsets.UTF_8.toString());
		configurazioni.put(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.setMarshallerProperties(configurazioni);
		marshaller.setContextPath("it.csi.aura.auraws.services.central.screeningepatitec");
		return marshaller;
	}

	@Bean(name = "soggettoAuraClient")
	SoggettoAuraServiceClient soggettoAuraServiceClient(Jaxb2Marshaller auraSoggettoMarshaller) {
		SoggettoAuraServiceClient client = new SoggettoAuraServiceClient();
		client.setMarshaller(auraSoggettoMarshaller);
		client.setUnmarshaller(auraSoggettoMarshaller);
		CustomLoggingInterceptor customLogging = new CustomLoggingInterceptor();
		ClientInterceptor[] clientInterceptors = new ClientInterceptor[] { wss4jSecurityInterceptorAuraSoggetto(),
				customLogging };
		client.setInterceptors(clientInterceptors);
		return client;
	}

	@Bean
	Wss4jSecurityInterceptor wss4jSecurityInterceptorAuraSoggetto() {
		Wss4jSecurityInterceptor securityInterceptor = new Wss4jSecurityInterceptor();
		String secAction = String.join(" ", ConfigurationConstants.USERNAME_TOKEN, ConfigurationConstants.TIMESTAMP);
		securityInterceptor.setSecurementUsername(auraSoggettoUsername);
		securityInterceptor.setSecurementPassword(auraSoggettoPassword);
		securityInterceptor.setSecurementPasswordType(WSS4JConstants.PW_TEXT);
		securityInterceptor.setSecurementActions(secAction);
		securityInterceptor.setSecurementTimeToLive(600);
		return securityInterceptor;
	}

}
