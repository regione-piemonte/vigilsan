/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per certPolicy complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="certPolicy">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cpText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cpsUri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "certPolicy", propOrder = {
    "cpText",
    "cpsUri"
})
public class CertPolicy {

    protected String cpText;
    protected String cpsUri;

    /**
     * Recupera il valore della propriet� cpText.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCpText() {
        return cpText;
    }

    /**
     * Imposta il valore della propriet� cpText.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCpText(String value) {
        this.cpText = value;
    }

    /**
     * Recupera il valore della propriet� cpsUri.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCpsUri() {
        return cpsUri;
    }

    /**
     * Imposta il valore della propriet� cpsUri.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCpsUri(String value) {
        this.cpsUri = value;
    }

}
