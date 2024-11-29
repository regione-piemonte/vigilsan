/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { SezioniEntity } from "./SezioniEntity";

// DOCUMENTAZIONE MODULO LISTA
export interface Documentazione {
  moduloConfigCod: string | null;
  moduloConfigDesc: string | null;
  moduloConfigOrd: string | null;
  reccount: number | null;
  docFlgObbligatorio: boolean;
}

// POST DOCUMENTAZIONE
export interface SendDocumentazione {
  documentazione: {
    documentazioneId?: number | null;
    strutturaId: number | null;
    strCatId?: number | null;
    moduloCompilatoId?: number | null;
    moduloConfigId: number | null;
    dataoraDocumentazione: number | null;
    occorrenza: number | null;
  }
  moduloCompilato: {
    note: string | null;
    modulo: SezioniEntity;
  }
}
