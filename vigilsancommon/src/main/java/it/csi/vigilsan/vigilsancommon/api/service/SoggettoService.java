/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.vigilsan.vigilsancommon.api.service;

import java.util.List;

import it.csi.vigilsan.vigilsancommon.api.persistence.model.Soggetto;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.SoggettoEstesoLista;
import it.csi.vigilsan.vigilsancommon.api.persistence.model.interfacecsv.SoggettoEstesoListaForCsv;
import it.csi.vigilsan.vigilsanutil.generic.util.paginazione.Paginazione;

public interface SoggettoService {

	Soggetto getSoggetto(Integer soggettoId);

	Soggetto getSoggetto(String codiceFiscale);

	void insertSoggetto(Integer sessionId, String codiceFiscale, String nome, String cognome,
			Boolean presenteConfiguratore, String utenteOperazione);

	List<SoggettoEstesoLista> getDocumentazioneCompilataList(Integer sId, String ruoloStrutturaCod, Integer strutturaId,
			String filter, Integer ospiteStatoId, String dataIngressoDa, String dataIngressoA, String dataUscitaDa,
			String dataUscitaA, Paginazione paginazione);

	List<SoggettoEstesoListaForCsv> getDocumentazioneCompilataList(Integer sId, String ruoloStrutturaCod,
			Integer strutturaId, String filter, Integer ospiteStatoId, String dataIngressoDa, String dataIngressoA,
			String dataUscitaDa, String dataUscitaA);

	Soggetto soggettoPost(Integer sId, String codiceFiscale, String shibIdentitaCodiceFiscale);

}