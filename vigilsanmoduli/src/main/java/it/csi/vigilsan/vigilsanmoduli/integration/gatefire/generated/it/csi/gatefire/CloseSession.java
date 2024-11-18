/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per closeSession complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="closeSession">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sessionId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sessionInput" type="{http://www.csi.it/gatefire/}sessionInput" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "closeSession", propOrder = {
    "sessionId",
    "sessionInput"
})
public class CloseSession {

    protected String sessionId;
    protected SessionInput sessionInput;

    /**
     * Recupera il valore della propriet� sessionId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * Imposta il valore della propriet� sessionId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSessionId(String value) {
        this.sessionId = value;
    }

    /**
     * Recupera il valore della propriet� sessionInput.
     * 
     * @return
     *     possible object is
     *     {@link SessionInput }
     *     
     */
    public SessionInput getSessionInput() {
        return sessionInput;
    }

    /**
     * Imposta il valore della propriet� sessionInput.
     * 
     * @param value
     *     allowed object is
     *     {@link SessionInput }
     *     
     */
    public void setSessionInput(SessionInput value) {
        this.sessionInput = value;
    }

}
