/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanfrontendsrv.api.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelDocumentazioneEsteso;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelNotifica;
import it.csi.vigilsan.vigilsanfrontendsrv.api.generated.dto.ModelVerificaDocumentazione;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.VigilsanCommonPostApiEnum;
import it.csi.vigilsan.vigilsanfrontendsrv.api.service.vigilsan.vigilsancommon.dto.ModelProfiloUtente;
import it.csi.vigilsan.vigilsanfrontendsrv.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanfrontendsrv.util.OkhttpCallUtils;
import it.csi.vigilsan.vigilsanutil.bff.service.vigilsan.vigilsancommon.impl.VigilsanCommonService;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import jakarta.ws.rs.core.HttpHeaders;

@Service
public class NotificaServiceImpl {

	@Autowired
	private VigilsanCommonService vigilsanCommonService;
	@Autowired
	private RilevazioniServiceImpl rilevazioniServiceImpl;

	@Autowired
	private RilevazioniServiceImpl rilevazioneService;

	@Autowired
	private UtenteServiceImpl utenteService;


	public ModelNotifica postNotificaGestioneDocumentale(ModelNotifica body, ModelProfiloUtente profiloUtente,
			HttpHeaders httpHeaders) throws IOException {

		body.setBodyTextLong("Accedi alla sezione gestione documentale per prendere visione degli ultimi aggiornamenti");
		var headers = OkhttpCallUtils.convertJakartaToOkHttpHeaders(httpHeaders);
		var moduli = rilevazioneService.getDocumentazioneCompilataLista(body.getStrutturaId(),
				profiloUtente.getSessioneId(), null, httpHeaders);
		var moduliList = moduli.getGenericObject();
		var struttura = utenteService.getStruttura(body.getStrutturaId(), profiloUtente.getSessioneId(), headers);
		body.setTitle("Aggiornamento gestione documentale - " + struttura.getStrutturaDesc());
		Boolean esitoVerifica = Boolean.FALSE;
		List<ModelVerificaDocumentazione> verificaDocumentazioneDaAggiornare = new ArrayList<>();
		for (ModelDocumentazioneEsteso modulo : moduliList) {
			if (modulo.getVerificaDocumentazione() != null
					&& modulo.getVerificaDocumentazione().getVerificaDocumentazioneId() != null
					&& Boolean.FALSE.equals(modulo.getVerificaDocumentazione().isEsitoVerifica())) {
				esitoVerifica = Boolean.TRUE;
				if (Objects.isNull(modulo.getVerificaDocumentazione().getNotificaId())) {
					verificaDocumentazioneDaAggiornare.add(modulo.getVerificaDocumentazione());
				}
			}
		}

		// Nessuna non conformità da notificare.
		RESTErrorUtil.checkCondition(esitoVerifica, ErrorCodeEnum.NESSUNA_NON_CONFORMITA);
		// Non conformita gia notificata.
		RESTErrorUtil.checkCondition(!verificaDocumentazioneDaAggiornare.isEmpty(),
				ErrorCodeEnum.NON_CONFORMITA_NOTIFICATA);

		var qp = new HashMap<String, Object>();
		ModelNotifica res = vigilsanCommonService.getGenericPost(VigilsanCommonPostApiEnum.POST_NOTIFICA, body, headers,
				qp, profiloUtente.getSessioneId());

		for (ModelVerificaDocumentazione ver : verificaDocumentazioneDaAggiornare) {
			ver.setNotificaId(res.getNotificaId());
			rilevazioniServiceImpl.postDocumentazioneVerifica(ver, profiloUtente, httpHeaders);
		}
		return res;

	}

}
