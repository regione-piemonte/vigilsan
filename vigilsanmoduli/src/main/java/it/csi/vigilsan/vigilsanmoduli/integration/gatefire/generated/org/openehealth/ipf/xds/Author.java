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
 * <p>Classe Java per Author complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Author">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authorPerson" type="{http://www.openehealth.org/ipf/xds}Person" minOccurs="0"/>
 *         &lt;element name="authorInstitution" type="{http://www.openehealth.org/ipf/xds}Organization" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="authorSpecialty" type="{http://www.openehealth.org/ipf/xds}Identifiable" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="authorRole" type="{http://www.openehealth.org/ipf/xds}Identifiable" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="authorTelecom" type="{http://www.openehealth.org/ipf/xds}Telecom" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Author", propOrder = {
    "authorPerson",
    "authorInstitution",
    "authorSpecialty",
    "authorRole",
    "authorTelecom"
})
public class Author {

    protected Person authorPerson;
    @XmlElement(nillable = true)
    protected List<Organization> authorInstitution;
    @XmlElement(nillable = true)
    protected List<Identifiable> authorSpecialty;
    @XmlElement(nillable = true)
    protected List<Identifiable> authorRole;
    @XmlElement(nillable = true)
    protected List<Telecom> authorTelecom;

    /**
     * Recupera il valore della propriet� authorPerson.
     * 
     * @return
     *     possible object is
     *     {@link Person }
     *     
     */
    public Person getAuthorPerson() {
        return authorPerson;
    }

    /**
     * Imposta il valore della propriet� authorPerson.
     * 
     * @param value
     *     allowed object is
     *     {@link Person }
     *     
     */
    public void setAuthorPerson(Person value) {
        this.authorPerson = value;
    }

    /**
     * Gets the value of the authorInstitution property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the authorInstitution property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAuthorInstitution().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Organization }
     * 
     * 
     */
    public List<Organization> getAuthorInstitution() {
        if (authorInstitution == null) {
            authorInstitution = new ArrayList<Organization>();
        }
        return this.authorInstitution;
    }

    /**
     * Gets the value of the authorSpecialty property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the authorSpecialty property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAuthorSpecialty().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Identifiable }
     * 
     * 
     */
    public List<Identifiable> getAuthorSpecialty() {
        if (authorSpecialty == null) {
            authorSpecialty = new ArrayList<Identifiable>();
        }
        return this.authorSpecialty;
    }

    /**
     * Gets the value of the authorRole property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the authorRole property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAuthorRole().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Identifiable }
     * 
     * 
     */
    public List<Identifiable> getAuthorRole() {
        if (authorRole == null) {
            authorRole = new ArrayList<Identifiable>();
        }
        return this.authorRole;
    }

    /**
     * Gets the value of the authorTelecom property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the authorTelecom property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAuthorTelecom().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Telecom }
     * 
     * 
     */
    public List<Telecom> getAuthorTelecom() {
        if (authorTelecom == null) {
            authorTelecom = new ArrayList<Telecom>();
        }
        return this.authorTelecom;
    }

}
