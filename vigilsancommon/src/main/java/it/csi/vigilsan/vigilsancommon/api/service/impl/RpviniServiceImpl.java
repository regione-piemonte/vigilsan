/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.google.gson.Gson;

import it.csi.vigilsan.vigilsancommon.api.generated.dto.ModelParametro;
import it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl.InvioNotificaDao;
import it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl.NotificaDao;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.InvioNotifica;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.NotificaEstesa;
import it.csi.vigilsan.vigilsancommon.api.service.RpviniService;
import it.csi.vigilsan.vigilsancommon.api.service.vigilsan.vigilsanrilevazioni.VigilsanRilevazioniGetListApiEnum;
import it.csi.vigilsan.vigilsancommon.integration.notificatore.dto.Email;
import it.csi.vigilsan.vigilsancommon.integration.notificatore.dto.Mex;
import it.csi.vigilsan.vigilsancommon.integration.notificatore.dto.MyRequestObject;
import it.csi.vigilsan.vigilsancommon.integration.notificatore.dto.Payload;
import it.csi.vigilsan.vigilsancommon.integration.notificatore.dto.Push;
import it.csi.vigilsan.vigilsancommon.integration.notificatore.impl.NotificatoreApiClient;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.impl.ApiUtils;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.vigilsanrilevazioni.impl.VigilsanRilevazioniService;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableWPersistenceApiServiceImpl;
import it.csi.vigilsan.vigilsanutil.generic.util.GsonUtils;
import jakarta.ws.rs.core.HttpHeaders;

@Service
public class RpviniServiceImpl extends AuditableWPersistenceApiServiceImpl implements RpviniService {
	private static final int ERRORI_CONSECUTIVI_MAX = 5;
	private static final String STATUS_201_CREATED = HttpStatusCode.valueOf(201).toString();
	private static final HttpStatusCode STATUS_408_TIMEOUT = HttpStatusCode.valueOf(408);
	private static final Gson gson = GsonUtils.createGson();

	private static final String RUOLO_OAM = "OAM";
	private static final String TAGS = "vigilsan";
	private static final String APPLICAZIONE_CONFIGURATORE = "VIGILSAN";

	private static final int GRANDEZZA_MODULO_INVII = 5;

	@Autowired
	private EmailBatchService emailService;

	@Autowired
	private SoggettoServiceImpl soggettoService;

	@Autowired
	private EnteServiceImpl enteService;

	@Autowired
	private StrutturaServiceImpl strutturaService;

	@Autowired
	private NotificaDao notificaDao;

	@Autowired
	private InvioNotificaDao invioNotificaDao;

	@Autowired
	private NotificatoreApiClient notificatoreService;

	@Autowired
	private VigilsanRilevazioniService vigilsanRilevazioniService;

	@Override
	public void rpviniInviaNotifiche(Integer sId, String cfUtente, HttpHeaders httpHeaders) throws Exception {
		List<NotificaEstesa> notificheDaInviare = notificaDao.getNotificheDaInviare();
		int erroriConsecutivi = 0;
		for (NotificaEstesa notifica : notificheDaInviare) {

			try {
				if (notifica.getSoggettoId() != null) {
//					var soggetto = soggettoService.getSoggetto(notifica.getSoggettoId());
//
//					invioNotificaPerCodiceFiscale(cfUtente, notifica, soggetto.getCodiceFiscale());

					// TODO:
				} else if (notifica.getStrutturaId() != null) {
//					var struttura = strutturaService.getStrutturaByStrutturaId(sId, notifica.getStrutturaId());
//					invioNotificaPerCollocazione(cfUtente, notifica, struttura.getStrutturaCodConfiguratore());

					inviaEmail(cfUtente, sId, httpHeaders, notifica);

				} else if (notifica.getEnteId() != null) {
//					var ente = enteService.getEnteById(notifica.getEnteId());
//					invioNotificaPerCollocazione(cfUtente, notifica, ente.getEnteCodConfiguratore());

					// TODO:
				}
			} catch (WebClientResponseException e) {
				logError(sId, "rpvinvInviaNotifiche",
						"errore WebClientResponseException: " + e.getStatusCode().toString(), e);
				// 408 Request Timeout in questo caso interrompere il ciclo.
				if (e.getStatusCode().equals(STATUS_408_TIMEOUT)) {
					logError(sId, "rpvinvInviaNotifiche", "esco dal ciclo perche' timeout", e);
					logErrorDb(sId, "rpvinvInviaNotifiche", "esco dal ciclo perche' timeout", e.getMessage());
				}

				erroriConsecutivi = gestioneErroreCIclo(sId, erroriConsecutivi, e);

			} catch (Exception e) {
				logError(sId, "rpvinvInviaNotifiche", "errore Exception: ", e);

				erroriConsecutivi = gestioneErroreCIclo(sId, erroriConsecutivi, e);

			}
		}
	}

	private void inviaEmail(String cfUtente, Integer sId, HttpHeaders httpHeaders, NotificaEstesa notifica)
			throws IOException, MessagingException {
		var toSave = new InvioNotifica();
		toSave.setDataoraInvio(new Date());
		toSave.setNotificaId(notifica.getNotificaId());
		toSave.setRequestContent(notifica.getBodyTextLong());
		toSave.setUtenteCreazione(cfUtente);

		String mail;
		try {
			mail = getMailForStruttura(sId, httpHeaders, notifica);
		} catch (Exception e) {
			toSave.setErroreInvio("Errore in chiamata per mail: " +e.getMessage());
			toSave.setResponseResult("ERROR_GET_MAIL_FROM_RILEVAZIONI");
			toSave.setEsitoInvio(Boolean.FALSE);
			invioNotificaDao.insert(toSave);
			throw e;
		}

		toSave.setCfDestinatario(mail);
		if (Objects.isNull(mail)) {
			toSave.setErroreInvio("MAIL associata a struttura non trovata");
			toSave.setResponseResult("MAIL_NON_TROVATA");
			toSave.setEsitoInvio(Boolean.FALSE);
			invioNotificaDao.insert(toSave);
			return;
		}
		try {
			var risultatoInvio = emailService.sendMail(sId, notifica.getBodyTextLong(), mail, notifica.getTitle());
			toSave.setEsitoInvio(Boolean.TRUE);
			invioNotificaDao.insert(toSave);
		} catch (MessagingException e) {
			toSave.setErroreInvio(e.getMessage());
			toSave.setResponseResult("ERROR_INVIO_MAIL");
			toSave.setEsitoInvio(Boolean.FALSE);
			invioNotificaDao.insert(toSave);
			throw e;
		} catch (Exception e) {
			toSave.setErroreInvio(e.getMessage());
			toSave.setResponseResult("ERROR_GENERICO_INVIO_MAIL");
			toSave.setEsitoInvio(Boolean.FALSE);
			invioNotificaDao.insert(toSave);
			throw e;
		}

	}

	private String getMailForStruttura(Integer sId, HttpHeaders httpHeaders, NotificaEstesa notifica)
			throws IOException {
		var qp = new HashMap<String, Object>();
		qp.put("struttura_id", notifica.getStrutturaId());
		qp.put("parametro_cod", "EMAIL");
		List<ModelParametro> mails = vigilsanRilevazioniService.getGenericGet(
				VigilsanRilevazioniGetListApiEnum.GET_DOCUMENTAZIONE_PARAMETRO_LISTA,
				ApiUtils.convertJakartaToOkHttpHeaders(httpHeaders), qp, sId);

		String mail = null;
		for (ModelParametro m : mails) {
			if (m.getValore() != null && !m.getValore().isBlank()) {
				mail = m.getValore();
				break;
			}
		}
		return mail;
	}

	private int gestioneErroreCIclo(Integer sId, int erroriConsecutivi, Exception e) throws Exception {
		if (erroriConsecutivi == ERRORI_CONSECUTIVI_MAX) {
			// 5 errori IN CASO METTO LOG DI ERRORE SU DB CON "INTERRUZIONE BATCH RPVINV PER
			// TROPPI TENTATIVI FALLITI"
			logError(sId, "rpvinvInviaNotifiche", "esco dal ciclo perche' troppi errori consecutivi: ", e);
			logErrorDb(sId, "rpvinvInviaNotifiche",
					"esco dal ciclo perche' troppi errori consecutivi: " + String.valueOf(ERRORI_CONSECUTIVI_MAX),
					e.getMessage());
			throw e;
		} else {
			erroriConsecutivi++;
		}
		return erroriConsecutivi;
	}

	private void invioNotificaPerCodiceFiscale(String cfUtente, NotificaEstesa notifica, String cfRequest) {

		if (Objects.isNull(cfRequest)) {
			var toSave = new InvioNotifica();
			toSave.setCfDestinatario(cfRequest);
			toSave.setDataoraInvio(new Date());
			toSave.setNotificaId(notifica.getNotificaId());
			toSave.setRequestContent(null);
			toSave.setUtenteCreazione(cfUtente);
			toSave.setErroreInvio("codice fiscale sconosciuto per soggetto");
			toSave.setResponseContent(null);
			toSave.setResponseResult(null);
			toSave.setEsitoInvio(Boolean.FALSE);
			invioNotificaDao.insert(toSave);
			return;
		}
		var request = generateRequestSingleCf(notifica, cfRequest, UUID.randomUUID().toString());
		invioNotificaSingola(request, notifica.getNotificaId(), cfRequest, cfUtente);
	}

	private void invioNotificaPerCollocazione(String cfUtente, NotificaEstesa notifica, String collocazione) {
		if (Objects.isNull(collocazione)) {
			var toSave = new InvioNotifica();
			toSave.setCfDestinatario(null);
			toSave.setDataoraInvio(new Date());
			toSave.setNotificaId(notifica.getNotificaId());
			toSave.setRequestContent(null);
			toSave.setUtenteCreazione(cfUtente);
			toSave.setErroreInvio("collocazione sconosciuta per notifica");
			toSave.setResponseContent(null);
			toSave.setResponseResult(null);
			toSave.setEsitoInvio(Boolean.FALSE);
			invioNotificaDao.insert(toSave);
			return;
		}
		String uuid = UUID.randomUUID().toString();
		var request = generateRequestCollocazione(notifica, collocazione, uuid);
		invioNotifica(request, notifica.getNotificaId(), cfUtente);
		invioNotificaSingola(request, notifica.getNotificaId(), null, cfUtente);
	}

	private void invioNotificaPerListaDiCodiciFiscali(String cfUtente, NotificaEstesa notifica,
			ArrayList<String> cfList) {
		List<List<String>> moduli = suddividiInModuli(cfList, GRANDEZZA_MODULO_INVII);
		String uuid = UUID.randomUUID().toString();

		for (List<String> cfInvioCorrente : moduli) {
			var request = generateRequestMultipleCf(notifica, cfInvioCorrente, uuid);
			invioNotificheBulk(request, notifica.getNotificaId(),
					cfInvioCorrente.stream().collect(Collectors.joining(", ")), cfUtente);

		}
	}

	private void invioNotifica(MyRequestObject request, Integer notificaId, String cfUtente) {
		var toSave = new InvioNotifica();
		toSave.setDataoraInvio(new Date());
		toSave.setNotificaId(notificaId);
		toSave.setRequestContent(gson.toJson(request));
		toSave.setUtenteCreazione(cfUtente);
		String response;

		try {
			response = notificatoreService.sendPostRequestToJsonString(request);
		} catch (Exception e) {
			toSave.setErroreInvio(e.getMessage());
			toSave.setEsitoInvio(Boolean.FALSE);
			invioNotificaDao.insert(toSave);
			throw e;
		}

		toSave.setEsitoInvio(Boolean.TRUE);
		toSave.setResponseContent(gson.toJson(response));
		invioNotificaDao.insert(toSave);
	}

	private void invioNotificheBulk(List<MyRequestObject> request, Integer notificaId, String cfRequest,
			String cfUtente) {
		var toSave = new InvioNotifica();
		toSave.setCfDestinatario(cfRequest);
		toSave.setDataoraInvio(new Date());
		toSave.setNotificaId(notificaId);
		toSave.setRequestContent(gson.toJson(request));
		toSave.setUtenteCreazione(cfUtente);
		String response;

		try {
			response = notificatoreService.sendPostRequestToJsonString(request);
		} catch (WebClientResponseException e) {
			toSave.setErroreInvio(e.getMessage());
			toSave.setResponseContent(e.getResponseBodyAsString());
			toSave.setResponseResult(e.getStatusCode().toString());
			toSave.setEsitoInvio(Boolean.FALSE);
			invioNotificaDao.insert(toSave);
			throw e;
		} catch (Exception e) {
			toSave.setErroreInvio(e.getMessage());
			toSave.setEsitoInvio(Boolean.FALSE);
			invioNotificaDao.insert(toSave);
			throw e;
		}

		toSave.setEsitoInvio(Boolean.TRUE);
		toSave.setResponseContent(gson.toJson(response));
		toSave.setResponseResult(STATUS_201_CREATED);

		invioNotificaDao.insert(toSave);
	}

	private void invioNotificaSingola(MyRequestObject request, Integer notificaId, String cfRequest, String cfUtente) {
		var toSave = new InvioNotifica();
		toSave.setCfDestinatario(cfRequest);
		toSave.setDataoraInvio(new Date());
		toSave.setNotificaId(notificaId);
		toSave.setRequestContent(gson.toJson(request));
		toSave.setUtenteCreazione(cfUtente);
		String response;

		try {
			response = notificatoreService.sendPostRequestToJsonString(request);
		} catch (WebClientResponseException e) {
			toSave.setErroreInvio(e.getMessage());
			toSave.setResponseContent(e.getResponseBodyAsString());
			toSave.setResponseResult(e.getStatusCode().toString());
			toSave.setEsitoInvio(Boolean.FALSE);
			invioNotificaDao.insert(toSave);
			throw e;
		} catch (Exception e) {
			toSave.setErroreInvio(e.getMessage());
			toSave.setEsitoInvio(Boolean.FALSE);
			invioNotificaDao.insert(toSave);
			throw e;
		}

		toSave.setEsitoInvio(Boolean.TRUE);
		toSave.setResponseContent(gson.toJson(response));
		toSave.setResponseResult(STATUS_201_CREATED);

		invioNotificaDao.insert(toSave);
	}

	private List<MyRequestObject> generateRequestMultipleCf(NotificaEstesa notifica, List<String> cfInvioCorrente,
			String bulkUuid) {
		var request = new ArrayList<MyRequestObject>();
		for (String cf : cfInvioCorrente) {
			request.add(generateRequest(notifica, cf, UUID.randomUUID().toString(), bulkUuid));
		}
		return request;
	}

	private MyRequestObject generateRequestCollocazione(NotificaEstesa notifica, String collocazione, String uuid) {
		var res = generateRequest(notifica, uuid, uuid);
		res.getPayload().setRuolo(RUOLO_OAM);
		res.getPayload().setCollocazione(collocazione);
		return res;
	}

	private MyRequestObject generateRequestSingleCf(NotificaEstesa notifica, String cf, String uuid) {
		return this.generateRequest(notifica, cf, uuid, uuid);
	}

	private MyRequestObject generateRequest(NotificaEstesa notifica, String cf, String uuid, String bulkUuid) {
		var res = generateRequest(notifica, uuid, bulkUuid);
		res.getPayload().setUser_id(cf);
		res.getPayload().setApplicazione(APPLICAZIONE_CONFIGURATORE);
		return res;
	}

	private MyRequestObject generateRequest(NotificaEstesa notifica, String uuid, String bulkUuid) {
		var request = new MyRequestObject();
		request.setUuid(uuid);
		request.setTo_be_retried(Boolean.FALSE);
		var payload = new Payload();
		request.setPayload(payload);

		payload.setId(request.getUuid());
		payload.setBulk_id(bulkUuid);
		payload.setTag(TAGS);

		var email = new Email();
		payload.setEmail(email);
		email.setSubject(notifica.getTitle());
		email.setBody(notifica.getBodyTextLong());
		email.setTemplate_id(notifica.getBodyHtml());

		var push = new Push();
		payload.setPush(push);
		push.setTitle(notifica.getTitle());
		push.setBody(notifica.getBodyTextShort());

		var mex = new Mex();
		payload.setMex(mex);
		mex.setTitle(notifica.getTitle());
		mex.setBody(notifica.getBodyTextShort());
		return request;
	}

	public static <T> List<List<T>> suddividiInModuli(List<T> lista, int modulo) {
		return IntStream.range(0, (lista.size() + modulo - 1) / modulo)
				.mapToObj(i -> lista.subList(i * modulo, Math.min((i + 1) * modulo, lista.size()))).toList();
	}

}
