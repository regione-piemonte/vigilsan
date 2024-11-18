/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire;

import javax.xml.datatype.XMLGregorianCalendar;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per episodio complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="episodio">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codiceLuogoAccettazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiceLuogoCP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiceLuogoDimissione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataFine" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dataInizio" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="idEpisodio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idEpisodioOriginanteRichiesta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idRichiestaCUP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idRichiestaOrderEntry" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idRichiestaPS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroNosologico" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroPassaggioPS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoEpisodio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoEpisodioOriginanteRichiesta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "episodio", propOrder = {
    "codiceLuogoAccettazione",
    "codiceLuogoCP",
    "codiceLuogoDimissione",
    "dataFine",
    "dataInizio",
    "idEpisodio",
    "idEpisodioOriginanteRichiesta",
    "idRichiestaCUP",
    "idRichiestaOrderEntry",
    "idRichiestaPS",
    "numeroNosologico",
    "numeroPassaggioPS",
    "tipoEpisodio",
    "tipoEpisodioOriginanteRichiesta"
})
public class Episodio {

    protected String codiceLuogoAccettazione;
    protected String codiceLuogoCP;
    protected String codiceLuogoDimissione;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataFine;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataInizio;
    protected String idEpisodio;
    protected String idEpisodioOriginanteRichiesta;
    protected String idRichiestaCUP;
    protected String idRichiestaOrderEntry;
    protected String idRichiestaPS;
    protected String numeroNosologico;
    protected String numeroPassaggioPS;
    protected String tipoEpisodio;
    protected String tipoEpisodioOriginanteRichiesta;

    /**
     * Recupera il valore della propriet� codiceLuogoAccettazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceLuogoAccettazione() {
        return codiceLuogoAccettazione;
    }

    /**
     * Imposta il valore della propriet� codiceLuogoAccettazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceLuogoAccettazione(String value) {
        this.codiceLuogoAccettazione = value;
    }

    /**
     * Recupera il valore della propriet� codiceLuogoCP.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceLuogoCP() {
        return codiceLuogoCP;
    }

    /**
     * Imposta il valore della propriet� codiceLuogoCP.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceLuogoCP(String value) {
        this.codiceLuogoCP = value;
    }

    /**
     * Recupera il valore della propriet� codiceLuogoDimissione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceLuogoDimissione() {
        return codiceLuogoDimissione;
    }

    /**
     * Imposta il valore della propriet� codiceLuogoDimissione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceLuogoDimissione(String value) {
        this.codiceLuogoDimissione = value;
    }

    /**
     * Recupera il valore della propriet� dataFine.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataFine() {
        return dataFine;
    }

    /**
     * Imposta il valore della propriet� dataFine.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataFine(XMLGregorianCalendar value) {
        this.dataFine = value;
    }

    /**
     * Recupera il valore della propriet� dataInizio.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataInizio() {
        return dataInizio;
    }

    /**
     * Imposta il valore della propriet� dataInizio.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataInizio(XMLGregorianCalendar value) {
        this.dataInizio = value;
    }

    /**
     * Recupera il valore della propriet� idEpisodio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdEpisodio() {
        return idEpisodio;
    }

    /**
     * Imposta il valore della propriet� idEpisodio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdEpisodio(String value) {
        this.idEpisodio = value;
    }

    /**
     * Recupera il valore della propriet� idEpisodioOriginanteRichiesta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdEpisodioOriginanteRichiesta() {
        return idEpisodioOriginanteRichiesta;
    }

    /**
     * Imposta il valore della propriet� idEpisodioOriginanteRichiesta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdEpisodioOriginanteRichiesta(String value) {
        this.idEpisodioOriginanteRichiesta = value;
    }

    /**
     * Recupera il valore della propriet� idRichiestaCUP.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdRichiestaCUP() {
        return idRichiestaCUP;
    }

    /**
     * Imposta il valore della propriet� idRichiestaCUP.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdRichiestaCUP(String value) {
        this.idRichiestaCUP = value;
    }

    /**
     * Recupera il valore della propriet� idRichiestaOrderEntry.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdRichiestaOrderEntry() {
        return idRichiestaOrderEntry;
    }

    /**
     * Imposta il valore della propriet� idRichiestaOrderEntry.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdRichiestaOrderEntry(String value) {
        this.idRichiestaOrderEntry = value;
    }

    /**
     * Recupera il valore della propriet� idRichiestaPS.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdRichiestaPS() {
        return idRichiestaPS;
    }

    /**
     * Imposta il valore della propriet� idRichiestaPS.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdRichiestaPS(String value) {
        this.idRichiestaPS = value;
    }

    /**
     * Recupera il valore della propriet� numeroNosologico.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroNosologico() {
        return numeroNosologico;
    }

    /**
     * Imposta il valore della propriet� numeroNosologico.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroNosologico(String value) {
        this.numeroNosologico = value;
    }

    /**
     * Recupera il valore della propriet� numeroPassaggioPS.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroPassaggioPS() {
        return numeroPassaggioPS;
    }

    /**
     * Imposta il valore della propriet� numeroPassaggioPS.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroPassaggioPS(String value) {
        this.numeroPassaggioPS = value;
    }

    /**
     * Recupera il valore della propriet� tipoEpisodio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoEpisodio() {
        return tipoEpisodio;
    }

    /**
     * Imposta il valore della propriet� tipoEpisodio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoEpisodio(String value) {
        this.tipoEpisodio = value;
    }

    /**
     * Recupera il valore della propriet� tipoEpisodioOriginanteRichiesta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoEpisodioOriginanteRichiesta() {
        return tipoEpisodioOriginanteRichiesta;
    }

    /**
     * Imposta il valore della propriet� tipoEpisodioOriginanteRichiesta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoEpisodioOriginanteRichiesta(String value) {
        this.tipoEpisodioOriginanteRichiesta = value;
    }

}
