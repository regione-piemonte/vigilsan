/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.org.openehealth.ipf.xds;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ReferenceId complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ReferenceId">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="assigningAuthority" type="{http://www.openehealth.org/ipf/xds}CXiAssigningAuthority" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="idTypeCode" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReferenceId", propOrder = {
    "assigningAuthority"
})
public class ReferenceId {

    protected CXiAssigningAuthority assigningAuthority;
    @XmlAttribute(name = "id")
    protected String id;
    @XmlAttribute(name = "idTypeCode")
    protected String idTypeCode;

    /**
     * Recupera il valore della propriet� assigningAuthority.
     * 
     * @return
     *     possible object is
     *     {@link CXiAssigningAuthority }
     *     
     */
    public CXiAssigningAuthority getAssigningAuthority() {
        return assigningAuthority;
    }

    /**
     * Imposta il valore della propriet� assigningAuthority.
     * 
     * @param value
     *     allowed object is
     *     {@link CXiAssigningAuthority }
     *     
     */
    public void setAssigningAuthority(CXiAssigningAuthority value) {
        this.assigningAuthority = value;
    }

    /**
     * Recupera il valore della propriet� id.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Imposta il valore della propriet� id.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Recupera il valore della propriet� idTypeCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdTypeCode() {
        return idTypeCode;
    }

    /**
     * Imposta il valore della propriet� idTypeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdTypeCode(String value) {
        this.idTypeCode = value;
    }

}
