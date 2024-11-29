/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

export interface SoggettoCommissione {
  validitaInizio: number | null;
  validitaFine: number | null;
  enteSoggId: number | null;
  enteId: number | null;
  soggettoId: number | null;
  ruoloEnteSoggettoId: number | null;
  ruoloEnteSoggetto: RuoloCommissione | null;
  soggetto: Soggetto | null;
}

export interface Soggetto {
  soggettoId: number | null;
  codiceFiscale: string | null;
  nome: string | null;
  cognome: string | null;
  presenteConfiguratore: boolean | null;
  dataNascita: number | null;
  enteSoggId?: number | null;
}

// RUOLI --------------------------
export interface RuoloCommissione {
  ruoloEnteSoggettoId: number | null;
  ruoloEnteSoggettoCod: string | null;
  ruoloEnteSoggettoDesc: string | null;
}
