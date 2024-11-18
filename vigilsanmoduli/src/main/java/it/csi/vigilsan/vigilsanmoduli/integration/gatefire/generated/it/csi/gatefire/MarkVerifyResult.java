/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per markVerifyResult complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="markVerifyResult">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.csi.it/gatefire/}baseResult">
 *       &lt;sequence>
 *         &lt;element name="certificate" type="{http://www.csi.it/gatefire/}certificate" minOccurs="0"/>
 *         &lt;element name="timeStamp" type="{http://www.csi.it/gatefire/}timeStamp" minOccurs="0"/>
 *         &lt;element name="valid" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="validation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="validationMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "markVerifyResult", propOrder = {
    "certificate",
    "timeStamp",
    "valid",
    "validation",
    "validationMessage"
})
public class MarkVerifyResult
    extends BaseResult
{

    protected Certificate certificate;
    protected TimeStamp timeStamp;
    protected Boolean valid;
    protected String validation;
    protected String validationMessage;

    /**
     * Recupera il valore della propriet� certificate.
     * 
     * @return
     *     possible object is
     *     {@link Certificate }
     *     
     */
    public Certificate getCertificate() {
        return certificate;
    }

    /**
     * Imposta il valore della propriet� certificate.
     * 
     * @param value
     *     allowed object is
     *     {@link Certificate }
     *     
     */
    public void setCertificate(Certificate value) {
        this.certificate = value;
    }

    /**
     * Recupera il valore della propriet� timeStamp.
     * 
     * @return
     *     possible object is
     *     {@link TimeStamp }
     *     
     */
    public TimeStamp getTimeStamp() {
        return timeStamp;
    }

    /**
     * Imposta il valore della propriet� timeStamp.
     * 
     * @param value
     *     allowed object is
     *     {@link TimeStamp }
     *     
     */
    public void setTimeStamp(TimeStamp value) {
        this.timeStamp = value;
    }

    /**
     * Recupera il valore della propriet� valid.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isValid() {
        return valid;
    }

    /**
     * Imposta il valore della propriet� valid.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setValid(Boolean value) {
        this.valid = value;
    }

    /**
     * Recupera il valore della propriet� validation.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidation() {
        return validation;
    }

    /**
     * Imposta il valore della propriet� validation.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidation(String value) {
        this.validation = value;
    }

    /**
     * Recupera il valore della propriet� validationMessage.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidationMessage() {
        return validationMessage;
    }

    /**
     * Imposta il valore della propriet� validationMessage.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidationMessage(String value) {
        this.validationMessage = value;
    }

}
