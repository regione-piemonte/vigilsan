/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsanrilevazioni.api.service.impl;

import java.io.ByteArrayOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.repository.impl.ModuloConfigRepository;
import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.repository.impl.RilevazioneDocumentazioneCsvRepository;
import it.csi.vigilsan.vigilsanrilevazioni.api.persistence.repository.impl.TableRepository;
import it.csi.vigilsan.vigilsanrilevazioni.util.ErrorCodeEnum;
import it.csi.vigilsan.vigilsanutil.generic.services.error.RESTErrorUtil;
import it.csi.vigilsan.vigilsanutil.generic.services.log.AuditableWPersistenceApiServiceImpl;
import it.csi.vigilsan.vigilsanutil.generic.util.DateFormatEnum;
import it.csi.vigilsan.vigilsanutil.generic.util.DateUtils;

@Service
public class RilevazioneDocumentazionePivotServiceImpl extends AuditableWPersistenceApiServiceImpl {

	@Autowired
	private RilevazioneDocumentazioneCsvRepository rilevazioneDocumentazioneRepo;
	@Autowired
	private TableRepository tableRepository;

	@Autowired
	private ModuloConfigRepository moduloConfigRepository;

	public ByteArrayOutputStream funzionalitaRilevazioneCompilataListaCsvGet(Integer sId, String moduloConfigCod,
			Integer strutturaId, Integer enteId, String dataRilevazioneDa, String dataRilevazioneA,
			String strutturaCategoriaCod) {
		RESTErrorUtil.checkCondition(moduloConfigRepository.existModuloConfigByModuloConfigCod(moduloConfigCod),
				ErrorCodeEnum.MODULO_CONFIG_COD_NON_COERENTE);
		// verifico siano parsificabili
		DateUtils.parsetDate(dataRilevazioneDa, DateFormatEnum.API_DATE_FORMAT);
		DateUtils.parsetDate(dataRilevazioneA, DateFormatEnum.API_DATE_FORMAT);

		return rilevazioneDocumentazioneRepo.selectRilevazioneCompilataListaCsvGet(moduloConfigCod, strutturaId, enteId,
				dataRilevazioneDa, dataRilevazioneA, strutturaCategoriaCod);

	}

	@Transactional
	public void funzionalitaRilevazionePivotModuliGeneraView(Integer sId) {
		try {
			// tabelle rilevazioni
			for (String moduloConfigCod : moduloConfigRepository.getDistincModuloConfigRilevazioniNonCancellati()) {
				// drop if exist
				rilevazioneDocumentazioneRepo.dropViewDvhExportModuli(moduloConfigCod);
				// reinsert
				rilevazioneDocumentazioneRepo.creaViewDvhExportModuliRilevazione(moduloConfigCod, null, null, null,
						null, null);

				rilevazioneDocumentazioneRepo.grantAccessViewDvhExportModuliRilevazione(moduloConfigCod, "vigilsan_ro");
			}
			// tabelle documentazioni
			for (String moduloConfigCod : moduloConfigRepository.getDistincModuloConfigDocumentazioniNonCancellati()) {
				// drop if exist
				rilevazioneDocumentazioneRepo.dropViewDvhExportModuli(moduloConfigCod);
				// reinsert
				rilevazioneDocumentazioneRepo.creaViewDvhExportModuliDocumentazione(moduloConfigCod, null, null, null,
						null, null);

				rilevazioneDocumentazioneRepo.grantAccessViewDvhExportModuliRilevazione(moduloConfigCod, "vigilsan_ro");

			}
			// tabelle varie
			for (String tableName : tableRepository.getTablesOver32CharLength()) {
				var newTableName = tableName.replaceFirst("vigil", "v");
				if (newTableName.length() > 32) {
					newTableName = newTableName.substring(0, 32);
				}

				// drop if exist
				tableRepository.dropViewExport(newTableName);
				// reinsert
				tableRepository.creaViewExport(newTableName, tableName);

				tableRepository.grantAccessViewExport(newTableName, "vigilsan_ro");

			}
		} catch (Exception e) {
			logError(sId, "funzionalitaRilevazionePivotModuliGeneraView", e.getMessage(), e);
			throw e;
		}
	}

}
