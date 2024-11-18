/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire;

import it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.org.openehealth.ipf.xds.SubmissionSet;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per repositoryQueryResult complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="repositoryQueryResult">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.csi.it/gatefire/}baseResult">
 *       &lt;sequence>
 *         &lt;element name="documentEntry" type="{http://www.csi.it/gatefire/}DocumentEntry" minOccurs="0"/>
 *         &lt;element name="submissionSet" type="{http://www.openehealth.org/ipf/xds}SubmissionSet" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "repositoryQueryResult", propOrder = {
    "documentEntry",
    "submissionSet"
})
public class RepositoryQueryResult
    extends BaseResult
{

    protected DocumentEntry documentEntry;
    protected SubmissionSet submissionSet;

    /**
     * Recupera il valore della propriet� documentEntry.
     * 
     * @return
     *     possible object is
     *     {@link DocumentEntry }
     *     
     */
    public DocumentEntry getDocumentEntry() {
        return documentEntry;
    }

    /**
     * Imposta il valore della propriet� documentEntry.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentEntry }
     *     
     */
    public void setDocumentEntry(DocumentEntry value) {
        this.documentEntry = value;
    }

    /**
     * Recupera il valore della propriet� submissionSet.
     * 
     * @return
     *     possible object is
     *     {@link SubmissionSet }
     *     
     */
    public SubmissionSet getSubmissionSet() {
        return submissionSet;
    }

    /**
     * Imposta il valore della propriet� submissionSet.
     * 
     * @param value
     *     allowed object is
     *     {@link SubmissionSet }
     *     
     */
    public void setSubmissionSet(SubmissionSet value) {
        this.submissionSet = value;
    }

}
