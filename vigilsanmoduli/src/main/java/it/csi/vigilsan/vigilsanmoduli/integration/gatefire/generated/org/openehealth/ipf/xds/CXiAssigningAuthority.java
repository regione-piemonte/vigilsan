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
 * <p>Classe Java per CXiAssigningAuthority complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="CXiAssigningAuthority">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.openehealth.org/ipf/xds}AssigningAuthority">
 *       &lt;sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="namespaceId" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CXiAssigningAuthority")
public class CXiAssigningAuthority
    extends AssigningAuthority
{

    @XmlAttribute(name = "namespaceId")
    protected String namespaceId;

    /**
     * Recupera il valore della propriet� namespaceId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNamespaceId() {
        return namespaceId;
    }

    /**
     * Imposta il valore della propriet� namespaceId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNamespaceId(String value) {
        this.namespaceId = value;
    }

}
