/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per annullaDocumento complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="annullaDocumento">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="repositoryInput" type="{http://www.csi.it/gatefire/}repositoryUndoInput" minOccurs="0"/>
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
@XmlType(name = "annullaDocumento", propOrder = {
    "repositoryInput",
    "callInfo"
})
public class AnnullaDocumento {

    protected RepositoryUndoInput repositoryInput;
    protected CallInfo callInfo;

    /**
     * Recupera il valore della propriet� repositoryInput.
     * 
     * @return
     *     possible object is
     *     {@link RepositoryUndoInput }
     *     
     */
    public RepositoryUndoInput getRepositoryInput() {
        return repositoryInput;
    }

    /**
     * Imposta il valore della propriet� repositoryInput.
     * 
     * @param value
     *     allowed object is
     *     {@link RepositoryUndoInput }
     *     
     */
    public void setRepositoryInput(RepositoryUndoInput value) {
        this.repositoryInput = value;
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
