/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per utente complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="utente">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.csi.it/gatefire/}persona">
 *       &lt;sequence>
 *         &lt;element name="codiceMatricola" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiceStruttura" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ruolo" type="{http://www.csi.it/gatefire/}ruolo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "utente", propOrder = {
    "codiceMatricola",
    "codiceStruttura",
    "ruolo"
})
public class Utente
    extends Persona
{

    protected String codiceMatricola;
    protected String codiceStruttura;
    protected Ruolo ruolo;

    /**
     * Recupera il valore della propriet� codiceMatricola.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceMatricola() {
        return codiceMatricola;
    }

    /**
     * Imposta il valore della propriet� codiceMatricola.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceMatricola(String value) {
        this.codiceMatricola = value;
    }

    /**
     * Recupera il valore della propriet� codiceStruttura.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceStruttura() {
        return codiceStruttura;
    }

    /**
     * Imposta il valore della propriet� codiceStruttura.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceStruttura(String value) {
        this.codiceStruttura = value;
    }

    /**
     * Recupera il valore della propriet� ruolo.
     * 
     * @return
     *     possible object is
     *     {@link Ruolo }
     *     
     */
    public Ruolo getRuolo() {
        return ruolo;
    }

    /**
     * Imposta il valore della propriet� ruolo.
     * 
     * @param value
     *     allowed object is
     *     {@link Ruolo }
     *     
     */
    public void setRuolo(Ruolo value) {
        this.ruolo = value;
    }

}
