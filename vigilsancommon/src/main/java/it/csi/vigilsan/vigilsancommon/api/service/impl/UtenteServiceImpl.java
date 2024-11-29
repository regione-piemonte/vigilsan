/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.micrometer.common.util.StringUtils;
import it.csi.vigilsan.vigilsancommon.api.generated.dto.ModelProfiloUtente;
import it.csi.vigilsan.vigilsancommon.api.generated.dto.ModelProfiloUtenteProfili;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.ApplicativoOperazioneRidottoCustom;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.Struttura;
import it.csi.vigilsan.vigilsancommon.api.service.EnteService;
import it.csi.vigilsan.vigilsancommon.api.service.SoggettoService;
import it.csi.vigilsan.vigilsancommon.api.service.StrutturaService;
import it.csi.vigilsan.vigilsancommon.api.service.UtenteInterface;
import it.csi.vigilsan.vigilsancommon.api.service.integration.configuratore.ConfiguratoreService;
import it.csi.vigilsan.vigilsancommon.api.service.integration.configuratore.dto.Funzionalita;
import it.csi.vigilsan.vigilsancommon.api.service.integration.configuratore.dto.Richiedente;
import it.csi.vigilsan.vigilsancommon.api.service.integration.configuratore.dto.TokenInformazione;
import it.csi.vigilsan.vigilsancommon.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTException;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableWPersistenceApiServiceImpl;
import it.csi.vigilsan.vigilsanutil.generic.util.AuditOperazioneEnum;

@Service
public class UtenteServiceImpl extends AuditableWPersistenceApiServiceImpl implements UtenteInterface {

	private static final String CODICE_COLLOCAZIONE_SEPARATOR = "@";
	private static final Boolean PRESENTE_CONFIGURATORE = Boolean.TRUE;
	private static final Boolean NON_PRESENTE_CONFIGURATORE = Boolean.FALSE;

	@Autowired
	private ConfiguratoreService configuratoreService;

	@Autowired
	private SoggettoService soggettoService;

	@Autowired
	private StrutturaService strutturaService;

	@Autowired
	private EnteService enteService;

	@Autowired
	private RuoloServiceImpl ruoloService;

	@Autowired
	private ProfiloServiceImpl profiloService;

	@Autowired
	private ApplicativoServiceImpl applicativoService;

	@Autowired
	private SqlStatementCsiLogSessionServiceImpl sqlStatementCsiLogSessionServiceImpl;

	@Override
	public ModelProfiloUtente getCurrentUserFromConfiguratore(String shibIdentitaCodiceFiscale, String xRequestId,
			String xForwardedFor, String xCodiceServizio, String token) throws IOException {

		var methodName = "getCurrentUserFromConfiguratore";
		Integer sessionId = sqlStatementCsiLogSessionServiceImpl.insertLogSession(xForwardedFor,
				shibIdentitaCodiceFiscale, PRESENTE_CONFIGURATORE);
		var ret = new ModelProfiloUtente();
		TokenInformazione res = null;
		try {
			ret.setApplicativoId(applicativoService.getByApplicativoCod(xCodiceServizio).getApplicativoId());

			res = configuratoreService.sendGetTokenInformation(shibIdentitaCodiceFiscale, xRequestId, xForwardedFor,
					token);
			var codiceCollocazione = res.getRichiedente().getCollocazione().getCodiceCollocazione();
			if (Objects.isNull(codiceCollocazione)) {
				throw RESTErrorUtil
						.generateGenericRestException(ErrorCodeEnum.COLLOCAZIONE_NON_TROVATA_CODICE_NON_VALORIZZATO);
			}

			ret.setSoggettoId(getIdSoggetto(sessionId, res.getRichiedente(), shibIdentitaCodiceFiscale));
			ret.setRuoloId(getRuoloId(res.getRichiedente(), shibIdentitaCodiceFiscale));
			ret.setProfili(getProfiliByTokenInfoFunzionalita(res.getFunzionalita()));

			if (Objects.nonNull(ret.getProfili()) && !ret.getProfili().isEmpty()) {
				ret.setProfiloId(ret.getProfili().get(0).getProfiloId());
			}

			ret.setEnteId(getEnteId(codiceCollocazione));
			Integer strutturaTipoId = null;
			if (Objects.isNull(ret.getEnteId())) {
				var struttura = getStrutturaId(sessionId, codiceCollocazione, shibIdentitaCodiceFiscale);
				if (Objects.isNull(struttura)) {
					throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.COLLOCAZIONE_NON_TROVATA);
				}
				ret.setStrutturaId(struttura.getStrutturaId());
				strutturaTipoId = struttura.getStrutturaTipoId();
			}
			ret.setAuth(applicativoService.getApplicativoOperazioneAuth(ret.getApplicativoId(), ret.getProfiloId(),strutturaTipoId));
			ret.setSessioneId(sessionId);
			sqlStatementCsiLogSessionServiceImpl.updateLogSession(sessionId, ret.getSoggettoId(), ret.getRuoloId(),
					ret.getApplicativoId(), ret.getProfiloId(), ret.getStrutturaId(), ret.getEnteId());
			return ret;
		} catch (RESTException e) {
			logErrorWhitAudit(sessionId, e, methodName,
					"Errore RESTException estrazione dati da risposta configuratore");
			logErrorDb(sessionId, methodName, "informazioni token",
					Objects.nonNull(res) ? res.toString() : "token is null");
			throw e;
		} catch (Exception e) {
			logErrorWhitAudit(sessionId, e, methodName, "Errore generico estrazione dati da risposta configuratore");
			logErrorDb(sessionId, methodName, "informazioni token",
					Objects.nonNull(res) ? res.toString() : "token is null");
			throw e;
		}
	}

	@Override
	public ModelProfiloUtente getCurrentUserFromShibIdentitaForBatch(String shibIdentitaCodiceFiscale,
			String xForwardedFor) {
		var methodName = "getCurrentUserFromShibIdentitaForBatch";
		Integer sessionId = sqlStatementCsiLogSessionServiceImpl.insertLogSession(
				Objects.nonNull(xForwardedFor) ? xForwardedFor : "0.0.0.0", shibIdentitaCodiceFiscale,
				NON_PRESENTE_CONFIGURATORE);
		var ret = new ModelProfiloUtente();

		try {
			ret.setSoggettoId(getIdSoggettoIfExist(shibIdentitaCodiceFiscale));
			ret.setSessioneId(sessionId);
			sqlStatementCsiLogSessionServiceImpl.updateLogSession(sessionId, ret.getSoggettoId(), ret.getRuoloId(),
					ret.getApplicativoId(), ret.getProfiloId(), ret.getStrutturaId(), ret.getEnteId());
			return ret;
		} catch (RESTException e) {
			logErrorWhitAudit(sessionId, e, methodName,
					"Errore RESTException estrazione dati da risposta configuratore");
			throw e;
		} catch (Exception e) {
			logErrorWhitAudit(sessionId, e, methodName, "Errore generico estrazione dati da risposta configuratore");
			throw e;
		}
	}

	private List<ModelProfiloUtenteProfili> getProfiliByTokenInfoFunzionalita(List<Funzionalita> list) {
		Map<String, ModelProfiloUtenteProfili> map = new HashMap<>();
		for (Funzionalita fun : list) {
			if (StringUtils.isNotBlank(fun.getCodiceFunzionalitaPadre())) {
				if (!map.containsKey(fun.getCodiceFunzionalitaPadre())) {
					var profiloUtente = new ModelProfiloUtenteProfili();
					var l = new ArrayList<String>();
					l.add(fun.getCodice());
					profiloUtente.setFunzione(l);
					profiloUtente.setProfiloId(
							profiloService.getProfiloByProfiloCod(fun.getCodiceFunzionalitaPadre()).getProfiloId());
					map.put(fun.getCodiceFunzionalitaPadre(), profiloUtente);
				} else {
					map.get(fun.getCodiceFunzionalitaPadre()).getFunzione().add(fun.getCodice());
				}
			} else if (!map.containsKey(fun.getCodice())) {
				var profiloUtente = new ModelProfiloUtenteProfili();
				profiloUtente.setProfiloId(
						profiloService.getProfiloByProfiloCod(fun.getCodice()).getProfiloId());
				map.put(fun.getCodice(), profiloUtente);
			}

		}
		return new ArrayList<>(map.values());
	}

	private Integer getEnteId(String codConfiguratore) {
		try {
			var ente = enteService.getEnteByEnteCodConfiguratore(codConfiguratore);
			return ente.getEnteId();
		} catch (RESTException e) {
			if (ErrorCodeEnum.ENTE_NON_TROVATO.getCode().equals(e.getCode())) {
				return null;
			}
			throw e;
		}

	}

	private Struttura getStrutturaId(Integer sId, String codiceCollocazione, String shibIdentitaCodiceFiscale) {
		if (Boolean.FALSE.equals(codiceCollocazione.contains(CODICE_COLLOCAZIONE_SEPARATOR))) {
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.COLLOCAZIONE_NON_TROVATA_ENTE_NON_TROVATO);
		}
		String codArpe = codiceCollocazione.substring(0, codiceCollocazione.indexOf(CODICE_COLLOCAZIONE_SEPARATOR));

		try {
			var struttura = strutturaService.getStrutturaByCodArpeValida(sId, codArpe);

			if (Objects.isNull(struttura.getStrutturaCodConfiguratore())
					|| !codiceCollocazione.equals(struttura.getStrutturaCodConfiguratore())) {
				struttura.setStrutturaCodConfiguratore(codiceCollocazione);
				struttura.setUtenteModifica(shibIdentitaCodiceFiscale);
				strutturaService.updateStrutturaByStrutturaId(sId, struttura);
			}
			return struttura;
		} catch (RESTException e) {
			if (RESTErrorUtil.isSameError(ErrorCodeEnum.STRUTTURA_NON_TROVATA, e)) {
				return null;
			}
			throw e;
		}
	}

	private Integer getRuoloId(Richiedente richiedente, String shibIdentitaCodiceFiscale) {
		String ruoloCod = richiedente.getRuolo();

		try {
			return ruoloService.getRuoloByRuoloCod(ruoloCod).getRuoloId();
		} catch (RESTException e) {
			if (ErrorCodeEnum.RUOLO_NON_TROVATO.getCode().equals(e.getCode())) {
				ruoloService.insertRuolo(ruoloCod, ruoloCod, shibIdentitaCodiceFiscale);

				return ruoloService.getRuoloByRuoloCod(ruoloCod).getRuoloId();
			}
			throw e;
		}
	}

	private Integer getIdSoggetto(Integer sessionId, Richiedente richiedente, String shibIdentitaCodiceFiscale) {
		String codiceFiscale = richiedente.getCodiceFiscale();

		try {
			return getIdSoggettoFromCodiceFiscale(codiceFiscale);
		} catch (RESTException e) {
			if (ErrorCodeEnum.SOGGETTOAPI_SOGG_NON_TROVATO_CF.getCode().equals(e.getCode())) {
				soggettoService.insertSoggetto(sessionId, codiceFiscale, richiedente.getNome(),
						richiedente.getCognome(), PRESENTE_CONFIGURATORE, shibIdentitaCodiceFiscale);

				return getIdSoggettoFromCodiceFiscale(codiceFiscale);
			}
			throw e;
		}
	}

	private Integer getIdSoggettoIfExist(String codiceFiscale) {

		try {
			return getIdSoggettoFromCodiceFiscale(codiceFiscale);
		} catch (RESTException e) {
			if (ErrorCodeEnum.SOGGETTOAPI_SOGG_NON_TROVATO_CF.getCode().equals(e.getCode())) {
				return null;
			}
			throw e;
		}
	}

	private Integer getIdSoggettoFromCodiceFiscale(String codiceFiscale) {
		var soggetto = soggettoService.getSoggetto(codiceFiscale);
		return soggetto.getSoggettoId();
	}

	@Override
	public void logout(String shibIdentitaCodiceFiscale, Integer sId) {
		var session = sqlStatementCsiLogSessionServiceImpl.getById(sId);
		RESTErrorUtil.checkCondition(Objects.isNull(session.getSessionLogout()), ErrorCodeEnum.TOKEN_OBBLIGATORIO);
		sqlStatementCsiLogSessionServiceImpl.logout(sId);
	}

	@Override
	public List<ApplicativoOperazioneRidottoCustom> getPermessiByProfiloIdAndApplicativoId(Integer profiloId,
			Integer applicativoId, Integer strutturaId, Integer sId) {
		
		return applicativoService.getApplicativoOperazioneAuthLista(applicativoId, profiloId,strutturaId);
	}

	@Override
	public ModelProfiloUtente modificaSessione(ModelProfiloUtente body, String xForwardedFor, Integer sId) {
		var oldSession = sqlStatementCsiLogSessionServiceImpl.getById(sId);
		Integer sessionId = sqlStatementCsiLogSessionServiceImpl.insertSession(xForwardedFor, oldSession.getCfUtente(),
				PRESENTE_CONFIGURATORE, body.getSoggettoId(), body.getRuoloId(), body.getApplicativoId(),
				body.getProfiloId(), body.getStrutturaId(), body.getEnteId());
		logout(oldSession.getCfUtente(), sId);
		logAuditDb(sId, AuditOperazioneEnum.UPDATE, "csi_log_session", "sessione cambiata in: " + sessionId);
		logAuditDb(sessionId, AuditOperazioneEnum.UPDATE, "csi_log_session", "sessione precedente: " + sId);

		var res = new ModelProfiloUtente();
		res.setApplicativoId(body.getApplicativoId());
		res.setSoggettoId(body.getSoggettoId());
		res.setRuoloId(body.getRuoloId());
		res.setProfili(body.getProfili());
		res.setProfiloId(body.getProfiloId());
		res.setEnteId(body.getEnteId());
		res.setStrutturaId(body.getStrutturaId());
		Integer strutturaTipoId = null;
		if(Objects.nonNull(body.getStrutturaId())) {
			var struttura  = strutturaService.getStrutturaByStrutturaId(sId, body.getStrutturaId());
			strutturaTipoId = struttura.getStrutturaTipoId();
		}
		res.setAuth(applicativoService.getApplicativoOperazioneAuth(res.getApplicativoId(), res.getProfiloId(),strutturaTipoId));
		res.setSessioneId(sessionId);
		return res;
	}


}
