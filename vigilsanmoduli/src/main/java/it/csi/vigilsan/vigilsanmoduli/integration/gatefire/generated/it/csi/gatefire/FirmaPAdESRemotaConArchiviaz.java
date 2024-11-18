/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per firmaPAdESRemotaConArchiviaz complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="firmaPAdESRemotaConArchiviaz">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="attachment" type="{http://www.csi.it/gatefire/}attachment" minOccurs="0"/>
 *         &lt;element name="padesInput" type="{http://www.csi.it/gatefire/}padesInput" minOccurs="0"/>
 *         &lt;element name="identity" type="{http://www.csi.it/gatefire/}signIdentity" minOccurs="0"/>
 *         &lt;element name="metadata" type="{http://www.csi.it/gatefire/}itiMetadata" minOccurs="0"/>
 *         &lt;element name="assertionIdentity" type="{http://www.csi.it/gatefire/}assertionIdentity" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "firmaPAdESRemotaConArchiviaz", propOrder = {
    "attachment",
    "padesInput",
    "identity",
    "metadata",
    "assertionIdentity"
})
public class FirmaPAdESRemotaConArchiviaz {

    protected Attachment attachment;
    protected PadesInput padesInput;
    protected SignIdentity identity;
    protected ItiMetadata metadata;
    protected AssertionIdentity assertionIdentity;

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
     * Recupera il valore della propriet� padesInput.
     * 
     * @return
     *     possible object is
     *     {@link PadesInput }
     *     
     */
    public PadesInput getPadesInput() {
        return padesInput;
    }

    /**
     * Imposta il valore della propriet� padesInput.
     * 
     * @param value
     *     allowed object is
     *     {@link PadesInput }
     *     
     */
    public void setPadesInput(PadesInput value) {
        this.padesInput = value;
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

    /**
     * Recupera il valore della propriet� metadata.
     * 
     * @return
     *     possible object is
     *     {@link ItiMetadata }
     *     
     */
    public ItiMetadata getMetadata() {
        return metadata;
    }

    /**
     * Imposta il valore della propriet� metadata.
     * 
     * @param value
     *     allowed object is
     *     {@link ItiMetadata }
     *     
     */
    public void setMetadata(ItiMetadata value) {
        this.metadata = value;
    }

    /**
     * Recupera il valore della propriet� assertionIdentity.
     * 
     * @return
     *     possible object is
     *     {@link AssertionIdentity }
     *     
     */
    public AssertionIdentity getAssertionIdentity() {
        return assertionIdentity;
    }

    /**
     * Imposta il valore della propriet� assertionIdentity.
     * 
     * @param value
     *     allowed object is
     *     {@link AssertionIdentity }
     *     
     */
    public void setAssertionIdentity(AssertionIdentity value) {
        this.assertionIdentity = value;
    }

}