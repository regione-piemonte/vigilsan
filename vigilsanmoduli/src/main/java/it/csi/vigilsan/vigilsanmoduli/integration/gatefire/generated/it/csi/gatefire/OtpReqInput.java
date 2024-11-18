/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per otpReqInput complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="otpReqInput">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="callInfo" type="{http://www.csi.it/gatefire/}callInfo" minOccurs="0"/>
 *         &lt;element name="identity" type="{http://www.csi.it/gatefire/}identity" minOccurs="0"/>
 *         &lt;element name="otpCredentialsType" type="{http://www.csi.it/gatefire/}otpCredentialsType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "otpReqInput", propOrder = {
    "callInfo",
    "identity",
    "otpCredentialsType"
})
public class OtpReqInput {

    protected CallInfo callInfo;
    protected Identity identity;
    protected OtpCredentialsType otpCredentialsType;

    /**
     * Recupera il valore della propriet� callInfo.
     * 
     * @return
     *     possible object is
     *     {@link CallInfo }
     *     
     */
    public CallInfo getCallInfo() {
        return callInfo;
    }

    /**
     * Imposta il valore della propriet� callInfo.
     * 
     * @param value
     *     allowed object is
     *     {@link CallInfo }
     *     
     */
    public void setCallInfo(CallInfo value) {
        this.callInfo = value;
    }

    /**
     * Recupera il valore della propriet� identity.
     * 
     * @return
     *     possible object is
     *     {@link Identity }
     *     
     */
    public Identity getIdentity() {
        return identity;
    }

    /**
     * Imposta il valore della propriet� identity.
     * 
     * @param value
     *     allowed object is
     *     {@link Identity }
     *     
     */
    public void setIdentity(Identity value) {
        this.identity = value;
    }

    /**
     * Recupera il valore della propriet� otpCredentialsType.
     * 
     * @return
     *     possible object is
     *     {@link OtpCredentialsType }
     *     
     */
    public OtpCredentialsType getOtpCredentialsType() {
        return otpCredentialsType;
    }

    /**
     * Imposta il valore della propriet� otpCredentialsType.
     * 
     * @param value
     *     allowed object is
     *     {@link OtpCredentialsType }
     *     
     */
    public void setOtpCredentialsType(OtpCredentialsType value) {
        this.otpCredentialsType = value;
    }

}
