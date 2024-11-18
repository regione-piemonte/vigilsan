/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire;

import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per documento complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="documento">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="accessionNumber" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="assettoOrganizzativo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiceDocumentoScaricabile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codicePin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataDisponibilitaReferto" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dataOraFirmaDocumento" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="firmatoDigitalmente" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="formatCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="importoTicketDaPagare" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="importoTicketPagato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="invioCLS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="invioFSE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="livelloRiservatezza" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="medicoRedattore" type="{http://www.csi.it/gatefire/}medico" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="medicoValidatore" type="{http://www.csi.it/gatefire/}medico" minOccurs="0"/>
 *         &lt;element name="mimeType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nre" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="oscuraScaricoCittadino" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="oscuramentoDocDSE" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="pagatoTicket" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="privacyDocumento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="scaricabileDalCittadino" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="soggettoALeggiSpeciali" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="tipoAttivitaClinica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoFirma" type="{http://www.csi.it/gatefire/}tipoFirma" minOccurs="0"/>
 *         &lt;element name="tipologiaDocumentoAlto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipologiaDocumentoMedio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipologiaStrutturaProdDoc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uniqueId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "documento", propOrder = {
    "accessionNumber",
    "assettoOrganizzativo",
    "codiceDocumentoScaricabile",
    "codicePin",
    "dataDisponibilitaReferto",
    "dataOraFirmaDocumento",
    "firmatoDigitalmente",
    "formatCode",
    "importoTicketDaPagare",
    "importoTicketPagato",
    "invioCLS",
    "invioFSE",
    "livelloRiservatezza",
    "medicoRedattore",
    "medicoValidatore",
    "mimeType",
    "nre",
    "oscuraScaricoCittadino",
    "oscuramentoDocDSE",
    "pagatoTicket",
    "privacyDocumento",
    "scaricabileDalCittadino",
    "soggettoALeggiSpeciali",
    "tipoAttivitaClinica",
    "tipoFirma",
    "tipologiaDocumentoAlto",
    "tipologiaDocumentoMedio",
    "tipologiaStrutturaProdDoc",
    "uniqueId"
})
public class Documento {

    @XmlElement(nillable = true)
    protected List<String> accessionNumber;
    protected String assettoOrganizzativo;
    protected String codiceDocumentoScaricabile;
    protected String codicePin;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataDisponibilitaReferto;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataOraFirmaDocumento;
    protected Boolean firmatoDigitalmente;
    protected String formatCode;
    protected String importoTicketDaPagare;
    protected String importoTicketPagato;
    protected String invioCLS;
    protected String invioFSE;
    protected String livelloRiservatezza;
    @XmlElement(nillable = true)
    protected List<Medico> medicoRedattore;
    protected Medico medicoValidatore;
    protected String mimeType;
    @XmlElement(nillable = true)
    protected List<String> nre;
    protected String oscuraScaricoCittadino;
    protected Boolean oscuramentoDocDSE;
    protected String pagatoTicket;
    protected String privacyDocumento;
    protected Boolean scaricabileDalCittadino;
    protected Boolean soggettoALeggiSpeciali;
    protected String tipoAttivitaClinica;
    protected TipoFirma tipoFirma;
    protected String tipologiaDocumentoAlto;
    protected String tipologiaDocumentoMedio;
    protected String tipologiaStrutturaProdDoc;
    protected String uniqueId;

    /**
     * Gets the value of the accessionNumber property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the accessionNumber property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAccessionNumber().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getAccessionNumber() {
        if (accessionNumber == null) {
            accessionNumber = new ArrayList<String>();
        }
        return this.accessionNumber;
    }

    /**
     * Recupera il valore della propriet� assettoOrganizzativo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssettoOrganizzativo() {
        return assettoOrganizzativo;
    }

    /**
     * Imposta il valore della propriet� assettoOrganizzativo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssettoOrganizzativo(String value) {
        this.assettoOrganizzativo = value;
    }

    /**
     * Recupera il valore della propriet� codiceDocumentoScaricabile.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceDocumentoScaricabile() {
        return codiceDocumentoScaricabile;
    }

    /**
     * Imposta il valore della propriet� codiceDocumentoScaricabile.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceDocumentoScaricabile(String value) {
        this.codiceDocumentoScaricabile = value;
    }

    /**
     * Recupera il valore della propriet� codicePin.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodicePin() {
        return codicePin;
    }

    /**
     * Imposta il valore della propriet� codicePin.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodicePin(String value) {
        this.codicePin = value;
    }

    /**
     * Recupera il valore della propriet� dataDisponibilitaReferto.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataDisponibilitaReferto() {
        return dataDisponibilitaReferto;
    }

    /**
     * Imposta il valore della propriet� dataDisponibilitaReferto.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataDisponibilitaReferto(XMLGregorianCalendar value) {
        this.dataDisponibilitaReferto = value;
    }

    /**
     * Recupera il valore della propriet� dataOraFirmaDocumento.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataOraFirmaDocumento() {
        return dataOraFirmaDocumento;
    }

    /**
     * Imposta il valore della propriet� dataOraFirmaDocumento.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataOraFirmaDocumento(XMLGregorianCalendar value) {
        this.dataOraFirmaDocumento = value;
    }

    /**
     * Recupera il valore della propriet� firmatoDigitalmente.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isFirmatoDigitalmente() {
        return firmatoDigitalmente;
    }

    /**
     * Imposta il valore della propriet� firmatoDigitalmente.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFirmatoDigitalmente(Boolean value) {
        this.firmatoDigitalmente = value;
    }

    /**
     * Recupera il valore della propriet� formatCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFormatCode() {
        return formatCode;
    }

    /**
     * Imposta il valore della propriet� formatCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFormatCode(String value) {
        this.formatCode = value;
    }

    /**
     * Recupera il valore della propriet� importoTicketDaPagare.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImportoTicketDaPagare() {
        return importoTicketDaPagare;
    }

    /**
     * Imposta il valore della propriet� importoTicketDaPagare.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImportoTicketDaPagare(String value) {
        this.importoTicketDaPagare = value;
    }

    /**
     * Recupera il valore della propriet� importoTicketPagato.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImportoTicketPagato() {
        return importoTicketPagato;
    }

    /**
     * Imposta il valore della propriet� importoTicketPagato.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImportoTicketPagato(String value) {
        this.importoTicketPagato = value;
    }

    /**
     * Recupera il valore della propriet� invioCLS.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvioCLS() {
        return invioCLS;
    }

    /**
     * Imposta il valore della propriet� invioCLS.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvioCLS(String value) {
        this.invioCLS = value;
    }

    /**
     * Recupera il valore della propriet� invioFSE.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvioFSE() {
        return invioFSE;
    }

    /**
     * Imposta il valore della propriet� invioFSE.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvioFSE(String value) {
        this.invioFSE = value;
    }

    /**
     * Recupera il valore della propriet� livelloRiservatezza.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLivelloRiservatezza() {
        return livelloRiservatezza;
    }

    /**
     * Imposta il valore della propriet� livelloRiservatezza.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLivelloRiservatezza(String value) {
        this.livelloRiservatezza = value;
    }

    /**
     * Gets the value of the medicoRedattore property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the medicoRedattore property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMedicoRedattore().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Medico }
     * 
     * 
     */
    public List<Medico> getMedicoRedattore() {
        if (medicoRedattore == null) {
            medicoRedattore = new ArrayList<Medico>();
        }
        return this.medicoRedattore;
    }

    /**
     * Recupera il valore della propriet� medicoValidatore.
     * 
     * @return
     *     possible object is
     *     {@link Medico }
     *     
     */
    public Medico getMedicoValidatore() {
        return medicoValidatore;
    }

    /**
     * Imposta il valore della propriet� medicoValidatore.
     * 
     * @param value
     *     allowed object is
     *     {@link Medico }
     *     
     */
    public void setMedicoValidatore(Medico value) {
        this.medicoValidatore = value;
    }

    /**
     * Recupera il valore della propriet� mimeType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * Imposta il valore della propriet� mimeType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMimeType(String value) {
        this.mimeType = value;
    }

    /**
     * Gets the value of the nre property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nre property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNre().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getNre() {
        if (nre == null) {
            nre = new ArrayList<String>();
        }
        return this.nre;
    }

    /**
     * Recupera il valore della propriet� oscuraScaricoCittadino.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOscuraScaricoCittadino() {
        return oscuraScaricoCittadino;
    }

    /**
     * Imposta il valore della propriet� oscuraScaricoCittadino.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOscuraScaricoCittadino(String value) {
        this.oscuraScaricoCittadino = value;
    }

    /**
     * Recupera il valore della propriet� oscuramentoDocDSE.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isOscuramentoDocDSE() {
        return oscuramentoDocDSE;
    }

    /**
     * Imposta il valore della propriet� oscuramentoDocDSE.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setOscuramentoDocDSE(Boolean value) {
        this.oscuramentoDocDSE = value;
    }

    /**
     * Recupera il valore della propriet� pagatoTicket.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPagatoTicket() {
        return pagatoTicket;
    }

    /**
     * Imposta il valore della propriet� pagatoTicket.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPagatoTicket(String value) {
        this.pagatoTicket = value;
    }

    /**
     * Recupera il valore della propriet� privacyDocumento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrivacyDocumento() {
        return privacyDocumento;
    }

    /**
     * Imposta il valore della propriet� privacyDocumento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrivacyDocumento(String value) {
        this.privacyDocumento = value;
    }

    /**
     * Recupera il valore della propriet� scaricabileDalCittadino.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isScaricabileDalCittadino() {
        return scaricabileDalCittadino;
    }

    /**
     * Imposta il valore della propriet� scaricabileDalCittadino.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setScaricabileDalCittadino(Boolean value) {
        this.scaricabileDalCittadino = value;
    }

    /**
     * Recupera il valore della propriet� soggettoALeggiSpeciali.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSoggettoALeggiSpeciali() {
        return soggettoALeggiSpeciali;
    }

    /**
     * Imposta il valore della propriet� soggettoALeggiSpeciali.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSoggettoALeggiSpeciali(Boolean value) {
        this.soggettoALeggiSpeciali = value;
    }

    /**
     * Recupera il valore della propriet� tipoAttivitaClinica.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoAttivitaClinica() {
        return tipoAttivitaClinica;
    }

    /**
     * Imposta il valore della propriet� tipoAttivitaClinica.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoAttivitaClinica(String value) {
        this.tipoAttivitaClinica = value;
    }

    /**
     * Recupera il valore della propriet� tipoFirma.
     * 
     * @return
     *     possible object is
     *     {@link TipoFirma }
     *     
     */
    public TipoFirma getTipoFirma() {
        return tipoFirma;
    }

    /**
     * Imposta il valore della propriet� tipoFirma.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoFirma }
     *     
     */
    public void setTipoFirma(TipoFirma value) {
        this.tipoFirma = value;
    }

    /**
     * Recupera il valore della propriet� tipologiaDocumentoAlto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipologiaDocumentoAlto() {
        return tipologiaDocumentoAlto;
    }

    /**
     * Imposta il valore della propriet� tipologiaDocumentoAlto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipologiaDocumentoAlto(String value) {
        this.tipologiaDocumentoAlto = value;
    }

    /**
     * Recupera il valore della propriet� tipologiaDocumentoMedio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipologiaDocumentoMedio() {
        return tipologiaDocumentoMedio;
    }

    /**
     * Imposta il valore della propriet� tipologiaDocumentoMedio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipologiaDocumentoMedio(String value) {
        this.tipologiaDocumentoMedio = value;
    }

    /**
     * Recupera il valore della propriet� tipologiaStrutturaProdDoc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipologiaStrutturaProdDoc() {
        return tipologiaStrutturaProdDoc;
    }

    /**
     * Imposta il valore della propriet� tipologiaStrutturaProdDoc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipologiaStrutturaProdDoc(String value) {
        this.tipologiaStrutturaProdDoc = value;
    }

    /**
     * Recupera il valore della propriet� uniqueId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUniqueId() {
        return uniqueId;
    }

    /**
     * Imposta il valore della propriet� uniqueId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUniqueId(String value) {
        this.uniqueId = value;
    }

}
