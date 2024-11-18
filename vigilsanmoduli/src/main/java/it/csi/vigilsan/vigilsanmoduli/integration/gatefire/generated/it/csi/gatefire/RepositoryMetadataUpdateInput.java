/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per repositoryMetadataUpdateInput complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="repositoryMetadataUpdateInput">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="docUid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="metadata" type="{http://www.csi.it/gatefire/}itiMetadataUpdate" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "repositoryMetadataUpdateInput", propOrder = {
    "docUid",
    "metadata"
})
public class RepositoryMetadataUpdateInput {

    protected String docUid;
    protected ItiMetadataUpdate metadata;

    /**
     * Recupera il valore della propriet� docUid.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocUid() {
        return docUid;
    }

    /**
     * Imposta il valore della propriet� docUid.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocUid(String value) {
        this.docUid = value;
    }

    /**
     * Recupera il valore della propriet� metadata.
     * 
     * @return
     *     possible object is
     *     {@link ItiMetadataUpdate }
     *     
     */
    public ItiMetadataUpdate getMetadata() {
        return metadata;
    }

    /**
     * Imposta il valore della propriet� metadata.
     * 
     * @param value
     *     allowed object is
     *     {@link ItiMetadataUpdate }
     *     
     */
    public void setMetadata(ItiMetadataUpdate value) {
        this.metadata = value;
    }

}
