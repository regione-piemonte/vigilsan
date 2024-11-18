/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.org.openehealth.ipf.xds;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per IdentifiedObject complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="IdentifiedObject">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="homeCommunityId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="entryUuid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="logicalUuid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="version" type="{http://www.openehealth.org/ipf/xds}Version" minOccurs="0"/>
 *         &lt;element name="uniqueId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="patientId" type="{http://www.openehealth.org/ipf/xds}Identifiable" minOccurs="0"/>
 *         &lt;element name="availabilityStatus" type="{http://www.openehealth.org/ipf/xds}AvailabilityStatus" minOccurs="0"/>
 *         &lt;element name="title" type="{http://www.openehealth.org/ipf/xds}LocalizedString" minOccurs="0"/>
 *         &lt;element name="comments" type="{http://www.openehealth.org/ipf/xds}LocalizedString" minOccurs="0"/>
 *         &lt;element name="limitedMetadata" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="extraMetadata" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="entry" maxOccurs="unbounded" minOccurs="0" form="unqualified">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" form="unqualified"/>
 *                           &lt;/sequence>
 *                           &lt;attribute name="key" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdentifiedObject", propOrder = {
    "homeCommunityId",
    "entryUuid",
    "logicalUuid",
    "version",
    "uniqueId",
    "patientId",
    "availabilityStatus",
    "title",
    "comments",
    "limitedMetadata",
    "extraMetadata"
})
@XmlSeeAlso({
    SubmissionSet.class,
    DocumentEntry.class
})
public abstract class IdentifiedObject {

    protected String homeCommunityId;
    protected String entryUuid;
    protected String logicalUuid;
    protected Version version;
    protected String uniqueId;
    protected Identifiable patientId;
    protected AvailabilityStatus availabilityStatus;
    protected LocalizedString title;
    protected LocalizedString comments;
    protected boolean limitedMetadata;
    protected IdentifiedObject.ExtraMetadata extraMetadata;

    /**
     * Recupera il valore della propriet� homeCommunityId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHomeCommunityId() {
        return homeCommunityId;
    }

    /**
     * Imposta il valore della propriet� homeCommunityId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHomeCommunityId(String value) {
        this.homeCommunityId = value;
    }

    /**
     * Recupera il valore della propriet� entryUuid.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntryUuid() {
        return entryUuid;
    }

    /**
     * Imposta il valore della propriet� entryUuid.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntryUuid(String value) {
        this.entryUuid = value;
    }

    /**
     * Recupera il valore della propriet� logicalUuid.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogicalUuid() {
        return logicalUuid;
    }

    /**
     * Imposta il valore della propriet� logicalUuid.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogicalUuid(String value) {
        this.logicalUuid = value;
    }

    /**
     * Recupera il valore della propriet� version.
     * 
     * @return
     *     possible object is
     *     {@link Version }
     *     
     */
    public Version getVersion() {
        return version;
    }

    /**
     * Imposta il valore della propriet� version.
     * 
     * @param value
     *     allowed object is
     *     {@link Version }
     *     
     */
    public void setVersion(Version value) {
        this.version = value;
    }

    /**
     * Recupera il valore della propriet� uniqueId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUniqueId() {
        return uniqueId;
    }

    /**
     * Imposta il valore della propriet� uniqueId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUniqueId(String value) {
        this.uniqueId = value;
    }

    /**
     * Recupera il valore della propriet� patientId.
     * 
     * @return
     *     possible object is
     *     {@link Identifiable }
     *     
     */
    public Identifiable getPatientId() {
        return patientId;
    }

    /**
     * Imposta il valore della propriet� patientId.
     * 
     * @param value
     *     allowed object is
     *     {@link Identifiable }
     *     
     */
    public void setPatientId(Identifiable value) {
        this.patientId = value;
    }

    /**
     * Recupera il valore della propriet� availabilityStatus.
     * 
     * @return
     *     possible object is
     *     {@link AvailabilityStatus }
     *     
     */
    public AvailabilityStatus getAvailabilityStatus() {
        return availabilityStatus;
    }

    /**
     * Imposta il valore della propriet� availabilityStatus.
     * 
     * @param value
     *     allowed object is
     *     {@link AvailabilityStatus }
     *     
     */
    public void setAvailabilityStatus(AvailabilityStatus value) {
        this.availabilityStatus = value;
    }

    /**
     * Recupera il valore della propriet� title.
     * 
     * @return
     *     possible object is
     *     {@link LocalizedString }
     *     
     */
    public LocalizedString getTitle() {
        return title;
    }

    /**
     * Imposta il valore della propriet� title.
     * 
     * @param value
     *     allowed object is
     *     {@link LocalizedString }
     *     
     */
    public void setTitle(LocalizedString value) {
        this.title = value;
    }

    /**
     * Recupera il valore della propriet� comments.
     * 
     * @return
     *     possible object is
     *     {@link LocalizedString }
     *     
     */
    public LocalizedString getComments() {
        return comments;
    }

    /**
     * Imposta il valore della propriet� comments.
     * 
     * @param value
     *     allowed object is
     *     {@link LocalizedString }
     *     
     */
    public void setComments(LocalizedString value) {
        this.comments = value;
    }

    /**
     * Recupera il valore della propriet� limitedMetadata.
     * 
     */
    public boolean isLimitedMetadata() {
        return limitedMetadata;
    }

    /**
     * Imposta il valore della propriet� limitedMetadata.
     * 
     */
    public void setLimitedMetadata(boolean value) {
        this.limitedMetadata = value;
    }

    /**
     * Recupera il valore della propriet� extraMetadata.
     * 
     * @return
     *     possible object is
     *     {@link IdentifiedObject.ExtraMetadata }
     *     
     */
    public IdentifiedObject.ExtraMetadata getExtraMetadata() {
        return extraMetadata;
    }

    /**
     * Imposta il valore della propriet� extraMetadata.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentifiedObject.ExtraMetadata }
     *     
     */
    public void setExtraMetadata(IdentifiedObject.ExtraMetadata value) {
        this.extraMetadata = value;
    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="entry" maxOccurs="unbounded" minOccurs="0" form="unqualified">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" form="unqualified"/>
     *                 &lt;/sequence>
     *                 &lt;attribute name="key" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "entry"
    })
    public static class ExtraMetadata {

        @XmlElement(namespace = "")
        protected List<IdentifiedObject.ExtraMetadata.Entry> entry;

        /**
         * Gets the value of the entry property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the entry property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEntry().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link IdentifiedObject.ExtraMetadata.Entry }
         * 
         * 
         */
        public List<IdentifiedObject.ExtraMetadata.Entry> getEntry() {
            if (entry == null) {
                entry = new ArrayList<IdentifiedObject.ExtraMetadata.Entry>();
            }
            return this.entry;
        }


        /**
         * <p>Classe Java per anonymous complex type.
         * 
         * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" form="unqualified"/>
         *       &lt;/sequence>
         *       &lt;attribute name="key" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class Entry {

            @XmlElement(namespace = "", required = true)
            protected List<String> value;
            @XmlAttribute(name = "key", required = true)
            protected String key;

            /**
             * Gets the value of the value property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the value property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getValue().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link String }
             * 
             * 
             */
            public List<String> getValue() {
                if (value == null) {
                    value = new ArrayList<String>();
                }
                return this.value;
            }

            /**
             * Recupera il valore della propriet� key.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKey() {
                return key;
            }

            /**
             * Imposta il valore della propriet� key.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKey(String value) {
                this.key = value;
            }

        }

    }

}
