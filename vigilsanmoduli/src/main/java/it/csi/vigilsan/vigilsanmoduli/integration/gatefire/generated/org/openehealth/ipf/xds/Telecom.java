/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.org.openehealth.ipf.xds;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per Telecom complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Telecom">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="use" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="countryCode" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="areaCityCode" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="localNumber" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="extension" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="unformattedPhoneNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Telecom", propOrder = {
    "use",
    "type",
    "email",
    "countryCode",
    "areaCityCode",
    "localNumber",
    "extension",
    "unformattedPhoneNumber"
})
public class Telecom {

    protected String use;
    protected String type;
    protected String email;
    protected Long countryCode;
    protected Long areaCityCode;
    protected Long localNumber;
    protected Long extension;
    protected String unformattedPhoneNumber;

    /**
     * Recupera il valore della propriet� use.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUse() {
        return use;
    }

    /**
     * Imposta il valore della propriet� use.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUse(String value) {
        this.use = value;
    }

    /**
     * Recupera il valore della propriet� type.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Imposta il valore della propriet� type.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Recupera il valore della propriet� email.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Imposta il valore della propriet� email.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Recupera il valore della propriet� countryCode.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCountryCode() {
        return countryCode;
    }

    /**
     * Imposta il valore della propriet� countryCode.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCountryCode(Long value) {
        this.countryCode = value;
    }

    /**
     * Recupera il valore della propriet� areaCityCode.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAreaCityCode() {
        return areaCityCode;
    }

    /**
     * Imposta il valore della propriet� areaCityCode.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAreaCityCode(Long value) {
        this.areaCityCode = value;
    }

    /**
     * Recupera il valore della propriet� localNumber.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getLocalNumber() {
        return localNumber;
    }

    /**
     * Imposta il valore della propriet� localNumber.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setLocalNumber(Long value) {
        this.localNumber = value;
    }

    /**
     * Recupera il valore della propriet� extension.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getExtension() {
        return extension;
    }

    /**
     * Imposta il valore della propriet� extension.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setExtension(Long value) {
        this.extension = value;
    }

    /**
     * Recupera il valore della propriet� unformattedPhoneNumber.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnformattedPhoneNumber() {
        return unformattedPhoneNumber;
    }

    /**
     * Imposta il valore della propriet� unformattedPhoneNumber.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnformattedPhoneNumber(String value) {
        this.unformattedPhoneNumber = value;
    }

}
