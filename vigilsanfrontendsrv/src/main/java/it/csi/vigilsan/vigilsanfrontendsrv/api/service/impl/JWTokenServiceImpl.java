/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.api.service.impl;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.google.gson.Gson;

import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.dto.ModelProfiloUtente;
import it.csi.vigilsan.vigilsanfrontendsrv.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTException;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableApiServiceImpl;

@Service
public class JWTokenServiceImpl extends AuditableApiServiceImpl {

	private static final int ORE_IN_SECONDI_15 = 60*60*15;
	
	@Value("${tokenjwt.secret.key:keyditest}")
	private String scrtKey;

	public String generateJWT(String ip, String codiceFiscale, Object i) {
		return JWT.create().withIssuer("Profilo").withSubject("Dettagli Profilo")
				.withClaim("profilo", new Gson().toJson(i)).withClaim("ip", ip).withClaim("cf", codiceFiscale).withIssuedAt(new Date())
				.withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(5)))
				.withJWTId(UUID.randomUUID().toString()).withNotBefore(new Date(System.currentTimeMillis()))
				.sign(getAlgoritm());
	}

	private ModelProfiloUtente readJWT(String jwtToken, JWTVerifier verifier, String ip, String codiceFiscale) {
		try {
			DecodedJWT decodedJWT = verifier.verify(jwtToken);
//			TODO: AGGIUNGI CONTROLLO IP
			if (!decodedJWT.getClaim("ip").asString().equals(ip)) {
				throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.IP_NON_VALIDO);
			}
			if (!decodedJWT.getClaim("cf").asString().equals(codiceFiscale)) {
				throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.IP_NON_VALIDO);
			}
			var claim = decodedJWT.getClaim("profilo");
			ModelProfiloUtente profilo = (new Gson()).fromJson(claim.asString(), ModelProfiloUtente.class);
			return profilo;
		} catch (JWTVerificationException e) {
			// TODO: SPECIFICA QUESTO ERRORE
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.TOKEN_SCADUTO);
		}
	}

	public ModelProfiloUtente readJWT(String jwtToken, String ip, String codiceFiscale) {
		JWTVerifier verifier = JWT.require(getAlgoritm()).withIssuer("Profilo").build();
		return readJWT(jwtToken, verifier, ip, codiceFiscale);
	}

	public ModelProfiloUtente readJWTlogout(String jwtToken, String ip, String codiceFiscale) {
		try {

			JWTVerifier verifier = JWT.require(getAlgoritm()).withIssuer("Profilo").acceptExpiresAt(ORE_IN_SECONDI_15).build();
			return readJWT(jwtToken, verifier, ip, codiceFiscale);
		} catch (RESTException e) {
			logWarn(null, "readJWTlogout", "eccezione gestita IllegalArgumentException con message: ", e.getMessage());
			return new ModelProfiloUtente();
		}
	}

	private Algorithm getAlgoritm() {
		return Algorithm.HMAC256(scrtKey);
	}
}
