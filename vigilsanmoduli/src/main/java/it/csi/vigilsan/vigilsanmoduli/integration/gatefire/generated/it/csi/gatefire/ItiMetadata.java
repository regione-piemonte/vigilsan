/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per itiMetadata complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="itiMetadata">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="documento" type="{http://www.csi.it/gatefire/}documento" minOccurs="0"/>
 *         &lt;element name="episodio" type="{http://www.csi.it/gatefire/}episodio" minOccurs="0"/>
 *         &lt;element name="paziente" type="{http://www.csi.it/gatefire/}paziente" minOccurs="0"/>
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
@XmlType(name = "itiMetadata", propOrder = {
    "documento",
    "episodio",
    "paziente",
    "richiesta"
})
public class ItiMetadata {

    protected Documento documento;
    protected Episodio episodio;
    protected Paziente paziente;
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
     * Recupera il valore della propriet� episodio.
     * 
     * @return
     *     possible object is
     *     {@link Episodio }
     *     
     */
    public Episodio getEpisodio() {
        return episodio;
    }

    /**
     * Imposta il valore della propriet� episodio.
     * 
     * @param value
     *     allowed object is
     *     {@link Episodio }
     *     
     */
    public void setEpisodio(Episodio value) {
        this.episodio = value;
    }

    /**
     * Recupera il valore della propriet� paziente.
     * 
     * @return
     *     possible object is
     *     {@link Paziente }
     *     
     */
    public Paziente getPaziente() {
        return paziente;
    }

    /**
     * Imposta il valore della propriet� paziente.
     * 
     * @param value
     *     allowed object is
     *     {@link Paziente }
     *     
     */
    public void setPaziente(Paziente value) {
        this.paziente = value;
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
