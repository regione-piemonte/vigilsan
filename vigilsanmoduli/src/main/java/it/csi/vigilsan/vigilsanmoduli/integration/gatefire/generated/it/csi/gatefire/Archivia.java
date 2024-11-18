/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per archivia complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="archivia">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="repositoryInput" type="{http://www.csi.it/gatefire/}repositoryInput" minOccurs="0"/>
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
@XmlType(name = "archivia", propOrder = {
    "repositoryInput",
    "callInfo"
})
public class Archivia {

    protected RepositoryInput repositoryInput;
    protected CallInfo callInfo;

    /**
     * Recupera il valore della propriet� repositoryInput.
     * 
     * @return
     *     possible object is
     *     {@link RepositoryInput }
     *     
     */
    public RepositoryInput getRepositoryInput() {
        return repositoryInput;
    }

    /**
     * Imposta il valore della propriet� repositoryInput.
     * 
     * @param value
     *     allowed object is
     *     {@link RepositoryInput }
     *     
     */
    public void setRepositoryInput(RepositoryInput value) {
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
