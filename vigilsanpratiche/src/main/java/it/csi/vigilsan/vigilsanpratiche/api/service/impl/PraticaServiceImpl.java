/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanpratiche.api.service.impl;

import java.awt.Color;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelAppuntamentoEsteso;
import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelAppuntamentoStato;
import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelAppuntamentoTipo;
import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelAzione;
import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelAzioneEstesa;
import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelChiaveValore;
import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelChiaveValoreList;
import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelPraticaClreqPost;
import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelPraticaDettaglio;
import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelPraticaDettaglioClreq;
import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelPraticaDettaglioEstesaPratica;
import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelPraticaDettaglioPost;
import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelPraticaPost;
import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelPraticaRequisiti;
import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelPraticaScadenza;
import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelPrescrizioneEsteso;
import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelPrescrizioneTipo;
import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelStrutturaTipoRidotto;
import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelZip;
import it.csi.vigilsan.vigilsanpratiche.api.generated.dto.ModelZipLista;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.dao.impl.AppuntamentoConfigDao;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.dao.impl.AppuntamentoDao;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.dao.impl.AppuntamentoSoggettoDao;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.dao.impl.AppuntamentoWorkflowDao;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.dao.impl.AzioneConfigDao;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.dao.impl.AzioneDao;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.dao.impl.ChecklistReqDao;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.dao.impl.PraticaClreqDao;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.dao.impl.PraticaDao;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.dao.impl.PraticaDettaglioClreqDao;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.dao.impl.PraticaDettaglioDao;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.dao.impl.PraticaScadenzaAnnullataDao;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.dao.impl.PraticaScadenzaGenerataDao;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.dao.impl.PraticaStatoDao;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.dao.impl.PraticaTipoDao;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.dao.impl.PraticaWorkflowDao;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.dao.impl.PrescrizioneConfigDao;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.dao.impl.PrescrizioneDao;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.dao.impl.PrescrizioneWorkflowDao;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.Appuntamento;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.AppuntamentoConfig;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.AppuntamentoEstesoPerDiario;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.AppuntamentoForPost;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.AppuntamentoSoggettoEstesoWsoggetto;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.AppuntamentoStato;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.AppuntamentoWorkflow;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.Azione;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.AzioneConfig;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.AzioneEstesa;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.AzioniEstese;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.ChecklistReqEsteso;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.ClreqEsito;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.Pratica;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaClreq;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaDettaglio;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaDettaglioClreq;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaDettaglioClreqEsteso;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaDettaglioEstesaPraticaPerDiario;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaDettaglioForPost;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaDettaglioPost;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaEstesa;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaForPost;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaRidottaEstesa;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaScadennzaAnnullata;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaScadenzaGenerata;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaStato;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaTipo;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PraticaWorkflow;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PrescrizioneConfig;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PrescrizioneForPost;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PrescrizioneStato;
import it.csi.vigilsan.vigilsanpratiche.api.persistence.model.PrescrizioneWorkflow;
import it.csi.vigilsan.vigilsanpratiche.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableWPersistenceApiServiceImpl;
import it.csi.vigilsan.vigilsanutil.generic.util.AuditOperazioneEnum;
import it.csi.vigilsan.vigilsanutil.generic.util.DateFormatEnum;
import it.csi.vigilsan.vigilsanutil.generic.util.DateUtils;
import it.csi.vigilsan.vigilsanutil.generic.util.paginazione.Paginazione;
import it.csi.vigilsan.vigilsanutil.generic.util.pdf.GeneratoreFileBuilderPdf;
import it.csi.vigilsan.vigilsanutil.generic.util.pdf.dto.BloccoDiTesto;
import it.csi.vigilsan.vigilsanutil.generic.util.pdf.dto.FileCustom;
import it.csi.vigilsan.vigilsanutil.generic.util.pdf.dto.Tabella;
import it.csi.vigilsan.vigilsanutil.generic.util.pdf.dto.Testo;
import it.csi.vigilsan.vigilsanutil.generic.util.pdf.mapper.PdfMapperBloccoDiTesto;
import it.csi.vigilsan.vigilsanutil.generic.util.pdf.mapper.PdfMapperBloccoDiTestoLista;
import it.csi.vigilsan.vigilsanutil.generic.util.pdf.mapper.PdfMapperTabella;

@Service
public class PraticaServiceImpl extends AuditableWPersistenceApiServiceImpl {

	@Autowired
	PraticaStatoDao praticaStatoDao;
	@Autowired
	PraticaTipoDao praticaTipoDao;
	@Autowired
	PraticaDao praticaDao;
	@Autowired
	AzioneDao azioneDao;
	@Autowired
	PraticaDettaglioDao praticaDettaglioDao;
	@Autowired
	PrescrizioneDao presctizioneDao;
	@Autowired
	AppuntamentoDao appuntamentoDao;
	@Autowired
	PrescrizoneServiceImpl prescrizioneService;
	@Autowired
	AppuntamentoServiceImpl appuntamentoService;
	@Autowired
	AzioneConfigDao azioneConfigDao;
	@Autowired
	PraticaWorkflowDao praticaWorkflowDao;
	@Autowired
	AppuntamentoConfigDao appuntamentoConfigDao;
	@Autowired
	AppuntamentoWorkflowDao appuntamentoWorkflowDao;
	@Autowired
	PrescrizioneConfigDao prescrizioneConfigDao;
	@Autowired
	PrescrizioneWorkflowDao prescrizioneWorkflowDao;
	@Autowired
	PraticaScadenzaGenerataDao praticaScadenzaGenerataDao;
	@Autowired
	PraticaScadenzaAnnullataDao praticaScadenzaAnnullataDao;
	@Autowired
	PrescrizioneDao prescrizioneDao;
	@Autowired
	ChecklistReqDao checkListReqDao;
	@Autowired
	PraticaClreqDao praticaClreqDao;
	@Autowired
	PraticaDettaglioClreqDao praticaDettaglioClreqDao;
	@Autowired
	AppuntamentoSoggettoDao appuntamentoSoggettoDao;

	public List<PraticaStato> getPraticaStatoDecodifica(Integer sId) {
		return praticaStatoDao.getAll();
	}

	public List<PraticaTipo> getPraticaTipoDecodifica(Integer sId) {
		return praticaTipoDao.getAll();
	}

	public List<PraticaTipo> getPraticaTipoNuovapratica(Integer sId, Integer enteTipoId) {
		return praticaTipoDao.getPraticaTipoByEnteTipoId(enteTipoId);
	}

	public List<ModelStrutturaTipoRidotto> getStrutturaRidottaByEnteTipoId(Integer sId, Integer enteTipoId) {
		return praticaTipoDao.getStrutturaRidottaByEnteTipoId(enteTipoId);
	}

	public List<PraticaRidottaEstesa> getPraticaInseribili(Integer enteId, Integer profiloId, Integer praticaTipoId,
			Integer strutturaTipoId, String filterStruttura, String dataChiusuraMaxStr, Paginazione paginazione,
			Integer sId) {

		var dataChiusuraMax = DateUtils.parsetDate(dataChiusuraMaxStr, DateFormatEnum.API_DATE_FORMAT);
		var res = praticaDao.selectPraticheInseribili(enteId, profiloId, praticaTipoId, strutturaTipoId,
				filterStruttura, dataChiusuraMax, paginazione);
		for (PraticaRidottaEstesa r : res) {

			r.setPraticaTipo(getPraticaTipoById(r.getPraticaTipoId(), sId));
			r.setAzioneIniziale(getAzioneInizialeById(r.getAzioneIdIniziale(), r.getPraticaTipoId(), sId));
		}
		return res;
	}

	private ModelAzioneEstesa getAzioneInizialeById(Integer id, Integer praticaTipoId, Integer sId) {
		try {
			return azioneDao.getAzioneIniziale(id, praticaTipoId);
		} catch (EmptyResultDataAccessException e) {
			logError(sId, "getAzioneInizialeById", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.AZIONE_INIZIALE_NON_TROVATA);
		}
	}

	private ModelAzione getAzioneById(Integer id, Integer sId) {
		try {
			return azioneDao.get(id);
		} catch (EmptyResultDataAccessException e) {
			logError(sId, "getAzioneById", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.AZIONE_NON_TROVATA);
		}
	}

	private PraticaTipo getPraticaTipoById(Integer id, Integer sId) {
		try {
			return praticaTipoDao.get(id);
		} catch (EmptyResultDataAccessException e) {
			logError(sId, "getPraticaTipoById", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.PRATICA_TIPO_NON_TROVATA);
		}
	}

	@Transactional
	public ModelPraticaPost praticaPost(ModelPraticaPost body, Integer enteId, Integer profiloId,
			String shibIdentitaCodiceFiscale, Integer sId) {
		RESTErrorUtil.checkNotNull(body.getPraticaTipoId(), ErrorCodeEnum.PRATICA_TIPO_ID_NON_TROVATA);
		RESTErrorUtil.checkNotNull(body.getPraticaDettaglio(), ErrorCodeEnum.PRATICA_DETTAGLIO_NON_TROVATA);
		RESTErrorUtil.checkNotNull(body.getPraticaDettaglio().getDataoraAzione(),
				ErrorCodeEnum.DATA_ORA_AZIONE_NON_TROVATA);
		RESTErrorUtil.checkCondition(
				body.getPraticaDettaglio().getDataoraAzione().before(newDateOggiMezzanotteMenoUnSecondo()),
				ErrorCodeEnum.DATA_ORA_AZIONE_NON_TROVATA);
		body.getPraticaDettaglio().setProfiloId(profiloId);
		RESTErrorUtil.checkCondition(!Scadenza.AUTOMATICA.getTipo().equals(body.getPraticaDettaglio().getFlgScadenza()),
				ErrorCodeEnum.UTENTE_NON_ABILITATO_PER_SCADENZE_AUTOMATICHE);

		UpdateStatus operazione = funzioneSelezioneOperazionePostPratica(body);
		if (UpdateStatus.UPDATE.equals(operazione)) {
			RESTErrorUtil.checkNotNull(body.getPraticaDettaglio().getPraticaDetId(),
					ErrorCodeEnum.PRATICA_DET_ID_OBBLIGATORIO);
			var praticaDet = praticaDettaglioDao.get(body.getPraticaDettaglio().getPraticaDetId());
			body.getPraticaDettaglio().setAzioneId(praticaDet.getAzioneId());
		} else {
			RESTErrorUtil.checkNotNull(body.getPraticaDettaglio().getAzioneId(), ErrorCodeEnum.AZIONE_ID_NON_TROVATA);
		}

		// verifriche in caso di update o delete
		if (UpdateStatus.UPDATE.equals(operazione) || UpdateStatus.DELETE.equals(operazione)) {
			controlliUpdateODeleteInPostPratica(body);
		}

		var scadenzeGenerate = praticaScadenzaGenerataDao.getByPraticaTipoId(body.getPraticaTipoId());

		// verifiche in caso di update e insert
		if (UpdateStatus.UPDATE.equals(operazione) || UpdateStatus.INSERT.equals(operazione)) {
			controlliInsertOUpdatePostPratica(body, scadenzeGenerate);
		}

		List<PrescrizioneForPost> prescrizioni = null;
		List<AppuntamentoForPost> appuntamenti = null;
		// prendo o genero la pratica associata
		PraticaForPost pratica = getPratica(body, enteId, shibIdentitaCodiceFiscale);

		if (Objects.isNull(pratica.getPraticaId())) {
			prescrizioni = new ArrayList<>();
			appuntamenti = new ArrayList<>();
		} else {
			appuntamenti = appuntamentoDao.getByPraticaIdForPost(body.getPraticaId());
			prescrizioni = presctizioneDao.getByPraticaIdForPost(body.getPraticaId());
		}

		var scadenzeAutomatiche = new ArrayList<PraticaDettaglioForPost>();
		var attivita = praticaDettaglioDao.selectListaPraticheForPost(body, shibIdentitaCodiceFiscale, operazione);

		funzioneAggiornamentoOggettiPratica(pratica, appuntamenti, prescrizioni, scadenzeGenerate, scadenzeAutomatiche,
				attivita, body.getPraticaDettaglio(), profiloId, shibIdentitaCodiceFiscale, sId);

		var scadenzeAutomaticheEsistenti = praticaDettaglioDao.getScadenzeEsistenti(pratica.getPraticaId());

		for (int i = 0; i < scadenzeAutomaticheEsistenti.size(); i++) {
			if (i < scadenzeAutomatiche.size()) {
				var scadenza = scadenzeAutomatiche.get(i);
				scadenza.setPraticaDetId(scadenzeAutomaticheEsistenti.get(i).getPraticaDetId());
				scadenza.setUpdateStatus(UpdateStatus.UPDATE.getTipo());
			} else {
				scadenzeAutomatiche.add(scadenzeAutomaticheEsistenti.get(i));
			}
		}

		funzioneOperazioniDatabasePerPraticaPost(operazione, pratica, prescrizioni, appuntamenti, attivita,
				scadenzeAutomatiche, shibIdentitaCodiceFiscale);

		body.setPraticaId(pratica.getPraticaId());
		return body;
	}

	private void funzioneOperazioniDatabasePerPraticaPost(UpdateStatus operazione, PraticaForPost pratica,
			List<PrescrizioneForPost> prescrizioni, List<AppuntamentoForPost> appuntamenti,
			List<PraticaDettaglioForPost> praticheDettaglio, ArrayList<PraticaDettaglioForPost> scadenzeAutomatiche,
			String shibIdentitaCodiceFiscale) {

		Integer nuovoAppuntamentoId = null;
		Integer nuovaPrescrizioneId = null;
		if (pratica.getUpdateStatus().equals(UpdateStatus.INSERT.getTipo())) {
			pratica.setPraticaId(praticaDao.getSequence());
			praticaDao.insert(pratica);
			praticaClreqDao.insertPostInserimentoPratica(pratica.getPraticaId());
		}
		for (AppuntamentoForPost a : appuntamenti) {
			if (a.getUpdateStatus().equals(UpdateStatus.INSERT.getTipo())) {
				nuovoAppuntamentoId = appuntamentoDao.getSequence();
				a.setAppuntamentoId(nuovoAppuntamentoId);
				a.setPraticaId(pratica.getPraticaId());
				appuntamentoDao.insert(a);
			}
		}
		for (PrescrizioneForPost p : prescrizioni) {
			if (p.getUpdateStatus().equals(UpdateStatus.INSERT.getTipo())) {
				nuovaPrescrizioneId = prescrizioneDao.getSequence();
				p.setPrescrizioneId(nuovaPrescrizioneId);
				p.setPraticaId(pratica.getPraticaId());
				prescrizioneDao.insert(p);
			}
		}

		for (PraticaDettaglioForPost a : praticheDettaglio) {
			if (a.getAppuntamentoId() != null && a.getAppuntamentoId().equals(-1)) {
				a.setAppuntamentoId(nuovoAppuntamentoId);
			}
			if (a.getPrescrizioneId() != null && a.getPrescrizioneId().equals(-1)) {
				a.setPrescrizioneId(nuovaPrescrizioneId);
			}
			if (a.getUpdateStatus().equals(UpdateStatus.INSERT.getTipo())) {
				a.setPraticaDetId(null);
				a.setPraticaId(pratica.getPraticaId());
				praticaDettaglioDao.insert(a);
				// log insert TODO:

			} else if (a.getUpdateStatus().equals(UpdateStatus.DELETE.getTipo())) {
				a.setUtenteCancellazione(shibIdentitaCodiceFiscale);
				praticaDettaglioDao.delete(a);
			} else if (a.getUpdateStatus().equals(UpdateStatus.UPDATE.getTipo())) {
				a.setUtenteCancellazione(null);
				a.setDataCancellazione(null);
				praticaDettaglioDao.update(a, a.getPraticaDetId());

			}
		}
		for (PraticaDettaglioForPost a : scadenzeAutomatiche) {
			if (a.getAppuntamentoId() != null && a.getAppuntamentoId().equals(-1)) {
				a.setAppuntamentoId(nuovoAppuntamentoId);
			}
			if (a.getPrescrizioneId() != null && a.getPrescrizioneId().equals(-1)) {
				a.setPrescrizioneId(nuovaPrescrizioneId);
			}
			if (a.getUpdateStatus().equals(UpdateStatus.INSERT.getTipo())) {
				a.setPraticaDetId(null);
				a.setPraticaId(pratica.getPraticaId());
				praticaDettaglioDao.insert(a);
			} else if (a.getUpdateStatus().equals(UpdateStatus.DELETE.getTipo())) {
				a.setUtenteCancellazione(shibIdentitaCodiceFiscale);
				praticaDettaglioDao.delete(a);
			} else {
				a.setUtenteCancellazione(null);
				a.setDataCancellazione(null);
				a.setUtenteCreazione(shibIdentitaCodiceFiscale);
				praticaDettaglioDao.updateSpecificaPostPratiche(a, a.getPraticaDetId());

			}
		}
		for (PrescrizioneForPost p : prescrizioni) {
			if (p.getUpdateStatus().equals(UpdateStatus.DELETE.getTipo())) {
				p.setUtenteCancellazione(shibIdentitaCodiceFiscale);
				prescrizioneDao.delete(p);
			} else if (p.getUpdateStatus().equals(UpdateStatus.UPDATE.getTipo())) {

				p.setUtenteCancellazione(null);
				p.setDataCancellazione(null);
				prescrizioneDao.update(p, p.getPrescrizioneId());
			}
		}
		for (AppuntamentoForPost a : appuntamenti) {
			if (a.getUpdateStatus().equals(UpdateStatus.DELETE.getTipo())) {
				a.setUtenteCancellazione(shibIdentitaCodiceFiscale);
				appuntamentoDao.delete(a);
			} else if (a.getUpdateStatus().equals(UpdateStatus.UPDATE.getTipo())) {
				a.setUtenteCancellazione(null);
				a.setDataCancellazione(null);
				appuntamentoDao.update(a, a.getAppuntamentoId());
			}
		}

		if (pratica.getUpdateStatus().equals(UpdateStatus.DELETE.getTipo())) {
			pratica.setUtenteCancellazione(shibIdentitaCodiceFiscale);
			praticaDao.delete(pratica);
		} else if (pratica.getUpdateStatus().equals(UpdateStatus.UPDATE.getTipo())) {
			pratica.setUtenteCancellazione(null);
			pratica.setDataCancellazione(null);
			praticaDao.update(pratica, pratica.getPraticaId());
		}

		if (operazione.equals(UpdateStatus.UPDATE)) {
			// id vecchio PRADETID
		} else if (operazione.equals(UpdateStatus.DELETE)) {

		}
	}

	private void controlliInsertOUpdatePostPratica(ModelPraticaPost body,
			List<PraticaScadenzaGenerata> praticaScadenzaGenerataList) {
		boolean trovato = Boolean.FALSE;
		List<AzioneConfig> azioneConfigList = azioneConfigDao.getByPraticaTipoId(body.getPraticaTipoId());
		for (AzioneConfig ag : azioneConfigList) {
			if (ag.getPraticaTipoId().equals(body.getPraticaTipoId())
					&& ag.getAzioneId().equals(body.getPraticaDettaglio().getAzioneId())
					&& ag.getProfiloId().equals(body.getPraticaDettaglio().getProfiloId())
					&& Abilitazione.WRITE.getTipo().equals(ag.getAbilitazione())) {
				trovato = Boolean.TRUE;
				break;
			}
		}
		if (Boolean.FALSE.equals(trovato)) {
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.UTENTE_NON_ABILITATO_INSERIMENTO,
					logAzioneDesc(body));
		}
		if (body.getPraticaDettaglio().getFlgScadenza() == null && body.getPraticaDettaglio().getProfiloId()
				.equals(body.getPraticaDettaglio().getProfiloIdScadenza())) {
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.AZIONE_ATTIVITA_DIVERSA);

		}

		if (Scadenza.MANUALE.getTipo().equals(body.getPraticaDettaglio().getFlgScadenza()) && !body
				.getPraticaDettaglio().getProfiloIdScadenza().equals(body.getPraticaDettaglio().getProfiloId())) {
			trovato = Boolean.FALSE;
			for (PraticaScadenzaGenerata sg : praticaScadenzaGenerataList) {
				if (sg.getPraticaTipoId().equals(body.getPraticaTipoId())
						&& sg.getAzioneId().equals(body.getPraticaDettaglio().getAzioneId())
						&& sg.getAzioneIdScadenza().equals(body.getPraticaDettaglio().getAzioneId())
						&& sg.getProfiloId().equals(body.getPraticaDettaglio().getProfiloIdScadenza())
						&& sg.getGiorniScadenza().equals(-1)) {
					trovato = Boolean.TRUE;
					break;
				}
			}
			if (Boolean.FALSE.equals(trovato)) {
				throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.AZIONE_NON_ABILITATO_INSMOD,
						logAzioneDesc(body), logProfiloId(body));
			}

		}
	}

	private void controlliUpdateODeleteInPostPratica(ModelPraticaPost body) {
		RESTErrorUtil.checkNotNull(body.getPraticaId(), ErrorCodeEnum.PRATICA_ID_OBBLIGATORIA);
		RESTErrorUtil.checkNotNull(body.getPraticaDettaglio().getPraticaDetId(),
				ErrorCodeEnum.PRATICA_DET_ID_OBBLIGATORIO);
		PraticaDettaglio praticaDettaglioOld;
		try {
			praticaDettaglioOld = praticaDettaglioDao
					.getByPraticaDetIdPraticaId(body.getPraticaDettaglio().getPraticaDetId(), body.getPraticaId());
		} catch (EmptyResultDataAccessException e) {
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.AZIONE_ATTIVITA_NON_TROVATA,
					body.getPraticaDettaglio().getPraticaDetId().toString());
		}
		if (Scadenza.AUTOMATICA.getTipo().equals(praticaDettaglioOld.getFlgScadenza())) {
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.AZIONE_NON_ABILITATO_SCADENZE_AUTOMATICHE);
		}
		if (!praticaDettaglioOld.getProfiloId().equals(body.getPraticaDettaglio().getProfiloId())) {
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.AZIONE_PROFILO_NON_ABILITATO);
		}
	}

	private UpdateStatus funzioneSelezioneOperazionePostPratica(ModelPraticaPost body) {
		UpdateStatus updateStatus;
		if (Objects.isNull(body.getPraticaDettaglio().getPraticaDetId())) {
			updateStatus = UpdateStatus.INSERT;
		} else if (Objects.isNull(body.getPraticaDettaglio().getAzioneId())) {
			updateStatus = UpdateStatus.UPDATE;
		} else {
			updateStatus = UpdateStatus.DELETE;
		}
		return updateStatus;
	}

	private void funzioneAggiornamentoOggettiPratica(PraticaForPost pratica, List<AppuntamentoForPost> appuntamenti,
			List<PrescrizioneForPost> prescrizioni, List<PraticaScadenzaGenerata> scadenzaGenerata,
			List<PraticaDettaglioForPost> scadenzeAutomatiche, List<PraticaDettaglioForPost> praticaDettaglio,
			ModelPraticaDettaglioPost dettaglioBody, Integer profiloId, String shibIdentitaCodiceFiscale, Integer sId) {
		List<PraticaWorkflow> praticaWorklowList = praticaWorkflowDao.getByPraticaTipoId(pratica.getPraticaTipoId());
		List<PraticaScadennzaAnnullata> praticaScadenzaAnnullataList = praticaScadenzaAnnullataDao
				.getByPraticaTipoId(pratica.getPraticaTipoId());
		List<PraticaStato> praticaStatoList = praticaStatoDao.getAll();

		List<AppuntamentoConfig> appuntamentoConfigList = appuntamentoConfigDao
				.getByPraticaTipoId(pratica.getPraticaTipoId());
		List<AppuntamentoWorkflow> appuntamentoWorkflowList = appuntamentoWorkflowDao
				.getByPraticaTipoId(pratica.getPraticaTipoId());
		List<AppuntamentoStato> appuntamentoStatoList = appuntamentoService.getAppuntamentoStatoDecodifica(sId);

		List<PrescrizioneConfig> prescrizioneConfigList = prescrizioneConfigDao
				.getByPraticaTipoId(pratica.getPraticaTipoId());
		List<PrescrizioneWorkflow> prescrizioneWorklowList = prescrizioneWorkflowDao
				.getByPraticaTipoId(pratica.getPraticaTipoId());
		List<PrescrizioneStato> prescrizioneStatoList = prescrizioneService.getPrescrizioneStatoDecodifica(sId);

		for (PraticaDettaglioForPost attivita : praticaDettaglio) {
			// se non scadenza e non e' un operazione di delete
			if (attivita.getFlgScadenza() == null
					&& !attivita.getUpdateStatus().equals(UpdateStatus.DELETE.getTipo())) {
				for (PraticaWorkflow pw : praticaWorklowList) {
					if (pw.getPraticaTipoId().equals(pratica.getPraticaTipoId())
							&& pw.getAzioneId().equals(attivita.getAzioneId())
							&& pw.getPraticaStatoIdIniziale() == null) {

						if (!pratica.getPraticaTipoId().equals(pw.getPraticaTipoId())) {
							throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.PRATICA_TIPO_ID_UGUALE,
									logAttivita(attivita));
						} else if (pratica.getDataoraApertura() != null) {
							throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.SECONDA_NUOVA_PRATICA,
									logAttivita(attivita));
						}
						pratica.setStatoId(null);
						pratica.setDataoraApertura(attivita.getDataoraAzione());
						pratica.setDataoraChiusura(null);
						pratica.setPraticaNumero(attivita.getPraticaNumero());
						if (Objects.isNull(attivita.getPraticaId())) {
							pratica.setPraticaId(-1);
							pratica.setStatoId(null);
							pratica.setUpdateStatus(UpdateStatus.INSERT.getTipo());
						} else {
							pratica.setUpdateStatus(UpdateStatus.UPDATE.getTipo());
							attivita.setPraticaId(pratica.getPraticaId());
						}
					}
				}
				if (Objects.isNull(pratica.getPraticaId())) {
					throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.PRATICA_ID_NULL,
							logAttivita(attivita));
				}

				PrescrizioneForPost prescrizione = funzioneGestionePrescrizionePerAggiornamentoOggettiPostPratica(
						pratica, prescrizioni, scadenzeAutomatiche, prescrizioneWorklowList, prescrizioneStatoList,
						attivita, shibIdentitaCodiceFiscale);

				AppuntamentoForPost appuntamento = funzioneGestionePrescrizionePerAggiornamentoAppuntamentoPostPratica(
						pratica, appuntamenti, scadenzeAutomatiche, dettaglioBody, appuntamentoWorkflowList,
						appuntamentoStatoList, attivita, prescrizione, shibIdentitaCodiceFiscale);
//			   -- controllo variazione stato pratica
				if (Objects.nonNull(pratica.getDataoraChiusura())) {
					throw RESTErrorUtil.generateGenericRestException(
							ErrorCodeEnum.ATTIVITA_NON_COMPATIBILE_PRATICA_CHIUSA, logAttivita(attivita),
							logPraticaTipo(pratica));
				}

				PraticaWorkflow praticaWorkflow = null;
				for (PraticaWorkflow pw : praticaWorklowList) {
					if (pw.getPraticaTipoId().equals(pratica.getPraticaTipoId())
							&& pw.getAzioneId().equals(attivita.getAzioneId())
							&& ((pw.getPraticaStatoIdIniziale() == null && pratica.getStatoId() == null)
									|| (pw.getPraticaStatoIdIniziale() != null
											&& pw.getPraticaStatoIdIniziale().equals(pratica.getStatoId())))) {
						praticaWorkflow = pw;
						break;
					}

				}
				if (praticaWorkflow == null && appuntamento == null && prescrizione == null) {
					throw RESTErrorUtil.generateGenericRestException(
							ErrorCodeEnum.ATTIVITA_NON_COMPATIBILE_PRATICA_STATO, logAttivita(attivita),
							logPraticaTipo(pratica), logStato(pratica));
				}

				if (praticaWorkflow == null) {
					attivita.setPraticaStatoId(pratica.getStatoId());
				} else {
					attivita.setPraticaStatoId(praticaWorkflow.getPraticaStatoIdFinale());

					if (!praticaWorkflow.getPraticaStatoIdFinale().equals(pratica.getStatoId())) {
						for (PrescrizioneForPost p : prescrizioni) {
							if (!attivita.getPraticaDetId().equals(p.getPraDetIdApertura())
									&& p.getDataoraApertura() != null && p.getDataoraChiusura() == null) {
								throw RESTErrorUtil.generateGenericRestException(
										ErrorCodeEnum.ATTIVITA_NON_PUO_CAMBIARE_STATO, logAttivita(attivita),
										logPrescrizioneTipo(p));
							}
						}
						for (AppuntamentoForPost a : appuntamenti) {
							if (!attivita.getPraticaDetId().equals(a.getPraDetIdApertura())
									&& a.getDataoraApertura() != null && a.getDataoraChiusura() == null) {
								throw RESTErrorUtil.generateGenericRestException(
										ErrorCodeEnum.ATTIVITA_PRATICA_CON_SOPRALLUOGO, logAttivita(attivita),
										logAppuntamentoTipo(a));
							}
						}

						pratica.setStatoId(praticaWorkflow.getPraticaStatoIdFinale());

						// TODO: è POSSIBILE CHE NON CI SIA PRATICA STATO?
						PraticaStato praticaStato = null;
						for (PraticaStato ps : praticaStatoList) {
							if (ps.getPraticaStatoId().equals(pratica.getStatoId())) {
								praticaStato = ps;
								break;
							}

						}

						if (Boolean.TRUE.equals(praticaStato.isPraticaStatoFinale())) {
							pratica.setDataoraChiusura(attivita.getDataoraAzione());
							scadenzeAutomatiche.clear();
						}

					}

				}

				if (appuntamento != null) {
					attivita.setAppuntamentoId(appuntamento.getAppuntamentoId());
					AppuntamentoConfig appuntamentoConfig = null;
					for (AppuntamentoConfig ac : appuntamentoConfigList) {
						if (ac.getPraticaTipoId().equals(pratica.getPraticaTipoId())
								&& ac.getAppuntamentoTipoId().equals(appuntamento.getAppuntamentoTipoId())
								&& ac.getPraticaStatoId().equals(pratica.getStatoId())) {
							appuntamentoConfig = ac;
						}
					}
					if (appuntamentoConfig == null) {
						throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.SOPRALLUOGO_NON_COMPATIBILE,
								logAppuntamentoTipo(appuntamento), logAttivita(attivita),
								attivita.getStatoId() != null ? logAttivitaStato(attivita) : "null");
					}
				}

				if (prescrizione != null) {
					Boolean trovato = Boolean.FALSE;
					for (PrescrizioneConfig pc : prescrizioneConfigList) {
						if (pc.getPraticaTipoId().equals(pratica.getPraticaTipoId())
								&& pc.getPrescrizioneTipoId().equals(prescrizione.getPrescrizioneTipoId())
								&& pc.getPraticaStatoId().equals(pratica.getStatoId())) {
							trovato = Boolean.TRUE;
						}
					}
					if (Boolean.FALSE.equals(trovato)) {
						throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.PRESCRIZIONE_NON_COMPATIBILE,
								logAttivita(attivita), logPraticaTipo(pratica), logStato(pratica));
					}
				}

				if (pratica.getDataoraChiusura() == null
						&& (prescrizione == null || prescrizione.getDataoraChiusura() == null
								|| (prescrizione.getPraDetIdChiusura() != null
										&& prescrizione.getPraDetIdChiusura().equals(attivita.getPraticaDetId())))
						&& (appuntamento == null || appuntamento.getDataoraChiusura() == null
								|| (appuntamento.getPraDetIdChiusura() != null
										&& appuntamento.getPraDetIdChiusura().equals(attivita.getPraticaDetId())))) {
					for (PraticaScadenzaGenerata sg : scadenzaGenerata) {
						if (sg.getAzioneId().equals(attivita.getAzioneId()) && ((prescrizione == null
								&& appuntamento == null)
								|| (prescrizione != null
										&& sg.getPrescrizioneTipoId().equals(prescrizione.getPrescrizioneTipoId()))
								|| (appuntamento != null
										&& sg.getAppuntamentoTipoId().equals(appuntamento.getAppuntamentoTipoId())))) {
							PraticaDettaglioForPost scadenza = new PraticaDettaglioForPost();
							scadenza.setPraticaDetId(-1);
							scadenza.setPraticaId(pratica.getPraticaId());
							if (prescrizione != null && !(prescrizione.getPraDetIdChiusura() != null
									&& prescrizione.getPraDetIdChiusura().equals(attivita.getPraticaDetId()))) {
								scadenza.setPrescrizioneId(attivita.getPrescrizioneId());
							}
							if (appuntamento != null && !(appuntamento.getPraDetIdChiusura() != null
									&& appuntamento.getPraDetIdChiusura().equals(attivita.getPraticaDetId()))) {
								scadenza.setAppuntamentoId(attivita.getAppuntamentoId());
							}
							scadenza.setAppuntamentoId(attivita.getAppuntamentoId());
							scadenza.setAzioneId(sg.getAzioneIdScadenza());

							Date dataOraAzione;
							if (appuntamento != null
									&& appuntamento.getPraDetIdApertura().equals(attivita.getPraticaDetId())) {
								dataOraAzione = aggiungiGiorniAData(appuntamento.getDataoraFine(),
										sg.getGiorniScadenza());

							} else {
								dataOraAzione = aggiungiGiorniAData(attivita.getDataoraAzione(),
										sg.getGiorniScadenza());

							}

							scadenza.setDataoraAzione(dataOraAzione);
							scadenza.setProfiloId(attivita.getProfiloId());
							scadenza.setProfiloIdScadenza(sg.getProfiloId());
							scadenza.setFlgScadenza(Scadenza.AUTOMATICA.getTipo());
							scadenza.setUtenteCreazione(attivita.getUtenteCreazione());
							scadenza.setUtenteModifica(attivita.getUtenteModifica());
							scadenza.setUpdateStatus(UpdateStatus.INSERT.getTipo());
							scadenzeAutomatiche.add(scadenza);
						}
					}

				}
				for (PraticaScadennzaAnnullata sa : praticaScadenzaAnnullataList) {
					if (sa.getAzioneId().equals(attivita.getAzioneId())) {
						scadenzeAutomatiche.removeIf(s -> s.getAzioneId().equals(sa.getAzioneIdScadenza()));
					}
				}
			} else if (Scadenza.MANUALE.getTipo().equals(attivita.getFlgScadenza())
					&& !attivita.getUpdateStatus().equals(UpdateStatus.DELETE.getTipo())) {
				if (attivita.getAppuntamentoId() != null) {
					// Appuntamento appuntamento =
					// appuntamentoDao.get(attivita.getAppuntamentoId());
					scadenzeAutomatiche.removeIf(s -> (s.getAppuntamentoId() != null
							&& s.getAppuntamentoId().equals(attivita.getAppuntamentoId()))
							&& s.getAzioneId().equals(attivita.getAzioneId()) && s.getProfiloId().equals(profiloId));
				} else if (attivita.getPrescrizioneId() != null) {
					// Prescrizione prescrizione =
					// prescrizioneDao.get(attivita.getPrescrizioneId());
					scadenzeAutomatiche.removeIf(s -> (s.getPrescrizioneId() != null
							&& s.getPrescrizioneId().equals(attivita.getPrescrizioneId()))
							&& s.getAzioneId().equals(attivita.getAzioneId()) && s.getProfiloId().equals(profiloId));

				}
				// scadenze manuali
				// se associata a prescrizione devi pescare la prescrizione associata
				// se associata ad appuntamento pesca appuntamento.
				// annnulla tutte le scadenze automatiche nella lista che abbiano la stessa
				// prescrizone o entrambi null e la stesso
				// appuntamento o entrambi null e lo stesso azioneId scadenza e lo stesso
				// profilo.

			}
		}
	}

	private Date aggiungiGiorniAData(Date data, Integer giorni) {
		return Date.from(data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(giorni)
				.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	private AppuntamentoForPost funzioneGestionePrescrizionePerAggiornamentoAppuntamentoPostPratica(
			PraticaForPost pratica, List<AppuntamentoForPost> appuntamenti,
			List<PraticaDettaglioForPost> scadenzeAutomatiche, ModelPraticaDettaglioPost dettaglioBody,
			List<AppuntamentoWorkflow> appuntamentoWorkflowList, List<AppuntamentoStato> appuntamentoStatoList,
			PraticaDettaglioForPost attivita, PrescrizioneForPost prescrizione, String shibIdentitaCodiceFiscale) {
		AppuntamentoForPost appuntamento = null;
		if (attivita.getAppuntamentoId() != null) {

			for (AppuntamentoForPost a : appuntamenti) {
				if (a.getAppuntamentoId().equals(attivita.getAppuntamentoId())) {
					appuntamento = a;
				}
			}
			if (appuntamento == null) {
				throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.ATTIVITA_SOPRALLUOGO_SCONOSCIUTO,
						logAttivita(attivita));
			}
		}
//-- controllo creazione sopralluogo

		AppuntamentoWorkflow appuntamentoWorkflow = null;
		for (AppuntamentoWorkflow aw : appuntamentoWorkflowList) {
			if (aw.getPraticaTipoId().equals(pratica.getPraticaTipoId())
					&& aw.getAzioneId().equals(attivita.getAzioneId())
					&& (appuntamento == null || aw.getAppuntamentoTipoId().equals(appuntamento.getAppuntamentoTipoId()))
					&& aw.getAppuntamentoStatoIdIniziale() == null) {
				appuntamentoWorkflow = aw;
			}
		}
		if (appuntamentoWorkflow != null) {
			if (appuntamento == null) {
				if (attivita.getPrescrizioneId() != null) {
					throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.ATTIVITA_GENERA_UN_SOPRALLUOGO,
							logAttivita(attivita), logPrescrizioneTipo(prescrizione));
				}
				for (AppuntamentoForPost a : appuntamenti) {
					if (a.getAppuntamentoId().equals(-1)) {
						throw RESTErrorUtil.generateGenericRestException(
								ErrorCodeEnum.ATTIVITA_GENERA_SECONDO_SOPRALLUOGO, logAttivita(attivita));
					}
				}

				if (attivita.getUpdateStatus().equals(UpdateStatus.INSERT.getTipo())
						&& dettaglioBody.getDataoraInizio() == null) {
					throw RESTErrorUtil.generateGenericRestException(
							ErrorCodeEnum.ATTIVITA_GENERA_SOPRALLUOGO_NO_ORA_INIZIO, logAttivita(attivita));
				}
				if (attivita.getUpdateStatus().equals(UpdateStatus.INSERT.getTipo())
						&& dettaglioBody.getDataoraFine() == null) {
					throw RESTErrorUtil.generateGenericRestException(
							ErrorCodeEnum.ATTIVITA_GENERA_SOPRALLUOGO_NO_ORA_FINE, logAttivita(attivita));
				}
				appuntamento = new AppuntamentoForPost();
				appuntamento.setAppuntamentoId(-1);
				appuntamento.setAppuntamentoTipoId(appuntamentoWorkflow.getAppuntamentoTipoId());
				appuntamento.setDataoraInizio(dettaglioBody.getDataoraInizio());
				appuntamento.setDataoraFine(dettaglioBody.getDataoraFine());
				appuntamento.setUtenteCreazione(shibIdentitaCodiceFiscale);
				appuntamento.setUtenteModifica(shibIdentitaCodiceFiscale);
				appuntamento.setUpdateStatus(UpdateStatus.INSERT.getTipo());
				appuntamenti.add(appuntamento);

			} else {
				appuntamento.setUpdateStatus(UpdateStatus.UPDATE.getTipo());
			}

			if (!appuntamento.getAppuntamentoTipoId().equals(appuntamentoWorkflow.getAppuntamentoTipoId())) {
				throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.ATTIVITA_GENERA_SOPRALLUOGO_DIVERSO,
						logAttivita(attivita), logAppuntamentoWorkflowAppuntamentoTipo(appuntamentoWorkflow),
						logAppuntamentoTipo(appuntamento));
			}

			appuntamento.setDataoraApertura(attivita.getDataoraAzione());
			appuntamento.setPraDetIdApertura(attivita.getPraticaDetId());
			appuntamento.setAppuntamentoNumero(attivita.getAppuntamentoNumero());
		} else if (appuntamento != null && appuntamento.getStatoId() == null) {
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.ATTIVITA_ASSOCITANA_NON_APERTA,
					logAttivita(attivita), logAppuntamentoTipo(appuntamento));
		}
//		   -- controllo variazione stato sopralluogo
		if (appuntamento != null) {
			if (appuntamento.getDataoraChiusura() != null) {
				throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.ATTIVITA_NON_COMATIBILE_CON_SOPRALLUOGO,
						logAttivita(attivita), logAppuntamentoTipo(appuntamento));
			}
			AppuntamentoWorkflow appuntamentoWorkflow2 = null;
			for (AppuntamentoWorkflow aw : appuntamentoWorkflowList) {
				if (aw.getPraticaTipoId().equals(pratica.getPraticaTipoId())
						&& aw.getAzioneId().equals(attivita.getAzioneId())
						&& aw.getAppuntamentoTipoId().equals(appuntamento.getAppuntamentoTipoId())
						&& ((aw.getAppuntamentoStatoIdIniziale() == null && appuntamento.getStatoId() == null)
								|| (aw.getAppuntamentoStatoIdIniziale() != null
										&& aw.getAppuntamentoStatoIdIniziale().equals(appuntamento.getStatoId())))) {
					appuntamentoWorkflow2 = aw;
					break;
				}
			}
			if (appuntamentoWorkflow2 == null) {
				throw RESTErrorUtil.generateGenericRestException(
						ErrorCodeEnum.ATTIVITA_NON_COMATIBILE_CON_SOPRALLUOGO_STATO, logAttivita(attivita),
						logAppuntamentoTipo(appuntamento), logAppuntamentoStato(appuntamento));
			}
			attivita.setAppuntamentoStatoId(appuntamentoWorkflow2.getAppuntamentoStatoIdFinale());
			appuntamento.setStatoId(appuntamentoWorkflow2.getAppuntamentoStatoIdFinale());

			AppuntamentoStato appuntamentoStato = null;
			for (AppuntamentoStato as : appuntamentoStatoList) {
				if (as.getAppuntamentoStatoId().equals(appuntamento.getStatoId())) {
					appuntamentoStato = as;
					break;
				}
			}

			if (Boolean.TRUE.equals(appuntamentoStato.isAppuntamentoStatoFinale())) {
				appuntamento.setPraDetIdChiusura(attivita.getPraticaDetId());
				appuntamento.setDataoraChiusura(attivita.getDataoraAzione());
				rimuoviAppuntamentoFromScadenzeAutomaticheList(scadenzeAutomatiche, appuntamento);
			}
		}
		return appuntamento;
	}

	private PrescrizioneForPost funzioneGestionePrescrizionePerAggiornamentoOggettiPostPratica(PraticaForPost pratica,
			List<PrescrizioneForPost> prescrizioni, List<PraticaDettaglioForPost> scadenzeAutomatiche,
			List<PrescrizioneWorkflow> prescrizioneWorklowList, List<PrescrizioneStato> prescrizioneStatoList,
			PraticaDettaglioForPost attivita, String shibIdentitaCodiceFiscale) {
		PrescrizioneForPost prescrizione = null;
		if (attivita.getPrescrizioneId() != null) {
			for (PrescrizioneForPost p : prescrizioni) {
				if (p.getPrescrizioneId().equals(attivita.getPrescrizioneId())) {
					prescrizione = p;
					break;
				}

			}
			if (prescrizione == null) {
				throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.PRESCRIZIONE_ASSOCIATA_NON_TROVATA,
						logAttivita(attivita));

			}
		}

		PrescrizioneWorkflow prescrizioneWorkflow = null;
		for (PrescrizioneWorkflow pw : prescrizioneWorklowList) {
			if (pw.getPraticaTipoId().equals(pratica.getPraticaTipoId())
					&& pw.getAzioneId().equals(attivita.getAzioneId())
					&& (prescrizione == null || pw.getPrescrizioneTipoId().equals(prescrizione.getPrescrizioneTipoId()))
					&& pw.getPrescrizioneStatoIdIniziale() == null) {
				prescrizioneWorkflow = pw;
				break;
//
			}
		}

		if (prescrizioneWorkflow != null) {
			if (prescrizione == null) {
				if (attivita.getAppuntamentoId() != null) {
					throw RESTErrorUtil.generateGenericRestException(
							ErrorCodeEnum.ATTIVITA_SOPRALLUOGO_GENEREREREBBE_PRESCRIZIONE, logAttivita(attivita),
							logAppuntamentoId(attivita));
				}

				for (PrescrizioneForPost p : prescrizioni) {
					if (p.getPrescrizioneId().equals(-1)) {
						throw RESTErrorUtil.generateGenericRestException(
								ErrorCodeEnum.ATTIVITA_GENEREREBBE_NUOVA_PRESCRIIONE, logAttivita(attivita));

					}
				}

				prescrizione = new PrescrizioneForPost();
				prescrizione.setPrescrizioneId(-1);
				prescrizione.setPrescrizioneTipoId(prescrizioneWorkflow.getPrescrizioneTipoId());
				prescrizione.setUtenteCreazione(shibIdentitaCodiceFiscale);
				prescrizione.setUtenteModifica(shibIdentitaCodiceFiscale);
				prescrizione.setUpdateStatus(UpdateStatus.INSERT.getTipo());
				prescrizioni.add(prescrizione);
			} else {
				prescrizione.setUpdateStatus(UpdateStatus.UPDATE.getTipo());
			}

			if (!prescrizione.getPrescrizioneTipoId().equals(prescrizioneWorkflow.getPrescrizioneTipoId())) {
				throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.ATTIVITA_GENERA_PRESCRIZIONE_DIVERSA,
						logAttivita(attivita), logPrescrizioneWorkflowPrescrizioneTipo(prescrizioneWorkflow),
						logPrescrizioneTipo(prescrizione));
			}
			prescrizione.setDataoraApertura(attivita.getDataoraAzione());
			prescrizione.setPraDetIdApertura(attivita.getPraticaDetId());
			prescrizione.setPrescrizioneNumero(attivita.getPrescrzioneNumero());
			attivita.setPrescrizioneId(prescrizione.getPrescrizioneId());
		} else if (prescrizione != null && prescrizione.getStatoId() == null) {
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.ATTIVITA_NON_ASSOCIATA_PRESCRIZIONE,
					logAttivita(attivita), logPrescrizioneTipo(prescrizione));
		}

		if (prescrizione != null) {
			if (prescrizione.getDataoraChiusura() != null) {
				throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.ATTIVITA_NON_COMPATIBILE_PRESCRIZIONE,
						logAttivita(attivita), logPrescrizioneTipo(prescrizione));

			} else {
				prescrizioneWorkflow = null;
				for (PrescrizioneWorkflow pw : prescrizioneWorklowList) {
					if (pw.getPraticaTipoId().equals(pratica.getPraticaTipoId())
							&& pw.getAzioneId().equals(attivita.getAzioneId())
							&& pw.getPrescrizioneTipoId().equals(prescrizione.getPrescrizioneTipoId())
							&& ((pw.getPrescrizioneStatoIdIniziale() == null && prescrizione.getStatoId() == null)
									|| (pw.getPrescrizioneStatoIdIniziale() != null && pw
											.getPrescrizioneStatoIdIniziale().equals(prescrizione.getStatoId())))) {
						prescrizioneWorkflow = pw;
					}
				}
				if (prescrizioneWorkflow == null) {
					throw RESTErrorUtil.generateGenericRestException(
							ErrorCodeEnum.ATTIVITA_NON_COMPATIBILE_PRESCRIZIONE_STATO, logAttivita(attivita),
							logPrescrizioneTipo(prescrizione), logAttivitaStato(attivita));
				}

				attivita.setPrescrizioneStatoId(prescrizioneWorkflow.getPrescrizioneStatoIdFinale());
				prescrizione.setStatoId(prescrizioneWorkflow.getPrescrizioneStatoIdFinale());
				PrescrizioneStato prescrizioneStato = null;
				for (PrescrizioneStato ps : prescrizioneStatoList) {
					if (ps.getPrescrizioneStatoId().equals(prescrizione.getStatoId())) {
						prescrizioneStato = ps;
					}
				}
				if (Boolean.TRUE.equals(prescrizioneStato.isPrescrizioneStatoFinale())) {
					prescrizione.setPraDetIdChiusura(attivita.getPraticaDetId());
					prescrizione.setDataoraChiusura(attivita.getDataoraAzione());
					// TODO in futuro con marco: scadenze automtaiche dove è caricata?
					rimuoviPrescrizioneFromScadenzeAutomaticheList(scadenzeAutomatiche, prescrizione);
				}
			}

		}
		return prescrizione;
	}

	private PraticaForPost getPratica(ModelPraticaPost body, Integer enteId, String shibIdentitaCodiceFiscale) {
		PraticaForPost pratica;
		if (Objects.nonNull(body.getPraticaId())) {
			pratica = praticaDao.getPraticaValida(body.getPraticaId());
			pratica.setUpdateStatus(UpdateStatus.DELETE.getTipo());
			pratica.setDataoraApertura(null);
			pratica.setDataoraChiusura(null);
		} else {
			pratica = new PraticaForPost();
			pratica.setEnteId(enteId);
			pratica.setStrutturaId(body.getStrutturaId());
			pratica.setPraticaTipoId(body.getPraticaTipoId());
			pratica.setUtenteCreazione(shibIdentitaCodiceFiscale);
			pratica.setPraticaNumero(body.getPraticaNumero());
			pratica.setUpdateStatus(UpdateStatus.ND.getTipo());
		}
		return pratica;
	}

	private Date newDateOggiMezzanotteMenoUnSecondo() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 0);
		Date dataMezzanotte = cal.getTime();
		return dataMezzanotte;
	}

	private void rimuoviAppuntamentoFromScadenzeAutomaticheList(List<PraticaDettaglioForPost> scadenzeAutomaticheList,
			AppuntamentoForPost appuntamento) {
		scadenzeAutomaticheList.removeIf(
				s -> (s.getAppuntamentoId() != null && s.getAppuntamentoId().equals(appuntamento.getAppuntamentoId())));
	}

	private void rimuoviPrescrizioneFromScadenzeAutomaticheList(List<PraticaDettaglioForPost> scadenzeAutomaticheList,
			PrescrizioneForPost prescrizione) {
		scadenzeAutomaticheList.removeIf(
				s -> (s.getPrescrizioneId() != null && s.getPrescrizioneId().equals(prescrizione.getPrescrizioneId())));
	}

	private String logStato(PraticaForPost pratica) {
		PraticaStato stato = praticaStatoDao.get(pratica.getStatoId());
		return stato.getPraticaStatoDesc();
	}

	private String logPraticaTipo(PraticaForPost pratica) {
		PraticaTipo tipo = praticaTipoDao.get(pratica.getPraticaTipoId());
		return tipo.getPraticaTipoDesc();
	}

	private String logAppuntamentoStato(AppuntamentoForPost appuntamento) {
		ModelAppuntamentoStato stato = appuntamentoService.getAppuntamentoStatoById(appuntamento.getStatoId(), null);
		return stato.getAppuntamentoStatoDesc();
	}

	private String logAppuntamentoTipo(AppuntamentoForPost appuntamento) {
		ModelAppuntamentoTipo tipo = appuntamentoService
				.getAppuntamentoTipoDecodificaById(appuntamento.getAppuntamentoTipoId(), null);
		return tipo.getAppuntamentoTipoDesc();
	}

	private String logAppuntamentoWorkflowAppuntamentoTipo(AppuntamentoWorkflow appuntamentoWorkflow) {
		ModelAppuntamentoTipo tipo = appuntamentoService
				.getAppuntamentoTipoDecodificaById(appuntamentoWorkflow.getAppuntamentoTipoId(), null);
		return tipo.getAppuntamentoTipoDesc();
	}

	private String logAttivitaStato(PraticaDettaglioForPost attivita) {
		PraticaStato stato = praticaStatoDao.get(attivita.getStatoId());
		return stato.getPraticaStatoDesc();
	}

	private String logPrescrizioneTipo(PrescrizioneForPost prescrizione) {
		ModelPrescrizioneTipo prescr = prescrizioneService
				.getPrescrizioneTipoDecodificaById(prescrizione.getPrescrizioneTipoId(), null);
		return prescr.getPrescrizioneTipoDesc();
	}

	private String logPrescrizioneWorkflowPrescrizioneTipo(PrescrizioneWorkflow prescrizioneWorkflow) {
		ModelPrescrizioneTipo prescr = prescrizioneService
				.getPrescrizioneTipoDecodificaById(prescrizioneWorkflow.getPrescrizioneTipoId(), null);
		return prescr.getPrescrizioneTipoDesc();
	}

	private String logAppuntamentoId(PraticaDettaglioForPost attivita) {
		Appuntamento appuntamento = appuntamentoDao.get(attivita.getAppuntamentoId());
		ModelAppuntamentoTipo tipo = appuntamentoService
				.getAppuntamentoTipoDecodificaById(appuntamento.getAppuntamentoTipoId(), null);
		return tipo.getAppuntamentoTipoDesc();
	}

	private String logAttivita(PraticaDettaglioForPost attivita) {
		Azione azione = azioneDao.get(attivita.getAzioneId());
		SimpleDateFormat formatoData = new SimpleDateFormat("HH:mm dd/MM/yyyy");

		return azione.getAzioneDesc() + " " + formatoData.format(attivita.getDataoraAzione());
	}

	private String logAzioneDesc(ModelPraticaPost body) {
		Azione azione = azioneDao.get(body.getPraticaDettaglio().getAzioneId());
		return azione.getAzioneDesc();
	}

	private String logProfiloId(ModelPraticaPost body) {
		return body.getPraticaDettaglio().getProfiloId().toString();
	}

	public ModelPraticaDettaglio praticaDettaglioPost(ModelPraticaDettaglio body, Integer profiloId,
			String shibIdentitaCodiceFiscale, Integer sId) {
		Integer id;
		try {
			if (Objects.isNull(body.getPraticaDetId())) {
				id = praticaDettaglioDao.getSequence();
				var praticaDet = new PraticaDettaglio();
				praticaDet.setPraticaDetId(id);
				praticaDet.setPraticaId(body.getPraticaId());
				praticaDet.setPraticaStatoId(body.getPraticaStatoId());
				praticaDet.setPrescrizioneId(body.getPrescrizioneId());
				praticaDet.setPrescrizioneStatoId(body.getPrescrizioneStatoId());
				praticaDet.setAppuntamentoId(body.getAppuntamentoId());
				praticaDet.setAppuntamentoStatoId(body.getAppuntamentoStatoId());
				praticaDet.setAzioneId(body.getAzioneId());
				praticaDet.setDataoraAzione(body.getDataoraAzione());
				praticaDet.setModuloCompilatoId(body.getModuloCompilatoId());
				praticaDet.setNote(body.getNote());
				praticaDet.setFlgScadenza(body.getFlgScadenza());
				praticaDet.setProfiloId(profiloId);
				praticaDet.setUtenteCreazione(shibIdentitaCodiceFiscale);

				praticaDettaglioDao.insert(praticaDet);
				logAuditDb(sId, AuditOperazioneEnum.INSERT, "vigil_t_pratica_dettaglio",
						praticaDet.getPraticaDetId().toString());
			} else {
				id = body.getPraticaDetId();
				var praticaDet = praticaDettaglioDao.get(body.getPraticaDetId());
				praticaDet.setPraticaStatoId(body.getPraticaStatoId());
				praticaDet.setPrescrizioneId(body.getPrescrizioneId());
				praticaDet.setPrescrizioneStatoId(body.getPrescrizioneStatoId());
				praticaDet.setAppuntamentoId(body.getAppuntamentoId());
				praticaDet.setAppuntamentoStatoId(body.getAppuntamentoStatoId());
				praticaDet.setAzioneId(body.getAzioneId());
				praticaDet.setDataoraAzione(body.getDataoraAzione());
				praticaDet.setModuloCompilatoId(body.getModuloCompilatoId());
				praticaDet.setPraticaId(body.getPraticaDetId());
				praticaDet.setNote(body.getNote());
				praticaDet.setFlgScadenza(body.getFlgScadenza());

				praticaDet.setUtenteModifica(shibIdentitaCodiceFiscale);

				praticaDettaglioDao.update(praticaDet, praticaDet.getPraticaDetId());
				logAuditDb(sId, AuditOperazioneEnum.UPDATE, "vigil_t_pratica_dettaglio",
						praticaDet.getPraticaDetId().toString());

			}

			return praticaDettaglioDao.get(id);
		} catch (EmptyResultDataAccessException e) {
			logError(sId, "praticaPost", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.ERRORE_INSERIMENTO_PRATICA_DETTAGLIO);
		}
	}

	public List<PraticaEstesa> getPratiche(Integer enteId, Integer strutturaId, Integer profiloId,
			Integer tipoPraticaId, Integer statoPraticaId, Integer tipoPrescrizioneId, Integer statoPrescrizioneId,
			String dataAperturaPrescrizioneDa, String dataAperturaPrescrizioneA, String dataChiusuraPrescrizioneDa,
			String dataChiusuraPrescrizioneA, Integer tipoAppuntamentoId, Integer statoAppuntamentoId,
			String dataInizioAppuntamentoDa, String dataInizioAppuntamentoA, String dataFineAppuntamentoDa,
			String dataFineAppuntamentoA, String dataPraticaAperturaDa, String dataPraticaAperturaA,
			String dataPraticaChiusuraDa, String dataPraticaChiusuraA, String filterStruttura, String filterUtente,
			Paginazione paginazione, Integer sId) {
		DateUtils.parsetDate(dataAperturaPrescrizioneDa, DateFormatEnum.API_TIMESTAMP_FORMAT);
		DateUtils.parsetDate(dataAperturaPrescrizioneA, DateFormatEnum.API_TIMESTAMP_FORMAT);
		DateUtils.parsetDate(dataChiusuraPrescrizioneDa, DateFormatEnum.API_TIMESTAMP_FORMAT);
		DateUtils.parsetDate(dataChiusuraPrescrizioneA, DateFormatEnum.API_TIMESTAMP_FORMAT);
		DateUtils.parsetDate(dataInizioAppuntamentoDa, DateFormatEnum.API_TIMESTAMP_FORMAT);
		DateUtils.parsetDate(dataInizioAppuntamentoA, DateFormatEnum.API_TIMESTAMP_FORMAT);
		DateUtils.parsetDate(dataFineAppuntamentoDa, DateFormatEnum.API_TIMESTAMP_FORMAT);
		DateUtils.parsetDate(dataFineAppuntamentoA, DateFormatEnum.API_TIMESTAMP_FORMAT);

		DateUtils.parsetDate(dataPraticaAperturaDa, DateFormatEnum.API_TIMESTAMP_FORMAT);
		DateUtils.parsetDate(dataPraticaAperturaA, DateFormatEnum.API_TIMESTAMP_FORMAT);
		DateUtils.parsetDate(dataPraticaChiusuraDa, DateFormatEnum.API_TIMESTAMP_FORMAT);
		DateUtils.parsetDate(dataPraticaChiusuraA, DateFormatEnum.API_TIMESTAMP_FORMAT);
		;
		List<PraticaEstesa> res = praticaDao.getPratiche(enteId, strutturaId, profiloId, tipoPraticaId, statoPraticaId,
				tipoPrescrizioneId, statoPrescrizioneId, dataAperturaPrescrizioneDa, dataAperturaPrescrizioneA,
				dataChiusuraPrescrizioneDa, dataChiusuraPrescrizioneA, tipoAppuntamentoId, statoAppuntamentoId,
				dataInizioAppuntamentoDa, dataInizioAppuntamentoA, dataFineAppuntamentoDa, dataFineAppuntamentoA,
				dataPraticaAperturaDa, dataPraticaAperturaA, dataPraticaChiusuraDa, dataPraticaChiusuraA,
				filterStruttura, filterUtente, paginazione);

		for (PraticaEstesa r : res) {
			r.setTipo(praticaTipoDao.get(r.getPraticaTipoId()));
			r.setPrescrizioni(presctizioneDao.getListByPratica(profiloId, r.getPraticaId()));
			for (ModelPrescrizioneEsteso p : r.getPrescrizioni()) {
				if (Objects.nonNull(p.getPrescrizioneTipoId())) {
					p.setPrescrizioneTipo(
							prescrizioneService.getPrescrizioneTipoDecodificaById(p.getPrescrizioneTipoId(), sId));
				}
			}
			r.setAppuntamenti(appuntamentoDao.getListByPratica(profiloId, r.getPraticaId()));
			for (ModelAppuntamentoEsteso a : r.getAppuntamenti()) {
				if (Objects.nonNull(a.getAppuntamentoTipoId())) {
					a.setAppuntamentoTipo(
							appuntamentoService.getAppuntamentoTipoDecodificaById(a.getAppuntamentoTipoId(), sId));
				}
			}
		}
		return res;
	}

	public AzioniEstese praticaAzioniGet(Integer profiloId, Integer praticaTipoId, Integer sId) {
		var res = new AzioniEstese();

		res.setAppuntamento(azioneDao.getAzioniAppuntamento(profiloId, praticaTipoId));
		tasformStringToList(res.getAppuntamento());
		res.setPrescrizione(azioneDao.getAzioniPrescrizione(profiloId, praticaTipoId));
		tasformStringToList(res.getPrescrizione());
		res.setPratica(azioneDao.getAzioniPratica(profiloId, praticaTipoId));
		tasformStringToList(res.getPratica());

		return res;
	}

	private void tasformStringToList(List<AzioneEstesa> azione) {
		for (AzioneEstesa a : azione) {
			if (Objects.nonNull(a.getStatoIdLIsta()) && !a.getStatoIdLIsta().trim().isEmpty()) {
				a.setStatoIdLista(Arrays.stream(a.getStatoIdLIsta().split(",")).map(String::trim).map(Integer::parseInt)
						.toList());

			}
		}
	}

	public List<ModelPraticaScadenza> praticaDettaglioPost(Integer profiloId, Integer enteId, Integer strutturaId,
			String dataDa, String dataA, Integer sId) {
		DateUtils.parsetDate(dataDa, DateFormatEnum.API_DATE_FORMAT);
		DateUtils.parsetDate(dataA, DateFormatEnum.API_DATE_FORMAT);
		return praticaDettaglioDao.getScazende(profiloId, enteId, strutturaId, dataDa, dataA);
	}

	public PraticaEstesa praticaGet(Integer praticaId, Integer profiloId, Integer enteId, Integer strutturaId,
			String filtroData, Integer sId) {
		var filterDate = DateUtils.parsetDate(filtroData, DateFormatEnum.API_TIMESTAMP_FORMAT);
		var res = praticaDao.getPraticaEstesa(praticaId);
		if (Objects.nonNull(enteId)) {
			RESTErrorUtil.checkCondition(res.getEnteId().equals(enteId), ErrorCodeEnum.PRATICA_NON_TROVATA_ENTE);
		} else {
			RESTErrorUtil.checkCondition(res.getStrutturaId().equals(strutturaId),
					ErrorCodeEnum.PRATICA_NON_TROVATA_STRUTTURA);
		}

		if (Objects.nonNull(res.getPraticaTipoId())) {
			res.setTipo(praticaTipoDao.get(res.getPraticaTipoId()));
		}
		res.setAttivita(praticaDettaglioDao.getByPratica(praticaId, filterDate));
		for (ModelPraticaDettaglioEstesaPratica a : res.getAttivita()) {
			if (Objects.nonNull(a.getAppuntamentoStatoId())) {
				a.setAppuntamentoStato(appuntamentoService.getAppuntamentoStatoById(a.getAppuntamentoStatoId(), sId));
			}
			if (Objects.nonNull(a.getPrescrizioneStatoId())) {
				a.setPrescrizioneStato(prescrizioneService.getPrescrizioneStatoById(a.getPrescrizioneStatoId(), sId));
			}
			if (Objects.nonNull(a.getPraticaStatoId())) {
				a.setPraticaStato(praticaStatoDao.get(a.getPraticaStatoId()));
			}
		}

		res.setAppuntamenti(appuntamentoDao.getByListaAppuntamentoId(praticaId));
		for (ModelAppuntamentoEsteso a : res.getAppuntamenti()) {
			if (Objects.nonNull(a.getAppuntamentoTipoId())) {
				a.setAppuntamentoTipo(
						appuntamentoService.getAppuntamentoTipoDecodificaById(a.getAppuntamentoTipoId(), sId));
			}
		}

		res.setPrescrizioni(presctizioneDao.getByListaPrescrizioneId(praticaId, filterDate));
		for (ModelPrescrizioneEsteso p : res.getPrescrizioni()) {
			if (Objects.nonNull(p.getPrescrizioneTipoId())) {
				p.setPrescrizioneTipo(
						prescrizioneService.getPrescrizioneTipoDecodificaById(p.getPrescrizioneTipoId(), sId));
			}
		}
		return res;
	}

	@Transactional
	public ModelPraticaPost praticaModificaPost(ModelPraticaPost body, Integer enteId, Integer profiloId,
			String shibIdentitaCodiceFiscale, Integer sId) {
		RESTErrorUtil.checkNotNull(body.getPraticaDettaglio(), ErrorCodeEnum.PRATICA_DETTAGLIO_NON_TROVATA);

		PraticaDettaglio praDetOld = praticaDettaglioDao.get(body.getPraticaDettaglio().getPraticaDetId());
		Integer id = praticaDettaglioDao.getSequence();
		praticaDettaglioDao.delete(praDetOld);
		praDetOld.setDataModifica(null);
		praDetOld.setDataCancellazione(null);
		praDetOld.setUtenteModifica(shibIdentitaCodiceFiscale);
		praDetOld.setUtenteCancellazione(null);
		praDetOld.setModuloCompilatoId(body.getPraticaDettaglio().getModuloCompilatoId());
		praDetOld.setPraticaDetId(id);
		praticaDettaglioDao.insert(praDetOld);
		PraticaDettaglioPost praDet = praticaDettaglioDao.getForPost(id);

		ModelPraticaPost res = praticaDao.getForModulo(body.getPraticaId());
		res.setPraticaDettaglio(praDet);

		return res;
	}

	public FileCustom generaDiario(ModelChiaveValoreList body, Integer praticaId, String filtroData, Integer sId) {
		var filterDate = DateUtils.parsetDate(filtroData, DateFormatEnum.API_TIMESTAMP_FORMAT);
		Pratica pratica = praticaDao.get(praticaId);

		if (filterDate != null && pratica.getDataoraChiusura() != null
				&& pratica.getDataoraChiusura().after(filterDate)) {
			pratica.setDataoraChiusura(null);
		}

		List<PraticaDettaglioEstesaPraticaPerDiario> listaAttivita = praticaDettaglioDao.getAttivitaByPratica(praticaId,
				filterDate);
		var praticaTipo = praticaTipoDao.get(pratica.getPraticaTipoId());
		var appuntamenti = appuntamentoDao.getByListaAppuntamentoIdPerDiario(praticaId, filterDate);
		for (AppuntamentoEstesoPerDiario a : appuntamenti) {
			if (filterDate != null && a.getDataoraChiusura() != null && a.getDataoraChiusura().after(filterDate)) {
				a.setDataoraChiusura(null);
			}

			if (Objects.nonNull(a.getAppuntamentoTipoId())) {
				a.setAppuntamentoTipo(
						appuntamentoService.getAppuntamentoTipoDecodificaById(a.getAppuntamentoTipoId(), sId));
			}
			a.setAppuntamentoSoggettoLista(
					appuntamentoSoggettoDao.getAppuntamentoSoggettoByAppuntamentoWsoggetto(a.getAppuntamentoId()));
		}

		var prescrizioni = presctizioneDao.getByListaPrescrizioneId(praticaId, filterDate);
		for (ModelPrescrizioneEsteso p : prescrizioni) {
			if (filterDate != null && p.getDataoraChiusura() != null && p.getDataoraChiusura().after(filterDate)) {
				p.setDataoraChiusura(null);
			}

			if (Objects.nonNull(p.getPrescrizioneTipoId())) {
				p.setPrescrizioneTipo(
						prescrizioneService.getPrescrizioneTipoDecodificaById(p.getPrescrizioneTipoId(), sId));
			}
		}
		Map<String, List<String>> objectMap = body.getChiaveValore().stream().collect(Collectors.groupingBy(
				ModelChiaveValore::getChiave, Collectors.mapping(ModelChiaveValore::getValore, Collectors.toList())));
		return generaDiario(objectMap, pratica, praticaTipo, listaAttivita, appuntamenti, prescrizioni, filterDate!=null?filterDate:new Date());
	}

	private static FileCustom generaDiario(Map<String, List<String>> chiaveValore, Pratica pratica,
			PraticaTipo praticaTipo, List<PraticaDettaglioEstesaPraticaPerDiario> listaAttivita,
			List<AppuntamentoEstesoPerDiario> appuntamenti, List<ModelPrescrizioneEsteso> prescrizioni, Date filterDate) {
		try {
			var fileBuilder = GeneratoreFileBuilderPdf.builder("pratica_" + pratica.getPraticaNumero())
					.setDistanzaDivisorio(3);
			PdfMapperBloccoDiTesto mapperBloccoDiTesto = new PdfMapperBloccoDiTesto();

			aggiungiRigaCentrata(new Testo("DIARIO PRATICA", Standard14Fonts.FontName.HELVETICA_BOLD, 14L, Color.BLUE),
					fileBuilder, mapperBloccoDiTesto);
			separatore(fileBuilder, mapperBloccoDiTesto, 10L);
			aggiungiRiga(new Testo("ASL di riferimento: " + getValoreSingolo(chiaveValore, "ENTE_DESC"),
					Standard14Fonts.FontName.HELVETICA, 10L, Color.BLACK), fileBuilder, mapperBloccoDiTesto);
			aggiungiRiga(new Testo(praticaTipo.getPraticaTipoDesc() + " " + pratica.getPraticaNumero(),
					Standard14Fonts.FontName.HELVETICA, 10L, Color.BLACK), fileBuilder, mapperBloccoDiTesto);
			separatore(fileBuilder, mapperBloccoDiTesto, 10L);
			aggiungiRiga(getTestoBase("Struttura: " + getValoreSingolo(chiaveValore, "STRUTTURA_COD_ARPE") + " "
					+ getValoreSingolo(chiaveValore, "STRUTTURA_DESC")), fileBuilder, mapperBloccoDiTesto);
			separatore(fileBuilder, mapperBloccoDiTesto, 10L);
			aggiungiRiga(getTestoBase("Indirizzo: " + getValoreSingolo(chiaveValore, "STRUTTURA_INDIRIZZO") + " "
					+ getValoreSingolo(chiaveValore, "COMUNE")), fileBuilder, mapperBloccoDiTesto);
			separatore(fileBuilder, mapperBloccoDiTesto, 10L);
			aggiungiRiga(getTestoBase("Email: " + getValoreSingolo(chiaveValore, "EMAIL")), fileBuilder,
					mapperBloccoDiTesto);
			aggiungiRiga(getTestoBase("Telefono: " + getValoreSingolo(chiaveValore, "TELEFONO")), fileBuilder,
					mapperBloccoDiTesto);
			aggiungiRiga(getTestoBase("Tipo struttura: " + getValoreSingolo(chiaveValore, "TIPO_STRUTTURA_DESC")),
					fileBuilder, mapperBloccoDiTesto);

			List<Testo> voceBlocco = new ArrayList<>();
			if (chiaveValore.containsKey("CATEGORIA_STRUTTURA")) {
				for (String valore : chiaveValore.get("CATEGORIA_STRUTTURA")) {
					voceBlocco.add(getTestoBase(valore));
				}
			}
			BloccoDiTesto testoDx = new BloccoDiTesto(voceBlocco);

			fileBuilder.aggiungiBlocchiDivisiDaDivisorioSpecifico(
					getBloccoTestoSingolo(getTestoBase("Categorie struttura: ")), mapperBloccoDiTesto, testoDx,
					new PdfMapperBloccoDiTestoLista());

			separatore(fileBuilder, mapperBloccoDiTesto, 10L);
			aggiungiRiga(getTestoBase("Situazione al: " + formatDataWhitDefault(filterDate)),
					fileBuilder, mapperBloccoDiTesto);
//			Categorie struttura: <lista categorie struttura> -> tutte le struttura categoria desc associate tramite la r struttura categoria.
			separatore(fileBuilder, mapperBloccoDiTesto, 10L);
			List<List<Testo>> valori = new ArrayList<>();
			for (PraticaDettaglioEstesaPraticaPerDiario attivita : listaAttivita) {
				if (Objects.nonNull(attivita.getFlgScadenza())) {
					continue;
				}
				valori.add(generaRighe(formatDataWhitDefault(attivita.getDataoraAzione()),
						getValoreDefault(attivita.getAppuntamentoNumero()),
						getValoreDefault(attivita.getPrescrizioneNumero()), attivita.getAzioneDesc(),
						attivita.getNote()));
			}

			List<Integer> dimensione = generaDimensioni(Integer.valueOf(12), Integer.valueOf(12), Integer.valueOf(12),
					Integer.valueOf(32), Integer.valueOf(32));
			Tabella listaSoggetti = new Tabella(generaIntestazioni("DATA ORA", "SPLG", "PRESC", "AZIONE", "NOTE"),
					valori);
			fileBuilder.aggiungiOggetto(listaSoggetti, new PdfMapperTabella(dimensione));

			for (AppuntamentoEstesoPerDiario appuntamento : appuntamenti) {
				fileBuilder.addPage();
				aggiungiRiga(new Testo(appuntamento.getAppuntamentoTipo().getAppuntamentoTipoDesc()
						+ " " + appuntamento.getAppuntamentoNumero(), Standard14Fonts.FontName.HELVETICA_BOLD, 12L, Color.BLUE),
						fileBuilder, mapperBloccoDiTesto);
				separatore(fileBuilder, mapperBloccoDiTesto, 10L);
				
//				aggiungiRiga(
//						new Testo(
//								appuntamento.getAppuntamentoNumero() + " "
//										+ appuntamento.getAppuntamentoTipo().getAppuntamentoTipoDesc(),
//								Standard14Fonts.FontName.HELVETICA, 10L, Color.BLACK),
//						fileBuilder, mapperBloccoDiTesto);
				aggiungiRiga(
						new Testo(
								"Apertura: " + formatDataWhitDefault(appuntamento.getDataoraApertura()) + " Chiusura: "
										+ formatDataWhitDefault(appuntamento.getDataoraChiusura()),
								Standard14Fonts.FontName.HELVETICA, 10L, Color.BLACK),
						fileBuilder, mapperBloccoDiTesto);
				aggiungiRiga(
						new Testo(
								"Inizio: " + formatDataWhitDefault(appuntamento.getDataoraInizio()) + " Fine: "
										+ formatDataWhitDefault(appuntamento.getDataoraFine()),
								Standard14Fonts.FontName.HELVETICA, 10L, Color.BLACK),
						fileBuilder, mapperBloccoDiTesto);
				aggiungiRiga(
						new Testo("Stato: " + appuntamento.getAppuntamentoStato().getAppuntamentoStatoDesc(),
								Standard14Fonts.FontName.HELVETICA, 10L, Color.BLACK),
						fileBuilder, mapperBloccoDiTesto);
				separatore(fileBuilder, mapperBloccoDiTesto, 10L);
				aggiungiTabellaAppuntamentoSoggettoPerAppuntamento(fileBuilder,
						appuntamento.getAppuntamentoSoggettoLista());
			}


			for (ModelPrescrizioneEsteso prescrizione : prescrizioni) {
				fileBuilder.addPage();
				aggiungiRiga(new Testo(prescrizione.getPrescrizioneTipo().getPrescrizioneTipoDesc() +
						" " + prescrizione.getPrescrizioneNumero(), Standard14Fonts.FontName.HELVETICA_BOLD, 12L, Color.BLUE),
						fileBuilder, mapperBloccoDiTesto);
				separatore(fileBuilder, mapperBloccoDiTesto, 10L);
//				aggiungiRiga(
//						new Testo(
//								prescrizione.getPrescrizioneNumero() + " "
//										+ prescrizione.getPrescrizioneTipo().getPrescrizioneTipoDesc(),
//								Standard14Fonts.FontName.HELVETICA, 10L, Color.BLACK),
//						fileBuilder, mapperBloccoDiTesto);
				aggiungiRiga(
						new Testo(
								"Apertura: " + formatDataWhitDefault(prescrizione.getDataoraApertura()) + " Chiusura: "
										+ formatDataWhitDefault(prescrizione.getDataoraChiusura()),
								Standard14Fonts.FontName.HELVETICA, 10L, Color.BLACK),
						fileBuilder, mapperBloccoDiTesto);
				aggiungiRiga(
						new Testo("Stato: " + prescrizione.getPrescrizioneStato().getPrescrizioneStatoDesc(),
								Standard14Fonts.FontName.HELVETICA, 10L, Color.BLACK),
						fileBuilder, mapperBloccoDiTesto);
				separatore(fileBuilder, mapperBloccoDiTesto, 10L);
			}

			return fileBuilder.aggiungiPiePagina().generaFilePdf();
		} catch (IOException e1) {
			// TODO correggi errore
			e1.printStackTrace();
			throw new RuntimeException();
		}
	}

	private static String formatDataWhitDefault(Date date) {
		if (date == null)
			return "";
		return DateUtils.formatDate(date, DateFormatEnum.DB_TIMESTAMP_FORMAT);
	}

	private static void aggiungiRigaSxDx(String testoSx, String testoDx, PdfMapperBloccoDiTesto mapperBloccoDiTesto,
			GeneratoreFileBuilderPdf fileBuilder) throws IOException {
		fileBuilder.aggiungiBlocchiDivisiDaDivisorioSpecifico(getBloccoTestoSingolo(getTestoBase(testoSx)),
				mapperBloccoDiTesto, getBloccoTestoSingolo(getTestoBase(testoDx)), mapperBloccoDiTesto);
	}

	private static Testo getTestoBase(String dato) throws IOException {
		return new Testo(dato, Standard14Fonts.FontName.HELVETICA, 10L, Color.BLACK);
	}

	private static BloccoDiTesto getBloccoTestoSingolo(Testo testo) {
		var voceBlocco = new ArrayList<Testo>();
		voceBlocco.add(testo);
		return new BloccoDiTesto(voceBlocco);
	}

	private static String getValoreSingolo(Map<String, List<String>> chiaveValore, String key) {
		if (chiaveValore.containsKey(key)) {
			return chiaveValore.get(key).get(0);
		} else {
			return "";
		}
	}

	private static String getValoreDefault(String vaL) {
		return Objects.nonNull(vaL) ? vaL : "";
	}

	private static void aggiungiTabellaAppuntamentoSoggettoPerAppuntamento(GeneratoreFileBuilderPdf fileBuilder,
			List<AppuntamentoSoggettoEstesoWsoggetto> appuntamentoSoggettoList) throws IOException {

		List<List<Testo>> valori = new ArrayList<>();
		for (AppuntamentoSoggettoEstesoWsoggetto as : appuntamentoSoggettoList) {
			valori.add(generaRighe(as.getRuoloAppuntamentoSoggetto().getRuoloAppuntamentoSoggettoDesc(),
					as.getCognome(), as.getNome()));
		}

		List<Integer> dimensione = generaDimensioni(Integer.valueOf(40), Integer.valueOf(30), Integer.valueOf(30));
		Tabella listaSoggetti = new Tabella(generaIntestazioni("RUOLO", "COGNOME", "NOME"), valori);
		fileBuilder.aggiungiOggetto(listaSoggetti, new PdfMapperTabella(dimensione));
	}

	private static List<Integer> generaDimensioni(Integer... dimensioniColonne) {
		List<Integer> res = new ArrayList<>();
		for (int i = 0; i < dimensioniColonne.length; i++) {
			res.add(dimensioniColonne[i]);
		}
		return res;
	}

	private static void separatore(GeneratoreFileBuilderPdf fileBuilder, PdfMapperBloccoDiTesto mapperBloccoDiTesto,
			long separatore) throws IOException {
		aggiungiRiga(new Testo("", Standard14Fonts.FontName.HELVETICA_BOLD, separatore, Color.BLUE), fileBuilder,
				mapperBloccoDiTesto);
	}

	private static void aggiungiRiga(Testo text, GeneratoreFileBuilderPdf fileBuilder,
			PdfMapperBloccoDiTesto mapperBloccoDiTesto) throws IOException {
		var spazio = new ArrayList<Testo>();
		spazio.add(text);
		fileBuilder.aggiungiOggetto(new BloccoDiTesto(spazio), mapperBloccoDiTesto);
	}

	private static List<Testo> generaIntestazioni(String... testi) throws IOException {
		List<Testo> res = new ArrayList<>();
		for (int i = 0; i < testi.length; i++) {

			res.add(new Testo(testi[i], Standard14Fonts.FontName.HELVETICA, 10, Color.BLUE));
		}
		return res;
	}

	private static List<Testo> generaRighe(String... testi) throws IOException {
		List<Testo> res = new ArrayList<>();
		for (int i = 0; i < testi.length; i++) {

			res.add(new Testo(testi[i], Standard14Fonts.FontName.HELVETICA, 9, Color.black));
		}
		return res;
	}

	private static void aggiungiRigaCentrata(Testo text, GeneratoreFileBuilderPdf fileBuilder,
			PdfMapperBloccoDiTesto mapperBloccoDiTesto) throws IOException {
		var spazio = new ArrayList<Testo>();
		spazio.add(text);
		BloccoDiTesto blocco = new BloccoDiTesto(spazio);
		blocco.setCenter(Boolean.TRUE);
		fileBuilder.aggiungiOggetto(blocco, mapperBloccoDiTesto);
	}

	public enum UpdateStatus {
		INSERT("I"), UPDATE("U"), DELETE("D"), ND("-");

		private final String tipo;

		UpdateStatus(String tipo) {

			this.tipo = tipo;
		}

		public String getTipo() {
			return tipo;
		}
	}

	public enum Scadenza {
		MANUALE("M"), AUTOMATICA("A");

		private final String tipo;

		Scadenza(String tipo) {

			this.tipo = tipo;
		}

		public String getTipo() {
			return tipo;
		}
	}

	public enum Abilitazione {
		WRITE("W");

		private final String tipo;

		Abilitazione(String tipo) {

			this.tipo = tipo;
		}

		public String getTipo() {
			return tipo;
		}
	}

	public ModelZipLista getModuliZip(PraticaEstesa pratica) {

		var res = new ModelZipLista();
		res.setFolderName(pratica.getPraticaNumero() + "_" + pratica.getPraticaId());

		for (ModelPraticaDettaglioEstesaPratica attivita : pratica.getAttivita()) {
			if (Objects.nonNull(attivita.getModuloCompilatoId())) {
				var fileToZip = new ModelZip();
				fileToZip.setModuloCompilatoId(attivita.getModuloCompilatoId());

				fileToZip.setFileName(pratica.getPraticaNumero() + "_" + DateUtils
						.formatDate(attivita.getDataoraAzione(), DateFormatEnum.UNDERSCORE_TIMESTAMP_FORMAT));
				res.getFiles().add(fileToZip);
			}

		}

		return res;
	}

	public ModelPraticaRequisiti getPraticaRequisito(Integer praticaId, Integer prescrizioneId, Integer praticaDetId,
			Integer clreqId, Integer sId) {

		try {
			return checkListReqDao.getPraticaRequisito(praticaId, prescrizioneId, praticaDetId, clreqId);
		} catch (EmptyResultDataAccessException e) {
			return new ModelPraticaRequisiti();
		}
	}

	public List<ChecklistReqEsteso> getPraticaRequisitiLista(Integer praticaId, Integer prescrizioneId,
			Integer praticaDetId, Integer sId) {
		var res = checkListReqDao.getFunctionByPraticaId(praticaId, prescrizioneId, praticaDetId);
		return res;
	}

	@Transactional
	public ModelPraticaClreqPost praticaRequisitiPost(ModelPraticaClreqPost body, String shibIdentitaCodiceFiscale,
			Integer sId) {
		var ids = new ArrayList<Integer>();
		// e io con sta lista vado a vedere se sono già associati
		// li ciclo se non trovo inserisco se trovo lascio.
		List<PraticaClreq> praticheOld = praticaClreqDao.selectValidiByPraticaIdAppuntamentoIdPraticaId(
				body.getPraticaId(), body.getPrescrizioneId(), body.getAppuntamentoId());
		for (Integer p : body.getClreqList()) {

			boolean trovato = false;
			Iterator<PraticaClreq> iteratore = praticheOld.iterator();

			while (iteratore.hasNext()) {
				var praticaOld = iteratore.next();
				if (praticaOld.getClreqId().equals(p)) {
					trovato = true;
					iteratore.remove();
					break;
				}
			}

			if (!trovato) {

				var praticaClreq = new PraticaClreq();
				praticaClreq.setPraticaClreqId(praticaClreqDao.getSequence());
				praticaClreq.setPraticaId(body.getPraticaId());
				praticaClreq.setPrescrizioneId(body.getPrescrizioneId());
				praticaClreq.setAppuntamentoId(body.getAppuntamentoId());
				praticaClreq.setClreqId(p);
				praticaClreq.setUtenteCreazione(shibIdentitaCodiceFiscale);
				praticaClreqDao.insert(praticaClreq);
				ids.add(praticaClreq.getPraticaClreqId());
			}
		}
		logAuditDb(sId, AuditOperazioneEnum.INSERT, "vigil_r_pratica_clreq", ids.toString());
		ids.clear();

		if (!praticheOld.isEmpty()) {
			for (PraticaClreq praticaOld : praticheOld) {
				praticaOld.setUtenteCancellazione(shibIdentitaCodiceFiscale);
				praticaClreqDao.delete(praticaOld);
				ids.add(praticaOld.getPraticaClreqId());
			}
		}
		logAuditDb(sId, AuditOperazioneEnum.LOGIC_DELETE, "vigil_r_pratica_clreq", ids.toString());
		return body;
	}

	@Transactional
	public ModelPraticaDettaglioClreq praticaDettaglioRequisitiPost(ModelPraticaDettaglioClreq body,
			String shibIdentitaCodiceFiscale, Integer sId) {
		PraticaDettaglioClreq res = new PraticaDettaglioClreq();
		AuditOperazioneEnum action = AuditOperazioneEnum.INSERT;
		StringBuilder ids = new StringBuilder();

		var olds = praticaDettaglioClreqDao.getValidoByPraticaDetIdAndClreqId(body.getPraticaDetId(),
				body.getClreqId());
		if (!olds.isEmpty()) {
			ids.append("old: [");
			for (PraticaDettaglioClreq old : olds) {
				old.setUtenteCancellazione(shibIdentitaCodiceFiscale);
				praticaDettaglioClreqDao.delete(old);
				ids.append(old.getPraticaDetClreqId() + " ");
			}
			action = AuditOperazioneEnum.UPDATE;
			ids.append("] new: ");
		}

		res.setPraticaDetClreqId(praticaDettaglioClreqDao.getSequence());
		res.setPraticaDetId(body.getPraticaDetId());
		res.setClreqEsitoId(body.getClreqEsitoId());
		res.setClreqId(body.getClreqId());
		res.setModuloCompilatoId(body.getModuloCompilatoId());
		res.setNote(body.getNote());
		res.setUtenteCreazione(shibIdentitaCodiceFiscale);
		praticaDettaglioClreqDao.insert(res);
		logAuditDb(sId, action, "vigil_r_pratica_dettaglio_clreq", ids.append(res.getPraticaDetClreqId()).toString());

		return res;
	}

	@Transactional
	public ModelPraticaDettaglioClreq praticaDettaglioRequisitiDeletePost(ModelPraticaDettaglioClreq body,
			String shibIdentitaCodiceFiscale, Integer sId) {
		var olds = praticaDettaglioClreqDao.getValidoByPraticaDetIdAndClreqId(body.getPraticaDetId(),
				body.getClreqId());
		PraticaDettaglioClreq res = null;
		for (PraticaDettaglioClreq old : olds) {
			old.setUtenteCancellazione(shibIdentitaCodiceFiscale);
			praticaDettaglioClreqDao.delete(old);
			logAuditDb(sId, AuditOperazioneEnum.LOGIC_DELETE, "vigil_r_pratica_dettaglio_clreq",
					old.getPraticaDetClreqId().toString());
			res = old;
		}
		return res;
	}

	public Object getPraticaDettaglioRequisitiLista(Integer praticaDetId, Integer sId) {
		List<PraticaDettaglioClreqEsteso> res = praticaDettaglioClreqDao.getByPraticaDetId(praticaDetId);
		return res;
	}

	public List<ClreqEsito> getPraticaRequisitiEsitoLista(Integer sId) {
		return praticaDettaglioClreqDao.getDecodificaClreqEsito();
	}
}