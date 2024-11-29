/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

export interface RilevazioniPerTab {
  rilevazioneId: number | null;
  strutturaId: number | null;
  strCatId?: number | null;
  moduloCompilatoId: number | null;
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
  modulo: Modulo;
}
export interface Modulo {
  moduloId: number | null;
  moduloCod: string | null;
  moduloDesc: string | null;
  moduloOrd: string | null;
  moduloTitolo: string | null;
  moduloCompilatoRidotto: ModuloCompilatoRidotto | null;
}
export interface ModuloCompilatoRidotto {
  moduloId: number | null;
  moduloCod: string | null;
  moduloDesc: string | null;
  moduloOrd: string | null;
  moduloTitolo: string | null;
  moduloIdPadre?: number | null;
  validitaInizio?: number | null;
  validitaFine?: number | null;
  dataCreazione?: number | null;
  dataModifica?: number | null;
  dataCancellazione?: number | null;
  utenteCreazione?: string | null;
  utenteModifica?: string | null;
  utenteCancellazione?: string | null;
  sezioni?: (SezioniEntity)[] | null;
  voci?: (VociEntity)[] | null;
}
export interface SezioniEntity {
  moduloId: number | null;
  moduloCod: string | null;
  moduloDesc: string | null;
  moduloOrd: string | null;
  moduloTitolo: string | null;
  moduloIdPadre: number | null;
  validitaInizio?: number | null;
  validitaFine?: number | null;
  dataCreazione?: number | null;
  dataModifica?: number | null;
  dataCancellazione?: number | null;
  utenteCreazione?: string | null;
  utenteModifica?: string | null;
  utenteCancellazione?: string | null;
  sezioni?: (SezioniEntity)[] | null;
  voci?: (VociEntity | null)[] | null;
}
export interface VociEntity {
  moduloId: number | null;
  moduloVoceId: number | null;
  moduloVoceCod: string | null;
  moduloVoceDesc: string | null;
  moduloVoceHint?: string | null;
  moduloVoceIdPadre?: number | null;
  moduloVoceTipoDati: string;
  moduloVoceUnitaMisura?: null;
  moduloListaId?: number | null;
  fileGruppoId?: number | null;
  valore?: string | null;
  note?: string | null;
  lista?: null;
  regole?: (null)[] | null;
  fileGruppo?: null;
  voci?: (VociEntity)[] | null;
}
