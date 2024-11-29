/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanbuonores.api;

import it.csi.vigilsan.vigilsanbuonores.api.dto.*;



import java.util.List;
import java.util.Map;

import java.io.InputStream;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.validation.constraints.*;
@Path("/status")


public interface StatusApi  {
   
    /**
     * summary = 
     * description = ping
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = OK<br>
           schema implementation = { @see String }</p>
         </li>
       </ul>
    */
    @GET
    
    
    @Produces({ "application/problem+json" })
    Response statusGet(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

}
