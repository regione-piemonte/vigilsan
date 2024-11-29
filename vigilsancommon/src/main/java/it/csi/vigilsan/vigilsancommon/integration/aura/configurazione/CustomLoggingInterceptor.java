/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.integration.aura.configurazione;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;

import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.xml.transform.StringResult;

import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableWPersistenceApiServiceImpl;

public class CustomLoggingInterceptor extends AuditableWPersistenceApiServiceImpl implements ClientInterceptor {

	@Override
	public boolean handleRequest(MessageContext messageContext) {
		// Ottieni il corpo del messaggio SOAP in uscita
		SoapMessage soapMessage = (SoapMessage) messageContext.getRequest();
		String requestPayload = convertDOMSourceToString(soapMessage.getPayloadSource());

		// Log del corpo del messaggio SOAP in uscita
		logInfo(null, "handleRequest", "Outgoing request message body:\n" + requestPayload);

		return true; // Continua con l'elaborazione normale
	}

	@Override
	public boolean handleResponse(MessageContext messageContext) {
		// Ottieni il corpo del messaggio SOAP in arrivo
		SoapMessage soapMessage = (SoapMessage) messageContext.getResponse();
		String responsePayload = convertDOMSourceToString(soapMessage.getPayloadSource());

		// Log del corpo del messaggio SOAP in arrivo
		logInfo(null, "handleResponse", "Incoming response message body:\n" + responsePayload);

		return true; // Continua con l'elaborazione normale
	}

	// Metodi non utilizzati, devono comunque essere implementati a causa
	// dell'interfaccia
	@Override
	public boolean handleFault(MessageContext messageContext) {

		SoapMessage soapMessage = (SoapMessage) messageContext.getRequest();
		String requestPayload = convertDOMSourceToString(soapMessage.getPayloadSource());

		// Log del corpo del messaggio SOAP in uscita
		logInfo(null, "handleFault", "Outgoing request message body:\n" + requestPayload);
		return true;
	}

	@Override
	public void afterCompletion(MessageContext messageContext, Exception ex) {
		// Non necessario per il logging
	}

//Metodo per convertire DOMSource in stringa XML
	private String convertDOMSourceToString(Source source) {
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
			transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
			transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
			Transformer transformer = transformerFactory.newTransformer();
			StringResult result = new StringResult();
			transformer.transform(source, result);
			return result.toString();
		} catch (TransformerException e) {
			// Gestione dell'eccezione
			logError(null, "convertDOMSourceToString", "error TransformerException: " + e.getMessage(), e);
			return null;
		}
	}
}
