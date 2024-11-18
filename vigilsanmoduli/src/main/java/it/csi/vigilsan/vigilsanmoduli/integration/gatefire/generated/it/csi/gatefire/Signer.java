/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire;

import javax.xml.datatype.XMLGregorianCalendar;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per signer complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="signer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="certificate" type="{http://www.csi.it/gatefire/}certificate" minOccurs="0"/>
 *         &lt;element name="signTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="signTimeStr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="signValidation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="signValidationMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="timestamp" type="{http://www.csi.it/gatefire/}timeStamp" minOccurs="0"/>
 *         &lt;element name="valid" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "signer", propOrder = {
    "certificate",
    "signTime",
    "signTimeStr",
    "signValidation",
    "signValidationMessage",
    "timestamp",
    "valid"
})
public class Signer {

    protected Certificate certificate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar signTime;
    protected String signTimeStr;
    protected String signValidation;
    protected String signValidationMessage;
    protected TimeStamp timestamp;
    protected Boolean valid;

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
     * Recupera il valore della propriet� signTime.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSignTime() {
        return signTime;
    }

    /**
     * Imposta il valore della propriet� signTime.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSignTime(XMLGregorianCalendar value) {
        this.signTime = value;
    }

    /**
     * Recupera il valore della propriet� signTimeStr.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignTimeStr() {
        return signTimeStr;
    }

    /**
     * Imposta il valore della propriet� signTimeStr.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignTimeStr(String value) {
        this.signTimeStr = value;
    }

    /**
     * Recupera il valore della propriet� signValidation.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignValidation() {
        return signValidation;
    }

    /**
     * Imposta il valore della propriet� signValidation.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignValidation(String value) {
        this.signValidation = value;
    }

    /**
     * Recupera il valore della propriet� signValidationMessage.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignValidationMessage() {
        return signValidationMessage;
    }

    /**
     * Imposta il valore della propriet� signValidationMessage.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignValidationMessage(String value) {
        this.signValidationMessage = value;
    }

    /**
     * Recupera il valore della propriet� timestamp.
     * 
     * @return
     *     possible object is
     *     {@link TimeStamp }
     *     
     */
    public TimeStamp getTimestamp() {
        return timestamp;
    }

    /**
     * Imposta il valore della propriet� timestamp.
     * 
     * @param value
     *     allowed object is
     *     {@link TimeStamp }
     *     
     */
    public void setTimestamp(TimeStamp value) {
        this.timestamp = value;
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

}
