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
 * <p>Classe Java per paziente complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="paziente">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.csi.it/gatefire/}persona">
 *       &lt;sequence>
 *         &lt;element name="codIstatComuneDiNascita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiceNazioneDiNascita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="comuneDiNascita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataDiNascita" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="idAura" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="identificativoGenitoreTutore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="indirizzoResidenza" type="{http://www.csi.it/gatefire/}indirizzo" minOccurs="0"/>
 *         &lt;element name="sesso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paziente", propOrder = {
    "codIstatComuneDiNascita",
    "codiceNazioneDiNascita",
    "comuneDiNascita",
    "dataDiNascita",
    "idAura",
    "identificativoGenitoreTutore",
    "indirizzoResidenza",
    "sesso"
})
public class Paziente
    extends Persona
{

    protected String codIstatComuneDiNascita;
    protected String codiceNazioneDiNascita;
    protected String comuneDiNascita;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataDiNascita;
    protected String idAura;
    protected String identificativoGenitoreTutore;
    protected Indirizzo indirizzoResidenza;
    protected String sesso;

    /**
     * Recupera il valore della propriet� codIstatComuneDiNascita.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodIstatComuneDiNascita() {
        return codIstatComuneDiNascita;
    }

    /**
     * Imposta il valore della propriet� codIstatComuneDiNascita.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodIstatComuneDiNascita(String value) {
        this.codIstatComuneDiNascita = value;
    }

    /**
     * Recupera il valore della propriet� codiceNazioneDiNascita.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceNazioneDiNascita() {
        return codiceNazioneDiNascita;
    }

    /**
     * Imposta il valore della propriet� codiceNazioneDiNascita.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceNazioneDiNascita(String value) {
        this.codiceNazioneDiNascita = value;
    }

    /**
     * Recupera il valore della propriet� comuneDiNascita.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComuneDiNascita() {
        return comuneDiNascita;
    }

    /**
     * Imposta il valore della propriet� comuneDiNascita.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComuneDiNascita(String value) {
        this.comuneDiNascita = value;
    }

    /**
     * Recupera il valore della propriet� dataDiNascita.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataDiNascita() {
        return dataDiNascita;
    }

    /**
     * Imposta il valore della propriet� dataDiNascita.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataDiNascita(XMLGregorianCalendar value) {
        this.dataDiNascita = value;
    }

    /**
     * Recupera il valore della propriet� idAura.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdAura() {
        return idAura;
    }

    /**
     * Imposta il valore della propriet� idAura.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdAura(String value) {
        this.idAura = value;
    }

    /**
     * Recupera il valore della propriet� identificativoGenitoreTutore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoGenitoreTutore() {
        return identificativoGenitoreTutore;
    }

    /**
     * Imposta il valore della propriet� identificativoGenitoreTutore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoGenitoreTutore(String value) {
        this.identificativoGenitoreTutore = value;
    }

    /**
     * Recupera il valore della propriet� indirizzoResidenza.
     * 
     * @return
     *     possible object is
     *     {@link Indirizzo }
     *     
     */
    public Indirizzo getIndirizzoResidenza() {
        return indirizzoResidenza;
    }

    /**
     * Imposta il valore della propriet� indirizzoResidenza.
     * 
     * @param value
     *     allowed object is
     *     {@link Indirizzo }
     *     
     */
    public void setIndirizzoResidenza(Indirizzo value) {
        this.indirizzoResidenza = value;
    }

    /**
     * Recupera il valore della propriet� sesso.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSesso() {
        return sesso;
    }

    /**
     * Imposta il valore della propriet� sesso.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSesso(String value) {
        this.sesso = value;
    }

}
