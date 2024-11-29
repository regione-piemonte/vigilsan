/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { FormControl } from "@angular/forms";

export interface SezioniEntity {
  moduloId: number | null;
  moduloCod: string | null;
  moduloDesc: string | null;
  moduloOrd: string | null;
  moduloTitolo: string | null;
  moduloIdPadre: number | null;
  validitaInizio?: null;
  validitaFine?: null;
  dataCreazione?: null;
  dataModifica?: null;
  dataCancellazione?: null;
  utenteCreazione?: string | null;
  utenteModifica?: string | null;
  utenteCancellazione?: string | null;
  sezioni?: (SezioniEntity)[] | null;
  voci?: (VociEntity)[] | null;
  note: string | null;
  visible?: boolean;
  visibleMsg?: string;
  regole?: (roulesSezioni)[] | null;
}
export interface VociEntity {
  moduloId: number | null;
  moduloVoceId: number | null;
  moduloVoceCod: string | null;
  moduloVoceDesc: string | null;
  moduloVoceHint?: string | null;
  moduloVoceIdPadre?: number | null;
  moduloVoceTipoDati: string | null;
  moduloVoceUnitaMisura?: null;
  moduloListaId?: number | null;
  moduloVoceDescCheck: string | null;
  flgCheck: boolean;
  fileGruppoId?: number | null;
  lista?: Lista | null;
  regole?: (roules)[] | null;
  fileGruppo?: FileGruppo | null;
  voci?: (VociEntity | null)[] | null;
  valore?: string | null;
  voceControl?: FormControl;
  voceControlTime?: FormControl;
  note: string | null;
}
export interface Lista {
  moduloListaCod: string | null;
  moduloListaDesc: string | null;
  moduloListaHint: string | null;
  moduloListaId: number | null;
  valori?: (ValoriEntity)[] | null;
  valore?: ValoriEntity | null;
  moduloListaOccMin: number | null;
  moduloListaOccMax: number | null;
}
export interface ValoriEntity {
  moduloListaValoreId: number | null;
  moduloListaValoreCod: string | null;
  moduloListaValoreDesc: string | null;
  moduloListaValoreHint?: string | null;
  valore: boolean;
}
export interface roules {
  nomeRegola?: string;
  moduloVoceRegolaTipo: string; // Che tipo: [calcolo,visibilita]
  moduloVoceRegolaExec: string; // Regola da controllare
  moduloVoceRegolaErrore: string; // Hint errore
}

export interface roulesSezioni {
  moduloRegolaId: number | null;
  moduloRegolaTipo: string | null;
  moduloRegolaExec: string | null;
  moduloRegolaErrore: string | null;
}

export interface FileGruppo {
  fileGruppoCod: string | null;
  fileGruppoDesc: string | null;
  fileTipi?: (FileTipiEntity)[] | null;
}
export interface FileTipiEntity {
  fileTipoId: number | null;
  fileTipoCod: string | null;
  fileTipoDesc: string | null;
  fileTipoHint: string | null;
  fileTipoObbligatorio: boolean | null;
  fileTipoFirmaRichiesta: boolean | null;
  fileId?: number | null;
  fileName: string | null;
  fileNomeCaricato?: string | null;
  fileContentTypes: number[];
  note: string | null;
  voceControl?: any | null;
}
