/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.configuration;

import org.springframework.stereotype.Component;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@Component
@ApplicationPath("/api")
public class JaxrsApplication extends Application {
    
}