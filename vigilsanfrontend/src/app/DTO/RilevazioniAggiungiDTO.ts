/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { SezioniEntity } from "./SezioniEntity";

export interface SendRilevazione {
  rilevazione: {
    rilevazioneId?: number | null;
    strutturaId: number | null;
    strCatId?: number | null;
    moduloCompilatoId?: number | null;
    moduloConfigId: number | null;
    dataoraRilevazione: number | null;
    validitaInizio: number | null;
    validitaFine: number | null;
    dataCreazione?: number | null;
    dataModifica?: number | null;
    dataCancellazione?: number | null;
    utenteCreazione?: string | null;
    utenteModifica?: string | null;
    utenteCancellazione?: string | null;
  }
  moduloCompilato: {
    note: string | null;
    modulo: SezioniEntity;
  }
}
export interface RilevazioniAggiungi {
  rilevazioneId?: number | null;
  strutturaId: number | null;
  strCatId?: number | null;
  moduloCompilatoId?: number | null;
  moduloConfigId: number | null;
  dataoraRilevazione: number | null;
  validitaInizio: number | null;
  validitaFine: number | null;
  modulo: Modulo | null;
  strutturaCategoria: StrutturaCategoria | null;
}
export interface StrutturaCategoria {
  dataCancellazione: number | null;
  dataCreazione: number | null;
  dataModifica: number | null;
  strutturaCategoriaCod: string | null;
  strutturaCategoriaDesc: string | null;
  strutturaCategoriaId: number | null;
  utenteCancellazione: string | null;
  utenteCreazione: string | null;
  utenteModifica: string | null;
  validitaFine: number | null;
  validitaInizio: number | null;
}
export interface Modulo {
  moduloId: number | null;
  moduloCod: string | null;
  moduloDesc?: null;
  moduloOrd?: null;
}
