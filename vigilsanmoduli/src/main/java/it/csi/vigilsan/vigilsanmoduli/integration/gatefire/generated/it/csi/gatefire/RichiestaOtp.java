/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per richiestaOtp complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="richiestaOtp">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="otpReqInput" type="{http://www.csi.it/gatefire/}otpReqInput" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "richiestaOtp", propOrder = {
    "otpReqInput"
})
public class RichiestaOtp {

    protected OtpReqInput otpReqInput;

    /**
     * Recupera il valore della propriet� otpReqInput.
     * 
     * @return
     *     possible object is
     *     {@link OtpReqInput }
     *     
     */
    public OtpReqInput getOtpReqInput() {
        return otpReqInput;
    }

    /**
     * Imposta il valore della propriet� otpReqInput.
     * 
     * @param value
     *     allowed object is
     *     {@link OtpReqInput }
     *     
     */
    public void setOtpReqInput(OtpReqInput value) {
        this.otpReqInput = value;
    }

}
