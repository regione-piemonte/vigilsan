/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per firmaPAdESMassiva complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="firmaPAdESMassiva">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="attachment" type="{http://www.csi.it/gatefire/}attachment" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="padesInput" type="{http://www.csi.it/gatefire/}padesInput" minOccurs="0"/>
 *         &lt;element name="identity" type="{http://www.csi.it/gatefire/}identity" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "firmaPAdESMassiva", propOrder = {
    "attachment",
    "padesInput",
    "identity"
})
public class FirmaPAdESMassiva {

    protected List<Attachment> attachment;
    protected PadesInput padesInput;
    protected Identity identity;

    /**
     * Gets the value of the attachment property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attachment property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttachment().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Attachment }
     * 
     * 
     */
    public List<Attachment> getAttachment() {
        if (attachment == null) {
            attachment = new ArrayList<Attachment>();
        }
        return this.attachment;
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
     *     {@link Identity }
     *     
     */
    public Identity getIdentity() {
        return identity;
    }

    /**
     * Imposta il valore della propriet� identity.
     * 
     * @param value
     *     allowed object is
     *     {@link Identity }
     *     
     */
    public void setIdentity(Identity value) {
        this.identity = value;
    }

}
