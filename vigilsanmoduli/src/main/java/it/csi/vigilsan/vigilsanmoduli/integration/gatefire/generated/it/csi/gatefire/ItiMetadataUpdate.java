/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per itiMetadataUpdate complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="itiMetadataUpdate">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="documento" type="{http://www.csi.it/gatefire/}documento" minOccurs="0"/>
 *         &lt;element name="richiesta" type="{http://www.csi.it/gatefire/}richiesta" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "itiMetadataUpdate", propOrder = {
    "documento",
    "richiesta"
})
public class ItiMetadataUpdate {

    protected Documento documento;
    protected Richiesta richiesta;

    /**
     * Recupera il valore della propriet� documento.
     * 
     * @return
     *     possible object is
     *     {@link Documento }
     *     
     */
    public Documento getDocumento() {
        return documento;
    }

    /**
     * Imposta il valore della propriet� documento.
     * 
     * @param value
     *     allowed object is
     *     {@link Documento }
     *     
     */
    public void setDocumento(Documento value) {
        this.documento = value;
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

}
