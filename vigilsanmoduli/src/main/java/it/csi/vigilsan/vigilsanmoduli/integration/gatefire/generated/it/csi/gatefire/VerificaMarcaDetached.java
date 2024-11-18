/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per verificaMarcaDetached complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="verificaMarcaDetached">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="mark" type="{http://www.csi.it/gatefire/}attachment" minOccurs="0"/>
 *         &lt;element name="originalFile" type="{http://www.csi.it/gatefire/}attachment" minOccurs="0"/>
 *         &lt;element name="callInfo" type="{http://www.csi.it/gatefire/}callInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "verificaMarcaDetached", propOrder = {
    "mark",
    "originalFile",
    "callInfo"
})
public class VerificaMarcaDetached {

    protected Attachment mark;
    protected Attachment originalFile;
    protected CallInfo callInfo;

    /**
     * Recupera il valore della propriet� mark.
     * 
     * @return
     *     possible object is
     *     {@link Attachment }
     *     
     */
    public Attachment getMark() {
        return mark;
    }

    /**
     * Imposta il valore della propriet� mark.
     * 
     * @param value
     *     allowed object is
     *     {@link Attachment }
     *     
     */
    public void setMark(Attachment value) {
        this.mark = value;
    }

    /**
     * Recupera il valore della propriet� originalFile.
     * 
     * @return
     *     possible object is
     *     {@link Attachment }
     *     
     */
    public Attachment getOriginalFile() {
        return originalFile;
    }

    /**
     * Imposta il valore della propriet� originalFile.
     * 
     * @param value
     *     allowed object is
     *     {@link Attachment }
     *     
     */
    public void setOriginalFile(Attachment value) {
        this.originalFile = value;
    }

    /**
     * Recupera il valore della propriet� callInfo.
     * 
     * @return
     *     possible object is
     *     {@link CallInfo }
     *     
     */
    public CallInfo getCallInfo() {
        return callInfo;
    }

    /**
     * Imposta il valore della propriet� callInfo.
     * 
     * @param value
     *     allowed object is
     *     {@link CallInfo }
     *     
     */
    public void setCallInfo(CallInfo value) {
        this.callInfo = value;
    }

}
