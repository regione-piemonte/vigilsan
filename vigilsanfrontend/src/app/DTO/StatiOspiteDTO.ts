/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

export interface StatoOspite {
  validitaInizio: number | null;
  validitaFine?: number | null;
  dataCreazione: number | null;
  dataModifica?: number | null;
  dataCancellazione?: number | null;
  utenteCreazione: string | null;
  utenteModifica?: string | null;
  utenteCancellazione?: string | null;
  ospiteStatoId: number | null;
  ospiteStatoCod: string | null;
  ospiteStatoDesc: string | null;
  ospiteStatoOrd: string | null;
  ospiteStatoHint?: string | null;
  flgPresenza: boolean | null;
  flgPosto: boolean | null;
}
