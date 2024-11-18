/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.org.openehealth.ipf.xds;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per DocumentEntry complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="DocumentEntry">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openehealth.org/ipf/xds}IdentifiedObject">
 *       &lt;sequence>
 *         &lt;element name="sourcePatientId" type="{http://www.openehealth.org/ipf/xds}Identifiable" minOccurs="0"/>
 *         &lt;element name="sourcePatientInfo" type="{http://www.openehealth.org/ipf/xds}PatientInfo" minOccurs="0"/>
 *         &lt;element name="creationTime" type="{http://www.openehealth.org/ipf/xds}Timestamp" minOccurs="0"/>
 *         &lt;element name="author" type="{http://www.openehealth.org/ipf/xds}Author" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="legalAuthenticator" type="{http://www.openehealth.org/ipf/xds}Person" minOccurs="0"/>
 *         &lt;element name="serviceStartTime" type="{http://www.openehealth.org/ipf/xds}Timestamp" minOccurs="0"/>
 *         &lt;element name="serviceStopTime" type="{http://www.openehealth.org/ipf/xds}Timestamp" minOccurs="0"/>
 *         &lt;element name="classCode" type="{http://www.openehealth.org/ipf/xds}Code" minOccurs="0"/>
 *         &lt;element name="confidentialityCode" type="{http://www.openehealth.org/ipf/xds}Code" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="eventCode" type="{http://www.openehealth.org/ipf/xds}Code" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="formatCode" type="{http://www.openehealth.org/ipf/xds}Code" minOccurs="0"/>
 *         &lt;element name="healthcareFacilityTypeCode" type="{http://www.openehealth.org/ipf/xds}Code" minOccurs="0"/>
 *         &lt;element name="languageCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="practiceSettingCode" type="{http://www.openehealth.org/ipf/xds}Code" minOccurs="0"/>
 *         &lt;element name="typeCode" type="{http://www.openehealth.org/ipf/xds}Code" minOccurs="0"/>
 *         &lt;element name="repositoryUniqueId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mimeType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="size" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="hash" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="type" type="{http://www.openehealth.org/ipf/xds}DocumentEntryType" minOccurs="0"/>
 *         &lt;element name="referenceIdList" type="{http://www.openehealth.org/ipf/xds}ReferenceId" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="documentAvailability" type="{http://www.openehealth.org/ipf/xds}DocumentAvailability" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentEntry", propOrder = {
    "sourcePatientId",
    "sourcePatientInfo",
    "creationTime",
    "author",
    "legalAuthenticator",
    "serviceStartTime",
    "serviceStopTime",
    "classCode",
    "confidentialityCode",
    "eventCode",
    "formatCode",
    "healthcareFacilityTypeCode",
    "languageCode",
    "practiceSettingCode",
    "typeCode",
    "repositoryUniqueId",
    "mimeType",
    "size",
    "hash",
    "uri",
    "type",
    "referenceIdList",
    "documentAvailability"
})
@XmlSeeAlso({
    it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.DocumentEntry.class
})
public class DocumentEntry
    extends IdentifiedObject
{

    protected Identifiable sourcePatientId;
    protected PatientInfo sourcePatientInfo;
    protected Timestamp creationTime;
    protected List<Author> author;
    protected Person legalAuthenticator;
    protected Timestamp serviceStartTime;
    protected Timestamp serviceStopTime;
    protected Code classCode;
    protected List<Code> confidentialityCode;
    protected List<Code> eventCode;
    protected Code formatCode;
    protected Code healthcareFacilityTypeCode;
    protected String languageCode;
    protected Code practiceSettingCode;
    protected Code typeCode;
    protected String repositoryUniqueId;
    protected String mimeType;
    protected Long size;
    protected String hash;
    protected String uri;
    protected DocumentEntryType type;
    @XmlElement(nillable = true)
    protected List<ReferenceId> referenceIdList;
    protected DocumentAvailability documentAvailability;

    /**
     * Recupera il valore della propriet� sourcePatientId.
     * 
     * @return
     *     possible object is
     *     {@link Identifiable }
     *     
     */
    public Identifiable getSourcePatientId() {
        return sourcePatientId;
    }

    /**
     * Imposta il valore della propriet� sourcePatientId.
     * 
     * @param value
     *     allowed object is
     *     {@link Identifiable }
     *     
     */
    public void setSourcePatientId(Identifiable value) {
        this.sourcePatientId = value;
    }

    /**
     * Recupera il valore della propriet� sourcePatientInfo.
     * 
     * @return
     *     possible object is
     *     {@link PatientInfo }
     *     
     */
    public PatientInfo getSourcePatientInfo() {
        return sourcePatientInfo;
    }

    /**
     * Imposta il valore della propriet� sourcePatientInfo.
     * 
     * @param value
     *     allowed object is
     *     {@link PatientInfo }
     *     
     */
    public void setSourcePatientInfo(PatientInfo value) {
        this.sourcePatientInfo = value;
    }

    /**
     * Recupera il valore della propriet� creationTime.
     * 
     * @return
     *     possible object is
     *     {@link Timestamp }
     *     
     */
    public Timestamp getCreationTime() {
        return creationTime;
    }

    /**
     * Imposta il valore della propriet� creationTime.
     * 
     * @param value
     *     allowed object is
     *     {@link Timestamp }
     *     
     */
    public void setCreationTime(Timestamp value) {
        this.creationTime = value;
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
     * Recupera il valore della propriet� legalAuthenticator.
     * 
     * @return
     *     possible object is
     *     {@link Person }
     *     
     */
    public Person getLegalAuthenticator() {
        return legalAuthenticator;
    }

    /**
     * Imposta il valore della propriet� legalAuthenticator.
     * 
     * @param value
     *     allowed object is
     *     {@link Person }
     *     
     */
    public void setLegalAuthenticator(Person value) {
        this.legalAuthenticator = value;
    }

    /**
     * Recupera il valore della propriet� serviceStartTime.
     * 
     * @return
     *     possible object is
     *     {@link Timestamp }
     *     
     */
    public Timestamp getServiceStartTime() {
        return serviceStartTime;
    }

    /**
     * Imposta il valore della propriet� serviceStartTime.
     * 
     * @param value
     *     allowed object is
     *     {@link Timestamp }
     *     
     */
    public void setServiceStartTime(Timestamp value) {
        this.serviceStartTime = value;
    }

    /**
     * Recupera il valore della propriet� serviceStopTime.
     * 
     * @return
     *     possible object is
     *     {@link Timestamp }
     *     
     */
    public Timestamp getServiceStopTime() {
        return serviceStopTime;
    }

    /**
     * Imposta il valore della propriet� serviceStopTime.
     * 
     * @param value
     *     allowed object is
     *     {@link Timestamp }
     *     
     */
    public void setServiceStopTime(Timestamp value) {
        this.serviceStopTime = value;
    }

    /**
     * Recupera il valore della propriet� classCode.
     * 
     * @return
     *     possible object is
     *     {@link Code }
     *     
     */
    public Code getClassCode() {
        return classCode;
    }

    /**
     * Imposta il valore della propriet� classCode.
     * 
     * @param value
     *     allowed object is
     *     {@link Code }
     *     
     */
    public void setClassCode(Code value) {
        this.classCode = value;
    }

    /**
     * Gets the value of the confidentialityCode property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the confidentialityCode property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConfidentialityCode().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Code }
     * 
     * 
     */
    public List<Code> getConfidentialityCode() {
        if (confidentialityCode == null) {
            confidentialityCode = new ArrayList<Code>();
        }
        return this.confidentialityCode;
    }

    /**
     * Gets the value of the eventCode property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the eventCode property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEventCode().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Code }
     * 
     * 
     */
    public List<Code> getEventCode() {
        if (eventCode == null) {
            eventCode = new ArrayList<Code>();
        }
        return this.eventCode;
    }

    /**
     * Recupera il valore della propriet� formatCode.
     * 
     * @return
     *     possible object is
     *     {@link Code }
     *     
     */
    public Code getFormatCode() {
        return formatCode;
    }

    /**
     * Imposta il valore della propriet� formatCode.
     * 
     * @param value
     *     allowed object is
     *     {@link Code }
     *     
     */
    public void setFormatCode(Code value) {
        this.formatCode = value;
    }

    /**
     * Recupera il valore della propriet� healthcareFacilityTypeCode.
     * 
     * @return
     *     possible object is
     *     {@link Code }
     *     
     */
    public Code getHealthcareFacilityTypeCode() {
        return healthcareFacilityTypeCode;
    }

    /**
     * Imposta il valore della propriet� healthcareFacilityTypeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link Code }
     *     
     */
    public void setHealthcareFacilityTypeCode(Code value) {
        this.healthcareFacilityTypeCode = value;
    }

    /**
     * Recupera il valore della propriet� languageCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     * Imposta il valore della propriet� languageCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLanguageCode(String value) {
        this.languageCode = value;
    }

    /**
     * Recupera il valore della propriet� practiceSettingCode.
     * 
     * @return
     *     possible object is
     *     {@link Code }
     *     
     */
    public Code getPracticeSettingCode() {
        return practiceSettingCode;
    }

    /**
     * Imposta il valore della propriet� practiceSettingCode.
     * 
     * @param value
     *     allowed object is
     *     {@link Code }
     *     
     */
    public void setPracticeSettingCode(Code value) {
        this.practiceSettingCode = value;
    }

    /**
     * Recupera il valore della propriet� typeCode.
     * 
     * @return
     *     possible object is
     *     {@link Code }
     *     
     */
    public Code getTypeCode() {
        return typeCode;
    }

    /**
     * Imposta il valore della propriet� typeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link Code }
     *     
     */
    public void setTypeCode(Code value) {
        this.typeCode = value;
    }

    /**
     * Recupera il valore della propriet� repositoryUniqueId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRepositoryUniqueId() {
        return repositoryUniqueId;
    }

    /**
     * Imposta il valore della propriet� repositoryUniqueId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRepositoryUniqueId(String value) {
        this.repositoryUniqueId = value;
    }

    /**
     * Recupera il valore della propriet� mimeType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * Imposta il valore della propriet� mimeType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMimeType(String value) {
        this.mimeType = value;
    }

    /**
     * Recupera il valore della propriet� size.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getSize() {
        return size;
    }

    /**
     * Imposta il valore della propriet� size.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setSize(Long value) {
        this.size = value;
    }

    /**
     * Recupera il valore della propriet� hash.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHash() {
        return hash;
    }

    /**
     * Imposta il valore della propriet� hash.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHash(String value) {
        this.hash = value;
    }

    /**
     * Recupera il valore della propriet� uri.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUri() {
        return uri;
    }

    /**
     * Imposta il valore della propriet� uri.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUri(String value) {
        this.uri = value;
    }

    /**
     * Recupera il valore della propriet� type.
     * 
     * @return
     *     possible object is
     *     {@link DocumentEntryType }
     *     
     */
    public DocumentEntryType getType() {
        return type;
    }

    /**
     * Imposta il valore della propriet� type.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentEntryType }
     *     
     */
    public void setType(DocumentEntryType value) {
        this.type = value;
    }

    /**
     * Gets the value of the referenceIdList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the referenceIdList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReferenceIdList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReferenceId }
     * 
     * 
     */
    public List<ReferenceId> getReferenceIdList() {
        if (referenceIdList == null) {
            referenceIdList = new ArrayList<ReferenceId>();
        }
        return this.referenceIdList;
    }

    /**
     * Recupera il valore della propriet� documentAvailability.
     * 
     * @return
     *     possible object is
     *     {@link DocumentAvailability }
     *     
     */
    public DocumentAvailability getDocumentAvailability() {
        return documentAvailability;
    }

    /**
     * Imposta il valore della propriet� documentAvailability.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentAvailability }
     *     
     */
    public void setDocumentAvailability(DocumentAvailability value) {
        this.documentAvailability = value;
    }

}
