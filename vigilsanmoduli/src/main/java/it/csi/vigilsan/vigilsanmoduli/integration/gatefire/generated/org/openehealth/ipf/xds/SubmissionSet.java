/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.org.openehealth.ipf.xds;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per SubmissionSet complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="SubmissionSet">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openehealth.org/ipf/xds}IdentifiedObject">
 *       &lt;sequence>
 *         &lt;element name="sourceId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="submissionTime" type="{http://www.openehealth.org/ipf/xds}Timestamp" minOccurs="0"/>
 *         &lt;element name="author" type="{http://www.openehealth.org/ipf/xds}Author" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="intendedRecipient" type="{http://www.openehealth.org/ipf/xds}Recipient" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="contentTypeCode" type="{http://www.openehealth.org/ipf/xds}Code" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SubmissionSet", propOrder = {
    "sourceId",
    "submissionTime",
    "author",
    "intendedRecipient",
    "contentTypeCode"
})
public class SubmissionSet
    extends IdentifiedObject
{

    protected String sourceId;
    protected Timestamp submissionTime;
    protected List<Author> author;
    protected List<Recipient> intendedRecipient;
    protected Code contentTypeCode;

    /**
     * Recupera il valore della propriet� sourceId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceId() {
        return sourceId;
    }

    /**
     * Imposta il valore della propriet� sourceId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceId(String value) {
        this.sourceId = value;
    }

    /**
     * Recupera il valore della propriet� submissionTime.
     * 
     * @return
     *     possible object is
     *     {@link Timestamp }
     *     
     */
    public Timestamp getSubmissionTime() {
        return submissionTime;
    }

    /**
     * Imposta il valore della propriet� submissionTime.
     * 
     * @param value
     *     allowed object is
     *     {@link Timestamp }
     *     
     */
    public void setSubmissionTime(Timestamp value) {
        this.submissionTime = value;
    }

    /**
     * Gets the value of the author property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the author property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAuthor().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Author }
     * 
     * 
     */
    public List<Author> getAuthor() {
        if (author == null) {
            author = new ArrayList<Author>();
        }
        return this.author;
    }

    /**
     * Gets the value of the intendedRecipient property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the intendedRecipient property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIntendedRecipient().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Recipient }
     * 
     * 
     */
    public List<Recipient> getIntendedRecipient() {
        if (intendedRecipient == null) {
            intendedRecipient = new ArrayList<Recipient>();
        }
        return this.intendedRecipient;
    }

    /**
     * Recupera il valore della propriet� contentTypeCode.
     * 
     * @return
     *     possible object is
     *     {@link Code }
     *     
     */
    public Code getContentTypeCode() {
        return contentTypeCode;
    }

    /**
     * Imposta il valore della propriet� contentTypeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link Code }
     *     
     */
    public void setContentTypeCode(Code value) {
        this.contentTypeCode = value;
    }

}
