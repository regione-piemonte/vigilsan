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
import org.springframework.transaction.annotation.Transactional;

import it.csi.vigilsan.vigilsancommon.api.generated.dto.ModelOspiteMovimento;
import it.csi.vigilsan.vigilsancommon.api.generated.dto.ModelOspiteMovimentoPost;
import it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl.OspiteCondizioniDao;
import it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl.OspiteMovimentoDao;
import it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl.OspiteMovimentoTipoDao;
import it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl.OspiteStatoDao;
import it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl.StrutturaSoggettoDao;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.OspiteCondizioni;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.OspiteMovimento;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.OspiteMovimentoEsteso;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.OspiteMovimentoTipoEsteso;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.OspiteMovimentoTipoEstesoGenerico;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.OspiteStato;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.StrutturaSoggetto;
import it.csi.vigilsan.vigilsancommon.api.persistence.repository.OspiteRepository;
import it.csi.vigilsan.vigilsancommon.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableWPersistenceApiServiceImpl;
import it.csi.vigilsan.vigilsanutil.generic.util.AuditOperazioneEnum;

@Service
public class OspiteServiceImpl extends AuditableWPersistenceApiServiceImpl {

	@Autowired
	private OspiteStatoDao ospiteStatoDao;

	@Autowired
	private OspiteRepository ospiteRepository;

	@Autowired
	private StrutturaSoggettoDao strutturaSoggettoDao;

	@Autowired
	private OspiteMovimentoDao ospiteMovimentoDao;

	@Autowired
	private OspiteMovimentoTipoDao ospiteMovimentoTipoDao;

	@Autowired
	private OspiteCondizioniDao ospiteCondizioniDao;

	@Autowired
	private StrutturaCategoriaServiceImpl strutturaCategoriaService;

	public List<OspiteStato> getStatoAllValid(Integer sId) {
		return ospiteStatoDao.getAll();
	}

	public List<OspiteMovimentoTipoEsteso> getOspiteMovimentoTipoAllValid(Integer sId) {
		var res = ospiteMovimentoTipoDao.getOspiteMovimentoTipoAllValid();
		for (OspiteMovimentoTipoEsteso movimentoTipo : res) {
			movimentoTipo.setOspiteMovimentoTipoConfig(
					ospiteRepository.getOspiteMovimentoTipoConfigByOspiteMovimentoTipoIdValid(
							movimentoTipo.getOspiteMovimentoTipoId()));
		}
		return res;
	}

	@Transactional
	public ModelOspiteMovimento postOspiteMovimento(Integer sId, Integer strutturaId, ModelOspiteMovimentoPost body,
			String shibIdentitaCodiceFiscale) {
		var res = new OspiteMovimento();
		res.setNote(body.getNote());
		res.setOspiteMovimentoTipoId(body.getOspiteMovimentoTipoId());
		res.setDataMovimento(body.getDataMovimento());
		res.setOspiteCondizioniId(body.getOspiteCondizioniId());
		res.setStrutturaIdOd(body.getStrutturaIdOd());
		res.setUtenteCreazione(shibIdentitaCodiceFiscale);
		res.setStrutturaCategoriaId(body.getStrutturaCategoriaId());
//		calcola stato alla data_movimento --> tira fuori uno stato.
		RESTErrorUtil.checkCondition(ospiteRepository
				.getListaOspiteMovimentoIdFromDataMovimentoAndStrIdAndSoggId(res.getDataMovimento(),
						body.getSoggettoId(), strutturaId, res.getOspiteMovimentoTipoId())
				.stream().allMatch(v -> v.getFlgValido()), ErrorCodeEnum.STATO_OSPITE_NON_CONSISTENTE);

		// verifica se sia nella lista

		// inserisci movimento
		StrutturaSoggetto strutturaSoggetto = verificaOinserisciStrutturaSoggetto(sId, strutturaId, body,
				res.getDataMovimento(), shibIdentitaCodiceFiscale);
		res.setStrSoggId(strutturaSoggetto.getStrSoggId());
		res.setOspiteMovimentoId(ospiteMovimentoDao.getSequence());
		ospiteMovimentoDao.insert(res);
		logAuditDb(sId, AuditOperazioneEnum.INSERT, "vigil_t_ospite_movimento", res.getOspiteMovimentoId().toString());

		return res;
	}

	private StrutturaSoggetto verificaOinserisciStrutturaSoggetto(Integer sId, Integer strutturaId,
			ModelOspiteMovimentoPost body, Date dataMovimento, String shibIdentitaCodiceFiscale) {
		StrutturaSoggetto strutturaSoggetto;
		try {
			strutturaSoggetto = strutturaSoggettoDao.getBySoggettoIdAndStrutturaIdValid(body.getSoggettoId(),
					strutturaId);
			if (dataMovimento.before(strutturaSoggetto.getValiditaInizio())) {
				// fai update
				strutturaSoggetto.setValiditaInizio(dataMovimento);
				strutturaSoggetto.setUtenteModifica(shibIdentitaCodiceFiscale);
				strutturaSoggettoDao.update(strutturaSoggetto, strutturaSoggetto.getStrSoggId());
				logAuditDb(sId, AuditOperazioneEnum.UPDATE, "vigil_r_struttura_soggetto",
						strutturaSoggetto.getStrSoggId().toString());
			}
		} catch (EmptyResultDataAccessException e) {
			// aggiungi strutturaSoggetto
			strutturaSoggetto = new StrutturaSoggetto();
			strutturaSoggetto.setStrSoggId(strutturaSoggettoDao.getSequence());
			strutturaSoggetto.setStrutturaId(strutturaId);
			strutturaSoggetto.setSoggettoId(body.getSoggettoId());
			strutturaSoggetto
					.setRuoloStrutturaSoggettoId(ospiteRepository.getRuoloStrutturaIdByRuoloStrutturaCod("OSPITE"));
			strutturaSoggetto.setValiditaInizio(dataMovimento);
			strutturaSoggetto.setUtenteCreazione(shibIdentitaCodiceFiscale);
			logAuditDb(sId, AuditOperazioneEnum.INSERT, "vigil_r_struttura_soggetto",
					strutturaSoggetto.getStrSoggId().toString());
			strutturaSoggettoDao.insert(strutturaSoggetto);
		}
		return strutturaSoggetto;
	}

	@Transactional
	public ModelOspiteMovimento deleteOspiteMovimento(Integer sId, ModelOspiteMovimento body, Integer strutturaId,
			String shibIdentitaCodiceFiscale) {
		var ospiteMovimento = getOspiteMovimento(sId, body.getOspiteMovimentoId());
		RESTErrorUtil.checkCondition(Objects.isNull(ospiteMovimento.getDataCancellazione()),
				ErrorCodeEnum.OSPITE_MOVIMENTO_GIA_CANCELLATO);
		var strSoggetto = getStrutturaSoggetto(sId, ospiteMovimento.getStrSoggId());
		RESTErrorUtil.checkCondition(strSoggetto.getStrutturaId().equals(strutturaId),
				ErrorCodeEnum.STRUTTURA_ID_NON_COERENTE);
		RESTErrorUtil.checkCondition(
				ospiteRepository.getListaOspiteMovimentoIdForDelete(body.getOspiteMovimentoId(),
						strSoggetto.getSoggettoId(), strutturaId).stream().allMatch(v -> v.getFlgValido()),
				ErrorCodeEnum.STATO_OSPITE_NON_CONSISTENTE_DELETE);

		ospiteMovimento.setUtenteCancellazione(shibIdentitaCodiceFiscale);
		ospiteMovimentoDao.delete(ospiteMovimento);
		logAuditDb(sId, AuditOperazioneEnum.LOGIC_DELETE, "vigil_t_ospite_movimento",
				ospiteMovimento.getOspiteMovimentoId().toString());

		// verifico se ci sono ancora movimenti associati sennò cancello l'associazione
		if (ospiteMovimentoDao.getByStrSoggIdValidi(strSoggetto.getStrSoggId()).isEmpty()) {
			strSoggetto.setUtenteCancellazione(shibIdentitaCodiceFiscale);
			strutturaSoggettoDao.delete(strSoggetto);
			logAuditDb(sId, AuditOperazioneEnum.LOGIC_DELETE, "vigil_r_struttura_soggetto",
					strSoggetto.getStrSoggId().toString());

		}

		return ospiteMovimento;

	}

	private OspiteMovimento getOspiteMovimento(Integer sId, Integer ospiteMovimentoId) {
		try {
			return ospiteMovimentoDao.get(ospiteMovimentoId);
		} catch (EmptyResultDataAccessException e) {
			logError(sId, "getOspiteMovimento", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.OSPITE_MOVIMENTO_NON_TROVATO);
		}
	}

	public List<OspiteMovimentoEsteso> getOspiteMovimentoTipoAllValid(Integer sId, Integer strutturaId,
			Integer soggettoId) {
		var res = ospiteMovimentoDao.getBySoggettoIdAndStrutturaIdValido(strutturaId, soggettoId);
		for (OspiteMovimentoEsteso movimento : res) {
			if (Objects.nonNull(movimento.getOspiteCondizioniId())) {
				movimento.setOspiteCondizioni(getOspiteCondizioniById(sId, movimento.getOspiteCondizioniId()));
			}
			OspiteMovimentoTipoEstesoGenerico ospiteMovimentoTipo = getOspiteMovimentoTipo(sId,
					movimento.getOspiteMovimentoTipoId());
			movimento.setOspiteMovimentoTipo(ospiteMovimentoTipo);
			if (Objects.nonNull(ospiteMovimentoTipo.getOspiteStatoId())) {
				ospiteMovimentoTipo.setOspiteStato(ospiteStatoDao.get(ospiteMovimentoTipo.getOspiteStatoId()));

			}

			if (Objects.nonNull(movimento.getStrutturaCategoriaId())) {
				movimento.setStrutturaCategoria(
						strutturaCategoriaService.getById(sId, movimento.getStrutturaCategoriaId()));
			}
		}
		return res;
	}

	private StrutturaSoggetto getStrutturaSoggetto(Integer sId, Integer strutturaSoggettoId) {
		try {
			return strutturaSoggettoDao.get(strutturaSoggettoId);
		} catch (EmptyResultDataAccessException e) {
			logError(sId, "getStrutturaSoggetto", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.STRUTTURA_SOGGETTO_NON_TROVATO);
		}
	}

	private OspiteCondizioni getOspiteCondizioniById(Integer sId, Integer id) {
		try {
			return ospiteCondizioniDao.get(id);
		} catch (EmptyResultDataAccessException e) {
			logError(sId, "getOspiteCondizioniById", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.OSPITE_CONDIZIONI_NON_TROVATA);
		}
	}

	private OspiteMovimentoTipoEstesoGenerico getOspiteMovimentoTipo(Integer sId, Integer id) {
		try {
			return ospiteMovimentoTipoDao.get(id, OspiteMovimentoTipoEstesoGenerico.class);
		} catch (EmptyResultDataAccessException e) {
			logError(sId, "getOspiteMovimentoTipo", e.getMessage(), e);
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.OSPITE_MOVIMENTO_TIPO_NON_TROVATO);
		}
	}

	public List<OspiteCondizioni> getOspiteCondizioniLista(Integer sId) {
		return ospiteCondizioniDao.getAll();
	}

}
