/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per autoSignIdentity complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="autoSignIdentity">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.csi.it/gatefire/}identity">
 *       &lt;sequence>
 *         &lt;element name="delegatedDomain" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="delegatedPassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="delegatedUser" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "autoSignIdentity", propOrder = {
    "delegatedDomain",
    "delegatedPassword",
    "delegatedUser"
})
public class AutoSignIdentity
    extends Identity
{

    protected String delegatedDomain;
    protected String delegatedPassword;
    protected String delegatedUser;

    /**
     * Recupera il valore della propriet� delegatedDomain.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDelegatedDomain() {
        return delegatedDomain;
    }

    /**
     * Imposta il valore della propriet� delegatedDomain.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDelegatedDomain(String value) {
        this.delegatedDomain = value;
    }

    /**
     * Recupera il valore della propriet� delegatedPassword.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDelegatedPassword() {
        return delegatedPassword;
    }

    /**
     * Imposta il valore della propriet� delegatedPassword.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDelegatedPassword(String value) {
        this.delegatedPassword = value;
    }

    /**
     * Recupera il valore della propriet� delegatedUser.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDelegatedUser() {
        return delegatedUser;
    }

    /**
     * Imposta il valore della propriet� delegatedUser.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDelegatedUser(String value) {
        this.delegatedUser = value;
    }

}
