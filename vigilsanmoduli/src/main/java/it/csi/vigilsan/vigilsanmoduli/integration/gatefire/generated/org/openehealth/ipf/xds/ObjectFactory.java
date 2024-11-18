/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.org.openehealth.ipf.xds;

import javax.xml.namespace.QName;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.org.openehealth.ipf.xds package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _DocumentEntry_QNAME = new QName("http://www.openehealth.org/ipf/xds", "documentEntry");
    private final static QName _SubmissionSet_QNAME = new QName("http://www.openehealth.org/ipf/xds", "submissionSet");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.org.openehealth.ipf.xds
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.org.openehealth.ipf.xds.IdentifiedObject.ExtraMetadata }
     * 
     */
    public it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.org.openehealth.ipf.xds.IdentifiedObject.ExtraMetadata createIdentifiedObjectExtraMetadata() {
        return new it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.org.openehealth.ipf.xds.IdentifiedObject.ExtraMetadata();
    }

    /**
     * Create an instance of {@link SubmissionSet }
     * 
     */
    public SubmissionSet createSubmissionSet() {
        return new SubmissionSet();
    }

    /**
     * Create an instance of {@link DocumentEntry }
     * 
     */
    public DocumentEntry createDocumentEntry() {
        return new DocumentEntry();
    }

    /**
     * Create an instance of {@link PatientInfo }
     * 
     */
    public PatientInfo createPatientInfo() {
        return new PatientInfo();
    }

    /**
     * Create an instance of {@link XcnName }
     * 
     */
    public XcnName createXcnName() {
        return new XcnName();
    }

    /**
     * Create an instance of {@link Author }
     * 
     */
    public Author createAuthor() {
        return new Author();
    }

    /**
     * Create an instance of {@link Organization }
     * 
     */
    public Organization createOrganization() {
        return new Organization();
    }

    /**
     * Create an instance of {@link LocalizedString }
     * 
     */
    public LocalizedString createLocalizedString() {
        return new LocalizedString();
    }

    /**
     * Create an instance of {@link CXiAssigningAuthority }
     * 
     */
    public CXiAssigningAuthority createCXiAssigningAuthority() {
        return new CXiAssigningAuthority();
    }

    /**
     * Create an instance of {@link Code }
     * 
     */
    public Code createCode() {
        return new Code();
    }

    /**
     * Create an instance of {@link Timestamp }
     * 
     */
    public Timestamp createTimestamp() {
        return new Timestamp();
    }

    /**
     * Create an instance of {@link Telecom }
     * 
     */
    public Telecom createTelecom() {
        return new Telecom();
    }

    /**
     * Create an instance of {@link AssigningAuthority }
     * 
     */
    public AssigningAuthority createAssigningAuthority() {
        return new AssigningAuthority();
    }

    /**
     * Create an instance of {@link Address }
     * 
     */
    public Address createAddress() {
        return new Address();
    }

    /**
     * Create an instance of {@link Identifiable }
     * 
     */
    public Identifiable createIdentifiable() {
        return new Identifiable();
    }

    /**
     * Create an instance of {@link Version }
     * 
     */
    public Version createVersion() {
        return new Version();
    }

    /**
     * Create an instance of {@link Recipient }
     * 
     */
    public Recipient createRecipient() {
        return new Recipient();
    }

    /**
     * Create an instance of {@link ReferenceId }
     * 
     */
    public ReferenceId createReferenceId() {
        return new ReferenceId();
    }

    /**
     * Create an instance of {@link Person }
     * 
     */
    public Person createPerson() {
        return new Person();
    }

    /**
     * Create an instance of {@link it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.org.openehealth.ipf.xds.IdentifiedObject.ExtraMetadata.Entry }
     * 
     */
    public it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.org.openehealth.ipf.xds.IdentifiedObject.ExtraMetadata.Entry createIdentifiedObjectExtraMetadataEntry() {
        return new it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.org.openehealth.ipf.xds.IdentifiedObject.ExtraMetadata.Entry();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DocumentEntry }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.openehealth.org/ipf/xds", name = "documentEntry")
    public JAXBElement<DocumentEntry> createDocumentEntry(DocumentEntry value) {
        return new JAXBElement<DocumentEntry>(_DocumentEntry_QNAME, DocumentEntry.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubmissionSet }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.openehealth.org/ipf/xds", name = "submissionSet")
    public JAXBElement<SubmissionSet> createSubmissionSet(SubmissionSet value) {
        return new JAXBElement<SubmissionSet>(_SubmissionSet_QNAME, SubmissionSet.class, null, value);
    }

}
