/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire;

import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per certificate complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="certificate">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="certPolicyList" type="{http://www.csi.it/gatefire/}certPolicy" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="issuer" type="{http://www.csi.it/gatefire/}issuer" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="revocationInfo" type="{http://www.csi.it/gatefire/}revocationInfo" minOccurs="0"/>
 *         &lt;element name="sn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="subject" type="{http://www.csi.it/gatefire/}subject" minOccurs="0"/>
 *         &lt;element name="timeValid" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="valid" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="validFrom" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="validTo" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "certificate", propOrder = {
    "certPolicyList",
    "issuer",
    "name",
    "revocationInfo",
    "sn",
    "subject",
    "timeValid",
    "valid",
    "validFrom",
    "validTo"
})
public class Certificate {

    @XmlElement(nillable = true)
    protected List<CertPolicy> certPolicyList;
    protected Issuer issuer;
    protected String name;
    protected RevocationInfo revocationInfo;
    protected String sn;
    protected Subject subject;
    protected Boolean timeValid;
    protected Boolean valid;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar validFrom;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar validTo;

    /**
     * Gets the value of the certPolicyList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the certPolicyList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCertPolicyList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CertPolicy }
     * 
     * 
     */
    public List<CertPolicy> getCertPolicyList() {
        if (certPolicyList == null) {
            certPolicyList = new ArrayList<CertPolicy>();
        }
        return this.certPolicyList;
    }

    /**
     * Recupera il valore della propriet� issuer.
     * 
     * @return
     *     possible object is
     *     {@link Issuer }
     *     
     */
    public Issuer getIssuer() {
        return issuer;
    }

    /**
     * Imposta il valore della propriet� issuer.
     * 
     * @param value
     *     allowed object is
     *     {@link Issuer }
     *     
     */
    public void setIssuer(Issuer value) {
        this.issuer = value;
    }

    /**
     * Recupera il valore della propriet� name.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Imposta il valore della propriet� name.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Recupera il valore della propriet� revocationInfo.
     * 
     * @return
     *     possible object is
     *     {@link RevocationInfo }
     *     
     */
    public RevocationInfo getRevocationInfo() {
        return revocationInfo;
    }

    /**
     * Imposta il valore della propriet� revocationInfo.
     * 
     * @param value
     *     allowed object is
     *     {@link RevocationInfo }
     *     
     */
    public void setRevocationInfo(RevocationInfo value) {
        this.revocationInfo = value;
    }

    /**
     * Recupera il valore della propriet� sn.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSn() {
        return sn;
    }

    /**
     * Imposta il valore della propriet� sn.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSn(String value) {
        this.sn = value;
    }

    /**
     * Recupera il valore della propriet� subject.
     * 
     * @return
     *     possible object is
     *     {@link Subject }
     *     
     */
    public Subject getSubject() {
        return subject;
    }

    /**
     * Imposta il valore della propriet� subject.
     * 
     * @param value
     *     allowed object is
     *     {@link Subject }
     *     
     */
    public void setSubject(Subject value) {
        this.subject = value;
    }

    /**
     * Recupera il valore della propriet� timeValid.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isTimeValid() {
        return timeValid;
    }

    /**
     * Imposta il valore della propriet� timeValid.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setTimeValid(Boolean value) {
        this.timeValid = value;
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
     * Recupera il valore della propriet� validFrom.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getValidFrom() {
        return validFrom;
    }

    /**
     * Imposta il valore della propriet� validFrom.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setValidFrom(XMLGregorianCalendar value) {
        this.validFrom = value;
    }

    /**
     * Recupera il valore della propriet� validTo.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getValidTo() {
        return validTo;
    }

    /**
     * Imposta il valore della propriet� validTo.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setValidTo(XMLGregorianCalendar value) {
        this.validTo = value;
    }

}
