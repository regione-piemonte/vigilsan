/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.ws.RequestWrapper;
import jakarta.xml.ws.ResponseWrapper;

/**
 * Servizio di gatefire
 *
 * This class was generated by Apache CXF 2.7.7
 * 2023-02-13T15:33:08.197+01:00
 * Generated source version: 2.7.7
 * 
 */
@WebService(targetNamespace = "http://www.csi.it/gatefire/", name = "gateFireSrvc")
@XmlSeeAlso({ObjectFactory.class, it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.org.openehealth.ipf.xds.ObjectFactory.class})
public interface GateFireSrvc {

    /**
     * firma XAdES Massiva  - se otp specificato: firma remota. Se otp nullo: firma automatica.
     */
    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "firmaXAdESMassiva", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FirmaXAdESMassiva")
    @WebMethod
    @ResponseWrapper(localName = "firmaXAdESMassivaResponse", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FirmaXAdESMassivaResponse")
    public it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FileResult firmaXAdESMassiva(
        @WebParam(name = "attachment", targetNamespace = "")
        java.util.List<it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.Attachment> attachment,
        @WebParam(name = "xadesInput", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.XadesInput xadesInput,
        @WebParam(name = "identity", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.Identity identity
    );

    /**
     * Marca temporale
     */
    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "marcaTemporale", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.MarcaTemporale")
    @WebMethod
    @ResponseWrapper(localName = "marcaTemporaleResponse", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.MarcaTemporaleResponse")
    public it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FileResult marcaTemporale(
        @WebParam(name = "attachment", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.Attachment attachment,
        @WebParam(name = "markInput", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.MarkInput markInput
    );

    /**
     * firma PAdES Massiva Automatica
     */
    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "firmaPAdESMassivaAutomatica", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FirmaPAdESMassivaAutomatica")
    @WebMethod
    @ResponseWrapper(localName = "firmaPAdESMassivaAutomaticaResponse", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FirmaPAdESMassivaAutomaticaResponse")
    public it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FileResult firmaPAdESMassivaAutomatica(
        @WebParam(name = "attachment", targetNamespace = "")
        java.util.List<it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.Attachment> attachment,
        @WebParam(name = "padesInput", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.PadesInput padesInput,
        @WebParam(name = "identity", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.AutoSignIdentity identity
    );

    /**
     * Verifica disponibilita' servizio di firma
     */
    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "ping", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.Ping")
    @WebMethod
    @ResponseWrapper(localName = "pingResponse", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.PingResponse")
    public it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.PingResult ping(
        @WebParam(name = "user", targetNamespace = "")
        java.lang.String user,
        @WebParam(name = "callInfo", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.CallInfo callInfo
    );

    /**
     * firma PAdES Massiva Remota
     */
    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "firmaPAdESMassivaRemota", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FirmaPAdESMassivaRemota")
    @WebMethod
    @ResponseWrapper(localName = "firmaPAdESMassivaRemotaResponse", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FirmaPAdESMassivaRemotaResponse")
    public it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FileResult firmaPAdESMassivaRemota(
        @WebParam(name = "attachment", targetNamespace = "")
        java.util.List<it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.Attachment> attachment,
        @WebParam(name = "padesInput", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.PadesInput padesInput,
        @WebParam(name = "identity", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.SignIdentity identity
    );

    /**
     * Apertura sessione di firma multipla. Si ottiene il session id da utlizzare nelle chiamate successive
     */
    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "openSession", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.OpenSession")
    @WebMethod
    @ResponseWrapper(localName = "openSessionResponse", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.OpenSessionResponse")
    public it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.SessionIdResult openSession(
        @WebParam(name = "sessionInput", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.SessionInput sessionInput
    );

    /**
     * firma XAdES - se otp specificato: firma remota. Se otp nullo: firma automatica.
     */
    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "firmaXAdES", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FirmaXAdES")
    @WebMethod
    @ResponseWrapper(localName = "firmaXAdESResponse", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FirmaXAdESResponse")
    public it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FileResult firmaXAdES(
        @WebParam(name = "attachment", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.Attachment attachment,
        @WebParam(name = "xadesInput", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.XadesInput xadesInput,
        @WebParam(name = "identity", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.Identity identity
    );

    /**
     * firma PAdES Automatica
     */
    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "firmaPAdESAutomatica", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FirmaPAdESAutomatica")
    @WebMethod
    @ResponseWrapper(localName = "firmaPAdESAutomaticaResponse", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FirmaPAdESAutomaticaResponse")
    public it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FileResult firmaPAdESAutomatica(
        @WebParam(name = "attachment", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.Attachment attachment,
        @WebParam(name = "padesInput", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.PadesInput padesInput,
        @WebParam(name = "identity", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.AutoSignIdentity identity
    );

    /**
     * firma PAdES con archiviazione del documento firmato su repository - se otp specificato: firma remota. Se otp nullo: firma automatica.
     */
    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "firmaPAdESConArchiviaz", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FirmaPAdESConArchiviaz")
    @WebMethod
    @ResponseWrapper(localName = "firmaPAdESConArchiviazResponse", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FirmaPAdESConArchiviazResponse")
    public it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FileResult firmaPAdESConArchiviaz(
        @WebParam(name = "attachment", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.Attachment attachment,
        @WebParam(name = "padesInput", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.PadesInput padesInput,
        @WebParam(name = "identity", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.Identity identity,
        @WebParam(name = "metadata", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.ItiMetadata metadata,
        @WebParam(name = "assertionIdentity", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.AssertionIdentity assertionIdentity
    );

    /**
     * Recupero metadati di un documento su repository.
     */
    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "recuperaMetadataDocumento", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.RecuperaMetadataDocumento")
    @WebMethod
    @ResponseWrapper(localName = "recuperaMetadataDocumentoResponse", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.RecuperaMetadataDocumentoResponse")
    public it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.RepositoryQueryResult recuperaMetadataDocumento(
        @WebParam(name = "repositoryInput", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.RepositoryDocIdInput repositoryInput,
        @WebParam(name = "callInfo", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.CallInfo callInfo
    );

    /**
     * richiesta otp
     */
    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "richiestaOtp", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.RichiestaOtp")
    @WebMethod
    @ResponseWrapper(localName = "richiestaOtpResponse", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.RichiestaOtpResponse")
    public it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.OtpResult richiestaOtp(
        @WebParam(name = "otpReqInput", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.OtpReqInput otpReqInput
    );

    /**
     * verifica marca
     */
    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "verificaMarca", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.VerificaMarca")
    @WebMethod
    @ResponseWrapper(localName = "verificaMarcaResponse", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.VerificaMarcaResponse")
    public it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.MarkVerifyResult verificaMarca(
        @WebParam(name = "attachment", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.Attachment attachment,
        @WebParam(name = "callInfo", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.CallInfo callInfo
    );

    /**
     * Recupero di un documento su repository.
     */
    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "recuperaDocumento", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.RecuperaDocumento")
    @WebMethod
    @ResponseWrapper(localName = "recuperaDocumentoResponse", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.RecuperaDocumentoResponse")
    public it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FileResult recuperaDocumento(
        @WebParam(name = "repositoryInput", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.RepositoryDocIdInput repositoryInput,
        @WebParam(name = "callInfo", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.CallInfo callInfo
    );

    /**
     * verifica firma
     */
    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "verificaFirma", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.VerificaFirma")
    @WebMethod
    @ResponseWrapper(localName = "verificaFirmaResponse", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.VerificaFirmaResponse")
    public it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.SignVerifyResult verificaFirma(
        @WebParam(name = "attachment", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.Attachment attachment,
        @WebParam(name = "callInfo", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.CallInfo callInfo
    );

    /**
     * Modifica dei metadati di un documento su repository.
     */
    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "modificaMetadati", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.ModificaMetadati")
    @WebMethod
    @ResponseWrapper(localName = "modificaMetadatiResponse", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.ModificaMetadatiResponse")
    public it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.Result modificaMetadati(
        @WebParam(name = "repositoryInput", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.RepositoryMetadataUpdateInput repositoryInput,
        @WebParam(name = "callInfo", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.CallInfo callInfo
    );

    /**
     * firma XAdES Massiva Automatica
     */
    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "firmaXAdESMassivaAutomatica", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FirmaXAdESMassivaAutomatica")
    @WebMethod
    @ResponseWrapper(localName = "firmaXAdESMassivaAutomaticaResponse", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FirmaXAdESMassivaAutomaticaResponse")
    public it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FileResult firmaXAdESMassivaAutomatica(
        @WebParam(name = "attachment", targetNamespace = "")
        java.util.List<it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.Attachment> attachment,
        @WebParam(name = "xadesInput", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.XadesInput xadesInput,
        @WebParam(name = "identity", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.AutoSignIdentity identity
    );

    /**
     * firma PAdES - se otp specificato: firma remota. Se otp nullo: firma automatica.
     */
    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "firmaPAdES", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FirmaPAdES")
    @WebMethod
    @ResponseWrapper(localName = "firmaPAdESResponse", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FirmaPAdESResponse")
    public it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FileResult firmaPAdES(
        @WebParam(name = "attachment", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.Attachment attachment,
        @WebParam(name = "padesInput", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.PadesInput padesInput,
        @WebParam(name = "identity", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.Identity identity
    );

    /**
     * firma PAdES Massiva  - se otp specificato: firma remota. Se otp nullo: firma automatica.
     */
    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "firmaPAdESMassiva", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FirmaPAdESMassiva")
    @WebMethod
    @ResponseWrapper(localName = "firmaPAdESMassivaResponse", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FirmaPAdESMassivaResponse")
    public it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FileResult firmaPAdESMassiva(
        @WebParam(name = "attachment", targetNamespace = "")
        java.util.List<it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.Attachment> attachment,
        @WebParam(name = "padesInput", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.PadesInput padesInput,
        @WebParam(name = "identity", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.Identity identity
    );

    /**
     * Chiusura sessione di firma multipla.
     */
    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "closeSession", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.CloseSession")
    @WebMethod
    @ResponseWrapper(localName = "closeSessionResponse", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.CloseSessionResponse")
    public it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.Result closeSession(
        @WebParam(name = "sessionId", targetNamespace = "")
        java.lang.String sessionId,
        @WebParam(name = "sessionInput", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.SessionInput sessionInput
    );

    /**
     * firma PAdES remota
     */
    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "firmaPAdESRemota", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FirmaPAdESRemota")
    @WebMethod
    @ResponseWrapper(localName = "firmaPAdESRemotaResponse", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FirmaPAdESRemotaResponse")
    public it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FileResult firmaPAdESRemota(
        @WebParam(name = "attachment", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.Attachment attachment,
        @WebParam(name = "padesInput", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.PadesInput padesInput,
        @WebParam(name = "identity", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.SignIdentity identity
    );

    /**
     * firma XAdES Massiva Remota
     */
    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "firmaXAdESMassivaRemota", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FirmaXAdESMassivaRemota")
    @WebMethod
    @ResponseWrapper(localName = "firmaXAdESMassivaRemotaResponse", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FirmaXAdESMassivaRemotaResponse")
    public it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FileResult firmaXAdESMassivaRemota(
        @WebParam(name = "attachment", targetNamespace = "")
        java.util.List<it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.Attachment> attachment,
        @WebParam(name = "xadesInput", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.XadesInput xadesInput,
        @WebParam(name = "identity", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.SignIdentity identity
    );

    /**
     * Archiviazione di un documento su repository.
     */
    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "archivia", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.Archivia")
    @WebMethod
    @ResponseWrapper(localName = "archiviaResponse", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.ArchiviaResponse")
    public it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.Result archivia(
        @WebParam(name = "repositoryInput", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.RepositoryInput repositoryInput,
        @WebParam(name = "callInfo", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.CallInfo callInfo
    );

    /**
     * firma PAdES remota con archiviazione del documento firmato su repository.
     */
    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "firmaPAdESRemotaConArchiviaz", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FirmaPAdESRemotaConArchiviaz")
    @WebMethod
    @ResponseWrapper(localName = "firmaPAdESRemotaConArchiviazResponse", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FirmaPAdESRemotaConArchiviazResponse")
    public it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FileResult firmaPAdESRemotaConArchiviaz(
        @WebParam(name = "attachment", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.Attachment attachment,
        @WebParam(name = "padesInput", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.PadesInput padesInput,
        @WebParam(name = "identity", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.SignIdentity identity,
        @WebParam(name = "metadata", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.ItiMetadata metadata,
        @WebParam(name = "assertionIdentity", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.AssertionIdentity assertionIdentity
    );

    /**
     * firma PAdES Automatica con archiviazione del documento firmato su repository.
     */
    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "firmaPAdESAutomaticaConArchiviaz", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FirmaPAdESAutomaticaConArchiviaz")
    @WebMethod
    @ResponseWrapper(localName = "firmaPAdESAutomaticaConArchiviazResponse", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FirmaPAdESAutomaticaConArchiviazResponse")
    public it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FileResult firmaPAdESAutomaticaConArchiviaz(
        @WebParam(name = "attachment", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.Attachment attachment,
        @WebParam(name = "padesInput", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.PadesInput padesInput,
        @WebParam(name = "identity", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.AutoSignIdentity identity,
        @WebParam(name = "metadata", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.ItiMetadata metadata,
        @WebParam(name = "assertionIdentity", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.AssertionIdentity assertionIdentity
    );

    /**
     * firma XAdES Automatica
     */
    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "firmaXAdESAutomatica", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FirmaXAdESAutomatica")
    @WebMethod
    @ResponseWrapper(localName = "firmaXAdESAutomaticaResponse", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FirmaXAdESAutomaticaResponse")
    public it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FileResult firmaXAdESAutomatica(
        @WebParam(name = "attachment", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.Attachment attachment,
        @WebParam(name = "xadesInput", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.XadesInput xadesInput,
        @WebParam(name = "identity", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.AutoSignIdentity identity
    );

    /**
     * verifica marca detached
     */
    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "verificaMarcaDetached", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.VerificaMarcaDetached")
    @WebMethod
    @ResponseWrapper(localName = "verificaMarcaDetachedResponse", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.VerificaMarcaDetachedResponse")
    public it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.MarkVerifyResult verificaMarcaDetached(
        @WebParam(name = "mark", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.Attachment mark,
        @WebParam(name = "originalFile", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.Attachment originalFile,
        @WebParam(name = "callInfo", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.CallInfo callInfo
    );

    /**
     * firma XAdES remota
     */
    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "firmaXAdESRemota", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FirmaXAdESRemota")
    @WebMethod
    @ResponseWrapper(localName = "firmaXAdESRemotaResponse", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FirmaXAdESRemotaResponse")
    public it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.FileResult firmaXAdESRemota(
        @WebParam(name = "attachment", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.Attachment attachment,
        @WebParam(name = "xadesInput", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.XadesInput xadesInput,
        @WebParam(name = "identity", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.SignIdentity identity
    );

    /**
     * Annullamento di un documento su repository.
     */
    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "annullaDocumento", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.AnnullaDocumento")
    @WebMethod
    @ResponseWrapper(localName = "annullaDocumentoResponse", targetNamespace = "http://www.csi.it/gatefire/", className = "it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.AnnullaDocumentoResponse")
    public it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.Result annullaDocumento(
        @WebParam(name = "repositoryInput", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.RepositoryUndoInput repositoryInput,
        @WebParam(name = "callInfo", targetNamespace = "")
        it.csi.vigilsan.vigilsanmoduli.integration.gatefire.generated.it.csi.gatefire.CallInfo callInfo
    );
}
