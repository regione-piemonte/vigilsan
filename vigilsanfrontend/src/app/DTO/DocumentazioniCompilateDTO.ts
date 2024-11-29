/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { ModuloCompilatoRidotto } from "./DocumentazioneModuloCompilatoRidottoDTO";

export interface DocumentazioneCompilata {
  documentazioneId: number | null;
  strutturaId: number | null;
  strCatId: number | null;
  moduloCompilatoId: number | null;
  moduloConfigCod: string | null;
  moduloConfigDesc: string | null;
  moduloConfigId: number | null;
  dataoraDocumentazione: number | null;
  dataoraScadenza: number | null;
  occorrenza: number;
  strutturaCategoria: null;
  modulo: Modulo;
  verificaDocumentazione: {
    verificaDocumentazioneId: number | null,
    documentazioneId: number | null,
    dataoraVerifica: number | null,
    esitoVerifica: boolean | null,
    note: string | null,
  }
}
export interface Modulo {
  moduloId: number | null;
  moduloCod: string | null;
  moduloDesc: string | null;
  moduloOrd: string | null;
  moduloCompilatoId: number | null;
  note: string | null;
  moduloCompilatoRidotto: ModuloCompilatoRidotto;
}
