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
 * <p>Classe Java per timeStamp complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="timeStamp">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="generationTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="serialNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="signValid" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="tsAuthorityName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "timeStamp", propOrder = {
    "generationTime",
    "serialNumber",
    "signValid",
    "tsAuthorityName"
})
public class TimeStamp {

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar generationTime;
    protected String serialNumber;
    protected Boolean signValid;
    protected String tsAuthorityName;

    /**
     * Recupera il valore della propriet� generationTime.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getGenerationTime() {
        return generationTime;
    }

    /**
     * Imposta il valore della propriet� generationTime.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setGenerationTime(XMLGregorianCalendar value) {
        this.generationTime = value;
    }

    /**
     * Recupera il valore della propriet� serialNumber.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * Imposta il valore della propriet� serialNumber.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSerialNumber(String value) {
        this.serialNumber = value;
    }

    /**
     * Recupera il valore della propriet� signValid.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSignValid() {
        return signValid;
    }

    /**
     * Imposta il valore della propriet� signValid.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSignValid(Boolean value) {
        this.signValid = value;
    }

    /**
     * Recupera il valore della propriet� tsAuthorityName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTsAuthorityName() {
        return tsAuthorityName;
    }

    /**
     * Imposta il valore della propriet� tsAuthorityName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTsAuthorityName(String value) {
        this.tsAuthorityName = value;
    }

}
