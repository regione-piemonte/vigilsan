/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.org.openehealth.ipf.xds;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per AssigningAuthority complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="AssigningAuthority">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="universalId" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="universalIdType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AssigningAuthority")
@XmlSeeAlso({
    CXiAssigningAuthority.class
})
public class AssigningAuthority {

    @XmlAttribute(name = "universalId")
    protected String universalId;
    @XmlAttribute(name = "universalIdType")
    protected String universalIdType;

    /**
     * Recupera il valore della propriet� universalId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUniversalId() {
        return universalId;
    }

    /**
     * Imposta il valore della propriet� universalId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUniversalId(String value) {
        this.universalId = value;
    }

    /**
     * Recupera il valore della propriet� universalIdType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUniversalIdType() {
        return universalIdType;
    }

    /**
     * Imposta il valore della propriet� universalIdType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUniversalIdType(String value) {
        this.universalIdType = value;
    }

}
