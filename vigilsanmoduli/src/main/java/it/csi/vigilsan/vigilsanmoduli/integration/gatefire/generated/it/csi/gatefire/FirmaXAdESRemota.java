/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per firmaXAdESRemota complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="firmaXAdESRemota">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="attachment" type="{http://www.csi.it/gatefire/}attachment" minOccurs="0"/>
 *         &lt;element name="xadesInput" type="{http://www.csi.it/gatefire/}xadesInput" minOccurs="0"/>
 *         &lt;element name="identity" type="{http://www.csi.it/gatefire/}signIdentity" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "firmaXAdESRemota", propOrder = {
    "attachment",
    "xadesInput",
    "identity"
})
public class FirmaXAdESRemota {

    protected Attachment attachment;
    protected XadesInput xadesInput;
    protected SignIdentity identity;

    /**
     * Recupera il valore della propriet� attachment.
     * 
     * @return
     *     possible object is
     *     {@link Attachment }
     *     
     */
    public Attachment getAttachment() {
        return attachment;
    }

    /**
     * Imposta il valore della propriet� attachment.
     * 
     * @param value
     *     allowed object is
     *     {@link Attachment }
     *     
     */
    public void setAttachment(Attachment value) {
        this.attachment = value;
    }

    /**
     * Recupera il valore della propriet� xadesInput.
     * 
     * @return
     *     possible object is
     *     {@link XadesInput }
     *     
     */
    public XadesInput getXadesInput() {
        return xadesInput;
    }

    /**
     * Imposta il valore della propriet� xadesInput.
     * 
     * @param value
     *     allowed object is
     *     {@link XadesInput }
     *     
     */
    public void setXadesInput(XadesInput value) {
        this.xadesInput = value;
    }

    /**
     * Recupera il valore della propriet� identity.
     * 
     * @return
     *     possible object is
     *     {@link SignIdentity }
     *     
     */
    public SignIdentity getIdentity() {
        return identity;
    }

    /**
     * Imposta il valore della propriet� identity.
     * 
     * @param value
     *     allowed object is
     *     {@link SignIdentity }
     *     
     */
    public void setIdentity(SignIdentity value) {
        this.identity = value;
    }

}
