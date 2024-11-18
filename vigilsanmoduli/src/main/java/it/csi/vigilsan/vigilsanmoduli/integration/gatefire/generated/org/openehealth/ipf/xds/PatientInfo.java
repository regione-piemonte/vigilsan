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
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per PatientInfo complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="PatientInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.openehealth.org/ipf/xds}Identifiable" maxOccurs="unbounded" minOccurs="0" form="unqualified"/>
 *         &lt;element name="name" type="{http://www.openehealth.org/ipf/xds}xcnName" maxOccurs="unbounded" minOccurs="0" form="unqualified"/>
 *         &lt;element name="birthTime" type="{http://www.openehealth.org/ipf/xds}Timestamp" minOccurs="0" form="unqualified"/>
 *         &lt;element name="gender" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="unqualified"/>
 *         &lt;element name="address" type="{http://www.openehealth.org/ipf/xds}Address" maxOccurs="unbounded" minOccurs="0" form="unqualified"/>
 *         &lt;element name="other" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0" form="unqualified"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PatientInfo", propOrder = {
    "id",
    "name",
    "birthTime",
    "gender",
    "address",
    "other"
})
public class PatientInfo {

    @XmlElement(namespace = "")
    protected List<Identifiable> id;
    @XmlElement(namespace = "")
    protected List<XcnName> name;
    @XmlElement(namespace = "")
    protected Timestamp birthTime;
    @XmlElement(namespace = "")
    protected String gender;
    @XmlElement(namespace = "")
    protected List<Address> address;
    @XmlElement(namespace = "")
    protected Object other;

    /**
     * Gets the value of the id property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the id property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getId().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Identifiable }
     * 
     * 
     */
    public List<Identifiable> getId() {
        if (id == null) {
            id = new ArrayList<Identifiable>();
        }
        return this.id;
    }

    /**
     * Gets the value of the name property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the name property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getName().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link XcnName }
     * 
     * 
     */
    public List<XcnName> getName() {
        if (name == null) {
            name = new ArrayList<XcnName>();
        }
        return this.name;
    }

    /**
     * Recupera il valore della propriet� birthTime.
     * 
     * @return
     *     possible object is
     *     {@link Timestamp }
     *     
     */
    public Timestamp getBirthTime() {
        return birthTime;
    }

    /**
     * Imposta il valore della propriet� birthTime.
     * 
     * @param value
     *     allowed object is
     *     {@link Timestamp }
     *     
     */
    public void setBirthTime(Timestamp value) {
        this.birthTime = value;
    }

    /**
     * Recupera il valore della propriet� gender.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGender() {
        return gender;
    }

    /**
     * Imposta il valore della propriet� gender.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGender(String value) {
        this.gender = value;
    }

    /**
     * Gets the value of the address property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the address property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAddress().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Address }
     * 
     * 
     */
    public List<Address> getAddress() {
        if (address == null) {
            address = new ArrayList<Address>();
        }
        return this.address;
    }

    /**
     * Recupera il valore della propriet� other.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getOther() {
        return other;
    }

    /**
     * Imposta il valore della propriet� other.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setOther(Object value) {
        this.other = value;
    }

}
