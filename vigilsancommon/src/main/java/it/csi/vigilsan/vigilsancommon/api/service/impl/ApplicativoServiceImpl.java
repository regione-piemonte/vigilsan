/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import it.csi.vigilsan.vigilsancommon.api.generated.dto.ModelApplicativo;
import it.csi.vigilsan.vigilsancommon.api.generated.dto.ModelAuth;
import it.csi.vigilsan.vigilsancommon.api.persistence.dao.impl.ApplicativoDao;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.Applicativo;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.ApplicativoOperazioneRidottoCustom;
import it.csi.vigilsan.vigilsancommon.api.persistence.repository.ApplicativoRepository;
import it.csi.vigilsan.vigilsancommon.api.persistence.repository.OperazioneProfiloRepository;
import it.csi.vigilsan.vigilsancommon.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import jakarta.validation.constraints.NotNull;

@Service
public class ApplicativoServiceImpl {

	@Autowired
	private ApplicativoRepository applicativoRepository;

	@Autowired
	private OperazioneProfiloRepository operazioneProfiloRepository;

	@Autowired
	private ApplicativoDao applicativoDao;

	public Applicativo getByApplicativoCod(String applicativoCod) {
		try {
			return applicativoRepository.getByApplicativoCod(applicativoCod);
		} catch (EmptyResultDataAccessException e) {
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.APPLICATIVO_NON_TROVATO);
		}
	}

	public ModelApplicativo getApplicativoByApplicativoId(@NotNull Integer applicativoId) {
		try {
			return applicativoDao.get(applicativoId);
		} catch (EmptyResultDataAccessException e) {
			throw RESTErrorUtil.generateGenericRestException(ErrorCodeEnum.APPLICATIVO_NON_TROVATO);
		}
	}

	public List<ModelAuth> getApplicativoOperazioneAuth(Integer applicativoId, Integer profiloId,
			Integer strutturaTipoId) {
		var operazioni = operazioneProfiloRepository.selectApplicativoOperazioneRidottoByApplicativoIdAndProfiloId(
				applicativoId, profiloId, strutturaTipoId);
		var res = new ArrayList<ModelAuth>();
		ModelAuth firstAuth = null;

		for (ApplicativoOperazioneRidottoCustom o : operazioni) {
			String funzione = o.getFunzione();
			String visibility = o.getPermesso();
			String[] subParts = funzione.split("-");
			if (firstAuth == null || !firstAuth.getF().equals(subParts[0])) {
				Boolean first = Boolean.TRUE;
				int length = subParts.length;
				ModelAuth lastAuth = null;
				for (int i = 0; i < length; i++) {
					var auth = new ModelAuth();
					auth.setF(subParts[i]);
					if (length == i + 1) {
						auth.setP(visibility);
					}

					if (Boolean.TRUE.equals(first)) {
						first = Boolean.FALSE;
						res.add(auth);
						firstAuth = auth;
						lastAuth = auth;
					} else {
						lastAuth.getA().add(auth);
						lastAuth = auth;
					}
				}
			} else if (!firstAuth.getF().equals(visibility)) {
				int length = subParts.length;
				ModelAuth lastAuth = firstAuth;

				for (int i = 1; i < length; i++) {
					String function = subParts[i];
					Optional<ModelAuth> currentAuth = lastAuth.getA().stream()
							.filter(obj -> obj.getF().equals(function)).findFirst();
					if (!currentAuth.isPresent()) {
						var auth = new ModelAuth();
						auth.setF(function);
						if (length == i + 1) {
							auth.setP(visibility);
						} else {
							auth.setP(lastAuth.getP());
						}
						lastAuth.getA().add(auth);
						lastAuth = auth;
						// aggiungilo
					} else {
						lastAuth = currentAuth.get();
					}
				}
			}

		}
		return res;
	}

	public List<ApplicativoOperazioneRidottoCustom> getApplicativoOperazioneAuthLista(Integer applicativoId,
			Integer profiloId, Integer strutturaId) {
		return operazioneProfiloRepository.selectApplicativoOperazioneByApplicativoIdAndProfiloId(applicativoId,
				profiloId, strutturaId);
	}
}
