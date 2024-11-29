/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

export interface MembroSopralluogo {
  validitaInizio: number | null;
  validitaFine: number | null;
  appuntamentoSoggettoId: number | null;
  appuntamentoId: number | null;
  soggettoId: number | null;
  ruoloAppuntamentoSoggettoId: number | null;
  ruoloAppuntamentoSoggetto: RuoloAppuntamentoSoggetto
  ruoloEnteSoggetto: RuoloEnteSoggetto
  soggetto: Soggetto
}

export interface RuoloAppuntamentoSoggetto {
  ruoloAppuntamentoSoggettoId: number | null;
  ruoloAppuntamentoSoggettoCod: string | null;
  ruoloAppuntamentoSoggettoDesc: string | null;
}

export interface RuoloEnteSoggetto {
  ruoloEnteSoggettoId: number | null;
  ruoloEnteSoggettoCod: string | null;
  ruoloEnteSoggettoDesc: string | null;
}

export interface Soggetto {
  soggettoId: number | null;
  codiceFiscale: string | null;
  nome: string | null;
  cognome: string | null;
  presenteConfiguratore: boolean;
  dataNascita: number | null;
}
// ---------------------------------------------------------
export interface RuoliMembriSopralluogo {
  ruoloAppuntamentoSoggettoId: number | null;
  ruoloAppuntamentoSoggettoCod: string | null;
  ruoloAppuntamentoSoggettoDesc: string | null;
}
