/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per signLayout complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="signLayout">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="image" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="lowLeftX" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="lowLeftY" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="page" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="reason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="showDateTime" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="text" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="upRightX" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="upRightY" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "signLayout", propOrder = {
    "image",
    "lowLeftX",
    "lowLeftY",
    "page",
    "reason",
    "showDateTime",
    "text",
    "upRightX",
    "upRightY"
})
public class SignLayout {

    protected byte[] image;
    protected Integer lowLeftX;
    protected Integer lowLeftY;
    protected Integer page;
    protected String reason;
    protected boolean showDateTime;
    protected String text;
    protected Integer upRightX;
    protected Integer upRightY;

    /**
     * Recupera il valore della propriet� image.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getImage() {
        return image;
    }

    /**
     * Imposta il valore della propriet� image.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setImage(byte[] value) {
        this.image = value;
    }

    /**
     * Recupera il valore della propriet� lowLeftX.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLowLeftX() {
        return lowLeftX;
    }

    /**
     * Imposta il valore della propriet� lowLeftX.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLowLeftX(Integer value) {
        this.lowLeftX = value;
    }

    /**
     * Recupera il valore della propriet� lowLeftY.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLowLeftY() {
        return lowLeftY;
    }

    /**
     * Imposta il valore della propriet� lowLeftY.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLowLeftY(Integer value) {
        this.lowLeftY = value;
    }

    /**
     * Recupera il valore della propriet� page.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPage() {
        return page;
    }

    /**
     * Imposta il valore della propriet� page.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPage(Integer value) {
        this.page = value;
    }

    /**
     * Recupera il valore della propriet� reason.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReason() {
        return reason;
    }

    /**
     * Imposta il valore della propriet� reason.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReason(String value) {
        this.reason = value;
    }

    /**
     * Recupera il valore della propriet� showDateTime.
     * 
     */
    public boolean isShowDateTime() {
        return showDateTime;
    }

    /**
     * Imposta il valore della propriet� showDateTime.
     * 
     */
    public void setShowDateTime(boolean value) {
        this.showDateTime = value;
    }

    /**
     * Recupera il valore della propriet� text.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getText() {
        return text;
    }

    /**
     * Imposta il valore della propriet� text.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setText(String value) {
        this.text = value;
    }

    /**
     * Recupera il valore della propriet� upRightX.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getUpRightX() {
        return upRightX;
    }

    /**
     * Imposta il valore della propriet� upRightX.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setUpRightX(Integer value) {
        this.upRightX = value;
    }

    /**
     * Recupera il valore della propriet� upRightY.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getUpRightY() {
        return upRightY;
    }

    /**
     * Imposta il valore della propriet� upRightY.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setUpRightY(Integer value) {
        this.upRightY = value;
    }

}
