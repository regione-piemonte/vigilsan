/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { ModuloCompilatoRidotto } from "./DocumentazioneModuloCompilatoRidottoDTO";

export interface DocumentazioneCompilabile {
  documentazioneId: number | null;
  strutturaId: number | null;
  strCatId: number | null;
  moduloCompilatoId: number | null;
  moduloConfigId: number | null;
  dataoraDocumentazione: number | null;
  dataoraScadenza: number | null;
  occorrenza: number | null;
  modulo: Modulo;
  moduloConfigCod: string | null;
  moduloConfigDesc: string | null;
  docFlgObbligatorio: boolean | null;
}
export interface Modulo {
  moduloId: number | null;
  moduloCod: string | null;
  moduloDesc: string | null;
  moduloOrd: string | null;
  moduloCompilatoRidotto: ModuloCompilatoRidotto;
}
