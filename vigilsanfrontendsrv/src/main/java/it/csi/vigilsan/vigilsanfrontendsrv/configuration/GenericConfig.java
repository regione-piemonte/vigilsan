/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("it.csi.vigilsan.vigilsanutil.generic")
@ComponentScan("it.csi.vigilsan.vigilsanutil.bff")
public class GenericConfig {

}
