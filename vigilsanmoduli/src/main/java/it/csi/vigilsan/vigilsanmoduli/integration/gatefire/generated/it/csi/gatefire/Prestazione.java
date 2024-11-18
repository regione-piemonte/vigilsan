/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per prestazione complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="prestazione">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codiceBrancaRegionale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiceCatalogoRegionalePrestazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "prestazione", propOrder = {
    "codiceBrancaRegionale",
    "codiceCatalogoRegionalePrestazione"
})
public class Prestazione {

    protected String codiceBrancaRegionale;
    protected String codiceCatalogoRegionalePrestazione;

    /**
     * Recupera il valore della propriet� codiceBrancaRegionale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceBrancaRegionale() {
        return codiceBrancaRegionale;
    }

    /**
     * Imposta il valore della propriet� codiceBrancaRegionale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceBrancaRegionale(String value) {
        this.codiceBrancaRegionale = value;
    }

    /**
     * Recupera il valore della propriet� codiceCatalogoRegionalePrestazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceCatalogoRegionalePrestazione() {
        return codiceCatalogoRegionalePrestazione;
    }

    /**
     * Imposta il valore della propriet� codiceCatalogoRegionalePrestazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceCatalogoRegionalePrestazione(String value) {
        this.codiceCatalogoRegionalePrestazione = value;
    }

}
