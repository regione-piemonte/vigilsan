/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per repositoryUndoInput complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="repositoryUndoInput">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="attachment" type="{http://www.csi.it/gatefire/}attachment" minOccurs="0"/>
 *         &lt;element name="originalDocUniqueId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="richiesta" type="{http://www.csi.it/gatefire/}richiesta" minOccurs="0"/>
 *         &lt;element name="undoDocUniqueId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "repositoryUndoInput", propOrder = {
    "attachment",
    "originalDocUniqueId",
    "richiesta",
    "undoDocUniqueId"
})
public class RepositoryUndoInput {

    protected Attachment attachment;
    protected String originalDocUniqueId;
    protected Richiesta richiesta;
    protected String undoDocUniqueId;

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
     * Recupera il valore della propriet� originalDocUniqueId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriginalDocUniqueId() {
        return originalDocUniqueId;
    }

    /**
     * Imposta il valore della propriet� originalDocUniqueId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginalDocUniqueId(String value) {
        this.originalDocUniqueId = value;
    }

    /**
     * Recupera il valore della propriet� richiesta.
     * 
     * @return
     *     possible object is
     *     {@link Richiesta }
     *     
     */
    public Richiesta getRichiesta() {
        return richiesta;
    }

    /**
     * Imposta il valore della propriet� richiesta.
     * 
     * @param value
     *     allowed object is
     *     {@link Richiesta }
     *     
     */
    public void setRichiesta(Richiesta value) {
        this.richiesta = value;
    }

    /**
     * Recupera il valore della propriet� undoDocUniqueId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUndoDocUniqueId() {
        return undoDocUniqueId;
    }

    /**
     * Imposta il valore della propriet� undoDocUniqueId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUndoDocUniqueId(String value) {
        this.undoDocUniqueId = value;
    }

}
