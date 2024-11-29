/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import it.csi.aura.auraws.services.central.screeningepatitec.Request;
import it.csi.aura.auraws.services.central.screeningepatitec.ScreeningEpatiteC;
import it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl.SoggettoDao;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.Soggetto;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.SoggettoEstesoLista;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.interfacecsv.SoggettoEstesoListaForCsv;
import it.csi.vigilsan.vigilsancommon.api.persistence.repository.SoggettoRepository;
import it.csi.vigilsan.vigilsancommon.api.service.SoggettoService;
import it.csi.vigilsan.vigilsancommon.integration.aura.soggettoaura.dto.SoggettoAuraCustom;
import it.csi.vigilsan.vigilsancommon.integration.aura.soggettoaura.service.SoggettoAuraService;
import it.csi.vigilsan.vigilsancommon.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTException;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableWPersistenceApiServiceImpl;
import it.csi.vigilsan.vigilsanutil.generic.util.AuditOperazioneEnum;
import it.csi.vigilsan.vigilsanutil.generic.util.DateFormatEnum;
import it.csi.vigilsan.vigilsanutil.generic.util.DateUtils;
import it.csi.vigilsan.vigilsanutil.generic.util.paginazione.Paginazione;

@Service
public class SoggettoServiceImpl extends AuditableWPersistenceApiServiceImpl implements SoggettoService {

	private static final String CODICE_FISCALE_NON_TROVATO = "Codice fiscale non trovato";
	private static final String OK = "OK";
	@Autowired
	private SoggettoDao soggettoDao;

	@Autowired
	private SoggettoRepository soggettoRepository;

	@Autowired
	private SoggettoAuraService soggettoAuraService;

	@Override
	public Soggetto soggettoPost(Integer sId, String codiceFiscale, String shibIdentitaCodiceFiscale) {
		var methodName = "soggettoPost";
		Soggetto soggetto;
		try {
			soggetto = getSoggetto(codiceFiscale);
		} catch (RESTException e) {
			if (RESTErrorUtil.isSameError(ErrorCodeEnum.SOGGETTOAPI_SOGG_NON_TROVATO_CF, e)) {
				// PRENDO DA AURA ( CON MEF REGIONALI EXTRAREGIONE ) -> NOME COGNOME DATA DI
				// NASCITA E CF.

				Request request = new Request();
				request.setCodiceFiscale(codiceFiscale);
				ScreeningEpatiteC reqq = new ScreeningEpatiteC();
				reqq.setAssistito(request);

				SoggettoAuraCustom soggettoDaAura = soggettoAuraService.getAuraSoggettoCustom(sId, reqq);
				// se non trovo errore

				if (Objects.isNull(soggettoDaAura)) {
					var err = RESTErrorUtil
							.generateGenericRestException(ErrorCodeEnum.AURA_ERRORE_SERVIZIO_ESTERNO_GENERICO);
					logErrorCfDb(sId, codiceFiscale, methodName);
					logErrorWhitAudit(sId, err, methodName, "soggettoDaAura non valorizzato");
					throw err;
				} else if (Objects.isNull(soggettoDaAura.getEsito())) {
					var err = RESTErrorUtil
							.generateGenericRestException(ErrorCodeEnum.AURA_ERRORE_SERVIZIO_ESTERNO_GENERICO);
					logErrorCfDb(sId, codiceFiscale, methodName);
					logErrorWhitAudit(sId, err, methodName, soggettoDaAura.toString());
					throw err;

				} else if (soggettoDaAura.getEsito().equals(CODICE_FISCALE_NON_TROVATO)) {

					var err = RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.AURA_SOGGETTO_NON_TROVATO);
					logErrorCfDb(sId, codiceFiscale, methodName);
					logErrorWhitoutStakTraceWhitAudit(sId, err, methodName, "soggetto non trovato");
					throw err;
				} else if (soggettoDaAura.getEsito().equals(OK)) {
					soggetto = new Soggetto();
					soggetto.setCodiceFiscale(soggettoDaAura.getCodiceFiscale());
					soggetto.setNome(soggettoDaAura.getNome());
					soggetto.setCognome(soggettoDaAura.getCognome());
					soggetto.setUtenteCreazione(shibIdentitaCodiceFiscale);
					soggetto.setDataNascita(
							DateUtils.parsetDate(soggettoDaAura.getDataNascita(), DateFormatEnum.AURA_DATE_FORMAT));
					soggetto.setPresenteConfiguratore(Boolean.FALSE);
					// se trovo salvo su db
					insertSoggettoWhitPk(sId, soggetto);
				} else {
					var err = RESTErrorUtil
							.generateGenericRestException(ErrorCodeEnum.AURA_ERRORE_SERVIZIO_ESTERNO_GENERICO_GESTITO);
					logErrorCfDb(sId, codiceFiscale, methodName);
					logErrorWhitoutStakTraceWhitAudit(sId, err, methodName, soggettoDaAura.toString());
					throw err;
				}

			} else {

				throw e;
			}
		}
		return soggetto;
	}

	private void logErrorCfDb(Integer sId, String codiceFiscale, String methodName) {
		logErrorDb(sId, methodName, "cf ricercato: ", codiceFiscale);
	}

	@Override
	public Soggetto getSoggetto(Integer soggettoId) {
		try {
			return soggettoDao.get(soggettoId);
		} catch (EmptyResultDataAccessException e) {
			logError(null, "getSoggetto", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.SOGGETTOAPI_SOGG_NON_TROVATO);
		}
	}

	@Override
	public Soggetto getSoggetto(String codiceFiscale) {
		try {
			return soggettoRepository.getByCodiceFiscale(codiceFiscale);
		} catch (EmptyResultDataAccessException e) {
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.SOGGETTOAPI_SOGG_NON_TROVATO_CF);
		}
	}

	@Override
	public void insertSoggetto(Integer sessionId, String codiceFiscale, String nome, String cognome,
			Boolean presenteConfiguratore, String utenteOperazione) {
		try {
			var sogg = new Soggetto();
			sogg.setCodiceFiscale(codiceFiscale);
			sogg.setCognome(cognome);
			sogg.setNome(nome);
			sogg.setPresenteConfiguratore(presenteConfiguratore);
			sogg.setUtenteCreazione(utenteOperazione);
			insertSoggettoWhitPk(sessionId, sogg);
		} catch (Exception e) {
			logError(null, "insertSoggetto", e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public List<SoggettoEstesoLista> getDocumentazioneCompilataList(Integer sId, String ruoloStrutturaCod,
			Integer strutturaId, String filter, Integer ospiteStatoId, String dataIngressoDa, String dataIngressoA,
			String dataUscitaDa, String dataUscitaA, Paginazione paginazione) {
		var methodName = "getDocumentazioneCompilataList";
		try {
			Date dataPrimoIngressoDa = DateUtils.parsetDate(dataIngressoDa, DateFormatEnum.API_DATE_FORMAT);
			Date dataPrimoIngressoA = DateUtils.parsetDate(dataIngressoA, DateFormatEnum.API_DATE_FORMAT);
			Date dataUltimaUscitaDa = DateUtils.parsetDate(dataUscitaDa, DateFormatEnum.API_DATE_FORMAT);
			Date dataUltimaUscitaA = DateUtils.parsetDate(dataUscitaA, DateFormatEnum.API_DATE_FORMAT);
			return soggettoDao.getSoggettoListPaginataFromParameter(ruoloStrutturaCod, strutturaId, filter,
					ospiteStatoId, dataPrimoIngressoDa, dataPrimoIngressoA, dataUltimaUscitaDa, dataUltimaUscitaA,
					paginazione, SoggettoEstesoLista.class);
		} catch (Exception e) {
			logError(sId, methodName, e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public List<SoggettoEstesoListaForCsv> getDocumentazioneCompilataList(Integer sId, String ruoloStrutturaCod,
			Integer strutturaId, String filter, Integer ospiteStatoId, String dataIngressoDa, String dataIngressoA,
			String dataUscitaDa, String dataUscitaA) {
		var methodName = "getDocumentazioneCompilataList";
		try {
			Date dataPrimoIngressoDa = DateUtils.parsetDate(dataIngressoDa, DateFormatEnum.API_DATE_FORMAT);
			Date dataPrimoIngressoA = DateUtils.parsetDate(dataIngressoA, DateFormatEnum.API_DATE_FORMAT);
			Date dataUltimaUscitaDa = DateUtils.parsetDate(dataUscitaDa, DateFormatEnum.API_DATE_FORMAT);
			Date dataUltimaUscitaA = DateUtils.parsetDate(dataUscitaA, DateFormatEnum.API_DATE_FORMAT);
			return soggettoDao.getSoggettoListPaginataFromParameter(ruoloStrutturaCod, strutturaId, filter,
					ospiteStatoId, dataPrimoIngressoDa, dataPrimoIngressoA, dataUltimaUscitaDa, dataUltimaUscitaA,
					null, SoggettoEstesoListaForCsv.class);
		} catch (Exception e) {
			logError(sId, methodName, e.getMessage(), e);
			throw e;
		}
	}

	private void insertSoggettoWhitPk(Integer sId, Soggetto soggetto) {
		try {
			soggetto.setSoggettoId(soggettoDao.getSequence());
			soggettoDao.insert(soggetto);
			logAuditDb(sId, AuditOperazioneEnum.INSERT, "vigil_t_soggetto", soggetto.getSoggettoId().toString());
		} catch (Exception e) {
			logError(null, "insertSoggetto", e.getMessage(), e);
			throw e;
		}
	}
}
