/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per xadesInput complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="xadesInput">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="callInfo" type="{http://www.csi.it/gatefire/}callInfo" minOccurs="0"/>
 *         &lt;element name="markIdentity" type="{http://www.csi.it/gatefire/}markIdentity" minOccurs="0"/>
 *         &lt;element name="requiredMark" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="xpath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "xadesInput", propOrder = {
    "callInfo",
    "markIdentity",
    "requiredMark",
    "xpath"
})
public class XadesInput {

    protected CallInfo callInfo;
    protected MarkIdentity markIdentity;
    protected boolean requiredMark;
    protected String xpath;

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
     * Recupera il valore della propriet� markIdentity.
     * 
     * @return
     *     possible object is
     *     {@link MarkIdentity }
     *     
     */
    public MarkIdentity getMarkIdentity() {
        return markIdentity;
    }

    /**
     * Imposta il valore della propriet� markIdentity.
     * 
     * @param value
     *     allowed object is
     *     {@link MarkIdentity }
     *     
     */
    public void setMarkIdentity(MarkIdentity value) {
        this.markIdentity = value;
    }

    /**
     * Recupera il valore della propriet� requiredMark.
     * 
     */
    public boolean isRequiredMark() {
        return requiredMark;
    }

    /**
     * Imposta il valore della propriet� requiredMark.
     * 
     */
    public void setRequiredMark(boolean value) {
        this.requiredMark = value;
    }

    /**
     * Recupera il valore della propriet� xpath.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXpath() {
        return xpath;
    }

    /**
     * Imposta il valore della propriet� xpath.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXpath(String value) {
        this.xpath = value;
    }

}
