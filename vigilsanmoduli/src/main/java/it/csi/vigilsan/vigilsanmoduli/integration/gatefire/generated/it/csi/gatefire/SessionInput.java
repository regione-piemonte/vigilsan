/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per sessionInput complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="sessionInput">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="callInfo" type="{http://www.csi.it/gatefire/}callInfo" minOccurs="0"/>
 *         &lt;element name="signIdentity" type="{http://www.csi.it/gatefire/}signIdentity" minOccurs="0"/>
 *         &lt;element name="signType" type="{http://www.csi.it/gatefire/}signType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sessionInput", propOrder = {
    "callInfo",
    "signIdentity",
    "signType"
})
public class SessionInput {

    protected CallInfo callInfo;
    protected SignIdentity signIdentity;
    protected SignType signType;

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
     * Recupera il valore della propriet� signIdentity.
     * 
     * @return
     *     possible object is
     *     {@link SignIdentity }
     *     
     */
    public SignIdentity getSignIdentity() {
        return signIdentity;
    }

    /**
     * Imposta il valore della propriet� signIdentity.
     * 
     * @param value
     *     allowed object is
     *     {@link SignIdentity }
     *     
     */
    public void setSignIdentity(SignIdentity value) {
        this.signIdentity = value;
    }

    /**
     * Recupera il valore della propriet� signType.
     * 
     * @return
     *     possible object is
     *     {@link SignType }
     *     
     */
    public SignType getSignType() {
        return signType;
    }

    /**
     * Imposta il valore della propriet� signType.
     * 
     * @param value
     *     allowed object is
     *     {@link SignType }
     *     
     */
    public void setSignType(SignType value) {
        this.signType = value;
    }

}
